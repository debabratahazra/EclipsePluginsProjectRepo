package com.odcgroup.workbench.generation.ui.action;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.AdaptableList;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.google.common.collect.Lists;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.FeatureSwitches;
import com.odcgroup.workbench.core.repository.ModelResourceVisitor;
import com.odcgroup.workbench.generation.GenerationCommon;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.ModelsHelper;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.cartridge.SelectionProvider;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;
import com.odcgroup.workbench.generation.ui.GenerationUICore;
import com.odcgroup.workbench.ui.OfsUICore;

public class CodeGenerationAction extends Action implements IWorkbenchWindowActionDelegate {
	//private static final Logger logger = LoggerFactory.getLogger(CodeGenerationAction.class);
	
	protected IStructuredSelection selection;
	protected IResource[] resourcesToSave;

	public CodeGenerationAction() {
		setText("Generate Code");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				GenerationUICore.PLUGIN_ID, "/icons/cog_go.png"));
	}

	public void dispose() {
	}	

	public void init(IWorkbenchWindow window) {
	}

	public void run() {
		selection = evaluateCurrentSelection();
		run(this);
	}

	public void run(IStructuredSelection selection) {
		this.selection = selection;
		run(this);
	}
	
	private void refreshGenFolders(Set<IFolder> foldersToRefresh, IProgressMonitor monitor) {
		monitor.beginTask("Post Generation, ", foldersToRefresh.size());
		try {
        	for (IFolder folder : foldersToRefresh) {
        		monitor.subTask("Refreshing folder : "+folder.getName());
        		folder.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 1));
        		monitor.worked(1);    
    		    if(monitor.isCanceled()) 
    		    	throw new OperationCanceledException();	        		
        	}
        	GenerationUICore.getDefault().logInfo(foldersToRefresh.size() + " folders refreshed.", null);
		} catch (CoreException e) {
			GenerationUICore.getDefault().logWarning("Error refreshing workspace", e);
		}
	}
	
	/**
	 * clean up the code-gen folders for the selected projects, before the generation
	 * 
	 * @param directlySelectedProjects
	 * @param monitor
	 */
	private Set<IFolder> cleanSelectedProjectsBeforeGen(Set<IProject> directlySelectedProjects, IProgressMonitor monitor) {
		
		Set<IFolder> foldersToRefresh = new HashSet<IFolder>();
		monitor.beginTask("Executing Cleanup ", directlySelectedProjects.size());
		
		for (IProject project : directlySelectedProjects) {			
			// Retrieve the gen project
			final String[] genProjectNames = GenerationCore.getJavaProjectNames(project);
			final String genProjectName = genProjectNames.length > 0 ? genProjectNames[0] : project.getName();
			
			// Avoid cleaning several time the same folder
			Set<IFolder> folderToClean = new CopyOnWriteArraySet<IFolder>();
			for (final CodeCartridge cartridge : GenerationCore.getRegisteredCodeCartridges()) {
				// Iterate through all cartridges (and not only selected one) to clean also source folder 
				// of a recently unselected cartridge
				folderToClean.add(GenerationCore.getJavaSourceFolder(project, cartridge.getCategory()));
			}
			
			monitor.subTask(GenerationUICore.getDefault().getString("workbench.generation.cleaning", genProjectName));
			
			// Clean all output folders of the gen project
			for (IFolder outputSourceFolder: folderToClean) {
			    if (outputSourceFolder.exists()) {
			        try {
			        	// DS-7347
			        	outputSourceFolder.delete(true, new SubProgressMonitor(monitor, 1));
					} catch (CoreException e) {
						IStatus status = new Status(IStatus.WARNING,
								GenerationUICore.PLUGIN_ID, 0,
								"Unable to clean \"" + genProjectName
										+ "\"", null);
						GenerationUICore.getDefault().getLog().log(status);
					}
					foldersToRefresh.add(outputSourceFolder);
			    }
			}
		    monitor.worked(1);		    
		    if(monitor.isCanceled()) 
		    	throw new OperationCanceledException();	
		}
		monitor.done();
		return foldersToRefresh;
	}

	public void run(IAction action) {
		if(!canContinue()) return;
		
		if(!saveResourcesAndContinue())return;

		Job job = new WorkspaceJob("Running Code Generation") {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				
				final Set<IProject> directlySelectedProjects = getDirectlySelectedProjects(selection);
				monitor.beginTask("Running Code Generation", 4);	
				
				// step 1 : Clean all gen projects (of directly selected models project)
				monitor.subTask("Cleanup");
				final int style = SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK;
				Set<IFolder> foldersToRefresh  = cleanSelectedProjectsBeforeGen(directlySelectedProjects, new SubProgressMonitor(monitor, 1, style));
	            final List<IStatus> nonOkStatuses = Lists.newArrayList(); 
	            
				// check if the project is OfsProject
				if (isSelectedProjectNative(selection)) {
					// step 2 : Run selection providers on the selected resources
					monitor.subTask("Resource Selection");
					Map<IProject, Collection<IOfsModelResource>> resourcesPerProjectMap = null;
					try {
						resourcesPerProjectMap = completeSelectedResources(new SubProgressMonitor(monitor, 1, style));
					} catch (CoreException ce) {
						String errorMsg = "Error in " + getClass().getName() +" while fetching resources from project ";
						errorMsg += ": "+ ce.getMessage();
						final IStatus errorStatus = new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 0,	errorMsg, ce);
						nonOkStatuses.add(errorStatus);	
					}	
					
					// step 3 : Execute the cartridges for all project
					monitor.subTask("Code Generation");
					if(resourcesPerProjectMap != null){
				    	for (final IProject project : resourcesPerProjectMap.keySet()) {
							try {
			    				Collection<IOfsModelResource> resources = resourcesPerProjectMap.get(project);
			    				IStatus status = new GenerationCommon().doGeneration(project, resources, foldersToRefresh, new SubProgressMonitor(monitor, 1, style));
			    				if (status == GenerationCommon.STATUS_USER_CANCELLATION) {
			    					break;
			    				}
			    				if (status != Status.OK_STATUS) {
			    					nonOkStatuses.add(status);
			    				}
							} catch (Exception e) {
									GenerationUICore.getDefault().logWarning("Error in Generation!!!", e);
							}
				    	}
					}
				} else {
					// step 2 : Run selection providers on the selected resources
					monitor.subTask("Resource Selection");
					Map<IProject, Collection<IResource>> resourcesPerProjectMap = getSelectedProjectResources(selection); 	
					
					// step 3 : Execute the cartridges for all project
					monitor.subTask("Code Generation");
		    		for (final IProject project : resourcesPerProjectMap.keySet()) {
						try {
		    				Collection<IResource> resources = resourcesPerProjectMap.get(project);
		    				IStatus status = new GenerationCommon().doGenerateNGC(project, resources, foldersToRefresh, new SubProgressMonitor(monitor, 1, style));
		    				if (status == GenerationCommon.STATUS_USER_CANCELLATION) {
		    					break;
		    				}
		    				if (status != Status.OK_STATUS) {
		    					nonOkStatuses.add(status);
		    				}
						} catch (Exception e) {
								GenerationUICore.getDefault().logWarning("Error in Generation!!!", e);
						}
		    		}
					
				}
	    		
	    		// step 4 : refresh the gen-folders
				if (FeatureSwitches.generationRefreshOutputFolders.get()) {
					monitor.subTask("Refresh");            
					refreshGenFolders(foldersToRefresh, new SubProgressMonitor(monitor, 1, style));
				}

				
	            if (nonOkStatuses.size() == 0) {
	            	return Status.OK_STATUS;
	            } else if (nonOkStatuses.size() == 1) {
	            	return nonOkStatuses.get(0);
	            } else {
	            	return new MultiStatus(GenerationUICore.PLUGIN_ID, Status.ERROR, nonOkStatuses.toArray(new IStatus[nonOkStatuses.size()]), 
	            			"The code generation produced " + nonOkStatuses.size() + " error(s). Click Details for more information.", null);
	            }
			}

		};
		job.setRule(null);
		job.setUser(true);
		job.setPriority(Job.LONG);
		job.schedule();
	}
	
	protected boolean canContinue() {
		//DS-3005
		boolean autoBuild = Platform.getPreferencesService().getBoolean(ResourcesPlugin.PI_RESOURCES,
				ResourcesPlugin.PREF_AUTO_BUILDING, true, null);
		if (!autoBuild) {
			String msg = "Automatic build is not enabled.  This might result in incorrect code-generation." +
					"\n Are you sure you want to proceed anyway with code generation?";
			boolean goAhead = MessageDialog.openConfirm(new Shell(), "Automatic build not enabled", msg);
			if (!goAhead) {
				return false;
			}
		}
		
		final Map<IProject, Collection<IResource>> resourcesPerProjectMap = getSelectedProjectResources(selection);

        // Check the project configuration
		for(IProject project : resourcesPerProjectMap.keySet()) {
			CodeGenInitializer initializer = new CodeGenInitializer();
			IStatus status = initializer.checkConfiguration(project);
			if(!status.isOK()) {
				StringBuilder sb = new StringBuilder();
				for(IStatus subStatus : status.getChildren()) {
					sb.append(subStatus.getMessage()).append("\n");
				}
				MessageDialog.openError(
						new Shell(),
						"Generation error due to configuration problems", 
						"Your current selection contains projects that have the following " +
						"configuration errors:\n\n" + sb.toString() + "\n" +
						"Please fix these problems and restart the generation.");
				return false;
			}
		}
		
		// Check for problem markers
		for(IProject project : resourcesPerProjectMap.keySet()) {
			try {
				String gen_marker_id = "com.odcgroup.workbench.generation.projectProblem";
				IMarker[] markers = project.findMarkers(gen_marker_id, true, IResource.DEPTH_ZERO);
				if(markers.length>0) {
					String message = "Problems in project configuration found.\n " 
						+ "Fix the project configuration and restart the generation.";
					MessageDialog.openError(new Shell(), "Project Configuration", message);
					return false;
				}
			} catch (CoreException e) {
				GenerationCore.getDefault().logError("Error reading markers for resource", e);
			}
		}
		
		boolean errorsExist = false;
		
		for(Collection<IResource> modelResources : resourcesPerProjectMap.values()) {
			for(IResource res : modelResources) {
				if(res!=null) {
					try {
						IMarker[] markers = res.findMarkers(null, true, IResource.DEPTH_INFINITE);
						for(IMarker marker : markers) {
							if(marker.getAttribute(IMarker.SEVERITY)!=null &&
									marker.getAttribute(IMarker.SEVERITY).equals(IMarker.SEVERITY_ERROR)) {
								errorsExist = true;
								break;
							}
						}
					} catch (CoreException e) {
						GenerationCore.getDefault().logError("Error reading markers for resource", e);
					}
				}
			}
		}
		
		if(!errorsExist) {
			return true;
		} else {		
			boolean ret = MessageDialog.openConfirm(
					new Shell(),
					"Generation warning due to existing errors", 
					"Your current selection contains resources that have errors, which may cause problems during " +
					"code generation or which may result in invalid generated files.\n\n" +
					"Are you sure that you want to continue anyhow?");
			return ret;
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// if the selection is a structured selection, set it as the current
		// selection
		// for the action
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	protected IStructuredSelection evaluateCurrentSelection() {
		IWorkbenchWindow window = GenerationUICore.getDefault().getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				return (IStructuredSelection) selection;
			}
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @throws CoreException 
	 * @see http://ria101.wordpress.com/2011/12/12/concurrenthashmap-avoid-a-common-misuse/
	 */
	private Map<IProject, Collection<IOfsModelResource>> getSelectedResourcesPerProject(IStructuredSelection selection) throws CoreException {
		if(isSelectionNullOrEmpty(selection)) return Collections.emptyMap();

		final Map<IProject, Collection<IOfsModelResource>> map = new ConcurrentHashMap<IProject, Collection<IOfsModelResource>>(selection.size(), 0.9f, 1);

		for(Object obj : selection.toList()) {
			if (obj instanceof IAdaptable) {
				IProject project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
				if (project != null) {
					List<IResource> resources = ModelsHelper.collectModelResources(obj);
					map.put(project, ModelsHelper.transform(project, resources));
				} else {
					IOfsElement element = (IOfsElement) ((IAdaptable) obj).getAdapter(IOfsElement.class);
					if(element!=null) {
						Collection<IOfsModelResource> resources = map.get(element.getOfsProject().getProject());
						if(resources==null) {
							resources = new HashSet<IOfsModelResource>();
							map.put(element.getOfsProject().getProject(), resources);
						}
						ModelResourceVisitor visitor = new ModelResourceVisitor(null, IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY);
						element.accept(visitor);
						resources.addAll(visitor.getMatchedResources());
					}
				}
			}
		}		
		return map;
	}
	


	/**
	 * @param selection
	 * @return
	 */
	private Map<IProject, Collection<IResource>> getSelectedProjectResources(IStructuredSelection selection) {
		if(isSelectionNullOrEmpty(selection)) return Collections.emptyMap();
		final Map<IProject, Collection<IResource>> map = new ConcurrentHashMap<IProject, Collection<IResource>>(selection.size(), 0.9f, 1);
		for(Object obj : selection.toList()) {
			if (obj instanceof IAdaptable) {
				IProject project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
				if (project != null) {
					Collection<IResource> resources = map.get(project);
					if(resources==null) {
						resources = new HashSet<IResource>();
						map.put(project, resources);
					}
					resources.addAll(getSelectedResources(project));					
				} else {
					IResource element = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
					if (element != null) {
						project = element.getProject();
						Collection<IResource> resources = map.get(project);
						if(resources==null) {
							resources = new HashSet<IResource>();
							map.put(project, resources);
						}
						resources.addAll(getSelectedResources(element));			
					}
				}
			}
		}		
		return map;
	}
	
	private Set<IResource> getSelectedResources(IResource element) {
		if (element != null) {
			CodeGenModelResourceVisitor visitor = new CodeGenModelResourceVisitor(OfsCore.getRegisteredModelNames());
			try {
				element.accept(visitor);
			} catch (CoreException e) {
				GenerationCore.getDefault().logError(e.getLocalizedMessage(), e);
			}
			return visitor.getModelResources();
		}
		return Collections.emptySet();
	}
	
	/*
	 * Return all projects directly selected
	 */
	private Set<IProject> getDirectlySelectedProjects(IStructuredSelection selection) {
		if(isSelectionNullOrEmpty(selection)) return Collections.emptySet();		
		final Set<IProject> selectedProjects = new CopyOnWriteArraySet<IProject>();		
		for(Object obj : selection.toList()) {
			if (obj instanceof IAdaptable) {
				IProject project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
				if (project != null) {
					selectedProjects.add(project);
				}
			}
		}
		return selectedProjects;
	}	
	
	private Map<IProject, Collection<IOfsModelResource>> completeSelectedResources(IProgressMonitor monitor) throws CoreException {
		SelectionProvider[] providers = GenerationCore.getRegisteredSelectionProviders();
		Map<IProject, Collection<IOfsModelResource>> map = getSelectedResourcesPerProject(selection); 
		monitor.beginTask("Executing Resource Selection", providers.length);
		for(SelectionProvider provider : providers) {
			monitor.subTask("for "+provider.getClass().getName());
			provider.completeModelResourceSelection(map);
			monitor.worked(1);
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
		}
		monitor.done();
		return map;
	}

	/**
	 * Builds an set of projects based upon the selection
	 * from the Odyssey view.
	 * 
	 * @return A Set of projects. Never returns null
	 */
	private Set<IProject> buildProjectList() {
		Set<IProject> projects = new CopyOnWriteArraySet<IProject>();
		for (Object obj : selection.toList()) {
			if (obj instanceof IAdaptable) {
				// Check to see if the selection is a project.
				IProject project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
				if (project != null) {
					projects.add(project);
				} else if (obj instanceof IOfsElement) {
					// Another file type have been selected get the
					IProject containerProj = ((IOfsElement) obj).getOfsProject().getProject();
					if (containerProj != null) {
						projects.add(containerProj);
					}
				}
			}
		}
		return projects;
	}


	protected boolean saveResourcesAndContinue() {
		boolean shouldSave = true;
		Set<IProject> pr = buildProjectList();
		if (pr.size() > 0) {
			shouldSave = showSaveDialogIfAnyResourcesAreDirty(pr);
			doSave();
		}
		return shouldSave;
	}
	
	/**
	 * Builds the list of editors that apply to this build that need to be saved
	 * 
	 * @param projects
	 *            the projects involved in this build, used to scope the
	 *            searching process
	 * @return the list of dirty editors for this launch to save, never null
	 */
	private IResource[] getScopedDirtyResources(Set<IProject> projects) {
		Set<IResource> dirtyres = new HashSet<IResource>();
		IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for(IWorkbenchWindow window : windows) {
			IWorkbenchPage[] pages = window.getPages();
			for(IWorkbenchPage page : pages) {
				IEditorPart[] eparts = page.getDirtyEditors();
				for(IEditorPart epart : eparts) { 
					IResource resource = (IResource) epart.getEditorInput().getAdapter(IResource.class);
					if (resource != null) {
						if(projects.contains(resource.getProject())){
							dirtyres.add(resource);
						}
					}
				}
			}
		}
		return (IResource[]) dirtyres.toArray(new IResource[dirtyres.size()]);
	}

	/**
	 * Show the save dialog with a list of editors to save (if any) The dialog
	 * is also not shown if the the preference for automatically saving dirty
	 * before launch is set to always
	 * 
	 * @param projects
	 *            The projects to consider for the save
	 *  @return 
	 *  		True if the user clicks "OK" or if there are no dirty resources. 
     *          False if it's "Cancel"
	 */
	protected boolean showSaveDialogIfAnyResourcesAreDirty(Set<IProject> projects) {
		IResource[] resources = getScopedDirtyResources(projects);
		if (resources.length > 0) {
			ListSelectionDialog lsd = new ListSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getShell(), new AdaptableList(resources), new WorkbenchContentProvider(),
					new WorkbenchLabelProvider(), "Select resources to save:");
			lsd.setInitialSelections(resources);
			lsd.setTitle("Save resources and generate code");
			if (lsd.open() == ListSelectionDialog.CANCEL) {
				return false;
			}
			Object[] objs = lsd.getResult();
			resourcesToSave = new IResource[objs.length];
			for (int i = 0; i < objs.length; i++) {
				resourcesToSave[i] = (IResource) objs[i];
			}
		}
		return true;
	}

	/**
	 * Saves the resources selected from the dialog.
	 */
	private void doSave() {
		if(resourcesToSave != null) {
			IDE.saveAllEditors(resourcesToSave, false);
			resourcesToSave = null;
		}
	}
	
	protected boolean isSelectionNullOrEmpty(IStructuredSelection selection) {
		return (selection == null || selection.isEmpty());
	}
	
	/**
	 * @param selection
	 * @return
	 */
	private boolean isSelectedProjectNative(IStructuredSelection selection) {
		if(selection == null || selection.isEmpty()) return false;
		for(Object obj : selection.toList()) {
			if (obj instanceof IAdaptable) {
				IProject project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
				if(project!=null) {
					if(OfsCore.isOfsProject(project)) {
						return true;
					}
				} else {
					IOfsElement element = (IOfsElement) ((IAdaptable) obj).getAdapter(IOfsElement.class);
					if(element!=null) {
						return true;
					}
				}
			}
		}		
		return false;
	}
	
	/**
	 *
	 * @author phanikumark
	 *
	 */
	class CodeGenModelResourceVisitor implements IResourceVisitor {
		
		private String[] extns;
		private Set<IResource> resources = new CopyOnWriteArraySet<IResource>();
		
		public CodeGenModelResourceVisitor(String[] acceptExtensions) {
			this.extns = acceptExtensions;
		}
		
		public Set<IResource> getModelResources() {
			return resources;
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
	        if(resource instanceof IProject || resource instanceof IFolder) {
	        	// continue traversing into projects and folders
	        	return true;
	        }	        
	        if(resource instanceof IFile) {
	        	IFile file = (IFile) resource;
	        	if(isAcceptedExtension(file.getFileExtension())) {
	        		resources.add(file);
	        	}
	        }
			return false;
		}

		private boolean isAcceptedExtension(String ext) {
			if (ext != null) {
			    for (int i = 0; i < extns.length; i++) {
			        if (ext.equals(extns[i])) {
			            return true;
			        }
			    }
			}
			return false;
		}
		
	}
}

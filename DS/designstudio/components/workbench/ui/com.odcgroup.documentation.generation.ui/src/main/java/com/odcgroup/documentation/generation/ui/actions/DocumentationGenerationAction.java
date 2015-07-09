package com.odcgroup.documentation.generation.ui.actions;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.documentation.generation.DocGenCartridge;
import com.odcgroup.documentation.generation.DocGenerator;
import com.odcgroup.documentation.generation.DocumentationCore;
import com.odcgroup.documentation.generation.ui.DocumentationUICore;
import com.odcgroup.documentation.generation.ui.properties.PropertyHelper;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.core.resources.OfsModelPackage;
import com.odcgroup.workbench.core.resources.OfsModelResource;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.PreferenceConstants;

public class DocumentationGenerationAction extends Action implements
		IWorkbenchWindowActionDelegate, IViewActionDelegate {

	private static final String DOCUMENTATION_FOLDER = "Documentation";
	private IStructuredSelection selection;

	public DocumentationGenerationAction() {
		setText("Generate Documentation");
		setImageDescriptor(DocumentationUICore.imageDescriptorFromPlugin(
				DocumentationUICore.PLUGIN_ID, "/icons/pdf.gif"));
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void run() {
		selection = evaluateCurrentSelection();
		run(this);
	}

	public void run(IAction action) {
		if (!canContinue())
			return;
		final Map<IProject, Collection<IOfsModelResource>> resourcesPerProjectMap = getSelectedResourcesPerProject(selection);
		IProgressMonitor monitor = Job.getJobManager().createProgressGroup();
		Collection<Job> jobs = new HashSet<Job>();
		monitor.beginTask("Generating documentation...", DocumentationCore.getRegisteredDocGenCartridges().length
				* jobs.size());
		StringBuffer text = new StringBuffer();
		for (final Entry<IProject, Collection<IOfsModelResource>> entry : resourcesPerProjectMap.entrySet()) {

			ProjectPreferences preferences = new ProjectPreferences(entry.getKey(), DocumentationUICore.PLUGIN_ID);
			String pref = preferences
					.get(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, "C:\\DesignStudio\\");
			text.append(pref.endsWith("\\") ? pref : (pref + "\\"));
			text.append(entry.getKey().getName() + "\\");
			Object firstElement = new StructuredSelection(selection).getFirstElement();
			if (firstElement instanceof TreeSelection) {

				Object firstElement2 = ((TreeSelection) firstElement).getFirstElement();
				if (firstElement2 instanceof OfsModelResource) {

					String path = ((OfsModelResource) firstElement2).getResource().getProjectRelativePath()
							.toString();
					processFolderPath(text, path);
					break;
				} else if (firstElement2 instanceof OfsModelPackage) {
					String path = ((OfsModelPackage) firstElement2).getResource().getProjectRelativePath()
							.toString();
					processFolderPath(text, path);
					break;
				}
			}

		}
		File root = new File(text.toString());
		if (root.exists()) {
			// collects files in directory
			File[] allfiles = root.listFiles();
			for (int i = 0; i < allfiles.length; i++) {
				//recursively delete files
				deleteDirectory(allfiles[i]);
			}
			try {
				// Delete root folder
				root.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (final Entry<IProject, Collection<IOfsModelResource>> entry : resourcesPerProjectMap.entrySet()) {
			// Run the actual operation for generating the code
			jobs.add(runCartridges(entry.getKey(), entry.getValue(), monitor));
		}
	}

	/**
	 * @param text build the path 
	 * @param path extract path information from respective container
	 */
	private void processFolderPath(StringBuffer text, String path) {
		String pathWithoutExtn = null;
		if(path.contains(".")){
			pathWithoutExtn = path.substring(0, path.indexOf("."));
		}else{
			pathWithoutExtn = path;
		}
		String[] chopPathWithoutExtn = pathWithoutExtn.split("/");
		text.append("GeneratedDocumentation" + "\\" );
		
		pathWithoutExtn = getContainerPath(pathWithoutExtn, chopPathWithoutExtn);
		text.append(pathWithoutExtn);
	}

	/**
	 * @param pathWithoutExtn source path
	 * @param chopPathWithoutExtn filtered path 
	 * @return appended Container name
	 */
	private String getContainerPath(String pathWithoutExtn,
			String[] chopPathWithoutExtn) {
		if(chopPathWithoutExtn[0].equals("domain"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Domains") ;
		}
		if(chopPathWithoutExtn[0].equals("fragment"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Fragments") ;
		}
		if(chopPathWithoutExtn[0].equals("menu"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Menus") ;
		}
		if(chopPathWithoutExtn[0].equals("module"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Modules") ;
		}
		if(chopPathWithoutExtn[0].equals("pageflow"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Pageflows") ;
		}
		if(chopPathWithoutExtn[0].equals("page"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Pages") ;
		}
		if(chopPathWithoutExtn[0].equals("workflow"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Workflows") ;
		}
		if(chopPathWithoutExtn[0].equals("translation"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Translations") ;
		}
		if(chopPathWithoutExtn[0].equals("rule"))
		{
			pathWithoutExtn = pathWithoutExtn.replaceFirst(chopPathWithoutExtn[0], "Rules") ;
		}
		return pathWithoutExtn;
	}

	private boolean canContinue() {
		Map<IProject, Collection<IOfsModelResource>> resourcesPerProjectMap = getSelectedResourcesPerProject(selection);

		boolean errorsExist = false;
		
		for(Collection<IOfsModelResource> modelResources : resourcesPerProjectMap.values()) {
			for(IOfsModelResource modelResource : modelResources) {
				IResource res = modelResource.getResource();
				if(res!=null) {
					try {
						IMarker[] markers = res.findMarkers(null, true, IResource.DEPTH_INFINITE);
						for(IMarker marker : markers) {
							Object attribute = marker.getAttribute(IMarker.SEVERITY);
							if(attribute!=null && attribute.equals(IMarker.SEVERITY_ERROR)) {
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

		// DS-2873: Check for a white space in the workspace location
		StringBuilder sb = new StringBuilder();
		for(IProject project : resourcesPerProjectMap.keySet()) {
			if(project.getLocation().toFile().toString().contains(" ")) {
				sb.append("Illegal path: " + project.getLocation().toFile().toString() + "\n");
			}
		}
		if(sb.length()!=0) {
			Shell shell = new Shell();
			MessageDialog.openError(
					shell,
					"Cannot start documentation generation", 
					"The PDF documentation generation requires that Design Studio model project paths contain no spaces.\n\n" +
					sb.toString());
			shell.dispose();
			return false;
		}

		if(!errorsExist) {
			return true;
		} else {	
			Shell shell = new Shell();
			boolean ret = MessageDialog.openConfirm(
					shell,
					"Generation warning due to existing errors", 
					"Your current selection contains resources that have errors, which may cause problems during " +
					"documentation generation.\n\n" +
					"Are you sure that you want to continue anyhow?");
			shell.dispose();
			return ret;
		}		
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// if the selection is a structured selection, set it as the current
		// selection for the action
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	private Job runCartridges(final IProject project, final Collection<IOfsModelResource> modelResources, IProgressMonitor monitor) {

		final DocGenCartridge[] cartridges = PropertyHelper.getSelectedDocGenCartridges(project);
		final IFolder outputFolder = project.getFolder(DOCUMENTATION_FOLDER);
		try {
			OfsCore.createFolder(outputFolder);
		} catch (CoreException e) {
			DocumentationUICore.getDefault().logError("Cannot create documentation output directory!", e);
			return null;
		}				
		try {
			outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			DocumentationUICore.getDefault().logError("Cannot refresh the output directory!", e);
			return null;
		}

		WorkspaceJob job = new WorkspaceJob("Generating documentation for project " + project.getName()) {			
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				int total = cartridges.length;
				monitor.beginTask("Executing documentation generation of project \'" + project.getName() + "\'", total * 10);
				DocGenerator lastDocGen = null;
				for(DocGenCartridge docGenCartridge : cartridges) {					
					monitor.subTask("Executing documentation generation cartridge '" + docGenCartridge.getName() + "'");
					if (docGenCartridge.isTerminal()) {
						lastDocGen = docGenCartridge.getDocGen();
						continue;
					}
					else{
						DocGenerator docGen = docGenCartridge.getDocGen();
						try {
							if (docGen.isValidForProject(project)) { // DS-1614
								SubProgressMonitor subMonitor = new SubProgressMonitor(
										monitor, 100);
								docGen.run(project, modelResources,
										outputFolder, subMonitor);
							}
						} catch (InterruptedException e) {
							return Status.CANCEL_STATUS;
						}
						if (monitor.isCanceled())
							return Status.CANCEL_STATUS;
					}
					monitor.worked(1);
				}
				if(lastDocGen!=null){
					try {
						if (lastDocGen.isValidForProject(project)) { // DS-1614
							SubProgressMonitor subMonitor = new SubProgressMonitor(
									monitor, 100);
							lastDocGen.run(project, modelResources,
									outputFolder, subMonitor);
						}
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					monitor.worked(1);
					
					/*//docgen for whole project (xls and html) is currently put on hold
					 * //generate excel file for whole project
					StringBuffer text = new StringBuffer();	
					ProjectPreferences preferences = new ProjectPreferences(project, DocumentationUICore.PLUGIN_ID);
					String pref = preferences.get(PreferenceConstants.PREF_DOCUMENTATION_ACTIVITYNAME, "C:\\DesignStudio\\");
					text.append(pref.endsWith("\\") ? pref: (pref + "\\") );
					text.append(project.getName() + "\\GeneratedDocumentation\\ListOfModels.xls" );
	
					XlsDocumentGenerationForProject xls = new XlsDocumentGenerationForProject(modelResources);
					try {
						WritableWorkbook workbook = xls.createWorkbook(new FileOutputStream(new File(text.toString())));
						workbook.write();
						workbook.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try{
						//read content from folder
						List<String> fileList = new ArrayList<String>();
						String pathname = pref + project.getName() + "\\GeneratedDocumentation\\";
						File folder = new File(pathname);
						if(folder.exists()) {	
							String[] a = folder.list();
							for(int i=0; i< a.length ; i++){
								if (!a[i].equalsIgnoreCase("index.html")) {
							
									fileList.add(a[i]);
								}
							}
						}
						
						//print to file
						String result = (indexFile(pathname ,fileList)).toString();
						BufferedWriter out = new BufferedWriter(new FileWriter(pathname + "index.html"));
						out.write(result);
						out.close();
						fileList.clear();
						} catch (IOException e) {
							e.printStackTrace();
						}*/
					
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(outputFolder.getParent());
//		job.setProgressGroup(monitor, cartridges.length);
		job.setUser(true);
		job.schedule();
		
		return job;
	}

	  public CharSequence indexFile(final String path, final List<String> fileList) {
		    StringBuilder _builder = new StringBuilder();
		    CharSequence _htmlOpening = this.htmlOpening();
		    _builder.append(_htmlOpening);
		    _builder.append("\n");
		    _builder.append("<ul>");
		    _builder.append("\n");
		    {
		      for(final String pageFile : fileList) {
		        _builder.append("\t");
		        _builder.append("<li>");
		        _builder.append("\n");
		        _builder.append("\t");
		        _builder.append("    ");
		        _builder.append("<A HREF=\"" + path + "\\" + pageFile +"\">");
		        _builder.append(path+  "\\");
		        _builder.append("</A>");
		        _builder.append("\n");
		        _builder.append("\t    ");
		        _builder.append("<br></br>");
		        _builder.append("\n");
		        _builder.append("\t");
		        _builder.append("</li>");
		        _builder.append("\n");
		      }
		    }
		    _builder.append("</ul>");
		    _builder.append("\n");
		    CharSequence _htmlClosing = this.htmlClosing();
		    _builder.append(_htmlClosing);
		    _builder.append("\n");
		    return _builder;
		  }
	  
	  public CharSequence htmlOpening() {
		  StringBuilder _builder = new StringBuilder();
		    _builder.append("<!DOCTYPE html>");
		    _builder.append("\n");
		    _builder.append("<html lang=\"en\">\t\t");
		    _builder.append("\n");
		    _builder.append("<body>");
		    _builder.append("\n");
		    return _builder;
		  }
		  
		  public CharSequence htmlClosing() {
			  StringBuilder _builder = new StringBuilder();
		    _builder.append("</body>");
		    _builder.append("\n");
		    _builder.append("</html>");
		    _builder.append("\n");
		    return _builder;
		  }
		  
	private IStructuredSelection evaluateCurrentSelection() {
		IWorkbenchWindow window = DocumentationUICore.getDefault().getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				return (IStructuredSelection) selection;
			}
		}
		return StructuredSelection.EMPTY;
	}

	private Map<IProject, Collection<IOfsModelResource>> getSelectedResourcesPerProject(IStructuredSelection selection) {
		Map<IProject, Collection<IOfsModelResource>> map = new HashMap<IProject, Collection<IOfsModelResource>>();

		if(selection==null) return map;
		
		for(Object obj : selection.toList()) {
			if (obj instanceof IAdaptable) {
				IProject project = (IProject) ((IAdaptable) obj).getAdapter(IProject.class);
				if(project!=null) {
					if(OfsCore.isOfsProject(project)) {
						Collection<IOfsModelResource> resources = map.get(project);
						if(resources==null) {
							resources = new HashSet<IOfsModelResource>();
							map.put(project, resources);
						}
						IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
						ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, OfsCore.getRegisteredModelNames());
						resources.addAll(lookup.getAllOfsModelResources(IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY));
					}
				} else {
					IResource resource = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
					if(resource!=null) {
						project = resource.getProject();
						Collection<IOfsModelResource> resources = map.get(project);
						if(resources==null) {
							resources = new HashSet<IOfsModelResource>();
							map.put(project, resources);
						}
						Set<IResource> selectedResources = getSelectedResources(resource);
						IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
						Set<IOfsModelResource> ofsResources = new HashSet<IOfsModelResource>();
						for (IResource resourceObj : selectedResources) {
							URI uri = URI.createPlatformResourceURI(resourceObj.getFullPath().toString(),true);
							IOfsModelResource ofsres = new OfsModelResource(ofsProject,uri,(IStorage)resourceObj,IOfsProject.SCOPE_ALL);
							ofsResources.add(ofsres);
						}
						resources.addAll(ofsResources);
					}
				}
			}
		}
		return map;
	}
	
	private Set<IResource> getSelectedResources(IResource element) {
		if (element != null) {
			DocModelResourceVisitor visitor = new DocModelResourceVisitor(OfsCore.getRegisteredModelNames());
			try {
				element.accept(visitor);
			} catch (CoreException e) {
				GenerationCore.getDefault().logError(e.getLocalizedMessage(), e);
			}
			return visitor.getModelResources();
		}
		return Collections.emptySet();
	}
	
    /**
     * @param directory files and sub-directory files to be deleted under this directory.
     * @return true on pass and false on failure 
     */
    public static void deleteDirectory(File directory) {
    	
    	 // Checks if file is a directory
        if (directory.isDirectory()) {
                //Gathers files in directory
                File[] b = directory.listFiles();
                for (int i = 0; i < b.length; i++) {
                    //Recursively deletes all files and sub-directories
                	deleteDirectory(b[i]);
                }
                // Deletes original sub-directory file
                directory.delete();
        } else {
                directory.delete();
        }
    	/*if (directory!=null && directory.isDirectory()) {
            String[] children = directory.list();
            for (int i=0; i<children.length; i++) {
                boolean pass = deleteDirectory(new File(directory, children[i]));
                if (!pass) {
                    return false;
                }
            }
        }
        if(directory !=null){
        	// The directory is now empty so delete it
            return directory.delete();
        }
        return false;*/
        
    }
    
    
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
	 */
	@Override
	public void init(IViewPart viewPart) {
		// TODO Auto-generated method stub
		
	}
	
	class DocModelResourceVisitor implements IResourceVisitor {
		
		private String[] extns;
		private Set<IResource> resources = new CopyOnWriteArraySet<IResource>();
		
		public DocModelResourceVisitor(String[] acceptExtensions) {
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

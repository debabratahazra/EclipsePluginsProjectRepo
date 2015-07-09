package com.odcgroup.translation.generation.ui.internal.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.translation.ui.navigator.TranslationNode;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelResourceLookup;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeCartridge;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;
import com.odcgroup.workbench.generation.properties.PropertyHelper;
import com.odcgroup.workbench.generation.ui.GenerationUICore;
import com.odcgroup.workbench.generation.ui.action.CodeGenerationAction;

public class TranslationGenerationAction extends CodeGenerationAction implements
		IWorkbenchWindowActionDelegate {

	public TranslationGenerationAction() {
		super();
	}

	@Override
	public void run(IAction action) {
		if(!canContinue()) return;

		Job job = new WorkspaceJob("Running Code Generation") {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				IOfsProject ofsProject = getProjectFromSelection();
				IProject project = ofsProject.getProject();

				Set<IOfsModelResource> allProjectResources = getAllProjectResources(ofsProject);

				monitor.beginTask("Generating translations...", 2);

				final CodeCartridge[] cartridges = PropertyHelper.getSelectedCodeCartridges(project);
				List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
				// iterate over all selected cartridges
				for (final CodeCartridge cartridge : cartridges) {
					if(!isTranslationCartridge(cartridge)) continue;
					
		            // get the output folder
		            final IFolder outputSourceFolder = GenerationCore.getJavaSourceFolder(project, cartridge.getCategory());

					monitor.subTask(GenerationUICore.getDefault().
							getFormattedString("workbench.generation.code.execCartridge", cartridge.getName()));
		            if (cartridge.getCodeGen() != null) {
						cartridge.getCodeGen().run(monitor, project, allProjectResources, outputSourceFolder, nonOkStatuses);
					} else {
						IStatus status = new Status(IStatus.WARNING,
								GenerationUICore.PLUGIN_ID, 0,
								"No code generator has been defined for cartridge \""
										+ cartridge.getName() + "\"", null);
						GenerationUICore.getDefault().getLog().log(status);
					}
		            if(monitor.isCanceled()) return Status.CANCEL_STATUS;
		            monitor.worked(1);
				}
	            try {
	            	ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, monitor);
				} catch (CoreException e) {
					GenerationUICore.getDefault().logWarning(
							"Error refreshing workspace", e);
				}
				return Status.OK_STATUS;
			}

		};
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
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

		IOfsProject ofsProject = getProjectFromSelection();
		CodeGenInitializer initializer = new CodeGenInitializer();
		IStatus status = initializer.checkConfiguration(ofsProject.getProject());
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
		
		boolean errorsExist = false;
		
		for(IOfsModelResource modelResource : getAllProjectResources(ofsProject)) {
			IResource res = modelResource.getResource();
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

	private boolean isTranslationCartridge(CodeCartridge cartridge) {
		if(cartridge.getId().equals(TranslationGenerationCore.NLS_CARTRIDGE_ID)) return true;
		return false;
	}

	private Set<IOfsModelResource> getAllProjectResources(
			IOfsProject ofsProject) {
		ModelResourceLookup lookup = new ModelResourceLookup(ofsProject, OfsCore.getRegisteredModelNames());
		return lookup.getAllOfsModelResources(IOfsProject.SCOPE_ALL - IOfsProject.SCOPE_DEPENDENCY);
	}

	private IOfsProject getProjectFromSelection() {
		if(selection==null) return null;
		
		for(Object obj : selection.toList()) {
			if (obj instanceof TranslationNode) {
				TranslationNode node = (TranslationNode) obj;	
				return node.getOfsProject();
			}
		}
		return null;
	}
}

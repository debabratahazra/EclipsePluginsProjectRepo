package com.odcgroup.workbench.ui.helper;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.odcgroup.workbench.core.IMetaModelVersioned;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.ui.ModelEditorInput;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * Utility to open ofs editors in custo/standard modes
 * 
 * @author pkk
 *
 */
public class OfsEditorUtil {
	
	public static final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

	/**
	 * @param ofsResource
	 */
	public static IEditorPart openEditor(IOfsModelResource ofsResource) {
		IEditorPart editorPart = null;
		IEditorInput input = null;
		URI ofsResourceUri = ofsResource.getURI();
		try {
			//begin DS-2029
			input = getEditorInput(ofsResource);
			IEditorReference[] editors = window.getActivePage().getEditorReferences();
			for (IEditorReference editorReference : editors) {
				IEditorInput eInput = editorReference.getEditorInput();
				if (eInput instanceof IStorageEditorInput) {
					IStorageEditorInput mInput = (IStorageEditorInput) eInput;
					if (mInput.getStorage() instanceof IOfsModelResource) {
						IOfsModelResource mResource = (IOfsModelResource) mInput.getStorage();
						if(mResource.getURI().equals(ofsResourceUri)) {
							input = mInput;
						}
					}
				}
			}
			//end DS-2029
			editorPart = window.getActivePage().openEditor(input, getEditorId(ofsResourceUri), true);
			
		} catch (PartInitException e) {
			OfsCore.getDefault().logError("Unable to open editor for "+ofsResourceUri.toString(), e);	
		} catch (Exception e){
			OfsCore.getDefault().logError("Unable to open editor for "+ofsResourceUri.toString(), e);					
		}
		return editorPart;
	}
	
	public static IEditorPart openEditor(URI uri) {
		IEditorPart editorPart = null;
		IEditorInput input = null;
		Path path = new Path(uri.toPlatformString(true));
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		if (file != null) {
			input = new FileEditorInput(file);
			try {
				IEditorReference[] editors = window.getActivePage().getEditorReferences();
				for (IEditorReference editorReference : editors) {
					IEditorInput eInput = editorReference.getEditorInput();
					if (eInput instanceof IStorageEditorInput) {
						IStorageEditorInput mInput = (IStorageEditorInput) eInput;
						if (mInput.getStorage() instanceof IOfsModelResource) {
							IOfsModelResource mResource = (IOfsModelResource) mInput.getStorage();
							if(mResource.getURI().equals(uri)) {
								input = mInput;
							}
						}
					}
				}
				editorPart = window.getActivePage().openEditor(input, getEditorId(uri), true);
			} catch (PartInitException e) {
				OfsCore.getDefault().logError("Unable to open editor for "+uri.toString(), e);	
			} catch (Exception e){
				OfsCore.getDefault().logError("Unable to open editor for "+uri.toString(), e);					
			}
		}
		return editorPart;			
	}
	
	public static IEditorPart openEditor(Resource resource) {
		return openEditor(resource.getURI());
	}
	
	/**
	 * DS-1593
	 * 
	 * @param ofsResource
	 * @return
	 */
	public static IEditorInput getEditorInput(IOfsModelResource ofsResource) {
		IEditorInput input = null;
		//DS-1510
		if (ofsResource.getResource() == null){
			input = new ModelEditorInput(ofsResource);
		} else {
			IFile file = OfsResourceHelper.getFile(ofsResource);				
			input = new FileEditorInput(file);
		}
		return input;			
	}

	/**
	 * @param ofsProject
	 * @param resourceUri
	 */
	public static IEditorPart openEditor(IOfsProject ofsProject, URI resourceUri) {
		if (resourceUri == null) {
			return null;
		}
		IEditorPart editorPart = null;
		IOfsModelResource mResource = null;
		IEditorInput input = null;
		try {
			mResource = ofsProject.getOfsModelResource(resourceUri);	
			editorPart = openEditor(mResource);
		} catch (ModelNotFoundException e) {
			OfsCore.getDefault().logError("Model not found for URI ["+resourceUri.toString()+"]", e);
		}
		return editorPart;
	}
	

	/**
	 * @param uri
	 * @return
	 */
	private static String getEditorId(URI uri) {
		return PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(uri.lastSegment()).getId();
	}
	
	public static  boolean isCorrectVersion(IOfsModelResource modelResource) {
		IMetaModelVersioned versionedModelResource = (IMetaModelVersioned) Platform.getAdapterManager().loadAdapter(modelResource, IMetaModelVersioned.class.getName());
		if(versionedModelResource!=null) {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			try {
				if(versionedModelResource.needsMigration()) {
					String version = versionedModelResource.getMetaModelVersion();
					if (StringUtils.isEmpty(version)) {
						version = "undefined";
					} else {
						version = "["+version+"]";
					}
					IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, 
							"Metamodel version of resource is " + version);
					ErrorDialog.openError(shell, "Error opening model resource", 
							"The model resource '" + modelResource.getName() + 
							"' has an outdated metamodel version and needs to be migrated.\n\n" +
							"Select the project head in the Navigator view and fix the following error from the problems view:\n" +
							"\"Design Studio project configuration: Project contains models that need to be migrated.\"", status);
					return false;
				}
			} catch (CoreException e) {
				IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, e.getLocalizedMessage(), e); 
				ErrorDialog.openError(shell, "Error opening model resource", 
						"The model resource '" + modelResource.getName() + 
						"' cannot be accessed.", status);
				return false;
			} catch (InvalidMetamodelVersionException e) {
				IStatus status = new Status(IStatus.ERROR, OfsUICore.PLUGIN_ID, e.getLocalizedMessage(), e); 
				ErrorDialog.openError(shell, "Error opening model resource", 
						"The model resource '" + modelResource.getName() + 
						"' has a too recent metamodel version (" + e.getMetamodelVersion() + 
						") and cannot be used with this Design Studio version.", status);
				return false;
			}
		}
		return true;
	}

}

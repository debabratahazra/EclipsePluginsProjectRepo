package com.odcgroup.workbench.ui;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.svn.core.connector.SVNConnectorException;
import org.eclipse.team.svn.ui.repository.IRepositoryEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IStorageEditorInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.CoreExceptionHelper;
import com.odcgroup.workbench.core.repository.ModelResourceSet;

/**
 *
 * @author pkk
 *
 */
public class OfsUIResourceHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OfsUIResourceHelper.class);
	
	public static IOfsProject getOfsProject(IEditorInput editorInput) {
		IOfsProject ofsProject = null;
		
		if (editorInput instanceof IFileEditorInput) {
			IFileEditorInput fei = (IFileEditorInput)editorInput;
			IProject project = fei.getFile().getProject();
			if (project != null) {
				ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
			}
		} else if (editorInput instanceof ModelEditorInput) {
			ModelEditorInput mei = (ModelEditorInput)editorInput;
			try {
				ofsProject = ((IOfsModelResource)mei.getStorage()).getOfsProject();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		} else if (editorInput instanceof IStorageEditorInput) {
			IStorageEditorInput pInput = (IStorageEditorInput) editorInput;
        	try {
				IStorage storage = pInput.getStorage();
				IPath path = storage.getFullPath();
				ofsProject = getOfsProject(path.toString());
			} catch (CoreException e) {
				// 
			}
		}
		
		return ofsProject;
	}
	
	public static IOfsProject getOfsProject(String remoteUrl) {
		try {
			URI uri = URI.createURI(remoteUrl);
			String extn = uri.fileExtension();
			if (extn.equals("mml")) {
				extn = "domain";
			}
			String trimmedUri = uri.trimFileExtension().toString();
			trimmedUri = trimmedUri.substring(0, trimmedUri.indexOf(extn)-1);
			String projectName = trimmedUri.substring(trimmedUri.lastIndexOf("/"));
	
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (project != null) { 
				return OfsCore.getOfsProjectManager().getOfsProject(project);
			}
		} catch (Exception e) {
			// Shit happens in real life (note e.g. http://rd.oams.com/browse/DS-6571)
			// Just logging it here in case it's needed for any analysis, and then return null is fine
			LOGGER.info("Exception in com.odcgroup.workbench.ui.OfsUIResourceHelper.getOfsProject({}), returning null; this is probably safe to ignore (DS-6571)", remoteUrl, e);
		}
		return null;
	}
	
	/**
     * @param storageEditorInput editor input from which to retrieve the model
     * @param pluginId calling plugin ID
     */
	public static EObject getModelFromEditorInput(IStorageEditorInput storageEditorInput, String pluginId) throws CoreException {
		IOfsProject ofsProject = OfsUIResourceHelper.getOfsProject(storageEditorInput);
		IStorage storage = storageEditorInput.getStorage();
		IPath path = storage.getFullPath();
		URI uri = URI.createURI(path.toString());
		uri = appendRevision(storageEditorInput, uri, pluginId);
		ModelResourceSet rs = ofsProject.getModelResourceSet();
		prepareResourceSet(rs, uri, pluginId);
		IOfsModelResource resource = rs.createOfsModelResource(uri, storage, IOfsProject.SCOPE_PROJECT);
		EList<EObject> modelObjects = null;
		try {
			modelObjects = resource.getEMFModel();
		}
		catch(IOException exception) {
			CoreExceptionHelper.throwCoreException(IStatus.ERROR, pluginId, "Could not load resource: "+uri, exception);
		} catch (InvalidMetamodelVersionException exception) {
			CoreExceptionHelper.throwCoreException(IStatus.ERROR, pluginId, "Outdated or invalid metamodel version "+exception.getMetamodelVersion()+"(current version is "+OfsCore.getVersionNumber()+") for resource: "+uri, exception);
		}
		return modelObjects.get(0);
	}

	private static URI appendRevision(IStorageEditorInput storageEditorInput, URI uri, String pluginId) throws CoreException {
		String revision = getRevision(storageEditorInput);
		if(revision == null) {
			CoreExceptionHelper.throwCoreException(IStatus.ERROR, pluginId, "Could not retrieve revision information for: "+uri);
		}
		return uri.appendQuery("revision="+revision);
	}

    private static void prepareResourceSet(ResourceSet resourceSet, URI modelURI, String pluginName) throws CoreException {
		String fileExtension = modelURI.fileExtension();
		if (fileExtension == null || fileExtension.length() == 0) {
			fileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;
		}

		final Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
		final Object resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);
		if(resourceFactory == null) {
			CoreExceptionHelper.throwCoreException(IStatus.ERROR, pluginName, "No resource factory found for file extension "+fileExtension);
		}
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension, resourceFactory);
	}
    
    private static String getRevision(IStorageEditorInput storageEditorInput) {
    	if(storageEditorInput instanceof IRepositoryEditorInput) {
    		return getRevision((IRepositoryEditorInput)storageEditorInput);
    	}
    	else if(storageEditorInput instanceof ModelEditorInput) {
    		return getRevision((ModelEditorInput)storageEditorInput);
    	}
    	else {
    		IFileRevision fileRevision = (IFileRevision)storageEditorInput.getAdapter(IFileRevision.class);
    		if(fileRevision != null) {
    			return getRevision(fileRevision);
    		}
    	}
    	return null;
    }
    
    private static String getRevision(IRepositoryEditorInput repositoryEditorInput) {
		try {
			return ""+repositoryEditorInput.getRepositoryResource().getRevision();
		} catch (SVNConnectorException e) {
			return null;
		}
    }
    
    private static String getRevision(IFileRevision fileRevision) {
    	return ""+fileRevision.getTimestamp();
    }
    
    private static String getRevision(ModelEditorInput modelEditorInput) {
    	return "jar";
    }
    
}

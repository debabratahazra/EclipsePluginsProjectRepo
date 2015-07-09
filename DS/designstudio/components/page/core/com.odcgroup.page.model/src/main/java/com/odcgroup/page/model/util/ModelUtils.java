package com.odcgroup.page.model.util;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.odcgroup.page.common.PageConstants;
import com.odcgroup.page.exception.PageException;
import com.odcgroup.page.log.Logger;
import com.odcgroup.page.model.Model;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

/**
 * Utility methods for models.
 * 
 * @author Gary Hayes
 */
public class ModelUtils {
	
	/**
	 * Opens the designated resource and extracts the Model. Returns null if the model cannot be extracted.
	 * 
	 * @param project the project to consider
	 * @param resourcePath The resource path containing the model
	 * @return Model The Model
	 */
	public static Model safeOpenModelFile(IProject project, String resourcePath) {
		
		Model result = null;
		
		String message = "Unable to load the resource: [" +  resourcePath + "]";
		
		try {
			URI uri = ModelURIConverter.createModelURI(resourcePath);
			IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(project);
			if (ofsProject != null) {
				IOfsModelResource modelResource = ofsProject.getOfsModelResource(uri);
				if (modelResource != null) {
					result = (Model) modelResource.getEMFModel().get(0);
				} else {
					String msg = message + " Please check if it needs migration or if it has been deleted.";
					Logger.error(msg);
				}
			} else {
				String projectName = project.getName();
				String msg = "Unable to find the OFS project ["+projectName+"] Please, check your workspace.";
				Logger.error(msg);
			}
		} catch (Exception ex) {
			// We catch all Exception's here because, if by accident some other file is called *.module or *.pagefrag
			// EMF will not be able to read it. This would stop the compilation of ALL the other
			// resources.
			Logger.error(message, ex);
		}

		return result;
	}
	
	/**
	 * Saves the model to the file.
	 * 
	 * @param model
	 *            The model to save
	 * @param file
	 *            The file to save the model to
	 * @throws PageException
	 *             Thrown if an error occurs
	 */
	public static void saveModel(Model model, IFile file) {

		// DS-1551
    	URI uri = ModelURIConverter.createModelURI((IResource)file);
    	
    	IOfsProject ofsProject = OfsCore.getOfsProjectManager().getOfsProject(file.getProject());
		ofsProject.getModelResourceSet().createOfsModelResource(uri, file, IOfsProject.SCOPE_PROJECT);
		Resource resource = ofsProject.getModelResourceSet().getResource(uri, false);
		resource.getContents().add(model);
		try {
			resource.save(null);
		} catch (IOException ioe) {
			Logger.error("Unable to create the model", ioe);
			throw new PageException("Unable to create the model", ioe);
		}
	}	
	
	public static boolean isFragment(IFile file) {
		return file != null && PageConstants.FRAGMENT_FILE_EXTENSION.equals(file.getFileExtension());
	}
	
	// getExpressionContext(Model model) which used to be here is in ConditionUtils now

}

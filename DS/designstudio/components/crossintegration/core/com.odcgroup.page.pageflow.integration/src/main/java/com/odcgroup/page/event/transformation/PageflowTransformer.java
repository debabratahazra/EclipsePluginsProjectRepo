package com.odcgroup.page.event.transformation;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.odcgroup.page.transformmodel.crossmodel.IOfsModelTransformer;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.generation.CodeGenerationPreferences;

/**
 * Utility class that helps transformation of pageflow specific ofsmodelresource
 * 
 * @author pkk
 * 
 */
public class PageflowTransformer implements IOfsModelTransformer {


	/* (non-Javadoc)
	 * @see com.odcgroup.page.generation.exporter.IOfsModelTransformer#transformResourceURI(com.odcgroup.workbench.core.IOfsModelResource)
	 */
	@Override
	public String transform(IOfsModelResource modelResource) {
		if (modelResource == null) {
			return "";
		}
		String value = modelResource.getURI().toString();
		String targetFileName = getPageflowTargetFileName(modelResource);
		if (targetFileName == null) {
			return value;
		}
		String modelName = getModelName(value, modelResource.getURI().fileExtension());
		IProject project = modelResource.getOfsProject().getProject();
		String activity = CodeGenerationPreferences.getPageflowActivityPreference(project);
		value = "/wui/activity/" + activity + "/pageflow/" + targetFileName + "/" + modelName;
		return value;
	}

	/**
	 * @param resourceURL
	 * @return string
	 */
	private static String getModelName(String resourceURL, String extn) {
		int index = resourceURL.lastIndexOf("/");
		return resourceURL.substring(index + 1, resourceURL.length() - extn.length()-1);
	}

	/**
	 * @param resourceURL
	 * @return string
	 */
	@SuppressWarnings("unused")
	private static String getPackage(String resourceURL) {
		int index = resourceURL.lastIndexOf("/");
		String resourceScheme = ModelURIConverter.SCHEME + ":/";
		return resourceURL.substring(resourceScheme.length(), index);
	}

	/**
	 * @param ofsProject
	 * @param modelURI
	 * @return pageflowfileName
	 */
	private static String getPageflowTargetFileName(IOfsModelResource ofsResource) {
		try {
			EList<EObject> modelContents = ofsResource.getEMFModel();
			for (EObject model : modelContents) {
				if (model instanceof Pageflow) {
					Pageflow pageflow = (Pageflow) model;
					return pageflow.getFileName();
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}

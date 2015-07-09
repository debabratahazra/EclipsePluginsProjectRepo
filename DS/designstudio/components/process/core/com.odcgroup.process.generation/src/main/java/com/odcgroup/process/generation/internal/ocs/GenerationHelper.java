package com.odcgroup.process.generation.internal.ocs;

import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.URIConverter;

import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.process.generation.ProcessGenerationCore;
import com.odcgroup.process.model.Property;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.ModelURIConverter;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;
import com.odcgroup.workbench.generation.CodeGenerationPreferences;

public class GenerationHelper {
	
	private static final String RESOURCE_URL_PREFIX = "resource:/";
	private static final String PAGEFLOW_EXTN = ".pageflow";
	
	/**
	 * @return
	 */
	public static String getSystemUser(){
		return System.getProperty("user.name");
	}
	

	/**
	 * @return
	 */
	public static Date getCurrentDate(){
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * @param resourceURL
	 * @return
	 */
	public static String resolveDesignStudioPageflowURIforGeneration(UserTask task, String resourceURL, List<Property> properties, Boolean inverted){
		if (resourceURL.startsWith(RESOURCE_URL_PREFIX) && resourceURL.endsWith(PAGEFLOW_EXTN)){
			String targetFileName = getPageflowTargetFileName(task, resourceURL);
			if (targetFileName == null){
				ProcessGenerationCore.getDefault().logError("Unable to resolve Pageflow ["+resourceURL+"]", null);
				return "UNRESOLVED::"+resourceURL;
			}
			String modelName = getModelName(resourceURL);
			if (inverted){
				IProject project = OfsResourceHelper.getProject(task.eResource());
				String activity = CodeGenerationPreferences.getPageflowActivityPreference(project);
				resourceURL = "/wui/activity/"+activity+"/pageflow/"
								+targetFileName+"/"+modelName
								+parseProperties(properties);
			} else {
				resourceURL = "pageflow:"+targetFileName+"#"+modelName+parseProperties(properties);
			}
			return resourceURL;
		} else {
			ProcessGenerationCore.getDefault().logError("Unable to resolve Pageflow ["+resourceURL+"]", null);
			return resourceURL;
		}
	}
	
	/**
	 * @param root
	 * @param modelURI
	 * @return
	 */
	private static String getPageflowTargetFileName(EObject root, String modelURI) {
		URIConverter converter = root.eResource().getResourceSet().getURIConverter();
		if(converter instanceof ModelURIConverter) {
			ModelURIConverter modelConverter = (ModelURIConverter) converter;
			IOfsProject ofsProject = modelConverter.getOfsProject();
			URI uri = URI.createURI(modelURI);
			try {
				EList<EObject> modelContents = ofsProject.getOfsModelResource(uri).getEMFModel();
				for(EObject model : modelContents) {
					if(model instanceof Pageflow) {
						Pageflow pageflow = (Pageflow) model;
						return pageflow.getFileName();
					}
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}	
	
	/**
	 * @param resourceURL
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getPackage(String resourceURL){
		int index = resourceURL.lastIndexOf("/");
		String value = resourceURL.substring(RESOURCE_URL_PREFIX.length(), index);
		if (value.startsWith("//")) {
			value = value.substring(2);
		}
		return value;
	}
	
	/**
	 * @param resourceURL
	 * @return
	 */
	private static String getModelName(String resourceURL){
		if (resourceURL.startsWith(RESOURCE_URL_PREFIX) && resourceURL.endsWith(PAGEFLOW_EXTN)){
			int index = resourceURL.lastIndexOf("/");
			return resourceURL.substring(index+1, resourceURL.length()-PAGEFLOW_EXTN.length());
		}
		return null;
	}
	
	/**
	 * @param properties
	 * @return
	 */
	private static String parseProperties(List<Property> properties){
		StringBuffer sb = new StringBuffer();
		for (Property property : properties) {
			if (properties.indexOf(property)> 0) {
				sb.append("&");
			} else{
				sb.append("?");
			}
			sb.append(property.getName()+"="+property.getValue());
		}
		return sb.toString();
	}
	
	/**
	 * @return
	 */
	public static String getDSBuildID(){
		String designStudioFeatureID = "com.odcgroup.designstudio";
		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		if (providers != null) {
		    for (int i = 0; i < providers.length; ++i) {
		        IBundleGroup[] bundleGroups = providers[i].getBundleGroups();
		        for (IBundleGroup bg: bundleGroups) {
		        	if (bg.getIdentifier().equals(designStudioFeatureID)) {
		                return bg.getName()+" "+bg.getVersion();
		            }
		        }
		    }
		}
		return "";
	}
}

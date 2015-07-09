package com.odcgroup.process.diagram.custom.util;

import org.eclipse.core.resources.IProject;

import com.odcgroup.workbench.generation.GenerationCore;

/**
 * @author mka
 *
 */
public class ProcessConstraintsUtil {
	/**
	 * @return technical business project
	 */
	public static String getBusinessTechProject(){
		return "com::odcgroup::process::model::constraints::ProcessTechBatchErrorsBE";
	}
	/**
	 * @return sketch business project
	 */
	public static String getBusinessSketchProject(){
		return "com::odcgroup::process::model::constraints::ProcessSketchBatchErrorsBE";
	}
	/**
	 * @return technical developer project
	 */
	public static String getDeveloperTechProject(){
		return "com::odcgroup::process::model::constraints::ProcessTechBatchErrorsDE";
	}
	/**
	 * @return sketch developer project
	 */
	public static String getDeveloperSketchProject(){
		return "com::odcgroup::process::model::constraints::ProcessSketchBatchErrorsDE";
	}
	
	/**
	 * return the appropriate check reference
	 * 
	 * @param project
	 * @return
	 */
	public static String getCheckFileRefBatchConstraint(IProject project){
		if(GenerationCore.isTechnical(project)){
			return getDeveloperTechProject();
		}else{
			return getDeveloperSketchProject();		
		}
	}
	
	/**
	 * @param project
	 * @return
	 */
	public static String getCheckFileRefForValidateAction(IProject project){
		String tempFile = getCheckFileRefBatchConstraint(project);
		tempFile = tempFile.replaceAll("::", "/");
		tempFile = "//src/main/resources/"+tempFile+".chk";
		return tempFile;
	}
}

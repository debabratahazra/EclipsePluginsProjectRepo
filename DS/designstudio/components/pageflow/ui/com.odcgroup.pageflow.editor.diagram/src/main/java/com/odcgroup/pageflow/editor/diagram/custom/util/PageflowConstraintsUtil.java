package com.odcgroup.pageflow.editor.diagram.custom.util;

import org.eclipse.core.resources.IProject;

import com.odcgroup.workbench.generation.GenerationCore;

/**
 * @author mka
 *
 */
public class PageflowConstraintsUtil {
	/**
	 * @return technical business project
	 */
	public static String getBusinessTechProject(){
		return "com::odcgroup::pageflow::model::constraints::PageflowTechBatchErrorsBE";
	}
	/**
	 * @return sketch business project
	 */
	public static String getBusinessSketchProject(){
		return "com::odcgroup::pageflow::model::constraints::PageflowSketchBatchErrorsBE";
	}
	/**
	 * @return technical developer project
	 */
	public static String getDeveloperTechProject(){
		return "com::odcgroup::pageflow::model::constraints::PageflowTechBatchErrorsDE";
	}
	/**
	 * @return sketch developer project
	 */
	public static String getDeveloperSketchProject(){
		return "com::odcgroup::pageflow::model::constraints::PageflowSketchBatchErrorsDE";
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

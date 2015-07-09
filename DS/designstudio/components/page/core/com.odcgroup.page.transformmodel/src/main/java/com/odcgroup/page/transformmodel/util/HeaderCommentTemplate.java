package com.odcgroup.page.transformmodel.util;

/**
 * @author pkk
 *
 */
public interface HeaderCommentTemplate {
	
	/**
	 * Comment Header in the generated artefacts
	 */
	public static String DESIGN_STUDIO_HEADER =
			"\n		****************************************************************************************\n"+
			"																		DO NOT MODIFY THIS FILE!\n"+
			"			This file has been generated automatically  by Design Studio. 		\n" +
			"			Any change will be lost when regenerated.\n"+
			"		****************************************************************************************\n"+
			"														Design Studio information of page model\n"+
			"			Project name     : {0}\n"+
			"			Package          : {1}\n"+
			"			Model name       : {2}\n"+
			"			Last modif. user : {3}\n"+
			"			Last modif. date : {4}\n"+
			"			Generation user  : {5}\n"+
			"			Generation date  : {6}\n"+
			"			DS version       : {7}\n"+
			"			Description      : {8}"+
			"\n		****************************************************************************************\n";

}

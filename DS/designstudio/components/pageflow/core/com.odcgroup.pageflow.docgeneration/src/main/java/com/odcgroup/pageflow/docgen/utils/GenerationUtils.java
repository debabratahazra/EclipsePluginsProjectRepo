package com.odcgroup.pageflow.docgen.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.pageflow.docgen.constants.PageflowDocgenConstants;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * @author ramapriyamn
 *
 */
public class GenerationUtils {

	/**
	 * return
	 * 
	 * @param ofsProject
	 * @param widget
	 * @return MdfDataset
	 */
	public static String getTitleForFileName(String targetFileName) {

		String fileName = targetFileName;
		if (fileName
				.equalsIgnoreCase(getTrimFileName(PageflowDocgenConstants.PAGEFLOW_DESCRIPTION_HTML_FILE))) {
			fileName = "Pageflow Description";

		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageflowDocgenConstants.PAGEFLOW_PROPERTIES_HTML_FILE))) {
			fileName = "Pageflow Properties";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageflowDocgenConstants.PAGEFLOW_STATES_HTML_FILE))) {
			fileName = "Pageflow States";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageflowDocgenConstants.PAGEFLOW_TRANSITIONS_HTML_FILE))) {
			fileName = "Light List of Pageflow Transitions";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageflowDocgenConstants.PAGEFLOW_XTRANSITIONS_HTML_FILE))) {
			fileName = "Large List of Pageflow Transitions";
		} 
		return fileName;
	}

	/**
	 * @param trimFileName
	 * @return
	 */
	public static String getTrimFileName(String trimFileName) {
		return trimFileName.substring(1);
	}
	
	/**
	 * @param project
	 * @return
	 */
	public static String getOutputPathFromPreference(IProject project) {
		ProjectPreferences preferences = new ProjectPreferences(project, "com.odcgroup.documentation.generation.ui");
		String pref = preferences.get(PageflowDocgenConstants.DOCUMENT_GENERATION_ACTIVITY_NAME, PageflowDocgenConstants.DEFUALT_DOCGEN_PATH);
		if(!pref.endsWith("\\") || !pref.endsWith("/")){
			pref += "\\";
		}
		return pref;
	}
	
	/**
	 * @param pageflowFolderPath
	 * @param fileList
	 */
	public static void readAllFiles(String pageflowFolderPath, List<String> fileList) {
		File folder = new File(pageflowFolderPath);
		if(folder.exists()) {	
			String[] a = folder.list();
			for(int i=0; i< a.length ; i++){
				if (!a[i].equalsIgnoreCase("index.html")) {
			
					fileList.add(a[i]);
				}
			}
		}
	}
	
	/**
	 * @param fileList
	 */
	public static void pageflowFilenameSorter(List<String> fileList){
		List<String> copyPageflowFiles = new ArrayList<String>();
		if(fileList.contains(PageflowDocgenConstants.PAGEFLOW_DESCRIPTION_HTML_FILE.substring(1))) copyPageflowFiles.add(PageflowDocgenConstants.PAGEFLOW_DESCRIPTION_HTML_FILE.substring(1));
		if(fileList.contains(PageflowDocgenConstants.PAGEFLOW_PROPERTIES_HTML_FILE.substring(1))) copyPageflowFiles.add(PageflowDocgenConstants.PAGEFLOW_PROPERTIES_HTML_FILE.substring(1)); 
		if(fileList.contains(PageflowDocgenConstants.PAGEFLOW_STATES_HTML_FILE.substring(1))) copyPageflowFiles.add(PageflowDocgenConstants.PAGEFLOW_STATES_HTML_FILE.substring(1));
		if(fileList.contains(PageflowDocgenConstants.PAGEFLOW_TRANSITIONS_HTML_FILE.substring(1))) copyPageflowFiles.add(PageflowDocgenConstants.PAGEFLOW_TRANSITIONS_HTML_FILE.substring(1));
		if(fileList.contains(PageflowDocgenConstants.PAGEFLOW_XTRANSITIONS_HTML_FILE.substring(1))) copyPageflowFiles.add(PageflowDocgenConstants.PAGEFLOW_XTRANSITIONS_HTML_FILE.substring(1));
		fileList.clear();
		fileList.addAll(copyPageflowFiles);
		copyPageflowFiles.clear();
	}
	
}

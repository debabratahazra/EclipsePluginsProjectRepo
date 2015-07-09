package com.odcgroup.page.docgen.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.docgen.constants.PageDesignerDocumentGenerationConstants;
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

		String fileName = targetFileName; //.substring(0,targetFileName.lastIndexOf("."))
		if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.PAGE_DESCRIPTION_HTML_FILE))) {
			fileName = "Page Description";

		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.PAGE_INCLUDE_HTML_FILE))) {
			fileName = "Page Includes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.PAGE_CONTAINER_HTML_FILE))) {
			fileName = "Page Containers";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.PAGE_XCONTAINER_HTML_FILE))) {
			fileName = "Large List of Page Containers";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_DESCRIPTION_HTML))) {
			fileName = "Module Description";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_CONTAINER_HTML))) {
			fileName = "Module Containers";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_INCLUDE_HTML))) {
			fileName = "Module Includes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_ATTRIBUTES_HTML))) {
			fileName = "Module Attributes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_ACTION_HTML))) {
			fileName = "Module Actions";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_ACTION_FILTERS_HTML))) {
			fileName = "Module Action Filters";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_ACTION_SEARCH_HTML))) {
			fileName = "Module Action Search";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_AUTOCOMPLETE_DESIGN_HTML))) {
			fileName = "Module AutoCompleteDesign";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_TRANSLATION_HTML))) {
			fileName = "Module Translations";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_XCONTAINER_HTML))) {
			fileName = "Large List of Module Containers";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MODULE_XATTRIBUTES_HTML))) {
			fileName = "Large List of Module Attributes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_DESCRIPTION_HTML))) {
			fileName = "Fragment Description";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_INCLUDE_HTML))) {
			fileName = "Fragment Includes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_ATTRIBUTES_HTML))) {
			fileName = "Fragment Attributes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_XATTRIBUTES_HTML))) {
			fileName = "Large List of Fragment Attributes";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_HTML))) {
			fileName = "Fragment Actions";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_FILTERS_HTML))) {
			fileName = "Fragment Action Filters";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_SEARCH_HTML))) {
			fileName = "Fragment Action Search";
		}
		else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_CONTAINER_HTML))) {
			fileName = "Fragment Containers";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.FRAGMENT_XCONTAINER_HTML))) {
			fileName = "Large List of Fragment Containers";
		}
		else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_DESCRIPTION_HTML))) {
			fileName = "Table Description";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_GROUPING_HTML))) {
			fileName = "Table Grouping";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_SORTING_HTML))) {
			fileName = "Table Sorting";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_FILTERS_HTML))) {
			fileName = "Table Filters";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_EXTRA_COLUMNS_HTML))) {
			fileName = "Table Extra Columns";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_COLUMNS_HTML))) {
			fileName = "Light List Of Table Columns";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.TABLE_XCOLUMNS_HTML))) {
			fileName = "Large List Of Table Columns";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MATRIX_DESCRIPTION_HTML))) {
			fileName = "Matrix description";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MATRIX_AXIS_HTML))) {
			fileName = "Matrix Axis";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MATRIX_CELL_HTML))) {
			fileName = "Matrix Cell";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MATRIX_FILTERS_HTML))) {
			fileName = "Matrix Filters";
		}
		else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MATRIX_XCOLUMNS_HTML))) {
			fileName = "Matrix Extra Columns";
		} else if (fileName
				.equalsIgnoreCase(getTrimFileName(PageDesignerDocumentGenerationConstants.MATRIX_XATTRIBUTES_HTML))) {
			fileName = "List of Matrix Attributes";
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
		String pref = preferences.get(PageDesignerDocumentGenerationConstants.DOCUMENT_GENERATION_ACTIVITY_NAME, PageDesignerDocumentGenerationConstants.DEFUALT_DOCGEN_PATH);
		if(!pref.endsWith("\\") || !pref.endsWith("/")){
			pref += "\\";
		}
		return pref;
	}
	
	/**
	 * @param moduleFolderPath
	 * @param fileList
	 */
	public static void readAllFiles(String moduleFolderPath, List<String> fileList) {
		File folder = new File(moduleFolderPath);
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
	public static void pageFilenameSorter(List<String> fileList){
		List<String> copyPageFiles = new ArrayList<String>();
		if(fileList.contains(PageDesignerDocumentGenerationConstants.PAGE_DESCRIPTION_HTML_FILE.substring(1))) copyPageFiles.add(PageDesignerDocumentGenerationConstants.PAGE_DESCRIPTION_HTML_FILE.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.PAGE_CONTAINER_HTML_FILE.substring(1))) copyPageFiles.add(PageDesignerDocumentGenerationConstants.PAGE_CONTAINER_HTML_FILE.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.PAGE_INCLUDE_HTML_FILE.substring(1))) copyPageFiles.add(PageDesignerDocumentGenerationConstants.PAGE_INCLUDE_HTML_FILE.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.PAGE_XCONTAINER_HTML_FILE.substring(1))) copyPageFiles.add(PageDesignerDocumentGenerationConstants.PAGE_XCONTAINER_HTML_FILE.substring(1));
		fileList.clear();
		fileList.addAll(copyPageFiles);
		copyPageFiles.clear();
	}
	
	/**
	 * @param fileList
	 */
	public static void moduleFilenameSorter(List<String> fileList){
		List<String> copyModuleFiles = new ArrayList<String>();
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_DESCRIPTION_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_DESCRIPTION_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_CONTAINER_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_CONTAINER_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_INCLUDE_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_INCLUDE_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_ATTRIBUTES_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_ATTRIBUTES_HTML.substring(1));
		
		
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_ACTION_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_ACTION_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_ACTION_FILTERS_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_ACTION_FILTERS_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_ACTION_SEARCH_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_ACTION_SEARCH_HTML.substring(1));
		
		
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_AUTOCOMPLETE_DESIGN_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_AUTOCOMPLETE_DESIGN_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_TRANSLATION_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_TRANSLATION_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_XCONTAINER_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_XCONTAINER_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MODULE_XATTRIBUTES_HTML.substring(1))) copyModuleFiles.add(PageDesignerDocumentGenerationConstants.MODULE_XATTRIBUTES_HTML.substring(1));
		
		fileList.clear();
		fileList.addAll(copyModuleFiles);
		copyModuleFiles.clear();
	}
	
	/**
	 * @param fileList
	 */
	public static void fragmentFilenameSorter(List<String> fileList){
		List<String> copyFragmentFiles = new ArrayList<String>();
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_DESCRIPTION_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_DESCRIPTION_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_CONTAINER_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_CONTAINER_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_INCLUDE_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_INCLUDE_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_ATTRIBUTES_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_ATTRIBUTES_HTML.substring(1));
		
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_FILTERS_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_FILTERS_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_SEARCH_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_ACTION_SEARCH_HTML.substring(1));
		
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_XCONTAINER_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_XCONTAINER_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.FRAGMENT_XATTRIBUTES_HTML.substring(1))) copyFragmentFiles.add(PageDesignerDocumentGenerationConstants.FRAGMENT_XATTRIBUTES_HTML.substring(1));
		
		fileList.clear();
		fileList.addAll(copyFragmentFiles);
		copyFragmentFiles.clear();
	}
	
	/**
	 * @param fileList
	 */
	public static void tableFilenameSorter(List<String> fileList){
		List<String> copyTableFiles = new ArrayList<String>();
		copyTableFiles.addAll(fileList);
		moduleFilenameSorter(copyTableFiles);
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_DESCRIPTION_HTML.substring(1)))  copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_DESCRIPTION_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_GROUPING_HTML.substring(1))) copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_GROUPING_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_SORTING_HTML.substring(1))) copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_SORTING_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_FILTERS_HTML.substring(1))) copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_FILTERS_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_EXTRA_COLUMNS_HTML.substring(1))) copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_EXTRA_COLUMNS_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_COLUMNS_HTML.substring(1))) copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_COLUMNS_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.TABLE_XCOLUMNS_HTML.substring(1))) copyTableFiles.add(PageDesignerDocumentGenerationConstants.TABLE_XCOLUMNS_HTML.substring(1));
		fileList.clear();
		fileList.addAll(copyTableFiles);
		copyTableFiles.clear();
	}

	/**
	 * 
	 * @param fileList
	 */
	public static void matrixFilenameSorter(List<String> fileList){
		List<String> copyMatrixFiles = new ArrayList<String>();
		copyMatrixFiles.addAll(fileList);
		tableFilenameSorter(copyMatrixFiles);
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MATRIX_DESCRIPTION_HTML.substring(1))) copyMatrixFiles.add(PageDesignerDocumentGenerationConstants.MATRIX_DESCRIPTION_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MATRIX_AXIS_HTML.substring(1))) copyMatrixFiles.add(PageDesignerDocumentGenerationConstants.MATRIX_AXIS_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MATRIX_CELL_HTML.substring(1))) copyMatrixFiles.add(PageDesignerDocumentGenerationConstants.MATRIX_CELL_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MATRIX_FILTERS_HTML.substring(1))) copyMatrixFiles.add(PageDesignerDocumentGenerationConstants.MATRIX_FILTERS_HTML.substring(1));
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MATRIX_XCOLUMNS_HTML.substring(1))) copyMatrixFiles.add(PageDesignerDocumentGenerationConstants.MATRIX_XCOLUMNS_HTML.substring(1)); 
		if(fileList.contains(PageDesignerDocumentGenerationConstants.MATRIX_XATTRIBUTES_HTML.substring(1))) copyMatrixFiles.add(PageDesignerDocumentGenerationConstants.MATRIX_XATTRIBUTES_HTML.substring(1));
		fileList.clear();
		fileList.addAll(copyMatrixFiles);
		copyMatrixFiles.clear();
	}
	
	
	/**
	 * @param n
	 * @return
	 */
	public static String generateEmptyTags(int n){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<n; i++){
			sb.append("<td></td>");
		}
		return sb.toString();
	}
	
}

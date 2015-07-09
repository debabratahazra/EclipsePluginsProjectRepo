package com.odcgroup.translation.ui.editor.model;

import org.eclipse.jface.viewers.LabelProvider;

import com.odcgroup.workbench.core.IOfsProject;

/**
 *
 * @author pkk
 *
 */
public interface ITranslationCollectorProvider {
	
	/**
	 * @return
	 */
	String getDisplayName();
	
	/**
	 * @return
	 */
	String[] getModelExtensions();
	
	/**
	 * @return
	 */
	boolean isDefaultActivated();
	
	/**
	 * @param ofsProject
	 * @return
	 */
	ITranslationCollector getTranslationCollector(IOfsProject ofsProject);
	
	/**
	 * @return
	 */
	ITranslationOwnerSelector getTranslationOwnerSelector();
	
	/**
	 * @return
	 */
	LabelProvider getModelLabelProvider();
	
	/**
	 * @param displayName
	 * set the display name for the model collector provider
	 */
	void setDisplayName(String displayName);
	
	/**
	 * comma separated model file extensions
	 * 
	 * @param modelExtensions
	 */
	void setModelExtensions(String modelExtensions);
	
	/**
	 * @param defaultActive
	 * set the collector is the default model
	 */
	void setDefaultActivated(boolean defaultActive);
	
	

}

package com.odcgroup.page.model.snippets;

import com.odcgroup.page.model.Model;

/**
 *
 * @author pkk
 *
 */
public interface IQuery extends ISnippetAdapter {
	
	/**
	 * @return domainEntityName
	 */
	String getSearchModuleDomainEntity();
	
	/**
	 * comma separated dataSet attributes
	 * @return attributes
	 */
	String[] getAttributes();
	
	/**
	 * @return string
	 */
	String getAttributesAsString();
	
	/**
	 * @param attributes
	 */
	void setAttributes(String[] attributes);
	
	/**
	 * comma separated attribute mappings
	 * @return mappings
	 */
	String[] getMappings();
	
	/**
	 * @return string
	 */
	String getMappingsAsString();
	
	/**
	 * @param mappings
	 */
	void setMappings(String[] mappings);
	
	/**
	 * @return module name
	 */
	String getSearchCriteriaModule();
	
	/**
	 * @return model
	 */
	Model getSearchCriteriaModuleModel();
	
	/**
	 * @param module
	 */
	void setSearchCriteriaModule(Object module);
	
	/**
	 * @return tabDisplay
	 */
	IQueryTabDisplay getTabDisplay();
	
	/**
	 * @return
	 */
	int getMaxRowCount();
	
	/**
	 * @param count
	 */
	void setMaxRowCount(int count);
	
	/**
	 * @param tabDisplay
	 */
	void setTabDisplay(IQueryTabDisplay tabDisplay);
	
	/**
	 * @param selectionMode
	 */
	void setSelectionMode(String selectionMode);
	
	
	/**
	 * @return selectionModel
	 */
	String getSelectionMode();
	
	
	/**
	 * @return runAtStart
	 */
	boolean runAtStart();
	
	/**
	 * @param runAtStart
	 */
	void setRunAtStart(boolean runAtStart);
	
	
	/**
	 * 
	 */
	void removeTabDisplay();
	
	/**
	 * 
	 */
	void removeAttributes();
	
	/**
	 * 
	 */
	void removeMappings();
	
	/**
	 * 
	 */
	void removeFilterSets();

}

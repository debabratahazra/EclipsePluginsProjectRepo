package com.odcgroup.page.model.snippets;

/**
 *
 * @author pkk
 *
 */
public interface IQueryTabDisplay extends ISnippetAdapter {
	
	/**
	 * @return tabs
	 */
	String[] getEnabledTabs();
	
	/**
	 * @return string
	 */
	String getEnabledTabsAsString();
	
	/**
	 * @return selection
	 */
	String getSelection();
	
	/**
	 * @param tabs
	 */
	void setEnabledTabs(String[] tabs);
	
	/**
	 * @param selection
	 */
	void setSelection(String selection);
	
	/**
	 * @return query
	 */
	IQuery getQuery();

}

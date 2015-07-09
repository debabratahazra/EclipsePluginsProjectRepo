package com.odcgroup.page.model.widgets;

import java.util.List;

import org.eclipse.emf.common.util.URI;

import com.odcgroup.page.model.Model;
import com.odcgroup.page.model.snippets.IParameterSnippet;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 * @author pkk
 *
 */
public interface ISearchField extends IWidgetAdapter {
	
	/**
	 * @return
	 */
	String getDomainAttribute();
	
	/**
	 * @return
	 */
	String getSearchType();
	
	/**
	 * @return
	 */
	String getWidgetGroup();
	
	/**
	 * @return
	 */
	Model getOutputDesign();
	
	/**
	 * @return
	 */
	URI getOutputDesignURI();
	
	/**
	 * @param parameter
	 */
	void addParameter(IParameterSnippet parameter);
	
	/**
	 * @param parameter
	 */
	void removeParameter(IParameterSnippet parameter);
	
	/**
	 * @return
	 */
	int getDelay();
	
	/**
	 * @return
	 */
	int getNumberOfCharacters();
	
	
	/**
	 * @return
	 */
	List<IParameterSnippet> getParameters();
	
	/**
	 * @return
	 */
	boolean isAutoCompleteOnly();
	
	/**
	 * @return
	 */
	boolean isAutoCompleteAndSearch();
	
	/**
	 * @return
	 */
	boolean isSearchOnly();
	
	
	
}

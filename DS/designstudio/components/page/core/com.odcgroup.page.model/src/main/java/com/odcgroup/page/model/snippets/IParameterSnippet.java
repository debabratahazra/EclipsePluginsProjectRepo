package com.odcgroup.page.model.snippets;

/**
 * @author pkk
 *
 */
public interface IParameterSnippet extends ISnippetAdapter {
	
	/**
	 * get the parameter name
	 * @return
	 */
	String getName();
	
	/**
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * get the parameter value
	 * @return
	 */
	String getValue();
	
	/**
	 * @param value
	 */
	void setValue(String value);

}

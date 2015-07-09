package com.odcgroup.page.model.snippets.impl;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IParameterSnippet;

/**
 * @author pkk
 *
 */
public class ParameterSnippet extends SnippetAdapter implements IParameterSnippet {
	
	/** */
	private static final String PARAMNAME_PROPERTY = "param-name";
	/** */
	private static final String PARAMVALUE_PROPERTY = "param-value";

	/**
	 * @param snippet
	 * @param parent 
	 */
	public ParameterSnippet(Snippet snippet) {
		super(snippet);
	}

	@Override
	public String getName() {
		return getPropertyValue(PARAMNAME_PROPERTY);
	}

	@Override
	public String getValue() {
		return getPropertyValue(PARAMVALUE_PROPERTY);
	}

	@Override
	public void setName(String name) {
		setPropertyValue(PARAMNAME_PROPERTY, name);
	}

	@Override
	public void setValue(String value) {
		setPropertyValue(PARAMVALUE_PROPERTY, value);		
	}
}

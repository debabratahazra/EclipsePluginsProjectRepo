package com.odcgroup.page.model.snippets.impl;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.ISnippetAdapter;
import com.odcgroup.page.model.snippets.ISnippetFactory;
import com.odcgroup.page.model.snippets.SnippetUtil;

/**
 *
 * @author pkk
 *
 */
public class SnippetAdapter implements ISnippetAdapter {
	
	/** */
	private Snippet snippet;
	
	/**
	 * @param snippet
	 */
	public SnippetAdapter(Snippet snippet) {
		this.snippet = snippet;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.ISnippetAdapter#getSnippet()
	 */
	public final Snippet getSnippet() {
		return this.snippet;
	}
	
	/**
	 * @param propertyName the name of a property
	 * @return the specified property
	 */
	protected final Property getProperty(String propertyName) {
		for (Property property : snippet.getProperties()) {
			if (property.getTypeName().equals(propertyName)) {
				return property;
			}
		}
		return null;
	}	

	/**
	 * @param propertyName the name of a property
	 * @return the value of the specified property
	 */
	protected final String getPropertyValue(String propertyName) {
		Property property = getProperty(propertyName);
		if (property != null) {
			return property.getValue();
		}
		return null;
	}

	/**
	 * Changes the value of the specified property
	 * 
	 * @param propertyName
	 *            the name of the property
	 * @param value
	 *            the new value of the property
	 */
	protected final void setPropertyValue(String propertyName, int value) {
		Property property = getProperty(propertyName);
		if (property != null) {
			property.setValue(value);
		}
	}
	
	/**
	 * @param propertyName
	 * @param value
	 */
	protected final void setPropertyValue(String propertyName, boolean value) {
		Property property = getProperty(propertyName);
		if (property != null) {
			property.setValue(value);
		}		
	}
	
	/**
	 * Changes the value of the specified property
	 * 
	 * @param propertyName
	 *            the name of the property
	 * @param value
	 *            the new value of the property
	 */
	protected final void setPropertyValue(String propertyName, String value) {
		Property property = getProperty(propertyName);
		if (property != null) {
			property.setValue(value);
		} else {
			property = ModelFactory.eINSTANCE.createProperty();
			property.setTypeName(propertyName);
			property.setLibraryName("xgui");
			property.setValue(value);
			snippet.getProperties().add(property);
		}
	}	
	
	/**
	 * @return snippetFactory
	 */
	public ISnippetFactory getSnippetFactory() {
		return SnippetUtil.getSnippetFactory();
	}

	
	/**
	 * @param strings
	 * @param separator
	 * @return string
	 */
	protected String joinByDelimiter(String[] strings, String separator) {
		return StringUtils.join(strings, separator);
	}	

}

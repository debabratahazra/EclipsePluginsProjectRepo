package com.odcgroup.page.model.snippets.impl;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.ILineItemSnippet;
import com.odcgroup.page.model.snippets.ILineSnippet;

/**
 * @author pkk
 *
 */
public class LineItemSnippet extends SnippetAdapter implements ILineItemSnippet {
	
	private ILineSnippet parent;
	private static final String TRANSLATION_PROPERTYTYPE = "translation";
	private static final String LINEATTRIBUTE_PROPERTYTYPE = "lineAttribute";

	/**
	 * @param snippet
	 * @param parent
	 */
	public LineItemSnippet(Snippet snippet, ILineSnippet parent) {
		super(snippet);
		this.parent = parent;
	}

	@Override
	public String getAttributeName() {
		return getPropertyValue(LINEATTRIBUTE_PROPERTYTYPE);
	}

	@Override
	public boolean isTranslation() {
		return Boolean.valueOf(getPropertyValue(TRANSLATION_PROPERTYTYPE)).booleanValue();
	}

	@Override
	public String getCSSClass() {
		return getPropertyValue(PropertyTypeConstants.CSS_CLASS);
	}

	@Override
	public void setAttributeName(String attribute) {
		setPropertyValue(LINEATTRIBUTE_PROPERTYTYPE, attribute);

	}

	@Override
	public void setTranslation(boolean translation) {
		setPropertyValue(TRANSLATION_PROPERTYTYPE, translation);
	}

	@Override
	public void setCSSClass(String css) {
		setPropertyValue(PropertyTypeConstants.CSS_CLASS, css);

	}

	@Override
	public ILineSnippet getParent() {
		return parent;
	}

}

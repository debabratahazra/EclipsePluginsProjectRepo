package com.odcgroup.page.model.snippets.impl;

import com.odcgroup.page.model.Snippet;
import com.odcgroup.page.model.snippets.IFilter;
import com.odcgroup.page.model.snippets.IFilterSet;

/**
 *
 * @author pkk
 *
 */
public class Filter extends SnippetAdapter implements IFilter {

	/** */
	private static final String ATTRIBUTE_PROPERTY = "attribute";
	/** */
	private static final String EDITMODE_PROPERTY = "editMode";
	/** */
	private static final String OPERATOR_PROPERTY = "operator";
	/** */
	private static final String VALUE_PROPERTY = "filterValue";
	/** */
	private static final String OTHERVALUE_PROPERTY = "otherValue";
	/** */	
	private Snippet parent;
	
	/**
	 * @param snippet
	 * @param parent 
	 */
	public Filter(Snippet snippet, Snippet parent) {
		super(snippet);
		this.parent = parent;
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#getDatasetAttributeName()
	 */
	public String getDatasetAttributeName() {
		return getPropertyValue(ATTRIBUTE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#getEditMode()
	 */
	public String getEditMode() {
		return getPropertyValue(EDITMODE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#getOperator()
	 */
	public String getOperator() {
		return getPropertyValue(OPERATOR_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#getValue()
	 */
	public String getValue() {
		return getPropertyValue(VALUE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#setDatasetAttribute(java.lang.String)
	 */
	public void setDatasetAttribute(String attribute) {
		setPropertyValue(ATTRIBUTE_PROPERTY, attribute);
		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#setEditMode(java.lang.String)
	 */
	public void setEditMode(String editMode) {
		setPropertyValue(EDITMODE_PROPERTY, editMode);		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#setOperator(java.lang.String)
	 */
	public void setOperator(String operator) {
		setPropertyValue(OPERATOR_PROPERTY, operator);			
	}	

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		setPropertyValue(VALUE_PROPERTY, value);		
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#getParent()
	 */
	public IFilterSet getParent() {
		return getSnippetFactory().adaptFilterSetSnippet(parent);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#getOtherValue()
	 */
	public String getOtherValue() {
		return getPropertyValue(OTHERVALUE_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.snippets.IFilter#setOtherValue(java.lang.String)
	 */
	public void setOtherValue(String value) {
		setPropertyValue(OTHERVALUE_PROPERTY, value);		
	}

}

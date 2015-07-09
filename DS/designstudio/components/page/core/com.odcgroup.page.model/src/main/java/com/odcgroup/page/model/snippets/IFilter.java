package com.odcgroup.page.model.snippets;


/**
 *
 * @author pkk
 *
 */
public interface IFilter extends ISnippetAdapter {
	
	/**
	 * @return the dataset attribute name
	 */
	String getDatasetAttributeName();
	
	/**
	 * @param attribute
	 */
	void setDatasetAttribute(String attribute);	
	
	/**
	 * @return the filter operator
	 */
	String getOperator();
	
	/**
	 * @param operator
	 */
	void setOperator(String operator);
	
	/**
	 * @return filter value
	 */
	String getValue();
	
	/**
	 * @param value
	 */
	void setValue(String value);
	
	/**
	 * @param value
	 */
	void setOtherValue(String value);
	
	/**
	 * @return value
	 */
	String getOtherValue();
	
	/**
	 * @return the edit mode
	 */
	String getEditMode();
	
	/**
	 * @param editMode
	 */
	void setEditMode(String editMode);
	
	/**
	 * @return filterset
	 */
	IFilterSet getParent();
	
	

}

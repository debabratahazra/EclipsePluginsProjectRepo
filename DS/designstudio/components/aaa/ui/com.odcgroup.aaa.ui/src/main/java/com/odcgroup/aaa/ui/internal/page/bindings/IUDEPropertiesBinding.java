package com.odcgroup.aaa.ui.internal.page.bindings;


public interface IUDEPropertiesBinding {

	String getDescription();
	
	/**
	 * Update the text fields from the model
	 */
	public void updateModelToText();

	/**
	 * Update the model from the text fields
	 */
	public void updateTextToModel(boolean modifyEvent);
	
	/**
	 * @return true when the updateModelToText is
	 * updating the text fields. This is needed to
	 * managed properly the dirty flag.
	 */
	public boolean isInitializing();

}

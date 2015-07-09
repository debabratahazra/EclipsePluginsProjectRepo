package com.odcgroup.workbench.editors.properties.model;



/**
 *
 * @author pkk
 *
 */
public interface IPropertySelectionDialog {
	
	public int open();
	
	public Object getSelection();	
	
	public Object getResultByProperty(IPropertyFeature property);

}

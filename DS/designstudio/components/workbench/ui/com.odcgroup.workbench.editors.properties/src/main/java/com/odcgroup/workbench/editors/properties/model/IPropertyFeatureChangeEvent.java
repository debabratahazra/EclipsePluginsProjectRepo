package com.odcgroup.workbench.editors.properties.model;



/**
 *
 * @author pkk
 *
 */
public interface IPropertyFeatureChangeEvent {
		
	public Object getValue();
	
	public IPropertyFeature getPropertyFeature();
	
	public boolean isValueFromClipboard();

}

package com.odcgroup.workbench.editors.properties.model;

import java.util.List;

/**
 *
 * @author pkk
 *
 */
public interface IPropertyContainer extends IPropertyFeatureChangeListener {
	
	public List<IPropertyFeature> getPropertyFeatures();
	
	public void addPropertyFeature(IPropertyFeature propertyFeature);
	
	//public void dispose();

}

package com.odcgroup.workbench.editors.properties.model;

import java.util.List;

import org.eclipse.emf.ecore.EReference;

/**
 *
 * @author pkk
 *
 */
public interface IPropertyReference extends IPropertyFeature, IPropertyContainer {
	
	public EReference getReference();
	
	public void addPropertyFeature(IPropertyFeature propertyFeature);
	
	public List<IPropertyFeature> getPropertyFeatures();
	

}

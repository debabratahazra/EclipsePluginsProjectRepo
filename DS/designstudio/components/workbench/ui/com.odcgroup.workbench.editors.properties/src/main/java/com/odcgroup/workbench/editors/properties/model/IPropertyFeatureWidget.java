package com.odcgroup.workbench.editors.properties.model;

import java.util.List;

import org.eclipse.emf.ecore.EObject;


/**
 *
 * @author pkk
 *
 */
public interface IPropertyFeatureWidget {	
	
	public void initiateWidget(EObject element, EObject root);
	
	public EObject getElement();
	
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyFeatureChangeListener);
	
	public void removePropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyFeatureChangeListener);	
	
	public void notifyPropertyFeatureChange(Object value);
	
	public List<IPropertyFeatureChangeListener> getPropertyFeatureChangeListeners();

}

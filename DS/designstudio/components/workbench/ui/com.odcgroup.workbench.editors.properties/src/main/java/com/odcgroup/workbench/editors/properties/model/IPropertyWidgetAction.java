package com.odcgroup.workbench.editors.properties.model;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelectionChangedListener;


/**
 *
 * @author pkk
 *
 */
public interface IPropertyWidgetAction extends IAction, ISelectionChangedListener {
	
	public List<EObject> getSelection();
	
	public void setPropertyItem(IPropertyFeature propertyItem);	
	
	public void addPropertyChangeListener(IPropertyFeatureChangeListener propertyChangeListener);
	
	public void removePropertyChangeListener(IPropertyFeatureChangeListener propertyChangeListener);

}

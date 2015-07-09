package com.odcgroup.workbench.editors.properties.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;
import com.odcgroup.workbench.editors.properties.model.IPropertyWidgetAction;
import com.odcgroup.workbench.editors.properties.model.impl.PropertyFeatureChangeEvent;

/**
 *
 * @author pkk
 *
 */
public abstract class AbstractWidgetAction extends BaseSelectionListenerAction implements IPropertyWidgetAction {
	
	private IPropertyFeature propertyItem;
	private List<IPropertyFeatureChangeListener> listeners = new ArrayList<IPropertyFeatureChangeListener>();

	/**
	 * @param table
	 */
	public AbstractWidgetAction(String text) {
		super(text);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public boolean updateSelection(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			return false;
		}
		return true;
	}	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.widgets.model.IPropertyWidgetAction#getSelection()
	 */
	@SuppressWarnings("unchecked")
	public final List<EObject> getSelection() {
		IStructuredSelection selection = getStructuredSelection();
		Iterator iterator = selection.iterator();
		List<EObject> selObjects = new ArrayList<EObject>();
		while(iterator.hasNext()) {
			selObjects.add((EObject) iterator.next());
		}
		return selObjects;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public final void run() {
		runAction();
	}
	
	
	/**
	 * @return the parent
	 */
	public IPropertyFeature getPropertyItem() {
		return propertyItem;
	}

	/**
	 * @param propertyItem the propertyItem to set
	 */
	public void setPropertyItem(IPropertyFeature propertyItem) {
		this.propertyItem = propertyItem;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.IPropertyWidget#addPropertyChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeListener)
	 */
	public void addPropertyChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		if (!listeners.contains(propertyChangeListener)) {
			listeners.add(propertyChangeListener);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.IPropertyWidget#removePropertyChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeListener)
	 */
	public void removePropertyChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		listeners.remove(propertyChangeListener);		
	}
	
	/**
	 * 
	 */
	public final void notifyPropertyChange(Object value) {	
		IPropertyFeatureChangeEvent  changeEvent = createChangeEvent(value);
		for (IPropertyFeatureChangeListener listener : listeners) {
			listener.propertyChanged(changeEvent);
		}
	}	
	
	/**
	 * @param value
	 * @return
	 */
	private IPropertyFeatureChangeEvent createChangeEvent(Object value) {
		return new PropertyFeatureChangeEvent(propertyItem, value, true);
	}
	
	/**
	 * 
	 */
	public abstract void runAction();

}

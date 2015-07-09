package com.odcgroup.workbench.editors.properties.item;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener;
import com.odcgroup.workbench.editors.properties.model.impl.PropertyFeatureChangeEvent;
import com.odcgroup.workbench.editors.properties.widgets.ISWTWidget;


/**
 *
 * @author pkk
 *
 */
public abstract class AbstractPropertyWidget implements IPropertyFeature, ISWTWidget {

	private List<IPropertyFeatureChangeListener> listeners = new ArrayList<IPropertyFeatureChangeListener>();
	private String label;
	private String tooltip;
	private EStructuralFeature structuralFeature;

	private EObject element;
	private EObject rootElement;

	/**
	 * @param label
	 * @param tooltip
	 */
	public AbstractPropertyWidget(EStructuralFeature feature, String label, String tooltip) {
		this.structuralFeature = feature;
		this.label = label;
		this.tooltip = tooltip;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeature#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeature#getTooltip()
	 */
	public String getTooltip() {
		return tooltip;
	}	

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureWidget#addPropertyFeatureChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeListener)
	 */
	public void addPropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		if (!listeners.contains(propertyChangeListener)) {
			listeners.add(propertyChangeListener);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.widgets.IPropertyWidget#removePropertyChangeListener(com.odcgroup.workbench.editors.properties.model.IPropertyItemChangeListener)
	 */
	public void removePropertyFeatureChangeListener(IPropertyFeatureChangeListener propertyChangeListener) {
		listeners.remove(propertyChangeListener);		
	}	

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureWidget#notifyPropertyFeatureChange(java.lang.Object)
	 */
	public void notifyPropertyFeatureChange(Object value) {		
		IPropertyFeatureChangeEvent changeEvent = createChangeEvent(value);
		for (IPropertyFeatureChangeListener listener : listeners) {
			listener.propertyChanged(changeEvent);
		}
	}	
	
	/**
	 * @param value
	 * @return
	 */
	private IPropertyFeatureChangeEvent createChangeEvent(Object value) {
		return new PropertyFeatureChangeEvent(this, value, false);
	}
	
	/**
	 * @return
	 */
	protected Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}
	
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureWidget#initiateWidget
	 * (org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	public final void initiateWidget(EObject element, EObject root) {
		this.element = element;
		this.rootElement = root;
		initPropertyControls();		
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureWidget#getParentElement()
	 */
	public EObject getElement() {
		return element;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeature#getStructuralFeature()
	 */
	public EStructuralFeature getStructuralFeature() {
		return structuralFeature;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureWidget#getPropertyFeatureChangeListeners()
	 */
	public List<IPropertyFeatureChangeListener> getPropertyFeatureChangeListeners() {
		return listeners;
	}
	
	/**
	 * @return the rootElement
	 */
	public EObject getRootElement() {
		return rootElement;
	}

	/**
	 * @param rootElement the rootElement to set
	 */
	public void setRootElement(EObject rootElement) {
		this.rootElement = rootElement;
	}
	
	/**
	 * 
	 */
	protected abstract void initPropertyControls();

}

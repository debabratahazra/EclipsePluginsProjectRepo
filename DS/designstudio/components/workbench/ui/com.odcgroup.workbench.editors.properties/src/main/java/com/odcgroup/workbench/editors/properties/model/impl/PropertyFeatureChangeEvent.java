package com.odcgroup.workbench.editors.properties.model.impl;

import java.util.EventObject;

import com.odcgroup.workbench.editors.properties.model.IPropertyFeature;
import com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent;

/**
 *
 * @author pkk
 *
 */
public class PropertyFeatureChangeEvent extends EventObject implements IPropertyFeatureChangeEvent {	
	
	private static final long serialVersionUID = 584593487744582559L;
	private final IPropertyFeature propertyFeature;
	private final Object value;
	private boolean valueFromClipboard;
	
	
	/**
	 * @param propertyFeature
	 * @param value
	 * @param valueFromClipboard
	 */
	public PropertyFeatureChangeEvent(final IPropertyFeature propertyFeature, Object value, boolean valueFromClipboard) {
		super(propertyFeature);
		this.propertyFeature = propertyFeature;
		this.value = value;
		this.valueFromClipboard = valueFromClipboard;
	}

	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent#getPropertyFeature()
	 */
	public IPropertyFeature getPropertyFeature() {
		return propertyFeature;
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent#getValue()
	 */
	public Object getValue() {
		return value;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.workbench.editors.properties.model.IPropertyFeatureChangeEvent#isValueFromClipboard()
	 */
	public boolean isValueFromClipboard() {
		return valueFromClipboard;
	}

}

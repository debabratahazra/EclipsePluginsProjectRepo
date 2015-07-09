/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.odcgroup.integrationfwk.ui.views;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;

/**
 * The property source provider for a button element.
 * 
 * @author Anthony Hunter
 */
public class TWSConsumerProjectProperties implements IPropertySource {

	final protected TWSConsumerProject element;

	protected static final String PROPERTY_NAME = "text"; //$NON-NLS-1$

	private final Object PropertiesTable[][] = {

	//        {PROPERTY_SIZE, new PropertyDescriptor(PROPERTY_SIZE, "Size")},//$NON-NLS-1$
	{ PROPERTY_NAME, new TextPropertyDescriptor(PROPERTY_NAME, "Name") }, //$NON-NLS-1$

	};

	String strName = "";//$NON-NLS-1$

	// Point ptSize = null;

	/**
	 * Creates a new ButtonElementProperties.
	 * 
	 * @param element
	 *            the element whose properties this instance represents
	 */
	public TWSConsumerProjectProperties(TWSConsumerProject element) {
		super();
		this.element = element;
		initProperties();
	}

	protected void firePropertyChanged(String propName, Object value) {

		System.out.println("can not change the project name this way");
		// use IProjectDescription tp change the IProject
		// IProject iProject = element.getProject();

		// if (iProject == null) {

		// return;
		// }

		// if (propName.equals(PROPERTY_FONT)) {
		/**
		 * Font oldfont = ctl.getFont(); if (oldfont != null) {
		 * oldfont.dispose(); }
		 */

		// }
		// if (propName.equals(PROPERTY_NAME)) {
		// can not chaneg the name of the project like that
		// element.setP
		// pro
		// return;
		// }

		// if (propName.equals(PROPERTY_CAT)) {
		// ctl.setCatName((String) value);
		// return;
		// }

	}

	/**
	 * Returns the mocha element.
	 * 
	 * @return MochaElement
	 */
	public TWSConsumerProject getButtonElement() {
		return element;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue() {
		return this;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		// Create the property vector.
		IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[PropertiesTable.length];

		for (int i = 0; i < PropertiesTable.length; i++) {
			// Add each property supported.

			PropertyDescriptor descriptor;

			descriptor = (PropertyDescriptor) PropertiesTable[i][1];
			propertyDescriptors[i] = descriptor;
			descriptor.setCategory("Basic");//$NON-NLS-1$
		}

		// Return it.
		return propertyDescriptors;

	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	public Object getPropertyValue(Object name) {
		if (name.equals(PROPERTY_NAME)) {
			return strName;
		}

		return null;
	}

	protected void initProperties() {
		// i guess it somehow initialises properties
		// get the properties from the object
		IProject iProject = element.getProject();

		if (iProject == null) {
			// the GUIView is probably hidden in this case
			return;
		}
		// load all the properties , ne after the other to be picked up by
		// LableSection
		strName = iProject.getName();
		// strCat = ctl.getCatName();
		/**
		 * Font font = ctl.getFont(); if (font != null) { strFont =
		 * font.getFontData().toString(); }
		 */
		// /ptSize = ctl.getSize();
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
	 */
	public boolean isPropertySet(Object id) {
		return false;
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(Object)
	 */
	public void resetPropertyValue(Object id) {
		//
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object,
	 *      Object)
	 */
	public void setPropertyValue(Object name, Object value) {
		// some kind of persistence have to take place here.....

		firePropertyChanged((String) name, value);

		if (name.equals(PROPERTY_NAME)) {
			strName = (String) value;
			return;
		}
		// keep for future if need example how to process object rather than
		// text
		// if (name.equals(PROPERTY_SIZE)) {
		// SizePropertySource sizeProp = (SizePropertySource) value;
		// ptSize = sizeProp.getValue();
		// }

	}

}
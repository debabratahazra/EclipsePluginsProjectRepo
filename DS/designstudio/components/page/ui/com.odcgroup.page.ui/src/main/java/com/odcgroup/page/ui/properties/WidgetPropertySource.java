/*
 * Created on Jul 20, 2003
 *
 * Eclipse GEF redbook sample application
 * $Source: /usr/local/cvsroot/SAL330RGEFDemoApplication/src/com/ibm/itso/sal330r/gefdemo/model/WorkflowElementPropertySource.java,v $
 * $Revision: 1.4 $
 * 
 * (c) Copyright IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0 
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 * 		ddean		Initial version
 */
package com.odcgroup.page.ui.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.metamodel.PropertyType;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.filter.PropertyFilter;
import com.odcgroup.page.util.ClassUtils;

/**
 * This class is intended to be a generic property source for all the widgets in the application's model.
 * It uses information in the PropertyType to build the property descriptors.
 * 
 * @author atr
 */
public class WidgetPropertySource implements IWidgetPropertySource {

	/** The wrapped widget. */
	private Widget widget;	
	/** The property category used to filter properties */
	private PropertyCategory currentPropertyCategory;
	/** The command stack used to execute commands*/
	private CommandStack commandStack;
	
	/**
	 * The Map of PropertySourceAdapters.
	 * Key: Property name
	 * Value: PropertySourceAdapter
	 */
	private Map<String, PropertySourceAdapter> propertySourcesAdapters = 
		new HashMap<String, PropertySourceAdapter>();
	
	/**
	 * Creates a new WidgetPropertySource.
	 * 
	 * @param widget
	 *          The widget for which property descriptors must be build
	 * @param commandStack
	 * 			The command stack used to execute command
	 */
	public WidgetPropertySource(Widget widget, CommandStack commandStack) {
		Assert.isNotNull(widget);
		Assert.isNotNull(commandStack);
		
		this.widget = widget;
		this.commandStack = commandStack;
		currentPropertyCategory = PropertyCategory.GENERAL_LITERAL;
	}

	/**
	 * Creates a new IPropertyDescriptor. The type to create depends upon whether
	 * the PropertyType is internationalizable or not.
	 * 
	 * @param property The Property to create the IPropertyDescriptor for
	 * @return IPropertyDescriptor The newly created IPropertyDescriptor
	 */
	private IPropertyDescriptor createPropertyDescriptor(Property property) {
		return new PropertyValuePropertyDescriptor(property);	
	}

	/**
	 * Creates the PropertySourceAdapter. This is either
	 * a DataValuePropertySourceAdapter or a ValuePropertySourceAdapter
	 * depending upon whether the Property is an enumeration or not.
	 * 
	 * @param property The Property to create the Adapter for
	 * @return PropertySourceAdapter The newly created PropertySourceAdapter
	 */
	private PropertySourceAdapter createPropertySourceAdapter(Property property) {
	    String sa = property.getType().getSourceAdapter();
	    if (! StringUtils.isEmpty(sa)) {
	        return (PropertySourceAdapter) ClassUtils.newInstance(getClass().getClassLoader(), sa, new Object[]{property, commandStack});
	    }
	    
	    // Use the default PropertySourceAdapter
	    if (property.getType().getDataType().getValues().size() > 0) {
	        return new DataValuePropertySourceAdapter(property, commandStack);
	    } else {
	        return new ValuePropertySourceAdapter(property, commandStack);
	    }
	}

	/**
	 * Filters the properties of the Widget.
	 * 
	 * @param widget The Widget to filter
	 * @return List The filtered properties
	 */
	private List<Property> filterProperties(Widget widget) {
		String pfStr = widget.getType().getPropertyFilterClass();
		if (StringUtils.isEmpty(pfStr)) {
			return widget.getProperties();
		}
		
		PropertyFilter pf = (PropertyFilter) ClassUtils.newInstance(getClass().getClassLoader(), pfStr, PropertyFilter.class);
		return pf.filter(widget);
	}

	/**
	 * Gets the editable value.
	 * 
	 * @return Object The editable value
	 */
	public Object getEditableValue() {
		return this;
	}

	/**
	 * Gets the PropertyDescriptors.
	 * 
	 * @return IPropertyDescriptor[] The property descriptors
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
		
		for (Property property : filterProperties(widget)) {
			PropertyType pt = property.getType();
			if (currentPropertyCategory != null) {
				if (pt.getCategory().equals(currentPropertyCategory)) {
					IPropertyDescriptor descriptor = createPropertyDescriptor(property);
					descriptors.add(descriptor);
				}
			} else {
				IPropertyDescriptor descriptor = createPropertyDescriptor(property);
				descriptors.add(descriptor);
			}
		}

		// Finally return the collection of properties
		return (IPropertyDescriptor[]) descriptors.toArray(new IPropertyDescriptor[] {});
	}

	/**
	 * Gets the PropertyPropertySource. These are lazily created.
	 * 
	 * @param id The id of the PropertyPropertySource to get
	 * @return PropertySourceAdapter The PropertySourceAdapter
	 */
	private PropertySourceAdapter getPropertySourceAdapter(Object id) {
		PropertySourceAdapter psa = (PropertySourceAdapter) propertySourcesAdapters.get(id);
		if (psa == null) {
			String name = (String) id;
			Property p = widget.findProperty(name);
			psa = createPropertySourceAdapter(p);
			propertySourcesAdapters.put(name, psa);
		}
		
		return psa;
	}

	/**
	 * Gets the PropertyValue. 
	 * 
	 * @param id The id of the property to get
	 * @return Object The PropertyValue
	 */
	public Object getPropertyValue(Object id) {
		return getPropertySourceAdapter(id).getPropertyValue();
	}
	
	/**
	 * Gets the Widget.
	 * 
	 * @return Widget The widget
	 */
	public final Widget getWidget() {
		return widget;
	}	
	
	/**
	 * Null implementation since the WidgetPropertySource is simply a container
	 * for PropertyPropertySource's.
	 * 
	 * @param id The id of the property
	 * @return boolean True all the time
	 */
	public boolean isPropertySet(Object id) {
		return getPropertySourceAdapter(id).isPropertySet();
	}	
	
	/**
	 * Null implementation since the WidgetPropertySource is simply a container
	 * for PropertyPropertySource's.
	 * 
	 * @param id The id of the property
	 */
	public void resetPropertyValue(Object id) {
		getPropertySourceAdapter(id).resetPropertyValue();
	}
	
	/**
	 * Sets the current property category.
	 * 
	 * @param currentPropertyCategory the property category
	 */
	public void setCurrentPropertyCategory(PropertyCategory currentPropertyCategory) {
		this.currentPropertyCategory = currentPropertyCategory;
	}
	
	/**
	 * Null implementation since the WidgetPropertySource is simply a container
	 * for PropertyPropertySource's.
	 * 
	 * @param newValue The new value of the property
	 * @param id The id of the property
	 */
	public void setPropertyValue(Object id, Object newValue) {
		getPropertySourceAdapter(id).setPropertyValue(newValue);
	}
}

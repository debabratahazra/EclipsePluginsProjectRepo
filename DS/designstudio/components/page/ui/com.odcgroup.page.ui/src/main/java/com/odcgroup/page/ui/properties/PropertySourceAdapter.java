package com.odcgroup.page.ui.properties;

import org.eclipse.ui.views.properties.IPropertySource;

/**
 * Adapter for a PropertySource. See {@link IPropertySource} for
 * a more detailed description of the different methods.
 * 
 * @author atr
 * @author Gary Hayes
 */
public interface PropertySourceAdapter {

	/**
	 * Gets the value of the property.
	 * 
	 * @return Object The value of the property
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public Object getPropertyValue();

	/**
	 * Returns true if the value of the Property is set.
	 * 
	 * @return boolean True if the value of the Property is set
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public boolean isPropertySet();

	/**
	 * Resets the value of the property.
	 * @see IPropertySource#resetPropertyValue(Object)
	 */
	public void resetPropertyValue();

	/**
	 * Sets the value of the property.
	 * 
	 * @param newValue
	 *            The value of the property
	 * @see IPropertySource#setPropertyValue(Object, Object)
	 */
	public void setPropertyValue(Object newValue);

}

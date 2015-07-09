package com.odcgroup.page.ui.properties;

import org.eclipse.ui.views.properties.IPropertySource;

import com.odcgroup.page.metamodel.PropertyCategory;
import com.odcgroup.page.model.Widget;

/**
 * Interface to Widget which is capable of supplying properties for display
 * by the property sheet page.
 * 
 * @author <a href="mailto:atripod@odyssey-group.com">Alain Tripod</a>
 */
public interface IWidgetPropertySource extends IPropertySource {

	/**
	 * Sets the current property category.
	 * 
	 * @param propertyCategory the property category
	 */
	public void setCurrentPropertyCategory(PropertyCategory propertyCategory);
	
	/**
	 * @return the wrapped Widget
	 */
	public Widget getWidget();

}

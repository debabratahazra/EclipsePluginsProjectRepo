package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.mdf.ecore.util.DomainRepository;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * This is the base class for all widget adapter
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class WidgetAdapter implements IWidgetAdapter {

	/** The aggregate widget */
	private Widget widget;

	/**
	 * @param widget
	 *            the adapter widget
	 */
	protected WidgetAdapter(Widget widget) {
		this.widget = widget;
	}
	

	/**
	 * @param propertyName the name of a property
	 * @return the specified property
	 */
	protected final Property getProperty(String propertyName) {
		return getWidget().findProperty(propertyName);
	}	

	/**
	 * @param propertyName the name of a property
	 * @return the value of the specified property
	 */
	protected final String getPropertyValue(String propertyName) {
		return getWidget().getPropertyValue(propertyName);
	}

	/**
	 * Changes the value of the specified property
	 * 
	 * @param propertyName
	 *            the name of the property
	 * @param value
	 *            the new value of the property
	 */
	protected final void setPropertyValue(String propertyName, int value) {
		getWidget().findProperty(propertyName).setValue(value);
	}
	
	/**
	 * Changes the value of the specified property
	 * 
	 * @param propertyName
	 *            the name of the property
	 * @param value
	 *            the new value of the property
	 */
	protected final void setPropertyValue(String propertyName, String value) {
		getWidget().setPropertyValue(propertyName, value);
	}	
	
    /**
     * @return DomainRepository
     */
    protected DomainRepository getDomainRepository() {
    	IOfsProject ofsProject = OfsResourceHelper.getOfsProject(getWidget().eResource());
        return DomainRepository.getInstance(ofsProject); 
    }
	
	
	/**
	 * @return the wrapped aggregate widget
	 */
	public final Widget getWidget() {
		return this.widget;
	}
	
}

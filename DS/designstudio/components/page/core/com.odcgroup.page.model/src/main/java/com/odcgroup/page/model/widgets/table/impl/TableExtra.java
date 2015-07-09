package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITableExtra;

/**
 * This is the reference implementation of ITableExtra, as a Widget Adapter.
 *
 * @author atr
 * @since DS 1.40.0
 */
public class TableExtra extends TableFeature implements ITableExtra {

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableFeature#getColumnName()
	 */
	public String getColumnName() {
		MdfModelElement element = getAttribute();
		return element != null ? element.getName() : "";
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableExtra#getAttribute()
	 */
	public MdfDatasetProperty getAttribute() {
		return (MdfDatasetProperty)getWidget().findDomainObject(getDomainRepository());
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableExtra#setAttribute(com.odcgroup.mdf.metamodel.MdfDatasetProperty)
	 */
	public void setAttribute(MdfDatasetProperty attribute) {
		getWidget().setPropertyValue(PropertyTypeConstants.DOMAIN_ATTRIBUTE, attribute.getName());
	}

	/**
	 * Constructs a new TableExtra Widget Adapter
	 * @param widget the adapted widget
	 */
	public TableExtra(Widget widget) {
		super(widget);
	}

}

package com.odcgroup.page.model.widgets.matrix.impl;

import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.model.widgets.table.impl.WidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public class MatrixExtra extends WidgetAdapter implements IMatrixExtra {
	
	private static final String AGGREGATION_TYPE = "aggregationType";

	/**
	 * Constructs a new MatrixExtra Widget Adapter
	 * @param widget the adapted widget
	 */
	public MatrixExtra(Widget widget) {
		super(widget);
	}

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

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtra#getAggregationType()
	 */
	@Override
	public String getAggregationType() {		
		return getWidget().getPropertyValue(AGGREGATION_TYPE);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtra#setAggregationType(java.lang.String)
	 */
	@Override
	public void setAggregationType(String type) {
		getWidget().setPropertyValue(AGGREGATION_TYPE, type);		
	}

}

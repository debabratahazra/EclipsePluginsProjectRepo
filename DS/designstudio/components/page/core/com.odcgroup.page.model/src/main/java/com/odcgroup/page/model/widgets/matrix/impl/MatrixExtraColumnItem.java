package com.odcgroup.page.model.widgets.matrix.impl;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;

/**
 * 
 * @author pkk
 *
 */
public class MatrixExtraColumnItem extends MatrixCellItem implements IMatrixExtraColumnItem {

	private static final String PROP_DISPLAYLASTROW = "displayInLastRow";
	
	/**
	 * @param widget
	 */
	protected MatrixExtraColumnItem(Widget widget) {
		super(widget);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IExtraColumnItem#displayLastRow()
	 */
	@Override
	public boolean displayLastRow() {
		return getProperty(PROP_DISPLAYLASTROW).getBooleanValue();
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getHorizontalAlignment()
	 */
	@Override
	public Property getHorizontalAlignment() {
		return getProperty(PropertyTypeConstants.HORIZONTAL_ALIGNMENT);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getVerticalAlignment()
	 */
	@Override
	public Property getVerticalAlignment() {
		return getProperty(PropertyTypeConstants.VERTICAL_ALIGNMENT);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem#getColumnComputation()
	 */
	@Override
	public Property getColumnComputation() {
		return getProperty("aggregationType");
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem#getDisplayFormat()
	 */
	public Property getDisplayFormat() {
		return getProperty("format");
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem#getCSSClass()
	 */
	public String getCSSClass() {
		return getPropertyValue(PropertyTypeConstants.CSS_CLASS);
	}

}

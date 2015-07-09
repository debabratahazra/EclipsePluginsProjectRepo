package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;

/**
 * This is the reference implementation of ITableKeepFilter, as a Widget Adapter.
 *
 * @author atr
 * @since DS 1.40.0
 */
public class TableKeepFilter extends TableFeature implements ITableKeepFilter {

	/** Name of the property that contains the column name */
	private static final String KEEP_FILTER_COLUMN_NAME_PROPERTY = "keep-filter-column-name";
	/** Name of the property that contains the operand */
	private static final String KEEP_FILTER_OPERAND_PROPERTY = "keep-filter-operand";
	/** Name of the property that contains the operator */
	private static final String KEEP_FILTER_OPERATOR_PROPERTY = "keep-filter-operator";
	
	/**
	 * Constructs a new TableKeepFilter Widget Adapter
	 * @param widget the adapted widget
	 */
	public TableKeepFilter(Widget widget) {
		super(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableKeepFilter#getColumnName()
	 */
	public String getColumnName() {
		return getPropertyValue(KEEP_FILTER_COLUMN_NAME_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableKeepFilter#getOperand()
	 */
	public String getOperand() {
		return getPropertyValue(KEEP_FILTER_OPERAND_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableKeepFilter#getOperator()
	 */
	public String getOperator() {
		return getPropertyValue(KEEP_FILTER_OPERATOR_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableKeepFilter#setColumnName(java.lang.String)
	 */
	public void setColumnName(String name) {
		setPropertyValue(KEEP_FILTER_COLUMN_NAME_PROPERTY, name);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableKeepFilter#setOperand(java.lang.String)
	 */
	public void setOperand(String operand) {
		setPropertyValue(KEEP_FILTER_OPERAND_PROPERTY, operand);
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableKeepFilter#setOperator(java.lang.String)
	 */
	public void setOperator(String operator) {
		setPropertyValue(KEEP_FILTER_OPERATOR_PROPERTY, operator);
	}
}

package com.odcgroup.page.model.widgets.table.impl;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITableSort;

/**
 * This is the reference implementation of ITableSort, as a Widget Adapter.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableSort extends TableFeature implements ITableSort {

	/** Name of the property that contains the column name */
	private static final String COLUMN_NAME_PROPERTY = "sort-column-name";
	/** Name of the property that contains the sorting direction */
	private static final String SORTING_DIRECTION_PROPERTY = "sort-direction";
	/** Name of the property that contains the rank */
	private static final String SORT_RANK_PROPERTY = "sort-rank";

	/**
	 * Constructs a new TableSort Widget Adapter
	 * 
	 * @param widget
	 *            the adapted widget
	 */
	public TableSort(Widget widget) {
		super(widget);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#getColumnName()
	 */
	public String getColumnName() {
		return getPropertyValue(COLUMN_NAME_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#getSortingDirection()
	 */
	public String getSortingDirection() {
		return getPropertyValue(SORTING_DIRECTION_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#getRank()
	 */
	public int getRank() {
		return getProperty(SORT_RANK_PROPERTY).getIntValue();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#isAscending()
	 */
	public boolean isAscending() {
		return "ascending".equals(getSortingDirection());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#isDescending()
	 */
	public boolean isDescending() {
		return "descending".equals(getSortingDirection());
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#setColumnName(java.lang.String)
	 */
	public void setColumnName(String name) {
		setPropertyValue(COLUMN_NAME_PROPERTY, name);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#setSortingDirection(java.lang.String)
	 */
	public void setSortingDirection(String direction) {
		setPropertyValue(SORTING_DIRECTION_PROPERTY, direction);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableSort#setRank(int)
	 */
	public void setRank(int newValue) {
		setPropertyValue(SORT_RANK_PROPERTY, newValue);
	}

}

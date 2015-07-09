package com.odcgroup.page.model.widgets.table;

import com.odcgroup.page.model.Widget;


/**
 * TODO: Document me!
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableFactory {
	
	/**
	 * @param widget a Table Aggregate widget
	 * @return a ITableAggregate adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableAggregate adaptTableAggregateWidget(Widget widget);

	/**
	 * @param widget a Table Column widget
	 * @return a ITableColumn adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableColumn adaptTableColumnWidget(Widget widget);
	
	/**
	 * @param widget a Table Column Item widget
	 * @return a ITableColumnItem adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableColumnItem adaptTableColumnItemWidget(Widget widget);
	
	/**
	 * @param templateType
	 * @return a new table column item populated by the template
	 */
	ITableColumnItem createTableColumnItem(String templateType);

	/**
	 * @param widget a Table Extra widget
	 * @return a ITableExtra adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableExtra adaptTableExtraWidget(Widget widget);

	/**
	 * @param widget a Table Group widget
	 * @return a ITableGroup adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableGroup adaptTableGroupWidget(Widget widget);

	/**
	 * @param widget a Table KeepFilter widget
	 * @return a ITableKeepFilter adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableKeepFilter adaptTableKeepFilterWidget(Widget widget);

	/**
	 * @param widget a Table Sort widget
	 * @return a ITableSort adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITableSort adaptTableSortWidget(Widget widget);

	/**
	 * @param widget a Table widget
	 * @return a ITable adapter 
	 * 
	 * @throws IllegalArgumentException if the widget is not a table widget
	 */
	ITable adaptTableWidget(Widget widget);
	
	/**
	 * @return a new instance of ITable
	 */
	ITable createTable();
	
	/**
	 * @return a new instance of ITableAggregate
	 */
	ITableAggregate createTableAggregate();
	
	/**
	 * @return a new instance of ITableColumn
	 */
	ITableColumn createTableColumn();
	
	/**
	 * @param templateType
	 * @return a new instance of ITableColumn
	 */
	ITableColumn createTableColumn(String templateType);

	/**
	 * @return a new instance of ITableExtra
	 */
	ITableExtra createTableExtra();

	/**
	 * @return a new instance of ITableGroup
	 */
	ITableGroup createTableGroup();

	/**
	 * @return a new instance of ITableKeepFilter
	 */
	ITableKeepFilter createTableKeepFilter();

	/**
	 * @return a new instance of ITableSort.
	 */
	ITableSort createTableSort();

}

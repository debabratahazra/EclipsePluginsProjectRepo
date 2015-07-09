package com.odcgroup.page.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableAggregate;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.model.widgets.table.ITableExtra;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.model.widgets.table.ITableUtilities;
import com.odcgroup.page.model.widgets.table.impl.TableUtilities;

/**
 * TODO: Document me!
 *
 * @author atr
 * @since DS 1.40.0
 */
public class TableHelper {
	
	/** the utilities for Table*** widget adapters */
	private static ITableUtilities UTILITIES = new TableUtilities();
	
	/**
	 * Changes the table utilities
	 * @param utilities the new utilities
	 */
	public static void setTableUtilities(ITableUtilities utilities) {
		TableHelper.UTILITIES = utilities;
	}

	/**
	 * @return the table utilities
	 */
	public static ITableUtilities getTableUtilities() {
		return TableHelper.UTILITIES;
	}
	
	/**
	 * @param widget the widget to be adapted
	 * @return a ITable adapter given a table widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table widget
	 */
	public static ITable getTable(Widget widget) {
		return getTableUtilities().getFactory().adaptTableWidget(widget);
	}

	/**
	 * @param widget the widget to be adapted
	 * @return a ITableColumn adapter given a table-column widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-column widget
	 */
	public static ITableColumn getTableColumn(Widget widget) {
		return getTableUtilities().getFactory().adaptTableColumnWidget(widget);
	}
	
	/**
	 * @param widget the widget to be adapted
	 * @return a ITableColumnItem adapter given a table-column-item widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-column-item widget
	 */
	public static ITableColumnItem getTableColumnItem(Widget widget) {
		return getTableUtilities().getFactory().adaptTableColumnItemWidget(widget);	
	}
	
	/**
	 * @param widget the widget to be adapted
	 * @return a ITableGroup adapter given a table-group widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-group widget
	 */
	public static ITableGroup getTableGroup(Widget widget) {
		return getTableUtilities().getFactory().adaptTableGroupWidget(widget);
	}
	
	/**
	 * @param widget the widget to be adapted
	 * @return a ITableAggregate adapter given a table-aggregate widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-aggregate widget
	 */
	public static ITableAggregate getTableAggreage(Widget widget) {
		return getTableUtilities().getFactory().adaptTableAggregateWidget(widget);
	}	
	
	/**
	 * @param widget the widget to be adapted
	 * @return a ITableSort adapter given a table-sort widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-sort widget
	 */
	public static ITableSort getTableSort(Widget widget) {
		return getTableUtilities().getFactory().adaptTableSortWidget(widget);
	}	
	
	/**
	 * @param widget the widget to be adapted
	 * @return a ITableKeepFilter adapter given a table-keep-filter widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-keep-filter widget
	 */
	public static ITableKeepFilter getTableKeepFilter(Widget widget) {
		return getTableUtilities().getFactory().adaptTableKeepFilterWidget(widget);
	}	

	/**
	 * @param widget the widget to be adapted
	 * @return a ITableExtra adapter given a table-extra widget
	 * 
	 * @throws IllegalArgumentException if the given widget is not a table-extra widget
	 */
	public static ITableExtra getTableExtra(Widget widget) {
		return getTableUtilities().getFactory().adaptTableExtraWidget(widget);
	}	
	
	/**
	 * @param columnName
	 * @param direction
	 * @param sortRank
	 * @return ITableSort
	 */
	public static ITableSort createTableSort(String columnName, String direction, int sortRank) {
		ITableSort tableSort = getTableUtilities().getFactory().createTableSort();
		tableSort.setColumnName(columnName);
		tableSort.setSortingDirection(direction);
		tableSort.setRank(sortRank);
		return tableSort;
	}
	
	/**
	 * @param table
	 * @return list
	 */
	public static List<String> getDomainBoundColumnNames(ITable table) {
		List<ITableColumn> columns = table.getColumns();
		List<String> names = new ArrayList<String>();
		for (ITableColumn column : columns) {
			if (column.isBoundToDomain()) {
				names.add(column.getColumnName());
			}
		}
		return names;
	}
	
	/**
	 * @param table
	 * @return list
	 */
	public static List<String> getComputedColumnNames(ITable table) {
		List<ITableColumn> columns = table.getColumns();
		List<String> names = new ArrayList<String>();
		for (ITableColumn column : columns) {
			if (column.isComputed()) {
				names.add(column.getColumnName());
			}
		}
		return names;		
	}
	
	/**
	 * @param table
	 * @return list
	 */
	public static List<String> getDynamicColumnNames(ITable table) {
		List<ITableColumn> columns = table.getColumns();
		List<String> names = new ArrayList<String>();
		for (ITableColumn column : columns) {
			if (column.isDynamic()) {
				names.add(column.getColumnName());
			}
		}
		return names;
	}
	
	/**
	 * @param table
	 * @return list
	 */
	public static List<String> getPlaceholderColumnNames(ITable table) {
		List<ITableColumn> columns = table.getColumns();
		List<String> names = new ArrayList<String>();
		for (ITableColumn column : columns) {
			if (column.isPlaceHolder()) {
				names.add(column.getColumnName());
			}
		}
		return names;
	}
	
	/**
	 * @param columnName
	 * @param operator
	 * @param value
	 * @return ITableKeepFilter
	 */
	public static ITableKeepFilter createTableKeepFilter(String columnName, String operator, String value) {
		ITableKeepFilter keepFilter = getTableUtilities().getFactory().createTableKeepFilter();
		keepFilter.setColumnName(columnName);
		keepFilter.setOperator(operator);
		keepFilter.setOperand(value);
		return keepFilter;
	}
	
	/**
	 * @param table
	 * @return rank
	 */
	public static int getNextSortRank(ITable table) {
		if (table.getSorts().size() == 0) {
			return 1;
		}
		List<ITableSort> sorts = new ArrayList<ITableSort>();
		sorts.addAll(table.getSorts());
		ITableSort maxSort = Collections.max(sorts, new Comparator<ITableSort>(){
			public int compare(ITableSort arg0, ITableSort arg1) {
				int r1 = arg0.getRank();
				int r2 = arg1.getRank();
				return r1 > r2 ? 1 : -1;
			}
			
		});		
		return maxSort.getRank()+1;
	}
	
	/**
	 * @param table
	 * @return rank
	 */
	public static int getNextGroupRank(ITable table) {
		if (table.getGroups().size() == 0) {
			return 1;
		}
		List<ITableGroup> groups = new ArrayList<ITableGroup>();
		groups.addAll(table.getGroups());
		ITableGroup maxSort = Collections.max(groups, new Comparator<ITableGroup>(){
			public int compare(ITableGroup arg0, ITableGroup arg1) {
				int r1 = arg0.getRank();
				int r2 = arg1.getRank();
				return r1 > r2 ? 1 : -1;
			}
			
		});		
		return maxSort.getRank()+1;		
	}
	
	/**
	 * @param table
	 * @param selection
	 * @return tablesort
	 */
	public static ITableSort getSortBeforeByRank(ITable table, ITableSort selection) {
		List<ITableSort> sorts = new ArrayList<ITableSort>();
		for(ITableSort sort : table.getSorts()) {
			if (sort.getRank()< selection.getRank()) {
				sorts.add(sort);
			}
		}

		if (sorts.size()== 1) {
			return sorts.get(0);
		} else if (sorts.size() == 0) {
			return selection;
		}
		ITableSort maxSort = Collections.max(sorts, new Comparator<ITableSort>(){
			public int compare(ITableSort arg0, ITableSort arg1) {
				int r1 = arg0.getRank();
				int r2 = arg1.getRank();
				return r1 > r2 ? 1 : -1;
			}
			
		});		
		return maxSort;
	}
	
	/**
	 * @param table
	 * @param selection
	 * @return group
	 */
	public static ITableGroup getGroupBeforeByRank(ITable table, ITableGroup selection) {
		List<ITableGroup> groups = new ArrayList<ITableGroup>();
		for(ITableGroup group : table.getGroups()) {
			if (group.getRank()< selection.getRank()) {
				groups.add(group);
			}
		}

		if (groups.size()== 1) {
			return groups.get(0);
		} else if (groups.size() == 0) {
			return selection;
		}
		ITableGroup maxGroup = Collections.max(groups, new Comparator<ITableGroup>(){
			public int compare(ITableGroup arg0, ITableGroup arg1) {
				int r1 = arg0.getRank();
				int r2 = arg1.getRank();
				return r1 > r2 ? 1 : -1;
			}
			
		});		
		return maxGroup;		
	}
	
	/**
	 * @param table
	 * @param selection
	 * @return ITableSort
	 */
	public static ITableSort getSortAfterByRank(ITable table, ITableSort selection) {
		List<ITableSort> sorts = new ArrayList<ITableSort>();
		for(ITableSort sort : table.getSorts()) {
			if (sort.getRank()> selection.getRank()) {
				sorts.add(sort);
			}
		}
		if (sorts.size()== 1) {
			return sorts.get(0);
		} else if (sorts.size() == 0) {
			return selection;
		}
		ITableSort minSort = Collections.min(sorts, new Comparator<ITableSort>(){
			public int compare(ITableSort arg0, ITableSort arg1) {
				int r1 = arg0.getRank();
				int r2 = arg1.getRank();
				return r1 > r2 ? 1 : -1;
			}
			
		});		
		return minSort;
	}
	
	/**
	 * @param table
	 * @param selection
	 * @return group
	 */
	public static ITableGroup getGroupAfterByRank(ITable table, ITableGroup selection) {
		List<ITableGroup> groups = new ArrayList<ITableGroup>();
		for(ITableGroup sort : table.getGroups()) {
			if (sort.getRank()> selection.getRank()) {
				groups.add(sort);
			}
		}
		if (groups.size()== 1) {
			return groups.get(0);
		} else if (groups.size() == 0) {
			return selection;
		}
		ITableGroup minGroup = Collections.min(groups, new Comparator<ITableGroup>(){
			public int compare(ITableGroup arg0, ITableGroup arg1) {
				int r1 = arg0.getRank();
				int r2 = arg1.getRank();
				return r1 > r2 ? 1 : -1;
			}
			
		});		
		return minGroup;
	}

	/**
	 * This class cannot be instantiated
	 */
	private TableHelper() {
	}
	
}

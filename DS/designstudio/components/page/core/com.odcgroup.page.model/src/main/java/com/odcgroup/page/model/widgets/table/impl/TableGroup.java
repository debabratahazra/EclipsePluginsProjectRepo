package com.odcgroup.page.model.widgets.table.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.model.widgets.table.ITableGroup;

/**
 * This is the reference implementation of ITableGroup, as a Widget Adapter.
 *
 * @author atr
 * @author scn
 * @since DS 1.40.0
 */
public class TableGroup extends TableFeature implements ITableGroup {

	private final String DISPLAY_FORMAT_PROPERTY = "format";

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getColumnName()
	 */
	public String getColumnName() {
		return getPropertyValue(GROUP_COLUMN_NAME_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getEvents()
	 */
	public List<Event> getEvents() {
		return getWidget().getEvents();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getMaxGrouping()
	 */
	public int getMaxGrouping() {
		return getProperty(GROUP_MAX_PROPERTY).getIntValue();
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getToolTip()
	 */
	public Property getToolTip() {
		return getProperty(GROUP_TOOLTIP_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getRank()
	 */
	public int getRank() {
		return getProperty(GROUP_RANK_PROPERTY).getIntValue();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getSortingColumnName()
	 */
	public String getSortingColumnName() {
		return getPropertyValue(GROUP_SORTING_COLUMN_NAME_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getSortingDirection()
	 */
	public String getSortingDirection() {
		return getPropertyValue(GROUP_SORTING_DIRECTION_PROPERTY);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isAscending()
	 */
	public boolean isAscending() {
		return "ascending".equals(getSortingDirection());
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isDescending()
	 */
	public boolean isDescending() {
		return "descending".equals(getSortingDirection());
	}	
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setColumnName(java.lang.String)
	 */
	public void setColumnName(String newValue) {
		setPropertyValue(GROUP_COLUMN_NAME_PROPERTY, newValue);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setRank(int)
	 */
	public void setRank(int newValue) {
		setPropertyValue(GROUP_RANK_PROPERTY, newValue);
		if (newValue != 1) {
			setHierarchy(false);
		}
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setMaxGrouping(int)
	 */
	public void setMaxGrouping(int newValue) {
		setPropertyValue(GROUP_MAX_PROPERTY, newValue);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setSortingColumnName(java.lang.String)
	 */
	public void setSortingColumnName(String newValue) {
		setPropertyValue(GROUP_SORTING_COLUMN_NAME_PROPERTY, newValue);
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setSortingDirection(java.lang.String)
	 */
	public void setSortingDirection(String newValue) {
		setPropertyValue(GROUP_SORTING_DIRECTION_PROPERTY, newValue);
	}

	/**
	 * Constructs a new TableGroup Widget Adapter
	 * @param widget the adapted widget
	 */
	public TableGroup(Widget widget) {
		super(widget);
	}


	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isCollapsed()
	 */
	public boolean isCollapsed() {
		if(getProperty(GROUP_COLLAPSED) != null)
			return getProperty(GROUP_COLLAPSED).getBooleanValue();
		return false;
	}
	
	/**
	 * @param collapsed
	 */
	public void setCollapsed(boolean collapsed) {
		getProperty(GROUP_COLLAPSED).setValue(collapsed);
	}

	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isHierarchy()
	 */
	public boolean isHierarchy() {
		return getProperty(GROUP_HIERARCHY).getBooleanValue();
	}

	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setHierarchy(boolean)
	 */
	public void setHierarchy(boolean hierarchy) {
		getProperty(GROUP_HIERARCHY).setValue(hierarchy);		
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isUsedForDynamicColumn()
	 */
	public boolean isUsedForDynamicColumn() {
		return getProperty(COLUMN_DYNAMIC_COLUMN).getBooleanValue();
	}
	
	/**
	 * @param value
	 */
	public void setUsedForDynamicColumn(boolean value) {
		getProperty(COLUMN_DYNAMIC_COLUMN).setValue(value);
	}	

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isAggregatedData()
	 */
	public boolean isAggregatedData() {
		return getProperty(GROUP_DATA_AGGR).getBooleanValue();
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setAggregatedData(boolean)
	 */
	public void setAggregatedData(boolean aggregate) {
		getProperty(GROUP_DATA_AGGR).setValue(aggregate);		
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#getEventLevel()
	 */
	public String getEventLevel() {
		String value = getPropertyValue(EVENT_LEVEL);
		if (StringUtils.isEmpty(value)) {
			value = NODELEAFLEVEL_EVENT;
		}
		return value;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#setEventLevel(java.lang.String)
	 */
	public void setEventLevel(String level) {
		Property property = getProperty(EVENT_LEVEL);
		if (property != null) {
			property.setValue(level);
		} else {
			property = ModelFactory.eINSTANCE.createProperty();
			property.setTypeName(EVENT_LEVEL);
			property.setLibraryName("xgui");
			property.setValue(level);
			getWidget().getProperties().add(property);
		}
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isLeafLevelEvent()
	 */
	public boolean isLeafLevelEvent() {
		return getEventLevel().equals(LEAFLEVEL_EVENT) ? true : false;
	}

	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableGroup#isNodeLeafLevelEvent()
	 */
	public boolean isNodeLeafLevelEvent() {
		return getEventLevel().equals(NODELEAFLEVEL_EVENT) ? true : false;
	}

	@Override
	public ITableColumn getTableColumn() {
		return TableHelper.getTableColumn(getWidget().getParent());
	}

	@Override
	public boolean canMoveUp() {
		return !isFirst();
	}

	@Override
	public boolean canMoveDown() {
		return !isLast();
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isFirst()
	 */
	public boolean isFirst() {
		List<ITableGroup> groups = getTable(this).getGroupsByRank();
		return getRank() == groups.get(0).getRank();
	}
	
	/**
	 * @see com.odcgroup.page.model.widgets.table.ITableColumn#isLast()
	 */
	public boolean isLast() {
		List<ITableGroup> groups = getTable(this).getGroupsByRank();
		return getRank() == groups.get(groups.size() - 1).getRank();
	}

	@Override
	public boolean canMoveRight() {
		return !isColumnLast() && isColumnRightMovable();
	}

	private boolean isColumnLast() {
		boolean status = false;
		List<ITableColumn> colList = getTable(this).getColumns();
		if(getWidget().getParent().getTypeName().equals("TableColumn")) {
			ITableColumn column = TableHelper.getTableColumn(getWidget().getParent());
			if(column.getColumnIndex() == colList.size() - 1)
				status = true;
		}
		return status;
	}

	@Override
	public boolean canMoveLeft() {
		return !isColumnFirst() && isColumnLeftMovable();
	}

	private boolean isColumnRightMovable() {
		if(getWidget().getParent().getTypeName().equals("TableColumn")) {
			ITableColumn column = TableHelper.getTableColumn(getWidget().getParent());
			if(getTable(this).getColumn(column.getColumnIndex() + 1) != null) {
				ITableColumn tabColumn = getTable(this).getColumn(column.getColumnIndex() + 1); 
				if(tabColumn.isPlaceHolder() || tabColumn.isDomain()) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isColumnLeftMovable() {
		if(getWidget().getParent().getTypeName().equals("TableColumn")) {
			ITableColumn column = TableHelper.getTableColumn(getWidget().getParent());
			if(getTable(this).getColumn(column.getColumnIndex() - 1) != null) {
				ITableColumn tabColumn = getTable(this).getColumn(column.getColumnIndex() - 1); 
				if(tabColumn.isPlaceHolder() || tabColumn.isDomain()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isColumnFirst() {
		boolean status = false;
		if(getWidget().getParent().getTypeName().equals("TableColumn")) {
			ITableColumn column = TableHelper.getTableColumn(getWidget().getParent());
			if(column.getColumnIndex() == 0)
				status = true;
		}
		return status;
	}

	@Override
	public boolean isPartOfTheFilter() {
		Property prop = getProperty(GROUP_IS_PART_OF_FILTER);
		if (prop != null) {
			return prop.getBooleanValue();
		}
		return true;
	}

	@Override
	public Property getDisplayFormat() {
		return getProperty(DISPLAY_FORMAT_PROPERTY);
	}
	
}

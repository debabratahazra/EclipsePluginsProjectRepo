package com.odcgroup.page.model.widgets.table;

import java.util.List;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;

/**
 * The interface ITableGroup provides services to ease the management of a Group
 * in a Table Widget. In practice it will be used to implements specific UI &
 * related commands, and also to implements specific PSM transformers.
 * <p>
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableGroup extends ITableFeature, IWidgetAdapter {
	
	/** */
	String GROUP_COLUMN_NAME_PROPERTY = "group-column-name";
	/** */
	String GROUP_MAX_PROPERTY = "group-max";
	/** */
	String GROUP_RANK_PROPERTY = "group-rank";
	/** */
	String GROUP_TOOLTIP_PROPERTY = "tooltip";
	/** */
	String GROUP_SORTING_COLUMN_NAME_PROPERTY = "group-sorting-column-name";
	/** */
	String GROUP_SORTING_DIRECTION_PROPERTY = "group-sorting-direction";	
	/** */
	String GROUP_HIERARCHY = "hierarchy";
	/** */
	String COLUMN_DYNAMIC_COLUMN = "group-dynamic-column";
	/** */
	String GROUP_DATA_AGGR = "aggregateData";
	/** */
	String EVENT_LEVEL = "eventLevel";
	/** */
	String LEAFLEVEL_EVENT = "onlyLeaf";
	/** */
	String NODELEAFLEVEL_EVENT = "all";
	
	String GROUP_COLLAPSED = "collapsed";
	
	String GROUP_IS_PART_OF_FILTER = "group-is-part-of-filter";

	/**
	 * @return the list of events for this group
	 */
	List<Event> getEvents();

	/**
	 * @return the value of the property max-grouping
	 */
	int getMaxGrouping();

	/**
	 * @return the rank of this group
	 */
	int getRank();
	
	/**
	 * @return the toolTip of this group
	 */
	Property getToolTip();

	/**
	 * @return the name of the column for the sort
	 */
	String getSortingColumnName();

	/**
	 * @return the sorting direction
	 */
	String getSortingDirection();
	
	/**
	 * @return {@code true} if the sorting direction is 'ascending', otherwise
	 *         it returns {@code false}
	 */
	boolean isAscending();

	/**
	 * @return {@code true} if the sorting direction is 'descending', otherwise
	 *         it returns {@code false}
	 */
	boolean isDescending();	
	
	/**
	 * @return
	 */
	boolean isPartOfTheFilter();

	/**
	 * @param newValue
	 */
	void setColumnName(String newValue);

	/**
	 * @param newValue
	 */
	void setMaxGrouping(int newValue);

	/**
	 * @param newValue
	 */
	void setRank(int newValue);

	/**
	 * @param newValue
	 */
	void setSortingColumnName(String newValue);

	/**
	 * @param newValue
	 */
	void setSortingDirection(String newValue);
	
	/**
	 * @return {@code true} if the user can click on the column to expand it, 
	 *         otherwise it returns {@code false}
	 */
	boolean isCollapsed();
	
	/**
	 * @param collapsed
	 */
	public void setCollapsed(boolean collapsed);
	
	/**
	 * @return {@code true} if the group is hierarchy otherwise {@code false}
	 */
	boolean isHierarchy();
	
	/**
	 * @param hierarchy
	 */
	public void setHierarchy(boolean hierarchy);
	
	/**
	 * @param value
	 */
	public void setUsedForDynamicColumn(boolean value);
	
	
	/**
	 * @return {@code true} if the group is used for dynamic colunn otherwise {@code false}
	 */
	boolean isUsedForDynamicColumn();	
	
	/**
	 * @param aggregate
	 */
	public void setAggregatedData(boolean aggregate);
	
	/**
	 * @return whether aggregated data or raw
	 */
	public boolean isAggregatedData();
	
	/**
	 * @return the event level
	 */
	public String getEventLevel();
	
	/**
	 * @param level
	 */
	public void setEventLevel(String level);
	
	/**
	 * @return leafLevel event
	 */
	public boolean isLeafLevelEvent();
	
	/**
	 * @return nodeleaflevel event
	 */
	public boolean isNodeLeafLevelEvent();
	
	/**
	 * @return parent tablecolumn
	 */
	public ITableColumn getTableColumn();
	
	/**
	 * @return moving up condition
	 */
	public boolean canMoveUp();
	
	/**
	 * @return moving down condition
	 */
	public boolean canMoveDown();
	
	/**
	 * @return moving up condition
	 */
	public boolean canMoveRight();
	
	/**
	 * @return moving down condition
	 */
	public boolean canMoveLeft();
	
	/**
	 * @return display format
	 */
	public Property getDisplayFormat();
}

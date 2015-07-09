package com.odcgroup.page.model.widgets.matrix;

import java.util.List;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.table.IWidgetAdapter;

/**
 *
 * @author pkk
 *
 */
public interface IMatrixAxis extends IWidgetAdapter {	
	
	/**
	 * @return is x-axis
	 */
	boolean isXAxis();
	/**
	 * @return is y-axis
	 */
	boolean isYAxis();

	/**
	 * @return the domain attribute for the group
	 */
	Property getDomainAttribute();
	
	/**
	 * @return the value of the property display-format
	 */
	Property getDisplayFormat();	
	
	/**
	 * @return events defined for the group
	 */
	List<Event> getEvents();

	/**
	 * @return the name of the column for the sort
	 */
	String getSortingColumnName();
	
	/**
	 * 
	 */
	void setSortingColumnName(String columnName);

	/**
	 * @return the sorting direction
	 */
	String getSortingDirection();
	
	/**
	 * @param direction
	 */
	void setSortingDirection(String direction);
	
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
	int getMaxGrouping();
	
	/**
	 * @return
	 */
	IMatrix getParent();	
	
	/**
	 * @return
	 */
	String  getHorizontalAlignment();
	
	/**
	 * @return
	 */
	boolean isDefaultHorizontalAlignment();

}

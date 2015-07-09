package com.odcgroup.page.model.widgets.table;


/**
 * The interface ITableAggregate provides services to ease the management of an
 * Aggregate in a Table Widget. In practice it will be used to implements specific
 * UI & related commands, and also to implements specific PSM transformers.
 * <p>
 *  
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableAggregate extends ITableFeature, IWidgetAdapter {

	/**
	 * Adds new group names to the aggregation. Do nothing if a name is
	 * already registered
	 * 
	 * @param names
	 *            the group names to be added
	 */
	void addGroupNames(String... names);

	/**
	 * @return the value of the computation attribute
	 */
	String getComputation();

	/**
	 * @return an array of all registered group names
	 */
	String[] getGroupNames();

	/**
	 * @return the name of the other column
	 */
	String getOtherColumnName();
	
	/**
	 * @param name of the group
	 * @return true if this aggregate contains the given group name
	 */
	boolean hasGroupName(String name);

	/**
	 * Removes the specified group names from this aggregation. Do nothing if a
	 * name is not found
	 * 
	 * @param names
	 *            the group names to be removed
	 */
	void removeGroupNames(String... names);

	/**
	 * Changes the name of the aggregated column
	 * 
	 * @param name
	 */
	void setColumnName(String name);

	/**
	 * Changes the value computation attribute
	 * 
	 * @param value
	 *            the new value
	 */
	void setComputation(String value);

	/**
	 * Changes the name of the other column
	 * 
	 * @param name
	 */
	void setOtherColumn(String name);

}

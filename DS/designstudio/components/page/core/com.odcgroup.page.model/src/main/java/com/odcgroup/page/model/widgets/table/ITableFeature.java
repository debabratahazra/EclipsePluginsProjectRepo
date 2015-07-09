package com.odcgroup.page.model.widgets.table;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public interface ITableFeature {
	
	/**
	 * @return the name of the column  
	 */
	String getColumnName();
	
	
	/**
	 * @return the table that owns this feature
	 */
	ITable getTable();
	
	/**
	 * @return the table that owns this feature
	 */
	ITable getTable(ITableGroup group);
	
}

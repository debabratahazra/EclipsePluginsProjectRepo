/**
 * 
 */
package com.odcgroup.workbench.ui.preferences.table;


/**
 * Table Descriptor
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableDescriptor {
	
	/**
	 * @param name
	 * @return TableColumnDescriptor
	 */
	TableColumnDescriptor getColumn(String name);
	
	/**
	 * @return the number of columns
	 */
	int getColumnCount();
	
	/**
	 * @return the column names
	 */
	String[] getColumnNames();
	
	/**
	 * @return the table column descriptors
	 */
	TableColumnDescriptor[] getColumns();
	
	/**
	 * @return heightHint
	 */
	int getHeightHint();

	/**
	 * @return the style
	 */
	int getStyle();
	
	/**
	 * @return headerVisible
	 */
	boolean isHeaderVisible();

	/**
	 * @return linesVisible
	 */
	boolean isLinesVisible();

	/**
	 * @param visible
	 */
	void setHeaderVisible(boolean visible);

	/**
	 * @param heightHint
	 */
	void setHeightHint(int heightHint);

	/**
	 * @param visible
	 */
	void setLinesVisible(boolean visible);

	/**
	 * @param style the style to set
	 */
	void setStyle(int style);

}

package com.odcgroup.page.model.widgets.table;

/**
 * DS-2620 : Allow multiple item in a column
 *
 * @author pkk
 *
 */
public interface ITableColumnItem extends IWidgetAdapter {
	

	/**
	 * @return the name of this column
	 */
	String getColumn();
	
	/**
	 * @param column
	 */
	void setColumn(String column);
	
	/**
	 * return the enclosing table column
	 */
	ITableColumn getTableColumn();	
	
	/**
	 * @return {@code true} if the item belongs to a new line, 
	 *         otherwise it returns {@code false}
	 */
	boolean isNewLine();
	
	/**
	 * @return the item width in percentage
	 */
	String getItemPercentageWidth();
	
	/**
	 * @return the value of the property display-format
	 */
	String getDisplayFormat();
	
	/**
	 * @return
	 */
	String getHorizontalAlignment();


}

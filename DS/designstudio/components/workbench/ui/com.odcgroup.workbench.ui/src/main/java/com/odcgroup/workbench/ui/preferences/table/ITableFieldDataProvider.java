package com.odcgroup.workbench.ui.preferences.table;

import org.eclipse.swt.graphics.Image;

/**
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableFieldDataProvider {
	
	/**
	 * @param name
	 * @return Object[]
	 */
	Object[] load(String name);

	/**
	 * @param preferenceName
	 * @return Object[]
	 */
	Object[] loadDefault(String preferenceName);

	/**
	 * @param preferenceName
	 * @param data
	 */
	void store(String preferenceName, Object[] data);

	/**
	 * @param element
	 * @param name
	 * @return
	 */
	Image getImage(Object data, String columnName);

	/**
	 * @param data
	 * @param columnName
	 * @return Object
	 */
	Object getValue(Object data, String columnName);

	/**
	 * @param data
	 * @param columnName
	 * @param value
	 */
	void setValue(Object data, String columnName, Object value);

}

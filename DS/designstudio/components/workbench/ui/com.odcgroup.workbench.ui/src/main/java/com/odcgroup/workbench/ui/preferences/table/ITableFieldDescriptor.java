package com.odcgroup.workbench.ui.preferences.table;


/**
 * The descriptor for a TableFieldEditor
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface ITableFieldDescriptor extends ITableDescriptor {
	
	
	/**
	 * @return ITableFieldDataProvider
	 */
	ITableFieldDataProvider getDataProvider();
	
	/**
	 * @param dataProvider
	 */
	void setDataProvider(ITableFieldDataProvider dataProvider);

}

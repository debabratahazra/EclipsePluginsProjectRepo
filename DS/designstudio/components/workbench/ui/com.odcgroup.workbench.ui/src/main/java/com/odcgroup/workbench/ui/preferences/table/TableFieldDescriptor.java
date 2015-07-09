package com.odcgroup.workbench.ui.preferences.table;


/**
 * The Table Field Descriptor 
 * @author atr
 * @since DS 1.40.0
 */
public class TableFieldDescriptor extends TableDescriptor implements ITableFieldDescriptor {

	/** */
	private ITableFieldDataProvider dataProvider;

	/**
	 * @see com.odcgroup.page.transformmodel.preferences.ITableFieldDescriptor#getDataProvider()
	 */
	public final ITableFieldDataProvider getDataProvider() {
		return dataProvider;
	}

	/**
	 * @see com.odcgroup.page.transformmodel.preferences.ITableFieldDescriptor#setDataProvider(com.odcgroup.page.transformmodel.preferences.ITableFieldDataProvider)
	 */
	public void setDataProvider(ITableFieldDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	/**
	 * @param columns
	 */
	public TableFieldDescriptor(TableColumnDescriptor... columns) {
		super(columns);
	}


}

package com.odcgroup.page.ui.properties.table.tab;

import org.eclipse.swt.custom.CTabFolder;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.model.widgets.table.ITableSort;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 * The class {@code TableSortTab} shows the sorts defined in the table.
 * 
 * @author pkk
 * @since DS.140.0
 */
public class TableSortTab extends TransformTableTab {
	
	


	/**
	 * @param parent
	 * @param style
	 * @param label
	 * @param sortable
	 */
	public TableSortTab(CTabFolder parent, int style, String label, boolean sortable) {
		super(parent, style, label, sortable);
	}


	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.TransformTableTab#createTabControl(org.eclipse.swt.custom.CTabFolder, int)
	 */
	@Override
	protected ListControl<ITableSort, ITable> createTabControl(CTabFolder parent, int style) {
		return new TableSortSection(parent, style, isSortable());
	}

}
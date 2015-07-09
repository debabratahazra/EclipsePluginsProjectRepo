package com.odcgroup.page.ui.properties.table.tab;

import org.eclipse.swt.custom.CTabFolder;

import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.ui.properties.table.controls.ListControl;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class TableExtraTab extends TransformTableTab {

	/**
	 * @param parent
	 * @param style
	 * @param label
	 * @param sortable
	 */
	public TableExtraTab(CTabFolder parent, int style, String label, boolean sortable) {
		super(parent, style, label, sortable);
	}

	/**
	 * (non-Javadoc)
	 * @see com.odcgroup.page.ui.properties.table.tab.TransformTableTab#createTabControl(org.eclipse.swt.custom.CTabFolder, int)
	 */
	@Override
	protected ListControl<?, ITable> createTabControl(CTabFolder parent, int style) {
		return new TableExtraSection(parent, style, isSortable());
	}
}

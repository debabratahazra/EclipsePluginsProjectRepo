package com.odcgroup.page.ui.edit.table;

import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * The class {@code TableColumnWidgetEditPart} implements special behaviors for 
 * TableColumn Widget.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableColumnWidgetEditPart extends WidgetEditPart {

	/**
	 * @return the Table Widget Adapter
	 */
	protected ITableColumn getTableColumn() {
		return TableHelper.getTableColumn(getWidget());
	}
}

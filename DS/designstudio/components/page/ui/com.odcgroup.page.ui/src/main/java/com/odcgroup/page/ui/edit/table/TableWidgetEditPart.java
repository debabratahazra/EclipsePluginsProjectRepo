package com.odcgroup.page.ui.edit.table;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.impl.WidgetImpl;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITable;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * The class {@code TableWidgetEditPart} implements special behaviors for Table
 * Widget.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class TableWidgetEditPart extends WidgetEditPart {

	/**
	 * @return the Table Widget Adapter
	 */
	protected ITable getTable() {
		return TableHelper.getTable(getWidget());
	}

	/**
	 * Gets the children of this Widget.
	 * 
	 * @return List The children of this Widget
	 */
	@SuppressWarnings({ "rawtypes" })
	protected List getModelChildren() {
		return getTable().getColumnWidgets();
	}
}

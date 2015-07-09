package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.MorphToCalendarCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Action triggered to replace the current TableColumnEditableItem subclass by a TableColumnCalendarItem.
 */
public class TableColumnItemMorphToCalendarAction extends AbstractTableColumnItem {
	
	public TableColumnItemMorphToCalendarAction(ActionParameters parameters, WidgetEditPart editPart) {
		super(parameters, editPart.getViewer());
		setImageDescriptor(PageUIPlugin.getImageDescriptor("/icons/obj16/calendar.png"));
		setEnabled(calculateEnabled());
	}
	
	public void run() {
		MorphToCalendarCommand command = new MorphToCalendarCommand(getSelectedWidget(), getParameters().getWidgetTemplateName());
		execute(command);
		selectWidget(command.getSelectedWidget());
	}
	
	protected boolean calculateEnabled() {
		Widget widget = getSelectedWidget();		
		if (!isRegularModule(widget)) {
			return false;
		}
		if (!isDomainTableColumn(widget)) {
			return false;
		}
		String wt = widget.getTypeName();
		return !wt.equals(WidgetTypeConstants.TABLE_COLUMN_CALENDAR_ITEM);
	}

}

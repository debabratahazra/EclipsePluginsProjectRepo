package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.MorphToValueCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Action triggered to replace the current TableColumnEditableItem subclass by a TableColumnItem.
 */
public class TableColumnItemMorphToValueAction extends AbstractTableColumnItem {

	public TableColumnItemMorphToValueAction(ActionParameters parameters, WidgetEditPart editPart) {
		super(parameters, editPart.getViewer());
		setEnabled(calculateEnabled());
	}
	
	public void run() {
		MorphToValueCommand command = new MorphToValueCommand(getSelectedWidget(), getParameters().getWidgetTemplateName());
		execute(command);
		selectWidget(command.getSelectedWidget());
	}

	protected boolean calculateEnabled() {
		Widget widget = getSelectedWidget();
		if (!isDomainTableColumn(widget)) {
			return false;
		}
		String wt = widget.getTypeName();
		return !wt.equals("TableColumnItem");
	}

}

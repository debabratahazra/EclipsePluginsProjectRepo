package com.odcgroup.page.ui.action.table;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.MorphToSearchCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Action triggered to replace the current TableColumnEditableItem subclass by a TableColumnSearchItem.
 */
public class TableColumnItemMorphToSearchAction extends AbstractTableColumnItem {

	public TableColumnItemMorphToSearchAction(ActionParameters parameters, WidgetEditPart editPart) {
		super(parameters, editPart.getViewer());
		setImageDescriptor(PageUIPlugin.getImageDescriptor("/icons/obj16/search.png"));
		setEnabled(calculateEnabled());
	}
	
	public void run() {
		MorphToSearchCommand command = new MorphToSearchCommand(getSelectedWidget(), getParameters().getWidgetTemplateName());
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
		return !wt.equals(WidgetTypeConstants.TABLE_COLUMN_SEARCH_ITEM);
	}

}

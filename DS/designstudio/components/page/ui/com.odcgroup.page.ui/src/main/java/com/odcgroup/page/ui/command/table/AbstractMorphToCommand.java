package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.TableHelper;
import com.odcgroup.page.model.widgets.table.ITableColumnItem;
import com.odcgroup.page.ui.command.BaseCommand;

public abstract class AbstractMorphToCommand extends BaseCommand {

	private Widget parent;
	private Widget oldWidget;
	private String templateType;
	private int index;
	
	public AbstractMorphToCommand(Widget selectedWidget, String templateType) {
		this.oldWidget = selectedWidget;
		this.templateType = templateType;
	}

	private ITableColumnItem createTableColumnItemFromTemplate() {
		return TableHelper.getTableUtilities().getFactory().createTableColumnItem(templateType);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		ITableColumnItem tableColumnItem = createTableColumnItemFromTemplate();
		copyProperties(oldWidget, tableColumnItem.getWidget());
		parent = oldWidget.getParent();
		index = parent.getContents().indexOf(oldWidget);
		parent.getContents().set(index, tableColumnItem.getWidget());
	}
	
	private void copyProperties(Widget oldWidget, Widget newWidget) {
		for (String propertyName: getProperties()) {
			newWidget.setPropertyValue(propertyName, oldWidget.getPropertyValue(propertyName));
		}
	}

	protected abstract String[] getProperties();

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		parent.getContents().set(index, oldWidget);
	}
	
	public Widget getSelectedWidget() {
		return parent.getContents().get(index);
	}


}

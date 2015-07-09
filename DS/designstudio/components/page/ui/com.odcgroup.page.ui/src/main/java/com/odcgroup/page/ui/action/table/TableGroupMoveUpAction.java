package com.odcgroup.page.ui.action.table;

import org.eclipse.gef.commands.Command;

import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableGroupMoveUpCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * This action moves the selected column to the left.
 * 
 * @author scn
 */
public class TableGroupMoveUpAction extends AbstractTableGroupMoveAction {

	ITableGroup group = null;
	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		if (getSelectedWidget() == null) {
			return false;
		}
		group = getGroup();
		return group != null ? group.canMoveUp() : false;
	}

	/**
	 * @param parameters
	 * @param editPart 
	 */
	public TableGroupMoveUpAction(ActionParameters parameters, WidgetEditPart editPart) {
		super(parameters, editPart);
		setEnabled(calculateEnabled());
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.table.AbstractTableColumnMoveAction#getMoveCommand()
	 */
	protected Command getMoveCommand() {
		return new TableGroupMoveUpCommand(getTable(), group);
	}

}

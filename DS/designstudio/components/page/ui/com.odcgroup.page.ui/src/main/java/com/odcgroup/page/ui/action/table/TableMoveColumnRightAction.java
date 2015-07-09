package com.odcgroup.page.ui.action.table;

import org.eclipse.gef.commands.Command;

import com.odcgroup.page.model.widgets.table.ITableColumn;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.command.table.TableMoveColumnRightCommand;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * This action moves the selected column to the right.
 * 
 * @author atr
 * @author pkk
 * @since DS 1.40.0
 */
public class TableMoveColumnRightAction extends AbstractTableColumnMoveAction {

	
	/**
	 * @see com.odcgroup.page.ui.action.table.AbstractTableColumnMoveAction#calculateEnabled()
	 */
	protected boolean calculateEnabled() {
		if (getSelectedWidget() == null) {
			return false;
		}
		ITableColumn column = getColumn();
		return column != null ? column.canMoveRight() : false;
	}
	

	/**
	 * @param parameters
	 * @param editPart 
	 */
	public TableMoveColumnRightAction(ActionParameters parameters, WidgetEditPart editPart) {
		super(parameters, editPart);
		setEnabled(calculateEnabled());
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.table.AbstractTableColumnMoveAction#getMoveCommand()
	 */
	protected Command getMoveCommand() {
		return new TableMoveColumnRightCommand(getColumn());
	}

}

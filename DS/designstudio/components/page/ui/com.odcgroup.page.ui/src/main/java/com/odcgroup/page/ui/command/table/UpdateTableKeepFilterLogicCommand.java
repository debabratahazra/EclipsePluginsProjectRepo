package com.odcgroup.page.ui.command.table;

import com.odcgroup.page.model.widgets.table.ITable;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class UpdateTableKeepFilterLogicCommand extends AbstractTableCommand {
	/** */
	private String oldProperty;
	/** */
	private String newValue;

	/**
	 * @param table
	 * @param newValue 
	 */
	public UpdateTableKeepFilterLogicCommand(ITable table, String newValue) {
		super(table);
		this.newValue = newValue;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldProperty = getTable().getKeepFilterLogic().getValue();
		getTable().getKeepFilterLogic().setValue(newValue);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (oldProperty != null) {
			getTable().getKeepFilterLogic().setValue(oldProperty);
		}
	}

}

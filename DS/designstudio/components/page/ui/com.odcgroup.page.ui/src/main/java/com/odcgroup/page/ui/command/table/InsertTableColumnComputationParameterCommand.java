package com.odcgroup.page.ui.command.table;

import java.util.List;

import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command insert a computation parameter given a computed table column
 * 
 * @author atr
 */
public class InsertTableColumnComputationParameterCommand extends AbstractTableColumnCommand {

	private String parameter;

	/**
	 * @param column
	 * @param parameter
	 */
	public InsertTableColumnComputationParameterCommand(ITableColumn column, String parameter) {
		super(column);
		this.parameter = parameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		parameters.add(parameter);
		column.setParameters(parameters);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		parameters.remove(parameter);
		column.setParameters(parameters);
	}

}

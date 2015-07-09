package com.odcgroup.page.ui.command.table;

import java.util.List;

import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command deletes a computation parameter given a computed table column
 * 
 * @author atr
 */
public class DeleteTableColumnComputationParameterCommand extends AbstractTableColumnCommand {

	private String parameter;
	private int position;

	/**
	 * @param column
	 * @param parameter
	 */
	public DeleteTableColumnComputationParameterCommand(ITableColumn column, String parameter) {
		super(column);
		this.parameter = parameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		position = parameters.indexOf(parameter);
		parameters.remove(position);
		column.setParameters(parameters);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		parameters.add(position, parameter);
		column.setParameters(parameters);
	}

}

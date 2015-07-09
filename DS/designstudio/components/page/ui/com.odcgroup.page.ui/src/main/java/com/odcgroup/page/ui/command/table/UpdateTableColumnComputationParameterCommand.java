package com.odcgroup.page.ui.command.table;

import java.util.List;

import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command insert a computation parameter given a computed table column
 * 
 * @author atr
 */
public class UpdateTableColumnComputationParameterCommand extends AbstractTableColumnCommand {

	private String oldParameter;
	private String newParameter;

	/**
	 * @param column
	 * @param oldParameter
	 * @param newParameter
	 */
	public UpdateTableColumnComputationParameterCommand(ITableColumn column, String oldParameter, String newParameter) {
		super(column);
		this.oldParameter = oldParameter;
		this.newParameter = newParameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		int pos = parameters.indexOf(oldParameter);
		if (pos != -1) {
			parameters.set(pos, newParameter);
		}
		column.setParameters(parameters);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		int pos = parameters.indexOf(newParameter);
		if (pos != -1) {
			parameters.set(pos, oldParameter);
		}
		column.setParameters(parameters);
	}

}

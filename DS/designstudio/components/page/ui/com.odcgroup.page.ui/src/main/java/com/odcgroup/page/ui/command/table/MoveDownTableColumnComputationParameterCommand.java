package com.odcgroup.page.ui.command.table;

import java.util.Collections;
import java.util.List;

import com.odcgroup.page.model.widgets.table.ITableColumn;

/**
 * This command move down the list a computation parameter given a computed table column
 * 
 * @author atr
 */
public class MoveDownTableColumnComputationParameterCommand extends AbstractTableColumnCommand {

	private String parameter;

	/**
	 * @param column
	 * @param parameter
	 */
	public MoveDownTableColumnComputationParameterCommand(ITableColumn column, String parameter) {
		super(column);
		this.parameter = parameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		int pos = parameters.indexOf(parameter);
		if (pos != -1 && (pos < parameters.size()-1)) {
			Collections.swap(parameters, pos, pos+1);
			column.setParameters(parameters);
		} 
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		ITableColumn column = getTableColumn();
		List<String> parameters = column.getParameters();
		int pos = parameters.indexOf(parameter);
		Collections.swap(parameters, pos, pos-1);
		column.setParameters(parameters);
	}

}

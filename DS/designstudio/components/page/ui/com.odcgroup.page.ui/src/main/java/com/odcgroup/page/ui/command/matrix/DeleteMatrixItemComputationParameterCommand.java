package com.odcgroup.page.ui.command.matrix;

import java.util.List;

import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class DeleteMatrixItemComputationParameterCommand extends BaseCommand {
	
	private IMatrixComputationItem column;
	private String parameter;
	private int position;

	/**
	 * @param column
	 * @param parameter
	 */
	public DeleteMatrixItemComputationParameterCommand(IMatrixComputationItem column, String parameter) {
		this.column = column;
		this.parameter = parameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		List<String> parameters = column.getComputationParameters();
		position = parameters.indexOf(parameter);
		parameters.remove(position);
		column.setComputationParameters(parameters);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		List<String> parameters = column.getComputationParameters();
		parameters.add(position, parameter);
		column.setComputationParameters(parameters);
	}	

}

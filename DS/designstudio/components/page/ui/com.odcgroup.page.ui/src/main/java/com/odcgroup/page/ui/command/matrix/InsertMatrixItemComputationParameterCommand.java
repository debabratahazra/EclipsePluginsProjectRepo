package com.odcgroup.page.ui.command.matrix;

import java.util.List;

import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertMatrixItemComputationParameterCommand extends BaseCommand {

	private String parameter;
	private IMatrixComputationItem column;

	/**
	 * @param column
	 * @param parameter
	 */
	public InsertMatrixItemComputationParameterCommand(IMatrixComputationItem column, String parameter) {
		this.column = column;
		this.parameter = parameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		List<String> parameters = column.getComputationParameters();
		parameters.add(parameter);
		column.setComputationParameters(parameters);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		List<String> parameters = column.getComputationParameters();
		parameters.remove(parameter);
		column.setComputationParameters(parameters);
	}



}

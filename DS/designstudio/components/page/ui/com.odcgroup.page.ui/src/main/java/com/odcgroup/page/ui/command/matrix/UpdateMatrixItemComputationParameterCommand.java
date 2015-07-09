package com.odcgroup.page.ui.command.matrix;

import java.util.List;

import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateMatrixItemComputationParameterCommand extends BaseCommand {

	private String oldParameter;
	private String newParameter;
	
	private IMatrixComputationItem column;

	/**
	 * @param column
	 * @param oldParameter
	 * @param newParameter
	 */
	public UpdateMatrixItemComputationParameterCommand(IMatrixComputationItem column, String oldParameter, String newParameter) {
		this.column = column;
		this.oldParameter = oldParameter;
		this.newParameter = newParameter;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		List<String> parameters = column.getComputationParameters();
		int pos = parameters.indexOf(oldParameter);
		if (pos != -1) {
			parameters.set(pos, newParameter);
		}
		column.setComputationParameters(parameters);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		List<String> parameters = column.getComputationParameters();
		int pos = parameters.indexOf(newParameter);
		if (pos != -1) {
			parameters.set(pos, oldParameter);
		}
		column.setComputationParameters(parameters);
	}



}

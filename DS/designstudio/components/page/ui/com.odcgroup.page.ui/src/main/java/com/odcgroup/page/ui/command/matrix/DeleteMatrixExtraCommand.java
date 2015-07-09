package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class DeleteMatrixExtraCommand extends BaseCommand {
	
	/** */
	private IMatrixExtra extra = null;
	/** */
	private IMatrix matrix;

	/**
	 * @param matrix
	 * @param extra 
	 */
	public DeleteMatrixExtraCommand(IMatrix matrix, IMatrixExtra extra) {
		this.matrix = matrix;
		this.extra = extra;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		matrix.remove(extra);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		matrix.add(extra);
	}




}

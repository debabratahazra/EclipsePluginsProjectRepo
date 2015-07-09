package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtra;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertMatrixExtraCommand extends BaseCommand {
		
	/** tableSort which is inserted */
	private IMatrixExtra extra;
	/** */
	private IMatrix matrix;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		matrix.add(extra);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (extra != null) {
			matrix.remove(extra);
		}
	}
	
	/**
	 * @param matrix
	 * @param extra 
	 */
	public InsertMatrixExtraCommand(IMatrix matrix, IMatrixExtra extra) {
		this.matrix = matrix;
		this.extra = extra;
	}
}

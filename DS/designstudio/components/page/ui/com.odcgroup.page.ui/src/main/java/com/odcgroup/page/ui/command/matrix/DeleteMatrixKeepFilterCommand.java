package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class DeleteMatrixKeepFilterCommand extends BaseCommand {
	
	/** */
	private ITableKeepFilter keepFilter = null;
	private IMatrix matrix;

	/**
	 * @param matrix
	 * @param keepFilter 
	 */
	public DeleteMatrixKeepFilterCommand(IMatrix matrix, ITableKeepFilter keepFilter) {
		this.matrix = matrix;
		this.keepFilter = keepFilter;
	}
	
	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		matrix.remove(keepFilter);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		matrix.add(keepFilter);
	}

}

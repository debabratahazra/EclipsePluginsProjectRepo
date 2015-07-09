package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertMatrixKeepFilterCommand extends BaseCommand {
		
	/** keepFilter which is inserted */
	private ITableKeepFilter keepFilter;
	private IMatrix matrix;

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		matrix.add(keepFilter);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (keepFilter != null) {
			matrix.remove(keepFilter);
		}
	}
	
	/**
	 * @param matrix
	 * @param keepFilter 
	 */
	public InsertMatrixKeepFilterCommand(IMatrix matrix, ITableKeepFilter keepFilter) {
		this.matrix = matrix;
		this.keepFilter = keepFilter;
	}

}

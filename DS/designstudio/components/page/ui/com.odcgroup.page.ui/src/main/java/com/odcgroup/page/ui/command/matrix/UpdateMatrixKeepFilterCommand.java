package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.model.widgets.table.ITableKeepFilter;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * TODO: Document me!
 *
 * @author pkk
 *
 */
public class UpdateMatrixKeepFilterCommand extends BaseCommand {
	
	/** new value */
	private ITableKeepFilter newValue;
	/** old value */
	private ITableKeepFilter oldValue;
	/** matrix */
	private IMatrix matrix;

	/**
	 * @param matrix
	 * @param newValue 
	 */
	public UpdateMatrixKeepFilterCommand(IMatrix matrix, ITableKeepFilter newValue, ITableKeepFilter oldValue) {
		this.matrix = matrix;
		this.oldValue = getOldValue(matrix, newValue, oldValue);
		this.newValue = newValue;
	}
	
	/**
	 * @param matrix
	 * @param newValue
	 * @return oldValue
	 */
	private ITableKeepFilter getOldValue(IMatrix matrix, ITableKeepFilter newValue, ITableKeepFilter oldValue) {
		for (ITableKeepFilter filter : matrix.getKeepFilters()) {
			if (filter.getColumnName().equals(newValue.getColumnName()) 
					&& filter.getOperand().equals(oldValue.getOperand()) 
					&& filter.getOperator().equals(oldValue.getOperator())) {
				return filter;
			}
		}
		return null;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		matrix.remove(oldValue);
		matrix.add(newValue);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		matrix.remove(newValue);
		matrix.add(oldValue);
	}

}

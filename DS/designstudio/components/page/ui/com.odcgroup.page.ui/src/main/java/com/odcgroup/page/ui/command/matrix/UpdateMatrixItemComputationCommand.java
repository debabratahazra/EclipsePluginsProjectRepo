package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.widgets.matrix.IMatrixComputationItem;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateMatrixItemComputationCommand extends BaseCommand {
	/** */
	private String oldProperty;
	/** */
	private String newValue;
	/** */
	private IMatrixComputationItem column;

	/**
	 * @param column
	 * @param newValue 
	 */
	public UpdateMatrixItemComputationCommand(IMatrixComputationItem column, String newValue) {
		this.column = column;
		this.newValue = newValue;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Property p = column.getComputation();
		if (p != null) {
			oldProperty = p.getValue();
			p.setValue(newValue);
			if (newValue.equals("make-amount")) {
				setDisplayType(column, "amount");
			} else if (newValue.equals("relative-percent")) {
				setDisplayType(column, "percentTA");				
			}
		}
	}
	
	/**
	 * @param column
	 * @param value
	 */
	private void setDisplayType(IMatrixComputationItem column, String value) {
		if (column instanceof IMatrixContentCellItem) {
			((IMatrixContentCellItem) column).getDisplayFormat().setValue(value);
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (oldProperty != null) {
			column.getComputation().setValue(oldProperty);
		}
	}


	

}

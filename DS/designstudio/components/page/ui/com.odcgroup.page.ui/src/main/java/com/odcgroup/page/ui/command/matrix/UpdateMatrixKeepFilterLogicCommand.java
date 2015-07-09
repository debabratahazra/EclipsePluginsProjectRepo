package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrix;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class UpdateMatrixKeepFilterLogicCommand extends BaseCommand {
	/** */
	private String oldProperty;
	/** */
	private String newValue;
	
	private IMatrix matrix;

	/**
	 * @param table
	 * @param newValue 
	 */
	public UpdateMatrixKeepFilterLogicCommand(IMatrix matrix, String newValue) {
		this.matrix = matrix;
		this.newValue = newValue;
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldProperty = matrix.getKeepFilterLogic().getValue();
		matrix.getKeepFilterLogic().setValue(newValue);
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (oldProperty != null) {
			matrix.getKeepFilterLogic().setValue(oldProperty);
		}
	}

}

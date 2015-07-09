package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.widgets.matrix.IMatrixAxis;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * @author pkk
 *
 */
public class UpdateMatrixSortCommand extends BaseCommand {
	
	private IMatrixAxis matrixAxis;
	
	private String columnName;
	private String direction;
	
	private String oldColumn;
	private String oldDir;
	
	/**
	 * @param matrixAxis
	 * @param columnName
	 * @param direction
	 */
	public UpdateMatrixSortCommand(IMatrixAxis matrixAxis, String columnName, String direction) {
		this.matrixAxis = matrixAxis;
		this.columnName = columnName;
		this.direction = direction;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		oldColumn = matrixAxis.getSortingColumnName();
		oldDir = matrixAxis.getSortingDirection();		
		matrixAxis.setSortingColumnName(columnName);
		matrixAxis.setSortingDirection(direction);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {	
		matrixAxis.setSortingColumnName(oldColumn);
		matrixAxis.setSortingDirection(oldDir);
	}

}

package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumn;
import com.odcgroup.page.model.widgets.matrix.IMatrixExtraColumnItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class MatrixExtraColumnInsertItemCommand extends BaseCommand {
	
	/** */
	private Widget widget;
	private IMatrixExtraColumn cell;
	private IMatrixExtraColumnItem item;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		item = MatrixHelper.getMatrixExtraColumnItem(widget);
		cell.addItem(item);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (cell.getItems().contains(item)) {
			cell.removeItem(item);
		}
	}

	/**
	 * @param cell
	 * @param widget 
	 * @param index 
	 */
	public MatrixExtraColumnInsertItemCommand(IMatrixExtraColumn cell, Widget widget) {
		this.cell = cell;
		this.widget = widget;
	}



}

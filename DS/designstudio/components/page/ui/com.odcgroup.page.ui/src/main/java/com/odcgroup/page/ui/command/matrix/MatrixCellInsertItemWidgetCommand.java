package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class MatrixCellInsertItemWidgetCommand extends BaseCommand {
	/** */
	private Widget widget;
	private IMatrixContentCell contentCell;
	private IMatrixContentCellItem item;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		item = MatrixHelper.getMatrixContentCellItem(widget);
		contentCell.addItem(item);
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		//TODO
	}

	/**
	 * @param table
	 * @param widget 
	 * @param index 
	 */
	public MatrixCellInsertItemWidgetCommand(IMatrixContentCell cell, Widget widget) {
		this.contentCell = cell;
		this.widget = widget;
	}

}

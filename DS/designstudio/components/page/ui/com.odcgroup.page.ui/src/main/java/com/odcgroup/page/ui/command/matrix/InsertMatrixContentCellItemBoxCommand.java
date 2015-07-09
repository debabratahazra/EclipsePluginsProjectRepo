package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.MatrixHelper;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCellItem;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertMatrixContentCellItemBoxCommand extends BaseCommand {
	
	/** */
	private Widget widget;
	private Widget box;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		Widget cell = MatrixHelper.getParentMatrixContentCell(box);
		if (cell != null && cell.getTypeName().equals(WidgetTypeConstants.MATRIX_CONTENTCELL)) {
			IMatrixContentCellItem item = MatrixHelper.getMatrixContentCellItem(widget);
			MatrixHelper.getMatrixContentCell(cell).addItem(item, box);
		}
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		box.getContents().remove(widget);
	}

	/**
	 * @param box 
	 * @param widget 
	 */
	public InsertMatrixContentCellItemBoxCommand(Widget box, Widget widget) {
		this.box = box;
		this.widget = widget;
	}

}

package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.metamodel.WidgetTypeConstants;
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
public class MatrixContentCellItemDeleteCommand extends BaseCommand {
	
	private IMatrixContentCell contentCell = null;
	private IMatrixContentCellItem item = null;
	private Widget box = null;
	
	/**
	 * @param parent
	 * @param child
	 */
	public MatrixContentCellItemDeleteCommand(Widget parent, Widget child) {
		if (parent.getTypeName().equals(WidgetTypeConstants.BOX) 
				|| parent.getTypeName().equals(WidgetTypeConstants.CONDITIONAL_BODY)) {
			Widget container = MatrixHelper.getParentMatrixContentCell(parent);
			this.contentCell = MatrixHelper.getMatrixContentCell(container);
			this.box = parent;
		} else if (parent.getTypeName().equals(WidgetTypeConstants.MATRIX_CONTENTCELL)) {
			this.contentCell = MatrixHelper.getMatrixContentCell(parent);			
		}
		this.item = MatrixHelper.getMatrixContentCellItem(child);
	}
	
	
	
	public void execute() {
		if (contentCell != null && item != null) {
			contentCell.removeItem(item);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (box != null) {
			contentCell.addItem(item, box);
		} else {
			contentCell.addItem(item);
		}
	}

}

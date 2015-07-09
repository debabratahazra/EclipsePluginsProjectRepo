package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.matrix.IMatrixContentCell;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class InsertMatrixContentCellItemCommand extends BaseCommand {
	
	private IMatrixContentCell contentCell;
	private Widget boxWidget = null;
	
	/**
	 * 
	 */
	public InsertMatrixContentCellItemCommand(IMatrixContentCell contentCell, Widget box) {
		this.contentCell = contentCell;
		this.boxWidget = box;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
		if(boxWidget == null) {
			contentCell.addItem(true);
		} else {
			contentCell.addToBox(boxWidget, true);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		super.undo();
	}

}

package com.odcgroup.page.ui.command.matrix;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 *
 * @author pkk
 *
 */
public class MatrixDoNotDeleteCommand extends BaseCommand {

	/**
	 * @param parent
	 * @param child
	 */
	public MatrixDoNotDeleteCommand(Widget parent, Widget child) {		
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.ui.command.BaseCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return false;
	}	
	
	public void execute() {
	}
}

package com.odcgroup.page.ui.command.grid;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.BaseCommand;

/**
 * A grid cell cannot be deleted
 * @author atr
 * @since DS 1.40.0
 */
public class GridDeleteCellCommand extends BaseCommand {
	
	public boolean canExecute() {
		return false;
	}

	public boolean canUndo() {
		return false;
	}

	public GridDeleteCellCommand(Widget parent, Widget child) {
		setLabel("Delete "+child.getTypeName()+" widget");
	}

}

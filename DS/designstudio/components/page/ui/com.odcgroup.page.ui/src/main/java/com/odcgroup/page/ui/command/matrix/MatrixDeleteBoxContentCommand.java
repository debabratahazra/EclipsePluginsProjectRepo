package com.odcgroup.page.ui.command.matrix;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.commands.CompoundCommand;

import com.odcgroup.page.model.Widget;

public class MatrixDeleteBoxContentCommand extends CompoundCommand {
	
	/** Widget to remove. */
	private Widget child;
	
	public MatrixDeleteBoxContentCommand(Widget parent, Widget child) {
		Assert.isNotNull(parent);
		Assert.isNotNull(child);
		
		this.child = child;
	
		setLabel("Delete "+child.getTypeName()+" widget");
		prepare();
	}
	
	private void prepare() {
		EList<Widget> list = child.getContents();
		MatrixContentCellItemDeleteCommand itemDelComm;
		for (Widget widget : list) {
			itemDelComm = new MatrixContentCellItemDeleteCommand(child, widget);
			add(itemDelComm);
		}
	}	
}

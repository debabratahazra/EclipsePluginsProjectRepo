package com.odcgroup.workbench.editors.properties.util;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Table;

public abstract class ListMenuAction extends Action {
	
	private Table table;

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}

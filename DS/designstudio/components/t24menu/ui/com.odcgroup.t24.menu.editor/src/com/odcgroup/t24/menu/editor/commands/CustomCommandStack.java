package com.odcgroup.t24.menu.editor.commands;

import org.eclipse.emf.common.command.BasicCommandStack;

public class CustomCommandStack extends BasicCommandStack {

	public void enableSave() {
		saveIndex = -2;
	}

	private void completeUndo() {
		super.undo();
		if (commandList.size() > top + 1) {
			commandList.remove(top + 1);
			mostRecentCommand = commandList.get(top);
		} else {
			mostRecentCommand = null;
		}
	}
}

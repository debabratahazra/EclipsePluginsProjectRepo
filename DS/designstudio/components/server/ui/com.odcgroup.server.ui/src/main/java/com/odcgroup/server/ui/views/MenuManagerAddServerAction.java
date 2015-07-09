package com.odcgroup.server.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;

import com.odcgroup.server.ui.ServerUICore;

/**
 * Add a contributed action for adding a new server to the menu
 */
public class MenuManagerAddServerAction implements IAddAction {
	
	private IMenuManager menuManager;
	
	public MenuManagerAddServerAction(IMenuManager menuManager) {
		this.menuManager = menuManager;
	}

	@Override
	public void addAction(Action action) {
		action.setImageDescriptor(ServerUICore.getImageDescriptor("icons/addserver.png"));
		menuManager.add(action);
	}
}

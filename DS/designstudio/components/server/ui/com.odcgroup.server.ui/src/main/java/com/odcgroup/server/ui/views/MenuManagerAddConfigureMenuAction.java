package com.odcgroup.server.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;

/**
 * Add a contributed action for the configuration of the server to the menu
 */
public class MenuManagerAddConfigureMenuAction implements IAddAction {
	
	private IMenuManager menu;
	
	public MenuManagerAddConfigureMenuAction(IMenuManager menu) {
		this.menu = menu;
	}

	@Override
	public void addAction(Action action) {
		menu.add(action);
	}

}

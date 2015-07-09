package com.odcgroup.t24.version.newscreen.ui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class NewScreenActionProvider extends CommonActionProvider {
	private NewScreenAction action;
	
	public NewScreenActionProvider() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (action.isEnabled())
			menu.appendToGroup("group.new", action);
	}
	
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		action = new NewScreenAction();
	}
}

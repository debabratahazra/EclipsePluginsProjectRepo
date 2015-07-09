package com.odcgroup.t24.menu.editor.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

/**
 * @author pkk
 */
public class NewMenuActionProvider extends CommonActionProvider {
	
	private NewMenuAction menuAction;

	public NewMenuActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		menuAction = new NewMenuAction();
	}
	
	public void fillContextMenu(IMenuManager menu) {
		if (menuAction.isEnabled())
			menu.appendToGroup("group.new", menuAction);
	}
}

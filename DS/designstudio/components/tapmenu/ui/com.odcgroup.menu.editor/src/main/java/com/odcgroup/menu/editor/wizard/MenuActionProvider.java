package com.odcgroup.menu.editor.wizard;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

/**
 * @author pkk
 *
 */
public class MenuActionProvider extends CommonActionProvider {
	
	private NewMenuAction menuAction;

	public MenuActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		menuAction = new NewMenuAction();
	}
	
	public void fillContextMenu(IMenuManager menu) {
		if (menuAction.isEnabled())
			menu.appendToGroup("group.new", menuAction);
	}
}

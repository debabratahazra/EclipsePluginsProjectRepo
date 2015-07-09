package com.odcgroup.edge.t24ui.model.ui.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class EsonActionProvider extends CommonActionProvider {
	
	private NewCosAction action;
	
	public EsonActionProvider() {
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (action.isEnabled())
			menu.appendToGroup("group.new", action);
	}
	
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		action = new NewCosAction();
	}
}

package com.odcgroup.service.model.ui;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

/**
 * 
 */


public class ServiceActionProvider extends CommonActionProvider {
	private NewServiceAction serviceAction;
	public ServiceActionProvider() {
		// TODO Auto-generated constructor stub
	}

	public void init(ICommonActionExtensionSite aSite) {
		serviceAction = new NewServiceAction();
	}
	
	public void fillContextMenu(IMenuManager menu) {
		if (serviceAction.isEnabled())
			menu.appendToGroup("group.new", serviceAction);
	}
}

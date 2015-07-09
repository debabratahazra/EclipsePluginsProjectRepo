package com.odcgroup.mdf.integration.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class DomainActionProvider extends CommonActionProvider {

	private NewDomainAction domainAction;

	public DomainActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		domainAction = new NewDomainAction();
	}

	public void fillContextMenu(IMenuManager menu) {
		if (domainAction.isEnabled())
			menu.appendToGroup("group.new", domainAction);
	}

}

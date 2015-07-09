package com.odcgroup.t24.enquiry.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public class NewEnquiryActionProvider extends CommonActionProvider {

    private NewEnquiryAction action;
	
	public NewEnquiryActionProvider() {
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (action.isEnabled())
			menu.appendToGroup("group.new", action);
	}
	
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		action = new NewEnquiryAction();
	}

}

package com.odcgroup.workbench.ui.navigator.pageflow;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.odcgroup.pageflow.editor.diagram.custom.wizards.NewPageflowAction;

public class PageflowActionProvider extends CommonActionProvider {

	private NewPageflowAction pageflowAction;

//	private NewModelPackageAction pageflowGroupAction;

	public PageflowActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		pageflowAction = new NewPageflowAction();
//		pageflowGroupAction = new NewModelPackageAction(Activator.MODEL_NAME);
	}

	public void fillContextMenu(IMenuManager menu) {
		if (pageflowAction.isEnabled())
			menu.appendToGroup("group.new", pageflowAction);
//		if (pageflowGroupAction.isEnabled())
//			menu.appendToGroup("group.new", pageflowGroupAction);
	}
}

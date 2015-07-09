package com.odcgroup.workbench.ui.navigator.process;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

import com.odcgroup.process.diagram.custom.wizards.NewProcessAction;

public class ProcessActionProvider extends CommonActionProvider {

	private NewProcessAction processAction;

//	private NewModelPackageAction processGroupAction;

	public ProcessActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		processAction = new NewProcessAction();
//		processGroupAction = new NewModelPackageAction(Activator.MODEL_NAME);
	}

	public void fillContextMenu(IMenuManager menu) {
		if (processAction.isEnabled())
			menu.appendToGroup("group.new", processAction);
//		if (processGroupAction.isEnabled())
//			menu.appendToGroup("group.new", processGroupAction);
	}

}

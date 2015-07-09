package com.odcgroup.t24.common.importer.ui.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;

public abstract class ModelImportActionProvider extends CommonActionProvider {
	
	private static String GROUP_NAME = "group.port";

	private ModelImportAction importAction;
	
	protected abstract ModelImportAction createImportAction();
	
	protected String getGroupName() {
		return ModelImportActionProvider.GROUP_NAME;
	}

	public void fillContextMenu(IMenuManager menu) {
		if (importAction.isEnabled())
			menu.appendToGroup(getGroupName(), importAction); //$NON-NLS-1$
	}

	public void init(ICommonActionExtensionSite aSite) {
		importAction = createImportAction();
	}

}

package com.odcgroup.workbench.ui.internal.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.odcgroup.workbench.ui.action.NewModelPackageAction;

public class NewPackageActionProvider extends CommonActionProvider {

	private NewModelPackageAction newPackageAction;

	private ICommonViewerWorkbenchSite viewSite = null;

	public void init(ICommonActionExtensionSite aConfig) {
		if (aConfig.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			viewSite = (ICommonViewerWorkbenchSite) aConfig.getViewSite();
			newPackageAction = new NewModelPackageAction();
		}
	}

	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
			.getSelection();
		boolean anyResourceSelected = !selection.isEmpty();

		if (anyResourceSelected) { 
			menu.appendToGroup(ICommonMenuConstants.GROUP_NEW, newPackageAction);
		}
	}
}

package com.odcgroup.tap.translation.ui.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

/**
 * TODO: Document me!
 * 
 * @author atr
 */
public class TAPTranslationActionProvider extends CommonActionProvider {

	protected TAPTranslationOpenEditor openAction;

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		ICommonViewerSite viewSite = (ICommonViewerSite) aSite.getViewSite();
		if (viewSite instanceof ICommonViewerWorkbenchSite) {
			ICommonViewerWorkbenchSite workbenchSite = (ICommonViewerWorkbenchSite) viewSite;
			openAction = new TAPTranslationOpenEditor(workbenchSite.getPage(),workbenchSite.getSelectionProvider());
		}
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		if (openAction.isEnabled()) {
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openAction);
		}
	}
	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		if (openAction.isEnabled()) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_OPEN, openAction);
		}
	}
}

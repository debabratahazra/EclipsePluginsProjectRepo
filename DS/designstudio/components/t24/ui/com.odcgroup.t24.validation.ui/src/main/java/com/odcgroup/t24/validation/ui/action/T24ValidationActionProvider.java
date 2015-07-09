package com.odcgroup.t24.validation.ui.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

public class T24ValidationActionProvider extends CommonActionProvider {

	private T24ValidateAction validateAction;
	
	@Override
	public void init(ICommonActionExtensionSite anActionSite) {
		validateAction = new T24ValidateAction();
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, validateAction);
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		updateActionBars();
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		validateAction.selectionChanged(selection);
	}
}

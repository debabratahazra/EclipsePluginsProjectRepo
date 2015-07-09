package com.odcgroup.workbench.tap.validation.ui.internal.navigator;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * DS-2251: Global validation framework context menu 
 * 
 * @author pkk, kkr
 * 
 */
public class ValidationActionProvider extends CommonActionProvider {

	private ValidateAction validateAction;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator
	 * .ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		validateAction = new ValidateAction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, validateAction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars
	 * )
	 */
	public void fillActionBars(IActionBars actionBars) {
		updateActionBars();
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		validateAction.selectionChanged(selection);
	}
}

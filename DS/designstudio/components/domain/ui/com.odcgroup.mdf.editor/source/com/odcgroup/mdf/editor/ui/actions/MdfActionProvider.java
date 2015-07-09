package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * DS-2216 
 * Contextual Menu as a temporary work around
 * 
 * @author pkk
 * 
 */
public class MdfActionProvider extends CommonActionProvider {

	private SynchronizeDatasetsAction syncAction;
	
	private static final String DOMAIN_FOLDER_NAME = "domain";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator
	 * .ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		syncAction = new SynchronizeDatasetsAction();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.
	 * action.IMenuManager)
	 */
	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		if (isAcceptableSelection(selection)) {
			menu.appendToGroup(ICommonMenuConstants.GROUP_ADDITIONS, syncAction);
		}
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
		if (isAcceptableSelection(selection)) {
			syncAction.selectionChanged(selection);
		}
	}

	/**
	 * @param selection
	 * @return
	 */
	private boolean isAcceptableSelection(IStructuredSelection selection) {
		boolean enable = false;
		if (selection.size() == 1) {
			Object select = selection.getFirstElement();
			if (select instanceof IOfsModelFolder) {
				if (((IOfsModelFolder) select).getName().equals(DOMAIN_FOLDER_NAME)) {
					enable = true;
				}
			} else if (select instanceof IOfsModelPackage) {
				if (((IOfsModelPackage) select).getModelFolder().getName().equals(DOMAIN_FOLDER_NAME)) {
					enable = true;
				}
			} else if (select instanceof IOfsModelResource) {
				String fileExtn = ((IOfsModelResource) select).getURI().fileExtension();
				if (fileExtn.equals("mml") || fileExtn.equals("domain")) {
					enable = true;
				}
			}
		}
		return enable;
	}
}

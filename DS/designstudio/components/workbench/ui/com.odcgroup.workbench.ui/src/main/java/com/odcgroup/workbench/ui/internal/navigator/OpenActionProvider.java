package com.odcgroup.workbench.ui.internal.navigator;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.OpenWithMenu;
import org.eclipse.ui.internal.navigator.AdaptabilityUtility;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * Provides the open action for double click in the navigator.
 * 
 */
public class OpenActionProvider extends CommonActionProvider {

	private OpenModelFileAction openFileAction;
	private OpenModelAction openModelAction;

	private ICommonViewerWorkbenchSite viewSite = null;

	private boolean contribute = false;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite aConfig) {
		if (aConfig.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			viewSite = (ICommonViewerWorkbenchSite) aConfig.getViewSite();
			openFileAction = new OpenModelFileAction(viewSite.getPage());
			openModelAction = new OpenModelAction(viewSite.getPage());
			contribute = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	public void fillActionBars(IActionBars theActionBars) {
		if (!contribute) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		if (selection.size() == 1 && selection.getFirstElement() instanceof IOfsModelResource) {

			// if resource is readonly, use openModelAction to open
			// DS-1237 (Open standard models in the editor)
			// DS-1515
			openModelAction.selectionChanged(selection);
			theActionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openModelAction);
		}
		if (selection.size() == 1 && selection.getFirstElement() instanceof IResource) {
			openFileAction.selectionChanged(selection);
			theActionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openFileAction);
		}
	}
	
	public void fillContextMenu(IMenuManager aMenu) {

		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		if (selection == null || selection.size() != 1) {
			return;
		}

		Object element = selection.getFirstElement();

		// first try IResource
		IAdaptable openable = (IAdaptable) AdaptabilityUtility.getAdapter(element, IResource.class);
		// otherwise try ResourceMapping
		if (openable == null) {
			openable = (IAdaptable) AdaptabilityUtility.getAdapter(element, ResourceMapping.class);
		} else if (((IResource) openable).getType() != IResource.FILE) {
			openable = null;
		}

		if (openable != null) {
			// Create a menu flyout.
			IMenuManager submenu = new MenuManager(WorkbenchNavigatorMessages.OpenActionProvider_OpenWithMenu_label,
					ICommonMenuConstants.GROUP_OPEN_WITH);
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_TOP));
			submenu.add(new OpenWithMenu(viewSite.getPage(), openable));
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_ADDITIONS));

			// Add the submenu.
			if (submenu.getItems().length > 2 && submenu.isEnabled()) {
				aMenu.appendToGroup(ICommonMenuConstants.GROUP_OPEN_WITH, submenu);
			}
		}
	}
}
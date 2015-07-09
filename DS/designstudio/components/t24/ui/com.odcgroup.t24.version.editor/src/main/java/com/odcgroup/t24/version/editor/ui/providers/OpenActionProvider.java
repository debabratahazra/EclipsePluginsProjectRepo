package com.odcgroup.t24.version.editor.ui.providers;

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

public class OpenActionProvider extends CommonActionProvider {
	
	private OpenVersionModelAction openVersionModelAction;
	
	private ICommonViewerWorkbenchSite viewSite = null;
	
	private boolean contribute = false;

	@Override
	public void init(ICommonActionExtensionSite aSite) {
		if (aSite.getViewSite() instanceof ICommonViewerWorkbenchSite) {
			openVersionModelAction = new OpenVersionModelAction();
			viewSite = (ICommonViewerWorkbenchSite) aSite.getViewSite();
			contribute = true;
		}
	}

	@Override
	public void fillActionBars(IActionBars actionBars) {
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();
		if (selection.size() == 1&& selection.getFirstElement() instanceof IOfsModelResource) {
			openVersionModelAction.selectionChanged(selection);
			actionBars.setGlobalActionHandler(ICommonActionConstants.OPEN, openVersionModelAction);
		}
	 }
	
	public void fillContextMenu(IMenuManager aMenu) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
				.getSelection();
		if (selection.size() == 1
				&& selection.getFirstElement() instanceof IOfsModelResource) {
			contribute = true;			
		} else if (selection.size() == 1
				&& selection.getFirstElement() instanceof IResource) {
			contribute = false;
		}
		if (!contribute || getContext().getSelection().isEmpty()) {
			return;
		}
		addOpenWithMenu(aMenu);
	}
	
	@SuppressWarnings("restriction")
	private void addOpenWithMenu(IMenuManager aMenu) {
		IStructuredSelection ss = (IStructuredSelection) getContext()
				.getSelection();

		if (ss == null || ss.size() != 1) {
			return;
		}

		Object o = ss.getFirstElement();

		// first try IResource
		IAdaptable openable = (IAdaptable) AdaptabilityUtility.getAdapter(o,
				IResource.class);
		// otherwise try ResourceMapping
		if (openable == null) {
			openable = (IAdaptable) AdaptabilityUtility.getAdapter(o,
					ResourceMapping.class);
		} else if (((IResource) openable).getType() != IResource.FILE) {
			openable = null;
		}

		if (openable != null) {
			// Create a menu flyout.
			IMenuManager submenu = new MenuManager(
					WorkbenchNavigatorMessages.OpenActionProvider_OpenWithMenu_label,
					ICommonMenuConstants.GROUP_OPEN_WITH);
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_TOP));
			submenu.add(new OpenWithMenu(viewSite.getPage(), openable)); 
			submenu.add(new GroupMarker(ICommonMenuConstants.GROUP_ADDITIONS));

			// Add the submenu.
			if (submenu.getItems().length > 2 && submenu.isEnabled()) {
				aMenu.appendToGroup(ICommonMenuConstants.GROUP_OPEN_WITH,
						submenu);
			}
		}
	}
}

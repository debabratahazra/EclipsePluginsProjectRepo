package com.odcgroup.workbench.ui.internal.navigator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RefreshAction;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.odcgroup.workbench.core.IOfsElement;


public class RefreshActionProvider extends CommonActionProvider {

	private RefreshAction refreshAction;
	private ICommonActionExtensionSite site;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		site = anActionSite;

		refreshAction = new RefreshAction(site.getViewSite().getShell());
        refreshAction.setDisabledImageDescriptor(getImageDescriptor("dlcl16/refresh_nav.gif"));//$NON-NLS-1$
        refreshAction
                .setImageDescriptor(getImageDescriptor("elcl16/refresh_nav.gif"));//$NON-NLS-1$       
        refreshAction.setActionDefinitionId("org.eclipse.ui.file.refresh"); //$NON-NLS-1$
	}

	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
			.getSelection();
		boolean anyResourceSelected = !selection.isEmpty();

        refreshAction.selectionChanged(selection);
        menu.appendToGroup(ICommonMenuConstants.GROUP_BUILD, refreshAction);
	}

	public void fillActionBars(IActionBars actionBars) {
 		actionBars.setGlobalActionHandler(ActionFactory.REFRESH.getId(),
                refreshAction);
        updateActionBars();
	}

    public void updateActionBars() {
        IStructuredSelection selection = (IStructuredSelection) getContext()
                .getSelection();
        List<Object> selectedResources = new ArrayList<Object>();

        for(Object obj : selection.toList()) {
        	if(obj instanceof IOfsElement || obj instanceof IProject) {
        		selectedResources.add(obj);
        	}
        }
        IStructuredSelection newSelection = new StructuredSelection(selectedResources);
        refreshAction.selectionChanged(newSelection);
    }

    /**
     * Returns the image descriptor with the given relative path.
     */
    protected ImageDescriptor getImageDescriptor(String relativePath) {
       return IDEWorkbenchPlugin.getIDEImageDescriptor(relativePath);
     
    }
}

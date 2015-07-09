package com.odcgroup.workbench.ui.internal.navigator;

import java.util.Iterator;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.odcgroup.workbench.ui.action.ExportProjectAction;


public class ProjectExportActionProvider extends CommonActionProvider {

	private ExportProjectAction exportAction;
	
	private ICommonActionExtensionSite site;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		site = anActionSite;

		exportAction = new ExportProjectAction();
	}

	public void fillContextMenu(IMenuManager menu) {
		IStructuredSelection selection = (IStructuredSelection) getContext()
			.getSelection();
		boolean anyResourceSelected = !selection.isEmpty()
		&& allResourcesAreOfType(selection, IResource.PROJECT);

		if (anyResourceSelected) { 
			menu.appendToGroup(ICommonMenuConstants.GROUP_PORT, exportAction);
		}
	}

	private boolean allResourcesAreOfType(IStructuredSelection selection,
			int type) {
		Iterator it = selection.iterator();
		while(it.hasNext()) {
			Object obj = it.next();
			if (obj instanceof IResource) {
				IResource resource = (IResource) obj;
				if(resource.getType()!= type) {
					return false;
				}
			}
		}
		return true;
	}

	public void fillActionBars(IActionBars actionBars) {
 
		updateActionBars(); 

		IStructuredSelection selection = (IStructuredSelection) getContext()
		.getSelection();

		exportAction.selectionChanged(selection);
	}
}

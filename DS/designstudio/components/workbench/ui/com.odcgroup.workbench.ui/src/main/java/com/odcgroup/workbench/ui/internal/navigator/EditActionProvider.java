package com.odcgroup.workbench.ui.internal.navigator;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.operations.UndoRedoActionGroup;

import com.odcgroup.workbench.ui.action.ProxyEditActionGroup;

/**
 * @since 3.2
 *
 */
public class EditActionProvider extends CommonActionProvider {
	 
	private ProxyEditActionGroup editGroup;
	private UndoRedoActionGroup undoRedoGroup;

	private ICommonActionExtensionSite site;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
		site = anActionSite;
		editGroup = new ProxyEditActionGroup(site.getViewSite().getShell());
		IUndoContext workspaceContext = (IUndoContext) ResourcesPlugin.getWorkspace().getAdapter(IUndoContext.class);
		undoRedoGroup = new UndoRedoActionGroup(((ICommonViewerWorkbenchSite) anActionSite.getViewSite()).getSite(),
		workspaceContext, true);
	}

	public void dispose() { 
		editGroup.dispose();
		undoRedoGroup.dispose();
	}

	public void fillActionBars(IActionBars actionBars) { 
		editGroup.fillActionBars(actionBars);
		undoRedoGroup.fillActionBars(actionBars);
	}

	public void fillContextMenu(IMenuManager menu) { 
		editGroup.fillContextMenu(menu);
		undoRedoGroup.fillContextMenu(menu);
	}

	public void setContext(ActionContext context) { 
		editGroup.setContext(context);
		undoRedoGroup.setContext(context);
	}

	public void updateActionBars() { 
		editGroup.updateActionBars();
		undoRedoGroup.updateActionBars();
	}
}

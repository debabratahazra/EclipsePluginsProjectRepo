package com.odcgroup.page.ui.workbench;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.odcgroup.page.ui.action.edit.CopyPageModelsAction;
import com.odcgroup.page.ui.action.edit.PastePageModelsAction;
import com.odcgroup.workbench.ui.action.edit.DeleteOfsElementsAction;

/**
 * This class adds context menu entries to the navigator. It extends the
 * according base class of the Common Navigator Framework (CNF) and its use is
 * declared in the extension org.eclipse.ui.navigator.navigatorContent
 * 
 * @author atr
 * 
 */
public class EditActionProvider extends CommonActionProvider {

	/** */
	private PastePageModelsAction pasteAction;
	/** */
	private CopyPageModelsAction copyAction;
	/** */
	private DeleteOfsElementsAction deleteAction;

	/**
	 * @param site
	 */
	private void addListeners(ICommonViewerSite site) {
		ISelectionProvider provider = site.getSelectionProvider();
		if (pasteAction != null) {
			provider.addSelectionChangedListener(pasteAction);
		}
		if (copyAction != null) {
			provider.addSelectionChangedListener(copyAction);
		}
		if (deleteAction != null) {
			provider.addSelectionChangedListener(deleteAction);
		}
	}

	/**
	 * @param site
	 */
	private void removeListeners(ICommonViewerSite site) {
		ISelectionProvider provider = site.getSelectionProvider();
		if (pasteAction != null) {
			provider.removeSelectionChangedListener(pasteAction);
		}
		if (copyAction != null) {
			provider.removeSelectionChangedListener(copyAction);
		}
		if (deleteAction != null) {
			provider.removeSelectionChangedListener(deleteAction);
		}
	}

	/**
	 * @param site
	 * @param treeViewer
	 */
	protected void createEditActions(ICommonViewerSite site, CommonViewer treeViewer) {
		Clipboard clipboard = new Clipboard(treeViewer.getTree().getDisplay());
		copyAction = new CopyPageModelsAction(site, clipboard);
		pasteAction = new PastePageModelsAction(site, clipboard);
		deleteAction = new DeleteOfsElementsAction(site);
	}

	/**
	 * @see org.eclipse.ui.navigator.CommonActionProvider#init(org.eclipse.ui.navigator.ICommonActionExtensionSite)
	 */
	@Override
	public void init(ICommonActionExtensionSite aSite) {
		super.init(aSite);

		ICommonViewerSite site = aSite.getViewSite();
		if (site instanceof ICommonViewerWorkbenchSite) {
			StructuredViewer viewer = aSite.getStructuredViewer();
			if (viewer instanceof CommonViewer) {
				createEditActions(site, (CommonViewer) viewer);
				addListeners(site);
			}
		}
	}

	/**
	 * @see org.eclipse.ui.actions.ActionGroup#dispose()
	 */
	@Override
	public void dispose() {
		removeListeners(getActionSite().getViewSite());
	}

	/**
	 * @see org.eclipse.ui.actions.ActionGroup#fillActionBars(org.eclipse.ui.IActionBars)
	 */
	@Override
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),copyAction);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), deleteAction);
		actionBars.updateActionBars();
	}

	/**
	 * @see org.eclipse.ui.actions.ActionGroup#fillContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup("group.edit", copyAction);
		menu.appendToGroup("group.edit", pasteAction);
		menu.appendToGroup("group.edit", deleteAction);
	}

	/**
	 * Creates a new ModuleActionProvider.
	 */
	public EditActionProvider() {
		super();
	}

}

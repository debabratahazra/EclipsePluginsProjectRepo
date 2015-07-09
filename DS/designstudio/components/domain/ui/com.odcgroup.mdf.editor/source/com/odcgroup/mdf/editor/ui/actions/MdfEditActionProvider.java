package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.navigator.resources.actions.EditActionProvider;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.odcgroup.workbench.ui.action.edit.CopyOfsElementsAction;
import com.odcgroup.workbench.ui.action.edit.DeleteOfsElementsAction;




/**
 * EditAction provider class for the Domain Models
 * @author snn
 *
 */
public class MdfEditActionProvider extends EditActionProvider {
	private CopyOfsElementsAction copyAction;
	private DeleteOfsElementsAction deleteAction;
	private PasteDomainModelAction pasteAction;
	
	/**
	 * MdfEditActionProvider construcotr.
	 */
	public MdfEditActionProvider(){
		super();
	}
	/**
	 * @see com.odcgroup.mdf.editor.ui.actions.MdfEditActionProvider.init
	 */
	public void init(ICommonActionExtensionSite anActionSite) {
	   super.init(anActionSite);
	   ICommonViewerSite site = anActionSite.getViewSite();
		if (site instanceof ICommonViewerWorkbenchSite) {
			StructuredViewer viewer = anActionSite.getStructuredViewer();
			if (viewer instanceof CommonViewer) {
				createEditActions(site, (CommonViewer) viewer);
				addListeners(site);
			}
		}
	}
	
	
	/**
	 * @see  com.odcgroup.mdf.editor.ui.actions.MdfEditActionProvider.fillActionBars
	 */
	public void fillActionBars(IActionBars actionBars) {
		actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),copyAction);
		actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(), pasteAction);
		actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(), deleteAction);
		actionBars.updateActionBars();
	}
	
	
	
	/**
	 * @see com.odcgroup.mdf.editor.ui.actions.MdfEditActionProvider.fillContextMenu.
	 */
	
	public void fillContextMenu(IMenuManager menu) {
		menu.appendToGroup("group.edit", copyAction);
		menu.appendToGroup("group.edit", pasteAction);
		menu.appendToGroup("group.edit", deleteAction);
		
	}

	
	/**add the copy/paste action for the Domain.
	 * @param site
	 * @param treeViewer
	 */
	protected void createEditActions(ICommonViewerSite site, CommonViewer treeViewer) {
		Clipboard clipboard = new Clipboard(treeViewer.getTree().getDisplay());
		copyAction = new CopyDomainModelAction(site, clipboard);
		pasteAction = new PasteDomainModelAction(site, clipboard);
		deleteAction = new DeleteOfsElementsAction(site);
	}
	
	public void dispose() {
		if(getActionSite() != null)
			removeListeners(getActionSite().getViewSite());
	}
	
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
}

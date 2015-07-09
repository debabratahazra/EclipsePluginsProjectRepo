package com.odcgroup.workbench.ui.action.edit;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.DeleteResourceAction;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.workbench.core.IOfsElement;

/**
 * This Action deletes one ore more selected OFS model elements.
 * 
 * It delegates its main work to the standard delete resource action.
 * 
 * @author atr
 */
public class DeleteOfsElementsAction extends SelectionDispatchAction {

	/**
	 * @param selection
	 * @return IAction
	 */
	private IAction createDeleteAction(IStructuredSelection selection) {

		IShellProvider sp = new IShellProvider() {
			public Shell getShell() {
				return getSite().getShell();
			}
		};

		DeleteResourceAction action = new DeleteResourceAction(sp);
		action.selectionChanged(selection);
		return action;
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.edit.SelectionDispatchAction#acceptOfsElement(com.odcgroup.workbench.core.IOfsElement)
	 */
	@Override
	protected boolean acceptOfsElement(IOfsElement element) {
		return true;
	}	

	/**
	 * @see com.odcgroup.page.ui.action.edit.SelectionDispatchAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected void selectionChanged(IStructuredSelection selection) {
		setEnabled( createDeleteAction(selection).isEnabled() );
	}
	
	/**
	 * @see com.odcgroup.page.ui.action.edit.SelectionDispatchAction#run(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected void run(IStructuredSelection selection) {
		createDeleteAction(selection).run();
	}
	
	/**
	 * @param site the site owning this action
	 */
	public DeleteOfsElementsAction(ICommonViewerSite site) {
		super(site);
		setText("Delete");
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE_DISABLED));
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_DELETE);
	}

}

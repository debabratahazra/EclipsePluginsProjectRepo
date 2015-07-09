package com.odcgroup.workbench.ui.action.edit;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.ICommonViewerSite;

import com.odcgroup.workbench.ui.dnd.OfsElementTransfer;
import com.odcgroup.workbench.core.IOfsElement;

/**
 * This Action copies one ore more selected OFS model elements.
 * 
 * @author atr
 */
public class CopyOfsElementsAction extends SelectionDispatchAction {

	/** */
	private Clipboard clipboard;
	
	/**
	 * Set the clipboard contents. Prompt to retry if clipboard is busy.
	 * 
	 * @param ofsElements
	 *            the OFS element to copy to the clipboard
	 */
	protected void setClipboard(IOfsElement[] ofsElements) {
		try {
			clipboard.setContents(new Object[] { ofsElements }, 
								  new OfsElementTransfer[] { OfsElementTransfer.getInstance() });
		} catch (SWTError e) {
			if (e.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
				throw e;
			}
			if (MessageDialog.openQuestion(getSite().getShell(), "Problem with copy title", // TODO ResourceNavigatorMessages.CopyToClipboardProblemDialog_title,  //$NON-NLS-1$
					"Problem with copy.")) { //$NON-NLS-1$
				setClipboard(ofsElements);
			}
		}
	}

	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run(IStructuredSelection selection) {
		setClipboard(getSelectedOfsElements().toArray(new IOfsElement[]{}));
	}

	/**
	 * Checks if the given OFS element can be part of the selection. This
	 * default implementation returns {@code true}
	 * 
	 * @param element
	 *            the OFS Element to check
	 * 
	 * @return {@code true} if the given OFS element can be part of the
	 *         selection
	 */
	protected boolean acceptOfsElement(IOfsElement element) {
		return true;
	}

	/**
	 * All selected OFS Elements must be homogeneous and have the same parent
	 * 
	 * @param selection
	 *            the current selection
	 * @return {@code true} if all selected OFS element are homogeneous and have
	 *         the same parent
	 */
	protected boolean updateSelection(IStructuredSelection selection) {

		if (selection.isEmpty()) {
			return false;
		}

		List<IOfsElement> selectedElements = getSelectedOfsElements();
		if (selectedElements.size() == 0) {
			return false;
		}

		// selection must be homegeneous
		int selectionCount = selection.size();
		if (selectedElements.size() != selectionCount) {
			return false;
		}

		// all selected OFS elements must have the same parent
		URI parentURI = getParentURI(selectedElements.get(0));
		for (IOfsElement element : selectedElements) {
			if (!getParentURI(element).equals(parentURI)) {
				return false;
			}
		}

		return true;

	}

	/**
	 * @see com.odcgroup.page.ui.action.edit.SelectionDispatchAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected void selectionChanged(IStructuredSelection selection) {
		setEnabled(updateSelection(selection));
	}

	/**
	 * @param site
	 *            the site owning this action
	 * @param clipboard
	 */
	public CopyOfsElementsAction(ICommonViewerSite site, Clipboard clipboard) {
		super(site);

		// overrides the standard copy action
		setText("Copy");
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setActionDefinitionId(IWorkbenchCommandConstants.EDIT_COPY);

		this.clipboard = clipboard;

	}

}

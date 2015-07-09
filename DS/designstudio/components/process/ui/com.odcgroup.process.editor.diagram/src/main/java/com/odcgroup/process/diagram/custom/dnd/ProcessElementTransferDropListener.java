package com.odcgroup.process.diagram.custom.dnd;

import org.eclipse.draw2d.Cursors;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.odcgroup.pageflow.model.PageflowModelLookup;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.workbench.core.IOfsModelResource;

/**
 * @author pkk performs a drop of a pageflow model on to UserTask
 */
public class ProcessElementTransferDropListener extends
		AbstractTransferDropTargetListener {

	/**
	 * Create a new ProcessElementTransferDropListener.
	 * 
	 * @param viewer
	 *            The EditPartViewer
	 */
	public ProcessElementTransferDropListener(EditPartViewer viewer) {
		super(viewer, FileTransfer.getInstance());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	protected void handleDragOver() {
		getCurrentEvent().detail = DND.DROP_NONE;

		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		URI resourceUri = getDroppedResource().getURI();
		if (targetEditPart.getModel() instanceof Node) {
			EObject eObject = ((Node) targetEditPart.getModel()).getElement();
			if (eObject instanceof UserTask) {
				if (resourceUri.fileExtension().equals("pageflow")) {	
					getCurrentEvent().detail = DND.DROP_COPY;
					getViewer().select(targetEditPart);					
				}
			}
		}
	}

	/**
	 * return the selected IFile to be dropped
	 * 
	 * @return
	 */
	protected IOfsModelResource getDroppedResource() {
		ISelection selection = LocalSelectionTransfer.getInstance().getSelection();
		return (IOfsModelResource) ((IStructuredSelection) selection).getFirstElement();
	}
	
	/**
	 * Handles the drop. This tries to open the file using EMF.
	 */
	protected void handleDrop() {
		updateTargetRequest();
		getViewer().setCursor(Cursors.WAIT);
		super.handleDrop();
		getViewer().setCursor(null);
		selectAddedViews();
	}	
	
	/**
	 * 
	 */
	private void selectAddedViews() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		getViewer().setFocus(targetEditPart);
	}
	

	/**
	 * The target request is a CreatePageflowRequest.
	 * 
	 * @return Request
	 */
	protected Request createTargetRequest() {
		Request request = null;
		String extension = getDroppedResource().getURI().fileExtension();
		for (String ext : PageflowModelLookup.EXTENSIONS) {
			if (extension.equals(ext)) {
				request = new CreatePageflowRequest();
			}
		}
		return request;
	}

	/**
	 * Assumes that the target request is a {@link CreateRequest}.
	 */
	protected void updateTargetRequest() {
		CreatePageflowRequest request = (CreatePageflowRequest) getTargetRequest();
		if (request != null) {
			request.setLocation(getDropLocation());
			IOfsModelResource mResource = getDroppedResource();
			URI modelURI = mResource.getURI();
			if (mResource != null && modelURI.fileExtension().equals("pageflow")) {
				request.setPageflowURI(modelURI.toString());
			} 
		}
	}

	/**
	 * Override the base-class version to set the target EditPart correctly. The
	 * target EditPart is simply the EditPart which contains the figure which
	 * contains the location of the mouse when the user releases the mouse
	 * button.
	 */
	protected void updateTargetEditPart() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		setTargetEditPart(targetEditPart);
	}
}

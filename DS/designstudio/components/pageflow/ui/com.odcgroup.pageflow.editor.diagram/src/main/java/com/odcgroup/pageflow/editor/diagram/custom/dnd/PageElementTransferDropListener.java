package com.odcgroup.pageflow.editor.diagram.custom.dnd;

import java.io.IOException;

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
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

import com.odcgroup.page.model.Model;
import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.CreateSubPageflowRequest;
import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.CreateViewPageRequest;
import com.odcgroup.pageflow.editor.diagram.custom.pageintegration.PageModelLookup;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.pageflow.model.PageflowModelLookup;
import com.odcgroup.pageflow.model.SubPageflowState;
import com.odcgroup.pageflow.model.ViewState;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;


/**
 * @author pkk
 * performs a drop of Page on to ViewState
 */
public class PageElementTransferDropListener extends AbstractTransferDropTargetListener{
	

	private boolean pageRelated = false;
	private boolean pageflowRelated = false;

	/**
	 * Create a new PageElementTransferDropListener.
	 * 
	 * @param viewer  The EditPartViewer
	 */
	public PageElementTransferDropListener(EditPartViewer viewer) {
		super(viewer, FileTransfer.getInstance());
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#isEnabled(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public boolean isEnabled(DropTargetEvent event) {
		for (int i = 0; i < event.dataTypes.length; i++) {
			if (getTransfer().isSupportedType(event.dataTypes[i])) {				
				CreateRequest request = (CreateRequest) getTargetRequest();
				if (request == null){
					return false;
				}
			}
		}
		return super.isEnabled(event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.dnd.AbstractTransferDropTargetListener#handleDragOver()
	 */
	protected void handleDragOver() {
		getCurrentEvent().detail = DND.DROP_NONE;
		
		if (!pageRelated && !pageflowRelated){
			return;
		}
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		EObject droppedModel;
		try {
			droppedModel = getDroppedModelResource().getEMFModel().get(0);
		} catch (IOException e) {
			return;
		} catch (InvalidMetamodelVersionException e) {
			return;
		}
		if (targetEditPart.getModel() instanceof Node) {
			EObject eObject = ((Node)targetEditPart.getModel()).getElement();
			if (eObject instanceof ViewState && pageRelated){
				getCurrentEvent().detail = DND.DROP_COPY;
				getViewer().select(targetEditPart);
			}
			if (eObject instanceof SubPageflowState && pageflowRelated){
				Pageflow parentPageflow = (Pageflow) eObject.eContainer();
				if (droppedModel != null && droppedModel instanceof Pageflow) {
					Pageflow subpageflow = (Pageflow)droppedModel;
					//avoid cyclic reference
					if (parentPageflow.getId().equals(subpageflow.getId()) && parentPageflow.getName().equals(subpageflow.getName())){
						return;
					} else {
						getCurrentEvent().detail = DND.DROP_COPY;
						getViewer().select(targetEditPart);						
					}
				}
			}
		} 
	}
	
	/**
	 * return the selected IFile to be dropped
	 * 
	 * @return
	 */
	protected IOfsModelResource getDroppedModelResource() {
		ISelection selection = LocalSelectionTransfer.getInstance().getSelection();
		if(((IStructuredSelection) selection).getFirstElement() instanceof IOfsModelResource) {
			return (IOfsModelResource) ((IStructuredSelection) selection).getFirstElement();
		} else {
			return null;
		}
	}
	
	/**
	 * Handles the drop. This tries to open the file using EMF.
	 */
	protected void handleDrop() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		EObject droppedModel;
		try {
			droppedModel = getDroppedModelResource().getEMFModel().get(0);
		} catch (IOException e) {
			return;
		} catch (InvalidMetamodelVersionException e) {
			return;
		}
		URI modelURI = getDroppedModelResource().getURI();
		if (droppedModel != null && droppedModel instanceof Model) {
			// handle page/module drop
			getCreateViewPageRequest().setPageUri(modelURI.toString());
		} else if (droppedModel != null && droppedModel instanceof Pageflow) {
			// handle subpageflow drop
			Pageflow subpageflow = (Pageflow) droppedModel;			
			if (subpageflow != null){
				getCreateSubPageflowRequest().setSubpageflow(subpageflow);
			} else {
				return;
			}
		}
		getViewer().setFocus(targetEditPart);
		this.pageflowRelated = false;
		this.pageRelated = false;
		super.handleDrop();
	}
		
	/**
	 * Gets the target Request cast as an CreateViewPageRequest.
	 * 
	 * @return CreateViewPageRequest
	 */
	private CreateViewPageRequest getCreateViewPageRequest() {
		return (CreateViewPageRequest) getTargetRequest();
	}
	
	/**
	 * @return
	 */
	private CreateSubPageflowRequest getCreateSubPageflowRequest() {
		return (CreateSubPageflowRequest) getTargetRequest();
	}

	/**
	 * The target request is a CreateViewPageRequest.
	 * 
	 * @return Request
	 */
	protected Request createTargetRequest() {
		Request request = null;
		String extension = getDroppedModelResource().getURI().fileExtension();
		for (String ext : PageModelLookup.EXTENSIONS) {
			if (extension.equals(ext)) {
				pageRelated = true;
			}
		}
		for (String ext : PageflowModelLookup.EXTENSIONS) {
			if (extension.equals(ext)){
				pageflowRelated = true;
			}
		}
		if (pageRelated){
			 request = new CreateViewPageRequest();			
		} else if (pageflowRelated){
			request = new CreateSubPageflowRequest();
		}
		return request;
	}

	/**
	 * Assumes that the target request is a {@link CreateRequest}.
	 */
	protected void updateTargetRequest() {
		CreateRequest request = (CreateRequest) getTargetRequest();
		if (request != null){
			request.setLocation(getDropLocation());
		}
	}

	/**
	 * Override the base-class version to set the target EditPart correctly. The target EditPart is simply the EditPart
	 * which contains the figure which contains the location of the mouse when the user releases the mouse button.
	 */
	protected void updateTargetEditPart() {
		EditPart targetEditPart = getViewer().findObjectAt(getDropLocation());
		setTargetEditPart(targetEditPart);
	}
}

package com.odcgroup.pageflow.editor.diagram.custom.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPage;

import com.odcgroup.pageflow.editor.diagram.custom.request.ToggleIDRequest;
import com.odcgroup.pageflow.editor.diagram.part.Messages;
import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;

public class ToggleIDDisplayAction extends DiagramAction {

	public ToggleIDDisplayAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
	}

	@Override
	public void init() {
		super.init();
		setId(Messages.ToggleIDDisplayToolBarID);
		setText(Messages.ToggleIDDisplayText);
		setToolTipText(Messages.ToggleIDDisplayToolTip);
		setImageDescriptor(getImageDescriptor("icons/cust16/toggle.png"));
		setDisabledImageDescriptor(getImageDescriptor("icons/cust16/toggle.png"));
	}

	@Override
	protected Request createTargetRequest() {
		return new ToggleIDRequest();
	}

	@Override
	protected boolean isSelectionListener() {
		return true;
	}

	/**
	 * @param imageName
	 * @return
	 */
	protected static ImageDescriptor getImageDescriptor(String imageName) {
		return PageflowDiagramEditorPlugin.findImageDescriptor(imageName);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createOperationSet()
	 */
	protected List createOperationSet() {
		List selection = getSelectedObjects();
		if (selection.isEmpty() || !(selection.get(0) instanceof EditPart)) {
			return Collections.EMPTY_LIST;
		} 		
		

		List<DiagramEditPart> targetedEPs = new ArrayList<DiagramEditPart>();
		if (selection.get(0) instanceof DiagramEditPart) {
			DiagramEditPart selectedEP = (DiagramEditPart)selection.get(0);
	    	targetedEPs.add(selectedEP);
		}		
		return targetedEPs.isEmpty() ? Collections.EMPTY_LIST : targetedEPs;
	}

}
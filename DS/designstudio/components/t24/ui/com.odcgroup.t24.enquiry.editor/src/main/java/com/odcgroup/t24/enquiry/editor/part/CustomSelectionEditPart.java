package com.odcgroup.t24.enquiry.editor.part;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.enquiry.SelectionExpression;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryDiagramXYLayoutPolicy;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;

/**
 * 
 * @author phanikumark
 * 
 */
public class CustomSelectionEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		final EnquiryDiagramEditPart enquiryDiagramEditPart = (EnquiryDiagramEditPart) getParent();
		final EnquiryFigure enquiryFigure = (EnquiryFigure) enquiryDiagramEditPart.getFigure();

		return enquiryFigure.getCustomSelectionContainer();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EnquiryDiagramXYLayoutPolicy());
	}

	@Override
	public IFigure getContentPane() {
		return getFigure();
	}

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new LinkedList<EObject>();
		SelectionExpression model = (SelectionExpression) getModel();
		if (model != null)
			list.addAll(model.getSelection());
		return list;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}
}

package com.odcgroup.t24.enquiry.editor.part;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.enquiry.Target;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryDiagramXYLayoutPolicy;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.figure.TargetFigure;

/**
 *
 * @author phanikumark
 *
 */
public class TargetEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		final EnquiryDiagramEditPart enquiryDiagramEditPart = (EnquiryDiagramEditPart) getParent();
		final EnquiryFigure enquiryFigure = (EnquiryFigure) enquiryDiagramEditPart.getFigure();

		return enquiryFigure.getTargetContainer();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new EnquiryDiagramXYLayoutPolicy());
	}
	
	@Override
	protected void refreshVisuals() {
		TargetFigure figure = (TargetFigure) getFigure();
		Target model = (Target) getModel();
		
		figure.getApplicationLabel().setText(model.getApplication());
		figure.getScreenLabel().setText(model.getScreen());	
		
	}

	@Override
	public IFigure getContentPane() {
		TargetFigure figure = (TargetFigure) getFigure();
		return figure.getContentPane();
	}

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new LinkedList<EObject>();
		Target model = (Target) getModel();
		if (model != null)
			list.addAll(model.getMappings());
		return list;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

}

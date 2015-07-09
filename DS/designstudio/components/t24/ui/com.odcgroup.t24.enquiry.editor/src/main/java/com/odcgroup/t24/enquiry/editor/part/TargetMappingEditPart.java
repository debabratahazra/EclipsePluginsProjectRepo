package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.enquiry.TargetMapping;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.figure.TargetMappingFigure;

/**
 *
 * @author phanikumark
 *
 */
public class TargetMappingEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		return new TargetMappingFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		TargetMappingFigure figure = (TargetMappingFigure) getFigure();
		TargetMapping model = (TargetMapping) getModel();
		
		figure.getFieldLabel().setText(model.getFromField());
		figure.getValueLabel().setText(model.getToField());
	}

}

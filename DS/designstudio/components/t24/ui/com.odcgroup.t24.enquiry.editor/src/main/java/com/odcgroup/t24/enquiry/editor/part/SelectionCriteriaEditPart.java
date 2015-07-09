package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.google.common.base.Joiner;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.enquiry.SelectionCriteria;
import com.odcgroup.t24.enquiry.figure.SelectionCriteriaFigure;

/**
 *
 * @author phanikumark
 *
 */
public class SelectionCriteriaEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		return new SelectionCriteriaFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		SelectionCriteriaFigure figure = (SelectionCriteriaFigure) getFigure();
		SelectionCriteria model = (SelectionCriteria) getModel();
		final String labelText = Joiner.on(" ").join(model.getField(), model.getOperand(), model.getValues());
		figure.getFieldLabel().setText(labelText);
	}

}

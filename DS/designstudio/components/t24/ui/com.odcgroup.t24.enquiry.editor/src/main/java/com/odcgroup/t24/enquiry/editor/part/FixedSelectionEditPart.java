package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.google.common.base.Joiner;
import com.odcgroup.t24.enquiry.enquiry.FixedSelection;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.figure.FixedSelectionFigure;

/**
 *
 * @author phanikumark
 *
 */
public class FixedSelectionEditPart extends AbstractEnquiryEditPart{

	@Override
	protected IFigure createFigure() {
		return new FixedSelectionFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override	
	protected void refreshVisuals() {
		final FixedSelection model = (FixedSelection) getModel();
		final FixedSelectionFigure figure = (FixedSelectionFigure) getFigure();

		final String labelText = Joiner.on(" ").join(model.getField(), model.getOperand(), model.getValues());
		figure.getLabel().setText(labelText);
	}
}

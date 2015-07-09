package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.google.common.base.Joiner;
import com.odcgroup.t24.enquiry.enquiry.Selection;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.figure.SelectionFigure;

/**
 *
 * @author phanikumark
 *
 */
public class SelectionEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		return new SelectionFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		SelectionFigure figure = (SelectionFigure) getFigure();
		Selection model = (Selection) getModel();
		final String labelText = Joiner.on(" ").join(model.getField(), model.getOperands().toString());
		figure.getFieldLabel().setText(labelText);
		
		final StringBuilder operatorText = new StringBuilder(model.getOperator().getLiteral());
		if(model.getMandatory() != null && model.getMandatory()){
			operatorText.append("  (*)");
		}
		if(model.getPopupDropDown() != null && model.getPopupDropDown()){
			operatorText.append("  Closed");
		}
		figure.getOperatorLabel().setText(operatorText.toString());
	}

}

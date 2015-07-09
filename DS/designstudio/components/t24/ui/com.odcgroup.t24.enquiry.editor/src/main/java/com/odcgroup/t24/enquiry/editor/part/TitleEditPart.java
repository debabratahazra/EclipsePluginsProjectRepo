package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.enquiry.EnquiryHeader;
import com.odcgroup.t24.enquiry.figure.TitleFigure;
import com.odcgroup.translation.translationDsl.LocalTranslations;

/**
 *
 * @author phanikumark
 *
 */
public class TitleEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		return new TitleFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}
	
	protected void refreshVisuals() {
		final EnquiryHeader model = (EnquiryHeader) getModel();
		LocalTranslations translation = (LocalTranslations) model.getLabel();
		if (translation != null) {
			final String labelText = translation.getTranslations().get(0).getText();
			final TitleFigure figure = (TitleFigure) getFigure();
			figure.getLabel().setText(labelText);
		}
	}


}

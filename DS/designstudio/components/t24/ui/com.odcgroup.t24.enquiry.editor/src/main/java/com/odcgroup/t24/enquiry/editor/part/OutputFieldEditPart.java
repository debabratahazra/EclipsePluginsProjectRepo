package com.odcgroup.t24.enquiry.editor.part;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.figure.OutputFieldFigure;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;

/**
 *
 * @author phanikumark
 *
 */
public class OutputFieldEditPart extends AbstractEnquiryEditPart{

	@Override
	protected IFigure createFigure() {
		return new OutputFieldFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}
	
	protected void refreshVisuals() {
		final Field model = (Field) getModel();
		final OutputFieldFigure figure = (OutputFieldFigure) getFigure();

		String labelText = model.getName();
		if (!StringUtils.isEmpty(labelText)) {
			labelText = labelText.replace('_', '.');
		}
		figure.getFieldLabel().setText(labelText);
		refreshFieldView(model, figure);
	}

	private void refreshFieldView(Field model, OutputFieldFigure figure) {
		if (EnquiryUtil.isHeaderField(model)) {
			figure.getOperatorLabel().setText("Header");
		} else if (EnquiryUtil.isFooterField(model)) {
			figure.getOperatorLabel().setText("Footer");
		} else if (EnquiryUtil.isFieldInvisible(model)) {
			figure.getOperatorLabel().setText("Invisible");
			figure.markInvisible();
		} else if (!EnquiryUtil.isFieldInvisible(model)) {
			figure.getOperatorLabel().setText("Displayed");
		}
	}

}

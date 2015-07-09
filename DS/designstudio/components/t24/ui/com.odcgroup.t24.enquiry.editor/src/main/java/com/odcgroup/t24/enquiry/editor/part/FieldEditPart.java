package com.odcgroup.t24.enquiry.editor.part;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.enquiry.Field;
import com.odcgroup.t24.enquiry.figure.FieldFigure;
import com.odcgroup.t24.enquiry.util.EnquiryUtil;
import com.odcgroup.translation.translationDsl.LocalTranslations;

/**
 *
 * @author phanikumark
 *
 */
public class FieldEditPart extends AbstractEnquiryEditPart{

	@Override
	protected IFigure createFigure() {
		return new FieldFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}
	
	@SuppressWarnings("rawtypes")
	protected void refreshVisuals() {
		final Field model = (Field) getModel();
		final FieldFigure figure = (FieldFigure) getFigure();
		figure.setField(model);

		LocalTranslations translation = (LocalTranslations) model.getLabel();
		String labelText = "";
		if (translation != null && !translation.getTranslations().isEmpty()) {
			labelText = translation.getTranslations().get(0).getText();
		}
		// if no translation found
		if (StringUtils.isEmpty(labelText)) {
			labelText = model.getName();
			if (!StringUtils.isEmpty(labelText)) {
				labelText = labelText.replace('_', '.');
			}
		}
		figure.getLabel().setText(labelText);
		refreshFieldView(model, figure);
		// workaround to refresh the co-field editparts after drop of relative field
		if (EnquiryUtil.isFieldRelative(model)) {
			List list = getParent().getChildren();
			for (Object object : list) {
				if (object instanceof FieldEditPart) {
					FieldEditPart fe = (FieldEditPart) object;
					Field fem = (Field)fe.getModel();
					if (EnquiryUtil.isFieldRelative(fem)) {
						fe.refreshFieldView(fem, (FieldFigure) fe.getFigure());
					}
				}
			}
		} else {
			figure.revalidate();
		}
	}

	private void refreshFieldView(Field model, FieldFigure figure) {
		if (EnquiryUtil.isHeaderField(model)) {
			figure.markHeader();
		} else if (EnquiryUtil.isFooterField(model)) {
			figure.markFooter();
		} else if (EnquiryUtil.isFieldInvisible(model)) {
			figure.markInvisible();
		} else if (EnquiryUtil.isFieldRelative(model)) {
			int line = model.getPosition().getLine();
			String relative = model.getPosition().getRelative();
			if (relative == null) {
				relative = "";
			}
			figure.markRelative(relative + String.valueOf(line), EnquiryUtil.getRelativeInfo(model));
		}
	}

}

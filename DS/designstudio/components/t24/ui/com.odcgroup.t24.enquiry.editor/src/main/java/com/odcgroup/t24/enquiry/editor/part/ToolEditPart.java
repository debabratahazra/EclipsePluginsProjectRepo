package com.odcgroup.t24.enquiry.editor.part;

import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.enquiry.Tool;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.figure.ToolFigure;

/**
 *
 * @author phanikumark
 *
 */
public class ToolEditPart extends AbstractEnquiryEditPart {


	@Override
	protected IFigure createFigure() {
		return new ToolFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		ToolFigure figure = (ToolFigure) getFigure();
		Tool model = (Tool) getModel();
		figure.getAttributesCompartment().removeAll();
		figure.getFieldLabel().setText(model.getName());
		List<String> commands = model.getCommand();
		Label label;
		final GridData gd = new GridData(GridData.FILL_BOTH);
		if (!commands.isEmpty()) {
			for (String string : commands) {
				label = new Label(string);
				label.setLabelAlignment(PositionConstants.LEFT);
				figure.getAttributesCompartment().add(label);
				figure.getLayoutManager().setConstraint(label, gd);
			}
		}
	}

}

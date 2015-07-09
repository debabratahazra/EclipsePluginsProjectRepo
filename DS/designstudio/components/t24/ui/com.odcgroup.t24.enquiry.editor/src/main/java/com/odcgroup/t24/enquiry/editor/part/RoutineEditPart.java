package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.enquiry.JavaRoutine;
import com.odcgroup.t24.enquiry.enquiry.Routine;
import com.odcgroup.t24.enquiry.figure.RoutineFigure;

/**
 *
 * @author phanikumark
 *
 */
public class RoutineEditPart extends AbstractEnquiryEditPart{

	@Override
	protected IFigure createFigure() {
		return new RoutineFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		final Routine model = (Routine) getModel();
		final RoutineFigure figure = (RoutineFigure) getFigure();

		String labelText = model.getName();
		String prefix = "";
		if(model instanceof JavaRoutine){
			prefix = "Java : ";
		}
		else{
			prefix = "JBC : ";
		}
		
		figure.getLabel().setText(prefix + labelText);
	}
}

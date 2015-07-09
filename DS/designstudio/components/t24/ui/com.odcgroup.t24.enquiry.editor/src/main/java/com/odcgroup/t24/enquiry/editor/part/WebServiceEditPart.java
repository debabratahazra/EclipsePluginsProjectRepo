package com.odcgroup.t24.enquiry.editor.part;

import java.util.List;

import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPolicy;

import com.odcgroup.t24.enquiry.enquiry.WebService;
import com.odcgroup.t24.enquiry.editor.policy.EnquiryEditorComponentEditPolicy;
import com.odcgroup.t24.enquiry.figure.EnquiryFigure;
import com.odcgroup.t24.enquiry.figure.WebServiceFigure;

/**
 *
 * @author phanikumark
 *
 */
public class WebServiceEditPart extends AbstractEnquiryEditPart {


	@Override
	protected IFigure createFigure() {

		final EnquiryDiagramEditPart enquiryDiagramEditPart = (EnquiryDiagramEditPart) getParent();
		final EnquiryFigure enquiryFigure = (EnquiryFigure) enquiryDiagramEditPart.getFigure();

		return enquiryFigure.getWebServicesFigure();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EnquiryEditorComponentEditPolicy());
	}

	@Override
	protected void refreshVisuals() {
		WebServiceFigure figure = (WebServiceFigure) getFigure();
		WebService model = (WebService) getModel();
		
		figure.getActivityLabel().setText(model.getWebServiceActivity());
		figure.getServicesContainer().removeAll();
		List<String> commands = model.getWebServiceNames();
		Label label;		
		final GridData gd = new GridData(GridData.FILL_BOTH);
		if (!commands.isEmpty()) {
			for (String string : commands) {
				label = new Label(string);
				label.setLabelAlignment(PositionConstants.LEFT);
				figure.getServicesContainer().add(label);
				figure.getLayoutManager().setConstraint(label, gd);
			}
		}
	}

}

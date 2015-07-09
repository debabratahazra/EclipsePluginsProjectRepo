package com.odcgroup.t24.enquiry.editor.part;

import org.eclipse.draw2d.IFigure;

/**
 *
 * @author phanikumark
 *
 */
public class ParametersEditPart extends AbstractEnquiryEditPart {

	@Override
	protected IFigure createFigure() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
	}

	@Override
	protected void refreshVisuals() {
		getParent().refresh();
	}

}

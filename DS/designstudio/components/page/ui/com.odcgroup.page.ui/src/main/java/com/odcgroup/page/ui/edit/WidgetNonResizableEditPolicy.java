package com.odcgroup.page.ui.edit;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

/**
 * EditPolicy for Widgets. This is responsable for allowing the user
 * to select Widgets to be, for example, deleted.
 * 
 * @author Gary Hayes
 */
public class WidgetNonResizableEditPolicy extends NonResizableEditPolicy {

	/**
	 * Gets the target EditPart for the request.
	 * 
	 * @param request The request
	 * @return EditPart The target EditPart
	 */
	public EditPart getTargetEditPart(Request request) {
		if (RequestConstants.REQ_SELECTION.equals(request.getType())) {
			return (EditPart) getHost();
		}
		return null;
	}
}

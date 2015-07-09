package com.odcgroup.page.ui.edit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * Handles direct-edit requests for Widgets. The previewValue is updated by the cell editor.
 * 
 * @author Gary Hayes
 */
public class WidgetDirectEditPolicy extends DirectEditPolicy {

	/**
	 * Gets the Command to be executed.
	 * 
	 * @param request The request
	 * @return Command The Command to be executed
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		String s = (String) request.getCellEditor().getValue();
		
		WidgetEditPart wep = (WidgetEditPart) getHost();
		Property p = wep.getWidget().findProperty(PropertyTypeConstants.PREVIEW_VALUE);
		if (p != null) {
			return new UpdatePropertyCommand(p, s);
		}
		
		return null;
	}

	/**
	 * Show the current edited value.
	 * 
	 * @param request The request
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
	}
}
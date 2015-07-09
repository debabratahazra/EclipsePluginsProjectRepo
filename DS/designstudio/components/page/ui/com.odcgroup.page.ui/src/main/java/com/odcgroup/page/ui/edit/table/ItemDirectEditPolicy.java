package com.odcgroup.page.ui.edit.table;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * The DirectEditPolicy for Items. We need a special policy because we need to 
 * recompute the 'previewValue' property from the old value and the value of the 
 * CellEditor. 
 * 
 * Note that for Items the previewValue is stored as a comma-separated list of values.
 * The Item EditPolicy should only update the first element in this List. This differs
 * from the EditPolicy for simple Widgets like TextFields which update the entire previewValue
 * property.
 * 
 * @author Gary Hayes
 */
public class ItemDirectEditPolicy extends DirectEditPolicy {
	
	/**
	 * Gets the Command to be executed.
	 * 
	 * @param request The request
	 * @return Command The Command to be executed
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {		
		String pv = (String) request.getCellEditor().getValue();
		Widget w = (Widget) getHost().getModel();
		String newPv = WidgetUtils.getPreviewValue(w, pv, 0);	
		Property p = w.findProperty(PropertyTypeConstants.PREVIEW_VALUE);
		if (p != null) {
			return new UpdatePropertyCommand(p, newPv);
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

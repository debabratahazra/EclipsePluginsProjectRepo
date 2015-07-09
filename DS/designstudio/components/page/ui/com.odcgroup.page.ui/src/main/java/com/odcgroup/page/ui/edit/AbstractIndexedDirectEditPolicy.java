package com.odcgroup.page.ui.edit;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.odcgroup.page.metamodel.PropertyTypeConstants;
import com.odcgroup.page.model.Property;
import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.util.WidgetUtils;
import com.odcgroup.page.ui.command.UpdatePropertyCommand;

/**
 * This is the DirectEditPolicy when the preview values are stored as comma-separated
 * values.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractIndexedDirectEditPolicy extends DirectEditPolicy {
	
	/**
	 * Gets the Command to be executed.
	 * 
	 * @param request The request
	 * @return Command The Command to be executed
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		int index = getSelectedIndex(request);
		Widget w = (Widget) getHost().getModel();
		
		String pv = (String) request.getCellEditor().getValue();
		
		String newPv = WidgetUtils.getPreviewValue(w, pv, index);
		
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
	
	/**
	 * Gets the selected Index.
	 * 
	 * @param request The Request
	 * @return int The selected Index
	 */
	abstract protected int getSelectedIndex(DirectEditRequest request);
}
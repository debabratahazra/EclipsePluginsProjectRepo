package com.odcgroup.page.ui.action;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Action to move a conditionalbody to the left for a conditional.
 * 
 * @author Gary Hayes
 */
public class MoveConditionalBodyLeftAction extends AbstractMoveTabAction {

	/**
	 * Creates a new MoveConditionalBodyLeftAction.
	 * 
	 * @param parameters
	 *            the set of parameters for the concrete action
	 * @param oldEditPart
	 *            the selected edit part. Note that this MUST not be used after the Command
	 *            has been executed since it is no longer associated with the View
	 */
	public MoveConditionalBodyLeftAction(ActionParameters parameters, WidgetEditPart oldEditPart) {
		super(parameters, oldEditPart);
		setEnabled(calculateEnabled());
	}	
	
	
	/**
	 * Gets the flag indicating if we should increment the index.
	 * 
	 * @return boolean
	 */
	protected boolean isIncrement() {
		return false;
	}	

	/**
	 * Returns true if the action is enabled.
	 * 
	 * @return boolean
	 */
	protected boolean calculateEnabled() {
		// Do this test to avoid error when trying to delete the tabbed pane
		if (getParentWidget() != null) {
			// Gets the table widget
			Widget widget = getParentWidget();		
			boolean enabled = (widget != null) && widget.canMoveLeftSelectedChild();
			return enabled;
		}
		else {
			return false;
		}
	}	
}
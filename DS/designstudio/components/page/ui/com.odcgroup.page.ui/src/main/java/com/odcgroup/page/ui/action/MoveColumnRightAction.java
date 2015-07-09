package com.odcgroup.page.ui.action;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Action to move a column to the right for a table.
 * 
 * @author Alexandre Jaquet
 * 
 */
public class MoveColumnRightAction extends AbstractMoveColumnAction {

	/**
	 * Creates a new MoveColumnRightAction.
	 * 
	 * @param parameters
	 *            the set of parameters for the concrete action
	 * @param oldEditPart
	 *            the selected edit part. Note that this MUST not be used after
	 *            the Command has been executed since it is no longer associated
	 *            with the View
	 */
	public MoveColumnRightAction(ActionParameters parameters,
			WidgetEditPart oldEditPart) {
		super(parameters, oldEditPart);
		setEnabled(calculateEnabled());
	}

	/**
	 * Gets the flag indicating if we should increment the index.
	 * 
	 * @return boolean
	 */
	protected boolean isIncrement() {
		return true;
	}

	/**
	 * Returns true if the action is enabled.
	 * 
	 * @return boolean
	 */
	protected boolean calculateEnabled() {
		// Do this test to avoid error when trying to delete the table figure
		if (getParentWidget() != null) {
			// Gets the table widget
			Widget widget = getParentWidget();
			boolean enabled = (widget != null)
					&& widget.canMoveRightSelectedChild();
			return enabled;
		} else {
			return false;
		}

	}
}
package com.odcgroup.page.ui.action;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.DeleteWidgetSetSelectedIndexCommand;

/**
 * Delete the CheckBox from the ColumnHeader
 * 
 * @author Alexandre Jaquet
 *
 */
public class DeleteCheckBoxAction extends AbstractGenericAction {

	/**
	 * Creates a new DeleteCheckBoxAction. 
	 *  
	 * @param parameters the set of parameters for the concrete action
	 */
	public DeleteCheckBoxAction(ActionParameters parameters) {
		super(parameters);
		
		setEnabled(calculateEnabled());
	}
	
	/**
	 * Returns true if the action is enabled.
	 * 
	 * @return boolean
	 */
	protected boolean calculateEnabled() {
		Widget w = getSelectedWidget();
		// A Widget must be selected
		// The ColumnHeader must have a size bigger than 0 to delete an item
		return (w != null) && w.getContents().size() >= 1;
	}
	
	/**
	 * Runs the action. 
	 */
	public void run() {
		Widget widget = getSelectedWidget();
		
		int index = widget.getIndexOfSelectedChild();
		if (index == -1) {
			index = 0;
		}
		Widget selectedChild = widget.getContents().get(index);
		
		DeleteWidgetSetSelectedIndexCommand dwc = new DeleteWidgetSetSelectedIndexCommand(widget, selectedChild);
		execute(dwc);
	}	
}

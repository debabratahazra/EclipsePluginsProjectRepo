package com.odcgroup.page.ui.action;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.command.DeleteWidgetSetSelectedIndexCommand;

/**
 * Deletes a Complex Body from a Complex Widget.
 * 
 * @author Gary Hayes
 */
public class DeleteComplexBodyAction extends AbstractGenericAction {

	/**
	 * Creates a new DeleteComplexBodyAction. 
	 *  
	 * @param parameters the set of parameters for the concrete action
	 */
	public DeleteComplexBodyAction(ActionParameters parameters) {
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
		return (w != null) && w.getIndexOfSelectedChild() != -1 && w.getContents().size() >= 1;
	}
	
	/**
	 * Runs the action. 
	 */
	public void run() {
		Widget widget = getSelectedWidget();
		
		int index = widget.getIndexOfSelectedChild();
		Widget selectedChild = widget.getContents().get(index);
		
		DeleteWidgetSetSelectedIndexCommand dwc = new DeleteWidgetSetSelectedIndexCommand(widget, selectedChild);
		execute(dwc);
	}	
}
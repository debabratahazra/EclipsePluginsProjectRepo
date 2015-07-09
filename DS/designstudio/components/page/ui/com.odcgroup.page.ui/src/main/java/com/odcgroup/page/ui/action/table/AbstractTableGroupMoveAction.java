package com.odcgroup.page.ui.action.table;


import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.model.widgets.table.ITableGroup;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.edit.WidgetEditPart;


/**
 * @author scn
 *
 */
public abstract class AbstractTableGroupMoveAction extends AbstractTableAction {

	/** */
	private WidgetEditPart editPart;

	/**
	 * @param parameters
	 * @param editPart 
	 */
	protected AbstractTableGroupMoveAction(ActionParameters parameters, WidgetEditPart editPart) {
		super(parameters);
		this.editPart = editPart;
	}	
	
	/**
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		
		ITableGroup group = getGroup();
		Widget widget = group.getWidget();
		
		// Gets the new root edit part
		if (editPart == null) {
			return;
		}
		
		EditPartViewer viewer = editPart.getViewer();
		
		execute(getMoveCommand());
		
		
		viewer.deselectAll();
		WidgetEditPart ep = (WidgetEditPart) viewer.getEditPartRegistry().get(widget);
		if(ep!=null) {
			ep.setSelected(EditPart.SELECTED);
			viewer.setFocus(ep);
			viewer.select(ep);
		}
	}	
	
	/**
	 * @return command
	 */
	protected abstract Command getMoveCommand();

	/**
	 * @see com.odcgroup.page.ui.action.AbstractGenericAction#calculateEnabled()
	 */
	protected abstract boolean calculateEnabled();

}

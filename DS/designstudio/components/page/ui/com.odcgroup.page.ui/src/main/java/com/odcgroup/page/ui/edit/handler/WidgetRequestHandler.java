package com.odcgroup.page.ui.edit.handler;

import org.eclipse.gef.commands.CommandStack;

import com.odcgroup.page.model.Widget;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Handles requests received from GEF.
 * 
 * @author atr
 */
public interface WidgetRequestHandler {

	/**
	 * Executes the action.
	 * 
	 * @param ofsProject The OFS project that we are handling this request for
	 * @param commandStack the command stack of the current edit part
	 * @param widget The Widget to execute the action for
	 */
	public void handle(IOfsProject ofsProject, CommandStack commandStack, Widget widget);
}

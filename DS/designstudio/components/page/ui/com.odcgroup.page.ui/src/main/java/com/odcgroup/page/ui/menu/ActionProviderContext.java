package com.odcgroup.page.ui.menu;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.viewers.ISelection;

import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * This is the action provider context. A concrete instance of it
 * is passed to concrete action provider.
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public interface ActionProviderContext {
	
	/**
	 * @return the action registry
	 */
	public ActionRegistry getRegistry();
	
	/**
	 * @return the command stack to be used by action
	 */
	public CommandStack getCommandStack();
	
	/**
	 * @return the current selection within the edit part
	 */
	public ISelection getSelection();
	
	
	/** Gets the WidgetEditPart. 
	 *
	 * @return returns the selected editPart
	 */
	public WidgetEditPart getWidgetEditPart();
	
}

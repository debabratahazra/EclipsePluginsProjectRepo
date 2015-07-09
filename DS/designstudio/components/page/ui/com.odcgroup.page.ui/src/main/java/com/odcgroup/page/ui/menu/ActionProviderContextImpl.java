package com.odcgroup.page.ui.menu;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.viewers.ISelection;

import com.odcgroup.page.ui.edit.WidgetEditPart;

/**
 * Implmenentatation class for the action provider context
 * 
 * @author Alexandre Jaquet
 *
 */
public class ActionProviderContextImpl implements ActionProviderContext {

	/** The command stack used by the provider. */
	private CommandStack commandStack;
	
	/** The action registry used by the provider. */
	private ActionRegistry registry;
	
	/** The current selection .*/
	private ISelection selection;
	
	/** The selected widget edit part. */
	private WidgetEditPart widgetEditPart;

	/**
	 * Gets the command stack. 
	 * 
	 * @return CommandStack returns the current CommandStack
	 */
	public CommandStack getCommandStack() {
		return commandStack;
	}

	/**
	 * Sets the CommandStack of the provider. 
	 * 
	 * @param commandStack
	 * 			The CommandStack
	 */
	public void setCommandStack(CommandStack commandStack) {
		this.commandStack = commandStack;
	}

	/**
	 * Gets the ActionRegistry used by the provider.
	 * 
	 * @return ActionRegistry returns the ActionRegistry used by the provider
	 * 	
	 */
	public ActionRegistry getRegistry() {
		return registry;
	}

	/**
	 * Sets the ActionRegistry of the provider.
	 * 
	 * @param registry 
	 * 			The ActionRegisry
	 */
	public void setRegistry(ActionRegistry registry) {
		this.registry = registry;
	}

	/**
	 * Gets the current ISelection.
	 * 
	 * @return ISelection returns the current Selection
	 */
	public ISelection getSelection() {
		return selection;
	}

	/**
	 * Sets the ISelection of the provider.
	 * 
	 * @param selection
	 * 			The current selection
	 */
	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Gets the current WidgetEditpart
	 * 
	 * @return WidgetEditPart returns the current WidgetEditPart
	 */
	public WidgetEditPart getWidgetEditPart() {
		return widgetEditPart;
	}

	/**
	 * Sets the WidgetEditPart of the actual provider.
	 * 
	 * @param widgetEditPart
	 * 				The WidgetEditPart
	 */
	public void setWidgetEditPart(WidgetEditPart widgetEditPart) {
		this.widgetEditPart = widgetEditPart;
	}
	

}

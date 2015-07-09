package com.odcgroup.page.ui.menu;

import org.eclipse.jface.action.IAction;

import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.util.ClassUtils;

/**
 * Action provider for the move column, tab and condition actions.
 * 
 * @author Alexandre Jaquet
 * @author Gary Hayes
 */
public class MoveActionProvider extends GenericActionProvider {

	/**
	 * Retrieves the action given its descriptor. This action will be invoked if and only if
	 * an action context provider has been defines (not null). So you don't need to check
	 * it in the concrete implementation.
	 * 
	 * @param action the action descriptor
	 * @return an action designated by the action descriptor.
	 */
	protected IAction retrieveActionFromModel(Action action) {

		// prepare action parameters
		ActionParameters params = new ActionParameters(action);
		params.setCommandStack(getCommandStack());
		params.setWidgetList(getSelectedWidgets());
		WidgetEditPart editPart = getContext().getWidgetEditPart();
		// instantiate a new action
		IAction newAction = 
			(IAction)ClassUtils.newInstance(
								// class loader
								getClass().getClassLoader(),
								// fully qualified class name 
								action.getDelegate(), 
								// arguments to pass to the new action 
								new Object[] {params, editPart},
								// the Class to which the action must be assignable
								IAction.class);
		
		// and return it
		return newAction;
	}
}

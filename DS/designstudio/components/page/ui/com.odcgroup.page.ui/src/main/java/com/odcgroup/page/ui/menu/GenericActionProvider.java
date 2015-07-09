package com.odcgroup.page.ui.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.odcgroup.page.model.Widget;
import com.odcgroup.page.ui.action.ActionParameters;
import com.odcgroup.page.ui.edit.WidgetEditPart;
import com.odcgroup.page.uimodel.Action;
import com.odcgroup.page.util.AdaptableUtils;
import com.odcgroup.page.util.ClassUtils;

/**
 * @author Alain Tripod
 * @version 1.0
 */
public class GenericActionProvider extends AbstractActionProvider {

	/**
	 * @return List
	 */
	protected List<Widget> getSelectedWidgets() {
		List<Widget> list = new ArrayList<Widget>();
		ISelection s = getSelection();
		if (s instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) s;
			Iterator<?> it = ss.iterator();
			while (it.hasNext()) {
				Widget w = (Widget) AdaptableUtils.getAdapter(it.next(), Widget.class);
				if (w != null) {
					list.add(w);
				}
				
			}
		}
		return list;
	}

	/**
	 * Retrieves the action given its descriptor. This action will be invoked if and only if
	 * an action context provider has been defines (not null). So you don't need to check
	 * it in the concrete implementation.
	 * 
	 * @param action the action descriptor
	 * 
	 * @return an action designated by the action descriptor.
	 */
	protected IAction retrieveActionFromModel(Action action) {

		// prepare action parameters
		ActionParameters params = new ActionParameters(action);
		params.setCommandStack(getCommandStack());
		params.setWidgetList(getSelectedWidgets());
		WidgetEditPart editPart = getContext().getWidgetEditPart();

		// Pass the widget edit part if supported. Useful to select a widget just created.
		Object[] args;
		if (editPart != null && 
				ClassUtils.hasConstructor(getClass().getClassLoader(), action.getDelegate(), new Class[] { params.getClass(), WidgetEditPart.class })) {
			args = new Object[] { params, editPart };
		} else {
			args = new Object[] { params };
		}
		
		// Instantiate a new action
		IAction newAction = 
			(IAction)ClassUtils.newInstance(
								// class loader
								getClass().getClassLoader(),
								// fully qualified class name 
								action.getDelegate(), 
								// arguments to pass to the new action 
								args,
								// the Class to which the action must be assignable
								IAction.class);
		
		// and return it
		return newAction;
		
	}

}

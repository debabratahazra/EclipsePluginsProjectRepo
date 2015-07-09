package com.odcgroup.page.ui.menu;

import org.eclipse.jface.action.IAction;

import com.odcgroup.page.uimodel.Action;

/**
 * Provides a standard action given an action descriptor.
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public class StandardActionProvider extends AbstractActionProvider implements ActionProvider {

	/**
	 * Retrieves the action given its descriptor. This action will be invoked if and only if
	 * an action context provider has been defines (not null). So you don't need to check
	 * it in the concrete implementation.
	 * 
	 * @param action the action descriptor
	 * 
	 * @return an action designated by the action descriptor.
	 */
	public IAction retrieveActionFromModel(Action action) {
		IAction result = null;
		String id = normalizeId(action.getId());
		if (id.length() > 0) {
			String stdId = getIdMap().get(id);
			if (stdId != null) {
				result = getActionRegistry().getAction(stdId);
			}
		}
		return result;
	}

}

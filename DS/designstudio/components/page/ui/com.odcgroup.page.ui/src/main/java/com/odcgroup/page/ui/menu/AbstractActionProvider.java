package com.odcgroup.page.ui.menu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;

import com.odcgroup.page.log.Logger;
import com.odcgroup.page.uimodel.Action;


/**
 * This is the base class for all concrete action providers<p>
 * 
 * @author Alain Tripod
 * @version 1.0
 */
abstract class AbstractActionProvider implements ActionProvider {
	
	/** Error message. */
	private static String MESSAGE_CONTEXT_IS_NULL =
		"The action provider context cannot be null";
	
	/** Error message. */
	private static String MESSAGE_DEFINE_CONTEXT =
		"Please, define the action provider context by calling the method 'setContext'";
	
	/** Error message. */
	private static String MESSAGE_ACTION_DESCRIPTOR_IS_NULL =
		"The action descriptor cannot be null";

	
	/** The current context */
	private ActionProviderContext context;
	
	/** Bind all standard action's id to lower case equivalences */
	private Map<String, String> standardIds = new HashMap<String, String>();
	
	/**
	 * This method constructs a map, to bind a lower case representation
	 * of action id, to their real value.<p>
	 * This will make the research of action more easier.
	 */
	@SuppressWarnings("unchecked")
	private void rebindActionIds() {
		standardIds.clear();
		Iterator it = getActionRegistry().getActions();
		while (it.hasNext()) {
			String stdId = ((IAction)it.next()).getId();
			String id = normalizeId(stdId);
			if (id.length() > 0) {
				standardIds.put(normalizeId(id), stdId);
			}
		}
	}
	
	/**
	 * Normalize the standard action id
	 * 
	 * @param id the action id to normalize
	 * @return a normalized id
	 */
	protected final String normalizeId(String id) {
		return (id != null) ? id.trim().toLowerCase() : "";
	}

	/**
	 * @return The map of action identifiers
	 */
	protected final Map<String, String> getIdMap() {
		return standardIds;
	}
	
	/**
	 * @return the current context
	 */
	protected final ActionProviderContext getContext() {
		return this.context;
	}
	
	/**
	 * @return the current action registry
	 */
	protected final ActionRegistry getActionRegistry() {
		return getContext().getRegistry();
	}
	
	/**
	 * @return the current command stack
	 */
	protected final CommandStack getCommandStack() {
		return getContext().getCommandStack();
	}

	/**
	 * @return the current selection
	 */
	protected final ISelection getSelection() {
		return getContext().getSelection();
	}

	/**
	 * Cannot be instantiated
	 */
	protected AbstractActionProvider() {
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
	protected abstract IAction retrieveActionFromModel(Action action);
		
	/**
	 * Defines the context to be used by this action provider. This method is
	 * always called before the method <code>getAction</code>.
	 * 
	 * @param context
	 *            the context
	 */
	public void setContext(ActionProviderContext context) {
		Assert.isNotNull(context, MESSAGE_CONTEXT_IS_NULL);
		
		this.context = context;
		rebindActionIds();
	}

	/**
	 * Returns the concrete action designated by the action descriptor, or
	 * <code>Null</code> if there is no corresponding concrete action.
	 * <p>
	 * 
	 * If no action provider context has been previously set, then no action
	 * will be returned by this method, so call the method
	 * <code>setContext</call> before invoking <code>getAction</code>.<p>
	 * 
	 * @param action
	 *            the action descriptor
	 *            
	 * @return the concrete action
	 */
	public IAction getAction(Action action) {
		Assert.isNotNull(action, MESSAGE_ACTION_DESCRIPTOR_IS_NULL);
		IAction result = null;
		ActionProviderContext ctx = getContext();
		if (ctx != null) {
			result = retrieveActionFromModel(action);
		} else {
			Logger.warning(MESSAGE_DEFINE_CONTEXT);
		}
		return result;
	}
	
}

package com.odcgroup.page.ui.menu;

import org.eclipse.jface.action.IAction;

import com.odcgroup.page.uimodel.Action;

/**
 * A <Code>ActionProvider</code> concrete instance is in charge to retrieve an
 * action given an action descriptor (Action). The retrieving process is based
 * on the current information maintained by an <code>ActionProviderContext</code> 
 * set by calling the method </code>setContext</code>
 * 
 * @author Alain Tripod
 * @version 1.0
 */
public interface ActionProvider {

	/**
	 * Defines the context to be used by this action provider. This method is
	 * always called before the method <code>getAction</code>.
	 * 
	 * @param ctx
	 *            the context
	 */
	public void setContext(ActionProviderContext ctx);

	/**
	 * Returns the concrete action designated by the action descriptor, or
	 * <code>Null</code> if there is no corresponding concrete action.
	 * <p>
	 * 
//	 * If no action provider context has been previously set, then no action
	 * will be returned by this method, so call the method
	 * <code>setContext</call> before invoking <code>getAction</code>.<p>
	 * 
	 * @param action
	 *            the action descriptor
	 *            
	 * @return the concrete action
	 */
	public IAction getAction(Action action);

}

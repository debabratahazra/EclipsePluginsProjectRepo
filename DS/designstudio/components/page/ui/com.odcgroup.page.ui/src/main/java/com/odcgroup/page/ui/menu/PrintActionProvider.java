package com.odcgroup.page.ui.menu;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;

import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.page.uimodel.Action;

/**
 * The provider of the print action, it delegate the action retrieving
 * and set an image for the action.
 * 
 * @author Gary Hayes
 *
 */
public class PrintActionProvider extends AbstractActionProvider {
	
	/** The icon image of the action. */
	private static ImageDescriptor IMAGE = PageUIPlugin.getImageDescriptor("icons/obj16/print.png");
	
	/** The standard action provider where we delegate*/
	private StandardActionProvider delegate;

	/**
	 * Constructor
	 */
	public PrintActionProvider() {
		delegate = new StandardActionProvider();
	}
	/**
	 * Retrieve the action from the model.
	 * 
	 * @param action
	 * 			The action to retrieve
	 */
	@Override
	protected IAction retrieveActionFromModel(Action action) {
		IAction a = delegate.retrieveActionFromModel(action);
		a.setImageDescriptor(IMAGE);
		a.setText("Print ...");
		return a;
	}
	
	/**
	 * Defines the context to be used by this action provider. This method is
	 * always called before the method <code>getAction</code>.
	 * 
	 * @param context
	 *            The context
	 */
	public void setContext(ActionProviderContext context) {
		super.setContext(context);
		delegate.setContext(context);
	}	

}

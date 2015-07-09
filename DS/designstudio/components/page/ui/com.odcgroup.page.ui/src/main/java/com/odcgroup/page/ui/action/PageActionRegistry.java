package com.odcgroup.page.ui.action;

import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.PrintAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * The ActionRegistry for the Page Designer.
 * 
 * @author Gary Hayes
 */
public class PageActionRegistry extends ActionRegistry {
	
	/**
	 * Creates a new PageActionRegistry.
	 * 
	 * @param workbenchPart The IWorkbenchPart
	 */
	public PageActionRegistry(IWorkbenchPart workbenchPart) {
		
		// register action "print"
		registerAction(new PrintAction(workbenchPart));
		
		// register action "create-image" 
		registerAction(new CreateImageAction(workbenchPart));
		
	}
}

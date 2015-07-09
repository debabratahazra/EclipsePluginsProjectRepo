package com.odcgroup.page.ui.workbench;

import com.odcgroup.page.ui.wizard.NewPageWizard;

/**
 * Action called when a new page is created.
 */
public class NewPageAction extends AbstractNewAction {

	/**
	 * Creates a new NewPageAction.
	 */
	public NewPageAction() {
		super("New Page...", "icons/obj16/page.png");
	}
	
	
	/**
	 * 
	 * @return Object
	 */
	protected Object getRegisteredWizardClass(){		
		return new NewPageWizard();
	}	
}
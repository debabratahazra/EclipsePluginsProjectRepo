package com.odcgroup.page.ui.workbench;

import com.odcgroup.page.ui.wizard.NewModuleWizard;

/**
 * Action called when a new module is created.
 */
public class NewModuleAction extends AbstractNewAction {

	/**
	 * Creates a new NewModuleAction.
	 */
	public NewModuleAction() {
		super("New Module...", "icons/obj16/module.png");
	}
	
	
	/**
	 * 
	 * @return Object
	 */
	protected Object getRegisteredWizardClass(){		
		return new NewModuleWizard();
	}	
}
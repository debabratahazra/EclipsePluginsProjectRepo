package com.odcgroup.page.ui.workbench;

import com.odcgroup.page.ui.wizard.NewFragmentWizard;

/**
 * Action called when a new fragment is created.
 */
public class NewFragmentAction extends AbstractNewAction {

	/**
	 * Creates a new NewFragmentAction.
	 */
	public NewFragmentAction() {
		super("New Fragment...", "icons/obj16/fragment.png");
	}
	
	
	/**
	 * 
	 * @return Object
	 */
	protected Object getRegisteredWizardClass(){		
		return new NewFragmentWizard();
	}	
}
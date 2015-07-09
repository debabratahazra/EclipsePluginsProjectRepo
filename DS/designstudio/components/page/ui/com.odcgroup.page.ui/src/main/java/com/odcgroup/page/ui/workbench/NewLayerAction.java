package com.odcgroup.page.ui.workbench;

import com.odcgroup.page.ui.wizard.NewLayerWizard;

/**
 * Action called when a new layer is created.
 */
public class NewLayerAction extends AbstractNewAction {

	/**
	 * Creates a new NewLayerAction.
	 */
	public NewLayerAction() {
		super("New Layer...", "icons/obj16/fragment.png");
	}
	
	
	/**
	 * 
	 * @return Object
	 */
	protected Object getRegisteredWizardClass(){		
		return new NewLayerWizard();
	}	
}
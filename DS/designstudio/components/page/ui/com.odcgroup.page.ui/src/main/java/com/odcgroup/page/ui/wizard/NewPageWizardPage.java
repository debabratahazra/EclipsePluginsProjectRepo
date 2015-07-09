package com.odcgroup.page.ui.wizard;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.workbench.ui.help.IOfsHelpContextIds;

/**
 * Wizard Page for creating a new Page.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public class NewPageWizardPage extends AbstractPageDesignerWizardPage {

	/**
	 * Initializes the Wizard.
	 * 
	 * @param specification
	 *            The specification of the new model
	 */
	public NewPageWizardPage(ModelSpecification specification) {
		super("New Page", specification);
	}
	
	/**
	 * Setup help
	 * @param parent
	 */
	public void createControl(Composite parent) {
        super.createControl(parent);
        
		// Set help context
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IOfsHelpContextIds.NEW_PAGE);
	}
	

}

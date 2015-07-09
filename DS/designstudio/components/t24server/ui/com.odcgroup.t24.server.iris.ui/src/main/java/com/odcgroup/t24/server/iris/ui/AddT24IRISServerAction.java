package com.odcgroup.t24.server.iris.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author satishnangi
 *
 */
public class AddT24IRISServerAction extends Action {
	private static Logger logger = LoggerFactory.getLogger(AddT24IRISServerAction.class);
	
	public AddT24IRISServerAction() {
		setText("Add New T24 IRIS Server...");
	}
	
	@Override
	public void run() {
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
		   	.getNewWizardRegistry().findWizard("com.odcgroup.workbench.ui.wizards.NewTemplateWizard");
		IWorkbenchWizard createWizard = null;
		if (descriptor != null) {
			try {
				 createWizard = descriptor.createWizard();
			} catch (CoreException e) {
				e.printStackTrace();
			}
			WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), createWizard);
			dialog.open();
		} else {
			logger.error("Unable to find the new project wizard");
		}
	}

}

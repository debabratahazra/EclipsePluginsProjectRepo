package com.odcgroup.ocs.server.embedded.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;

import com.odcgroup.ocs.server.ui.OcsServerUICore;

/**
 * Action that add new embedded server
 */
public class AddEmbeddedServerAction extends Action {

	public AddEmbeddedServerAction() {
		setText("Add New Embedded Server...");
	}

	@Override
	public void run() {
		IWizardDescriptor descriptor = PlatformUI.getWorkbench()
		   	.getNewWizardRegistry().findWizard("com.odcgroup.workbench.ui.wizards.NewTemplateWizard");
		if (descriptor != null) {
			try {
				IWizard wizard = descriptor.createWizard();
				WizardDialog wd = new  WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), wizard);
				wd.setTitle(wizard.getWindowTitle());
				wd.open();
			} catch (CoreException e) {
				// Ignore
			}
		} else {
			Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
			ErrorDialog.openError(
					shell,
					"Error opening New Template wizard",
					"Wizard Editor cannot be opened",
					new Status(IStatus.ERROR,
							OcsServerUICore.PLUGIN_ID,
							"Unable to find the New Template wizard", null));
		}
	}

}

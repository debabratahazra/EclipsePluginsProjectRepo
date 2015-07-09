package com.odcgroup.integrationfwk.ui.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProjectFactory;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;

public class TWSConsumerWizard extends Wizard implements INewWizard,
		IExecutableExtension {

	private TWSConsumerProjectDialog pageOne;
	private IConfigurationElement confgElement;
	/** instance of {@link TWSConsumerWizardHelper} */
	private TWSConsumerWizardHelper twsConsumerWizardHelper;
	private static final String PAGE_NAME = "TWS_Consumer_Wizard_Page1";

	public TWSConsumerWizard() {
		setWindowTitle(StringConstants._UI_WIZARD_CREATE_INTEGRATION_PROJECT_TITLE);
	}

	@Override
	public void addPages() {
		pageOne = new TWSConsumerProjectDialog(PAGE_NAME);
		pageOne.setTitle(StringConstants._UI_WIZARD_CREATE_INTEGRATION_PROJECT);
		pageOne.setDescription("Enter a project name");
		addPage(pageOne);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection) this is setting the
	 * progress monitor bar
	 */

	private void displayErrorDialog(Exception e) {
		IStatus status = new Status(IStatus.ERROR, "Error", e.getMessage());
		ErrorDialog.openError(getShell(), "An error occurred", null, status);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean performCancel() {
		try {
			if (twsConsumerWizardHelper != null) {
				TWSConsumerProjectFactory projectFactory = new TWSConsumerProjectFactory();
				projectFactory.deleteProject(twsConsumerWizardHelper
						.getCreatedProject());
			}
		} catch (CoreException e) {
			TWSConsumerLog.logError(e);
		}

		return super.performCancel();
	}

	/*
	 * This is what is invoked when user click finish button if exception
	 * occured then the status return is false
	 */
	@Override
	public boolean performFinish() {
		boolean status = false;
		twsConsumerWizardHelper = new TWSConsumerWizardHelper(pageOne);
		try {

			getContainer().run(false, false, twsConsumerWizardHelper);

			// this will switch to our custom perspective

			BasicNewProjectResourceWizard.updatePerspective(confgElement);
			status = twsConsumerWizardHelper.getStatus();
		}

		catch (Exception e) {
			status = false;
			TWSConsumerLog.logError(e);
			displayErrorDialog(e);
		}
		return status;
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		confgElement = config;
	}
}
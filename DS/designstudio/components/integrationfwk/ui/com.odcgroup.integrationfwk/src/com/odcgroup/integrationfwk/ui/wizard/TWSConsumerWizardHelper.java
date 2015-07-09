package com.odcgroup.integrationfwk.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.common.MasterDataBuilder;
import com.odcgroup.integrationfwk.ui.prefprop.TWSPropertiesView;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProjectFactory;
import com.odcgroup.integrationfwk.ui.projects.TWSProjectPreferences;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

/*
 * This is a class that runs on behalf of TWSConsumer and 
 * it's run method contains all the work.
 */
public class TWSConsumerWizardHelper implements IRunnableWithProgress {

	// use status variable to report the outcome of run method
	// becasue run is void and can not return anything
	private boolean status;
	private final TWSConsumerProjectDialog pageOne;
	/** instance of {@link TWSConsumerProject} */
	private TWSConsumerProject project;

	public TWSConsumerWizardHelper(TWSConsumerProjectDialog pageOne) {
		this.pageOne = pageOne;
	}

	private void displayErrorDialog(Exception e) {
		IStatus status = new Status(IStatus.ERROR, "Error", e.getMessage());
		// ErrorDialog.openError(this.getShell(), "An error occurred", null,
		// status);
		ErrorDialog.openError(null, "An error occurred", null, status);
	}

	// TODO: Check the correctness of the name
	private void displayT24ServicesErrorDialog() {
		String errorMessage = "An error occurred while getting the T24 Services from T24. Check the Temenos Web Services properties for the project are correct. See the Error Log for full details.";
		IStatus status = new Status(IStatus.ERROR, "Error", errorMessage);
		// /ErrorDialog.openError(this.getShell(),
		// "Error getting the T24 Services from T24", null, status);
		ErrorDialog.openError(null, "Error getting the T24 Services from T24",
				null, status);
	}

	/**
	 * get the landscape url
	 * 
	 * @return
	 */
	private String getComponentServiceURL() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			return twsPropertiesView().getComponentServiceURL();
		} else {
			return twsProjectProperties().getComponentServiceURLPreference();
		}
	}

	/**
	 * Helps to get the created project.
	 * 
	 * @return instance of {@link TWSConsumerProject}
	 */
	public TWSConsumerProject getCreatedProject() {
		return project;
	}

	private String getHost() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			return twsPropertiesView().getHost();
		} else {
			return twsProjectProperties().getHostPreference();
		}
	}

	private String getOfsSource() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			return twsPropertiesView().getOfsSource();
		} else {
			return twsProjectProperties().getOfsSourcePreference();
		}
	}

	private String getPassword() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			return twsPropertiesView().getPassword();
		} else {
			return twsProjectProperties().getPasswordPreference();
		}
	}

	private int getPort() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			try {
				return Integer.parseInt(twsPropertiesView().getPort());
			} catch (NumberFormatException nfe) {
				return twsProjectProperties().getPortPreference();
			}
		} else {
			return twsProjectProperties().getPortPreference();
		}
	}

	public boolean getStatus() {
		return status;
	}

	private String getUserName() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			return twsPropertiesView().getUserName();
		} else {
			return twsProjectProperties().getUserNamePreference();
		}
	}

	/**
	 * 
	 * @return true if agent connection, false otherwise.
	 */
	private boolean isAgentConnection() {
		if (twsPropertiesView().getUseProjectSpecificSettings()) {
			return twsPropertiesView().getAgentConnection();
		} else {
			return new Boolean(twsProjectProperties()
					.getAgentConnectionPreference());
		}
	}

	/**
	 * Method which helps to validate the agent details. If any empty values
	 * found, a dialog will be shown to the user as whether they want to use the
	 * default preference value, and then proceed based on the user's selection.
	 * 
	 * @return true if valid to proceed/user wants to correct the details, false
	 *         otherwise
	 */
	private boolean isValidToProceed() {
		if (!isAgentConnection()) {
			return true;
		}
		return TWSConsumerProjectUtil.isValidAgentDetails(twsPropertiesView()
				.getHost(), twsPropertiesView().getPort(), twsPropertiesView()
				.getOfsSource());
	}

	/*
	 * This is a method that is executed from TWSConsumerWizard
	 */
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {

		status = false;

		monitor.beginTask(
				StringConstants._UI_WIZARD_CREATING_INTEGRATION_PROJECT, 1000);
		monitor.setTaskName(StringConstants._UI_WIZARD_CREATING_INTEGRATION_PROJECT);
		SubProgressMonitor subMonitor = new SubProgressMonitor(monitor, 218);
		monitor.worked(100);
		// /Do something
		String ProjectExplorer_VIEW_ID = "org.eclipse.ui.navigator.ProjectExplorer";
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();

		try {
			// just like the Perspective
			workbenchWindow.getActivePage().showView(ProjectExplorer_VIEW_ID);
			monitor.worked(218);
			URI customLocationURI = null;
			if (pageOne.getLocationOption()) {
				new URI("file:/"
						+ pageOne.getProjectLocation().replace('\\', '/') + "/"
						+ pageOne.getProjectName());
			} else {
				customLocationURI = new URI("file:/"
						+ pageOne.getProjectLocation().replace('\\', '/') + "/"
						+ pageOne.getProjectName());
			}
			TWSConsumerProjectFactory twsConsumerProjectFactory = new TWSConsumerProjectFactory();
			project = twsConsumerProjectFactory.createProject(
					pageOne.getProjectName(), customLocationURI);
			project.setUseProjectSpecificSettings(twsPropertiesView()
					.getUseProjectSpecificSettings());
			monitor.worked(900);
			// possibly a common block
			if (twsPropertiesView().getUseProjectSpecificSettings()) {
				if (!isValidToProceed()) {
					return;
				}
				updateAgentDetails();
				project.setHost(getHost());
				project.setPort(getPort());
				project.setOfsSource(getOfsSource());
				project.setAgentConnection(isAgentConnection());
				project.setComponentServiceURL(getComponentServiceURL());
				project.setUserName(getUserName());
				project.setPassword(getPassword());
			}
			monitor.worked(5900);
			if (TWSConsumerProjectUtil.isValidConnection(project, subMonitor)) {
				MasterDataBuilder.startBuilding(project);
			} else {
				// user can correct the project settings if wrong.
				return;
			}
			monitor.worked(5950);
			// change the status when we get to the end of the run
			status = true;
		} catch (Exception ef) {
			status = false;// not really required
			// Added to fix the defect 353169
			IProgressMonitor progressMonitor = new NullProgressMonitor();
			if (ef instanceof IllegalArgumentException) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(pageOne.getProjectName());
				if (project.exists()) {
					try {
						project.delete(true, true, new SubProgressMonitor(
								progressMonitor, 10));
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
			// Defect 353169 ends here
			TWSConsumerLog.logError(ef);
			displayErrorDialog(ef);
			return;
		}
		monitor.done();
	}

	private TWSProjectPreferences twsProjectProperties() {
		return new TWSProjectPreferences();
	}

	private TWSPropertiesView twsPropertiesView() {
		return pageOne.getTWSCredentials();
	}

	/**
	 * Method which updates the agent details such as host, port, ofs with
	 * default preference if any one of those has left blank.
	 */
	private void updateAgentDetails() {
		if (twsPropertiesView().getHost().length() == 0) {
			twsPropertiesView().setHost(
					twsProjectProperties().getHostPreference());
		}
		if (twsPropertiesView().getPort().length() == 0) {
			twsPropertiesView().setPort(
					String.valueOf(twsProjectProperties().getPortPreference()));
		}
		if (twsPropertiesView().getOfsSource().length() == 0) {
			twsPropertiesView().setOfsSource(
					twsProjectProperties().getOfsSourcePreference());
		}
	}
}

package com.odcgroup.integrationfwk.ui.utils;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.common.ConnectionValidator;
import com.odcgroup.integrationfwk.ui.common.SubroutineConstants;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;

/**
 * Class which contains utility methods to help out the TWS Consumer project,
 * its preferences and its properties.
 * 
 * @author sbharathraja
 * 
 */
// TODO: error dialog methods been placed here for time-being. Need to think
// about its generic
public final class TWSConsumerProjectUtil {

	private static void displayConnectionErrorDialog() {
		IStatus status = new Status(IStatus.ERROR, "Error",
				SubroutineConstants.T24_CONNECTION_ERROR);
		ErrorDialog.openError(null, "Error connecting to T24", null, status);
	}

	/**
	 * method which display the error dialog about empty url.
	 */
	private static void displayEmptyURLErrorDialog() {
		String errorMessage = "Landscape or FlowService URL is Empty!";
		IStatus status = new Status(IStatus.ERROR, "Error", errorMessage);
		ErrorDialog.openError(Display.getCurrent().getActiveShell(),
				"Error in connection", null, status);
	}

	/**
	 * method to display the error dialog if there seems to be connection
	 * problem while the user trying to connect T24 through webservices.
	 */
	private static void displayT24WebServicesConnectionErrorDialog() {
		String _logLocation = TWSConsumerLog.getLogLocation();
		String errorMessage = "An error occurred while getting the T24 Services from T24. Check the user credentials and connection url. See the Error Log in location  "
				+ _logLocation + "  for full details.";
		IStatus status = new Status(IStatus.ERROR, "Error", errorMessage);
		ErrorDialog.openError(Display.getCurrent().getActiveShell(),
				"Error in connection", null, status);
	}

	/**
	 * Helps to get the catalog service url.
	 * 
	 * @param componentServiceURL
	 *            - component service url.
	 * @return constructed catalog service url.
	 */
	public static final String getCatalogServiceURL(String componentServiceURL) {
		if (componentServiceURL
				.endsWith(StringConstants.COMP_URL_SPLITTING_CHAR)) {
			componentServiceURL = componentServiceURL.substring(0,
					componentServiceURL.length() - 1);
		}
		return componentServiceURL + StringConstants.COMP_URL_SPLITTING_CHAR
				+ StringConstants.COMP_SERVICES_STRING
				+ StringConstants.COMP_URL_SPLITTING_CHAR
				+ StringConstants.COMP_CATALOG_SERVICE_STRING;
	}

	/**
	 * Helps to get the flow service url.
	 * 
	 * @param componentServiceURL
	 *            - component service url
	 * @return constructed flow service url.
	 */
	public static final String getFlowServiceURL(String componentServiceURL) {
		if (componentServiceURL
				.endsWith(StringConstants.COMP_URL_SPLITTING_CHAR)) {
			componentServiceURL = componentServiceURL.substring(0,
					componentServiceURL.length() - 1);
		}
		return componentServiceURL + StringConstants.COMP_URL_SPLITTING_CHAR
				+ StringConstants.COMP_SERVICES_STRING
				+ StringConstants.COMP_URL_SPLITTING_CHAR
				+ StringConstants.COMP_FLOW_SERVICES_STRING;
	}

	/**
	 * Helps to get the landscape service url.
	 * 
	 * @param componentServiceURL
	 *            - component service url.
	 * 
	 * @return constructed landscape service url.
	 */
	public static final String getLandscapeServiceURL(String componentServiceURL) {
		if (componentServiceURL
				.endsWith(StringConstants.COMP_URL_SPLITTING_CHAR)) {
			componentServiceURL = componentServiceURL.substring(0,
					componentServiceURL.length() - 1);
		}
		return componentServiceURL + StringConstants.COMP_URL_SPLITTING_CHAR
				+ StringConstants.COMP_SERVICES_STRING
				+ StringConstants.COMP_URL_SPLITTING_CHAR
				+ StringConstants.COMP_LANDSCAPE_SERVICES_STRING;
	}

	/**
	 * Helps to check whether the given details such as host, port and ofs
	 * regards to agent connection has filled properly or not. Those three
	 * fields cannot be blank.
	 * 
	 * @param host
	 *            - host name
	 * @param port
	 *            - port number
	 * @param ofsSource
	 *            - ofs id
	 * @return true if valid, false otherwise.
	 */
	public static boolean isValidAgentDetails(String host, String port,
			String ofsSource) {
		if (host.length() == 0 || port.length() == 0 || ofsSource.length() == 0) {
			String message = "Empty/invalid agent details found. Do you want to continue with default preference details?";
			return MessageDialog.openConfirm(null, "Invalid agent details",
					message);
		}
		return true;
	}

	/**
	 * Method which validate the connection details.
	 * <p>
	 * Real business logic for validation goes here.
	 * 
	 * @param project
	 *            - instance of {@link TWSConsumerProject}
	 * @return true if valid connection, false otherwise.
	 */
	public static final boolean isValidConnection(TWSConsumerProject project) {
		ConnectionValidator validator = new ConnectionValidator();
		if (project.isAgentConnection()) {
			// check the T24 connection through agent
			if (!validator.isValidAgentConnection(project)) {
				displayConnectionErrorDialog();
				return false;
			}
			return true;
		} else {
			// check the empty url's
			if ("".equalsIgnoreCase(project.getComponentServiceURL())) {
				displayEmptyURLErrorDialog();
				return false;
			}
			// validate the url's
			boolean isValidFlowServiceURL = validator
					.isValidFlowServiceURL(project.getComponentServiceURL());
			boolean isValidCatalogServiceURL = validator
					.isValidCatalogServiceURL(project
							.getComponentServiceURL());
			boolean isValidLandScapeURL = validator.isValidLandScapeURL(
					project.getComponentServiceURL(), project.getPassword(),
					project.getUserName(), project);
			if (!isValidLandScapeURL || !isValidFlowServiceURL
					|| !isValidCatalogServiceURL) {
				displayT24WebServicesConnectionErrorDialog();
				return false;
			}
			return true;
		}
	}

	/**
	 * Method which validate the connection details.
	 * 
	 * @param project
	 *            - instance of {@link TWSConsumerProject}
	 * @param subMonitor
	 *            - monitor to be shown in progress bar.
	 * @return true if valid connection, false otherwise.
	 */
	public static final boolean isValidConnection(TWSConsumerProject project,
			SubProgressMonitor subMonitor) {
		subMonitor.beginTask("Validate Connection", 100);
		subMonitor.setTaskName("Validating the Connection");
		subMonitor.worked(50);
		boolean isValid = isValidConnection(project);
		subMonitor.worked(50);
		subMonitor.done();
		return isValid;
	}

}

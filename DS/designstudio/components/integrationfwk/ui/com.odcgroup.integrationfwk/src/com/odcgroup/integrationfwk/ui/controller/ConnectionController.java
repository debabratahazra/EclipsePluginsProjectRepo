package com.odcgroup.integrationfwk.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.odcgroup.integrationfwk.ui.model.ComponentServiceField;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Parameter;
import com.odcgroup.integrationfwk.ui.model.SourceType;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;

/**
 * Controller class which controls the connections to T24, both JAgent and Web
 * Services.
 * 
 */

public class ConnectionController {

	static private ConcurrentMap<String, JAgentController> jAgentControllers = new ConcurrentHashMap<String, JAgentController>();
	static private ConcurrentMap<String, WebServiceController> webServicesControllers = new ConcurrentHashMap<String, WebServiceController>();
	private static ConnectionController connectionController;

	public static ConnectionController createConnectionController(
			TWSConsumerProject currentProject) {
		if (connectionController == null) {
			connectionController = new ConnectionController();

			return connectionController;
		} else {
			return connectionController;
		}
	}

	private ConnectionController() {
	}

	private List<String> getApplicaitonsVersions(
			TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getApplicationsVersions();
	}

	public List<Field> getApplicationFields(TWSConsumerProject currentProject,
			String applicationName) {
		return getT24Connection(currentProject).getApplicationFields(
				applicationName);
	}

	public List<String> getApplicationList(TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getApplicationList();
	}

	/**
	 * Method which helps to get the event input for visual flow page based on
	 * the exit point type.
	 * 
	 * @param currentProject
	 *            - current consumer project.
	 * @param exitPointType
	 *            - type of the exit point (Version/Application/Component
	 *            Service/ TSA Service)
	 * @return list of available inputs.
	 */
	public List<String> getEventInputList(TWSConsumerProject currentProject,
			SourceType exitPointType) {
		switch (exitPointType) {
		case COMPONENT_SERVICE:
			return getServiceOperationsList(currentProject);
		case TSA_SERVICE:
			return getApplicationList(currentProject);
		case VERSION:
			return getApplicationList(currentProject);
		case APPLICATION:
			return getApplicationList(currentProject);
		}
		// return empty list in case of exception
		return new ArrayList<String>();
	}

	public List<String> getExitPointList(TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getExitPoints();
	}

	private T24Connection getJagentConnection(TWSConsumerProject currentProject) {
		String projectName = currentProject.getProject().getName();
		JAgentController jAgentController = jAgentControllers.get(projectName);
		if (jAgentController != null) {
			return jAgentController;
		} else {
			jAgentController = new JAgentController(currentProject);
			jAgentControllers.put(projectName, jAgentController);
			return jAgentController;
		}
	}

	public String[] getOperationList(TWSConsumerProject currentProject,
			String serviceName) {
		return getT24Connection(currentProject).getOperationList(serviceName)
				.toArray(new String[0]);
	}

	public List<String> getOverrideList(TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getOverrides();
	}

	/**
	 * Method which helps to get the parameter field list using given project
	 * with given service and operation.
	 * 
	 * @param currentProject
	 *            - consumer project.
	 * @param service
	 *            - name of the component service.
	 * @param operation
	 *            - operation of the component service.
	 * @return list which contains the instance of {@link Field}
	 */
	public List<Field> getParameterFieldList(TWSConsumerProject currentProject,
			String service, String operation) {
		List<Field> fieldList = new ArrayList<Field>();
		for (java.util.ListIterator<Parameter> paramIterator = getT24Connection(
				currentProject).getParameterList(service, operation)
				.listIterator(); paramIterator.hasNext();) {
			Parameter param = paramIterator.next();
			Field field = new ComponentServiceField();
			field.setFieldName(param.getName());
			field.setFieldType(param.getTypeName());
			// display name will be added only when the field is selected for
			// enrichment.
			field.setDisplayName("");
			fieldList.add(field);
		}
		return fieldList;
	}

	public String[] getServiceList(TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getServiceList().toArray(
				new String[0]);
	}

	/**
	 * Method which helps to get the list of service and its operations with
	 * comma splitted.
	 * 
	 * @param currentProject
	 *            - current consumer project.
	 * @return list of string.
	 */
	private List<String> getServiceOperationsList(
			TWSConsumerProject currentProject) {
		List<String> serviceOperationList = new ArrayList<String>();
		for (String service : getServiceList(currentProject)) {
			for (String operation : getOperationList(currentProject, service)) {
				serviceOperationList.add(service + "," + operation);
			}
		}
		return serviceOperationList;
	}

	private T24Connection getT24Connection(TWSConsumerProject currentProject) {
		if (currentProject.isAgentConnection()) {
			return getJagentConnection(currentProject);
		} else {
			return getWebServicesConnection(currentProject);
		}
	}

	/**
	 * Helps to get the list of TSA Services using the connection details
	 * available in given project.
	 * 
	 * @param currentProject
	 * @return list of TSA Services.
	 */
	public List<String> getTsaServicesList(TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getTSAServicesList();
	}

	public List<String> getVersionList(TWSConsumerProject currentProject) {
		return getT24Connection(currentProject).getVersionList();
	}

	private T24Connection getWebServicesConnection(
			TWSConsumerProject currentProject) {
		String projectName = currentProject.getProject().getName();
		WebServiceController webServicesController = webServicesControllers
				.get(projectName);
		if (webServicesController != null) {
			return webServicesController;
		} else {
			webServicesController = new WebServiceController(currentProject);
			webServicesControllers.put(projectName, webServicesController);
			return webServicesController;
		}
	}
}

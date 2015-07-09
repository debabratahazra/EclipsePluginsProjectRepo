package com.odcgroup.integrationfwk.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.odcgroup.integrationfwk.ui.model.Operation;
import com.odcgroup.integrationfwk.ui.model.Parameter;
import com.odcgroup.integrationfwk.ui.model.Service;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.utils.XmlParser;

public class ComponentServiceController {

	private List<Service> serviceList;
	private final TWSConsumerProject currentProject;
	private final String COMPONENT_SERVICE_FILE = "componentService.xml";

	public ComponentServiceController(TWSConsumerProject currentProject) {
		this.currentProject = currentProject;
		serviceList = new ArrayList<Service>();
	}

	private Operation findOperation(String serviceName, String operationName) {
		for (Service service : serviceList) {
			if (service.getComponentService().equalsIgnoreCase(serviceName)) {
				for (Operation operation : service.getOperations()) {
					if (operation.getOperation()
							.equalsIgnoreCase(operationName)) {
						return operation;
					}
				}
			}
		}
		return new Operation();
	}

	public List<String> getOperationList(String serviceName) {
		if (serviceList.size() <= 0) {
			serviceList = XmlParser.getServices(currentProject.getPathString()
					+ File.separatorChar + COMPONENT_SERVICE_FILE);
		}
		for (Service service : serviceList) {
			if (service.getComponentService().equalsIgnoreCase(serviceName)) {
				return getOperationName(service.getOperations());
			}
		}
		// Operation is empty. No Operation for Given service.
		return new ArrayList<String>();
	}

	private List<String> getOperationName(List<Operation> operationList) {
		List<String> operations = new ArrayList<String>();
		for (Operation operation : operationList) {
			operations.add(operation.getOperation());
		}
		return operations;
	}

	public List<Parameter> getParameters(String serviceName,
			String operationName) {
		if (serviceList.size() <= 0) {
			serviceList = XmlParser.getServices(currentProject.getPathString()
					+ File.separatorChar + COMPONENT_SERVICE_FILE);
		}
		return findOperation(serviceName, operationName).getParameter();
	}

	public List<String> getServiceList() {
		if (serviceList.size() > 0) {
			return getServiceName();
		}
		serviceList = XmlParser.getServices(currentProject.getPathString()
				+ File.separatorChar + COMPONENT_SERVICE_FILE);
		return getServiceName();
	}

	private List<String> getServiceName() {
		List<String> services = new ArrayList<String>();
		for (Service service : serviceList) {
			services.add(service.getComponentService());
		}
		return services;
	}

	public boolean isServiceAvailable() {
		if (serviceList.size() > 0) {
			return true;
		}
		File file = new File(currentProject.getPathString()
				+ File.separatorChar + COMPONENT_SERVICE_FILE);
		if (file.exists()) {
			return true;
		}
		return false;
	}

}

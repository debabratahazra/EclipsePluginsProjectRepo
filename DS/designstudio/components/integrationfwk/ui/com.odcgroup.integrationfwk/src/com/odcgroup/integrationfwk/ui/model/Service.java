package com.odcgroup.integrationfwk.ui.model;

import java.util.ArrayList;
import java.util.List;

public class Service {

	private final List<Operation> operations;
	private String componentService;

	public Service() {
		operations = new ArrayList<Operation>();
	}

	public void addOperations(Operation operation) {
		operations.add(operation);
	}

	public String getComponentService() {
		return componentService;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setComponentService(String componentService) {
		this.componentService = componentService;
	}
}

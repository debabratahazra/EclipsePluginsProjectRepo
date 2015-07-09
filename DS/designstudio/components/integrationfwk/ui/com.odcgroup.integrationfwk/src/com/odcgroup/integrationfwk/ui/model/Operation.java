package com.odcgroup.integrationfwk.ui.model;

import java.util.ArrayList;
import java.util.List;

public class Operation {

	private final List<Parameter> parameters;
	private String operation;

	public Operation() {
		parameters = new ArrayList<Parameter>();
	}

	public void addParameter(Parameter parameter) {
		parameters.add(parameter);
	}

	public String getOperation() {
		return operation;
	}

	public List<Parameter> getParameter() {
		return parameters;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}

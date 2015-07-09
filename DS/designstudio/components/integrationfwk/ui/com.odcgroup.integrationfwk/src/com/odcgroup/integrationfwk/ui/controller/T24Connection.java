package com.odcgroup.integrationfwk.ui.controller;

import java.util.List;

import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Parameter;

public interface T24Connection {
	public List<Field> getApplicationFields(String applicationName);

	public List<String> getApplicationList();

	public List<String> getApplicationsVersions();

	public List<String> getExitPoints();

	public List<String> getOperationList(String serviceName);

	public List<String> getOverrides();

	public List<Parameter> getParameterList(String serviceName,
			String operationName);

	public List<String> getServiceList();

	/**
	 * Responsible for collecting the list of TSA Services.
	 * 
	 * @return list of TSA Services.
	 */
	public List<String> getTSAServicesList();

	public List<String> getVersionList();

}

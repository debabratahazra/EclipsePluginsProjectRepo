package com.odcgroup.integrationfwk.ui.controller;

import java.util.List;

import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Parameter;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationFieldsLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationsVersionsLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ComponentService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ExitPointLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.OverridesLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.TSALandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.VersionLandscapeService;

public class JAgentController implements T24Connection {

	private final TWSConsumerProject currentProject;
	private final ConfigEntity configEntity;
	private VersionLandscapeService versionService;
	private OverridesLandscapeService overridesLandscapeService;
	private ApplicationLandscapeService applicationService;
	private ExitPointLandscapeService exitPoint;
	private ApplicationFieldsLandscapeService applicationFieldsLandscapeService;
	private ApplicationsVersionsLandscapeService applicationsVerionsLandscapeService;
	private ComponentService componentService;
	/** instance of {@link TSALandscapeService} */
	private TSALandscapeService tsaLandscapeService;
	private final String projectName;
	private final ComponentServiceController componentServiceController;

	public JAgentController(TWSConsumerProject currentProject) {
		this.currentProject = currentProject;
		projectName = currentProject.getProject().getName();
		configEntity = new ConfigEntity();
		initJagent();
		componentServiceController = new ComponentServiceController(
				currentProject);
	}

	public List<Field> getApplicationFields(String applicationName) {
		return applicationFieldsLandscapeService.getFields(applicationName,
				projectName).getInputFields();
	}

	public List<String> getApplicationList() {
		return applicationService.getResponse(projectName);
	}

	public List<String> getApplicationsVersions() {
		return applicationsVerionsLandscapeService.getResponse(projectName);
	}

	public List<String> getExitPoints() {
		return exitPoint.getResponse(projectName);
	}

	public List<String> getOperationList(String serviceName) {
		if (componentServiceController.isServiceAvailable()) {
			return componentServiceController.getOperationList(serviceName);
		} else {
			componentService.getComponentService(
					currentProject.getPathString(), projectName);
			return componentServiceController.getOperationList(serviceName);
		}
	}

	public List<String> getOverrides() {
		return overridesLandscapeService.getResponse(projectName);
	}

	public List<Parameter> getParameterList(String serviceName,
			String operationName) {
		if (componentServiceController.isServiceAvailable()) {
			return componentServiceController.getParameters(serviceName,
					operationName);
		} else {
			componentService.getComponentService(
					currentProject.getPathString(), projectName);
			return componentServiceController.getParameters(serviceName,
					operationName);
		}
	}

	public List<String> getServiceList() {
		if (componentServiceController.isServiceAvailable()) {
			return componentServiceController.getServiceList();
		} else {
			componentService.getComponentService(
					currentProject.getPathString(), projectName);
			return componentServiceController.getServiceList();
		}
	}

	public List<String> getTSAServicesList() {
		return tsaLandscapeService.getResponse(projectName);
	}

	public List<String> getVersionList() {
		return versionService.getResponse(projectName);
	}

	private void initConfigEntity() {
		configEntity.setPortNumber(currentProject.getPort());
		configEntity.setHostName(currentProject.getHost());
		configEntity.setOfsSource(currentProject.getOfsSource());
	}

	private void initJagent() {
		initConfigEntity();
		applicationService = new ApplicationLandscapeService(configEntity,
				projectName);
		versionService = new VersionLandscapeService(configEntity, projectName);
		exitPoint = new ExitPointLandscapeService(configEntity, projectName);
		overridesLandscapeService = new OverridesLandscapeService(configEntity,
				projectName);
		applicationFieldsLandscapeService = new ApplicationFieldsLandscapeService(
				configEntity, projectName);
		applicationsVerionsLandscapeService = new ApplicationsVersionsLandscapeService(
				configEntity, projectName);
		componentService = new ComponentService(configEntity, projectName);
		tsaLandscapeService = new TSALandscapeService(configEntity, projectName);
	}
}

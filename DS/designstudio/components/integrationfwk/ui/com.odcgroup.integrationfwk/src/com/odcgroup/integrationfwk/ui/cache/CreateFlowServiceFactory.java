package com.odcgroup.integrationfwk.ui.cache;

import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.decorators.FilePropertyPage;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.services.connectivity.FlowServices;
import com.odcgroup.integrationfwk.ui.services.connectivity.TafjCreateFlowServiceDataBuilder;
import com.odcgroup.integrationfwk.ui.services.connectivity.TafjCreateFlowServiceDataBuilderImpl;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.flowservice.CreateFlowService;
import com.odcgroup.integrationfwk.ui.t24connectivity.flowservice.DeleteAllFlowsService;
import com.odcgroup.integrationfwk.ui.t24connectivity.flowservice.TafcCreateFlowServiceDataBuilder;
import com.odcgroup.integrationfwk.ui.t24connectivity.flowservice.TafcCreateFlowServiceDataBuilderImpl;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

public class CreateFlowServiceFactory {

	private FlowServices flowServices;
	private CreateFlowService createFlowService;
	private DeleteAllFlowsService deleteAllFlows;
	private final TWSConsumerProject project;
	private final boolean isAgentConnection;

	CreateFlowServiceFactory(TWSConsumerProject project) {
		this.project = project;
		isAgentConnection = project.isAgentConnection();

	}

	public void createFlow(EventFlow eventFlow, StringBuffer publishLog,
			FilePropertyPage filePropertyPage, String projectName)
			throws TWSConsumerPluginException {
		if (isAgentConnection) {
			getJagentCreateFlow(
					new TafcCreateFlowServiceDataBuilderImpl(eventFlow))
					.createFlow(eventFlow, publishLog, filePropertyPage,
							projectName);
		} else {
			getWebServicesCreateFlow(
					new TafjCreateFlowServiceDataBuilderImpl(eventFlow))
					.createFlow(eventFlow, publishLog, filePropertyPage,
							projectName, project);
		}
	}

	public void createSchema(EventFlow eventFlow)
			throws TWSConsumerPluginException {
		if (isAgentConnection) {
			getJagentCreateFlow(
					new TafcCreateFlowServiceDataBuilderImpl(eventFlow))
					.createSchema(project, eventFlow.getEvent().getFlowName());
		} else {
			// for Web service connection, schema is created along with create
			// flow method.
		}
	}

	public void deleteAllFlows(String projectName) {
		if (isAgentConnection) {
			getJagentDeleteAllFlow(projectName).deleteFlow(projectName);
		} else {
			getWebServicesCreateFlow().deleteAllFlows(projectName);
		}
	}

	/**
	 * Helps to get the flow service url from consumer project.
	 * 
	 * @return flow service url.
	 */
	private String getFlowServiceURL() {
		String componentServiceURL = project.getComponentServiceURL();
		return TWSConsumerProjectUtil.getFlowServiceURL(componentServiceURL);
	}

	private CreateFlowService getJagentCreateFlow(
			TafcCreateFlowServiceDataBuilder createFlowServiceDataBuilder) {
		if (createFlowService != null) {
			createFlowService
					.updateFlowServiceDataBuilder(createFlowServiceDataBuilder);
			return createFlowService;
		} else {
			ConfigEntity configEntity = populateConfigEntity(project.getHost(),
					project.getPort(), project.getOfsSource());
			createFlowService = new CreateFlowService(configEntity, project
					.getProject().getName(), createFlowServiceDataBuilder);
			return createFlowService;
		}
	}

	private DeleteAllFlowsService getJagentDeleteAllFlow(String projectName) {
		if (deleteAllFlows != null) {
			return deleteAllFlows;
		} else {
			ConfigEntity configEntity = populateConfigEntity(project.getHost(),
					project.getPort(), project.getOfsSource());
			deleteAllFlows = new DeleteAllFlowsService(configEntity,
					projectName);
			return deleteAllFlows;
		}
	}

	private FlowServices getWebServicesCreateFlow() {
		if (flowServices != null) {
			return flowServices;
		} else {
			flowServices = new FlowServices(getFlowServiceURL(),
					project.getPassword(), project.getUserName());
			return flowServices;
		}
	}

	private FlowServices getWebServicesCreateFlow(
			TafjCreateFlowServiceDataBuilder createFlowServiceDataBuilder) {
		if (flowServices != null) {
			flowServices
					.setFlowServiceDataBuilder(createFlowServiceDataBuilder);
			return flowServices;
		} else {
			flowServices = new FlowServices(getFlowServiceURL(),
					project.getPassword(), project.getUserName(),
					createFlowServiceDataBuilder);
			return flowServices;
		}
	}

	private ConfigEntity populateConfigEntity(String hostName, int portNumber,
			String ofsSource) {
		ConfigEntity configEntity = new ConfigEntity();
		configEntity.setHostName(hostName);
		configEntity.setPortNumber(portNumber);
		configEntity.setOfsSource(ofsSource);
		return configEntity;
	}
}

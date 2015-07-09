package com.odcgroup.integrationfwk.ui.t24connectivity;

import com.odcgroup.integrationfwk.ui.model.EventFlow;

/**
 * 
 * @author eswaranmuthu
 * 
 */
public abstract class IntegrationFlowService extends IntegrationService {

	protected final String CREATE_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.createFlow";
	protected final String READ_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.readFlow";
	protected final String DELETE_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.deleteFlow";
	protected final String DELETE_ALL_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.deleteAllFlows";
	protected final String RELEVANT_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.getRelevantFlows";
	protected final String UPDATE_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.updateFlow";
	protected final String VALIDATE_FLOW_SERVICE = "T24IntegrationFlowServiceImpl.validateFlow";
	protected EventFlow eventFlow;

	public IntegrationFlowService(ConfigEntity configEntity, String projectName) {
		super(configEntity, projectName);

	}

	public EventFlow getT24Event() {
		return eventFlow;
	}

	public void setT24Event(EventFlow event) {
		eventFlow = event;
	}
}

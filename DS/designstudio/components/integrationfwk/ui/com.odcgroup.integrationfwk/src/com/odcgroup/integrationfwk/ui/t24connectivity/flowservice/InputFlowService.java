package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

public abstract class InputFlowService extends IntegrationFlowService {

	/** instance to helps to construct the data for creating flow */
	private TafcCreateFlowServiceDataBuilder flowServiceDataBuilder;

	/**
	 * constructs the Input Flow Service instance with given details.
	 * 
	 * @param configEntity
	 * @param projectName
	 * @param flowServiceDataBuilder
	 */
	public InputFlowService(ConfigEntity configEntity, String projectName,
			TafcCreateFlowServiceDataBuilder flowServiceDataBuilder) {
		super(configEntity, projectName);
		this.flowServiceDataBuilder = flowServiceDataBuilder;
	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection. SUBROUTINE
	 * T24IntegrationFlowServiceImpl.createFlow(exitPoint, integrationFlowBase,
	 * contractData, componentServiceData, integrationFlowSchema,
	 * responseDetails) excluding integrationFlowSchema, responseDetails all
	 * other are IN argument.
	 */
	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		try {
			jSubroutineRequest
					.add(flowServiceDataBuilder.createExitPointData());
			jSubroutineRequest.add(flowServiceDataBuilder
					.createIntegrationFlowBaseData());
			jSubroutineRequest.add(flowServiceDataBuilder.createContractData());
			jSubroutineRequest.add(flowServiceDataBuilder
					.createComponetServiceData());
			jSubroutineRequest.add(new JDynArray(""));
			jSubroutineRequest.add(new JDynArray(""));
		} catch (TWSConsumerPluginException e) {
			TWSConsumerLog
					.logError(
							"Error occurs while constructing the subroutine request for createFlow",
							e);
		}
		return jSubroutineRequest;
	}

	/**
	 * Set the flow service data builder to this service.
	 * 
	 * @param flowServiceDataBuilder
	 */
	protected void setFlowServiceDataBuilder(
			TafcCreateFlowServiceDataBuilder flowServiceDataBuilder) {
		this.flowServiceDataBuilder = flowServiceDataBuilder;
	}
}

package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.TWSConsumerPluginException;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

/**
 * validates integration flow in T24.
 * 
 * @author eswaranmuthu
 * 
 */
public class ValidateFlowService extends IntegrationFlowService {

	/**
	 * instance of data builder which helps to construct the data for
	 * creating/validating a flow in T24
	 */
	private final TafcCreateFlowServiceDataBuilder flowServiceDataBuilder;

	public ValidateFlowService(ConfigEntity configEntity, EventFlow event,
			String projectName,
			TafcCreateFlowServiceDataBuilder flowServiceDataBuilder) {
		super(configEntity, projectName);
		this.flowServiceDataBuilder = flowServiceDataBuilder;
	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection. SUBROUTINE
	 * T24IntegrationFlowServiceImpl.createFlow(exitPoint, integrationFlowBase,
	 * contractData, componentServiceData, integrationFlowSchema,
	 * responseDetails) excluding responseDetails all other are IN argument.
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
		} catch (TWSConsumerPluginException e) {
			TWSConsumerLog.logError(
					"Error occurs while constructing the data to validateFlow",
					e);
		}
		return jSubroutineRequest;
	}

	public void validateFlow(String projectName, EventFlow t24Event) {
		eventFlow = t24Event;
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		getT24Response(jSubroutineRequest, VALIDATE_FLOW_SERVICE, projectName,
				false);
	}
}

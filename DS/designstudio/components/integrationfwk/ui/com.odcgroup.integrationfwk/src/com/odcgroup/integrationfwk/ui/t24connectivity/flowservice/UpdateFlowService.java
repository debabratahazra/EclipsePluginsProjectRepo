package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.model.EventFlow;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

/**
 * updates integration flow in T24 with given T24Event model class.
 * 
 * @author eswaranmuthu
 * 
 */
public class UpdateFlowService extends IntegrationFlowService {

	public UpdateFlowService(ConfigEntity configEntity, EventFlow event,
			String projectName) {
		super(configEntity, projectName);
	}

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		// nothing to implement now
		return new JSubroutineParameters();
	}

	public void updateFlow(String projectName) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		getT24Response(jSubroutineRequest, UPDATE_FLOW_SERVICE, projectName,
				false);
	}
}

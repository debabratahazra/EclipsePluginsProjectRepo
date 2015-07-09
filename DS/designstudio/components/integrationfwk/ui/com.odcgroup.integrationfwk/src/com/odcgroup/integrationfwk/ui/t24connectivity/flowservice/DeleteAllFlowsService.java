package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

public class DeleteAllFlowsService extends IntegrationFlowService {

	private String projectName;

	public DeleteAllFlowsService(ConfigEntity configEntity, String projectName) {
		super(configEntity, projectName);
	}

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(setProjectName());
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	public void deleteFlow(String projectName) {
		this.projectName = projectName;
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		getT24Response(jSubroutineRequest, DELETE_ALL_FLOW_SERVICE,
				projectName, false);
	}

	/**
	 * constructs JDynArray for flow name
	 * 
	 * @return JDynArray with flow name in it.
	 */
	private JDynArray setProjectName() {
		JDynArray projectNameDynArray = new JDynArray(projectName);
		return projectNameDynArray;
	}
}

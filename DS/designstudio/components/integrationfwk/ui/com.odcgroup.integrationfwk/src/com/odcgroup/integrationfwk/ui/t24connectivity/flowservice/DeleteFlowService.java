package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

/**
 * deletes the given integration flow from T24.
 * 
 * @author eswaranmuthu
 * 
 */
public class DeleteFlowService extends IntegrationFlowService {

	private final String flowName;

	public DeleteFlowService(ConfigEntity configEntity, String flowName,
			String projectName) {
		super(configEntity, projectName);
		this.flowName = flowName;
	}

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		// TODO Auto-generated method stub
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(setFlowName());
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	public void deleteFlow(String projectName) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		getT24Response(jSubroutineRequest, DELETE_FLOW_SERVICE, projectName,
				false);
	}

	/**
	 * constructs JDynArray for flow name
	 * 
	 * @return JDynArrat with flow name in it.
	 */
	private JDynArray setFlowName() {
		JDynArray flowName = new JDynArray(this.flowName);
		return flowName;
	}

}

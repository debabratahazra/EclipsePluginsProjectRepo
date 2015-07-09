package com.odcgroup.integrationfwk.ui.t24connectivity.flowservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationFlowService;

/**
 * gets all integration flow attached to given contract (Version or
 * application).
 * 
 * @author eswaranmuthu
 * 
 */
public class RelevantFlowService extends IntegrationFlowService {

	private final String exitPointFilter;
	private final String SUCCESS = "SUCCESS";

	public RelevantFlowService(ConfigEntity configEntity,
			String exitPointFilter, String projectName) {
		super(configEntity, projectName);
		this.exitPointFilter = exitPointFilter;

	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection. SUBROUTINE
	 * T24IntegrationFlowServiceImpl.relevantFlow(exitPoint: str
	 * ,exitPointFilter : str , integrationFlows : str[], responseDetails)
	 * exitPointFilter is the only IN argument.
	 */

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(exitPointFilter));
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	/**
	 * The two OUT arguments are integrationFlows and responseDetails, 3rd and
	 * 4th arguments of jSubroutineResponse respectively.
	 * 
	 * @param jSubroutineResponse
	 * @return
	 */
	private List<String> getRelevantFlow(
			JSubroutineParameters jSubroutineResponse) {
		JDynArray integrationFlows = jSubroutineResponse.get(2);
		List<String> flowList = new ArrayList<String>();
		int flowSize = integrationFlows.getNumberOfAttributes();
		if (jSubroutineResponse.get(3).get(1).equalsIgnoreCase(SUCCESS)) {
			for (int count = 0; count < flowSize; count++) {
				flowList.add(integrationFlows.get(2, count + 1));
			}
			return flowList;
		} else {
			return new ArrayList<String>();
		}
	}

	public List<String> relevantFlow(String projectName) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, RELEVANT_FLOW_SERVICE, projectName, false);
		getRelevantFlow(jSubroutineResponse);
		return null;
	}
}

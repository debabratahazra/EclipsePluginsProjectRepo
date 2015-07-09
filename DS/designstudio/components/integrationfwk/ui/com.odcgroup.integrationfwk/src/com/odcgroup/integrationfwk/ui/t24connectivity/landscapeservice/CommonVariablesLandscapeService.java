package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationLandscapeService;

/**
 * gets list of all common variables from T24.
 * 
 * @author eswaranmuthu
 * 
 */
public class CommonVariablesLandscapeService extends
		IntegrationLandscapeService {

	public CommonVariablesLandscapeService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	protected CommonVariablesLandscapeService(
			IntegrationConnector integrationConnector) {
		super(integrationConnector);
	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection.
	 * 
	 * @return
	 */
	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	/**
	 * method constructs List of common variables from JSubroutineParameters
	 * only one arguments and it is OUT param.
	 * 
	 * @param jSubroutineResponse
	 *            JSubroutineParameters type response coming from T24.
	 * @return
	 */
	private List<String> getCommonVariables(
			JSubroutineParameters jSubroutineResponse) {
		List<String> commonVariables = new ArrayList<String>();
		int itemCount = jSubroutineResponse.get(0).getNumberOfAttributes();
		for (int itemNum = 0; itemNum < itemCount; itemNum++) {
			/*
			 * JSubroutineParameters extends List<jDynArray>, the below line get
			 * first dynArray from List, returns all subsequent dynarray.
			 */
			commonVariables.add(jSubroutineResponse.get(0).get(itemNum + 1, 1));
		}
		return commonVariables;
	}

	@Override
	public List<String> getResponse(String projectName) {
		return getResponse(projectName, false);
	}

	/**
	 * returns list of common variables from T24.
	 * 
	 * @return
	 */
	public List<String> getResponse(String projectName,
			boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, LANDSCAPE_COMMON_VARS, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			return getCommonVariables(jSubroutineResponse);
		} else {
			return new ArrayList();
		}
	}
}

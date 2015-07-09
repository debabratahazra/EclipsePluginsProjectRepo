package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.T24Cache;

/**
 * gets list of all overrides from T24.
 * 
 * @author eswaranmuthu
 * 
 */
public class OverridesLandscapeService extends IntegrationLandscapeService {

	public OverridesLandscapeService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	protected OverridesLandscapeService(
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
	 * 
	 * method constructs List of overrides from JSubroutineParameters only one
	 * arguments and it is OUT param.
	 * 
	 * @param jSubroutineResponse
	 *            JSubroutineParameters type response coming from T24.
	 * @return
	 */
	public List<String> getOverrides(JSubroutineParameters jSubroutineResponse) {
		List<String> overrides = new ArrayList<String>();
		int itemCount = jSubroutineResponse.get(0).getNumberOfAttributes();
		for (int itemNum = 0; itemNum < itemCount; itemNum++) {
			/*
			 * JSubroutineParameters extends List<jDynArray>, the below line get
			 * first dynArray from List, returns all subsequent dynarray.
			 */

			overrides.add(jSubroutineResponse.get(0).get(itemNum + 1, 1));
		}
		return overrides;
	}

	@Override
	public synchronized List<String> getResponse(String projectName) {
		return getResponse(projectName, false);
	}

	/**
	 * returns list of all overrides from T24.
	 * 
	 * @return
	 */

	public synchronized List<String> getResponse(String projectName,
			boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		T24Cache t24Cache = T24Cache.getT24Cache();
		List<String> overridesList = t24Cache.getOverridesList(projectName);
		if (overridesList != null) {
			return overridesList;
		}
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, LANDSCAPE_OVERRIDE, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			overridesList = getOverrides(jSubroutineResponse);
			t24Cache.addOverrides(overridesList, projectName);
			return overridesList;
		} else {
			return new ArrayList();
		}
	}
}

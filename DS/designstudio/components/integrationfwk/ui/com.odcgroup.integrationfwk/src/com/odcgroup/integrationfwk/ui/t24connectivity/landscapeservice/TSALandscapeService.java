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
 * Providing the TSA related Landscape service.
 * 
 * @author sbharathraja
 * 
 */
public class TSALandscapeService extends IntegrationLandscapeService {

	public TSALandscapeService(ConfigEntity configEntity, String projectName) {
		super(configEntity, projectName);
	}

	protected TSALandscapeService(IntegrationConnector integrationConnector) {
		super(integrationConnector);
	}

	/**
	 * Responsible for build the list of services using given subroutine
	 * response.
	 * 
	 * @param jSubroutineResponse
	 * @return list of string.
	 */
	private List<String> buildServiceList(
			JSubroutineParameters jSubroutineResponse) {
		List<String> tsaServicesList = new ArrayList<String>();
		JDynArray jdynArray = jSubroutineResponse.get(0);
		int itemCount = jdynArray.getNumberOfAttributes();
		for (int itemNum = 0; itemNum < itemCount; itemNum++) {
			/*
			 * JSubroutineParameters extends List<jDynArray>, the below line get
			 * first dynArray from List, returns all subsequent dynarray.
			 */
			tsaServicesList.add(jdynArray.get(itemNum + 1, 1));
		}
		return tsaServicesList;
	}

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	@Override
	public List<String> getResponse(String projectName) {
		return getTsaServicesList(projectName, false);
	}

	/**
	 * Helps to get the list of TSA services. If the given project name has the
	 * required list in cache already return it, else connect to T24 and then
	 * collect the details.
	 * 
	 * @param projectName
	 *            - name of the project.
	 * @param fromBackGroundThread
	 *            - true if the requests came through background thread
	 *            (actually when creating a new project), false otherwise.
	 * @return If the given project name has the required list in cache already
	 *         return it, else connect to T24 and then collect the details.
	 */
	public synchronized List<String> getTsaServicesList(String projectName,
			boolean fromBackGroundThread) {
		// get the cache
		T24Cache t24Cache = T24Cache.getT24Cache();
		List<String> tsaServiceList = t24Cache.getTsaServicesList(projectName);
		// return the list from cache.
		if (tsaServiceList != null) {
			return tsaServiceList;
		}
		// build the request
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		// call the respective subroutine
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, TSA_SERVICE, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			tsaServiceList = buildServiceList(jSubroutineResponse);
			// add the list to cache for further usage.
			t24Cache.addTSAServiceList(tsaServiceList, projectName);
			return tsaServiceList;
		} else {
			// happens only when error occur while call the subroutine.
			return new ArrayList<String>();
		}
	}

}

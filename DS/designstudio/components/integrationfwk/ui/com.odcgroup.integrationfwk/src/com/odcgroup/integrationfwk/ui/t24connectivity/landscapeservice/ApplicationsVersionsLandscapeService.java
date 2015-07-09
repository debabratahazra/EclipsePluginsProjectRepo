package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.T24Cache;
import com.odcgroup.integrationfwk.ui.utils.Utils;

public class ApplicationsVersionsLandscapeService extends
		IntegrationLandscapeService {

	public ApplicationsVersionsLandscapeService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	protected ApplicationsVersionsLandscapeService(
			IntegrationConnector integrationConnector) {
		super(integrationConnector);
	}

	/**
	 * constructs jSubroutineRequest, which is passed as an argument to
	 * jConnection.
	 * 
	 * @returns JSubroutineParameters
	 */
	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	/**
	 * method constructs List of versions and applications from
	 * JSubroutineParameters. only one argument and it is OUT param.
	 * 
	 * @param jSubroutineResponse
	 *            response from T24 comes as JSubroutineParameters.
	 * @return
	 */
	private List<String> getApplicationVersionList(
			JSubroutineParameters jSubroutineResponse) {
		List<String> version = new ArrayList<String>();
		int itemCount = jSubroutineResponse.get(0).getNumberOfAttributes();
		for (int itemNum = 0; itemNum < itemCount; itemNum++) {
			/*
			 * JSubroutineParameters extends List<jDynArray>, the below line get
			 * first dynArray from List, returns all subsequent dynarray.
			 */
			version.add(jSubroutineResponse.get(0).get(itemNum + 1, 1));
		}
		return version;
	}

	@Override
	public synchronized List<String> getResponse(String projectName) {
		return getResponse(projectName, false);
	}

	/**
	 * returns list of applications and versions.
	 */

	public synchronized List<String> getResponse(String projectName,
			boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		T24Cache t24Cache = T24Cache.getT24Cache();
		List<String> validAppVersionList = new ArrayList<String>();
		Utils utils = new Utils();
		List<String> applicationVersionList = t24Cache
				.getApplicationVersionList(projectName);
		if (applicationVersionList != null) {
			return applicationVersionList;
		}
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, LANDSCAPE_APPLICATIONS_VERSIONS,
				projectName, fromBackGroundThread);
		if (jSubroutineResponse != null) {
			applicationVersionList = getApplicationVersionList(jSubroutineResponse);
			validAppVersionList = utils
					.removeAAArrangementRelated(applicationVersionList);
			t24Cache.addApplicationVersion(validAppVersionList, projectName);
			return validAppVersionList;
		} else {
			log("Response from T24 is NULL");
			return new ArrayList<String>();
		}
	}
}

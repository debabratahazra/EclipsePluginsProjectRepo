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

/**
 * gets list of all versions from T24.
 * 
 * @author eswaranmuthu
 * 
 */

public class VersionLandscapeService extends IntegrationLandscapeService {

	public VersionLandscapeService(ConfigEntity configEntity, String projectName) {
		super(configEntity, projectName);
	}

	protected VersionLandscapeService(IntegrationConnector integrationConnector) {
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

	@Override
	public synchronized List<String> getResponse(String projectName) {

		return getResponse(projectName, false);
	}

	/**
	 * @return
	 */
	public synchronized List<String> getResponse(String projectName,
			boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		List<String> validVersionList = new ArrayList<String>();
		Utils utils = new Utils();
		T24Cache t24Cache = T24Cache.getT24Cache();
		List<String> versionList = t24Cache.getVersionList(projectName);
		if (versionList != null) {
			return versionList;
		}

		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, LANDSCAPE_VERSIONS, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			versionList = getVersionList(jSubroutineResponse);
			validVersionList = utils.removeAAArrangementRelated(versionList);
			t24Cache.addVersionList(validVersionList, projectName);
			return validVersionList;
		} else {
			log("Response from T24 is NULL");
			return new ArrayList<String>();
		}
	}

	/**
	 * method constructs List of version from JSubroutineParameters only one
	 * arguments and it is OUT param.
	 * 
	 * @param jSubroutineResponse
	 *            response from T24 comes as JSubroutineParameters.
	 * @return
	 */
	public List<String> getVersionList(JSubroutineParameters jSubroutineResponse) {
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
}

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
 * gets list of all the application from T24.
 * 
 * @author eswaranmuthu
 * 
 */
public class ApplicationLandscapeService extends IntegrationLandscapeService {

	public ApplicationLandscapeService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	protected ApplicationLandscapeService(
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
	 * method constructs List of Application from JSubroutineParameters contains
	 * only one arguments and it is OUT param.
	 * 
	 * @param jSubroutineResponse
	 *            JSubroutineParameters type response coming from T24.
	 * @return
	 */
	public List<String> getApplicationList(
			JSubroutineParameters jSubroutineResponse) {
		List<String> application = new ArrayList<String>();
		JDynArray jdynArray = jSubroutineResponse.get(0);
		int itemCount = jdynArray.getNumberOfAttributes();
		for (int itemNum = 0; itemNum < itemCount; itemNum++) {
			/*
			 * JSubroutineParameters extends List<jDynArray>, the below line get
			 * first dynArray from List, returns all subsequent dynarray.
			 */
			application.add(jdynArray.get(itemNum + 1, 1));
		}
		return application;
	}

	@Override
	public synchronized List<String> getResponse(String projectName) {
		return getResponse(projectName, false);
	}

	/**
	 * returns list of application from T24
	 * 
	 * @return
	 */
	public synchronized List<String> getResponse(String projectName,
			boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		T24Cache t24Cache = T24Cache.getT24Cache();
		List<String> applicationList = t24Cache.getApplicationList(projectName);
		List<String> validApplicationList = new ArrayList<String>();
		Utils utils = new Utils();
		if (applicationList != null) {
			return applicationList;
		}
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, LANDSCAPE_APPLICATIONS, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			applicationList = getApplicationList(jSubroutineResponse);
			validApplicationList = utils
					.removeAAArrangementRelated(applicationList);
			t24Cache.addApplicationList(validApplicationList, projectName);
			return validApplicationList;
		} else {
			return new ArrayList<String>();
		}
	}
}

package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationLandscapeService;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;

public class CreateDataLibraryService extends IntegrationLandscapeService {

	public CreateDataLibraryService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	protected CreateDataLibraryService(IntegrationConnector integrationConnector) {
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

	// Return type does not matter. just to make it in line with other sub
	// classes, return type is List.
	@Override
	public List<String> getResponse(String projectName) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, CREATE_DATA_LIBRARY, projectName, false);
		if (jSubroutineResponse != null
				&& jSubroutineResponse.get(0).get(1).equals("SUCCESS")) {
			LogConsole.getSoaConsole().logMessage(
					"Data library created : Successfully");
			return null;
		} else {
			LogConsole.getSoaConsole().logMessage(
					"Creating Data library : Failed");
			return null;
		}
	}
}

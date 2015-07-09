package com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice;

import java.util.ArrayList;
import java.util.List;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JSubroutineParameters;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationLandscapeService;
import com.odcgroup.integrationfwk.ui.utils.FileUtil;

public class ComponentService extends IntegrationLandscapeService {

	private final String COMPONENT_SERVICE_FILE = "componentService.xml";

	public ComponentService(ConfigEntity configEntity, String projectName) {
		super(configEntity, projectName);
	}

	protected ComponentService(IntegrationConnector integrationConnector) {
		super(integrationConnector);
	}

	@Override
	protected JSubroutineParameters constructjSubroutineRequest() {
		JSubroutineParameters jSubroutineRequest = new JSubroutineParameters();
		jSubroutineRequest.add(new JDynArray(""));
		jSubroutineRequest.add(new JDynArray(""));
		return jSubroutineRequest;
	}

	public synchronized void getComponentService(String path, String projectName) {
		getComponentService(path, projectName, false);
	}

	/**
	 * 
	 * @param projectName
	 * @return
	 */
	public synchronized void getComponentService(String path,
			String projectName, boolean fromBackGroundThread) {
		JSubroutineParameters jSubroutineRequest = constructjSubroutineRequest();
		JSubroutineParameters jSubroutineResponse = getT24Response(
				jSubroutineRequest, COMPONENT_SERVICE, projectName,
				fromBackGroundThread);
		if (jSubroutineResponse != null) {
			getXml(jSubroutineResponse, path);
		}
	}

	@Override
	public List<String> getResponse(String projectName) {
		return new ArrayList<String>();
	}

	private void getXml(JSubroutineParameters jSubroutineResponse,
			String filePath) {
		String xmlString = jSubroutineResponse.get(0).toString();
		xmlString = xmlString.substring(xmlString.indexOf(">") + 1);
		FileUtil.doXmlFileWrite(xmlString, COMPONENT_SERVICE_FILE, filePath);
	}
}

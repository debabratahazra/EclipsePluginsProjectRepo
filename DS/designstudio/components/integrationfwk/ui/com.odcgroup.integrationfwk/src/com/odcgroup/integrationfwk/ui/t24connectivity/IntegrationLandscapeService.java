package com.odcgroup.integrationfwk.ui.t24connectivity;

import java.util.List;

import com.odcgroup.integrationfwk.ui.utils.LogConsole;

public abstract class IntegrationLandscapeService extends IntegrationService {

	protected final String LANDSCAPE_VERSIONS = "IntegrationLandscapeService.getVersions";
	protected final String LANDSCAPE_APPLICATIONS = "IntegrationLandscapeService.getApplications";
	protected final String LANDSCAPE_EXITPOINTS = "IntegrationLandscapeService.getVersionExitPoints";
	protected final String LANDSCAPE_OVERRIDE = "IntegrationLandscapeService.getOverrideCodes";
	protected final String LANDSCAPE_COMMON_VARS = "IntegrationLandscapeService.getCommonVars";
	protected final String LANDSCAPE_APPLICATION_FIELDS = "IntegrationLandscapeService.getFlowFields";
	protected final String LANDSCAPE_APPLICATIONS_VERSIONS = "IntegrationLandscapeService.getApplicationsVersions";
	protected final String CREATE_DATA_LIBRARY = "IF.DATA.LIBRARY.REC.CREATE";
	protected final String COMPONENT_SERVICE = "T24CatalogServiceImpl.getServices";
	/** Subroutine for get the list of TSA services */
	protected final String TSA_SERVICE = "IntegrationLandscapeService.getTsaServiceJobs";

	public IntegrationLandscapeService(ConfigEntity configEntity,
			String projectName) {
		super(configEntity, projectName);
	}

	/**
	 * Constructs the Integration Landscape Service with the given connector.
	 * 
	 * @param integrationConnector
	 */
	protected IntegrationLandscapeService(
			IntegrationConnector integrationConnector) {
		super(integrationConnector);
	}

	abstract public List<String> getResponse(String projectName);

	protected void log(String message) {
		LogConsole.getSoaConsole().logMessage(message);
	}
}

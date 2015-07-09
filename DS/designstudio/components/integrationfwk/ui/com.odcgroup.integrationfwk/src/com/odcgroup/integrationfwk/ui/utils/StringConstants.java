package com.odcgroup.integrationfwk.ui.utils;

import java.io.File;

//TODO later on move this class to plugin.properties file.
public class StringConstants {

	public static final String EVENTS = "events";
	public static final String EVENT = ".event";
	public static final String FLOW = ".flow";
	public static final String FLOWS = "flows";
	public static final String PUBLISH_LOG = "log" + File.separatorChar
			+ "publish.log";
	public static final String START_PUBLISH = "trying to publish : ";
	public static final String EVENT_DETAILS = "event details ......\n";
	public static final String CONTRACT_NAME = "Contract Name : ";
	public static final String SCHEMA_EXTENSION = ".xsd";

	// component service related constants.
	/** string which represent the services location in url */
	public static final String COMP_SERVICES_STRING = "services";
	/** string which represent the flow service url */
	public static final String COMP_FLOW_SERVICES_STRING = "IntegrationFlowServiceWS?wsdl";
	/** string which represent the landscape service url */
	public static final String COMP_LANDSCAPE_SERVICES_STRING = "IntegrationLandscapeServiceWS?wsdl";
	/** string which represent the service repository url. */
	public static final String COMP_CATALOG_SERVICE_STRING = "CatalogServiceWS?wsdl";
	/** string which represent the splitting character in url */
	public static final String COMP_URL_SPLITTING_CHAR = "/";

	// project folder name definition starting.
	/** name of the schema folder */
	public static final String SCHEMA_FOLDER_NAME = "schemas";
	/** name of the events folder */
	public static final String EVENT_FOLDER_NAME = "events";
	/** name of the flows folder */
	public static final String FLOW_FOLDER_NAME = "flows";
	/** name of the log folder */
	public static final String LOG_FOLDER_NAME = "log";
	// project folder name definition ending

	// --- project connection dialog reference ---

	public static final String _UI_WIZARD_CREATE_INTEGRATION_PROJET_IN_LOCATION = "Create an Integration project in the specified location.";
	public static final String _UI_WIZARD_CREATE_INTEGRATION_PROJECT = "Create an Integration Project";
	public static final String _UI_WIZARD_CREATE_INTEGRATION_PROJECT_TITLE = "New Integration Project";
	public static final String _UI_WIZARD_CREATING_INTEGRATION_PROJECT = "Creating Integration Project...";
	public static final String _UI_WIZARD_CONNECTION_PROPERTIES = "Connection Properties";

	// --------------------------------------------

	// wizard error messages
	/**
	 * error message which will be shown in wizard when the given file/project
	 * name contains any invalid character
	 */
	public static final String ERROR_MSG_CONTAINS_INVALID_CHAR = "File name contains invalid character!";
	/**
	 * error message which will be shown to the user while project/file name is
	 * not satisfying the flow name rule..
	 */
	public static final String ERROR_MSG_FLOWNAME_RULES_NOT_MEET = "Flow name should contains 3 letters at minimum.";
	/**
	 * error message which will be shown when the file/project name is empty
	 */
	public static final String ERROR_MSG_EMPTY_NAME = "File name can not be empty.";

	public static final String ERROR_FILENAME_CANNOT_START_WITH_NUMBERS = "The file name cannot start with a number.";
	public static final String ERROR_FILENAME_CANNOT_CONTAIN_ANY_SPECIAL_CHARACTERS = "The file name cannot contain any special characters.";

	// ------------------Error Responses --------------------
	public static final String ERROR_MSG_RESPONSE_EMPTY = "Response list size is zero";

}

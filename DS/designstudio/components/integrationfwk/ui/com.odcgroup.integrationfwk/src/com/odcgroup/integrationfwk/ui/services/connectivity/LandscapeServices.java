package com.odcgroup.integrationfwk.ui.services.connectivity;

import integrationlandscapeservicews.IntegrationLandscapeServiceWS;
import integrationlandscapeservicews.IntegrationLandscapeServiceWSPortType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.common.GetFieldsServiceResponse;
import com.odcgroup.integrationfwk.ui.common.ServiceResponses;
import com.odcgroup.integrationfwk.ui.model.ApplicationVersionField;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;
import com.odcgroup.integrationfwk.ui.utils.StringConstants;
import com.temenos.services.integrationlandscape.data.response.xsd.GetApplicationsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetApplicationsVersionsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetFlowFieldsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetOverrideCodesResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetTsaServiceJobsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetVersionExitPointsResponse;
import com.temenos.services.integrationlandscape.data.response.xsd.GetVersionsResponse;
import com.temenos.services.integrationlandscape.data.xsd.ApplicationField;
import com.temenos.services.integrationlandscape.data.xsd.VersionExitPoint;
import com.temenos.soa.services.data.xsd.ObjectFactory;
import com.temenos.soa.services.data.xsd.Response;
import com.temenos.soa.services.data.xsd.T24UserDetails;

public class LandscapeServices {

    private IntegrationLandscapeServiceWSPortType servicePort;
    private T24UserDetails userDetails;

    public LandscapeServices(IntegrationLandscapeServiceWS landscapeServiceWS,
	    String password, String userName) {
	initService(landscapeServiceWS);
	initUserDetails(userName, password);
    }

    public LandscapeServices(String url, String password, String userName) {
	initService(url);
	initUserDetails(userName, password);
    }

    private String buildErrorResponse(List<Response> errors) {
	StringBuffer response = new StringBuffer("Landscape Service Failed ");
	for (Response error : errors) {
	    response.append(error).append(" ");
	}
	return response.toString();
    }

    private void errorMessage(Exception ex) {
	TWSConsumerLog
		.logError(
			"Error connecting to T24 check that Landscape Service is runnning, verify URL and user credentials",
			ex);
	LogConsole
		.getSoaConsole()
		.logMessage(
			"Error connecting to T24 check that Landscape Service is runnning, verify URL and user credentials");
    }

    public GetFieldsServiceResponse getApplicationFields(String applicationName) {
	GetFieldsServiceResponse response = null;
	GetFlowFieldsResponse result = servicePort.getFlowFields(userDetails,
		applicationName);

	if (result.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    String errorMessage = buildErrorResponse(result
		    .getResponseDetails().getValue().getResponses());
	    response = new GetFieldsServiceResponse(false, null, errorMessage);

	} else if (result.getApplicationFields().size() == 0) {
	    StringBuilder responseError = new StringBuilder(
		    "Error : LandscapeServices' getApplicationFields - ")
		    .append(applicationName).append(" : ")
		    .append(StringConstants.ERROR_MSG_RESPONSE_EMPTY);
	    response = new GetFieldsServiceResponse(false,
		    new ArrayList<Field>(), responseError.toString());

	} else {
	    List<Field> applicationFields = new ArrayList<Field>();
	    Character c = 253;
	    for (ApplicationField application : result.getApplicationFields()) {
		String temp = application.getName().getValue();
		if (temp.contains(c.toString())) {
		    temp = temp.substring(0, temp.indexOf(c.toString()));
		}
		Field field = new ApplicationVersionField();
		field.setFieldName(temp);
		temp = application.getDataType().getValue();
		field.setFieldType(temp);
		applicationFields.add(field);
	    }
	    response = new GetFieldsServiceResponse(true, applicationFields,
		    null);
	}
	return response;
    }

    public ServiceResponses getApplications() {
	ServiceResponses response = null;
	List<String> applications = null;
	GetApplicationsResponse result = servicePort
		.getApplications(userDetails);
	if (result.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    response = new ServiceResponses(false, null, result
		    .getResponseDetails().getValue().getResponses());
	} else {

	    applications = result.getApplicationNames();
	    response = new ServiceResponses(true, applications, null);
	}
	return response;
    }

    public ServiceResponses getApplicationsVersions() {
	ServiceResponses response = null;
	List<String> applicationsVersions = null;
	GetApplicationsVersionsResponse result = servicePort
		.getApplicationsVersions(userDetails);
	if (result.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    response = new ServiceResponses(false, null, result
		    .getResponseDetails().getValue().getResponses());

	} else {
	    applicationsVersions = result.getBaseEventNames();
	    response = new ServiceResponses(true, applicationsVersions, null);
	}
	return response;
    }

    public ServiceResponses getExitPoints() {
	List<String> versionExitPoint = new ArrayList<String>();
	ServiceResponses response = null;
	Character c = 253;
	GetVersionExitPointsResponse result = servicePort
		.getVersionExitPoints(userDetails);
	if (result.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    response = new ServiceResponses(false, null, result
		    .getResponseDetails().getValue().getResponses());
	} else {
	    for (VersionExitPoint exitPoint : result.getVersionExitPoints()) {
		String temp = exitPoint.getName().getValue();
		if (temp.contains(c.toString())) {
		    temp = temp.substring(0, temp.indexOf(c.toString()));
		}
		versionExitPoint.add(temp);
		response = new ServiceResponses(true, versionExitPoint, null);
	    }
	}
	return response;
    }

    public ServiceResponses getOverrideCodes() {
	List<String> overrideCodes = null;
	ServiceResponses response = null;
	GetOverrideCodesResponse result = servicePort
		.getOverrideCodes(userDetails);
	if (result.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    response = new ServiceResponses(false, null, result
		    .getResponseDetails().getValue().getResponses());
	} else {
	    overrideCodes = result.getOverrideCodes();
	    response = new ServiceResponses(true, overrideCodes, null);
	}
	return response;
    }

    /**
     * Helps to get the list of TSA Service Jobs.
     *
     * @return the list of TSA service names.
     */
    public ServiceResponses getTsaServices() {
	// get the response from service proxy.
	GetTsaServiceJobsResponse response = servicePort
		.getTsaServiceJobs(userDetails);
	if (response.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    return new ServiceResponses(false, null, response
		    .getResponseDetails().getValue().getResponses());

	}
	if (response == null) {
	    // for exceptional scenario
	    return new ServiceResponses(true, new ArrayList<String>(), null);
	}

	return new ServiceResponses(true, response.getTsaServiceJobs(), null);
    }

    public ServiceResponses getVersions() {
	ServiceResponses response = null;
	List<String> versions = null;
	GetVersionsResponse result = servicePort.getVersions(userDetails);
	if (result.getResponseDetails().getValue().getReturnCode().getValue()
		.equals("FAILURE")) {
	    response = new ServiceResponses(false, null, result
		    .getResponseDetails().getValue().getResponses());
	    return response;

	} else {
	    versions = result.getVersionNames();
	    response = new ServiceResponses(true, versions, null);

	}
	return response;
    }

    /**
     *
     *
     * @param landscapeServiceWS
     */

    private void initService(IntegrationLandscapeServiceWS landscapeServiceWS) {
	servicePort = landscapeServiceWS
		.getIntegrationLandscapeServiceWSHttpSoap11Endpoint();
    }

    /**
     *
     * @param url
     */
    private void initService(String url) {
	URL wsdlUrl = null;
	new ArrayList<String>();
	try {
	    wsdlUrl = new URL(url);
	} catch (MalformedURLException e) {
	    // TODO Handle them
	    e.printStackTrace();
	}
	try {
	    integrationlandscapeservicews.IntegrationLandscapeServiceWS service = new IntegrationLandscapeServiceWS(
		    wsdlUrl, new QName("http://IntegrationLandscapeServiceWS",
			    "IntegrationLandscapeServiceWS"));
	    servicePort = service
		    .getIntegrationLandscapeServiceWSHttpSoap11Endpoint();
	} catch (javax.xml.ws.WebServiceException ex) {
	    errorMessage(ex);
	}
    }

    /**
     *
     *
     * @param userName
     * @param password
     */

    private void initUserDetails(String userName, String password) {
	ObjectFactory factory = new ObjectFactory();
	userDetails = new com.temenos.soa.services.data.xsd.T24UserDetails();
	userDetails.setUser(factory.createT24UserDetailsUser(userName));
	userDetails.setPassword(factory.createT24UserDetailsPassword(password));
    }
}

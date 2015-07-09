package com.odcgroup.integrationfwk.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.WebServiceException;

import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.common.GetFieldsServiceResponse;
import com.odcgroup.integrationfwk.ui.common.ServiceResponses;
import com.odcgroup.integrationfwk.ui.model.Field;
import com.odcgroup.integrationfwk.ui.model.Parameter;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.services.connectivity.LandscapeServices;
import com.odcgroup.integrationfwk.ui.t24connectivity.T24Cache;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;
import com.temenos.soa.services.data.xsd.Response;

public class WebServiceController implements T24Connection {

    private final String projectName;
    private final LandscapeServices landscapeServices;
    private final T24Cache t24Cache;
    /** @see ComponentServiceController */
    private final ComponentServiceController componentServiceController;

    public WebServiceController(TWSConsumerProject currentProject) {
	projectName = currentProject.getProject().getName();
	landscapeServices = new LandscapeServices(
		getLandScapeURL(currentProject), currentProject.getPassword(),
		currentProject.getUserName());
	t24Cache = T24Cache.getT24Cache();
	componentServiceController = new ComponentServiceController(
		currentProject);
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

    public List<Field> getApplicationFields(String applicationName) {
	GetFieldsServiceResponse response = landscapeServices
		.getApplicationFields(applicationName);
	List<Field> fieldList = null;
	if (response.isStatus()) {
	    fieldList = response.getResponse();
	} else {
	    logWebServiceResponseErrors(response.getErrorMessage());
	}
	return fieldList;
    }

    public synchronized List<String> getApplicationList() {
	List<String> applicationList = t24Cache.getApplicationList(projectName);
	if (applicationList != null) {
	    return applicationList;
	}
	try {
	    ServiceResponses response = landscapeServices.getApplications();
	    if (response.isStatus()) {
		applicationList = response.getResponse();
		t24Cache.addApplicationList(applicationList, projectName);
	    } else {
		logWebServiceResponse(response.getErrorMessage());
	    }
	} catch (javax.xml.ws.WebServiceException ex) {
	    errorMessage(ex);
	}
	return applicationList;
    }

    public synchronized List<String> getApplicationsVersions() {
	// TODO IntegrationLandscapeAxis2.war has some problem with
	// getApplicationsVersions web method.
	// therefore instead of calling applicaitonsVersions web method,
	// getapplications and getVersions are called list are concatenated.
	List<String> applications = getApplicationList();
	List<String> versions = getVersionList();
	List<String> applicationsVersions = new ArrayList<String>();
	applicationsVersions.addAll(versions);
	applications.addAll(applications);
	return applicationsVersions;
    }

    public synchronized List<String> getExitPoints() {
	List<String> exitPointList = t24Cache.getExitPointList(projectName);
	if (exitPointList != null) {
	    return exitPointList;
	}
	try {
	    ServiceResponses response = landscapeServices.getExitPoints();
	    exitPointList = response.getResponse();
	    if (response.isStatus()) {
		t24Cache.addExitPoint(exitPointList, projectName);
	    } else {
		logWebServiceResponse(response.getErrorMessage());
	    }

	} catch (javax.xml.ws.WebServiceException ex) {
	    errorMessage(ex);
	}

	return exitPointList;
    }

    /**
     * Helps to get the landscape url from consumer project.
     *
     * @return landscape url.
     */
    private String getLandScapeURL(TWSConsumerProject currentProject) {
	String componentServiceURL = currentProject.getComponentServiceURL();
	return TWSConsumerProjectUtil
		.getLandscapeServiceURL(componentServiceURL);
    }

    public List<String> getOperationList(String serviceName) {
	return componentServiceController.getOperationList(serviceName);
    }

    public synchronized List<String> getOverrides() {
	List<String> overrideCodes = t24Cache.getOverridesList(projectName);

	if (overrideCodes != null) {
	    return overrideCodes;
	}
	try {
	    ServiceResponses response = landscapeServices.getOverrideCodes();
	    if (response.isStatus()) {
		overrideCodes = response.getResponse();
		t24Cache.addOverrides(overrideCodes, projectName);
	    } else {
		logWebServiceResponse(response.getErrorMessage());
	    }
	} catch (javax.xml.ws.WebServiceException ex) {
	    errorMessage(ex);
	}

	return overrideCodes;
    }

    public List<Parameter> getParameterList(String serviceName,
	    String operationName) {
	return componentServiceController.getParameters(serviceName,
		operationName);
    }

    public List<String> getServiceList() {
	return componentServiceController.getServiceList();
    }

    public List<String> getTSAServicesList() {
	List<String> tsaServices = t24Cache.getTsaServicesList(projectName);
	if (tsaServices != null) {
	    // returning the services list from cache.
	    return tsaServices;
	}
	try {

	    ServiceResponses response = landscapeServices.getTsaServices();
	    if (response.isStatus()) {
		tsaServices = response.getResponse();
		// update the cache.
		t24Cache.addTSAServiceList(tsaServices, projectName);

	    } else {
		logWebServiceResponse(response.getErrorMessage());
	    }
	    return tsaServices;
	} catch (WebServiceException e) {
	    errorMessage(e);
	    // return an empty list for exceptional scenario
	    return new ArrayList<String>();
	}
    }

    public synchronized List<String> getVersionList() {
	List<String> versionList = t24Cache.getVersionList(projectName);
	if (versionList != null) {
	    return versionList;
	}
	try {
	    ServiceResponses response = landscapeServices.getVersions();
	    if (response.isStatus()) {
		versionList = response.getResponse();
		t24Cache.addVersionList(versionList, projectName);
	    } else {
		logWebServiceResponse(response.getErrorMessage());
	    }

	} catch (javax.xml.ws.WebServiceException ex) {
	    errorMessage(ex);
	}

	return versionList;
    }

    // this method logs the service response errors from t24 to console
    private void logWebServiceResponse(List<Response> error) {
	StringBuffer response = new StringBuffer("Landscape Service Failed ");
	for (int i = 0; i < error.toArray().length; i++) {
	    response.append(error.get(i)).append(" ");
	}
	logWebServiceResponseErrors(response.toString());
    }

    private void logWebServiceResponseErrors(String error) {
	LogConsole.getSoaConsole().logMessage(error);
    }
}

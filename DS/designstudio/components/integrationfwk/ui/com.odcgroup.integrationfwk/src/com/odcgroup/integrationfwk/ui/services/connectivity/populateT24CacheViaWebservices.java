package com.odcgroup.integrationfwk.ui.services.connectivity;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.integrationfwk.ui.common.ServiceResponses;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.t24connectivity.T24Cache;
import com.odcgroup.integrationfwk.ui.utils.LogConsole;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;
import com.temenos.soa.services.data.xsd.Response;

public class populateT24CacheViaWebservices {

    private final LandscapeServices landscapeServices;
    private final TWSConsumerProject consumerProject;
    private final T24Cache t24Cache;
    private final IProgressMonitor monitor;
    private final String projectName;

    public populateT24CacheViaWebservices(TWSConsumerProject consumerProject,
	    LandscapeServices landscapeServices, IProgressMonitor monitor) {
	this.landscapeServices = landscapeServices;
	t24Cache = T24Cache.getT24Cache();
	this.consumerProject = consumerProject;
	this.monitor = monitor;
	projectName = consumerProject.getProject().getName();
    }

    /**
     * Method which helps to check whether the job is cancelled by user or not.
     * If cancelled then this method will perform the necessary action.
     *
     * @return true if cancelled, false otherwise.
     */
    private boolean isJobCancelled() {
	if (monitor.isCanceled()) {
	    T24Cache.getT24Cache().clearCache(projectName);
	    monitor.setCanceled(true);
	    return true;
	}
	return false;
    }

    // this method logs the service response errors from t24 to console
    private void logWebServiceResponse(List<Response> error)

    {
	StringBuffer response = new StringBuffer("Landscape Service Failed ");
	for (int i = 0; i < error.toArray().length; i++) {
	    response.append(error.get(i)).append(" ");
	}
	LogConsole.getSoaConsole().logMessage(response.toString());
    }

    private boolean populateApplication() {
	if (isJobCancelled()) {
	    return false;
	}
	monitor.setTaskName("populate application...");

	ServiceResponses response = landscapeServices.getApplications();
	if (response.isStatus()) {
	    List<String> applicationList = response.getResponse();

	    t24Cache.addApplicationList(applicationList, projectName);
	} else {
	    logWebServiceResponse(response.getErrorMessage());
	}
	monitor.worked(80);

	return response.isStatus();

    }

    private void populateApplicationVersion() {
	if (isJobCancelled()) {
	    return;
	}

	ServiceResponses response = landscapeServices.getApplicationsVersions();
	if (response.isStatus()) {

	    List<String> applicationVersionList = response.getResponse();

	    t24Cache.addApplicationVersion(applicationVersionList, projectName);
	} else {
	    logWebServiceResponse(response.getErrorMessage());
	}

    }

    /**
     * Responsible for collecting the component service related details from
     * service repository of T24 component framework
     */
    private void populateComponentService() {
	if (isJobCancelled()) {
	    return;
	}
	monitor.setTaskName("populate component services...");
	String wsdlUrl = TWSConsumerProjectUtil
		.getCatalogServiceURL(consumerProject.getComponentServiceURL());
	ComponentServices componentServices = new ComponentServices(wsdlUrl,
		consumerProject.getUserName(), consumerProject.getPassword());

	componentServices.getComponentServices(consumerProject.getPathString());
	monitor.worked(100);
    }

    private boolean populateExitPoint() {
	if (isJobCancelled()) {
	    return false;
	}
	monitor.setTaskName("populate exit point...");
	ServiceResponses response = landscapeServices.getExitPoints();
	if (response.isStatus()) {
	    List<String> exitPointList = response.getResponse();
	    t24Cache.addApplicationList(exitPointList, projectName);
	    monitor.worked(60);
	} else {
	    logWebServiceResponse(response.getErrorMessage());
	}

	return response.isStatus();
    }

    private boolean populateOverrides() {
	if (isJobCancelled()) {
	    return false;
	}
	monitor.setTaskName("populate overrides...");
	ServiceResponses response = landscapeServices.getOverrideCodes();
	if (response.isStatus()) {
	    List<String> overridesList = response.getResponse();

	    t24Cache.addOverrides(overridesList, projectName);
	    monitor.worked(90);
	} else {
	    logWebServiceResponse(response.getErrorMessage());
	}

	return response.isStatus();

    }

    private boolean populateVersion() {
	monitor.setTaskName("populate version...");

	ServiceResponses response = landscapeServices.getVersions();
	if (response.isStatus()) {
	    List<String> versionList = response.getResponse();
	    t24Cache.addVersionList(versionList, projectName);
	    if (isJobCancelled()) {
		monitor.setCanceled(true);
	    }
	    monitor.worked(40);
	} else {
	    logWebServiceResponse(response.getErrorMessage());
	}

	return response.isStatus();
    }

    public boolean start() {

	boolean status = false;
	if (!monitor.isCanceled()) {
	    status = populateVersion();
	    if (!status) {
		return status;
	    }
	}
	if (!monitor.isCanceled()) {
	    status = populateExitPoint();
	    if (!status) {
		return status;
	    }

	}
	if (!monitor.isCanceled()) {
	    status = populateApplication();
	    if (!status) {
		return status;
	    }

	}
	if (!monitor.isCanceled()) {
	    status = populateOverrides();
	    if (!status) {
		return status;
	    }

	    // if(!monitor.isCanceled())
	    // populateApplicationVersion(); TODO some problem
	    // with application
	    // and version, for time being calling versions and application and
	    // concatenating them.
	}

	if (!monitor.isCanceled()) {
	    populateComponentService();

	}

	return status;

    }
}

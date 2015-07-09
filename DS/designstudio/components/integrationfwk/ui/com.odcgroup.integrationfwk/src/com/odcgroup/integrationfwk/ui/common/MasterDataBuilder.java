package com.odcgroup.integrationfwk.ui.common;

import integrationlandscapeservicews.IntegrationLandscapeServiceWS;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.jbase.jremote.JRemoteException;
import com.odcgroup.integrationfwk.ui.IntegrationToolingActivator;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;
import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.services.connectivity.LandscapeServices;
import com.odcgroup.integrationfwk.ui.services.connectivity.populateT24CacheViaWebservices;
import com.odcgroup.integrationfwk.ui.t24connectivity.ConfigEntity;
import com.odcgroup.integrationfwk.ui.t24connectivity.IntegrationConnector;
import com.odcgroup.integrationfwk.ui.t24connectivity.T24Cache;
import com.odcgroup.integrationfwk.ui.utils.TWSConsumerProjectUtil;

/**
 * Responsible for build the master data into {@link T24Cache} from T24 using
 * connection provided in the project creation wizard.
 * 
 * @author sbharathraja
 * 
 */
public class MasterDataBuilder {

	/**
	 * Responsible for build the master data using JAgent
	 * 
	 * @param twsConsumerProject
	 */
	private static final void startAgentModeBuilder(
			final TWSConsumerProject twsConsumerProject) {
		Job backGroundJob = new WorkspaceJob("accessing data from T24") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				startBackgroundThread(twsConsumerProject, monitor);
				return new Status(Status.OK,
						IntegrationToolingActivator.PLUGIN_ID,
						"Master data's fetching from T24 is completed.");
			}
		};
		backGroundJob.schedule();
	}

	/**
	 * Starts back ground thread and populates cache with master data from T24.
	 * 
	 * @param twsProject
	 * @param monitor
	 */
	private static final void startBackgroundThread(
			TWSConsumerProject twsProject, IProgressMonitor monitor) {
		monitor.beginTask("Trying to connect with t24...", 100);
		ConfigEntity configEntity = new ConfigEntity();
		configEntity.setHostName(twsProject.getHost());
		configEntity.setPortNumber(twsProject.getPort());
		configEntity.setOfsSource(twsProject.getOfsSource());
		IntegrationConnector integrationConnector = null;
		try {
			integrationConnector = IntegrationConnector
					.getIntegrationConnector(configEntity, twsProject
							.getProject().getName());
			monitor.worked(10);
			integrationConnector.startBackgroundThread(twsProject,
					configEntity, monitor);
			monitor.done();
		} catch (JRemoteException e) {
			integrationConnector.disconnectJagent(twsProject.getProject()
					.getName());
			monitor.worked(100);
			monitor.done();
			TWSConsumerLog
					.logError(
							"Error connecting to T24 check that jbase_agent is runnning, verify host, port and OFS SOURCE settings",
							e);
		}
	}

	/**
	 * start the process of fetching master data from T24 using the connection
	 * details available within the given project.
	 * <p>
	 * Please ensure that the connection details attached with the given project
	 * are valid, before calling this utility.
	 * 
	 * @param twsConsumerProject
	 */
	public static final void startBuilding(TWSConsumerProject twsConsumerProject) {
		// avoid the exception
		if (twsConsumerProject == null) {
			return;
		}
		if (twsConsumerProject.isAgentConnection()) {
			startAgentModeBuilder(twsConsumerProject);
		} else {
			try {
				URL wsdlUrl = new URL(
						TWSConsumerProjectUtil
								.getLandscapeServiceURL(twsConsumerProject
										.getComponentServiceURL()));
				IntegrationLandscapeServiceWS landscapeServiceWS = new IntegrationLandscapeServiceWS(
						wsdlUrl, new QName(
								"http://IntegrationLandscapeServiceWS",
								"IntegrationLandscapeServiceWS"));
				startWebServiceModeBuilder(landscapeServiceWS,
						twsConsumerProject);
			} catch (MalformedURLException e) {
				TWSConsumerLog.logError(
						"Landscape service url is not well formed", e);
			}
		}
	}

	/**
	 * start the building process of master data via webservice mode.
	 * 
	 * @param landscapeServiceWS
	 * @param twsConsumerProject
	 */
	private static final void startWebServiceModeBuilder(
			final IntegrationLandscapeServiceWS landscapeServiceWS,
			final TWSConsumerProject twsConsumerProject) {

		Job backGroundJob = new WorkspaceJob("accessing data from T24") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				monitor.beginTask("trying to connect t24 via web services", 100);
				LandscapeServices landscapeServices = new LandscapeServices(
						landscapeServiceWS, twsConsumerProject.getPassword(),
						twsConsumerProject.getUserName());
				monitor.worked(20);
				boolean status = new populateT24CacheViaWebservices(twsConsumerProject,
					       landscapeServices, monitor).start();
				monitor.done();
				if (status) {
					return new Status(Status.OK,
							IntegrationToolingActivator.PLUGIN_ID,
							"Master data's fetching from T24 is completed.");
				} else {
					return new Status(
							Status.ERROR,
							IntegrationToolingActivator.PLUGIN_ID,
							"An error occurred while getting the T24 Services from T24. Check the console for error details");
				}

			}
		};
		backGroundJob.schedule();
	}

	private MasterDataBuilder() {

	}

}

package com.odcgroup.integrationfwk.ui.t24connectivity;

import org.eclipse.core.runtime.IProgressMonitor;

import com.odcgroup.integrationfwk.ui.projects.TWSConsumerProject;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ApplicationsVersionsLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ComponentService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.ExitPointLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.OverridesLandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.TSALandscapeService;
import com.odcgroup.integrationfwk.ui.t24connectivity.landscapeservice.VersionLandscapeService;

public class PopulateT24CacheViaJagent {

	private ApplicationLandscapeService applicationLandscapeService;
	private VersionLandscapeService versionLandscapeService;
	private OverridesLandscapeService overridesLandscapeService;
	private ExitPointLandscapeService exitPointLandsacpeService;
	private ApplicationsVersionsLandscapeService applicationsVersionsLandscapeService;
	private ComponentService componentService;
	/** instance of {@link TSALandscapeService} */
	private TSALandscapeService tsaLandscapeService;

	private final String projectName;
	private final ConfigEntity configEntity;
	private final String path;
	private final IProgressMonitor monitor;

	PopulateT24CacheViaJagent(TWSConsumerProject twsProject,
			ConfigEntity configEntity, IProgressMonitor monitor) {
		projectName = twsProject.getProject().getName();
		path = twsProject.getPathString();
		this.configEntity = configEntity;
		this.monitor = monitor;
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

	private void populateApplication() {
		if (isJobCancelled()) {
			return;
		}
		monitor.setTaskName("populate application landscape services...");
		applicationLandscapeService = new ApplicationLandscapeService(
				configEntity, projectName);
		applicationLandscapeService.getResponse(projectName, true);
		monitor.worked(55);
	}

	private void populateApplicationVersion() {
		if (isJobCancelled()) {
			return;
		}
		monitor.setTaskName("populate application version landscape services...");
		applicationsVersionsLandscapeService = new ApplicationsVersionsLandscapeService(
				configEntity, projectName);
		applicationsVersionsLandscapeService.getResponse(projectName, true);
		monitor.worked(90);
	}

	private void populateComponentService() {
		if (isJobCancelled()) {
			return;
		}
		monitor.setTaskName("populate component services...");
		componentService = new ComponentService(configEntity, projectName);
		componentService.getComponentService(path, projectName);
		monitor.worked(70);
	}

	private void populateExitPoint() {
		if (isJobCancelled()) {
			return;
		}
		monitor.setTaskName("populate exit point...");
		exitPointLandsacpeService = new ExitPointLandscapeService(configEntity,
				projectName);
		exitPointLandsacpeService.getResponse(projectName, true);
		monitor.worked(40);
	}

	private void populateOverrides() {
		if (isJobCancelled()) {
			return;
		}
		monitor.setTaskName("populate overrides...");
		overridesLandscapeService = new OverridesLandscapeService(configEntity,
				projectName);
		overridesLandscapeService.getResponse(projectName, true);
		monitor.worked(85);
	}

	private void populateTsaServices() {
		if (isJobCancelled()) {
			return;
		}
		monitor.setTaskName("populate TSA services...");
		tsaLandscapeService = new TSALandscapeService(configEntity, projectName);
		tsaLandscapeService.getTsaServicesList(projectName, true);
		monitor.worked(100);
	}

	private void populateVersion() {
		monitor.setTaskName("populate version...");
		versionLandscapeService = new VersionLandscapeService(configEntity,
				projectName);
		versionLandscapeService.getResponse(projectName, true);
		if (isJobCancelled()) {
			return;
		}
		monitor.worked(25);
	}

	public void start() {
		if (!monitor.isCanceled()) {
			populateVersion();
		}
		if (!monitor.isCanceled()) {
			populateExitPoint();
		}
		if (!monitor.isCanceled()) {
			populateApplication();
		}
		if (!monitor.isCanceled()) {
			populateComponentService();
		}
		if (!monitor.isCanceled()) {
			populateOverrides();
		}
		if (!monitor.isCanceled()) {
			populateApplicationVersion();
		}
		if (!monitor.isCanceled()) {
			populateTsaServices();
		}
	}
}

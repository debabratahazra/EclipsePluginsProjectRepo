package com.odcgroup.ocs.support.installer;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.repository.maven.IMavenConfigProvider;

public class OcsMavenConfigProvider implements IMavenConfigProvider {

	private static final Logger logger = LoggerFactory.getLogger(OcsMavenConfigProvider.class);	

	private static final String TARGET_PLATFORM_PATH = "com/odcgroup/odp/ocs-core";

	public File getLocalRepositoryLocation() {
		// DS-4018 NPE relative to OCS binaries folder occurs when starting DS
		// OSGi service might still need some time to be available
		waitForLocationService();
		return OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder();
	}

	public void waitForLocationService() {
		Location installLocation = Platform.getInstallLocation();
		for (int i = 0; i < 20 && installLocation == null; i++) {
			try {
				Thread.sleep(25L);
			} catch (InterruptedException e) { 
				// noop
			}
			installLocation = Platform.getInstallLocation();
		}
		if (installLocation == null) {
			throw new IllegalStateException();
		}
	}

	@Override
	public File getTargetPlatformPath() {
		File localRepository = getLocalRepositoryLocation();
		if (localRepository != null) {
			File targetPlatformPath = new File(localRepository, TARGET_PLATFORM_PATH);
			if (targetPlatformPath.exists()) {
				return targetPlatformPath;
			} else {
				logger.warn("No target platform path found in the locaL repository (" + TARGET_PLATFORM_PATH + ")");
			}
		} else {
			logger.warn("No target platform because no OCS binaries installation has been done (not local repository found.)");
		}
		return null;
	}

}

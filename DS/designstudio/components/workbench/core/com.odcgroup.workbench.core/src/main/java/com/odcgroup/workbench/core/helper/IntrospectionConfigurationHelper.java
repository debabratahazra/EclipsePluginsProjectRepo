package com.odcgroup.workbench.core.helper;

import org.eclipse.core.runtime.Platform;

/**
 * This helper class provides method to find the configuration folder available 
 * in application installed location
 * 
 * @author mumesh
 * 
 */
public class IntrospectionConfigurationHelper {

	private static String CONFIGURATION_FOLDER = "Configuration";

	public static String getConfigurationFolderPath() {
		String configurationPath;
		String installationPath = Platform.getInstallLocation().getURL().getPath();
		configurationPath = installationPath.concat(CONFIGURATION_FOLDER);
		return configurationPath;
	}

}

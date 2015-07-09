package com.odcgroup.workbench.core.targetplatform;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.maven.IMavenConfigProvider;

/**
 * Hold the target platform information used by DesignStudio
 * @author yan
 */
public class TargetPlatform {

	private static final String TARGET_PLATFORM_VERSION_ATTRIBUTE_NAME = "ds.target.platform.version";

	private static final String MAVEN_CONFIG_EXTENSION_POINT = "com.odcgroup.workbench.core.maven.config.provider";

	static final private Logger logger = LoggerFactory.getLogger(TargetPlatform.class);

	/**
	 * @return the version of the target platform
	 */
	public static String getTechnicalVersion() {
		String technicalVersion = chooseTargetPlatformVersion(installedTargetPlatforms());
		
		if (technicalVersion == null) {
			logger.error("No Target Platform version defined: please use -D"+TARGET_PLATFORM_VERSION_ATTRIBUTE_NAME+"=some.value");
			logger.error("Unexpected behavior can occur if this property is not set");
		}
		return technicalVersion;
	}
	
	public static List<String> installedTargetPlatforms() {
		List<String> result = new ArrayList<String>();
		File targetPlatformPath = getTargetPlatformPath();
		if (targetPlatformPath != null && targetPlatformPath.exists()) {
			// filters version folders that contains .pom file
			File[] listFiles = targetPlatformPath.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory() && 
							StringUtils.isNumeric(pathname.getName().substring(0, 0)) &&
							containsPom(pathname);
				}

				// Useful to avoid folders with only .lastUpdated
				private boolean containsPom(File pathname) {
					if (pathname.exists()) {
						File[] pomFiles = pathname.listFiles(new FileFilter() {
							@Override
							public boolean accept(File pathname) {
								return pathname.getName().endsWith(".pom");
							}
						});
						return pomFiles.length > 0;
					} else {
						return false;
					}
				}
			});
			for (File versionFolder : listFiles) {
				result.add(versionFolder.getName());
			}
		}
		return result;
	}
	
	static IMavenConfigProvider mavenConfigProvider;
	private static File getTargetPlatformPath() {
		if (mavenConfigProvider == null) {
			// Retrieve the maven config extension point
			IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(MAVEN_CONFIG_EXTENSION_POINT);
			if (config.length == 1) {
				try {
					Object executableExtension = config[0].createExecutableExtension("type");
					if ((executableExtension instanceof IMavenConfigProvider)) {
						mavenConfigProvider = (IMavenConfigProvider)executableExtension;
					}
				} catch (CoreException e) {
					mavenConfigProvider = null;
				}
			}
		}
		if (mavenConfigProvider != null) {
			return mavenConfigProvider.getTargetPlatformPath();
		} else {
			return null;
		}
	}

	private static String chooseTargetPlatformVersion(List<String> targetPlatformVersions) {
		final String technicalVersionSpecifiedBySystemProperty = System.getProperty(TARGET_PLATFORM_VERSION_ATTRIBUTE_NAME);
		final String defaultTechnicalVersion = OfsCore.getDefault().getString(TARGET_PLATFORM_VERSION_ATTRIBUTE_NAME);
		
		String choosenTargetPlatformVersion = null;
		if (!targetPlatformVersions.isEmpty()) {
			if (targetPlatformVersions.contains(technicalVersionSpecifiedBySystemProperty)) {
				return selectTargetPlatform(technicalVersionSpecifiedBySystemProperty, "forced by system property", targetPlatformVersions);
			} else if (targetPlatformVersions.contains(defaultTechnicalVersion)) {
				return selectTargetPlatform(defaultTechnicalVersion, "default version", targetPlatformVersions);
			} else {
				Collections.sort(targetPlatformVersions);
				return selectTargetPlatform(targetPlatformVersions.get(targetPlatformVersions.size()-1), "latest version found in available versions", targetPlatformVersions);
			}
		} else {
			if (technicalVersionSpecifiedBySystemProperty != null) {
				return selectTargetPlatform(technicalVersionSpecifiedBySystemProperty, "forced by system property", targetPlatformVersions);
			} else if (defaultTechnicalVersion != null) {
				return selectTargetPlatform(defaultTechnicalVersion, "default version", targetPlatformVersions);
			} else {
				return selectTargetPlatform("1.0-SNAPSHOT", "default version", targetPlatformVersions);
			}
		}
	}
	
	static String lastMessageLogged;
	private static String selectTargetPlatform(String selectedTargetPlatform, String reason, List<String> availableTargetPlatformVersions) {
		String message = "Target Platform selected: " + selectedTargetPlatform + " (" + reason + "). " +
				"Available versions found: [" + StringUtils.join(availableTargetPlatformVersions, ", ") + "].";
		if (!message.equals(lastMessageLogged)) {
			OfsCore.getDefault().logInfo(message, null);
			lastMessageLogged = message;
		}
		return selectedTargetPlatform;
	}
}

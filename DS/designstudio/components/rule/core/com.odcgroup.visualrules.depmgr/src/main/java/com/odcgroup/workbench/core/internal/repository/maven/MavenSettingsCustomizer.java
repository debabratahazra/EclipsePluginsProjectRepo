package com.odcgroup.workbench.core.internal.repository.maven;

import java.io.File;

import org.apache.maven.settings.Mirror;
import org.apache.maven.settings.Settings;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.maven.IMavenConfigProvider;

import de.visualrules.artifact.maven.util.ISettingsCustomizer;

/**
 * This class modifies the settings that the VR-embedded Maven runtime is using
 * 
 * @author Kai Kreuzer
 *
 */
public class MavenSettingsCustomizer implements ISettingsCustomizer {

	protected static final Logger logger = LoggerFactory.getLogger(MavenSettingsCustomizer.class);
	
	private static final String MAVEN_CONFIG_EXTENSION_POINT = "com.odcgroup.workbench.core.maven.config.provider";

	@Override
	public void customize(Settings settings) {
		
		// DS-3544: We need to add a mirror for the internal Odyssey repository
		// with a dummy URL as otherwise Maven will try to connect to it
		// (as there are some poms, which define this repository)

		Mirror mirror = new Mirror();
		mirror.setId("fake");
		mirror.setName("Fake Odyssey Maven Repository");
		mirror.setMirrorOf("external:*");
		mirror.setUrl("dummy");
		settings.addMirror(mirror);
		
		String forcedLocalRepo = OfsCore.getForcedLocalRepository();
		if (forcedLocalRepo != null) {
			logger.info("Forcing maven local repository to: " + forcedLocalRepo);
			logger.info("(defined in maven.repo.local system properties)");
			settings.setLocalRepository(forcedLocalRepo);
		} else {
			File localRepo = getLocalRepository();
			if (localRepo != null) {
				logger.info("Setting maven local repository to: " + localRepo.getAbsolutePath());
				logger.info("(forced by OCS maven integration, i.e. ocs-binaries)");
				settings.setLocalRepository(localRepo.getAbsolutePath());
			} else {
				logger.info("Local repository not overridden. Will use the settings.xml found in %USER%/.m2 folder");
			}
		}
	}
	
	/**
	 * @return the maven local repository to be used
	 */
	private static File getLocalRepository() {
		// Retrieve the maven config extension point
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(MAVEN_CONFIG_EXTENSION_POINT);
		
		if (config.length != 1) {
			logger.error("The extension point is not properly configured");
			throw new IllegalStateException("The extension point is not properly configured");
		}

		try {
			Object mavenConfig = config[0].createExecutableExtension("type");
			if (!(mavenConfig instanceof IMavenConfigProvider)) {
				logger.error("The extension point is not properly configured (wrong type)");
				throw new IllegalStateException("The extension point is not properly configured (wrong type)");
			}
			return ((IMavenConfigProvider)mavenConfig).getLocalRepositoryLocation();
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

}

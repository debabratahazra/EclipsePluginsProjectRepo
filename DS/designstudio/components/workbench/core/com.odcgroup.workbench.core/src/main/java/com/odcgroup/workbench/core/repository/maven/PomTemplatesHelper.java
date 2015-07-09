package com.odcgroup.workbench.core.repository.maven;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.OfsCore;

public class PomTemplatesHelper {

	private static final String POM_TEMPLATE_EXTENTION_POINT = "com.odcgroup.workbench.core.maven.pom.template";

	protected static final Logger logger = LoggerFactory.getLogger(PomTemplatesHelper.class);

	public static IPomTemplatesProvider getPomTemplatesProvider() {
		// Retrieve the maven config extension point
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(POM_TEMPLATE_EXTENTION_POINT);
		
		if (config.length == 0) {
			final String message = "No extension point is defined. The template feature will be not working properly.";
			logger.error(message);
			OfsCore.getDefault().logError(message, null);
			throw new IllegalStateException(message);
		}

		if (config.length > 1) {
			final String message = "Only one extension point should be defined. Taking the first one.";
			logger.error(message);
			OfsCore.getDefault().logError(message, null);
		}

		try {
			Object templateProvider = config[0].createExecutableExtension("class");
			if (!(templateProvider instanceof IPomTemplatesProvider)) {
				logger.error("The extension point is not properly configured (wrong type)");
				throw new IllegalStateException("The extension point is not properly configured (wrong type)");
			} else if (config.length > 1) {
				logger.info("The first " + POM_TEMPLATE_EXTENTION_POINT + " defined is " + templateProvider.getClass());
			}
			return (IPomTemplatesProvider)templateProvider;
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

}

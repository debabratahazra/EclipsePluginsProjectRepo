package com.odcgroup.workbench.core.migration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.workbench.core.repository.LanguageRepositoryProvider;

public final class MigrationProcessorProvider {

	private static final Logger logger = LoggerFactory.getLogger(LanguageRepositoryProvider.class);

	// the declaration of this extension point is optional.
	private static String MIGRATION_PROVIDER_EXTENSION_POINT = "com.odcgroup.workbench.core.migration.provider";

	private static MigrationProcessorProvider _instance = new MigrationProcessorProvider();

	private synchronized IMigrationProcessor doGetMigrationProcessor() throws CoreException {
		IMigrationProcessor provider = null;
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(
				MIGRATION_PROVIDER_EXTENSION_POINT);
		if (config.length == 1) {
			IConfigurationElement element = config[0];
			provider = (IMigrationProcessor) element.createExecutableExtension("class");
		} else if (config.length > 1) {
			logger.error("There are ["+config.length+"] definition(s) for the extension point "
					+ MIGRATION_PROVIDER_EXTENSION_POINT + ". At most one is required per product.");
		}
		return provider;
	}

	/**
	 * @return a migration processor or null if not defined.
	 * @throws CoreException
	 */
	public static IMigrationProcessor getMigrationProcessor() throws CoreException {
		return _instance.doGetMigrationProcessor();
	}

}

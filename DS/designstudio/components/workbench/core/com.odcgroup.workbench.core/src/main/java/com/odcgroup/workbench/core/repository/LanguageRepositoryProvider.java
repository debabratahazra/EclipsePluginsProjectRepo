package com.odcgroup.workbench.core.repository;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LanguageRepositoryProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageRepositoryProvider.class);

	private static String LANGUAGE_REPOSITORY_EXTENSION_POINT = "com.odcgroup.workbench.language.repository";

	private static LanguageRepositoryProvider _instance = new LanguageRepositoryProvider();

	private Map<String, ? super ILanguageRepository> repositories = new HashMap<String, ILanguageRepository>();

	private synchronized <T extends ILanguageRepository> T doGetLanguageRepository(String languageName) {
		T repository = (T)repositories.get(languageName);
		if (repository == null) {
			try {
				IConfigurationElement[] config = Platform.getExtensionRegistry()
						.getConfigurationElementsFor(LANGUAGE_REPOSITORY_EXTENSION_POINT);
				for (IConfigurationElement e : config) {
					String value = e.getAttribute("language");
					if (languageName.equals(value)) {
						repository = (T) e.createExecutableExtension("class");
						repositories.put(languageName, repository);
						break;
					}
				}
			} catch (Exception ex) {
				logger.error("Unable to load extension point for language"+languageName, ex);
			}
		}
		return repository;
	}

	public static <T extends ILanguageRepository> T getLanguageRepository(String languageName) {
		return _instance.doGetLanguageRepository(languageName);
	}

}

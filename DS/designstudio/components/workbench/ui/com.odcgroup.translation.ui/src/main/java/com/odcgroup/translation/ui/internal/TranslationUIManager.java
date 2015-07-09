package com.odcgroup.translation.ui.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.editor.model.ITranslationCollectorProvider;

/**
 *
 * @author pkk
 *
 */
public class TranslationUIManager {
	

	private static Logger logger = LoggerFactory.getLogger(TranslationUIManager.class);

	/** Extension point */
	private static final String TRANSLATION_COLLECTOR = "com.odcgroup.translation.collector";
	
	/**
	 * @return
	 */
	public static List<ITranslationCollectorProvider> loadCollectorProviders() {
		List<ITranslationCollectorProvider> collectorProviders = new ArrayList<ITranslationCollectorProvider>();
		logger.trace(TranslationCore.LOG_MARKER,
				"Starting to load all Collector Providers from Extension Point: {}",
				TRANSLATION_COLLECTOR);

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TRANSLATION_COLLECTOR);
		IExtension[] extensions = point.getExtensions();
		for (IExtension iExtension : extensions) {
			IConfigurationElement[] configElements = iExtension.getConfigurationElements();
			for (IConfigurationElement configElement : configElements) {
				try {
					ITranslationCollectorProvider provider = (ITranslationCollectorProvider) configElement.createExecutableExtension("class");
					provider.setDefaultActivated(new Boolean(configElement.getAttribute("defaultActive")).booleanValue());
					provider.setDisplayName(configElement.getAttribute("displayName"));
					provider.setModelExtensions(configElement.getAttribute("fileExtensions"));
					collectorProviders.add(provider);
				} catch (CoreException ex) {
					logger.error(TranslationCore.LOG_MARKER,
							"A TranslatonCollector cannot be instantiated, please check extension point: {}",
							TRANSLATION_COLLECTOR, ex);
				}
			}
			
		}
		sortCollectors(collectorProviders);
		return collectorProviders;
	}
	
	/**
	 * sort collectors by displayName
	 */
	private static void sortCollectors(List<ITranslationCollectorProvider> collectorProviders) {
		Comparator<ITranslationCollectorProvider> comparator = new Comparator<ITranslationCollectorProvider>() {
			@Override
			public int compare(ITranslationCollectorProvider o1, ITranslationCollectorProvider o2) {
				return o1.getDisplayName().compareTo(o2.getDisplayName());
			}
			
		};
		Collections.sort(collectorProviders, comparator);
	}

}

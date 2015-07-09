package com.odcgroup.translation.ui.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.command.ITranslationCommandProvider;
import com.odcgroup.translation.ui.internal.views.TranslationViewer;
import com.odcgroup.translation.ui.views.ITranslationTableProvider;

/**
 *
 * @author atr
 */
public final class TranslationTableManager {

	private static Logger logger = LoggerFactory.getLogger(TranslationViewer.class);
	
	/** Extension point */
	private static final String TRANSLATION_TABLE_PROVIDER = "com.odcgroup.translation.viewer";
	
	/** Extension point */
	private static final String TRANSLATION_COMMAND_PROVIDER = "com.odcgroup.translation.viewer.command";

	/** All Translation Table Providers declared in Extension Points */
	private static List<ITranslationTableProvider> translationTableProviders = null;
	
	/** All Translation Command Providers declared in Extension Points */
	private static List<ITranslationCommandProvider> translationCommandProviders = null;

	/** */
	private Object lock = new Object();
	
	/** */
	private IProject project;

	/**
	 * @return
	 */
	private static List<ITranslationTableProvider> loadTableProvidersFromExtensionPoints() {

		// to collect all declared translation providers
		List<ITranslationTableProvider> providers = new ArrayList<ITranslationTableProvider>();

		logger.trace(TranslationCore.LOG_MARKER,
				"Starting to load all Translation Table Providers from Extension Point: {}",
				TRANSLATION_TABLE_PROVIDER);

		// walk through all declared extension points
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TRANSLATION_TABLE_PROVIDER);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx = 0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx];
				try {
					ITranslationTableProvider provider = (ITranslationTableProvider) configElement
							.createExecutableExtension("class");
					providers.add(provider);
					logger.trace(TranslationCore.LOG_MARKER,
							"Loaded Translation Table Provider: {}",
							provider.getId());
				} catch (CoreException ex) {
					logger.error(TranslationCore.LOG_MARKER,
							"A TranslatonTableProvider cannot be instantiated, please check extension point: {}",
							TRANSLATION_TABLE_PROVIDER, ex);
				} finally {

				}

			}
		}

		logger.trace(TranslationCore.LOG_MARKER,
				"All Translation Table Providers have been loaded: {}",
				providers.size());

		return providers;
	}	
	
	/**
	 * @return
	 */
	private static List<ITranslationCommandProvider> loadCommandProvidersFromExtensionPoints() {

		// to collect all declared translation providers
		List<ITranslationCommandProvider> providers = new ArrayList<ITranslationCommandProvider>();

		logger.trace(TranslationCore.LOG_MARKER,
				"Starting to load all Translation Command Providers from Extension Point: {}",
				TRANSLATION_COMMAND_PROVIDER);

		// walk through all declared extension points
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TRANSLATION_COMMAND_PROVIDER);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx = 0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx];
				try {
					ITranslationCommandProvider provider = (ITranslationCommandProvider) configElement
							.createExecutableExtension("class");
					providers.add(provider);
					logger.trace(TranslationCore.LOG_MARKER,
							"Loaded Translation Command Provider: {}",
							provider.getId());
				} catch (CoreException ex) {
					logger.error(TranslationCore.LOG_MARKER,
							"A TranslatonCommandProvider cannot be instantiated, please check extension point: {}",
							TRANSLATION_COMMAND_PROVIDER, ex);
				} finally {

				}

			}
		}

		logger.trace(TranslationCore.LOG_MARKER,
				"All Translation Command Providers have been loaded: {}",
				providers.size());

		return providers;
	}	
	
	/**
	 * @return
	 */
	protected List<ITranslationTableProvider> getTranslationTableProviders() {
		if (null == translationTableProviders) {
			synchronized(lock) {
				if (null == translationTableProviders) {
					translationTableProviders = loadTableProvidersFromExtensionPoints();
				}
			}
		}
		return translationTableProviders;
	}
	
	/**
	 * @return
	 */
	protected List<ITranslationCommandProvider> getTranslationCommandProviders() {
		if (null == translationCommandProviders) {
			synchronized(lock) {
				if (null == translationCommandProviders) {
					translationCommandProviders = loadCommandProvidersFromExtensionPoints();
				}
			}
		}
		return translationCommandProviders;
	}
	
	/**
	 * @param parent
	 * @return
	 */
	public ITranslationTableProvider getTranslationTableProvider() {

		// Looks for a provider that accepts the object passed in parameter.
		ITranslationTableProvider provider = null;
		int priority = -1;
		for (ITranslationTableProvider tp : getTranslationTableProviders()) {
			try {
				if (project.hasNature(tp.getNature())) {
					if (tp.getPriority() > priority) {
						provider = tp;
						priority = tp.getPriority();
					}
				}
			} catch (CoreException ex) {
				logger.warn(TranslationCore.LOG_MARKER,
						"Cannot read the nature of the project", ex);
			}
		}

		return provider;

	}

	/**
	 * @param parent
	 * @return
	 */
	public ITranslationCommandProvider getTranslationCommandProvider(ITranslation translation) {

		// Looks for a provider that accepts the object passed in parameter.
		ITranslationCommandProvider provider = null;
		
		Object owner = translation.getOwner();
		
		int priority = -1;
		for (ITranslationCommandProvider tp : getTranslationCommandProviders()) {
			try {
				if (project.hasNature(tp.getNature())) {
					if (tp.getPriority() > priority) {
						for (Class<?> clazz : tp.getInputTypes()) {
							if (clazz.isAssignableFrom(owner.getClass())) {
									// found one
									provider = tp;
									priority = tp.getPriority();
							}
						}
					}
				}
			} catch (CoreException ex) {
				logger.warn(TranslationCore.LOG_MARKER,
						"Cannot read the nature of the project", ex);
			}
		}
		
		return provider;

	}	
	/**
	 * @param project
	 */
	public TranslationTableManager(IProject project) {
		this.project = project;
	}

}

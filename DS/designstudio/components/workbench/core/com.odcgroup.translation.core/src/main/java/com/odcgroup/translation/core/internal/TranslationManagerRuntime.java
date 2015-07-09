package com.odcgroup.translation.core.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitorProvider;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;

/**
 * TODO: Document me!
 *
 * @author atr
 */
public final class TranslationManagerRuntime {
	
	private static Logger logger = LoggerFactory.getLogger(TranslationManager.class);

	/** Extension point */
	private static final String TRANSLATION_PROVIDER = "com.odcgroup.translation.provider";

	/** Extension point */
	private static final String TRANSLATION_MODEL_VISITOR = "com.odcgroup.translation.visitor";

	/** Maintains the association beetwen project and translation managers */
	private static ConcurrentHashMap<IProject, ITranslationManager> managers = new ConcurrentHashMap<IProject, ITranslationManager>();

	/** All Translation Providers declared in Extension Points */
	private static List<ITranslationProvider> translationProviders = null;

	/** All Translation Visitors declared in Extension Points */
	private static List<ITranslationModelVisitorProvider> modelVisitors = null;

	/** protects the collection translationProviders */
	private static Object lock = new Object();
	
	/**
	 * This class listen to resource changes, and removes the projects from the
	 * map whenever a project is closed.
	 */
	private static class RemoveProjectListener implements IResourceChangeListener {
		public synchronized void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
			case IResourceChangeEvent.POST_CHANGE:
				IResource res = event.getDelta().getResource();

				// remove project from map, if it has been deleted from the
				// workspace
				if ((res instanceof IWorkspaceRoot)) {
					IResourceDelta[] children = event.getDelta().getAffectedChildren();
					if (children.length == 1) {
						IResource child = children[0].getResource();
						if ((child instanceof IProject) && children[0].getKind() == IResourceDelta.REMOVED) {
							managers.remove((IProject) child);
							logger.trace(TranslationCore.LOG_MARKER,
									"Translation Manager for project [{}] successfuly uninstalled", 
									child.getName());
						}
					}
				}

			}
		}
	}

	/** */
	private static RemoveProjectListener removeProjectListener;
	
	
	
	/**
	 * @return
	 */
	private static List<ITranslationProvider> loadTranslationProvidersFromExtensionPoints() {

		// to collect all declared translation providers
		List<ITranslationProvider> providers = new ArrayList<ITranslationProvider>();

		logger.trace(TranslationCore.LOG_MARKER,
				"Starting to load all Translation Providers from Extension Point: {}",
				TRANSLATION_PROVIDER);

		// walk through all declared extension points
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TRANSLATION_PROVIDER);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx = 0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx];
				try {
					ITranslationProvider provider = (ITranslationProvider) configElement
							.createExecutableExtension("class");
					providers.add(provider);
					logger.trace(TranslationCore.LOG_MARKER,
								"Loaded Translation Provider: {}",
								provider.getId());
				} catch (CoreException ex) {
					logger.trace(TranslationCore.LOG_MARKER,
							"(ERROR) A TranslatonProvider cannot be instantiated, please check extension point:{}",
							TRANSLATION_PROVIDER, ex);
				} finally {

				}

			}
		}

		logger.trace(TranslationCore.LOG_MARKER,
				"All Translation Providers have been loaded: {}",
				providers.size());

		return providers;
	}	

	/**
	 * @return
	 */
	private static List<ITranslationModelVisitorProvider> loadModelVisitorProvidersFromExtensionPoints() {

		// to collect all declared translation providers
		List<ITranslationModelVisitorProvider> providers = new ArrayList<ITranslationModelVisitorProvider>();

		logger.trace(TranslationCore.LOG_MARKER,
				"Starting to load all providers for visiting translations in models from Extension Point: {}",
				TRANSLATION_MODEL_VISITOR);

		// walk through all declared extension points
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(TRANSLATION_MODEL_VISITOR);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx = 0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx];
				try {
					ITranslationModelVisitorProvider provider = (ITranslationModelVisitorProvider) configElement
							.createExecutableExtension("class");
					providers.add(provider);
					logger.trace(TranslationCore.LOG_MARKER,
								"Loaded providers for visiting translations in models: {}",
								provider.getId());
				} catch (CoreException ex) {
					logger.trace(TranslationCore.LOG_MARKER,
							"(ERROR) A provider for visiting translations in models cannot be instantiated, please check extension point:{}",
							TRANSLATION_MODEL_VISITOR, ex);
				} finally {

				}

			}
		}

		logger.trace(TranslationCore.LOG_MARKER,
				"All providers for visiting translations in models have been loaded: {}",
				providers.size());

		return providers;
	}		
	/**
	 * @return a Translation Manager given a IProject
	 */
	public static ITranslationManager getManager(IProject project) {
		if(project==null) return null;
		
		// Gets the translation manager
		ITranslationManager manager = managers.get(project);
		if (null == manager) {
			// there is no manager for the project
			TranslationManager newManager = new TranslationManager(project) {
				protected List<ITranslationProvider> getTranslationProviders() {
					if (null == translationProviders) {
						synchronized(lock) {
							if (null == translationProviders) {
								translationProviders = loadTranslationProvidersFromExtensionPoints();
							}
						}
					}
					return translationProviders;
				}
				protected List<ITranslationModelVisitorProvider> getTranslationModelVisitorProviders() {
					if (null == modelVisitors) {
						synchronized(lock) {
							if (null == modelVisitors) {
								modelVisitors = loadModelVisitorProvidersFromExtensionPoints();
							}
						}
					}
					return modelVisitors;
				}
			};
			manager = managers.putIfAbsent(project, newManager);
			if (manager == null) {
				logger.trace(TranslationCore.LOG_MARKER,
						"Translation Manager for project [{}] successfuly installed", 
						project.getName());
				manager = newManager;
			}
		}

		return manager;
	}

	/**
	 * 
	 */
	public static void install() {

		logger.trace(TranslationCore.LOG_MARKER,
				"Starting Translation Manager Provider");
		
		// Install the listener 
		if (null == removeProjectListener) {
			removeProjectListener = new RemoveProjectListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(removeProjectListener,
					IResourceChangeEvent.POST_CHANGE);
		}
		
	}
	
	/**
	 * 
	 */
	public static void uninstall() {

		logger.trace(TranslationCore.LOG_MARKER, "Stopping Translation Manager Provider");

		// Uninstall the listener 
		if (removeProjectListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(removeProjectListener);
			removeProjectListener = null;
		}

		// Release all Translation Providers
		if (translationProviders != null) {
			translationProviders.clear();
			translationProviders = null;
			logger.trace(TranslationCore.LOG_MARKER, "Translation Providers released");
		}

		// Release all model visitors
		if (modelVisitors != null) {
			modelVisitors.clear();
			modelVisitors = null;
			logger.trace(TranslationCore.LOG_MARKER, "Translation Model Vistors released");
		}
	}
}

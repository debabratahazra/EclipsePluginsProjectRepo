package com.odcgroup.translation.generation.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.IFilter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorHandler;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationGenerationRuntime;
import com.odcgroup.translation.generation.ITranslationGenerator;
import com.odcgroup.translation.generation.ITranslationGeneratorProvider;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.ITranslationKeyHandler;
import com.odcgroup.translation.generation.ITranslationKeyProvider;
import com.odcgroup.translation.generation.ITranslationKeyTable;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.translation.generation.internal.registry.TranslationKeyTable;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.OfsCore;

/**
 * @author atr
 */
final class TranslationGenerationRuntime implements ITranslationGenerationRuntime {

	private static Logger logger = LoggerFactory.getLogger(TranslationGenerationRuntime.class);

	/** Extension point */
	private static final String TRANSLATION_KEY_PROVIDER = "com.odcgroup.translation.generation.keyprovider";
	/** Extension point */
	private static final String TRANSLATION_GENERATOR_PROVIDER = "com.odcgroup.translation.generation.generator";

	/**
	 * The registered key providers
	 */
	private List<ITranslationKeyProvider> keyProviders;

	/**
	 * The registered generator providers
	 */
	private List<ITranslationGeneratorProvider> genProviders;
	
	/** registered model names */
	private Set<String> modelNames;

	private Map<URI, ITranslationKeyTable> tableMap = new HashMap<URI, ITranslationKeyTable>();

	/**
	 * @param <T>
	 * @param extensionPoint
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> loadProvidersFromExtensionPoint(String extensionPoint) {
		// to collect all declared translation providers
		List<T> providers = new ArrayList<T>();

		logger.trace(TranslationGenerationCore.LOG_MARKER, "Starting to load all providers from extension point: {}",
				extensionPoint);

		// walk through all declared extension points
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(extensionPoint);
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx = 0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx];
				try {
					T provider = (T) configElement.createExecutableExtension("class");
					providers.add(provider);
					logger.trace(TranslationGenerationCore.LOG_MARKER, "Loaded provider: {}", provider);
				} catch (CoreException ex) {
					String id = configElement.getAttribute("id");
					logger.trace(TranslationGenerationCore.LOG_MARKER,
							"(ERROR) A provider cannot be instantiated, please check the extension point:{} id:{}", 
							new Object[]{extensionPoint, id},
							ex);
				} 
			}
		}

		logger.trace(TranslationCore.LOG_MARKER, "Count of providers loaded: {}", providers.size());
		return providers;
	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	protected ITranslationKeyProvider getTranslationKeyProvider(final Object object) {

		// Looks for a provider that accepts the object passed in parameter.
		for (ITranslationKeyProvider tkp : this.keyProviders) {
			for (Class<?> clazz : tkp.getInputTypes()) {
				if (clazz.isAssignableFrom(object.getClass())) {
					IFilter filter = tkp.getFilter();
					if (filter == null || filter.select(object)) {
						// found one
						logger.trace(TranslationCore.LOG_MARKER, 
								"Translation Key Provider found: {}", 
								tkp.getId());
						return tkp;
					}
				}
			}
		}

		logger.trace(TranslationCore.LOG_MARKER, 
				"Translation Provider not found for object's class {}", 
				object.getClass().getName());

		return null;

	}

	/**
	 * @param project
	 * @param object
	 * @return The translation key
	 */
	public ITranslationKey getTranslationKey(IProject project, ITranslation translation) {
		ITranslationKey key = null;
		if (translation != null) {
			ITranslationKeyProvider tkp = getTranslationKeyProvider(translation.getOwner());
			if (tkp != null) {
				key = tkp.getTranslationKey(project, translation);
			}
		}
		return key;
	}

	@Override
	public void generate(IProject project, Collection<IOfsModelResource> modelResources, Map<String, ?> properties) throws CoreException {
		initTables(project, modelResources);

		// find the generator
		final ITranslationGenerator generator = getTranslationGenerator(project, properties);

		// start it
		generator.startGeneration();

		// walk through all translations key and generate
		for (ITranslationKeyTable table : tableMap.values()) {
			table.visit(new ITranslationKeyHandler() {
				@Override
				public void notify(ITranslationKey key) throws CoreException {
					generator.generate(key);
				}
			});
		}

		generator.endGeneration();

		for (ITranslationKeyTable table : tableMap.values()) {
			table.clear();
		}
		tableMap.clear();
	}

	@Override
	public void release(BundleContext context) {
		tableMap.clear();
	}

	/**
	 * @param context
	 */
	public TranslationGenerationRuntime(BundleContext context) {
		keyProviders = loadProvidersFromExtensionPoint(TRANSLATION_KEY_PROVIDER);
		genProviders = loadProvidersFromExtensionPoint(TRANSLATION_GENERATOR_PROVIDER);
		modelNames = new HashSet<String>(Arrays.asList(OfsCore.getRegisteredModelNames()));
		modelNames.add("mml");
		modelNames.remove("translation");
	}

	/**
	 * @param modelResources 
	 * 
	 */
	private void initTables(IProject project, Collection<IOfsModelResource> modelResources) throws CoreException {

		tableMap.clear();

		ITranslationManager tm = TranslationCore.getTranslationManager(project);

		for (IOfsModelResource res : modelResources) {
			try {
				List<EObject> eObjList = res.getEMFModel();
				if (eObjList.size() > 0) {
					EObject model = eObjList.get(0);
					ITranslationModelVisitor visitor = tm.getTranslationModelVisitor(model);
					if (visitor != null) {
						final ITranslationKeyTable table = getTable(project, res.getURI());
						visitor.visit(new ITranslationModelVisitorHandler() {
							public void notify(ITranslation translation) {
								table.add(translation);
							}
						});
					}
				}
			} catch (IOException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID,
						"Error while collecting translations for model " + res.getURI(), ex);
				throw new CoreException(status);
			} catch (InvalidMetamodelVersionException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID,
						"Error while collecting translations for model " + res.getURI(), ex);
				throw new CoreException(status);
			}
		}
		
	}

	/**
	 * @return
	 */
	protected final List<ITranslationKeyProvider> getTranslationKeyProviders() {
		return this.keyProviders;
	}

	/**
	 * @return
	 */
	protected final List<ITranslationGeneratorProvider> getTranslationGenProviders() {
		return this.genProviders;
	}

	/**
	 * @param project 
	 * @param uri
	 * @return
	 */
	protected ITranslationKeyTable getTable(IProject project, URI uri) {
		ITranslationKeyTable table = tableMap.get(uri);
		if (null == table) {
			table = new TranslationKeyTable(project, getTranslationKeyProviders());
			tableMap.put(uri, table);
		}
		return table;
	}

	/**
	 * @param properties
	 * @return
	 */
	protected ITranslationGenerator getTranslationGenerator(IProject project, Map<String, ?> properties) throws CoreException {

		ITranslationGenerator generator = null;
		String id = (String) properties.get("generator");

		if (StringUtils.isEmpty(id)) {
			id = "";
		}

		// Looks for a provider that accepts the object passed in parameter.
		ITranslationGeneratorProvider provider = null;
		int priority = -1;
		for (ITranslationGeneratorProvider tp : getTranslationGenProviders()) {
			if (project.hasNature(tp.getNature())) {
				if (tp.getPriority() > priority) {
					if (tp.getId().equals(id)) {
						// found one
						provider = tp;
						priority = tp.getPriority();
					}
				}
			}
		}
		
		if (provider != null) {
			generator = provider.getTranslationGenerator(project, properties);
		}

		if (null == generator) {
			String message = "Cannot find a translation generator with id [" + id + "]. Please check the extension point.";
			IStatus status = new Status(IStatus.ERROR, TranslationGenerationCore.PLUGIN_ID, -1, message, null);
			throw new CoreException(status);
		}

		return generator;
	}

}

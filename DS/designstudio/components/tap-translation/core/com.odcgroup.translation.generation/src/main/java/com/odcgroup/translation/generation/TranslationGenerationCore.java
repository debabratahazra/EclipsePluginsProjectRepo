package com.odcgroup.translation.generation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.generation.internal.TranslationGenerationRuntimeProvider;
import com.odcgroup.translation.generation.xls.IColumnProvider;
import com.odcgroup.workbench.core.AbstractActivator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.logging.LoggingConstants;

/**
 * The activator class controls the plug-in life cycle
 */
public class TranslationGenerationCore extends AbstractActivator {

	private static final Logger logger = LoggerFactory.getLogger(TranslationGenerationCore.class);
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.odcgroup.translation.generation";

	// the id of the nls code generation cartridge
	public static final String NLS_CARTRIDGE_ID = "com.odcgroup.translation.code.generator";

	// The shared instance
	private static TranslationGenerationCore plugin;
	
	private ITranslationGenerationRuntime runtime;
	
	// marker used for SLF4J logging to identify the plugin
    public static final Marker LOG_MARKER = createBundleMarker();

	private static final String COLUMNPROVIDER_EXTENSION_ID = "com.odcgroup.translation.generation.xlscolumnprovider";	
	
    private static final Marker createBundleMarker() {
    	Marker bundleMarker = MarkerFactory.getMarker(PLUGIN_ID);
    	bundleMarker.add(MarkerFactory.getMarker(LoggingConstants.IS_BUNDLE_MARKER));
    	return bundleMarker;
    }    
    
	/**
	 * The constructor
	 */
	public TranslationGenerationCore() {
		plugin = this;
	}

	/**
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		runtime = TranslationGenerationRuntimeProvider.getRuntime(context);
	}

	/**
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		runtime.release(context);
		super.stop(context);
	}

	/**
	 * Retrieves a Boolean value indicating whether tracing is enabled for the
	 * specified debug option.
	 * 
	 * @return Whether tracing is enabled for the debug option of the plug-in.
	 * @param option The debug option for which to determine trace enablement.
	 * 
	 */
	 public static boolean shouldTrace(String option) {
		boolean trace = false;
		if(getDefault().isDebugging()) {
			trace = isTraceOptionEnabled(option);
		}
		return trace;
	}			
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TranslationGenerationCore getDefault() {
		return plugin;
	}
	
	/**
	 * @param project
	 * @param translation
	 * @return ITranslationKey
	 */
	public static ITranslationKey getTranslationKey(IProject project, ITranslation translation) {
		return getDefault().runtime.getTranslationKey(project, translation );
	}
	
	/**
	 * Transforms the translations to the Platform Specific Models
	 * @param project 
	 * @param properties
	 */
	public static void generateTranslations(IProject project, Collection<IOfsModelResource> modelResources, Map<String, ?> properties) throws CoreException {
		getDefault().runtime.generate(project, modelResources, properties);
	}

    /**
     * This method checks all extension for the extension point com.odcgroup.workbench.model
     * and returns an string array of their names.
     * @return the names of all registered models
     */
    public static Iterable<IColumnProvider> getColumnProviders() {
    
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(COLUMNPROVIDER_EXTENSION_ID);
		if (point == null) return Lists.newArrayList();;
    	
		Ordering<IConfigurationElement> priorityOrdering = Ordering.natural().onResultOf(new Function<IConfigurationElement, String>() {
			@Override
			public String apply(IConfigurationElement from) {
				return from.getAttribute("priority");
			}
		});
		
		Iterable<IConfigurationElement> sortedElements = 
				ImmutableSortedSet.orderedBy(priorityOrdering).addAll(Arrays.asList(point.getConfigurationElements())).build();
		
		Iterable<IColumnProvider> providers = Iterables.transform(sortedElements, new Function<IConfigurationElement, IColumnProvider>() {
			@Override
			public IColumnProvider apply(IConfigurationElement from) {
				try {
					return (IColumnProvider) from.createExecutableExtension("class");
				} catch (CoreException e) {
					logger.warn("Failed initializing XLS column contributing class for extension '{}'", from.getName(), e);
					return null;
				}
			}
		});
		return providers;
	}

}

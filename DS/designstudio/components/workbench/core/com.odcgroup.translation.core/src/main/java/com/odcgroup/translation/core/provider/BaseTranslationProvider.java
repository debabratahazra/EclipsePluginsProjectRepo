package com.odcgroup.translation.core.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.google.common.collect.MapMaker;
import com.odcgroup.translation.core.IFilter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.translation.BaseTranslation;

/**
 * This is the base class for all implementation of the interface
 * ITranslationProvider.
 * 
 * @author atr
 */
public abstract class BaseTranslationProvider implements ITranslationProvider, IExecutableExtension {

	private String id;
	private String name;
	private String nature;
	private int priority = 2;
	private IFilter filter;
	private List<Class<?>> inputTypes;
	private Map<ITranslationKind, String> displayNames;
	
	public static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature";
	
	private Map<Object, BaseTranslation> existingTranslations = new MapMaker().weakKeys().weakValues().makeMap();
	
	private static class DefaultFilter implements IFilter {
		public final boolean select(Object toTest) {
			return true;
		}
	}

	@Override
	public final String getId() {
		return this.id;
	}

	@Override
	public final String getName() {
		return this.name;
	}

	@Override
	public final String getNature() {
		return this.nature;
	}

	@Override
	public final int getPriority() {
		return this.priority;
	}

	@Override
	public String toString() {
		return "TranslationProvider{id:" + getId() + "}";
	}
	
	@Override
	public final IFilter getFilter() {
		return filter;
	}

	@Override
	public final Class<?>[] getInputTypes() {
		return inputTypes.toArray(new Class<?>[0]);
	}
	
	@Override
	public final String getDisplayName(ITranslationKind kind) {
		String display = displayNames.get(kind);
		if (display == null) {
			display = StringUtils.capitalize(kind.name());
		}
		return display;
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {

		// Get the unique identifier
		id = config.getAttribute("id");

		// Get the name
		name = config.getAttribute("name");
		if (null == name) {
			name = "";
		}
		
		// Get the nature
		nature = config.getAttribute("natureId");
		if (null == nature) {
			nature = "";
		}

		// Gets the priority value
		String prio = config.getAttribute("priority");
		if (StringUtils.isNotBlank(prio)) {
			try {
				priority = Integer.parseInt(prio);
			} catch (NumberFormatException ex) {
				IStatus status = new Status(IStatus.ERROR, TranslationCore.PLUGIN_ID, 
						"The translation provider [" + id + "] has an invalid priority value ["+prio+"]");
				throw new CoreException(status);
			}
		}
		
		// Gets the filter
		String filterClass = config.getAttribute("filter");
		if (StringUtils.isNotBlank(filterClass)) {
			filter = (IFilter) config.createExecutableExtension("filter");
		} else {
			filter = new DefaultFilter();
		}
		
		// Get the display name for all translation kinds
		displayNames = new HashMap<ITranslationKind, String>();
		for (IConfigurationElement input : config.getChildren("kind")) {
			String kindName = input.getAttribute("name");
			String displayName = input.getAttribute("display");
			ITranslationKind kind = ITranslationKind.valueOf(kindName);
			displayNames.put(kind, displayName);
		}
		
		// Gets the input types
		inputTypes = new ArrayList<Class<?>>();
		for (IConfigurationElement input : config.getChildren("input")) {
			String typeClass = input.getAttribute("type");
			if (StringUtils.isNotBlank(typeClass)) {
				try {
					String contributorName = input.getDeclaringExtension().getContributor().getName();
					Class<?> javaClass = Platform.getBundle(contributorName).loadClass(typeClass);
					inputTypes.add(javaClass);
				} catch (ClassNotFoundException e) {
					IStatus status = new Status(IStatus.ERROR, TranslationCore.PLUGIN_ID, 
							"The translation provider [" + id + "] has an invalid input type ["+typeClass+"]");
					throw new CoreException(status);
				}
			}
		}
	}

	@Override
	public final ITranslation getTranslation(IProject project, Object obj) {
		
		if (null == project || null == obj) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
				
		return createTranslation(project, obj);
	}
	
	private BaseTranslation createTranslation(IProject project, Object element) {
		synchronized(existingTranslations) {
			BaseTranslation translation = existingTranslations.get(element);
			if (translation == null) {
				// ensure the project contains the required nature
				try {
					if (!project.hasNature(JAVA_NATURE)) {
						throw new IllegalArgumentException("Argument project has not the required nature: "+JAVA_NATURE);
					}
				} catch (CoreException ex) {
					throw new IllegalArgumentException(ex);
				}
				translation = newTranslation(project, element);
				if (translation != null) {
					existingTranslations.put(element, translation);
				}
			}
			return translation;
		}
	}
	
	/**
	 * Create the appropriate instance for the element type
	 * @param project
	 * @param element
	 * @return the appropriate instance for the element type
	 */
	protected abstract BaseTranslation newTranslation(IProject project, Object element);		
}

package com.odcgroup.translation.core.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.IFilter;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorProvider;
import com.odcgroup.translation.core.TranslationCore;

/**
 * This is the base class for all implementation of the interface
 * ITranslationProvider.
 * 
 * @author atr
 */
public abstract class BaseTranslationModelVisitorProvider implements ITranslationModelVisitorProvider, IExecutableExtension {

	private String id;
	private String name;
	private IFilter filter;
	private List<Class<?>> inputTypes;
	
	private class DefaultFilter implements IFilter {
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
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {

		// Get the unique identifier
		id = config.getAttribute("id");

		// Get the name
		name = config.getAttribute("name");
		if (null == name)
			name = "";
		
		// Gets the filter
		String filterClass = config.getAttribute("filter");
		if (StringUtils.isNotBlank(filterClass)) {
			filter = (IFilter) config.createExecutableExtension("filter");
		} else {
			filter = new DefaultFilter();
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
	public final ITranslationModelVisitor getModelVisitor(IProject project, Object object) {
		
		if (null == project || null == object) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}
		
		// ensure the project contains the required nature
		try {
			if (!project.hasNature(BaseTranslationProvider.JAVA_NATURE)) {
				throw new IllegalArgumentException("Argument project has not the required nature: "+BaseTranslationProvider.JAVA_NATURE);
			}
		} catch (CoreException ex) {
			throw new IllegalArgumentException(ex);
		}
		
		return newModelVisitor(project, object);
	}

	
	/**
	 * Create the appropriate instance for the element type
	 * @param project
	 * @param element
	 * @return the appropriate instance for the element type
	 */
	protected abstract ITranslationModelVisitor newModelVisitor(IProject project, Object element);

}

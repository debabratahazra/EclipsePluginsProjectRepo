package com.odcgroup.translation.generation.provider;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationGeneratorProvider;

/**
 * This is the base class for all implementation of the interface
 * ITranslationGeneratorProvider.
 * 
 * @author atr
 */
public abstract class BaseTranslationGeneratorProvider implements ITranslationGeneratorProvider, IExecutableExtension {

	private String id;
	private String name;
	private String nature;
	private int priority = 2;

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
		return "TranslationGeneratorProvider{id:" + getId() + "}";
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
				IStatus status = new Status(IStatus.ERROR, TranslationCore.PLUGIN_ID, "The translation generator provider [" + id
						+ "] has an invalid priority value [" + prio + "]");
				throw new CoreException(status);
			}
		}

	}
}

package com.odcgroup.translation.ui.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.ui.views.ITranslationModel;

/**
 * This the base class for all translation commands providers.
 *
 * @author atr
 */
public abstract class BaseTranslationCommandProvider implements ITranslationCommandProvider, IExecutableExtension  {

	private String id;
	private String name;
	private String nature;
	private int priority = 2;
	private List<Class<?>> inputTypes;
	
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
	public final ITranslationCommand getEditCommand(ITranslationModel model) {
		if (null == model) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		return doGetEditCommand(model);
	}
	
	@Override
	public final ITranslationCommand getRemoveCommand(ITranslationModel model) {
		if (null == model) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		return doGetRemoveCommand(model);
	}
	
	/**
	 * @param model, never null
	 * @return the edit command
	 */
	protected abstract ITranslationCommand doGetEditCommand(ITranslationModel model);
	
	/**
	 * @param model, never null
	 * @return the remove command
	 */
	protected abstract ITranslationCommand doGetRemoveCommand(ITranslationModel model);

	/**
	 * 
	 */
	public BaseTranslationCommandProvider() {
	}

}

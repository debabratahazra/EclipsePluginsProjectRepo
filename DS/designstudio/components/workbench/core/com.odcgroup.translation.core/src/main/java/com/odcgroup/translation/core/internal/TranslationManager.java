package com.odcgroup.translation.core.internal;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.translation.core.IFilter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorProvider;
import com.odcgroup.translation.core.ITranslationPreferences;
import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.internal.preferences.TranslationPreferences;

/**
 * This class implements the basic mechanism to get a specific implementation of
 * a ITranslationProvider given an object.
 * 
 * @author atr
 */
abstract class TranslationManager implements ITranslationManager {

	private static Logger logger = LoggerFactory.getLogger(TranslationManager.class);
	
	/** */
	private IProject project;

	/**
	 * 
	 * @param obj
	 * @return
	 */
	private ITranslationProvider getTranslationProvider(final Object obj) {

		if (!project.isOpen()) {
			return null;
		}
		
		logger.trace(TranslationCore.LOG_MARKER, 
				"Looking for a Translation Provider given object's class {}", 
				obj.getClass().getName());

		// Looks for a provider that accepts the object passed in parameter.
		ITranslationProvider provider = null;
		int priority = -1;
		for (ITranslationProvider tp : getTranslationProviders()) {
			try {
				if (project.hasNature(tp.getNature())) {
					if (tp.getPriority() > priority) {
						for (Class<?> clazz : tp.getInputTypes()) {
							if (clazz.isAssignableFrom(obj.getClass())) {
								IFilter filter = tp.getFilter();
								if (filter == null || filter.select(obj)) {
									// found one
									provider = tp;
									priority = tp.getPriority();
								}
							}
						}
					}
				}
			} catch (CoreException ex) {
				logger.warn("Cannot read the nature of the project", ex);
			}
		}

		// no provider for the given eObj.
		if (provider != null) {
			logger.trace(TranslationCore.LOG_MARKER, 
					"Translation Provider found: {}", 
					provider.getId());
		} else {
			logger.trace(TranslationCore.LOG_MARKER, 
					"Translation Provider not found for object's class {}"
					,obj.getClass().getName());
		}

		return provider;

	}

	/**
	 * 
	 * @param object
	 * @return
	 */
	private ITranslationModelVisitorProvider getTranslationModelVisitorProvider(final Object object) {

		if (!project.isOpen()) {
			return null;
		}
		
		logger.trace(TranslationCore.LOG_MARKER, 
				"Looking for a provider for visiting translations in model given object's class {}", 
				object.getClass().getName());

		// Looks for a provider that accepts the object passed in parameter.
		ITranslationModelVisitorProvider provider = null;
		for (ITranslationModelVisitorProvider tp : getTranslationModelVisitorProviders()) {
			for (Class<?> clazz : tp.getInputTypes()) {
				if (clazz.isAssignableFrom(object.getClass())) {
					IFilter filter = tp.getFilter();
					if (filter == null || filter.select(object)) {
						// found one
						provider = tp;
						logger.trace(TranslationCore.LOG_MARKER, 
								"Found a provider for visiting translations in model: {}", 
								provider.getId());
						return tp;
					}
				}
			}
		}

		// no provider for the given eObj.
		logger.trace(TranslationCore.LOG_MARKER, 
					"Unable to find a provider for visiting translations in model for object's class {}"
					,object.getClass().getName());

		return null;

	}
	
	
	/**
	 * @return IProject
	 */
	protected final IProject getProject() {
		return this.project;
	}

	/**
	 * @return the list of all declared Translation Providers
	 */
	protected abstract List<ITranslationProvider> getTranslationProviders();


	/**
	 * @return the list of all provider for visiting translation in models
	 */
	protected abstract List<ITranslationModelVisitorProvider> getTranslationModelVisitorProviders();
	
	
	/*
	 * Typically this method is invoked from the translation view to retrieve a
	 * specific translation provider for the current element selected within the
	 * editor.
	 */
	@Override
	public ITranslation getTranslation(final Object obj) {

		ITranslation translation = null;

		logger.trace(TranslationCore.LOG_MARKER, 
				"Looking for Translations given object {}", obj);

		ITranslationProvider provider = getTranslationProvider(obj);
		if (provider != null) {
			translation = provider.getTranslation(getProject(), obj);
		}

		if (translation != null) {
			logger.trace(TranslationCore.LOG_MARKER, "Translations supported for object {}", obj);
		} else {
			logger.trace(TranslationCore.LOG_MARKER, "Translations not supported for object {}", obj);
		}

		return translation;
	}
	
	@Override
	public ITranslationModelVisitor getTranslationModelVisitor(Object object) {
		ITranslationModelVisitor visitor = null;
		ITranslationModelVisitorProvider provider = getTranslationModelVisitorProvider(object);
		if (provider != null) {
			visitor = provider.getModelVisitor(getProject(), object);
		}
		return visitor;
	}

	@Override
	public ITranslationPreferences getPreferences() {
		return new TranslationPreferences(project);
	}

	/**
	 * Constructor
	 */
	TranslationManager(IProject project) {
		super();
		this.project = project;
	}

}

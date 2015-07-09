package com.odcgroup.translation.core;

import org.eclipse.core.resources.IProject;

/**
 * The interface ITranslationProvider provides factory method to create an
 * ITranslation implementation (domain, workflow, page)
 * 
 * @author atr
 */
public interface ITranslationProvider {

	/**
	 * @return the unique identifier of this translation provider
	 */
	String getId();

	/**
	 * @return the name of this translation provider
	 */
	String getName();

	/**
	 * @return the nature or an empty string 
	 */
	String getNature();

	/**
	 * @return the priority value in the range 0..int'max
	 */
	int getPriority();

	/**
	 * @return the filter
	 */
	IFilter getFilter();

	/**
	 * @return
	 */
	Class<?>[] getInputTypes();

	/**
	 * Returns the translation support given an object. If this object is not
	 * an instance of any of the input types then <code>null</code> is returned
	 * 
	 * @param project
	 * @param obj
	 * @return
	 */
	ITranslation getTranslation(IProject project, Object obj);
	
	/**
	 * Returns the display name of a translation kind
	 * @param kind the translation kind
	 * @return the requested display name
	 */
	String getDisplayName(ITranslationKind kind);
}

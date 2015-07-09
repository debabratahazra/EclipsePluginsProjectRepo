package com.odcgroup.translation.core;

import org.eclipse.core.resources.IProject;

/**
 * @author atr
 */
public interface ITranslationModelVisitorProvider {
	
	/**
	 * @return the unique identifier of this provider
	 */
	String getId();

	/**
	 * @return the name of this provider
	 */
	String getName();

	/**
	 * @return the filter
	 */
	IFilter getFilter();

	/**
	 * @return
	 */
	Class<?>[] getInputTypes();

	/**
	 * Returns the model visitor given an object. If this object is not
	 * an instance of any of the input types then <code>null</code> is returned
	 * 
	 * @param project
	 * @param obj
	 * @return
	 */
	ITranslationModelVisitor getModelVisitor(IProject project, Object obj);

}

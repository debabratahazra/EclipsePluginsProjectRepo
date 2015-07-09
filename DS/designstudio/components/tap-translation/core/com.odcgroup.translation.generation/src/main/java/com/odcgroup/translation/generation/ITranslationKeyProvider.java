package com.odcgroup.translation.generation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.IFilter;
import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
public interface ITranslationKeyProvider {
	
	/**
	 * @return the unique identifier of this translation provider
	 */
	String getId();

	/**
	 * @return the name of this translation provider
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
	 * @param project
	 * @param translation
	 * @return
	 */
	ITranslationKey getTranslationKey(IProject project, ITranslation translation); 

}

package com.odcgroup.translation.generation;

import java.util.Map;

import org.eclipse.core.resources.IProject;

/**
 * @author atr
 */
public interface ITranslationGeneratorProvider {
	
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
	 * @param project
	 * @param properties
	 * @return
	 */
	ITranslationGenerator getTranslationGenerator(IProject project, Map<String, ?> properties);

}

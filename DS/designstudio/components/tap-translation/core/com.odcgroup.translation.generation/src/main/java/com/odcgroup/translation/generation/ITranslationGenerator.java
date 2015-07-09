package com.odcgroup.translation.generation;

import org.eclipse.core.runtime.CoreException;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public interface ITranslationGenerator {

	/**
	 * @param properties
	 * 
	 */
	void startGeneration() throws CoreException;

	/**
	 * 
	 */
	void endGeneration() throws CoreException;

	/**
	 * @param key
	 */
	void generate(ITranslationKey key) throws CoreException;

}

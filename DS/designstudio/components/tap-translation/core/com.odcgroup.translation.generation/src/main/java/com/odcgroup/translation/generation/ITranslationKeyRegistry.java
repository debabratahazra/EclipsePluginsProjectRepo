package com.odcgroup.translation.generation;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;

/**
 * @author atr
 */
public interface ITranslationKeyRegistry {

	/**
	 * @param properties
	 */
	void generate(Map<String, ?> properties) throws CoreException;

}

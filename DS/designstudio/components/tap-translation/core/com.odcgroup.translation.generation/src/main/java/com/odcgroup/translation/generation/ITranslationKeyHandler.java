package com.odcgroup.translation.generation;

import org.eclipse.core.runtime.CoreException;


/**
 * @author atr
 */
public interface ITranslationKeyHandler {
	
	void notify(ITranslationKey key) throws CoreException;

}

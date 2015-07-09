package com.odcgroup.translation.generation;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.translation.core.ITranslation;

/**
 * @author atr
 */
public interface ITranslationKeyTable {
	
	void add(ITranslation translation);
	
	void clear();
	
	int size();

	void visit(ITranslationKeyHandler handler) throws CoreException;

}

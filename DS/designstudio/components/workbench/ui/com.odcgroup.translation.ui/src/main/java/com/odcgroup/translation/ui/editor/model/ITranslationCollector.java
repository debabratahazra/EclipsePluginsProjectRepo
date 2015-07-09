package com.odcgroup.translation.ui.editor.model;

import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.odcgroup.translation.core.ITranslation;


/**
 *
 * @author pkk
 *
 */
public interface ITranslationCollector {	
	
	
	/**
	 * @param scope
	 * @return
	 * @throws CoreException
	 */
	public List<ITranslation> collectTranslations(int scope) throws CoreException;

}

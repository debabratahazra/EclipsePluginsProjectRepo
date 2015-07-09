package com.odcgroup.domain.translation.editor;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * 
 * @author pkk
 *
 */
public class MdfTranslationCollector extends BaseTranslationCollector {
	
	/**
	 * @param ofsProject
	 */
	public MdfTranslationCollector(IOfsProject ofsProject, String[] modelNames) {
		super(ofsProject, modelNames);
	}	
	
}

package com.odcgroup.process.translation.editor;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 *
 * @author pkk
 *
 */
public class ProcessTranslationCollector extends BaseTranslationCollector {

	/**
	 * @param ofsProject
	 */
	public ProcessTranslationCollector(IOfsProject ofsProject, String[] modelNames) {
		super(ofsProject, modelNames);
	}

}

package com.odcgroup.t24.version.editor.translation;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 */
public class VersionTranslationCollector extends BaseTranslationCollector {

	/**
	 * @param ofsProject
	 */
	public VersionTranslationCollector(IOfsProject ofsProject, String[] modelNames) {
		super(ofsProject, modelNames);
	}

}

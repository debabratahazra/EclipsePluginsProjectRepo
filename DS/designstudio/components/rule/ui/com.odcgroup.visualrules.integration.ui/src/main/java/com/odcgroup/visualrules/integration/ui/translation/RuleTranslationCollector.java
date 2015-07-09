package com.odcgroup.visualrules.integration.ui.translation;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 */
public class RuleTranslationCollector extends BaseTranslationCollector {

	/**
	 * @param ofsProject
	 * @param modelNames
	 */
	public RuleTranslationCollector(IOfsProject ofsProject, String[] modelNames) {
		super(ofsProject, modelNames);
	}

}

package com.odcgroup.page.translation;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 *
 * @author pkk
 *
 */
public class PageModelTranslationCollector extends BaseTranslationCollector {

	/**
	 * @param ofsProject
	 * @param extensions 
	 */
	public PageModelTranslationCollector(IOfsProject ofsProject, String[] extensions) {
		super(ofsProject, extensions);
	}

}

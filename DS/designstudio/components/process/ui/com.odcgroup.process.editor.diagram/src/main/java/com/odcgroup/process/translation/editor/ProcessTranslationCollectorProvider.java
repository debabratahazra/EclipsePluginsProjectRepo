package com.odcgroup.process.translation.editor;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 *
 * @author pkk
 *
 */
public class ProcessTranslationCollectorProvider extends BaseTranslationCollectorProvider {

	@Override
	public ITranslationCollector getTranslationCollector(IOfsProject ofsProject) {
		return new ProcessTranslationCollector(ofsProject, getModelExtensions());
	}

	@Override
	public ITranslationOwnerSelector getTranslationOwnerSelector() {
		return new ProcessTranslationOwnerSelector();
	}

}

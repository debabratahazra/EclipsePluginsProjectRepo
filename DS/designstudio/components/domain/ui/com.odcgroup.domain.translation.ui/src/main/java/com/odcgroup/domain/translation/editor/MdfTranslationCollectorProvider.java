package com.odcgroup.domain.translation.editor;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * Translation Collector Provider for domain models
 *
 * @author pkk
 *
 */
public class MdfTranslationCollectorProvider extends BaseTranslationCollectorProvider {

	@Override
	public ITranslationCollector getTranslationCollector(IOfsProject ofsProject) {
		return new MdfTranslationCollector(ofsProject, getModelExtensions());
	}

	@Override
	public ITranslationOwnerSelector getTranslationOwnerSelector() {
		return new MdfTranslationSelector();
	}
}

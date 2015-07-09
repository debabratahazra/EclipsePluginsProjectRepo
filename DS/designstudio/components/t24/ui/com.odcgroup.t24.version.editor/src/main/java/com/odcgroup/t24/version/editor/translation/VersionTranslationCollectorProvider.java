package com.odcgroup.t24.version.editor.translation;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * @author atr
 */
public class VersionTranslationCollectorProvider extends BaseTranslationCollectorProvider {

	@Override
	public ITranslationCollector getTranslationCollector(IOfsProject ofsProject) {
		return new VersionTranslationCollector(ofsProject, getModelExtensions());
	}

	@Override
	public ITranslationOwnerSelector getTranslationOwnerSelector() {
		return new VersionTranslationOwnerSelector();
	}

}

package com.odcgroup.t24.enquiry.editor.translation;

import com.odcgroup.translation.ui.editor.model.BaseTranslationCollectorProvider;
import com.odcgroup.translation.ui.editor.model.ITranslationCollector;
import com.odcgroup.translation.ui.editor.model.ITranslationOwnerSelector;
import com.odcgroup.workbench.core.IOfsProject;

/**
 * TODO: Document me!
 *
 * @author mumesh
 *
 */
public class EnquiryTranslationCollectorProvider extends BaseTranslationCollectorProvider {

	@Override
	public ITranslationCollector getTranslationCollector(IOfsProject ofsProject) {
		// TODO Auto-generated method stub
		return new EnquiryTranslationCollector(ofsProject, getModelExtensions());
	}

	@Override
	public ITranslationOwnerSelector getTranslationOwnerSelector() {
		// TODO Auto-generated method stub
		return new EnquiryTranslationOwnerSelector();
	}

}

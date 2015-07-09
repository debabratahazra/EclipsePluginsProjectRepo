package com.odcgroup.page.transformmodel.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.model.widgets.table.IWidgetAdapter;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.workbench.core.repository.OfsResourceHelper;

/**
 * @author amc
 *
 */
public class WidgetAdapterTranslationKeyFactory {

	public ITranslationKey getKey(IWidgetAdapter adapter, Object forTranslation) {
		IProject project = OfsResourceHelper.getProject(adapter.getWidget().eResource());
		ITranslationManager translationManager = TranslationCore.getTranslationManager(project);
		ITranslation translation = translationManager.getTranslation(forTranslation);
		ITranslationKey key = TranslationGenerationCore.getTranslationKey(project, translation);
		return key;
	}
	
	public static WidgetAdapterTranslationKeyFactory getInstance() {
		return new WidgetAdapterTranslationKeyFactory();
	}
}

package com.odcgroup.menu.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.menu.model.MenuRoot;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.ITranslationModelVisitorProvider;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;

public class TranslationVisitorProvider extends	BaseTranslationModelVisitorProvider implements
		ITranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project,
			Object element) {
		return new MenuTranslationModelVisitor(project, (MenuRoot) element);
	}

}

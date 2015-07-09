package com.odcgroup.page.model.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.page.model.Model;
import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;

/**
 * TODO: Document me!
 *
 * @author atr
 *
 */
public class PageTranslationModelVisitorProvider extends BaseTranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project, Object element) {
		return new PageTranslationModelVisitor(project, (Model)element);
	}

}

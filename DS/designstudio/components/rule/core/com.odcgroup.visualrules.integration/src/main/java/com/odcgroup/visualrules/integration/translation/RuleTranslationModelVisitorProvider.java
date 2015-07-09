package com.odcgroup.visualrules.integration.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationModelVisitor;
import com.odcgroup.translation.core.provider.BaseTranslationModelVisitorProvider;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleTranslationRepo;

/**
 * @author atr
 */
public class RuleTranslationModelVisitorProvider extends BaseTranslationModelVisitorProvider {

	@Override
	protected ITranslationModelVisitor newModelVisitor(IProject project, Object element) {
		return new RuleTranslationModelVisitor(project, (RuleTranslationRepo)element);
	}

}

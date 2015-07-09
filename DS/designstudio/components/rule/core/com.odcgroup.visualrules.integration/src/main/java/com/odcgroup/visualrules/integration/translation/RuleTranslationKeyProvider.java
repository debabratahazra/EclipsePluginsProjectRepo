package com.odcgroup.visualrules.integration.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.provider.BaseTranslationKeyProvider;

/**
 * @author atr
 */
public class RuleTranslationKeyProvider extends BaseTranslationKeyProvider {

	@Override
	protected ITranslationKey newTranslationKey(IProject project, ITranslation translation) {
		return new RuleTranslationKey(translation);
	}
	
}

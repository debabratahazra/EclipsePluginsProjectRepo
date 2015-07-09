package com.odcgroup.visualrules.integration.translation;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationProvider;
import com.odcgroup.translation.core.provider.BaseTranslationProvider;
import com.odcgroup.translation.core.translation.BaseTranslation;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;

/**
 * Provides a translation adapter for visual rule.
 * @author atr
 */
public class RuleTranslationProvider extends BaseTranslationProvider implements ITranslationProvider {

	@Override
	protected BaseTranslation newTranslation(IProject project, Object object) {
		if(object instanceof RuleMessageProxy) {
			return new RuleTranslation(this, project, (RuleMessageProxy) object);
		} else {
			throw new IllegalArgumentException("RuleTranslationProvider doesn't support " + object + " input");
		}
	}
}

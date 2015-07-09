package com.odcgroup.visualrules.integration.translation;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.visualrules.integration.model.ruletranslation.RuleMessageProxy;

/**
 * @author kkr
 */
class RuleTranslationKey implements ITranslationKey {

	/** The wrapped translation */
	private ITranslation translation;
	
	private String key = null;

	@Override
	public final ITranslation getTranslation() {
		return translation;
	}
	
	@Override
	public final ITranslationKind[] getTranslationKinds() {
		return translation.getAllKinds();
	}	
	
	@Override
	public String getKey(ITranslationKind kind) {
		if(key==null) {
			RuleMessageProxy owner = (RuleMessageProxy)getTranslation().getOwner();
			key = owner.getCode();
		}
		return key;
	}
	
	@Override
	public String getMessage(ITranslationKind kind, Locale locale) throws TranslationException {
		return translation.getText(kind, locale);
	}
	
	/**
	 * @param translation
	 */
	protected RuleTranslationKey(ITranslation translation) {
		this.translation = translation;
	}

}

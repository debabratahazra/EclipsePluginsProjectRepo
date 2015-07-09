package com.odcgroup.translation.generation.provider;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * @author atr
 */
public abstract class BaseTranslationkey implements ITranslationKey {

	/** The wrapped translation */
	private ITranslation translation;
	
	/**
	 * @return
	 */
	protected abstract String getKeyPrefix();
	
	/**
	 * @param kind
	 * @return
	 */
	protected abstract String getKeySuffix(ITranslationKind kind);
	
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
		String key = getKeyPrefix();
		if (key != null) {
			key += "." + getKeySuffix(kind);
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
	protected BaseTranslationkey(ITranslation translation) {
		this.translation = translation;
	}
}

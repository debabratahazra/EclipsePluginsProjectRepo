package com.odcgroup.translation.generation;

import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;

/**
 * @author atr
 */
public interface ITranslationKey {
	
	/**
	 * @return the wrapped translation.
	 */
	ITranslation getTranslation();	
	
	/**
	 * @return the translation kinds  
	 */
	ITranslationKind[] getTranslationKinds();
	
	/**
	 * @param kind the translation kind
	 * @return the key given a translation kind
	 */
	String getKey(ITranslationKind kind);

	/**
	 * @param kind
	 * @param locale
	 * @return
	 */
	String getMessage(ITranslationKind kind, Locale locale) throws TranslationException;

}
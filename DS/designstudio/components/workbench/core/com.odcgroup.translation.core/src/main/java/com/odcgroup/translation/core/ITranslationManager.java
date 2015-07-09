package com.odcgroup.translation.core;

/**
 * -- Gets the TranslationManager 
 * ITranslationManager tm = TranslationCore.getTranslationManager();
 * 
 * @author atr
 */
public interface ITranslationManager {
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	ITranslation getTranslation(Object object);
	
	/**
	 * @param object
	 * @return
	 */
	ITranslationModelVisitor getTranslationModelVisitor(Object object);
	
	/**
	 * @return
	 */
	ITranslationPreferences getPreferences();
}

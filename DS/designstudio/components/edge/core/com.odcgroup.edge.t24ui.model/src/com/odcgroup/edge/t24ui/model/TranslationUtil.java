package com.odcgroup.edge.t24ui.model;

import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IProject;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;



/**
 *Helper Class to get the Translation related operations.  
 *
 */

public class TranslationUtil {
	/**
	 * get the selected languages on a given project
	 * @param project
	 * @return list of Locale
	 */
	public static List<Locale> getInterestLanguages(IProject project){
		ITranslationManager translationManager = TranslationCore.getTranslationManager(project);
		List<Locale> intrestedLocales = translationManager.getPreferences().getAdditionalLocales();
		return intrestedLocales;
	}
	/**
	 * get the default locale 
	 * @param project
	 * @return
	 */
	public static Locale getDefaultLanguage(IProject project){
		ITranslationManager translationManager = TranslationCore.getTranslationManager(project);
		Locale defaultLocale = translationManager.getPreferences().getDefaultLocale();
		return defaultLocale;
	}
 
}

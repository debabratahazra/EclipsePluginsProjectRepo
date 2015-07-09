package com.odcgroup.translation.ui.editor.model;

import java.util.List;
import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;

/**
 *
 * @author pkk
 *
 */
public interface ITranslationTableModel  {
	
	/**
	 * @return List<Locale>
	 */
	List<Locale> getAdditionalLocales();

	/**
	 * @return Locale
	 */
	Locale getDefaultLocale();

	/**
	 * @return
	 */
	public ITranslationTableItem[] getTranslationItems();
	
	/**
	 * @param translations
	 */
	void addTranslations(List<ITranslation> translations);
	
	/**
	 * @param items
	 */
	public void addTranslationTableItems(List<ITranslationTableItem> items);
}

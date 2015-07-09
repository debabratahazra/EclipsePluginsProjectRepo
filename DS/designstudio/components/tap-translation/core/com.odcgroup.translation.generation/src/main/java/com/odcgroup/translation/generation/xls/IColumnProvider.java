package com.odcgroup.translation.generation.xls;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * This interface is implemented by classes that want to contribute additional columns to the XLS translation file.
 * 
 * @author Kai Kreuzer
 * @since 6.0.0
 *
 */
public interface IColumnProvider {

	/**
	 * Provides the column header
	 * 
	 * @return the column header
	 */
	public String getHeader();
	
	/**
	 * Provides the cell content for a given translation key and kind
	 * 
	 * @param key the translation key
	 * @param kind the translation kind
	 * @return the cell content
	 */
	public String getContent(ITranslationKey key, ITranslationKind kind);
	
	/**
	 * Defines whether the column should be added before or after the translation columns
	 * 
	 * @return true, if it should be inserted before the translation columns
	 */
	public boolean isBeforeTranslations();
}

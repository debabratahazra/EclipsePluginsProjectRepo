package com.odcgroup.translation.ui.editor.model;

import java.util.List;
import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 *
 * @author pkk
 *
 */
public interface ITranslationTableItem  {
	
	/**
	 * @return
	 */
	String getText();

	/**
	 * @param locale
	 * @return
	 */
	String getText(Locale locale);
	
	/**
	 * @return
	 */
	ITranslationKind getTranslationKind();
	
	/**
	 * @return
	 */
	boolean isReadOnly();
	
	/**
	 * @return
	 */
	String getResourceURI();
	
	/**
	 * @return
	 */
	String getResourceFragment();
	
	
	/**
	 * @return
	 */
	String getProject();


	/**
	 * @return
	 */
	ITranslation getTranslation();
	
	/**
	 * @return
	 */
	List<Locale> getLocales();
	
	/**
	 * @param locales
	 */
	void setLocales(List<Locale> locales);
	
	/**
	 * @param translation
	 */
	void setTranslation(ITranslation translation);	

}

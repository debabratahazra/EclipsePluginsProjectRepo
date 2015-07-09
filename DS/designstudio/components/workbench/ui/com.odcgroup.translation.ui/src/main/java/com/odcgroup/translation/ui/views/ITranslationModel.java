package com.odcgroup.translation.ui.views;

import java.util.List;
import java.util.Locale;

import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;

/**
 * The interface <code>ITranslationModel</code> is the presentation model of the
 * <code>ITranslationViewer</code>. It provides all operations needed to display
 * translations of an object, change the translation, and allows the viewer to
 * listen to specific changes.
 * <p>
 * 
 * @author atr
 */
public interface ITranslationModel extends IBaseTranslationModel {

	final String TRANSLATION_TEXT_PROPERTY = "com.odcgroup.translation.text";
	final String TRANSLATION_KIND_PROPERTY = "com.odcgroup.translation.kind";
	final String TRANSLATION_LOCALE_PROPERTY = "com.odcgroup.translation.locale";
	final String TRANSLATION_INFO_PROPERTY = "com.odcgroup.translation.info";

	/**
	 * @return List<Locale>
	 */
	List<Locale> getAdditionalLocales();

	/**
	 * @return Locale
	 */
	Locale getDefaultLocale();

	/**
	 * @param kind
	 * @return
	 */
	String getDisplayName(ITranslationKind kind);

	/**
	 * @return an information message regarding the current selected message.
	 */
	String getInformation();
	
	/**
	 * Returns the information regarding the origin of the translation
	 */
	String getOrigin(Locale locale);
	

	/**
	 * @return
	 */
	ITranslationKind getSelectedKind();

	/**
	 * @return
	 */
	Locale getSelectedLocale();

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
	 * 
	 */
	ITranslationKind[] getTranslationKinds();

	/**
	 * @return
	 */
	ITranslationKind getDefaultKind();

	/**
	 * @return
	 */
	boolean isReadOnly();

	/**
	 * @param kind
	 */
	void selectKind(ITranslationKind kind);

	/**
	 * @param locale
	 */
	void selectLocale(Locale locale);

	/**
	 * Changes the information regarding the current selected message in the
	 * table.
	 * 
	 * @param newInformation
	 */
	void setInformation(String newInformation);

	/**
	 * @param locale
	 * @param newText
	 */
	void setText(Locale locale, String newText);

	/**
	 * @param newText
	 */
	void setText(String newText);

	/**
	 * @return
	 */
	ITranslation getTranslation();
		
	boolean acceptRichText();
	
	void accept(ITranslationKind[] kinds);
	
	void reject(ITranslationKind[] kinds);
	
	void setMultiLinesEditor(ITranslationKind kind, boolean value);
	
	boolean hasMultiLinesEditor(ITranslationKind kind);

}

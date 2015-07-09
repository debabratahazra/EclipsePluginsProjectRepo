package com.odcgroup.translation.core;

import java.util.Locale;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public interface ITranslationChangeEvent {
	
	/**
	 * @return the translation on which this event initially occurred.
	 */
	ITranslation getSource();
	
	/**
	 * @return the translation kind that was changed.
	 */
	ITranslationKind getKind();
	
	/**
	 * @return the locale for which the text of the message was changed.
	 */
	Locale getLocale();

	/**
	 * @return the new value of the message.
	 */
	String getNewText();

	/**
	 * @return the old value of the message
	 */
	String getOldText();
}

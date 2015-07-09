package com.odcgroup.translation.core;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public interface ITranslationChangeListener {

	/**
	 * Notifies this listener that the state of the translation has changed.
	 */
	void notifyChange(ITranslationChangeEvent event);

}

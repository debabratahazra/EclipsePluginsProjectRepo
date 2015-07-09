package com.odcgroup.translation.core;

/**
 * TODO: Document me!
 * 
 * @author atr
 * 
 */
public interface IBaseTranslation {

	/**
	 * Add a ITranslationChangeListener to the translation change for all
	 * properties. Has no effect if an identical listener is already registered.
	 * <p>
	 * Translation listener are informed about state changes that affect the
	 * rendering of the viewer that display the translation
	 * </p>
	 * If <code>listener</code> is null, no exception is thrown and no action is
	 * taken.
	 * 
	 * @param listener
	 *            The ITranslationChangeListener to be added
	 */
	void addTranslationChangeListener(ITranslationChangeListener listener);

	/**
	 * @param kind
	 * @param listener
	 */
	void addTranslationChangeListener(ITranslationKind kind, ITranslationChangeListener listener);

	/**
	 * Removes an listener to this translation. 
	 * 
	 * If <code>listener</code> is null, or was never added, no exception is
	 * thrown and no action is taken.
	 * 
	 * @param listener
	 *            The ITranslationChangeListener to be removed
	 */
	void removeTranslationChangeListener(ITranslationChangeListener listener);

	/**
	 * @param kind
	 * @param listener
	 */
	void removeTranslationChangeListener(ITranslationKind kind, ITranslationChangeListener listener);
}

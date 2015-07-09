package com.odcgroup.translation.core;

import java.util.Locale;

/**
 * This interface <code>ITranslation</code> provides operations for reading,
 * changing and deleting translations of a localizable object.
 * <p>
 * The underlying object can have translations of different kinds. For each kind
 * of translation, a message (text) can be expressed in different languages.
 * <p>
 * So, a translation is mainly characterized by the set of translation kinds
 * supported by the object, and for each message: a language and a text.
 * 
 * @author atr
 */
public interface ITranslation extends IBaseTranslation {

	/**
	 * Returns the display name of the given translation kind
	 * 
	 * @param kind
	 *            the translation kind
	 * @return the display name
	 */
	String getDisplayName(ITranslationKind kind);

	/**
	 * This methods returns an ordered array all translation kinds supported by
	 * the underlying object returned by the method {@link #getOwner()}.
	 * <p>
	 * 
	 * @return all supported translation kinds
	 */
	ITranslationKind[] getAllKinds();

	/**
	 * Checks whether any kind of translations can be changed.
	 * If this method return <code>true</code>, then the isReadonly method should also return true.
	 * <p>
	 * If for some reasons, the read-only status of the translation cannot be
	 * determined an exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @return <code>true</code> if the translation can be changed, otherwise it
	 *         returns <code>false</code>
	 * @throws TranslationException
	 * @see isProtected
	 */
	boolean isReadOnly() throws TranslationException;
	
	/**
	 * Check whether the translation is protected (in the T'A metadictionnary
	 * or in the T'A formats). 
	 * If this method return <code>true</code>, then the isReadonly method should also return true.
	 * <p>
	 * If for some reasons, the read-only status of the translation cannot be
	 * determined an exception <code>TranslationException</code> is raised.
	 * <p>
	 * @return <code>true</code> if the translation is protected, <code>false</code
	 * otherwise
	 * @throws TranslationException
	 * @see isReadOnly
	 */
	boolean isProtected() throws TranslationException;

	/**
	 * This method returns <code>true</code> if and only if the translation
	 * defined on the underlying object returned by the method
	 * {@link #getOwner()} can be inherited from another dependent object,
	 * otherwise it returns <code>false</code>.
	 * <p>
	 * 
	 * @return <code>true</code> if the translation texts can be inherited,
	 *         otherwise it returns <code>false</code>
	 * 
	 */
	boolean isInheritable();

	/**
	 * A translation is <i>inherited</i> if the text of the message (returned by
	 * the method <code>getText</code>) for a given a translation kind and a
	 * locale is owned by a dependent object and not directly by the underlying
	 * object returned by the method {@link #getOwner()}.
	 * <p>
	 * This method returns <code>true</code> if the designated translation is
	 * inherited otherwise it returns <code>false</code>.
	 * <p>
	 * This method must returns always <code>false</code> if translations are
	 * not inheritable, i.e. the method {@link #isInheritable()} returns
	 * <code>false</code>.
	 * <p>
	 * All parameters are mandatory. An <Code>IllegalArgumentException</code> is
	 * thrown if one of them is <code>null</code>
	 * <p>
	 * If for some reasons, the inherited status of the translation cannot be
	 * determined an exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind
	 * @param locale
	 *            the locale of the message
	 * 
	 * @return <code>true</code> if the designated translation is inherited,
	 *         otherwise it returns <code>false</code>.
	 * 
	 * @exception IllegalArgumentException
	 * @throws TranslationException
	 */
	boolean isInherited(ITranslationKind kind, Locale locale) throws TranslationException;

	/**
	 * Gets the inherited text of the translation given its kind and locale.
	 * <p>
	 * If there is no inherited translation defined for the designated
	 * <code>locale</code> and <code>kind</code>, an <code>null</code> returned.
	 * If either the methods {@link #isInherited(ITranslationKind, Locale)} or
	 * {@link #isInheritable()} returns <code>false</code> then this method
	 * returns <code>null</code>
	 * <p>
	 * All parameters are mandatory. An <Code>IllegalArgumentException</code> is
	 * thrown if one of them is <code>null</code>.
	 * <p>
	 * If for some reasons, the text of the translation cannot be determined an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be read
	 * @param locale
	 *            the locale
	 * 
	 * @return the inherited text of the translation or <code>null</code>
	 * 
	 * @exception IllegalArgumentException
	 * @throws TranslationException
	 */
	String getInheritedText(ITranslationKind kind, Locale locale) throws TranslationException;

	/**
	 * Gets the text of the translation given its kind and locale.
	 * <p>
	 * If there is no translation defined for the designated <code>locale</code>
	 * and <code>kind</code>, an <code>null</code> returned.
	 * <p>
	 * The inherited message is always returned whenever the method
	 * {@link #isInherited(ITranslationKind, Locale)} return <code>true</code>
	 * returns <code>true</code>.
	 * <p>
	 * All parameters are mandatory. An <Code>IllegalArgumentException</code> is
	 * thrown if one of them is <code>null</code>.
	 * <p>
	 * If for some reasons, the text of the translation cannot be determined an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be read
	 * @param locale
	 *            the locale
	 * 
	 * @return the text of the translation
	 * 
	 * @exception IllegalArgumentException
	 * @throws TranslationException
	 */
	String getText(ITranslationKind kind, Locale locale) throws TranslationException;

	/**
	 * This method changes the text of a message identified by its kind of
	 * translation and a locale. It returns always the old value of the message
	 * or <code>null</code> if it is not yet defined. The <code>newText</code>
	 * passed as a parameter cannot be <code>null</code> (in order to delete an
	 * existing translation you have to call the method
	 * {@link #delete(ITranslationKind, Locale)}.
	 * <p>
	 * All parameters are mandatory. An <Code>IllegalArgumentException</code> is
	 * thrown if one of them is <code>null</code>.
	 * <p>
	 * If for some reasons, the text of the translation cannot changed an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be updated
	 * @param locale
	 *            the locale
	 * @param newText
	 *            the new value of the message
	 * 
	 * @return the old text of the designated message, or <code>null</code> if
	 *         the text of the message is not yet defined.
	 * 
	 * @exception IllegalArgumentException
	 * @throws TranslationException
	 */
	String setText(ITranslationKind kind, Locale locale, String newText) throws TranslationException;
	
	/**
	 * This method returns always the underlying 'localizable' object which was
	 * passed to the method {@link ITranslationManager#getTranslation(Object)}
	 * to retrieve its translations.
	 * 
	 * @returns the underlying localizable object.
	 */
	Object getOwner();

	/**
	 * Removes the designated translation from the underlying object. It returns
	 * also the old value of the message or <code>null</code> if it was not yet
	 * defined.
	 * <p>
	 * After this method has been called, the method
	 * {@link #getText(ITranslationKind, Locale)} will returns the inherited
	 * message if the method {@link #isInheritable()} return
	 * <code>true</false>, otherwise it will returns <code>null</code>.
	 * <p>
	 * All parameters are mandatory. An <Code>IllegalArgumentException</code> is
	 * thrown if one of them is <code>null</code>.
	 * <p>
	 * If for some reasons, the text of the translation cannot be deleted an
	 * exception <code>TranslationException</code> is raised.
	 * <p>
	 * 
	 * @param kind
	 *            the translation kind to be deleted
	 * @param locale
	 *            the locale
	 * 
	 * @returns the text of the deleted message;
	 * 
	 * @exception IllegalArgumentException
	 * @throws TranslationException
	 */
	String delete(ITranslationKind kind, Locale locale) throws TranslationException;
	
	
	/**
	 * Returns <code>"true"</code> if there is at least one translation defined for any 
	 * locale given a translation kind, otherwise it returns <code>"false"</code>
	 * 
	 * @param kind
	 * @return
	 */
	boolean messagesFound(ITranslationKind kind);
	
	/**
	 * Returns <code>"true"</code> if there is at least one translation defined for any 
	 * locale given a translation kind, otherwise it returns <code>"false"</code>. If the 
	 * parameter <code>richtext</code> is false, then the call is equilent to a call
	 * to the method <code>messagesFound(kind)</code>
	 * 
	 * @param kind
	 * @param richtext 
	 * @return <code>"true"</code> if at least one rich text translation exists. 
	 */
	boolean messagesFound(ITranslationKind kind, boolean richtext);
	
	
	/**
	 * Returns <code>"true"</code> if the underlying object accept rich text translation 
	 * for a given a translation kind, otherwise it returns <code>"false"</code>
	 */
	boolean acceptRichText(ITranslationKind kind);
	

}

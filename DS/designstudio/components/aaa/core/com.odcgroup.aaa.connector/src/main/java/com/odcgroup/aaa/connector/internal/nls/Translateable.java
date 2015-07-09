package com.odcgroup.aaa.connector.internal.nls;

import java.util.Set;


/**
 * Translateable, something which can return it's name in a given language. 
 *
 * @author Michael Vorburger
 */
public interface Translateable {

    /**
     * Return object name in a certain language.
     * 
     * @param lang the Language
     * @return translated name of the object in that language (never null)
     * @throws IllegalArgumentException if there is no translation in this language
     */
    String getTranslatedName(Language lang) throws IllegalArgumentException;

    /**
     * Check if this object has a name in a certain language.
     * 
     * @param lang the Language
     * @return true if it has a translation in this language (so {@link #getTranslatedName(Language)} is valid), false if not (so getTranslatedName would throw an IllegalArgumentException) 
     */
    boolean hasTranslatedName(Language lang);

    /**
     * Return all languages for which this object has translations defined.
     * 
     * @return Set of all Languages that this object is translated into
     */
    Set<Language> getTranslatedLanguages();
}

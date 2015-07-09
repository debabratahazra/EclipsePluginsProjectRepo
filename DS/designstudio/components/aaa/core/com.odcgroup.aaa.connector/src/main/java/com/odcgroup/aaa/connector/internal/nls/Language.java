package com.odcgroup.aaa.connector.internal.nls;


/**
 * Language.
 *
 * @author Michael Vorburger
 */
public interface Language /* extends Translateable */ {

    /**
     * Unique code of the language (as found in T'A, not an ISO code, presumably).
     * @return E.g. "en", "fr" or "de".
     */
    String getCode();

    /**
     * The name of the language (in English).
     * @return E.g. "English" or "Esperanto".
     */
    String getName();
}

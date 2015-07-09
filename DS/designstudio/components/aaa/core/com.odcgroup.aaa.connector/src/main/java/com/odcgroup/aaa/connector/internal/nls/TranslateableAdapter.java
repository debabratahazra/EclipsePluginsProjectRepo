package com.odcgroup.aaa.connector.internal.nls;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


/**
 * Translateable Adapter, an abstract helper superclass for classes who implement the 'Translateable' interface.
 *  
 * @see Translateable
 * @author Michael Vorburger
 */
@MappedSuperclass
public abstract class TranslateableAdapter implements Translateable {

    @Transient // @see LabelsDAO
    private Map<Language, String> translations;

    /* (non-Javadoc)
     * @see com.odcgroup.tangij.nls.Translateable#getTranslatedName(com.odcgroup.tangij.nls.Language)
     */
    public String getTranslatedName(Language lang) {
        if (!hasTranslatedName(lang))  {
            throw new IllegalArgumentException("This Translateable object (" + this.toString() + ") has no translation in " + lang.getName());
        }
        return translations.get(lang);
    }

    /* (non-Javadoc)
     * @see com.odcgroup.tangij.nls.Translateable#hasTranslatedName(com.odcgroup.tangij.nls.Language)
     */
    public boolean hasTranslatedName(Language lang) {
        if (translations != null) {
            return translations.containsKey(lang);            
        }
        else  {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * @see com.odcgroup.tangij.nls.Translateable#getTranslatedLanguages()
     */
    @SuppressWarnings("unchecked")
    public Set<Language> getTranslatedLanguages() {
        if (translations != null) {
            return translations.keySet();
        } else {
            return Collections.EMPTY_SET;
        }
    }
    
    public void addTranslatedLabel(Language language, String name, int size) {
        if (translations == null) {
            translations = new HashMap<Language, String>(size);
        }
        if (language == null) {
            throw new IllegalArgumentException("language == null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name == null");
        }
        translations.put(language, name);
    }
}

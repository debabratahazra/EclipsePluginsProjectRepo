package com.temenos.t24.tools.eclipse.basic.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

/**
 * Class used to initialize default preference values.
 */
public class T24PreferenceInitializer extends AbstractPreferenceInitializer {
    /**
     * constructor
     */
    public T24PreferenceInitializer(){
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeDefaultPreferences() {
        /* No preference initialisation at the moment.
         * Initialisation happens when the plug-in is loaded for the first time.
         */ 
        
    }
    
}
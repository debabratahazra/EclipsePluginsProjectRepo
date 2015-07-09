package com.odcgroup.page.model.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.odcgroup.page.model.corporate.CorporateDesignUtils;
import com.odcgroup.page.model.corporate.CorporateImagesUtils;

/**
 * @author atr
 *
 */
public class PageModelPreferenceInitializer extends AbstractPreferenceInitializer {
	
	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		CorporateDesignUtils.installDefaults();
		CorporateImagesUtils.initializeDefaultScope();
	}

	/**
	 * 
	 */
	public PageModelPreferenceInitializer() {
	}

}

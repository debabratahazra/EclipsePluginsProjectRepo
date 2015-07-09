package com.odcgroup.page.model.corporate.internal;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.page.model.corporate.CorporateDesign;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * This is the base class for all CorporateDesign implementations
 *
 * @author atr
 * @since DS 1.40.0
 */
abstract class AbstractCorporateDesign implements CorporateDesign {

	/**
	 * 
	 */
	private ProjectPreferences prefStore;
	
	/**
	 * @param preferences
	 */
	private void setPropertyStore(ProjectPreferences preferences) {
		this.prefStore = preferences;
	}

	/**
	 * @param preferences
	 */
	protected AbstractCorporateDesign(ProjectPreferences preferences) {
		setPropertyStore(preferences);
	}
	
	/**
	 * @return PropertyStore
	 */
	public final ProjectPreferences getPropertyStore() {
		return this.prefStore;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.corporate.CorporateDesign#addPreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		getPropertyStore().addPreferenceChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.model.corporate.CorporateDesign#removePreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void removePreferenceChangeListener(IPreferenceChangeListener listener) {
		getPropertyStore().removePreferenceChangeListener(listener);
	}	
	
	/**
	 * Dispose the corporate design
	 */
	abstract void dispose();	

}

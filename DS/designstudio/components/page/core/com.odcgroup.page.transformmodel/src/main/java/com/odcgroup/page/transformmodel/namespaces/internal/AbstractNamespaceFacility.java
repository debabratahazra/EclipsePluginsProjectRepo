package com.odcgroup.page.transformmodel.namespaces.internal;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.page.transformmodel.namespaces.NamespaceFacility;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * This is the base class for all NamespaceFacility implementations
 *
 * @author atr
 * @since DS 1.40.0
 */
abstract class AbstractNamespaceFacility implements NamespaceFacility {

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
	protected AbstractNamespaceFacility(ProjectPreferences preferences) {
		setPropertyStore(preferences);
	}
	
	/**
	 * @see com.odcgroup.page.transformmodel.namespaces.NamespaceFacility#getPropertyStore()
	 */
	public final ProjectPreferences getPropertyStore() {
		return this.prefStore;
	}
	
	/* (non-Javadoc)
	 * @see com.odcgroup.page.transformmodel.namespaces.NamespaceFacility#addPreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		getPropertyStore().addPreferenceChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.page.transformmodel.namespaces.NamespaceFacility#removePreferenceChangeListener(org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener)
	 */
	public void removePreferenceChangeListener(IPreferenceChangeListener listener) {
		getPropertyStore().removePreferenceChangeListener(listener);
	}	
	
	/**
	 * Dispose allocated resources
	 */
	abstract void dispose();	
	
	/**
	 * Sets the default values of the PropertyStore
	 */
	abstract void setDefaultValues();

}

package com.odcgroup.page.metamodel.display.internal;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.osgi.framework.Bundle;

import com.odcgroup.page.metamodel.display.DisplayFormat;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * This is the base class for all DisplayFormat implementations
 *
 * @author atr
 * @since DS 1.40.0
 */
abstract class AbstractDisplayFormat implements DisplayFormat {

	
	/** */
	private Bundle bundle;
	
	/**
	 * 
	 */
	private ProjectPreferences prefStore;
	
	/**
	 * @return Bundle
	 */
	protected final Bundle getBundle() {
		return this.bundle;
	}

	/**
	 * @param projectPreferences
	 */
	private void setPreferenceStore(ProjectPreferences projectPreferences) {
		this.prefStore = projectPreferences;
	}

	/**
	 * @param bundle
	 * @param projectPreferences
	 */
	protected AbstractDisplayFormat(Bundle bundle, ProjectPreferences projectPreferences) {
		this.bundle = bundle;
		setPreferenceStore(projectPreferences);
	}
	
	/**
	 * @return IEclipsePreferences
	 */
	public final ProjectPreferences getPreferenceStore() {
		return this.prefStore;
	}
	
	/**
	 * @param listener
	 */
	public void addPreferenceChangeListener(IPreferenceChangeListener listener) {
		getPreferenceStore().addPreferenceChangeListener(listener);
	}

	/**
	 * @param listener
	 */
	public void removePreferenceChangeListener(IPreferenceChangeListener listener) {
		getPreferenceStore().removePreferenceChangeListener(listener);
	}	
	
	/**
	 * Disposes 
	 */
	abstract void dispose();	
	
}

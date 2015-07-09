package com.odcgroup.page.metamodel.display;

import java.util.List;

import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;

import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * DisplayFormat.
 * 
 * @author atr
 * @since DS 1.40.0
 */
public interface DisplayFormat {
	
	/**
	 * @param listener
	 */
	void addPreferenceChangeListener(IPreferenceChangeListener listener);

	/**
	 * @param listener
	 */
	void removePreferenceChangeListener(IPreferenceChangeListener listener);	
	
	/**
	 * @return IPreferenceStore
	 */
	ProjectPreferences getPreferenceStore();
	
	/**
	 * Sets the data formats
	 * @param formats a list of data formats
	 */
	void setDataFormats(List<String> formats);
	
	/**
	 * @return a list of data formats
	 */
	List<String> getDataFormats();

	/**
	 * @return a list of default data formats
	 */
	List<String> getDefaultDataFormats();

}

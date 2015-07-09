package com.odcgroup.workbench.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.action.AbstractScopeToggleAction;

/**
 * OFS Workbench Preference Initializer
 *
 */
public class WorkbenchPreferenceInitializer extends AbstractPreferenceInitializer {

	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		// by default, display models from all scopes
		store.setDefault(AbstractScopeToggleAction.PREFERENCE_KEY, 0xFFFF);
		
	}
	
	/**
	 * @return
	 */
	protected IPreferenceStore getPreferenceStore() {
		return OfsUICore.getDefault().getPreferenceStore();
	}

}

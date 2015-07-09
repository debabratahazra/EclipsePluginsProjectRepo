package com.odcgroup.ocs.support.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.ocs.support.ui.OCSSupportUICore;

public class OCSRuntimePreferenceInitializer extends
        AbstractPreferenceInitializer {

    public void initializeDefaultPreferences() {
        IPreferenceStore store = OCSSupportUICore.getDefault().getPreferenceStore();
        store.setDefault(OCSRuntimePreference.INSTALL_DIR, OCSSupportUICore.getDefault().getString("OCSRuntimePreferenceInitializer.installDir.default")); //$NON-NLS-1$

        store.setDefault(OCSRuntimePreference.EAR_DIR,
        		OCSSupportUICore.getDefault().getString("OCSRuntimePreferenceInitializer.earDir.default")); //$NON-NLS-1$
    }

}

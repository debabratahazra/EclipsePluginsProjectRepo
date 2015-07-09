package com.odcgroup.ocs.support.ui.installer;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;


public class M2EclipsePreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		if (OcsBinariesExtractionUIFacade.instance().isMavenPrefAutomaticallyUpdated()) {
	        OcsBinariesExtractionUIFacade.instance().updateSettingsXmlAndM2EclipseConfiguration();
		}
	}

}

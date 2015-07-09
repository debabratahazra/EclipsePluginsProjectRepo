package com.odcgroup.t24.maven.ui.initializer;

import java.io.File;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.odcgroup.t24.maven.ui.T24MavenHelper;
import com.odcgroup.workbench.ui.maven.M2EclipseIntegrationFacade;

public class M2EclipsePreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		updateSettingsXmlAndM2EclipseConfiguration();
	}
	
	/**
	 * Update the m2eclipse configuration to make the extracted binaries available
	 */
	public void updateSettingsXmlAndM2EclipseConfiguration() {
		// Update the settings.xml file used m2eclipse (if required)
		File t24BinariesFolder = T24MavenHelper.INSTANCE.getT24BinariesFolder();
		if (t24BinariesFolder != null) {
			M2EclipseIntegrationFacade.instance().updateSettingsXml(t24BinariesFolder.getAbsolutePath(), false);
		}

		// Update the user settings of m2eclipse to reference the
		// settings.xml updated (if required)
		M2EclipseIntegrationFacade.instance().updateUserSettingsFileProperty(true);
	}

}

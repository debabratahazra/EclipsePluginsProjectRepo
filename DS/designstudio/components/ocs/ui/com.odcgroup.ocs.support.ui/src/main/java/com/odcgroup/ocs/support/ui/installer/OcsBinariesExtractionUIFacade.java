package com.odcgroup.ocs.support.ui.installer;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.workbench.ui.maven.M2EclipseIntegrationFacade;

/**
 * Contains the code to extract and configure m2eclipse integration to
 * work with the extracted binaries of OCS.
 * @author yan
 */
public class OcsBinariesExtractionUIFacade {

	private static Logger logger = LoggerFactory.getLogger(OcsBinariesExtractionUIFacade.class);

	/** The m2eclipse integration is working offline */
	protected static final boolean WORK_OFFLINE = true;

	/** Singleton */
	protected static OcsBinariesExtractionUIFacade INSTANCE;
	
	public static OcsBinariesExtractionUIFacade instance() {
		if (INSTANCE == null) {
			INSTANCE = new OcsBinariesExtractionUIFacade();
		}
		return INSTANCE;
	}

	/**
	 * Clean up old extractions.
	 */
	public void cleanUpOldExtraction() {
		File ocsBinariesFolder = OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder();
		if (ocsBinariesFolder!=null && ocsBinariesFolder.exists()) {
			File currentRepo = OcsBinariesExtractionFacade.instance().getOcsBinariesExtractFolder();
			String deleteFailure = null;
			for (File repo : OcsBinariesExtractionFacade.instance().getOcsBinariesExtractions()) {
				if (!repo.equals(currentRepo)) {
					try {
						FileUtils.deleteDirectory(repo);
					} catch (IOException e) {
						if (deleteFailure != null) {
							deleteFailure += ", ";
						} else {
							deleteFailure = "";
						}
						deleteFailure += repo.getName();
					}
				}
			}
			if (deleteFailure != null) {
				String title = "Previous Triple'A Plus binaries removal problem";
				String message = "Unable to remove the following outdated ocs extraction: " +
					deleteFailure + "\n" +
					"(Located in " + ocsBinariesFolder + ")";
				logger.warn(title + ": " + message);
			}
		}
	}

	/**
	 * Update the m2eclipse configuration to make the extracted binaries available
	 * @return skipSettingsReload allows to skip the reload of the settings.xml by m2eclipse.
	 * This is used to allow not reloading the settings xml during the startup sequence.
	 */
	public void updateSettingsXmlAndM2EclipseConfiguration() {
		// Update the settings.xml file used m2eclipse (if required)
		File ocsBinariesFolder = OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder();
		if (ocsBinariesFolder != null) {
			M2EclipseIntegrationFacade.instance().updateSettingsXml(ocsBinariesFolder.getAbsolutePath(), WORK_OFFLINE);
		}

		// Update the user settings of m2eclipse to reference the
		// settings.xml updated (if required)
		M2EclipseIntegrationFacade.instance().updateUserSettingsFileProperty(isMavenPrefAutomaticallyUpdated());
	}

	/**
	 * Update the m2eclipse configuration to make the extracted binaries available
	 * @return
	 * @return skipSettingsReload allows to skip the reload of the settings.xml by m2eclipse.
	 * This is used to allow not reloading the settings xml during the startup sequence.
	 */
	public boolean updateM2EclipseConfiguration() {
		// Update the user settings of m2eclipse to reference the
		// settings.xml updated (if required)
		boolean updateUserSettingsFileProperty = M2EclipseIntegrationFacade.instance().updateUserSettingsFileProperty(true);
		M2EclipseIntegrationFacade.instance().reloadSettingsXml();
		return updateUserSettingsFileProperty;
	}

	/**
	 * @return <code>true</code> if the maven preferences should be automatically
	 * updated, <code>false</code> otherwise.
	 */
	public boolean isMavenPrefAutomaticallyUpdated() {
		return OCSPluginActivator.getDefault().getProjectPreferences().getBoolean(OCSRuntimePreference.AUTO_UPDATE_MAVEN, false);
	}

}

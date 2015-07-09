package com.odcgroup.workbench.core.internal.repository.maven.helper;

import java.io.File;
import java.io.IOException;

import org.apache.maven.settings.Settings;

import de.visualrules.artifact.maven.util.ISettingsCustomizer;

public class TestMavenSettingsCustomizer implements ISettingsCustomizer {

	@Override
	public void customize(Settings settings) {
		try {
			// we define a temporary folder for the local Maven repo, just to be sure
			// that it does not contain anything yet, which might impact our tests in
			// an unforseen manner.
			File tempLocalRepo = File.createTempFile("localrepo", null);
			tempLocalRepo.delete();
			tempLocalRepo.mkdir();
			tempLocalRepo.deleteOnExit();
			settings.setLocalRepository(tempLocalRepo.getAbsolutePath());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

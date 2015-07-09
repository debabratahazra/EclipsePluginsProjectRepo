package com.odcgroup.t24.maven.ui;

import java.io.File;

import org.eclipse.core.runtime.Platform;

public class T24MavenHelper {

	private static final String T24_BINARIES_FOLDER = "t24-binaries";

	public static T24MavenHelper INSTANCE = new T24MavenHelper();
	
	public File getT24BinariesFolder() {
		return new File(Platform.getInstallLocation().getURL().getPath() + T24_BINARIES_FOLDER);
	}

}

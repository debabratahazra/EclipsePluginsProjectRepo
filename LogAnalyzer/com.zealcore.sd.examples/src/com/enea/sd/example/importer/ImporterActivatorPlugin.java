package com.enea.sd.example.importer;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Plugin;

public class ImporterActivatorPlugin extends Plugin {

	private static ImporterActivatorPlugin instance;

	public ImporterActivatorPlugin() {
		super();
		instance = this;
	}

	public static ImporterActivatorPlugin getInstance() {
		return instance;
	}

	public static String getBundlePath() {
		// First, fetch the URL of the plug-in.
		URL pluginURL = getInstance().getBundle().getEntry("/");
		// This URL starts with the pseudo protocol "plugin:"
		// Therefore resolve this URL into a real URL
		try {
			URL resolvedURL = FileLocator.resolve(pluginURL);
			// Extract the path information
			return resolvedURL.getPath();
		} catch (IOException e) {

		}
		return null;

	}
}

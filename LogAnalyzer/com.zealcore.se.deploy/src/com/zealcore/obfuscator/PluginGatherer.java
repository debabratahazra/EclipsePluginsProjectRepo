/*
 * 
 */
package com.zealcore.obfuscator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PluginGatherer {

	private static final String JAR_SUFFIX = ".jar";

	public static void main(final String[] args) {
		PluginGatherer gatherer = new PluginGatherer();
		try {
			gatherer.gatherPlugins("C:/deploy/systemexplorer/internal");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gathers plugins from a directory and returns them as list of Bundles.
	 * 
	 * @param dirName
	 *            the directory name to look for bundles
	 * 
	 * @return the list of bundles
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public List<Bundle> gatherPlugins(final String dirName)
			throws FileNotFoundException {
		final ArrayList<Bundle> bundles = new ArrayList<Bundle>();
		File dir = new File(dirName);

		if (!dir.exists()) {
			throw new FileNotFoundException(dirName);
		}

		if (!dir.isDirectory()) {
			throw new IllegalArgumentException(dirName + " is not a directory");
		}

		PluginReader reader = new PluginReader();

		for (File file : dir.listFiles()) {
			System.out.println("Checking " + file
					+ (file.isDirectory() ? " > D" : " > F"));
			if (isPlugin(file)) {
				if (file.getName().endsWith(JAR_SUFFIX)) {
					bundles.add(reader.readJarPlugin(file.getAbsolutePath()));
				} else {
					bundles.add(reader.readDirectoryPlugin(file
							.getAbsolutePath()));
				}
			}
		}

		for (Bundle bundle : bundles) {
			System.out.println(bundle);
		}
		return bundles;
	}

	/**
	 * Checks if is plugin. This is fast check, it will return true if the file
	 * is a file and ends with .jar. If the file is a directory it will return
	 * true if there is a META-INF/MANIFEST.MF file in the directory
	 * 
	 * @param file
	 *            the file or dir to test
	 * 
	 * @return true, if file is plugin
	 */
	public boolean isPlugin(final File file) {

		if (file.isFile()) {
			return file.getName().endsWith(JAR_SUFFIX);
		}
		if (file.isDirectory()) {
			String absolutePath = file.getAbsolutePath();
			String mfName = absolutePath + "/META-INF/MANIFEST.MF";
			File test = new File(mfName);
			return test.exists();
		}
		return false;
	}

	public List<Bundle> gatherPlugins(final String dir, final boolean recursive) {
		return new ArrayList<Bundle>();
	}
}

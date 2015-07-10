/*
 * 
 */
package com.zealcore.obfuscator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

public class PluginReader {

	private static final String COMMA = ",";

	static final String EXPORT_PACKAGE = "Export-package";

	static final String REQUIRE_BUNDLE = "Require-Bundle";

	static final String BUNDLE_CLASS_PATH = "Bundle-ClassPath";

	public static void main(final String[] args) {
		PluginReader reader = new PluginReader();
		if (args.length < 1) {
			System.out.println("Specify file");
			return;
		}
		reader.readJarPlugin(args[0]);
	}

	Bundle readDirectoryPlugin(final String path) {

		try {

			Bundle bundle = new Bundle();
			final String manifestPath = path + "/META-INF/MANIFEST.MF";
			File manifestFile = new File(manifestPath);
			if (!manifestFile.exists()) {
				throw new FileNotFoundException(manifestPath + "  not found");
			}

			Manifest manifest = new Manifest(new FileInputStream(manifestFile));

			for (Entry<Object, Object> entry : manifest.getMainAttributes()
					.entrySet()) {

				Attributes.Name name = (Name) entry.getKey();
				String value = (String) entry.getValue();

				if (name.toString().equalsIgnoreCase(BUNDLE_CLASS_PATH)) {
					readClassPath(bundle, value);
				} else if (name.toString().equalsIgnoreCase(REQUIRE_BUNDLE)) {
					readRequiredBundles(bundle, value);
				} else if (name.toString().equalsIgnoreCase(EXPORT_PACKAGE)) {
					readExportedPackages(bundle, value);
				}
			}
			return bundle;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	Bundle readJarPlugin(final String path) {
		try {

			Bundle bundle = new Bundle();
			File f = new File(path);
			if (!f.exists()) {
				throw new FileNotFoundException("File not found");
			}
			JarFile jarfile = new JarFile(path);
			Manifest manifest = jarfile.getManifest();

			for (Entry<Object, Object> entry : manifest.getMainAttributes()
					.entrySet()) {

				Attributes.Name name = (Name) entry.getKey();
				String value = (String) entry.getValue();

				if (name.toString().equalsIgnoreCase(BUNDLE_CLASS_PATH)) {
					readClassPath(bundle, value);
				} else if (name.toString().equalsIgnoreCase(REQUIRE_BUNDLE)) {
					readRequiredBundles(bundle, value);
				} else if (name.toString().equalsIgnoreCase(EXPORT_PACKAGE)) {
					readExportedPackages(bundle, value);
				}
			}
			return bundle;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Reads exported packages.
	 * 
	 * @param value
	 *            the comma concatinated string value
	 * @param bundle
	 *            the bundle
	 */
	private void readExportedPackages(final Bundle bundle, final String value) {
		for (String string : value.split(COMMA)) {
			bundle.getExportedPackages().add(ExportedPackage.valueOf(string));
		}

	}

	/**
	 * Read required bundles.
	 * 
	 * @param value
	 *            the comma concatinated string value
	 * @param bundle
	 *            the bundle
	 */
	private void readRequiredBundles(final Bundle bundle, final String value) {
		for (String string : value.split(COMMA)) {
			bundle.getRequiredBundles().add(RequiredBundle.valueOf(string));
		}

	}

	/**
	 * Reads class path libraries.
	 * 
	 * @param value
	 *            the comma concatinated string value
	 * @param bundle
	 *            the bundle
	 */
	private void readClassPath(final Bundle bundle, final String value) {
		for (String string : value.split(COMMA)) {
			bundle.getLibraries().add(BundleLibrary.valueOf(string));
		}
	}

	static class Dependency {
		private String name;

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return getName();
		}

		public void setName(final String name) {
			this.name = name;
		}
	}

	static class RequiredBundle extends Dependency {

		static RequiredBundle valueOf(final String name) {
			final RequiredBundle instance = new RequiredBundle();
			instance.setName(name);
			return instance;
		}
	}

	static class BundleLibrary extends Dependency {
		static BundleLibrary valueOf(final String name) {
			final BundleLibrary instance = new BundleLibrary();
			instance.setName(name);
			return instance;
		}
	}

	static class ExportedPackage {
		private String name;

		public String getName() {
			return name;
		}

		static ExportedPackage valueOf(final String name) {
			ExportedPackage pkg = new ExportedPackage();
			pkg.name = name;
			return pkg;
		}
	}
}

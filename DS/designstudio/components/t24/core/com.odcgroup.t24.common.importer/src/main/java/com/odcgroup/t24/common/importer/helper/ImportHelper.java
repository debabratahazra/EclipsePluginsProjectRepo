package com.odcgroup.t24.common.importer.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.odcgroup.workbench.core.helper.IntrospectionConfigurationHelper;

/**
 * This file is used to access the fix for import issues in T24 xml files.
 * 
 * @author mumesh
 * 
 */
public class ImportHelper {

	private static String INTROSPECTION_FILE = "T24Importfixes.properties";

	private static String APPLICATION_IMPORT_FIX = "fixapplicationimport";

	public static File getIntrospectionFile() {
		String configurationFolderPath = IntrospectionConfigurationHelper.getConfigurationFolderPath();
		File importFixFile = new File(configurationFolderPath.concat(File.separator).concat(INTROSPECTION_FILE));
		if (importFixFile.exists()) {
			return importFixFile;
		}
		return null;
	}

	private static String getProperty(Properties properties, String key) {
		String value = properties.getProperty(key.toLowerCase());
		if (value != null) {
			return StringUtils.trimToEmpty(value);
		}
		value = properties.getProperty(key.toUpperCase());
		if (value != null) {
			return StringUtils.trimToEmpty(properties.getProperty(key.toUpperCase()));
		}
		return StringUtils.trimToEmpty(properties.getProperty(key));
	}

	public static String getApplicationFix(Properties properties) {
		return getProperty(properties, APPLICATION_IMPORT_FIX);
	}

	/**
	 * @param file
	 * @return
	 */
	public static Properties readPropertiesFile(File file) throws IOException {
		FileInputStream finStream = null;
		Properties properties = null;
		try {
			finStream = new FileInputStream(file);
			properties = new Properties();
			properties.load(finStream);
		} finally {
			IOUtils.closeQuietly(finStream);
		}
		return properties;
	}

}

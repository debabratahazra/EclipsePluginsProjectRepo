package com.temenos.t24.tools.eclipse.views.graphical.componentInteraction.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.google.common.io.Closeables;

/**
 * Helper class for reading the properties file
 * 
 * @author sbharathraja
 * 
 */
class ComponentInteractionPropertiesFileReader {

	/** instance of class {@link ComponentInteractionPropertiesFileReader} */
	private static ComponentInteractionPropertiesFileReader fileReader = new ComponentInteractionPropertiesFileReader();
	private Properties properties = null;

	/**
	 * get the instance
	 * 
	 * @return instance of class {@link ComponentInteractionPropertiesFileReader}
	 */
	protected static ComponentInteractionPropertiesFileReader getInstance() {
		return fileReader;
	}

	private ComponentInteractionPropertiesFileReader() {
		// block the instance creation
	}

	/**
	 * get the values equivalent to given key
	 * 
	 * @param key
	 * @return values equivalent to given key
	 */
	protected String getValues(String key) {
		if (properties != null)
			return properties.getProperty(key);
		else
			return "";
	}

	/**
	 * read the property file as instance of class {@link Properties}
	 * 
	 * @param propertyFile
	 *            - .properties file to be read
	 * @return instance of class {@link Properties}
	 */
	protected Properties getPropertyFileAsInstance(File propertyFile) {
		if (propertyFile.exists()) {
		    FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(propertyFile);
				properties = new Properties();
				properties.load(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
	            Closeables.closeQuietly(inputStream);
			}
		}
		return properties;
	}

}

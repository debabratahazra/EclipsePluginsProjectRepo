package com.odcgroup.ds.t24.packager.data;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

public class DataHelper {

	private static final String DATA_FILE_EXTENTION = ".d";
	private static final String DATA_FILE_SEPARATOR = "!";
	
	public static String getFilename(String name) throws InvalidDataFilenameException {
		if (!isDataFilename(name)) {
			throw new InvalidDataFilenameException(invalidDataFilenameMessage(name));
		}
		try {
			// Skip the folder name (the component) 
			String filename = name.substring(name.indexOf('/')+1);
			return filename.substring(0, filename.indexOf(DATA_FILE_SEPARATOR));
		} catch (Exception e) {
			throw new InvalidDataFilenameException(invalidDataFilenameMessage(name));
		}
	}

	public static String getName(String name) throws InvalidDataFilenameException {
		if (!isDataFilename(name)) {
			throw new InvalidDataFilenameException(invalidDataFilenameMessage(name));
		}
		try {
			// Skip the folder name (the component) 
			String filename = name.substring(name.indexOf('/')+1);
			return filename.substring(filename.indexOf(DATA_FILE_SEPARATOR)+1, filename.indexOf(DATA_FILE_EXTENTION));
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidDataFilenameException(invalidDataFilenameMessage(name));
		}
	}
	
	public static boolean isDataFile(File file) {
		String filename = file.getName();
		return isDataFilename(filename);
	}

	private static boolean isDataFilename(String name) {
		return StringUtils.endsWith(name, DATA_FILE_EXTENTION) &&
				StringUtils.split(name, DATA_FILE_SEPARATOR).length == 2;
	}

	private static String invalidDataFilenameMessage(String name) {
		return "The file " + name + " doesn't follow the convention for data file (splited by a ! and with a .d extension)";
	}
}

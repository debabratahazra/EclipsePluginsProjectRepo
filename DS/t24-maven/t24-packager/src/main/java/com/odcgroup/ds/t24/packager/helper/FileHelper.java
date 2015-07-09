package com.odcgroup.ds.t24.packager.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileHelper {

	public static void safelyCleanFolder(File folder) throws IOException {
		if (folder.getAbsolutePath().length() < 4) {
			throw new IOException("Unable to clean a folder too near to the root folder ("+folder.getAbsolutePath()+")");
		}
		FileUtils.cleanDirectory(folder);
	}
	
	public static String[] list(File file) {
		return safeArray(file.list());
	}
	
	public static File[] listFiles(File file) {
		return safeArray(file.listFiles());
	}
	
	public static File[] listFiles(File sourceFolder,
			FilenameFilter filenameFilter) {
		return safeArray(sourceFolder.listFiles(filenameFilter));
	}

	private static File[] safeArray(File[] files) {
		if (files == null) {
			return new File[0];
		} else {
			return files;
		}
	}

	private static String[] safeArray(String[] files) {
		if (files == null) {
			return new String[0];
		} else {
			return files;
		}
	}

}

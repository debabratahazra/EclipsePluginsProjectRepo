package com.odcgroup.integrationfwk.ui.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.google.common.io.Closeables;
import com.odcgroup.integrationfwk.ui.TWSConsumerLog;

/**
 * 
 * Responsible for write the T24 Response schema in relevant project folder.
 * 
 */
public class FileUtil {

	public static void createFolder(String schemaFolderPath) {
		File directory = new File(schemaFolderPath);
		directory.mkdir();
	}

	public static void deleteDirectory(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					deleteDirectory(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();

				}
			}

		} else {
			// if file, then delete it
			file.delete();

		}
	}

	/**
	 * utility method to remove the physical file from given path
	 * 
	 * @param filePathWithName
	 *            - full valid path with file name and extension
	 */
	public static void deleteFile(String filePathWithName) {
		File file = new File(filePathWithName);
		if (!file.exists()) {
			return;
		} else {
			file.delete();
		}
	}

	/**
	 * responsible for writing the io file
	 * 
	 * @param fileContents
	 *            - content to be write
	 * @param fileName
	 *            - name of the file
	 * @param filePath
	 *            - path for the file
	 */
	public static void doFileWrite(String fileContents, String fileName, String filePath) {
		try {
			File schemaFile = new File(filePath + File.separator + fileName);
			FileUtils.writeStringToFile(schemaFile, fileContents);
		} catch (IOException e) {
			TWSConsumerLog.logError(e);
		}

	}

	public static void doXmlFileWrite(String xmlContent, String fileName, String filePath) {
		try {
			File schemaFile = new File(filePath + File.separator + fileName);
			FileUtils.writeStringToFile(schemaFile, xmlContent);
		} catch (IOException e) {
			TWSConsumerLog.logError(e);
		}

	}

	/**
	 * Extracts the ASCII contents of the passed IFile, and stick them into a
	 * String
	 * 
	 * @param file
	 *            - an IFile
	 * @return String with its contents.
	 */
	public static String getFileContents(IFile file) {
		StringBuffer sb = new StringBuffer();
		Reader r = null;
		try {
			r = new InputStreamReader(file.getContents(), "ASCII");
			int c;
			while ((c = r.read()) != -1) {
				sb.append((char) c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		} finally {
			Closeables.closeQuietly(r);
		}
		return sb.toString();
	}

}

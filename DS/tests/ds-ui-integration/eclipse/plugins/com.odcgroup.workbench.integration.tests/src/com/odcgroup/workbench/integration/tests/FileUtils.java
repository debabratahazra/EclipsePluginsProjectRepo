package com.odcgroup.workbench.integration.tests;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

	public static String readFileAsString(String filePath) throws Exception {
		return readFileAsString(new File(filePath));
	}
	
	public static String readFileAsString(File file) throws Exception {
		 byte[] buffer = new byte[(int) file.length()];
		    BufferedInputStream inputStream = null;
		    try {
		        inputStream = new BufferedInputStream(new FileInputStream(file));
		        inputStream.read(buffer);
		    } finally {
		        if (inputStream != null) 
		        	try { 
		        		inputStream.close(); 
		        	} 
		        	catch (IOException ignored) { }
		    }
		    return new String(buffer);
	}
}

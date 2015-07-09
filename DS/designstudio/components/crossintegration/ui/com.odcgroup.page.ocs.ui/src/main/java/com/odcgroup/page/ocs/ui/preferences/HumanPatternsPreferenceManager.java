package com.odcgroup.page.ocs.ui.preferences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.page.ocs.PageOCSUIActivator;

/**
 * @author pkk
 *
 */
public class HumanPatternsPreferenceManager {

	/** 	 */
	static final String HUMANPATTERNS_FILE = "HumanPatterns.PROPERTIES";
	
	/** 	 */
	static final String HUMANPATTERNS_LOCATION = "OriginalWuiblocks\\WUI-UNPROFILED\\com\\odcgroup\\uif\\udp\\view";

	private static String getOCSInstallDirectory() {
		String dir = OCSPluginActivator.getDefault().getPreferenceManager().getInstallDirectory();
		return dir;
	}
	
	/**
	 * @return String
	 */
	public static List<String> loadHumanPropertiesFromOCSRuntime() {
		List<String> list = new ArrayList<String>();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(
					getOCSInstallDirectory()+"\\"+HUMANPATTERNS_LOCATION+"\\"+HUMANPATTERNS_FILE);
			list = fetchHumanProperties(fileInputStream);
		} catch (FileNotFoundException ex) {
			String message = "Triple'A Plus HumanPatterns File Not Found";
			IStatus status = new Status(IStatus.ERROR, PageOCSUIActivator.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, message, ex);
			PageOCSUIActivator.getDefault().getLog().log(status);
		} catch (IOException ex) {
			String message = "Unable to read Triple'A Plus HumanPatterns";
			IStatus status = new Status(IStatus.ERROR, PageOCSUIActivator.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, message, ex);
			PageOCSUIActivator.getDefault().getLog().log(status);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException ex) {
					// do nothing;
				}
			}
		}
		return list;
	}	
	
	/**
	 * @param inputStream 
	 * @return String a comma delimited string of data formats
	 * @throws IOException 
	 */
	public static List<String> fetchHumanProperties(InputStream inputStream) throws IOException {
		List<String> list = new ArrayList<String>();
		Properties properties = new Properties();
		properties.load(inputStream);		
		Set<Object> keys = properties.keySet();
		for (Object object : keys) {
			String key = (String) object;
			if (!(key.contains("alignment") || key.contains("xls"))) {
				list.add(key);
			}
		}
		return list;
	}
	
	
	/**
	 * @return boolean
	 */
	public static boolean humanPropertiesAvailable() {
		String installDir = getOCSInstallDirectory();
		if (StringUtils.isNotEmpty(installDir)) {
			try {
				new FileInputStream(installDir+"\\"+HUMANPATTERNS_LOCATION+"\\"+HUMANPATTERNS_FILE);
			} catch (FileNotFoundException e) {
				return false;
			}
			return true;
		}
		return false;
	}

}

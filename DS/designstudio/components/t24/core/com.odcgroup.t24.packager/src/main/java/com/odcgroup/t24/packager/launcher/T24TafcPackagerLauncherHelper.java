package com.odcgroup.t24.packager.launcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.packager.core.launcher.PackagerLauncherHelper;
import com.odcgroup.packager.core.launcher.UnableToOpenExplorerException;
import com.odcgroup.packager.core.launcher.UnableToStartPackagerException;

/**
 * TODO: Document me!
 *
 * @author RAMAPRIYAMN
 *
 */
public class T24TafcPackagerLauncherHelper extends PackagerLauncherHelper{


	private boolean openGeneratedTAFCPackageAtTheEnd = true;
	public static final String HOME ="HOME";
	public static final String HYPERSIGAR = "HYPERSIGAR";
	public static final String INCLUDE = "INCLUDE";
	public static final String PATH = "PATH";
	public static final String ROOT = "ROOT";
	public static final String TAFC_HOME = "TAFC_HOME";
	public static final String TOOLS = "TOOLS";
	public static final String LIB = "LIB";
	
	private static Logger logger = LoggerFactory.getLogger(T24TafcPackagerLauncherHelper.class);
	
	static T24TafcPackagerLauncherHelper INSTANCE = new T24TafcPackagerLauncherHelper();
	
	public static T24TafcPackagerLauncherHelper getHelper() {
		return INSTANCE;
	}
	
	@Override
	public ILaunch launchPackager(IProject packagerProject) throws UnableToStartPackagerException {
		return super.launchPackager(packagerProject);
	}

	@Override
	protected Map<String, Object> getDefaultProperties(IProject project) {
		Map<String, Object> defaultLaunchConfigAttributesValues = super.getDefaultProperties(project);
		defaultLaunchConfigAttributesValues.put(M2_GOALS, "clean package");
		defaultLaunchConfigAttributesValues.put(M2_OFFLINE, false);
		defaultLaunchConfigAttributesValues.put(M2_PROFILES, "ds.no.jdk");
		defaultLaunchConfigAttributesValues.put(M2_PROPERTIES, Arrays.asList(StringUtils.split("ds.ignoreValidationErrors=false")));
		defaultLaunchConfigAttributesValues.put(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, "${workspace_loc:/" + project.getName() + "/module}");
		
		Map<String, Object> defaultProperties = new HashMap<String, Object>();
		
		Properties prop = super.loadPackagerProperties(project);
		
		String t24home = prop.getProperty("HOME").trim();
		
		logger.debug("HOME:t24home:  " + t24home);
		defaultProperties.put(HOME,t24home);
		defaultProperties.put(HYPERSIGAR,t24home+"\\..\\..\\..\\..\\..\\Infra\\HypericSigar\\sigar-bin\\lib");
		defaultProperties.put(INCLUDE,t24home+"\\..\\..\\..\\..\\..\\Infra\\MSVisualStudio\\VC\\include");
		defaultProperties.put(PATH,t24home+"\\bin;"+t24home+"\\t24bin;"+t24home+"\\..\\..\\..\\..\\Programs\\TAFC\\bin;"+t24home+"\\..\\..\\..\\..\\..\\Infra\\HypericSigar\\sigar-bin\\lib;"+t24home+"\\..\\..\\..\\..\\..\\Infra\\MSVisualStudio\\VC\\bin\\amd64;"+t24home+"\\..\\..\\..\\..\\..\\Infra\\MSVisualStudio\\Common7\\IDE;"+t24home+"\\updater\\bin;C:\\windows\\system32;"+t24home+"\\..\\..\\..\\..\\..\\Infra\\Tar\\bin;C:\\EDS\\maven\\apache-maven-3.0\\bin");
		defaultProperties.put(ROOT,t24home+"\\..\\..\\..");
		defaultProperties.put(TAFC_HOME,t24home+"\\..\\..\\..\\..\\Programs\\TAFC");
		defaultProperties.put(TOOLS,t24home+"\\..\\..\\..\\..\\..\\Infra\\");
		defaultProperties.put(LIB,t24home+"\\..\\..\\..\\..\\..\\Infra\\MSSDK\\Windows\\Lib\\x64;"+t24home+"\\..\\..\\..\\..\\..\\Infra\\MSVisualStudio\\VC\\lib\\amd64");
		
		defaultLaunchConfigAttributesValues.put(ILaunchManager.ATTR_ENVIRONMENT_VARIABLES, defaultProperties);
		
		defaultLaunchConfigAttributesValues.put(ILaunchManager.ATTR_APPEND_ENVIRONMENT_VARIABLES, true);
		
		return defaultLaunchConfigAttributesValues;
		
	}
	/**
	 * Open the generated zip in a Windows Explorer
	 * @param generatedPackage 
	 */
	public void openGeneratedTAFCPackage(String generatedPackage) throws UnableToOpenExplorerException {
		if (!isOpenGeneratedTAFCPackageAtTheEnd())
			return;
		
		try {
			openExplorerAndSelectFile(generatedPackage);
		} catch (IOException e) {
			logger.error("TAFC Packager Generation Problem: Unable to show the generated file in the Windows Explorer:" + generatedPackage, e);
			throw new UnableToOpenExplorerException("Unable to show the generated file in the Windows Explorer:" + generatedPackage);
		}
	}

	private void openExplorerAndSelectFile(String filename) throws IOException {
		Runtime.getRuntime().exec("explorer.exe /select," + filename);
	}

	/**
	 * @return the openGeneratedCustoPackageAtTheEnd
	 */
	public boolean isOpenGeneratedTAFCPackageAtTheEnd() {
		return openGeneratedTAFCPackageAtTheEnd;
	}

	/**
	 * @param openGeneratedCustoPackageAtTheEnd the openGeneratedCustoPackageAtTheEnd to set
	 */
	public void setOpenGeneratedTAFCPackageAtTheEnd(
			boolean openGeneratedTAFCPackageAtTheEnd) {
		this.openGeneratedTAFCPackageAtTheEnd = openGeneratedTAFCPackageAtTheEnd;
	}
}

package com.odcgroup.ocs.packager.launcher;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.RefreshUtil;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackagerLauncherHelper {
	
	public static final String M2ECLIPSE_LAUNCH_CONFIG_TYPE = "org.eclipse.m2e.Maven2LaunchConfigurationType";
	
	public static final String M2_DEBUG_OUTPUT ="M2_DEBUG_OUTPUT";
	public static final String M2_GOALS = "M2_GOALS";
	public static final String M2_NON_RECURSIVE = "M2_NON_RECURSIVE";
	public static final String M2_OFFLINE = "M2_OFFLINE";
	public static final String M2_PROFILES = "M2_PROFILES";
	public static final String M2_PROPERTIES = "M2_PROPERTIES";
	public static final String M2_RUNTIME = "M2_RUNTIME";
	public static final String M2_SKIP_TESTS = "M2_SKIP_TESTS";
	public static final String M2_UPDATE_SNAPSHOTS = "M2_UPDATE_SNAPSHOTS";
	public static final String M2_WORKSPACE_RESOLUTION = "M2_WORKSPACE_RESOLUTION";
	
	private static Logger logger = LoggerFactory.getLogger(PackagerLauncherHelper.class);
	
	static PackagerLauncherHelper INSTANCE = new PackagerLauncherHelper();
	
	public static PackagerLauncherHelper getHelper() {
		return INSTANCE;
	}
	
	private boolean openGeneratedCustoPackageAtTheEnd = true;
	
	public ILaunch launchPackager(IProject packagerProject) throws UnableToStartPackagerException {
		
		// Launch the server
		try {
			final ILaunchConfiguration configuration = buildLaunchConfiguration(packagerProject);
			final ILaunch launch = configuration.launch(ILaunchManager.RUN_MODE, null);
			
			// Clean up the configuration (delete it) once finished
			new Thread() {
				@Override
				public void run() {
					while (!launch.isTerminated()) {
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							break;
						}
					}
					try {
						configuration.delete();
					} catch (CoreException e) {
						logger.error("Unable to remove the launch configuration", e);
					}
				}
			}.start();
			return launch;
		} catch (CoreException e) {
			logger.error("Cannot launch the packager: Unable to execute the launch configuration", e);
			throw new UnableToStartPackagerException("Unable to execute the launch configuration: "+ e.getMessage(), e);
		}
	}
	
	private Map<String, Object> getDefaultProperties(IProject project) {
		Map<String, Object> defaultLaunchConfigAttributesValues = new HashMap<String, Object>();
		defaultLaunchConfigAttributesValues.put(M2_DEBUG_OUTPUT, false);
		defaultLaunchConfigAttributesValues.put(M2_GOALS, "clean install");
		defaultLaunchConfigAttributesValues.put(M2_NON_RECURSIVE, false);
		defaultLaunchConfigAttributesValues.put(M2_OFFLINE, true);
		defaultLaunchConfigAttributesValues.put(M2_PROFILES, "");
		defaultLaunchConfigAttributesValues.put(M2_PROPERTIES, Arrays.asList(StringUtils.split("ds.ignoreValidationErrors=false,ds.no.jdk=true", ",")));
		defaultLaunchConfigAttributesValues.put(M2_RUNTIME, "EMBEDDED");
		defaultLaunchConfigAttributesValues.put(M2_SKIP_TESTS, false);
		defaultLaunchConfigAttributesValues.put(M2_UPDATE_SNAPSHOTS, false);
		defaultLaunchConfigAttributesValues.put(M2_WORKSPACE_RESOLUTION, true);
		defaultLaunchConfigAttributesValues.put(RefreshUtil.ATTR_REFRESH_SCOPE, "${workspace}");
		defaultLaunchConfigAttributesValues.put(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "");
		defaultLaunchConfigAttributesValues.put(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, "${workspace_loc:/" + project.getName() + "/module-pom}");
		return defaultLaunchConfigAttributesValues; 
	}

	/**
	 * @param packagerProject
	 * @return
	 * @throws CoreException
	 */
	ILaunchConfiguration buildLaunchConfiguration(
			IProject packagerProject) throws CoreException {
		
		ILaunchConfigurationWorkingCopy workingCopy = createWorkingCopy();

		Properties packagerProperties = loadPackagerProperties(packagerProject);
		Map<String, Object> defaultProperties = getDefaultProperties(packagerProject);
		configureLaunch(workingCopy, packagerProperties, defaultProperties);
		
		forceZipLocation(workingCopy, packagerProject.getLocation().toOSString());
		
		ILaunchConfiguration configuration = workingCopy.doSave();
		return configuration;
	}

	/**
	 * @return
	 * @throws CoreException
	 */
	protected ILaunchConfigurationWorkingCopy createWorkingCopy()
			throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(M2ECLIPSE_LAUNCH_CONFIG_TYPE);
		ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, "Start Custo Packager");
		return workingCopy;
	}

	protected void forceZipLocation(ILaunchConfigurationWorkingCopy workingCopy, String repoFolder) {
		String oldValue = getWorkingCopyAttribute(workingCopy);
		String newParameter = "-DrepoFolder=" + repoFolder;
		setWorkingCopyAttribute(workingCopy, IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, newParameter + (oldValue!=null?" "+oldValue:""));
	}

	/**
	 * @param workingCopy
	 */
	protected String getWorkingCopyAttribute(
			ILaunchConfigurationWorkingCopy workingCopy) {
		String key = IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS;
		try {
			return workingCopy.getAttribute(key, "");
		} catch (CoreException e) {
			logger.error("Unable to get attribute value. Key=" + key);
			return "";
		}
	}

	/**
	 * @param workingCopy
	 * @param packagerProperties
	 * @param defaultProperties
	 */
	void configureLaunch(ILaunchConfigurationWorkingCopy workingCopy,
			Properties packagerProperties,
			Map<String,Object> defaultProperties) {
		
		Set<Object> packagerPropertiesNotProcessed = packagerProperties.keySet();
		
		// Set the default values (if not overridden by the packager.properties)
		for (Map.Entry<String, Object> entry: defaultProperties.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (packagerProperties.containsKey(key)) {
				if (value instanceof String) {
					setWorkingCopyAttribute(workingCopy, key, packagerProperties.getProperty(key));
				} else if (value instanceof Boolean) {
					setWorkingCopyAttribute(workingCopy, key, Boolean.valueOf(packagerProperties.getProperty(key)));
				} else if (value instanceof List) {
					setWorkingCopyAttribute(workingCopy, key, Arrays.asList(StringUtils.split(packagerProperties.getProperty(key), ",")));
				}
				packagerPropertiesNotProcessed.remove(key);
			} else {
				setWorkingCopyAttribute(workingCopy, key, value);
			}
		}

		// Use the additional properties defined in the packager.properties
		for (Object key: packagerPropertiesNotProcessed) {
			String keyStr = (String)key;
			String value = packagerProperties.getProperty((String)keyStr);
			if (value.equals("true") || value.equals("false")) {
				setWorkingCopyAttribute(workingCopy, keyStr, Boolean.valueOf(value));
			} else {
				setWorkingCopyAttribute(workingCopy, keyStr, value);
			}
		}
	}

	/**
	 * @param workingCopy
	 * @param key
	 * @param value
	 */
	@SuppressWarnings("rawtypes")
	private void setWorkingCopyAttribute(
			ILaunchConfigurationWorkingCopy workingCopy, String key,
			Object value) {
		if (value instanceof String) {
			workingCopy.setAttribute(key, (String)value);
		} else if (value instanceof Boolean) {
			workingCopy.setAttribute(key, (Boolean)value);
		} else if (value instanceof List) {
			workingCopy.setAttribute(key, (List)value);
		} else {
			logger.error("not supported launch config attribute type (key="+key+", value="+value+", value type="+(value!=null?value.getClass():null)+")");
		}
	}

	Properties loadPackagerProperties(IProject packagerProject) {
		Properties properties = new Properties();
		IFile file = packagerProject.getFile("config/packager.properties");
		if (file.exists()) {
			FileReader reader = null;
			try {
				reader = new FileReader(file.getLocation().toFile());
				properties.load(reader);
			} catch (FileNotFoundException e) {
				// ignore
				logger.error("unable to load the packager properties", e);
			} catch (IOException e) {
				// ignore
				logger.error("unable to load the packager properties", e);
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						// Ignore
					}
				}
			}
		}
		return properties;
	}

	/**
	 * Open the generated zip in a Windows Explorer
	 * @param generatedPackage 
	 */
	public void openGeneratedCustoPackage(String generatedPackage) throws UnableToOpenExplorerException {
		if (!isOpenGeneratedCustoPackageAtTheEnd())
			return;
		
		try {
			openExplorerAndSelectFile(generatedPackage);
		} catch (IOException e) {
			logger.error("Custo Packager Generation Problem: Unable to show the generated file in the Windows Explorer:" + generatedPackage, e);
			throw new UnableToOpenExplorerException("Unable to show the generated file in the Windows Explorer:" + generatedPackage);
		}
	}

	private void openExplorerAndSelectFile(String filename) throws IOException {
		Runtime.getRuntime().exec("explorer.exe /select," + filename);
	}

	/**
	 * @return the openGeneratedCustoPackageAtTheEnd
	 */
	public boolean isOpenGeneratedCustoPackageAtTheEnd() {
		return openGeneratedCustoPackageAtTheEnd;
	}

	/**
	 * @param openGeneratedCustoPackageAtTheEnd the openGeneratedCustoPackageAtTheEnd to set
	 */
	public void setOpenGeneratedCustoPackageAtTheEnd(
			boolean openGeneratedCustoPackageAtTheEnd) {
		this.openGeneratedCustoPackageAtTheEnd = openGeneratedCustoPackageAtTheEnd;
	}	
	

}

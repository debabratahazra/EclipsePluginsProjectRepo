package com.odcgroup.t24.packager.launcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
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
public class T24TafjPackagerLauncherHelper extends PackagerLauncherHelper {

	public static final String FILE_NAME_TO_SEARCH = "tools.jar";
	private List<String> result = new ArrayList<String>();

	/**
	 * 
	 */
	private boolean openGeneratedTAFJPackageAtTheEnd = true;
	private static Logger logger = LoggerFactory.getLogger(T24TafjPackagerLauncherHelper.class);

	static T24TafjPackagerLauncherHelper INSTANCE = new T24TafjPackagerLauncherHelper();

	public static T24TafjPackagerLauncherHelper getHelper() {
		return INSTANCE;
	}

	String jrePath = "";

	@Override
	public ILaunch launchPackager(IProject packagerProject) throws UnableToStartPackagerException {
		result.clear();
		IVMInstall vmInstall = JavaRuntime.getDefaultVMInstall();
		File jdkLocation = vmInstall.getInstallLocation();
		if (jdkLocation.exists()) {
			searchDirectory(jdkLocation);
		}
		jrePath = jdkLocation.getPath();
		int count = result.size();
		if (count == 0) {
			throw new UnableToStartPackagerException(
					"The Installed JREs in Windows->Preferences->Java->Installed JREs does not have tools.jar,"
							+ " please configure JDK having tools.jar and make it default.", new Exception());
		}
		return super.launchPackager(packagerProject);
	}

	@Override
	protected Map<String, Object> getDefaultProperties(IProject project) {
		Map<String, Object> defaultLaunchConfigAttributesValues = super.getDefaultProperties(project);
		defaultLaunchConfigAttributesValues.put(M2_GOALS, "clean package");
		defaultLaunchConfigAttributesValues.put(M2_OFFLINE, false);
		defaultLaunchConfigAttributesValues.put(M2_PROFILES, "ds.no.jdk");
		defaultLaunchConfigAttributesValues.put(M2_PROPERTIES, Arrays.asList(StringUtils.split("ds.ignoreValidationErrors=false")));
		defaultLaunchConfigAttributesValues.put(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY,
				"${workspace_loc:/" + project.getName() + "/module}");
		defaultLaunchConfigAttributesValues.put(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, jrePath);

		return defaultLaunchConfigAttributesValues;

	}

	/**
	 * @param jdk
	 *            directory
	 */
	public void searchDirectory(File directory) {

		if (directory.isDirectory()) {
			search(directory);
		} else {
			logger.info(directory.getAbsoluteFile() + " is not a directory!");
		}

	}

	/**
	 * @param jar
	 *            file search
	 */
	private void search(File file) {

		if (file.isDirectory()) {
			logger.info("Search directory ... " + file.getAbsoluteFile());
			// check directory permission
			if (file.canRead()) {
				for (File temp : file.listFiles()) {
					if (temp.isDirectory()) {
						search(temp);
					} else {
						if (FILE_NAME_TO_SEARCH.equals(temp.getName().toLowerCase())) {
							result.add(temp.getAbsoluteFile().toString());
						}
					}
				}

			} else {
				logger.error(file.getAbsoluteFile() + "Permission Denied");
			}
		}

	}

	/**
	 * Open the generated zip in a Windows Explorer
	 * 
	 * @param generatedPackage
	 */
	public void openGeneratedTAFJPackage(String generatedPackage) throws UnableToOpenExplorerException {
		if (!isOpenGeneratedTAFJPackageAtTheEnd())
			return;

		try {
			openExplorerAndSelectFile(generatedPackage);
		} catch (IOException e) {
			logger.error("TAFJ Packager Generation Problem: Unable to show the generated file in the Windows Explorer:"
					+ generatedPackage, e);
			throw new UnableToOpenExplorerException("Unable to show the generated file in the Windows Explorer:"
					+ generatedPackage);
		}
	}

	private void openExplorerAndSelectFile(String filename) throws IOException {
		Runtime.getRuntime().exec("explorer.exe /select," + filename);
	}

	/**
	 * @return the openGeneratedCustoPackageAtTheEnd
	 */
	public boolean isOpenGeneratedTAFJPackageAtTheEnd() {
		return openGeneratedTAFJPackageAtTheEnd;
	}

	/**
	 * @param openGeneratedCustoPackageAtTheEnd
	 *            the openGeneratedCustoPackageAtTheEnd to set
	 */
	public void setOpenGeneratedTAFJPackageAtTheEnd(boolean openGeneratedTAFJPackageAtTheEnd) {
		this.openGeneratedTAFJPackageAtTheEnd = openGeneratedTAFJPackageAtTheEnd;
	}
}

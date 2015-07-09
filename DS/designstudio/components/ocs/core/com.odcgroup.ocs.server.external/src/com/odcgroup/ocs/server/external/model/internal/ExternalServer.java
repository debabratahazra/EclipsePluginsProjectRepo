package com.odcgroup.ocs.server.external.model.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jdt.core.JavaCore;

import com.odcgroup.ocs.server.external.builder.internal.delta.ServerLocationConstants;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.model.impl.OCSServer;
import com.odcgroup.ocs.server.preferences.DSServerPreferenceManager;
import com.odcgroup.ocs.support.OCSPluginActivator;
import com.odcgroup.ocs.support.preferences.OCSRuntimePreference;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * External server managed by the ServerView
 */
public class ExternalServer extends OCSServer implements IExternalServer {

	protected static final String STARTOCS_SCRIPT = "/ocs-instance/startocsDomain.cmd";
	protected static final String STOPOCS_SCRIPT = "/ocs-instance/bin/stopWebLogic.cmd";
	protected static final String RELOAD_MESSAGES_TOUCH_FILE = "/.reload-nls-messages";
	private static final String INSTALL_WAS_BOOTPORT = "install.was.bootport";
	protected static final String ALL_IN_ONE_DPI_PROPERTIES = "/_dpi_install/dpi/all-in-one.dpi.properties";

	boolean displayAllDeployedFiles;

	public ExternalServer(String id, String name) {
		super(id, name);
		this.installDir = getProjectPreferences().get(OCSRuntimePreference.INSTALL_DIR, null);

		OCSPluginActivator.getDefault().getProjectPreferences().addPreferenceChangeListener(new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				updateServerValuesFromPreferences();
				if (OCSRuntimePreference.INSTALL_DIR.equals(event.getKey())) {
					serverChanged();
				}
			}
		});
	}

	protected void updateServerValuesFromPreferences() {
		super.updateServerValuesFromPreferences();
		installDir = OCSPluginActivator.getDefault().getProjectPreferences().get(OCSRuntimePreference.INSTALL_DIR, null);
		displayAllDeployedFiles = Boolean.valueOf(getProjectPreferences().get(DSServerPreferenceManager.DISPLAY_ALL_DEPLOYED_FILES, "true"));
	}

	private ProjectPreferences getProjectPreferences() {
		return OCSPluginActivator.getDefault().getProjectPreferences();
	}

	public int getState() {
		// Not configured until the WebLogic start script
		// is available
		if (!startScriptExists()) {
			return IDSServer.STATE_NOT_CONFIGURED;
		}
		return super.getState();
	}

	public boolean startScriptExists() {
		return new File(getStartScript()).exists();
	}

	public String getListeningPort() {
		File allInOneDpiProperties = getAllInOneDpiProperties();
		FileReader reader = null;
		try {
			reader = new FileReader(allInOneDpiProperties);
			Properties props = new Properties();
			props.load(reader);
			return props.getProperty(INSTALL_WAS_BOOTPORT);
		} catch (FileNotFoundException e) {
			return "";
		} catch (IOException e) {
			return "";
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

	/**
	 * @return
	 */
	protected File getAllInOneDpiProperties() {
		return new File(getInstallationDirectory() + ALL_IN_ONE_DPI_PROPERTIES);
	}

	/**
	 * @return server start script location
	 */
	public String getStartScript() {
		return getInstallationDirectory() + STARTOCS_SCRIPT;
	}

	/**
	 * @return server stop script location
	 */
	public String getStopScript() {
		return getInstallationDirectory() + STOPOCS_SCRIPT;
	}

	/**
	 * @return the touch file used to ask cocoon to reload the translations
	 */
	public String getReloadMessagesTouchFile() {
		return getInstallationDirectory() + "/" + ServerLocationConstants.WUI_BLOCK_LOCATION + RELOAD_MESSAGES_TOUCH_FILE;
	}

	public boolean isDisplayAllDeployedFiles() {
		return displayAllDeployedFiles;
	}

	@Override
	public String getLogDirectory() {
		return getInstallationDirectory() + LOGS_LOCATION;
	}

	@Override
	public String getInstallationDirectory() {
		return installDir;
	}

	@Override
	public IProject getServerProject() {
		// There is not server project in that case
		return null;
	}

	@Override
	public boolean canDeploy(IProject project) {
		// Non java project cannot be deployed
		// Models project cannot be deplyoed
		try {
			if (!project.hasNature(JavaCore.NATURE_ID) || OfsCore.isOfsProject(project)) {
				return false;
			}
		} catch (CoreException e) {
			return false;
		}

		// All project can be deployed according to external server
		return true;
	}
}

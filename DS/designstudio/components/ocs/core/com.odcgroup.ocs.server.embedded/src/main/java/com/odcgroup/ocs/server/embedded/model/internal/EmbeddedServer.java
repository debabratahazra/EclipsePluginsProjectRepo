package com.odcgroup.ocs.server.embedded.model.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaCore;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerManager;
import com.odcgroup.ocs.server.model.impl.OCSServer;
import com.odcgroup.server.server.IDSServerStateChangeListener;
import com.odcgroup.workbench.core.OfsCore;

public class EmbeddedServer extends OCSServer implements IEmbeddedServer {

	private static final String LISTENING_PORT = "listening.port";
	protected static final String DEFAULT_LISTENING_PORT = "8080";
	private static final String VM_ARGUMENTS = "vm.arguments";
	protected static final String DEFAULT_VM_ARGUMENTS = "-Xmx512m -Dlogback.configurationFile=config/logback.xml";
	private static final String STARER_CLASS = "starter.class";
	protected static final String DEFAULT_STARER_CLASS = "com.odcgroup.ds.tslapp.TSLAppWUIJettyStarterMain";
	protected static final String SERVER_PROPERTIES = "/config/server.properties";
	protected final IProject serverProject;
	private IResourceChangeListener serverPropertiesChangeListener = null;
	
	public EmbeddedServer(String id, String name, IProject serverProject) {
		super(id, name);
		this.installDir = serverProject.getLocationURI().getPath();
		this.serverProject = serverProject;
	}

	public String getListeningPort() {
		return readParameter(LISTENING_PORT, DEFAULT_LISTENING_PORT);
	}

	@Override
	public String getVmArguments() {
		return readParameter(VM_ARGUMENTS, DEFAULT_VM_ARGUMENTS);
	}


	@Override
	public String getStarterClass() {
		return readParameter(STARER_CLASS, DEFAULT_STARER_CLASS);
	}
	
	/**
	 * @param parameter
	 * @return
	 */
	private String readParameter(String parameter, String defaultValue) {
		FileReader reader = null;
		try {
			reader = new FileReader(getServerPropertiesFile());
			Properties props = new Properties();
			props.load(reader);
			if (props.containsKey(parameter)) {
				return props.getProperty(parameter);
			} else {
				return defaultValue;
			}
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
	protected File getServerPropertiesFile() {
		return new File(serverProject.getLocation().toFile(), SERVER_PROPERTIES);
	}

	public IProject getServerProject() {
		return serverProject;
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
	public void addServerStateChangeListener(
			IDSServerStateChangeListener listener) {
		super.addServerStateChangeListener(listener);
		if(serverPropertiesChangeListener==null) {
			serverPropertiesChangeListener = new ServerPropertiesChangeListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(serverPropertiesChangeListener, IResourceChangeEvent.POST_CHANGE);
		}
	}
	
	@Override
	public void removeServerStateChangeListener(
			IDSServerStateChangeListener listener) {
		super.removeServerStateChangeListener(listener);
		if(stateListeners.size()==0) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(serverPropertiesChangeListener);
			serverPropertiesChangeListener = null;
		}
	}

	private final class ServerPropertiesChangeListener implements
			IResourceChangeListener {
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			if (delta != null) {
				final IPath serverPropertiesFullPath = serverProject.getFullPath().append(SERVER_PROPERTIES);
				if(delta.findMember(serverPropertiesFullPath)!=null) {
					serverChanged();
				}
			}
		}
	}

	@Override
	public boolean canDeploy(IProject project) {
		// Non java project cannot be deployed
		// Models project cannot be deployed
		try {
			if (!project.hasNature(JavaCore.NATURE_ID) || OfsCore.isOfsProject(project)) {
				return false;
			}
		} catch (CoreException e) {
			return false;
		}
		
		// Avoid to propose to deploy the server project itself.
		if (EmbeddedServerManager.getInstance().canBeAddedToServersList(project)) {
			return false;
		}
		return true;
	}
}

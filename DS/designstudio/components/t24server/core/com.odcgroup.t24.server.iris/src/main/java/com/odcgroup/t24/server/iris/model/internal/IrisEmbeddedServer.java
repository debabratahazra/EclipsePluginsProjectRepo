package com.odcgroup.t24.server.iris.model.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.JavaCore;

import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.server.IDSServerStateChangeListener;
import com.odcgroup.t24.server.iris.model.IIrisEmbeddedServer;
import com.odcgroup.t24.server.iris.util.IrisServerManager;
import com.odcgroup.workbench.core.OfsCore;

/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class IrisEmbeddedServer extends DSServer implements IIrisEmbeddedServer {
	
	protected static final String DEFAULT_STARER_CLASS = "com.temenos.ds.embedded.server.iris.IrisEmbeddedServer";
	protected static final String WEB_SERVER_PROPERTIES = "/src/main/resources/web-server.properties";
	protected static final String JETTY_REALM_PROPERTIES = "/src/main/resources/jetty-realm.properties";
	protected final IProject serverProject;
	private IResourceChangeListener serverPropertiesChangeListener = null;

	public IrisEmbeddedServer(String id, String name, IProject serverProject) {
		super(id, name);
		this.installDir = serverProject.getLocationURI().getPath();
		this.serverProject = serverProject;
	}

	@Override
	public String getListeningPort() {
		// Not required.
		return null;
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
		return serverProject;
	}

	@Override
	public boolean canDeploy(IProject project) {
		try {
			if (!project.hasNature(JavaCore.NATURE_ID) || OfsCore.isOfsProject(project)) {
				return false;
			}
		} catch (CoreException e) {
			return false;
		}
		
		if (IrisServerManager.getInstance().canBeAddedToServersList(project)) {
			return false;
		}
		return true;
	}

	@Override
	public String getStarterClass() {
		return DEFAULT_STARER_CLASS;
	}

	@Override
	public String getProgArguments(IIrisEmbeddedServer irisServer) {
		String propertiesPath = new String();
		if(irisServer != null){
			IPath path = irisServer.getServerProject().getProject().getLocation();
			IPath webPath = path.append(WEB_SERVER_PROPERTIES);
			IPath jettyPath = path.append(JETTY_REALM_PROPERTIES);
			propertiesPath = "\""+ webPath.toOSString() + "\"" + " " + "\"" + jettyPath.toOSString() + "\"";
		}
		return propertiesPath;
	}

	@Override
	public String getVmArguments() {
		return "";
	}
	
	@Override
	public void addServerStateChangeListener(IDSServerStateChangeListener listener) {
		super.addServerStateChangeListener(listener);
		if(serverPropertiesChangeListener==null) {
			serverPropertiesChangeListener = new ServerPropertiesChangeListener();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(serverPropertiesChangeListener, IResourceChangeEvent.POST_CHANGE);
		}
	}
	
	@Override
	public void removeServerStateChangeListener(IDSServerStateChangeListener listener) {
		super.removeServerStateChangeListener(listener);
		if(stateListeners.size()==0) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(serverPropertiesChangeListener);
			serverPropertiesChangeListener = null;
		}
	}
	
	private final class ServerPropertiesChangeListener implements IResourceChangeListener {
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			if (delta != null) {
				serverChanged();
			}
		}
	}
	
}


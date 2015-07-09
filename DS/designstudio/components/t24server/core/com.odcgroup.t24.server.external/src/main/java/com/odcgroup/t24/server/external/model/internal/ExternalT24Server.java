package com.odcgroup.t24.server.external.model.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSServer;
import com.odcgroup.server.server.IDSServerStateChangeListener;
import com.odcgroup.server.util.DSProjectUtil;
import com.odcgroup.t24.server.external.model.AAProductsDetails;
import com.odcgroup.t24.server.external.model.ApplicationDetail;
import com.odcgroup.t24.server.external.model.EnquiryDetail;
import com.odcgroup.t24.server.external.model.IExternalLoader;
import com.odcgroup.t24.server.external.model.IExternalObject;
import com.odcgroup.t24.server.external.model.IExternalServer;
import com.odcgroup.t24.server.external.model.InteractionDetail;
import com.odcgroup.t24.server.external.model.LocalRefApplicationDetail;
import com.odcgroup.t24.server.external.model.LocalRefDetail;
import com.odcgroup.t24.server.external.model.VersionDetail;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper;
import com.odcgroup.t24.server.external.util.ServerPropertiesHelper.T24ConnectionProtocol;
import com.odcgroup.t24.server.external.util.T24ExternalServerManager;

/**
 * External server managed by the ServerView
 */
public class ExternalT24Server extends DSServer implements IExternalServer {

	public static final String CONFIG = "config";
	public static final String SERVER_PROPERTIES = "server.properties";
	
	public static final String SERVER_PROPERTIES_PATH = "/"+ CONFIG + "/" + SERVER_PROPERTIES;

	private IProject serverProject;
	private IResourceChangeListener serverPropertiesChangeListener = null;

	public ExternalT24Server(String id, String name, IProject serverProject) {
		super(id, name);
		this.installDir = serverProject.getLocationURI().getPath();
		this.serverProject = serverProject;
		Preferences t24ExternalServerState = ConfigurationScope.INSTANCE.getNode("com.odcgroup.t24.server.external");
	    Preferences t24ExtServPref = t24ExternalServerState.node("t24ExtServ");
	    //default or saved preference value
		this.currentState = Integer.valueOf(t24ExtServPref.get(serverProject.getName(),"9"));
	}

	@Override
	public void setState(int newState) {
		super.setState(newState);
		//DS-5922
		Preferences t24ExternalServerState = ConfigurationScope.INSTANCE.getNode("com.odcgroup.t24.server.external");
        Preferences t24ExtServPref = t24ExternalServerState.node("t24ExtServ");
        t24ExtServPref.put(serverProject.getName(), this.getState()+"");
        try {
            // save the preferences
        	t24ExternalServerState.flush();
        } catch (BackingStoreException e) {
        	  return;
        }
	}
	/**
	 * @return
	 */
	public File getServerPropertiesFile() {
		if (serverProject != null && serverProject.getLocation() != null) {
			return new File(serverProject.getLocation().toFile(), SERVER_PROPERTIES_PATH);
		}
		return new File("dummy");	// Avoid NPE
	}
		
	public List<IDSProject> getT24Projects(List<IDSProject> t24Projects) {
		List<IDSProject> list = new ArrayList<IDSProject>();
		for(IDSProject prj: t24Projects){
			IProject iPrj = (IProject)prj;
			if (iPrj.getFolder(".gen") != null) {
				list.add(prj);
			}
		}
		return list;
	}

	@Override
	public IProject getServerProject() {
		return serverProject;
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
				final IPath serverPropertiesFullPath = serverProject.getFullPath().append(SERVER_PROPERTIES_PATH);
				if(delta.findMember(serverPropertiesFullPath)!=null) {
					serverChanged();
				}
			}
		}
	}

	@Override
	public List<IDSProject> getDeployedProjects() {
		try {
			Properties properties = readPropertiesFile();
			String deployedProjects = ServerPropertiesHelper.getDeployedProjects(properties);
			return convertProjectFileStringToList(deployedProjects);
		} catch (IOException e) {
			// If no server properties are found, 
			return Collections.emptyList();
		}
	}

	/**
	 * @param deployedProjects
	 * @return
	 */
    private List<IDSProject> convertProjectFileStringToList(String deployedProjects) {
    	List<IDSProject> projects = new ArrayList<IDSProject>();
    	if (deployedProjects != null && !deployedProjects.trim().equals("")) {
    		String[] splitDeployedProjects = StringUtils.split(deployedProjects, ",");
    		IDSProject dsProject = null;
    		for(int i=0;i<splitDeployedProjects.length; i++){
    			String name = splitDeployedProjects[i].trim();
    			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
    			if (project.exists() && project.isOpen()) {
    				String loc = project.getLocation().toOSString();
    				dsProject = DSProjectUtil.getDsProject(name, loc );
    				projects.add(dsProject);
    			}
   			}
    	}
    	return projects;
    }
    
	@Override
	public String getLogDirectory() {
		return null;
	}

	@Override
	public String getInstallationDirectory() {
		return null;
	}

	@Override
	public String getListeningPort() {
		return null;
	}
	
	public Properties readPropertiesFile() throws IOException {
		FileInputStream finStream = null;
		try {
			finStream = new FileInputStream(getServerPropertiesFile());
			Properties prop = new Properties();
			prop.load(finStream);
			return prop;
		} finally{
			 IOUtils.closeQuietly(finStream);
		}
	}

	@Override
	public boolean canDeploy(IProject project) {
		// Avoid to propose to deploy the server project itself.
		if (T24ExternalServerManager.getInstance().canBeAddedToServersList(project)) {
			return false;
		}
		
		try {
			return project.hasNature(JavaCore.NATURE_ID);
		} catch (CoreException e) {
			return false;
		}
	}

	@Override
	public final <E extends IExternalObject> IExternalLoader getExternalLoader(Class<E> type) throws T24ServerException {
		// TODO replace the code below with a mechanism based on an extension point
		IExternalLoader loader = null; 
		try {
			Properties properties = readPropertiesFile();
			boolean useWebService = ServerPropertiesHelper.getProtocol(properties) == T24ConnectionProtocol.WS;
			if (ApplicationDetail.class == type) {
				if (useWebService) {
					loader = new ApplicationWebServiceLoader(properties);
				} else {
					loader = new ApplicationLoader(properties);
				}
			} else if (EnquiryDetail.class == type) {
				if (useWebService) {
					loader = new EnquiryWebServiceLoader(properties);
				} else {
					loader = new EnquiryLoader(properties);
				}
			} else if (VersionDetail.class == type) {
				if (useWebService) {
					loader = new VersionWebServiceLoader(properties);
				} else {
					loader = new VersionLoader(properties);
				}
			} else if (LocalRefDetail.class == type) {
				if (useWebService) {
					loader = new LocalRefWebServiceLoader(properties);
				} else {
					loader = new LocalRefLoader(properties);
				}
			} else if (LocalRefApplicationDetail.class == type) {
				if (useWebService) {
					loader = new LocalRefApplicationWebServiceLoader(properties);
				} else {
					loader = new LocalRefApplicationLoader(properties);
				}
			} else if (AAProductsDetails.class == type) {
				if (useWebService) {
					loader = new AAProductsWebServiceLoader(properties);
				} else {
					loader = new AAProductsLoader(properties);
				}
			} else if (InteractionDetail.class == type) {
				if (useWebService) {
					loader = new InteractionWebLoader(properties);
				} else {
					loader = new InteractionLoader(properties);
				}
			}
			return loader;
		} catch (IOException e) {
			throw new T24ServerException("Unable to read the server properties", e);
		}
	}


}

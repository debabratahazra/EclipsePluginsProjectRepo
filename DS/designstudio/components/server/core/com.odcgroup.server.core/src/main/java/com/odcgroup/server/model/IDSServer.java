package com.odcgroup.server.model;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.server.server.IDSServerStateChangeListener;

/**
 * Interface of server managed by the server view
 */
public interface IDSServer extends IDSServerStates {

	public static final String LOGS_LOCATION = "/logs";

	public String getName();

	public String getListeningPort();

	public String getId();

	public int getState();

	public void setState(int state);

	public List<IDSProject> getDsProjects();

	public IDSProject getDsProject(String name);

	public void addDsProject(IDSProject dsProject);

	public boolean removeDsProject(String name);

	public boolean containsProject(String name);

	public boolean isClearLogAtStartup();

	public void addServerStateChangeListener(IDSServerStateChangeListener listener);

	public void removeServerStateChangeListener(IDSServerStateChangeListener listener);

	public void updateProjectList(IDSProject[] dsProjects);

	public String getLogDirectory();

	public String getInstallationDirectory();

	public List<String> getWatchedLogFiles();
	
	/**
	 * @return the server project or null if not project is associated to this server.
	 */
	public IProject getServerProject();
	
	/**
	 * @param project
	 * @return <code>true</code> if the server is able to deploy the project in parameter,
	 * <code>false</code> otherwise.
	 */
	public boolean canDeploy(IProject project);

}

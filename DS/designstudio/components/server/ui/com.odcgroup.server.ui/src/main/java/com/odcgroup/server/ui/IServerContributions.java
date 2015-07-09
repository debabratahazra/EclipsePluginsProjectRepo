package com.odcgroup.server.ui;

import java.util.List;

import org.eclipse.core.resources.IProject;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.views.IAddAction;

public interface IServerContributions {
	
	public enum ChangeKind { ADDED, CHANGED, REMOVED };

	/**
	 * Called to rebuild internally the servers/projects list
	 */
	public void refreshServers();
	
	/**
	 * Gather the list of servers (managed from the server view)
	 * @return the list of servers
	 */
	public List<IDSServer> getServers();

	/**
	 * Called when a project is opened in the workbench (manual project opening or new project creation).
	 * The project can be a server project, a deployed project or an unrelated project
	 * @param newProject
	 */
	public void notifyProjectChanged(IProject project, ChangeKind kind);

	/**
	 * Add a listener to respond to external event that modify the server list.
	 * This allows the view to be notified when the model changes.
	 * @param listener
	 */
	public void addListenerServerAddedRemovedExternally(IServerExternalChangeListener listener);

	/**
	 * Fill Add Server Toolbar menu
	 * @param addAction
	 */
	public void fillAddServerToolbarMenu(IAddAction addAction); 

	/**
	 * Fill Add Server to the context menu
	 * @param server
	 * @param addAction
	 */
	void fillAddServerContextMenu(IDSServer server, IAddAction addAction); 
	
	/**
	 * Used to add configuration entries to the context menu
	 * @param server
	 * @param addAction
	 */
	void fillConfigureServerContextMenu(IDSServer server, IAddAction addAction); 
	
	/**
	 * Some action can be taken when a project is add/removed from the server view
	 */
	void updateDeployedProjects(IDSServer server, IDSProject[] projects);	
	
	/**
	 * Starts the server
	 * @param server
	 * @param debug true if the server should start in debug mode
	 * @throws UnableToStartServerException
	 */
	void start(IDSServer server, boolean debug) throws UnableToStartServerException; 
	
	/**
	 * stops the server
	 * @param server
	 * @throws UnableToStopServerException
	 */
	void stop(IDSServer server) throws UnableToStopServerException;

}

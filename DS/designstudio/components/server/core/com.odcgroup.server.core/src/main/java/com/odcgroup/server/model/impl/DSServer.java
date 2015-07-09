package com.odcgroup.server.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.server.IDSServerStateChangeListener;

/**
 * Common behavior of servers managed by the ServerView
 */
public abstract class DSServer implements IDSServer {

	private static Logger logger = LoggerFactory.getLogger(DSServer.class);

	private String name;
	private String id;
	private Map<String, IDSProject> projects;
	protected int currentState = IDSServer.STATE_STOPPED;
	protected List<IDSServerStateChangeListener> stateListeners = new LinkedList<IDSServerStateChangeListener>();

	protected String installDir;

	public DSServer(String id, String name) {
		this.name = name;
		this.id = id;

		projects = new LinkedHashMap<String, IDSProject>();
		updateServerValuesFromPreferences();
	}

	protected void updateServerValuesFromPreferences() {
	}

	public void addDsProject(IDSProject dsProject) {
		dsProject.setServer(this);
		projects.put(dsProject.getName(), dsProject);
		serverChanged();
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public List<IDSProject> getDsProjects() {
		List<IDSProject> list = new ArrayList<IDSProject>();
		list.addAll(projects.values());
		return list;
	}

	public boolean removeDsProject(String name) {
		if (projects.containsKey(name)) {
			projects.remove(name);
			serverChanged();
			return true;
		} else {
			return false;
		}
	}

	public boolean containsProject(String name) {
		return projects.containsKey(name);
	}

	public void updateProjectList(IDSProject[] dsProjects) {
		projects.clear();
		for (IDSProject dsProject : dsProjects) {
			projects.put(dsProject.getName(), dsProject);
		}
		serverChanged();
	}

	public int getState() {
		return this.currentState;
	}

	public void setState(int newState) {
		if (this.currentState != newState) {
			this.currentState = newState;
			serverChanged();
		}
	}

	protected void serverChanged() {
		for (IDSServerStateChangeListener listener : stateListeners) {
			try {
				listener.serverStateChanged(this);
			} catch (RuntimeException e) {
				logger.error("Unexpected error during listener call", e);
			}
		}
	}

	public void addServerStateChangeListener(IDSServerStateChangeListener listener) {
		stateListeners.add(listener);
	}

	public void removeServerStateChangeListener(IDSServerStateChangeListener listener) {
		stateListeners.remove(listener);
	}

	@Override
	public List<String> getWatchedLogFiles() {
		return Collections.<String>emptyList();
	}

	public IDSProject getDsProject(String name) {
		return projects.get(name);
	}
	
	@Override
	public boolean isClearLogAtStartup() {
		return false;
	}

}

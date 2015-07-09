package com.odcgroup.ocs.server.preferences;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.util.DSProjectUtil;
import com.odcgroup.workbench.core.preferences.ProjectPreferences;

/**
 * @author yan
 * @since 1.40
 */
public class DSServerPreferenceManager {

	private static final String PREFIX = "com.odcgroup.ocs.server";

    public static final String WATCHED_LOG_FILES = PREFIX + ".watched.logs";
    public static final String DEPLOYED_PROJECTS = PREFIX + ".deployed.projects";
    public static final String CLEAR_LOG_AT_STARTUP = PREFIX + ".clear.logs.at.startup";
    public static final String DISPLAY_ALL_DEPLOYED_FILES = PREFIX + ".display.all.deployed.files";

    public DSServerPreferenceManager() {
    }

    public ProjectPreferences getProjectPreferences() {
        return new ProjectPreferences(null, ServerCore.PLUGIN_ID);
    }

    public List<String> getWatchedLogFiles() {
    	return convertWatchedLogFileStringToList(getProjectPreferences().get(WATCHED_LOG_FILES, null));
    }

    public List<IDSProject> getDeployedProjects() {
    	String deployedProjects = getProjectPreferences().get(DEPLOYED_PROJECTS, null);
		return convertProjectFileStringToList(deployedProjects);
    }

    /**
     * @param deployedProjects
     * @return
     */
    private static List<IDSProject> convertProjectFileStringToList(String deployedProjects) {
    	List<IDSProject> projects = new ArrayList<IDSProject>();
    	if (deployedProjects != null && !deployedProjects.trim().equals("")) {
    		StringTokenizer st = new StringTokenizer(deployedProjects, ",");
    		String token = null;
    		IDSProject project = null;
    		while (st.hasMoreTokens()) {
    			token = st.nextToken().trim();
    			int index = token.indexOf("<");
    			String name = token.substring(0, index);
    			String loc = token.substring(index+1, token.length()-1);
    			project = DSProjectUtil.getDsProject(name, loc);
    			projects.add(project);
    		}
    	}
    	return projects;
    }

    /**
     * @param projects
     */
    public  void updateDeployedProjects(IDSProject[] projects) {
    	if (projects.length == 0) {
			getProjectPreferences().put(DSServerPreferenceManager.DEPLOYED_PROJECTS, "");
		}
		if (projects.length > 0) {
			StringBuffer sb = new StringBuffer();
			IDSProject project = null;
			for (int ii = 0; ii < projects.length; ii++) {
				project = (IDSProject) projects[ii];
				sb.append(project.getName()+"<"+project.getProjectLocation().toString()+">");
				if (ii != projects.length-1) {
					sb.append(",");
				}
			}
			getProjectPreferences().put(DSServerPreferenceManager.DEPLOYED_PROJECTS, sb.toString());
		}
		getProjectPreferences().flush();
    }

    /**
     * @param watchedLogFileString
     * @return
     */
    public static List<String> convertWatchedLogFileStringToList(String watchedLogFileString) {
		List<String> watchedLogFiles = new LinkedList<String>();
		if (watchedLogFileString == null) {
			return watchedLogFiles;
		}

		StringTokenizer st = new StringTokenizer(watchedLogFileString, ",");
		while (st.hasMoreTokens()) {
			watchedLogFiles.add(st.nextToken().trim());
		}
		return watchedLogFiles;
    }

}

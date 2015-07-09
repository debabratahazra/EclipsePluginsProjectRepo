package com.odcgroup.server.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSProject;

/**
 *
 * @author can
 *
 */
public class DSProjectUtil {

	/**
	 * @param dsProjects
	 * @return
	 */
	public static List<IProject> convertToIProjects(IDSProject[] dsProjects) {
		return convertToIProjects(ResourcesPlugin.getWorkspace().getRoot(), dsProjects);
	}

	public static List<IProject> convertToIProjects(IWorkspaceRoot workspaceRoot, IDSProject[] dsProjects) {
		if (dsProjects == null) {
			return new ArrayList<IProject>();
		} else {
			return convertToIProjects(workspaceRoot, Arrays.asList(dsProjects));
		}
	}

	public static List<IProject> convertToIProjects(List<IDSProject> dsProjects) {
		return convertToIProjects(ResourcesPlugin.getWorkspace().getRoot(), dsProjects);
	}

	/**
	 * @param dsProjects
	 * @return
	 */
	public static List<IProject> convertToIProjects(IWorkspaceRoot workspaceRoot, List<IDSProject> dsProjects) {
		List<IProject> projects = new LinkedList<IProject>();

		if (dsProjects != null) {

			for (IDSProject dsProject : dsProjects) {
				projects.add(convertToIProject(workspaceRoot, dsProject));
			}
		}

		return projects;
	}

	public static IProject convertToIProject(IWorkspaceRoot workspaceRoot, IDSProject dsProject) {
		String dsProjectName = dsProject.getName();
		return workspaceRoot.getProject(dsProjectName);
	}

	/**
	 * @param project
	 * @return
	 */
	public static IDSProject getDsProject(IProject project) {
		return new DSProject(project);
	}

	/**
	 * @param name
	 * @param projectLocation
	 * @return
	 */
	public static IDSProject getDsProject(String name, String projectLocation) {
		if (name != null && projectLocation != null) {
			return new DSProject(name, projectLocation);
		}
		return null;
	}

	/**
	 * @param projectName
	 * @param newProjectList
	 */
	public static List<IDSProject> removeProjectByName(String projectName, List<IDSProject> projectList) {
		List<IDSProject> newProjectList = new ArrayList<IDSProject>(projectList);
		for (Iterator<IDSProject> iter = newProjectList.iterator(); iter.hasNext(); ) {
			if (iter.next().getName().equals(projectName)) {
				iter.remove();
			}
		}
		return newProjectList;
	}
	
	/**
	 * Add -model-gen deployed project to Server
	 * @param projectName
	 * @param newProjectList
	 */
	public static List<IDSProject> addProjectByName(IProject iProject, List<IDSProject> projectList) {
		List<IDSProject> newProjectList = new ArrayList<IDSProject>(projectList);
		for (IDSProject idsProject : newProjectList) {
			if(idsProject.getName().equals(iProject.getName())){
				// Avoid same deployable project to add in Server
				return newProjectList;
			}
		}
		newProjectList.add(new DSProject(iProject));
		return newProjectList;
	}
}

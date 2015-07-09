package com.odcgroup.ocs.server.external.ui.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.Job;

import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.util.DSProjectUtil;

/**
 * Helper to prepare the projects for undeployment.
 * @author can
 */
public class PrepareUndeploymentFacade {

	public static void undeployProjects(List<IProject> deployedProjects, List<IProject> ocsProjects) {
		List<IProject> undeployedProjects = listUndeployedProjects(deployedProjects, ocsProjects);
		for (IProject project : undeployedProjects) {
			undeployProject(project);
		}
	}

	public static void undeployProjects(List<IDSProject> previousDeployedOCSProjects, IDSProject[] ocsProjects) {
		List<IProject> deployedProjects = DSProjectUtil.convertToIProjects(previousDeployedOCSProjects);
		List<IProject> newProjectsList = DSProjectUtil.convertToIProjects(ocsProjects);
		undeployProjects(deployedProjects, newProjectsList);
	}


	public static List<IProject> listUndeployedProjects(List<IProject> oldDeployedProjects, List<IProject> newlyDeployedProjects) {
		List<IProject> undeployedProjects = new ArrayList<IProject>();

		for (IProject project : oldDeployedProjects) {
			if(!newlyDeployedProjects.contains(project)) {
				undeployedProjects.add(project);
			}
		}

		return undeployedProjects;
	}

	/**
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public static void undeployProject(IProject project) {
		UndeployJob undeployJob = new UndeployJob(project);
		undeployJob.setRule(project);
		undeployJob.setPriority(Job.BUILD);
		undeployJob.schedule();
	}
}

package com.odcgroup.ocs.server.external.ui.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.ocs.server.external.model.IExternalServer;
import com.odcgroup.ocs.server.external.ui.OCSServerUIExternalCore;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.ui.internal.CommonConsole;
import com.odcgroup.workbench.core.OfsCore;

/**
 * Helper to prepare the projects for deployment.
 * @see com.odcgroup.ocs.server.builder.internal.mapping.PrepareDeploymentHelper
 * for low level method not related to builder and nature
 * @author yan
 */
public class PrepareDeploymentFacade {


	/**
	 * Add the deploy builder to a list of projects
	 */
	public static void makeProjectsDeployable(Collection<IProject> projects, boolean forceDeploy) {
		for (IProject project : projects) {
			addNature(project);
			addDeployBuilder(project);
		}
		triggerDeployBuilder(projects, forceDeploy);
	}

	/**
	 * Update the server view and ensure the builder of deployed project
	 * are correctly setup.
	 */
	public static void updateServerViewAndFixConfig() {
		// Build the list of projects name open in the workspace
		Map<String, IProject> openProjectsInWorkspace = new HashMap<String, IProject>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (project.isOpen()) {
				openProjectsInWorkspace.put(project.getName(), project);
			}
		}

		// Remove all project not in the workspace
		List<String> removedProjects = new ArrayList<String>();
		List<IDSProject> keptProjects = new ArrayList<IDSProject>();
		IExternalServer externalServer = OCSServerUIExternalCore.getDefault().getExternalServer();
		for (IDSProject ocsProject : externalServer.getDsProjects()) {
			if (!openProjectsInWorkspace.containsKey(ocsProject.getName())) {
				externalServer.removeDsProject(ocsProject.getName());
				removedProjects.add(ocsProject.getName());
			} else {
				// Ensure the existing project are correctly configured
				PrepareDeploymentFacade
						.ensureProjectIsDeployable(openProjectsInWorkspace
								.get(ocsProject.getName()));
				keptProjects.add(ocsProject);
			}
		}

		// Save the configuration
		ServerCore.getDefault().getOCSServerPreferenceManager().updateDeployedProjects(
				keptProjects.toArray(new IDSProject[keptProjects.size()]));
	}

	/**
	 * Ensure the project is property configured (has the right
	 * nature and builder)
	 * @param project
	 */
	private static void ensureProjectIsDeployable(IProject project) {
		addNature(project);
		addDeployBuilder(project);
	}

	/**
	 * @param project
	 */
	private static void addNature(IProject project) {
		CommonConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
		if (project.isOpen()) {
			try {
				OfsCore.addNature(project, OCSServerUIExternalCore.NATURE_ID);
			} catch (CoreException e) {
				deployConsole.printError("Cannot add the deploy nature to " + project.getName() + " because " + e.getMessage());  //$NON-NLS-1$
				return;
			}
		} else {
			deployConsole.printWarning("Cannot add the deploy builder to " + project.getName() + " because it is a closed project");  //$NON-NLS-1$
		}
	}

	/**
	 * Add the deploy builder to a project
	 */
	private static void addDeployBuilder(IProject project) {
		CommonConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();

		// Cannot modify closed projects
		if (!project.isOpen()) {
			deployConsole.printWarning("Cannot add the deploy builder to " + project.getName() + " because it is a closed project");  //$NON-NLS-1$
			return;
		}

		// Get the description
		IProjectDescription description;
		try {
			description = project.getDescription();
		} catch (CoreException e) {
			deployConsole.printError("Unable to get project description of " + project.getName());  //$NON-NLS-1$
			return;
		}

		// Builder already associated to the project ?
		for (ICommand cmd : description.getBuildSpec()) {
			if (cmd.getBuilderName().equals(DeployBuilder.BUILDER_ID)) {
				return;
			}
		}

		// Associate builder with the project
		ICommand newCmd = description.newCommand();
		newCmd.setBuilderName(DeployBuilder.BUILDER_ID);
		List<ICommand> newCmds = new ArrayList<ICommand>();
		newCmds.addAll(Arrays.asList(description.getBuildSpec()));
		newCmds.add(newCmd);
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));
		try {
			project.setDescription(description, null);
		} catch (CoreException e) {
			deployConsole.printError("Unable to set the project description of " + project.getName());  //$NON-NLS-1$
		}
	}

	/**
	 * Trigger the builder for each project
	 */
	private static void triggerDeployBuilder(final Collection<IProject> projects, final boolean forceDeploy) {
		// Schedule jobs to do the first deploy
		for (final IProject project : projects) {
			WorkspaceJob job = new WorkspaceJob("Deploying " + project.getName() + " to server") {  //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					CommonConsole deployConsole = OCSServerUIExternalCore.getDefault().getDeployBuilderConsole();
					IStatus status = PrepareDeploymentFacade.prepareProject(project, forceDeploy);
					if (status != Status.OK_STATUS) {
						deployConsole.printError("Unable to run the builder for " + project.getName());  //$NON-NLS-1$
					}
					if (monitor.isCanceled()) {
						deployConsole.printInfo("Deploy canceled for " + project.getName());  //$NON-NLS-1$
					}
					if (status != Status.OK_STATUS || monitor.isCanceled()) {
						deployConsole.printInfo("Please do a manual clean of the project");  //$NON-NLS-1$
					}
					return Status.OK_STATUS;
				}
			};
	        job.setRule(project);
	        job.setPriority(Job.LONG);
	        job.schedule();
		}
	}

	/**
	 * Prepare all project with a deploy builder to be deployed. This includes:
	 * <ul><li>Rename the existing jar with a .original extension (i.e. otf-core.jar.original</li>
	 * <li>Create a empty folder for the jar (i.e. a otf-core.jar folder)</li></ul>
	 * @throws CoreException
	 * @retrun the operation status
	 */
	private static IStatus prepareProject(IProject project, boolean forceDeploy) throws CoreException {
		IStatus status;
		DeployBuilder deployBuilder = getDeployBuilder(project);
		if (deployBuilder != null) {
			if (!forceDeploy && !deployBuilder.needsPrepareDeploymentDestinations()) {
				return Status.OK_STATUS;
			}
			status = deployBuilder.prepareDeploymentDestinations();
			if (status == Status.OK_STATUS) {
				try {
					getDeployBuilder(project).initialBuild();
				} catch (CoreException e) {
					status = new Status(
						IStatus.ERROR,
						ServerCore.PLUGIN_ID,
						"Problems occured during the initial deployment.\n" +
						"Check details for more information.",
						e);
				}
				status = Status.OK_STATUS;
			}
		} else {
			status = new Status(
					IStatus.ERROR,
					ServerCore.PLUGIN_ID,
					"Unable to get the deploy builder associated to this project: " + project.getName());
		}
		return status;
	}

	/**
	 * @param project some project
	 * @return the Deploy builder from the project (null if not present)
	 */
	private static DeployBuilder getDeployBuilder(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			ICommand[] commands = description.getBuildSpec();
			for (ICommand command : commands) {
				if (command.getBuilderName().equals(DeployBuilder.BUILDER_ID)) {
					return new DeployBuilder(project);
				}
			}
		} catch (CoreException e) {
			return null;
		}
		return null;
	}

}

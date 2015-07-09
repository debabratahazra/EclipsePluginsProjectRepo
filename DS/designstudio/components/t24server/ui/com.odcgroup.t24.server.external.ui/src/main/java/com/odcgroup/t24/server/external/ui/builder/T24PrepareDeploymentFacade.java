package com.odcgroup.t24.server.external.ui.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

import com.odcgroup.t24.server.external.ui.T24ServerUIExternalCore;
import com.odcgroup.workbench.core.OfsCore;

public class T24PrepareDeploymentFacade {


	/**
	 * Add the deploy builder to a list of projects
	 */
	public static void makeProjectsDeployable(Collection<IProject> projects) {
		for (IProject project : projects) {
			addNature(project);
			addDeployBuilder(project);
		}
	}

	/**
	 * @param project
	 */
	private static void addNature(IProject project) {
		T24DeployConsole deployConsole = T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();
		if (project.isOpen()) {
			try {
				OfsCore.addNature(project, T24ServerUIExternalCore.NATURE_ID);
			} catch (CoreException e) {
				deployConsole.printError("Cannot add the deploy nature to " + project.getName() + " because " + e.getMessage());  
				return;
			}
		} else {
			deployConsole.printWarning("Cannot add the deploy builder to " + project.getName() + " because it is a closed project");  
		}
	}

	/**
	 * Add the deploy builder to a project
	 */
	private static void addDeployBuilder(IProject project) {
		T24DeployConsole deployConsole = T24ServerUIExternalCore.getDefault().getDeployBuilderConsole();

		// Cannot modify closed projects
		if (!project.isOpen()) {
			deployConsole.printWarning("Cannot add the deploy builder to " + project.getName() + " because it is a closed project");  
			return;
		}

		// Get the description
		IProjectDescription description;
		try {
			description = project.getDescription();
		} catch (CoreException e) {
			deployConsole.printError("Unable to get project description of " + project.getName());  
			return;
		}

		// Builder already associated to the project ?
		for (ICommand cmd : description.getBuildSpec()) {
			if (cmd.getBuilderName().equals(T24DeployBuilder.BUILDER_ID)) {
				return;
			}
		}

		// Associate builder with the project
		ICommand newCmd = description.newCommand();
		newCmd.setBuilderName(T24DeployBuilder.BUILDER_ID);
		List<ICommand> newCmds = new ArrayList<ICommand>();
		newCmds.addAll(Arrays.asList(description.getBuildSpec()));
		newCmds.add(newCmd);
		description.setBuildSpec(newCmds.toArray(new ICommand[newCmds.size()]));
		try {
			project.setDescription(description, null);
		} catch (CoreException e) {
			deployConsole.printError("Unable to set the project description of " + project.getName()); 
		}
	}

}

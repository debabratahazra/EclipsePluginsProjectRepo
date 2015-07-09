package com.odcgroup.ocs.support.ui.m2eclipse.migration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IMarkerResolution;

import com.odcgroup.ocs.support.m2eclipse.migration.OldM2EclipseHelper;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

public class OldM2EclipseProblemResolution implements IMarkerResolution {
	
	@Override
	public String getLabel() {
		return "Migrate maven configuration of the project.";
	}

	@Override
	public void run(IMarker marker) {
		final IProject project = (IProject) marker.getResource().getProject();
		migrateMavenConfigurationWithErrorDisplay(project);
		try {
			marker.delete();
		} catch (CoreException e) {
			String type;
			try {
				type = marker.getType();
			} catch (CoreException e2) {
				type = "Unknown";
			} 
			OfsUICore.getDefault().logWarning("Cannot delete marker :" + type, e);
		}

	}

	private void migrateMavenConfigurationWithErrorDisplay(IProject project) {
		try {
			migrateMavenConfiguration(project);
		} catch (CoreException e) {
			ErrorDialog.openError(Display.getDefault().getActiveShell(), 
					"Problem during maven configuration migration", 
					"Unable to update the project description (The .project or .classpath file might be read-only.)", 
					e.getStatus());
		}
	}

	private void migrateMavenConfiguration(IProject project)
			throws CoreException {
		IProjectDescription description = project.getDescription();
		List<String> natureIds = new ArrayList<String>(Arrays.asList(description.getNatureIds()));
		
		boolean natureUpdated = false;
		if (natureIds.contains(OldM2EclipseHelper.OLD_M2ECLIPSE_NATURE)) {
			natureIds.remove(OldM2EclipseHelper.OLD_M2ECLIPSE_NATURE);
			if (!natureIds.contains(OfsCore.M2ECLIPSE_NATURE)) {
				natureIds.add(OfsCore.M2ECLIPSE_NATURE);
			}
			description.setNatureIds(natureIds.toArray(new String[natureIds.size()]));
			natureUpdated = true;
		}
		
		List<ICommand> buildSpec = new ArrayList<ICommand>(Arrays.asList(description.getBuildSpec()));
		boolean buildSpecUpdated = false;
		for (ICommand command: description.getBuildSpec()) {
			if (command.getBuilderName().equals(OldM2EclipseHelper.OLD_M2ECLIPSE_BUILDER)) {
				buildSpec.remove(command);
				buildSpecUpdated = true;
			}
		}
		if (buildSpecUpdated) {
			boolean newM2EclipseBuilderFound = false;
			for (ICommand command: buildSpec) {
				if (command.getBuilderName().equals(OfsCore.M2ECLIPSE_BUILDER)) {
					newM2EclipseBuilderFound = true;
					break;
				}
			}
			if (!newM2EclipseBuilderFound) {
				ICommand m2eclipseCommand = description.newCommand();
				m2eclipseCommand.setBuilderName(OfsCore.M2ECLIPSE_BUILDER);
				buildSpec.add(m2eclipseCommand);
			}
			description.setBuildSpec(buildSpec.toArray(new ICommand[buildSpec.size()]));
		}
		
		if (natureUpdated || buildSpecUpdated) {
			project.setDescription(description, null);
		}
		
		updateClasspathContainer(project);
	}

	private void updateClasspathContainer(IProject project)
			throws CoreException, JavaModelException {
		IJavaProject javaProject = (IJavaProject)project.getNature(JavaCore.NATURE_ID);
		if (javaProject != null) {
			List<IClasspathEntry> rawClasspath = new ArrayList<IClasspathEntry>(Arrays.asList(javaProject.getRawClasspath()));
			boolean oldM2EclipseCPFound = false;
			for (IClasspathEntry entry : javaProject.getRawClasspath()) {
				if (entry.getPath().toString().equals(OldM2EclipseHelper.OLD_M2ECLIPSE_CLASSPATH)) {
					rawClasspath.remove(entry);
					oldM2EclipseCPFound = true;
				}
			}
			if (oldM2EclipseCPFound) {
				boolean newM2EclipseCPFound = false;
				for (IClasspathEntry entry : rawClasspath) {
					if (entry.getPath().toString().equals(OfsCore.M2ECLIPSE_CLASSPATH_CONTAINER)) {
						newM2EclipseCPFound = true;
					}
				}
				if (!newM2EclipseCPFound) {
					IClasspathEntry m2EclipseCPEntry = JavaCore.newContainerEntry(OfsCore.M2ECLIPSE_CLASSPATH_CONTAINER_PATH);
					rawClasspath.add(m2EclipseCPEntry);
					javaProject.setRawClasspath(rawClasspath.toArray(new IClasspathEntry[rawClasspath.size()]), null);
				}
			}
		}
	}
}

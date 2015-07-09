package com.odcgroup.workbench.generation.ui.init;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.AbstractProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

/**
 * This initializer contains the m2eclipse related initialization to
 * avoid the necessity to have m2eclipse in the generator edition
 * of DesignStudio.<br>
 * Note: this code will have to be moved back to CodeGenInitializer
 * when/if we want to compile gen project during code generation 
 * (by maven).
 * @author yan
 */
public class UICodeGenInitializer extends AbstractProjectInitializer {

	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		MultiStatus status = (MultiStatus) super.checkConfiguration(project, delta);
		
		if (OfsCore.isOfsProject(project)) {
			// DS-3659
			try {
				if (!project.hasNature(OfsCore.M2ECLIPSE_NATURE)) {
					IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
							"Project must have m2eclipse nature.");
					status.add(error);
				}
			} catch (CoreException e) {
			}

			try {
				IProjectDescription desc = project.getDescription();
				ICommand[] commands = desc.getBuildSpec();
				boolean hasMavenBuilder = false;
				for(ICommand command : commands) {
					if(command.getBuilderName().equals(OfsCore.M2ECLIPSE_BUILDER)) {
						hasMavenBuilder = true;
						break;
					}
				}
				
				if (!hasMavenBuilder) {
					IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
							"Project must have Maven builder.");
						status.add(error);
				}
			} catch (CoreException e) {
			}
		}
		
		if(GenerationCore.isTechnical(project)) {
			// check for other configuration
			for(String projectName : GenerationCore.getJavaProjectNames(project)) {
				IProject outputProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
				if(outputProject.exists()) {
					// check maven classpath provider
					IStatus mavenStatus = checkOdysseyClasspath(outputProject);
					if (!mavenStatus.isOK()) {
						status.addAll(mavenStatus);
					}
				}
			}
		}
		
		// models project need a classpath container too
		IStatus mavenStatus = checkOdysseyClasspath(project);
		if (!mavenStatus.isOK()) {
			status.addAll(mavenStatus);
		}
		
		return status;
	}
	
	private IStatus checkOdysseyClasspath(IProject project) {
		MultiStatus status = new MultiStatus(GenerationCore.PLUGIN_ID, IStatus.OK, "ok", null);
		
		IJavaProject javaProject = JavaCore.create(project);
		try {
			boolean m2eclipseContainerFound = false;
			for (IClasspathEntry cp : javaProject.getRawClasspath()) {
				if (cp.getPath().equals(OfsCore.M2ECLIPSE_CLASSPATH_CONTAINER_PATH)) {
					m2eclipseContainerFound = true;
					break;
				}
			}
			if (!m2eclipseContainerFound) {
				status.add(new Status(IStatus.ERROR, GenerationCore.PLUGIN_ID, 
						project.getName() + " project has no Maven Dependencies classpath."));
			}
		} catch (JavaModelException e) {
			// donothing
		}
		return status;
	}
	

	@Override
	public void updateConfiguration(final IProject project, IProgressMonitor monitor) throws CoreException {
		if (OfsCore.isOfsProject(project)) {
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IProjectDescription desc = project.getDescription();
					IProjectDescription newDescNature = addMavenNature(desc);
					if (newDescNature != null) {
						project.setDescription(newDescNature, monitor);
					}
					GenerationCore.createSourceFolder(project, project.getFolder("src"));
					addMavenClasspathContainer(project, monitor);
					
					IClasspathEntry jreEntry = JavaCore.newContainerEntry(new Path("org.eclipse.jdt.launching.JRE_CONTAINER"), false);	
					GenerationCore.addClasspathEntry(JavaCore.create(project), jreEntry);
				}
			};
			ResourcesPlugin.getWorkspace().run(runnable, monitor);
			runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IProjectDescription desc = project.getDescription();
					IProjectDescription newDescBuilder = addMavenBuilder(desc);
					if (newDescBuilder != null) {
						project.setDescription(newDescBuilder, monitor);
					}
				}
			};
			ResourcesPlugin.getWorkspace().run(runnable, monitor);
		}

		if (GenerationCore.isTechnical(project)) {
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					for (String genProjectName : GenerationCore.getJavaProjectNames(project)) {
						IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
						
						// The gen project exists only with a technical nature, but doesn't if 
						// it is a sketching project
						if (genProject.exists()) {
							IProjectDescription desc = genProject.getDescription();
							IProjectDescription newDescNature = addMavenNature(desc);
							if (newDescNature != null) {
								desc = newDescNature;
							}
							IProjectDescription newDescBuilder = addMavenBuilder(desc);
							if (newDescBuilder != null) {
								desc = newDescBuilder;
							}
							if (newDescNature!=null || newDescBuilder!=null) {
								genProject.setDescription(desc, monitor);
							}
							
							addMavenClasspathContainer(genProject, monitor);
						}
					}
				}
			};
			ResourcesPlugin.getWorkspace().run(runnable, monitor);
		}
	}

	private IProjectDescription addMavenNature(IProjectDescription desc) {
		ArrayList<String> natureIds = new ArrayList<String>(Arrays.asList(desc.getNatureIds()));
		if(!natureIds.contains(OfsCore.M2ECLIPSE_NATURE)) {
			natureIds.add(OfsCore.M2ECLIPSE_NATURE);
			desc.setNatureIds(natureIds.toArray(new String[0]));
			return desc;
		}
		return null;
	}
	
	private IProjectDescription addMavenBuilder(IProjectDescription desc) {
		ICommand[] commands = desc.getBuildSpec();
		boolean builderFound = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(OfsCore.M2ECLIPSE_BUILDER)) {
				builderFound = true;
			}
		}
		if (!builderFound) {
			ArrayList<ICommand> commandsList = new ArrayList<ICommand>(Arrays.asList(commands));
			ICommand command = desc.newCommand();
			command.setBuilderName(OfsCore.M2ECLIPSE_BUILDER);
			commandsList.add(command);
			desc.setBuildSpec(commandsList.toArray(new ICommand[commandsList.size()]));
			return desc;
		} else {
			return null;
		}
	}
	
	private IProjectDescription removeMavenBuilder(IProjectDescription desc) {
		ICommand[] commands = desc.getBuildSpec();
		ArrayList<ICommand> commandsList = new ArrayList<ICommand>(Arrays.asList(commands));
		boolean builderFound = false;
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(OfsCore.M2ECLIPSE_BUILDER)) {
				commandsList.remove(commands[i]);
				builderFound = true;
			}
		}
		if (builderFound) {
			desc.setBuildSpec(commandsList.toArray(new ICommand[commandsList.size()]));
			return desc;
		}
		return null;
	}
	
	private void addMavenClasspathContainer(IProject project, IProgressMonitor monitor) throws CoreException {
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry entry = JavaCore.newContainerEntry(OfsCore.M2ECLIPSE_CLASSPATH_CONTAINER_PATH);
		GenerationCore.addClasspathEntry(javaProject, entry);
	}

}

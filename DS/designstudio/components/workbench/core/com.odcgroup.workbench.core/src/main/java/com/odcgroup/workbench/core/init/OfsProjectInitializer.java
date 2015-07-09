package com.odcgroup.workbench.core.init;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.maven.PomTemplatesHelper;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;

public class OfsProjectInitializer extends AbstractProjectInitializer {

	public void updateConfiguration(final IProject project,
			IProgressMonitor monitor) throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor monitor) throws CoreException {
				updatePomFile(project);

				// set Odyssey nature
				IProjectDescription description = project.getDescription();
				List<String> natureIds = (List<String>) new ArrayList(Arrays
						.asList(description.getNatureIds()));
				if (!natureIds.contains(OfsCore.NATURE_ID))
					natureIds.add(0, OfsCore.NATURE_ID);

				// we remove nature that we needed in previous releases, but
				// which are obsolete now
				if (natureIds.contains("org.eclipse.pde.PluginNature"))
					natureIds.remove("org.eclipse.pde.PluginNature");

				// add the Java nature/builder
				if (!natureIds.contains("org.eclipse.jdt.core.javanature"))
					natureIds.add("org.eclipse.jdt.core.javanature");
				OfsCore.addProjectBuilder(project, "org.eclipse.jdt.core.javabuilder");
				
				description.setNatureIds(natureIds.toArray(new String[natureIds
						.size()]));
				project.setDescription(description, null);

				// remove the PDE builders
				OfsCore.removeProjectBuilder(project,
						"org.eclipse.pde.SchemaBuilder");
				OfsCore.removeProjectBuilder(project,
						"org.eclipse.pde.ManifestBuilder");

				// remove the META-INF folder if it exists
				IFolder metainfFolder = project.getFolder("META-INF");
				if (metainfFolder.exists())
					metainfFolder.delete(true, monitor);

				if (!ofsBuilderIsFirst(project)) {
					OfsCore.removeProjectBuilder(project, OfsCore.BUILDER_ID);
					OfsCore.addProjectBuilderAtTop(project, OfsCore.BUILDER_ID);
				}

			}
		};
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			ResourcesPlugin.getWorkspace().run(runnable, monitor);
		} else {
			// Just in case this is run from a non-OSGi/SE unit test..
			runnable.run(monitor);
		}
		updateProjectClassPathEntries(project);
	}
	
	/**
	 * method that cleans up unnecessary classpath entries
	 * 
	 * @param project
	 * @throws CoreException
	 */
	private void updateProjectClassPathEntries(final IProject project) throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {			
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				IJavaProject javaProject = JavaCore.create(project);
				// check if the java project exists
				if (javaProject.exists()) {
			         List<IClasspathEntry> newEntries = new ArrayList<IClasspathEntry>();
			         javaProject.setRawClasspath(newEntries.toArray(new IClasspathEntry[0]),
			                 new NullProgressMonitor());
			 		updateOutputFolder(project);
				}
				project.refreshLocal(0, new NullProgressMonitor());				
			}
		};
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			ResourcesPlugin.getWorkspace().run(runnable, new NullProgressMonitor());
		} else {
			// Just in case this is run from a non-OSGi/SE unit test..
			runnable.run(new NullProgressMonitor());
		}
	}

	private boolean ofsBuilderIsFirst(IProject project) {
		try {
			IProjectDescription desc = project.getDescription();
			ICommand[] commands = desc.getBuildSpec();

			if(commands.length > 0 && commands[0].getBuilderName().equals(OfsCore.BUILDER_ID)) {
				return true;
			} else {
				return false;
			}
		} catch (CoreException e) {
			return false;
		}
	}

	public IStatus checkConfiguration(IProject project, IResourceDelta delta) {
		MultiStatus status = (MultiStatus) super.checkConfiguration(project, delta);

		// DS-7902: Only do the check which enforces that DS Builder must be first if we're on a DS TAP models project,
		// as it was introduced for VR in DS-3526.
		IFolder modulesFolder = project.getFolder("rule");
		if (modulesFolder.exists()) {
			if(!ofsBuilderIsFirst(project)) {
				status
				.add(new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
						"Design Studio builder must be the first builder in the list."));
			}
		}

		// OCS-23973: check for spaces in the project name
		if (project.getName().contains(" ")) {
			IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
					"Project name must not contain spaces.");
			status.add(error);
		}

		try {
			if (project.hasNature("org.eclipse.pde.PluginNature")) {
				IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
						"Project must not have  PDE nature.");
				status.add(error);
			}
		} catch (CoreException e) {
		}
		
		
		try {
			if (!project.hasNature("org.eclipse.jdt.core.javanature")) {
				IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
						"Project must have Java nature.");
				status.add(error);
			}
		} catch (CoreException e) {
		}
		
		if (!project.getFile("pom.xml").exists()) {
			IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
					"pom.xml file is missing in project!");
			status.add(error);
		}
		
		try {
			if (project.hasNature("org.eclipse.jdt.core.javanature")) {
				IJavaProject javaProject = JavaCore.create(project);
				String outputPath = javaProject.getOutputLocation().toOSString();
				if(!outputPath.isEmpty() && !outputPath.endsWith("target")) {
					IStatus error = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
							"Project output folder should be target.");
					status.add(error);
				}
			}
		} catch (CoreException e) {
		}
		return status;
	}
	
	private void updateOutputFolder(IProject project) throws CoreException {
		IProgressMonitor pm = new NullProgressMonitor();
		project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		IFolder binFolder = project.getFolder("bin");
		if(binFolder.exists()) { 
			binFolder.delete(true, pm);
		}
		IFolder targetFolder = project.getFolder("target");
		if(!targetFolder.exists()) { 
			targetFolder.create(true, true, pm);
		}
		IJavaProject javaProject = JavaCore.create(project);
		javaProject.setOutputLocation(targetFolder.getFullPath(), pm);
	}

	private void updatePomFile(IProject project) throws CoreException {
		IFile pomFile = project.getFile("pom.xml");

		// create a new pom.xml if none exists
		if (!pomFile.exists()) {
			String pomContent;
			pomContent = getModelPomXml();
			
			if (pomContent == null) {
				IStatus status = new Status(IStatus.ERROR, OfsCore.PLUGIN_ID,
						"Error creating pom.xml for project "
								+ project.getName());
				throw new CoreException(status);
			}
			
			pomContent = StringUtils.replace(pomContent, "${artifactId}",
					project.getName());
			pomContent = StringUtils.replace(pomContent, "${ds.target.platform.version}",
					TargetPlatform.getTechnicalVersion());

			InputStream transformedIs = new ByteArrayInputStream(pomContent.getBytes());
			pomFile.create(transformedIs, IFile.FORCE, null);
			pomFile.refreshLocal(IResource.DEPTH_ONE, null);
		}
	}

	private String getModelPomXml() throws CoreException {
		return PomTemplatesHelper.getPomTemplatesProvider().getModelsPomTemplate();
	}
}

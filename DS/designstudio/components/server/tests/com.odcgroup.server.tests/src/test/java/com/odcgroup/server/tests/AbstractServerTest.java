package com.odcgroup.server.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public abstract class AbstractServerTest extends AbstractDSUnitTest {

	protected IJavaProject createJavaProject(String projectName, String... additionalNatures) throws CoreException {
		IProject project = createProject(projectName, additionalNatures);
		
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry sourceEntry = JavaCore.newSourceEntry(new Path("//" + project.getName() + "//src"));
		javaProject.setRawClasspath(new IClasspathEntry[] {sourceEntry}, null);
		return javaProject;
	}
	
	protected IProject createProject(String projectName, String... additionalNatures) throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();

		String[] newNatures = new String[natures.length + additionalNatures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		System.arraycopy(additionalNatures, 0, newNatures, natures.length, additionalNatures.length);
		newNatures[newNatures.length-1] = JavaCore.NATURE_ID;
		description.setNatureIds(newNatures);
		project.setDescription(description, null);

		return project;
	}
	
}

package com.odcgroup.workbench.generation.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.init.OfsProjectInitializer;
import com.odcgroup.workbench.generation.GenerationCore;

public class GenerationCoreCreateTest {

	IProject project;

	@Before
	public void setUp() throws Exception {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		project = workspace.getRoot().getProject(this.getClass().getName());
		project.create(null);
		project.open(null);
		new OfsProjectInitializer().updateConfiguration(project, null);
		OfsCore.addNature(project, JavaCore.NATURE_ID);
	}

	@After
	public void tearDown() throws Exception {
		project.delete(true, null);
	}

	@Test
	public void testCreateSourceFolderIProjectIFolder() throws CoreException {
		IFolder folder = project.getFolder("src");
		GenerationCore.createSourceFolder(project, folder);
		Assert.assertTrue(GenerationCore.isJavaSourceFolder(folder));
	}

	@Test
	public void testCreateSourceFolderIProjectIFolderIPath() throws CoreException {
		IFolder folder = project.getFolder("src");
		IFolder outputFolder = project.getFolder("output");
		outputFolder.create(true, true, null);
		IPath outputPath = outputFolder.getFullPath();
		GenerationCore.createSourceFolder(project, folder, outputPath);
		Assert.assertTrue(GenerationCore.isJavaSourceFolder(folder));
		Assert.assertEquals(outputPath, getOutputPath(project, folder));
	}

	@Test
	public void testCreateSourceFolders() throws CoreException {
		IFolder[] folders = { project.getFolder("src1"), project.getFolder("src2"), project.getFolder("src3") };
		IFolder outputFolder = project.getFolder("output");
		outputFolder.create(true, true, null);
		IPath outputPath = outputFolder.getFullPath();
		GenerationCore.createSourceFolders(project, folders, outputPath);
		for (IFolder folder : folders) {
			Assert.assertTrue(GenerationCore.isJavaSourceFolder(folder));
			Assert.assertEquals(outputPath, getOutputPath(project, folder));
		}
	}

	@Test
	public void testAddClasspathEntries() throws CoreException {
		IFolder folder1 = project.getFolder("src1");
		IFolder folder2 = project.getFolder("src2");
		folder1.create(true, true, null);
		folder2.create(true, true, null);
		IClasspathEntry[] cpEntries = { JavaCore.newSourceEntry(folder1.getFullPath()),
				JavaCore.newSourceEntry(folder2.getFullPath()) };
		GenerationCore.addClasspathEntries(JavaCore.create(project), cpEntries);
		Assert.assertTrue(GenerationCore.isJavaSourceFolder(folder1));
		Assert.assertTrue(GenerationCore.isJavaSourceFolder(folder2));
	}

	@Test
	public void testAddClasspathEntry() throws CoreException {
		IFolder folder = project.getFolder("src");
		folder.create(true, true, null);
		IClasspathEntry cpEntry = JavaCore.newSourceEntry(folder.getFullPath());
		GenerationCore.addClasspathEntry(JavaCore.create(project), cpEntry);
		Assert.assertTrue(GenerationCore.isJavaSourceFolder(folder));
	}
	
	@Test
	public void testAddClasspathEntryWhenClasspathContainsEntryWithSamePathAndDifferentOutput() throws Exception {
		// given
		IFolder folder1 = project.getFolder("src1");
		IFolder folder2 = project.getFolder("src2");
		folder1.create(true, true, null);
		folder2.create(true, true, null);
		IFolder output = project.getFolder("output");
		output.create(true, true, null);
		IClasspathEntry[] cpEntriesArray = {JavaCore.newSourceEntry(folder1.getFullPath()),
		JavaCore.newSourceEntry(folder2.getFullPath())};
		
		IJavaProject javaProject = JavaCore.create(project);
		javaProject.setRawClasspath(new IClasspathEntry[]{}, null);
		IClasspathEntry newEntry = JavaCore.newSourceEntry(folder1.getFullPath(), null, output.getFullPath());
		// when
		GenerationCore.addClasspathEntries(javaProject, cpEntriesArray);
		GenerationCore.addClasspathEntry(javaProject, newEntry);
		// then
		IClasspathEntry[] classpathEntriesAfter = javaProject.getRawClasspath();
		Assert.assertEquals(2, classpathEntriesAfter.length);
		Assert.assertTrue(Arrays.asList(classpathEntriesAfter).contains(newEntry));
	}

	private IPath getOutputPath(IProject project, IFolder folder) throws JavaModelException {
		List<IClasspathEntry> cp = new ArrayList<IClasspathEntry>(Arrays.asList(JavaCore.create(project)
				.getRawClasspath()));
		for (IClasspathEntry cpEntry : cp) {
			if (cpEntry.getPath().equals(folder.getFullPath())) {
				return cpEntry.getOutputLocation();
			}
		}
		Assert.fail(folder.getName() + " cannot be found on classpath of project " + project.getName());
		return null;
	}
}

package com.odcgroup.workbench.generation.init.classpath;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.init.CodeGenInitializer;

/**
 * @author amc
 */
public class OutputPathCheckerTest {

	
	private static final String PROJECT_NAME = "OutputPathInitializerTest-project";

	private static final String SRC = "/src";
	private static final String TARGET = "/target";
	private static final String CLASSPATH_ENTRY_SRC = "/"+PROJECT_NAME+SRC+"/ClassPathEntryOne";
	private static final String CLASSPATH_ENTRY_SRC_TWO = "/"+PROJECT_NAME+SRC+"/ClassPathEntryTwo";
	private static final String CLASSPATH_OUTPUT_CORRECT = "/"+PROJECT_NAME+TARGET+"/ClassPathEntryOne";
	private static final String CLASSPATH_OUTPUT_INCORRECT = "/"+PROJECT_NAME+TARGET+"/classes";
	
	private static final String CLASSPATH_ENTRY_SRC_LONG = "/"+PROJECT_NAME+SRC+"/a/b/c/d";
	private static final String CLASSPATH_OUTPUT_CORRECT_LONG = "/"+PROJECT_NAME+TARGET+"/a/b/c/d";
	
	
	private IProject project;
	private IJavaProject javaProject;
	private OutputPathChecker checker;
	
	@Before
	public void setUp() throws Exception {
		checker = new OutputPathChecker();
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJECT_NAME);
		project.create(null);
		project.open(null);
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[]{JavaCore.NATURE_ID});
		project.setDescription(description, null);
		
		javaProject = JavaCore.create(project);
		
		// Set the default output
		IFolder defaultTargetFolder = project.getFolder(CodeGenInitializer.TARGET_CLASSES);
		OfsCore.createFolder(defaultTargetFolder);
		javaProject.setOutputLocation(defaultTargetFolder.getFullPath(), null);
		
		clearClasspath();
	}

	public void clearClasspath() throws JavaModelException {
		javaProject.setRawClasspath(new IClasspathEntry[]{}, null);
	}

	@After
	public void tearDown() throws Exception {
		project.delete(true, null);
	}

	@Test
	public void testCheckWhenClasspathContainsSrcEntryWithSeparateOutputPathReturnsError() throws Exception {
		// given
		addSrcClasspathEntryWithCorrectOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC);
		// when
		IStatus status = checker.check(javaProject, pathsToCheck);
		// then
		Assert.assertFalse(status.isOK());
		Assert.assertEquals("Separate output folder per source folder is forbidden", "Source folder must not use separate output folder per source folder (m2eclipse limitation): OutputPathInitializerTest-project(source entry: /OutputPathInitializerTest-project/src/ClassPathEntryOne; ouput path: /OutputPathInitializerTest-project/target/ClassPathEntryOne)", status.getChildren()[0].getMessage());
	}
	
	@Test
	public void testCheckWhenClasspathContainsLongSrcEntryWithLongOutputReturnsError() throws Exception {
		// given
		addLongSrcClasspathEntryWithLongOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC_LONG);
		// when
		IStatus status = checker.check(javaProject, pathsToCheck);
		// then
		Assert.assertFalse(status.isOK());
		Assert.assertEquals("Separate output folder per source folder is forbidden", "Source folder must not use separate output folder per source folder (m2eclipse limitation): OutputPathInitializerTest-project(source entry: /OutputPathInitializerTest-project/src/a/b/c/d; ouput path: /OutputPathInitializerTest-project/target/a/b/c/d)", status.getChildren()[0].getMessage());
	}
	
	@Test
	public void testWhenClasspathEntryIsIncorrectButNotInPathsToCheckReturnsOK() throws Exception {
		// given
		addSrcClasspathEntryWithoutOutputPath();
		// when
		IStatus status = checker.check(javaProject, null);
		// then
		assertStatusOK(status);
	}

	public void assertStatusOK(IStatus status) {
		Assert.assertEquals(IStatus.OK, status.getSeverity());
		IStatus[] children = status.getChildren();
		Assert.assertEquals(0, children.length);
	}

	@Test
	public void testWhenClasspathEntryHasNoOutputPathsSetThatCheckReturnsOk() throws Exception {
		addSrcClasspathEntryWithoutOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC);
		IStatus status = checker.check(javaProject, pathsToCheck);
		//then
		assertStatusOK(status);
	}

	@Test
	public void testCheckWhenClasspathContainsSrcEntryWithInorrectOutputPathReturnsError() throws Exception {
		// given
		addSrcEntryWithIncorrectOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC);
		// when
		IStatus status = checker.check(javaProject, pathsToCheck);
		// then
		Assert.assertTrue(status.isMultiStatus());
		IStatus[] children = status.getChildren();
		Assert.assertEquals(1, children.length);
		IStatus childStatus = children[0];
		Assert.assertEquals(IStatus.ERROR, childStatus.getSeverity());
		Assert.assertEquals(GenerationCore.PLUGIN_ID, childStatus.getPlugin());
		final String actualMessage = childStatus.getMessage();
		Assert.assertTrue(actualMessage.contains(PROJECT_NAME));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_ENTRY_SRC));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_OUTPUT_INCORRECT));
	}
	
	@Test
	public void testCheckWhenClasspathContainsLongSrcEntryWithOutputPathReturnsError() throws Exception {
		// given
		addSrcEntryWithIncorrectOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC);
		// when
		IStatus status = checker.check(javaProject, pathsToCheck);
		// then
		Assert.assertTrue(status.isMultiStatus());
		IStatus[] children = status.getChildren();
		Assert.assertEquals(1, children.length);
		IStatus childStatus = children[0];
		Assert.assertEquals(IStatus.ERROR, childStatus.getSeverity());
		Assert.assertEquals(GenerationCore.PLUGIN_ID, childStatus.getPlugin());
		final String actualMessage = childStatus.getMessage();
		Assert.assertTrue(actualMessage.contains(PROJECT_NAME));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_ENTRY_SRC));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_OUTPUT_INCORRECT));
	}
	
	@Test
	public void testCheckWhenClasspathContainsMultipleIncorrectOutputPathsReturnsMultipleErrors() throws Exception {
		// given
		addSrcEntryWithIncorrectOutputPath();
		addSrcEntryTwoWithIncorrectOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC, CLASSPATH_ENTRY_SRC_TWO);
		// when
		IStatus status = checker.check(javaProject, pathsToCheck);
		// then
		Assert.assertTrue(status.isMultiStatus());
		IStatus[] children = status.getChildren();
		Assert.assertEquals(2, children.length);
		
		IStatus childStatus1 = children[0];
		Assert.assertEquals(IStatus.ERROR, childStatus1.getSeverity());
		Assert.assertEquals(GenerationCore.PLUGIN_ID, childStatus1.getPlugin());
		final String actualMessage = childStatus1.getMessage();
		Assert.assertTrue(actualMessage.contains(PROJECT_NAME));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_ENTRY_SRC));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_OUTPUT_INCORRECT));
		
		IStatus childStatus2 = children[1];
		Assert.assertEquals(IStatus.ERROR, childStatus2.getSeverity());
		Assert.assertEquals(GenerationCore.PLUGIN_ID, childStatus2.getPlugin());
		final String actualMessage2 = childStatus2.getMessage();
		Assert.assertTrue(actualMessage2.contains(PROJECT_NAME));
		Assert.assertTrue(actualMessage2.contains(CLASSPATH_ENTRY_SRC_TWO));
		Assert.assertTrue(actualMessage2.contains(CLASSPATH_OUTPUT_INCORRECT));
	}
	
	@Test
	public void testCheckRemovesIncorrectClasspathInPathsToCheckAndIgnoresClasspathNotInPathsToCheck() throws Exception {
		// given
		addSrcEntryWithIncorrectOutputPath();
		addSrcEntryTwoWithIncorrectOutputPath();
		List<String> pathsToCheck = Arrays.asList(CLASSPATH_ENTRY_SRC_TWO);
		// when
		IStatus status = checker.check(javaProject, pathsToCheck);
		// then
		Assert.assertTrue(status.isMultiStatus());
		IStatus[] children = status.getChildren();
		Assert.assertEquals(1, children.length);
		
		IStatus childStatus1 = children[0];
		Assert.assertEquals(IStatus.ERROR, childStatus1.getSeverity());
		Assert.assertEquals(GenerationCore.PLUGIN_ID, childStatus1.getPlugin());
		final String actualMessage = childStatus1.getMessage();
		Assert.assertTrue(actualMessage.contains(PROJECT_NAME));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_ENTRY_SRC_TWO));
		Assert.assertTrue(actualMessage.contains(CLASSPATH_OUTPUT_INCORRECT));
	}
	
	public void addSrcClasspathEntryWithoutOutputPath() throws JavaModelException {
		IClasspathEntry classpathEntry = JavaCore.newSourceEntry(new Path(CLASSPATH_ENTRY_SRC));
		GenerationCore.addClasspathEntry(javaProject, classpathEntry);
	}

	private void addSrcClasspathEntryWithCorrectOutputPath() throws JavaModelException {
		IClasspathEntry classpathEntry = JavaCore.newSourceEntry(new Path(CLASSPATH_ENTRY_SRC), null, new Path(CLASSPATH_OUTPUT_CORRECT));
		GenerationCore.addClasspathEntry(javaProject, classpathEntry);
	}

	private void addSrcEntryWithIncorrectOutputPath() throws JavaModelException {
		IClasspathEntry classpathEntry = JavaCore.newSourceEntry(new Path(CLASSPATH_ENTRY_SRC), null, new Path(CLASSPATH_OUTPUT_INCORRECT));
		GenerationCore.addClasspathEntry(javaProject, classpathEntry);
	}
	
	private void addSrcEntryTwoWithIncorrectOutputPath() throws JavaModelException {
		IClasspathEntry classpathEntry = JavaCore.newSourceEntry(new Path(CLASSPATH_ENTRY_SRC_TWO), null, new Path(CLASSPATH_OUTPUT_INCORRECT));
		GenerationCore.addClasspathEntry(javaProject, classpathEntry);
	}

	private void addLongSrcClasspathEntryWithLongOutputPath() throws JavaModelException {
		IClasspathEntry classpathEntry = JavaCore.newSourceEntry(new Path(CLASSPATH_ENTRY_SRC_LONG), null, new Path(CLASSPATH_OUTPUT_CORRECT_LONG));
		GenerationCore.addClasspathEntry(javaProject, classpathEntry);
	}

	@Test
	public void testDefaultOutputFolderIsOk() throws JavaModelException {
		// Given
		javaProject.setOutputLocation(new Path("/" + project.getName() + "/target/classes"), null);
		// When
		IStatus status = new OutputPathChecker().check(javaProject, null);
		// Then
		assertStatusOK(status);
	}

	@Test
	public void testDefaultOutputFolderOk() throws JavaModelException {
		// Given
		// ... the output folder is properly configured 
		// When
		IStatus status = new OutputPathChecker().check(javaProject, null);
		// Then
		Assert.assertTrue(status.isOK());
	}

}

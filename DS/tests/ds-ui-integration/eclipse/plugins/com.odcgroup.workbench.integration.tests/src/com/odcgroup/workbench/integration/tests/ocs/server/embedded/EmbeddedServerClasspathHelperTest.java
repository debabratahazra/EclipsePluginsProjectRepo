package com.odcgroup.workbench.integration.tests.ocs.server.embedded;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.ocs.server.embedded.model.IEmbeddedServer;
import com.odcgroup.ocs.server.embedded.model.internal.EmbeddedServer;
import com.odcgroup.ocs.server.embedded.nature.EmbeddedServerNature;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerClasspathHelper;
import com.odcgroup.ocs.server.embedded.util.EmbeddedServerManager;
import com.odcgroup.server.model.IDSProject;
import com.odcgroup.server.model.impl.DSProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.targetplatform.TargetPlatform;
import com.odcgroup.workbench.integration.tests.AbstractSWTBotTest;
import com.odcgroup.workbench.integration.tests.Activator;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EmbeddedServerClasspathHelperTest extends AbstractSWTBotTest {

	private static final String RESOURCES_SERVER = "resources/serverProject/";
	private static final String RESOURCES_SERVER_POM = RESOURCES_SERVER + "pom.xml";
	private static final String RESOURCES_SERVER_CLASSPATH = RESOURCES_SERVER + ".classpath";
	
	private static final String SERVER_PROJECT = "serverProject";
	private static final String JAVA_PROJECT = "javaProject";
	private static final String TEST_MODELS = "test-models";
	private static final String TEST_MODELS_GEN = "test-models-gen";
	
	private IJavaProject serverProject;
	private IProject mavenProject;
	private IProject javaProject;

	@Before
	public void setUp() {
		try {
			javaProject = createJavaProject(JAVA_PROJECT).getProject();
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		
		try {
			serverProject = createJavaProject(SERVER_PROJECT, EmbeddedServerNature.NATURE_ID, OfsCore.M2ECLIPSE_NATURE);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		
		// Configure server project (pom)
		InputStream stream = null; 
		try {
			URL pomUrl = FileLocator.find(Activator.getDefault().getBundle(), new Path(RESOURCES_SERVER_POM), null);
			stream = pomUrl.openStream();
			String pomContent = IOUtils.toString(stream);
			pomContent = StringUtils.replace(pomContent, "${ds.target.platform.version}", TargetPlatform.getTechnicalVersion());
			IFile pomFile = serverProject.getProject().getFile("pom.xml");
			FileUtils.writeStringToFile(pomFile.getLocation().toFile(), pomContent);
			pomFile.refreshLocal(IResource.DEPTH_ONE, null);
			
			URL classpathUrl = FileLocator.find(Activator.getDefault().getBundle(), new Path(RESOURCES_SERVER_CLASSPATH), null);
			stream = classpathUrl.openStream();
			String classpathContent = IOUtils.toString(stream);
			IFile classpathFile = serverProject.getProject().getFile(".classpath");
			FileUtils.writeStringToFile(classpathFile.getLocation().toFile(), classpathContent);
			
			serverProject.getResource().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(stream);
		}

		EmbeddedServerManager.getInstance().reset();
		
		waitForClasspathResolution();
		mavenProject = ResourcesPlugin.getWorkspace().getRoot().getProject(TEST_MODELS_GEN);
	}
	
	@After
	public void tearDown() {
		try {
			if (serverProject != null) {
				serverProject.getProject().delete(true, null);
			}
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		try {
			ResourcesPlugin.getWorkspace().getRoot().getProject(TEST_MODELS).delete(true, null);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		try {
			if (javaProject != null) {
				javaProject.delete(true, null);
			}
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		EmbeddedServerManager.getInstance().reset();
	}
		
	@Test
	public void testGetWorkspaceReferencedProject_mavenDependencies() {
		// Given
		Set<IProject> mavenProjects = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), true, false);
		
		// Then
		Assert.assertEquals(1, mavenProjects.size());
		Assert.assertEquals("The maven project should be referenced", mavenProject, mavenProjects.iterator().next());
	}
	
	@Test
	public void testGetWorkspaceReferencedProject_buildPathDependencies() throws JavaModelException {
		// Given
		IEmbeddedServer embeddedServer = new EmbeddedServer("someId", "someName", serverProject.getProject());
		
		// When
		Set<IProject> javaProjects = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), false, true);
		
		// Then
		Assert.assertEquals(0, javaProjects.size());
		
		// Given
		List<IDSProject> addedProject = new ArrayList<IDSProject>();
		addedProject.add(new DSProject(javaProject));
		EmbeddedServerClasspathHelper.getInstance().updateServerBuildPath(embeddedServer, addedProject, new ArrayList<IDSProject>());
		waitForClasspathResolution();

		// Given
		javaProjects = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), false, true);
		
		// Then
		Assert.assertEquals(1, javaProjects.size());
		Assert.assertEquals("The java project should be referenced", javaProject, javaProjects.iterator().next());
	}
	
	@Test
	public void testGetWorkspaceReferencedProject_InclusionLogic() throws JavaModelException {
		// Given
		IEmbeddedServer embeddedServer = new EmbeddedServer("someId", "someName", serverProject.getProject());
		List<IDSProject> addedProject = new ArrayList<IDSProject>();
		addedProject.add(new DSProject(javaProject));
		List<IDSProject> removedProject = new ArrayList<IDSProject>();
		EmbeddedServerClasspathHelper.getInstance().updateServerBuildPath(embeddedServer, addedProject, removedProject);
		
		waitForClasspathResolution();
		
		// When
		Set<IProject> noReference = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), false, false);
		Set<IProject> onlyContainerReferences = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), true, false);
		Set<IProject> onlyBuildPathReferences = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), false, true);
		Set<IProject> bothReferencesType = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), true, true);
		
		// Then
		Assert.assertEquals("No references should be returned", 0, noReference.size());
		Assert.assertEquals("Only container references should be returned", 1, onlyContainerReferences.size());
		Assert.assertEquals("Only build path references should be returned", 1, onlyBuildPathReferences.size());
		Assert.assertEquals("Both references should be returned", 2, bothReferencesType.size());
		Assert.assertTrue("All container references should be included", bothReferencesType.containsAll(onlyContainerReferences));
		Assert.assertTrue("All build path references should be included", bothReferencesType.containsAll(onlyBuildPathReferences));
	}
	
	@Test
	public void testUpdateServerBuildPath() throws JavaModelException {
		// Given
		IEmbeddedServer embeddedServer = new EmbeddedServer("someId", "someName", serverProject.getProject());
		
		// When
		List<IDSProject> addedProject = new ArrayList<IDSProject>();
		addedProject.add(new DSProject(javaProject));
		List<IDSProject> removedProject = new ArrayList<IDSProject>();
		EmbeddedServerClasspathHelper.getInstance().updateServerBuildPath(embeddedServer, addedProject, removedProject);
		
		waitForClasspathResolution();

		// Then
		Set<IProject> workspaceReferencedProject = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), false, true);
		Assert.assertEquals("Should find 1 referenced project", 1, workspaceReferencedProject.size());
		Assert.assertEquals("Should be the java project", javaProject, workspaceReferencedProject.iterator().next());
		
		// When
		addedProject.clear();
		removedProject.clear();
		removedProject.add(new DSProject(javaProject));
		EmbeddedServerClasspathHelper.getInstance().updateServerBuildPath(embeddedServer, addedProject, removedProject);

		waitForClasspathResolution();

		workspaceReferencedProject = EmbeddedServerClasspathHelper.getInstance().getWorkspaceReferencedProject(serverProject.getProject(), false, true);
		Assert.assertEquals("Shouldn't find referenced project", 0, workspaceReferencedProject.size());
	}

	/**
	 * 
	 */
	private void waitForClasspathResolution() {
		try {
			Job.getJobManager().join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		} catch (OperationCanceledException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	protected IJavaProject createJavaProject(String projectName, String... additionalNatures) throws CoreException {
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
		
		return JavaCore.create(project);
	}
	

}

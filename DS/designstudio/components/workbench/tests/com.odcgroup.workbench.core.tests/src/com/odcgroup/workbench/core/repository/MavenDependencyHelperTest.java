package com.odcgroup.workbench.core.repository;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.jdom.Document;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.repository.maven.MavenDependencyHelper;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MavenDependencyHelperTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws CoreException {
		createModelsProject();
	}

	@After
	public void tearDown() throws CoreException {
		deleteModelsProjects();
	}

	@Test
	public void testReadPom() throws CoreException, IOException {
		// Given a new models project
		
		// When
		Document pom = MavenDependencyHelper.parsePom(getProject().getFile(
				new Path("pom.xml")));
		
		// Then
		Assert.assertEquals("The root element should be the project", "project", pom.getRootElement().getName());
		Assert.assertNotNull("Should have at least an artifact id defined", pom.getRootElement().getChild("artifactId", pom.getRootElement().getNamespace()));
	}

	@Test
	public void testUpdatePom() throws CoreException {
		// Given
		IFile pomFile = getProject().getFile(new Path("pom.xml"));
		Document pom = MavenDependencyHelper.parsePom(pomFile);
		
		// When
		Assert.assertFalse("Shouldn't have the test dependency at first", MavenDependencyHelper.hasDeclaredDependency(pom, "someGroupId", "someArtifactId", "someVersion"));
		MavenDependencyHelper.addDependency(pom, "someGroupId", "someArtifactId", "someVersion");
		MavenDependencyHelper.updatePom(pomFile, pom);
		
		// Then
		Assert.assertTrue("Should have the test dependency by now", MavenDependencyHelper.hasDeclaredDependency(pom, "someGroupId", "someArtifactId", "someVersion"));
	}

}

package com.odcgroup.workbench.generation.tests;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.generation.GenerationCore;

/**
 * @author yan
 */

public class GenerationPomTest extends AbstractDSGenerationTest {

	@Before
	public void setUp() {
		createModelAndGenProject();
	}
	
	@After
	public void tearDown() {
		deleteModelsProjects();
	}

	@Test
	public void testGenPom() throws CoreException, IOException {
		IFile modelsPom = getProject().getFile("pom.xml");
		InputStream is = null;
		try {
			is = modelsPom.getContents();
			String pom = IOUtils.toString(is);
			Assert.assertTrue("Should have a ds-models packaginge", pom.contains("<packaging>ds-models</packaging>"));
		} finally {
			IOUtils.closeQuietly(is);
		}
		
		for (String genProjectName : GenerationCore.getJavaProjectNames(getProject())) {
			IProject genProject = ResourcesPlugin.getWorkspace().getRoot().getProject(genProjectName);
			IFile genPom = genProject.getFile("pom.xml");
			is = null;
			try {
				is = genPom.getContents();
				String pom = IOUtils.toString(is);
				Assert.assertFalse("Should have been replaced", pom.contains("${ds.groupId}"));
				Assert.assertFalse("Should have been replaced", pom.contains("${ds.artifactId}"));
				Assert.assertFalse("Should have been replaced", pom.contains("${ds.models.groupId}"));
				Assert.assertFalse("Should have been replaced", pom.contains("${ds.models.artifactId}"));
				Assert.assertTrue("Should have a depdendence to the models", pom.contains("<type>ds-models</type>"));
				Assert.assertTrue("Should have a ds-generated packaginge", pom.contains("<packaging>ds-generated</packaging>"));
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
	}
}

package com.odcgroup.aaa.generation.udentities;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class SafetyNetUDEGenerationTest extends AbstractDSUnitTest {

	private static final String TEST_CASE_DOMAIN = "ds5287/TestCase.domain";

	@Before
	public void setUp() {
		createModelsProject();
		copyPmsModelsResourceInModelsProject();
		copyResourceInModelsProject("domain/" + TEST_CASE_DOMAIN);
		
		try {
			OfsCore.addNature(getProject(), AAACore.NATURE_ID);
		} catch (CoreException e) {
			throw new RuntimeException("Unable to set the AAA nature to the project", e);
		}
	}

	@After
	public void tearDown() {
		deleteModelsProjects();
	}
	
	@Test
	public void testModelsSafetyNet() throws IOException, ModelNotFoundException, CoreException {
		// Given
		
		// When
		String udeCommands = generateUDE(getProject(), URI.createURI("resource:///" + TEST_CASE_DOMAIN));
		System.out.println(udeCommands);
		
		// Then
		assertEqualsIgnoreREM(readFileFromClasspath("expectedUDESafetyNet.txt"), 
				udeCommands);
	}

	private void assertEqualsIgnoreREM(String expected, String actual) {
		List<String> actualSignificantLines = keepSignificantLines(actual);
		List<String> expectedSignificantLines = keepSignificantLines(expected);
		
		if (actualSignificantLines.size() != expectedSignificantLines.size()) {
			Assert.assertEquals("Number of line of significant lines are different " +
					"(expected:" + expectedSignificantLines.size() + ", " +
					"actual:" + actualSignificantLines.size() + ")", 
					StringUtils.join(expectedSignificantLines.iterator(), "\n"),
					StringUtils.join(actualSignificantLines.iterator(), "\n"));
		}
		for (int i=0; i<actualSignificantLines.size(); i++) {
			Assert.assertEquals("The generated line doesn't match the expected one", expectedSignificantLines.get(i), actualSignificantLines.get(i));
		}
	}

	private List<String> keepSignificantLines(String contents) {
		List<String> result = new ArrayList<String>();
		for (String line : contents.split("\\n")) {
			line = StringUtils.removeEnd(line, "\r");
			if (line.startsWith("REM") || line.isEmpty()) 
				continue;
			result.add(line);
		}
		return result;
	}

	private String generateUDE(IProject project, URI modelURI) throws IOException, ModelNotFoundException, CoreException {
		// Prepare project
		final String UDE_OUTPUT_FOLDER = "generated-ude";
		project.getFolder(UDE_OUTPUT_FOLDER).create(true, true, null);

		// Generate ude
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(
				modelURI);

		List<IStatus> nonOkStatus = new ArrayList<IStatus>();
		new UDEntitiesCodeGenerator().doRun(project, 
				project.getFolder(UDE_OUTPUT_FOLDER), 
				Arrays.<IOfsModelResource>asList(new IOfsModelResource[]{modelResource}), nonOkStatus);

		// Retrieve the generated file
		File dir = project.getFolder(UDE_OUTPUT_FOLDER).getLocation()
				.toFile();
		Assert.assertTrue("The generation folder is not available", dir.exists()
				&& dir.isDirectory());

		final String GENERATED_IMPORT_FILE = UDEntitiesCodeGenerator.getGeneratedImportFile(project);
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.equals(GENERATED_IMPORT_FILE);
			}
		};
		Assert.assertEquals("There should be one file generated", 1, dir.listFiles(filter).length);
		return FileUtils.readFileToString(dir.listFiles(filter)[0]);
	}

}

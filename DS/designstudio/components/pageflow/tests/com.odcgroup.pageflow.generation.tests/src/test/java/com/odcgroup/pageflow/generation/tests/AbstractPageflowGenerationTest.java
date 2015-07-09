package com.odcgroup.pageflow.generation.tests;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.monitor.NullProgressMonitor;
import org.junit.Assert;
import org.xml.sax.SAXException;

import com.odcgroup.pageflow.generation.PageflowMerger;
import com.odcgroup.pageflow.model.Pageflow;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.InvalidMetamodelVersionException;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class AbstractPageflowGenerationTest extends AbstractDSUnitTest {

	public AbstractPageflowGenerationTest() {
	}
	
	protected void assertTransformation(URI modelUri, String expectedXml,
			final String... skippedXPaths) throws ModelNotFoundException,
			IOException, InvalidMetamodelVersionException, CoreException,
			SAXException {
		assertTransformation("Unexpected difference with the generation of "
				+ modelUri, modelUri, expectedXml, skippedXPaths);
	}

	protected void assertTransformation(String message, URI modelUri,
			String expectedXml, final String... skippedXPaths)
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException, CoreException, SAXException {
		String generatedXml = createXmlFromUri(modelUri);

		assertXml(message, expectedXml, generatedXml, skippedXPaths);
	}

	private String createXmlFromUri(URI modelUri) throws ModelNotFoundException, IOException, InvalidMetamodelVersionException {
		return generatePageflow(getOfsProject(), modelUri);
	}

	protected String generatePageflow(IOfsProject ofsProject, URI modelURI)
			throws ModelNotFoundException, IOException,
			InvalidMetamodelVersionException {
		// Prepare project
		IProject project = ofsProject.getProject();
		final String PAGEFLOW_OUTPUT_FOLDER = "generated-pageflow";
		File outputFolder = project.getFolder(PAGEFLOW_OUTPUT_FOLDER)
				.getLocation().toFile();
		FileUtils.forceMkdir(outputFolder);
		FileUtils.cleanDirectory(outputFolder);

		// Generate pageflow
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(
				modelURI);
		boolean success = generatePageflow(PAGEFLOW_OUTPUT_FOLDER, project,
				modelResource);

		if (!success) {
			throw new RuntimeException("Unable to generate the pageflow ("
					+ modelURI + ")");
		}

		// Retrieve the generated file
		File dir = project.getFolder(PAGEFLOW_OUTPUT_FOLDER + "/META-INF/config").getLocation()
				.toFile();
		Assert.assertTrue("The generation folder is not available", dir.exists()
				&& dir.isDirectory());

		FilenameFilter ff = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		};
		Assert.assertEquals("There should be one file generated", 1,
				dir.listFiles(ff).length);
		File file = dir.listFiles(ff)[0];
		return FileUtils.readFileToString(file);
	}

	private boolean generatePageflow(String outputfolder, IProject project,
			IOfsModelResource modelResource) throws IOException,
			InvalidMetamodelVersionException {
		WorkflowRunner wfRunner = new WorkflowRunner();
		Map<String, String> properties = new HashMap<String, String>();
		Map<String, Object> slotContents = new HashMap<String, Object>();
		return generatePageflow(outputfolder, wfRunner, properties,
				slotContents, project, modelResource);
	}

	@SuppressWarnings("deprecation")
	protected boolean generatePageflow(String outputfolder,
			WorkflowRunner wfRunner, Map<String, String> properties,
			Map<String, Object> slotContents, IProject project,
			IOfsModelResource modelResource) throws IOException,
			InvalidMetamodelVersionException {
		boolean success;
		Pageflow pageflow = (modelResource.getEMFModel().get(0) instanceof Pageflow) ? (Pageflow) modelResource
				.getEMFModel().get(0) : (Pageflow) modelResource.getEMFModel()
				.get(1);

		pageflow = new PageflowMerger(pageflow).merge();

		properties.put("projectName", "test");
		properties.put("systemUser", System.getProperty("user.name"));
		properties.put("packageName", "testpackage");
		properties.put("pageflowModelSlot", "pageflowModelSlot");
		properties.put("outlet", project.getFolder(outputfolder).getLocation()
				.toString());
		slotContents.put("pageflowModelSlot", Collections.singleton(pageflow));
		properties.put("pageflowFileName", pageflow.getFileName());
		success = wfRunner.run(
				"com/odcgroup/pageflow/generation/ocs/workflow.oaw",
				new NullProgressMonitor(), properties, slotContents);
		return success;
	}

}

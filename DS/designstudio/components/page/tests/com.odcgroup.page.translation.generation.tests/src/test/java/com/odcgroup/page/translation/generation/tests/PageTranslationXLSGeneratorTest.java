package com.odcgroup.page.translation.generation.tests;

import java.io.IOException;
import java.util.Collections;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.generation.internal.generator.xls.TranslationXLSGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

@SuppressWarnings("restriction")
public class PageTranslationXLSGeneratorTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS4750.module");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testXLSGeneration() throws CoreException, InterruptedException, ModelNotFoundException, IOException {
		TranslationXLSGenerator generator = new TranslationXLSGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/module/DS4750.module"));
		IFolder outputFolder = getProject().getFolder("Documentation");
		outputFolder.create(true, true, null);
		generator.run(getProject(), Collections.singleton(modelResource), outputFolder, null);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile translationFile = outputFolder.getFile("Doc/Translation.xls");
		Assert.assertTrue(translationFile.exists());
		String content = FileUtils.readFileToString(translationFile.getLocation().toFile());
		Assert.assertTrue(content.contains("ReportKey"));
		Assert.assertTrue(content.contains("ID_DS4750.text"));
		Assert.assertTrue(content.contains("ID_DS4750.tooltip"));
	}

}

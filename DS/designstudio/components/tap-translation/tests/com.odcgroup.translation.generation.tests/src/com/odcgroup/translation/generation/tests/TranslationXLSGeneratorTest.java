package com.odcgroup.translation.generation.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

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

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.internal.generator.xls.TranslationXLSGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class TranslationXLSGeneratorTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
	 createModelsProject();
		ITranslationManager translationManager = TranslationCore.getTranslationManager(getProject());
		translationManager.getPreferences().addAdditionalLocale(Collections.singleton(Locale.FRANCE));
		copyResourceInModelsProject("domain/ds4517/DS4517.domain");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testXLSGeneration() throws CoreException, InterruptedException, ModelNotFoundException, IOException {
		TranslationXLSGenerator generator = new TranslationXLSGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///ds4517/DS4517.domain"));
		IFolder outputFolder = getProject().getFolder("Documentation");
		outputFolder.create(true, true, null);
		generator.run(getProject(), Collections.singleton(modelResource), outputFolder, null);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile translationFile = outputFolder.getFile("Doc/Translation.xls");
		Assert.assertTrue(translationFile.exists());
		String content = FileUtils.readFileToString(translationFile.getLocation().toFile());
		Assert.assertTrue(content.contains("French France Text"));
		Assert.assertTrue(content.contains("French Text"));
		Assert.assertTrue(content.contains("German Text"));
		Assert.assertTrue(content.contains("English Text"));
	}
	

}

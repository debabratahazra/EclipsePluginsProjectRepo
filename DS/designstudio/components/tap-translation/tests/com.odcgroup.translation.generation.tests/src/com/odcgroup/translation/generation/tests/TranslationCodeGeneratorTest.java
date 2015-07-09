package com.odcgroup.translation.generation.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.internal.generator.nls.TranslationCodeGenerator;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.generation.GenerationCore;
import com.odcgroup.workbench.generation.cartridge.CodeGenCategory;
import com.odcgroup.workbench.generation.tests.AbstractDSGenerationTest;

@Ignore
public class TranslationCodeGeneratorTest extends AbstractDSGenerationTest {

	@Before
	public void setUp() throws Exception {
		createModelAndGenProject();	
		ITranslationManager translationManager = TranslationCore.getTranslationManager(getProject());
		translationManager.getPreferences().addAdditionalLocale(Collections.singleton(Locale.FRANCE));
		copyResourceInModelsProject("domain/ds4517/DS4517.domain");
		copyResourceInModelsProject("menu/Default/gen/MenuGen.menu");
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}
	
	@Test
	public void testRunNLSGeneratorAndCheckResult() throws CoreException, ModelNotFoundException, IOException {
		TranslationCodeGenerator generator = new TranslationCodeGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///ds4517/DS4517.domain"));
		IOfsModelResource modelResource2 = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/gen/MenuGen.menu"));
		IFolder outputFolder = GenerationCore.getJavaSourceFolder(getProject(), CodeGenCategory.WUIBLOCK);
		Collection<IOfsModelResource> resources = new ArrayList<IOfsModelResource>();
		resources.add(modelResource);
		resources.add(modelResource2);
		IProgressMonitor monitor = new NullProgressMonitor();
		List<IStatus> nonOkStatuses = new ArrayList<IStatus>();
		generator.run(monitor, getProject(), resources, outputFolder, nonOkStatuses);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFolder translationFolder = outputFolder.getFolder(getProject().getName());
		Assert.assertTrue(translationFolder.getFile("messages_fr_FR.xml").exists());
		Assert.assertTrue(translationFolder.getFile("messages_fr.xml").exists());
		Assert.assertTrue(translationFolder.getFile("messages_de.xml").exists());
		Assert.assertTrue(translationFolder.getFile("messages.xml").exists());
		
		String content = FileUtils.readFileToString(translationFolder.getFile("messages_fr_FR.xml").getLocation().toFile());
		Assert.assertTrue(content.contains("<message key=\"ds4157.ds4517class.text\">French France Text</message>"));
		
		content = FileUtils.readFileToString(translationFolder.getFile("messages.xml").getLocation().toFile());
		Assert.assertTrue("message.xml should contains \"<message key=\"menu.aaa.activity\">PMA</message>\" but it don't. Here is the full contents:\n"+ content, content.contains("<message key=\"menu.aaa.activity\">PMA</message>"));
		
		IFolder blockInfFolder = outputFolder.getFolder("BLOCK-INF");
		FileUtils.readFileToString(blockInfFolder.getFile("block.xml").getLocation().toFile());
	}
	
}

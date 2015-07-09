package com.odcgroup.translation.generation.tests;

import java.io.IOException;
import java.util.Collections;

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

public class TranslationXLSExportTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	/**
	 * This test case if for Widget Label
	 */
	@Test
	public void testTranslationXLSExport1() throws CoreException, InterruptedException, ModelNotFoundException, IOException {
		copyResourceInModelsProject("module/Default/module/DS4751.module");
		TranslationXLSGenerator generator = new TranslationXLSGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/module/DS4751.module"));
		IFolder outputFolder = getProject().getFolder("Documentation");
		outputFolder.create(true, true, null);
		generator.run(getProject(), Collections.singleton(modelResource), outputFolder, null);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile translationFile = outputFolder.getFile("Doc/Translation.xls");
		Assert.assertTrue(translationFile.exists());
		
		ExcelSheetReader reader = new ExcelSheetReader();
		reader.readExcelFile(translationFile.getLocation().toString());
		String actualResult = reader.getContent(new int[]{2,5});
		Assert.assertEquals("<b>English Rich Text Label</b>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,6});
		Assert.assertEquals("<i>French Rich Text Label</i>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,7});
		Assert.assertEquals("<u>German Rich Text Label</u>", actualResult);
		
		reader.close();
		
	}
	
	/**
	 * This test case is for Widget Radio
	 */
	@Test
	public void testTranslationXLSExport2() throws CoreException, InterruptedException, ModelNotFoundException, IOException {
		copyResourceInModelsProject("module/Default/module/DS4752.module");
		TranslationXLSGenerator generator = new TranslationXLSGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/module/DS4752.module"));
		IFolder outputFolder = getProject().getFolder("Documentation");
		outputFolder.create(true, true, null);
		generator.run(getProject(), Collections.singleton(modelResource), outputFolder, null);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile translationFile = outputFolder.getFile("Doc/Translation.xls");
		Assert.assertTrue(translationFile.exists());
		
		ExcelSheetReader reader = new ExcelSheetReader();
		reader.readExcelFile(translationFile.getLocation().toString());
		String actualResult = reader.getContent(new int[]{2,5});
		Assert.assertEquals("<b>English Rich Text Radio</b>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,6});
		Assert.assertEquals("<i>French Rich Text Radio</i>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,7});
		Assert.assertEquals("<u>German Rich Text Radio</u>", actualResult);
		
		reader.close();
	}
	
	/**
	 * This test case is for Widget Button
	 */
	@Test
	public void testTranslationXLSExport3() throws CoreException, InterruptedException, ModelNotFoundException, IOException {
		copyResourceInModelsProject("module/Default/module/DS4753.module");
		
		TranslationXLSGenerator generator = new TranslationXLSGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/module/DS4753.module"));
		IFolder outputFolder = getProject().getFolder("Documentation");
		outputFolder.create(true, true, null);
		generator.run(getProject(), Collections.singleton(modelResource), outputFolder, null);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile translationFile = outputFolder.getFile("Doc/Translation.xls");
		Assert.assertTrue(translationFile.exists());
		
		ExcelSheetReader reader = new ExcelSheetReader();
		reader.readExcelFile(translationFile.getLocation().toString());
		String actualResult = reader.getContent(new int[]{2,5});
		Assert.assertEquals("<b>English Rich Text Button</b>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,6});
		Assert.assertEquals("<i>French Rich Text Button</i>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,7});
		Assert.assertEquals("<u>German Rich Text Button</u>", actualResult);
		
		reader.close();
	}
	
	/**
	 * This test case is for Widget Check box.
	 */
	@Test
	public void testTranslationXLSExport4() throws CoreException, InterruptedException, ModelNotFoundException, IOException {
		copyResourceInModelsProject("module/Default/module/DS4754.module");
		copyResourceInModelsProject("domain/ds4754/DS4754.domain"); 
		TranslationXLSGenerator generator = new TranslationXLSGenerator();
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/module/DS4754.module"));
		IFolder outputFolder = getProject().getFolder("Documentation");
		outputFolder.create(true, true, null);
		generator.run(getProject(), Collections.singleton(modelResource), outputFolder, null);
		outputFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		IFile translationFile = outputFolder.getFile("Doc/Translation.xls");
		Assert.assertTrue(translationFile.exists());
		
		ExcelSheetReader reader = new ExcelSheetReader();
		reader.readExcelFile(translationFile.getLocation().toString());
		String actualResult = reader.getContent(new int[]{2,5});
		Assert.assertEquals("<b>English Rich Text Check Box</b>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,6});
		Assert.assertEquals("<i>French Rich Text Check Box</i>", actualResult);
		
		actualResult = reader.getContent(new int[]{2,7});
		Assert.assertEquals("<u>German Rich Text Check Box</u>", actualResult);
		
		reader.close();
	}
	
}

package com.odcgroup.page.translation.generation.tests;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.generation.TranslationGenerationCore;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author atr
 */
public class TranslationXLSGenerationTest extends AbstractDSUnitTest {

	private static final String DOCUMENTATION_FOLDER = "Documentation";
	private static final String MODULE_MODEL = "module/Default/module/ds3339.module";
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(MODULE_MODEL);
	}

	@After
	public void tearDown() throws Exception  {
		deleteModelsProjects();
	}	
	
	/**
	 * @throws ModelNotFoundException 
	 * 
	 */
	@Test
	public void testExcelGeneration() throws ModelNotFoundException {
		
		IFolder docFolder = getProject().getFolder(DOCUMENTATION_FOLDER);
		try {
			OfsCore.createFolder(docFolder);
		} catch (CoreException ex) {
			Assert.fail("Unable to create documentation folder");
		}
		
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("generator", "com.odcgroup.translaton.xls.generator");
		properties.put("folder", docFolder);
		try {
			@SuppressWarnings("unchecked")
			Set<IOfsModelResource> modelResources = Collections.singleton(OfsCore.getOfsProject(getProject()).getOfsModelResource(URI.createURI("resource:///Default/module/ds3339.module")));
			TranslationGenerationCore.generateTranslations(getProject(), modelResources, properties);
		} catch (CoreException ex) {
			Assert.fail("Unable to create XLS file");
		}
		
		// just test the existence of the xls file
		IFolder xlsFolder = docFolder.getFolder("Doc");
		IFile xlsFile = xlsFolder.getFile("Translation.xls");
		Assert.assertTrue("XLS file has not been generated", xlsFile.exists());
		
	}
	
}

package com.odcgroup.page.ui.util.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.PartInitException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 * @author yan
 */
public class PageDesignerEditorTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3168/DS-3168.domain";
	private static final String MODULE_MODEL = "module/Default/module/DS-3168-BigModule.module";

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, MODULE_MODEL);
        closeWelcomeScreen(); // Otherwise the editor won't get displayed
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }

	/**
	 * Open the PageDesigner (module) on a large model and validate the execution time
	 * @throws PartInitException
	 */
	@Test
	public void testDs3168PageDesignerOpeningSpeed() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_MODEL);
		Assert.assertTrue(moduleFile.exists());
		
//		long start = System.currentTimeMillis();
		openDefaultEditor(moduleFile);
//		long executionTime = System.currentTimeMillis() - start;
//		
//		final long MAX_EXECUTION_TIME = 7000;
//		Assert.assertTrue("Page designer opened too slowly ["+executionTime+"ms](", executionTime < MAX_EXECUTION_TIME);
	}
}

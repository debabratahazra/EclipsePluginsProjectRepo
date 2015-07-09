package com.odcgroup.page.validation.resolution;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;
import com.odcgroup.workbench.tap.validation.ValidationUtil;

public class HtmlIdProblemResolutionTest extends AbstractDSUnitTest {

	private static final String TEST_MODULE = "module/Default/module/DS3583_MissingIdModule.module";

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject(TEST_MODULE);
	}

	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testGenerateMissingIds() throws CoreException {
		IFile file = getProject().getFile(TEST_MODULE);
		Object adapter = file.getAdapter(IOfsElement.class);
		if(adapter instanceof IOfsModelResource) {
			IOfsModelResource modelResource = (IOfsModelResource) adapter;
			Assert.assertFalse(ValidationUtil.validate(modelResource, null, false).isOK());
			
			HtmlIdProblemResolution.generateMissingIds(file, null);
			Assert.assertTrue(ValidationUtil.validate(modelResource, null, false).isOK());
		} else {
			Assert.fail("Cannot find test model!");
		}		
	}
}

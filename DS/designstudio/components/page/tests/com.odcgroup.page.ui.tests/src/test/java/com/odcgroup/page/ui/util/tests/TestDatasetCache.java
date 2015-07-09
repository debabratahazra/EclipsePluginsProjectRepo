package com.odcgroup.page.ui.util.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.ui.tests.util.PageUiAssert;
import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.IOfsProject;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.repository.ModelURIConverter;

public class TestDatasetCache extends AbstractPageDesignerUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3458/DS3458.domain";
	private static final String MODULE_MODEL = "module/widget/tabletree/DS4428.module";

	
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
	 * @throws PartInitException 
	 */
	@Test
	public void testDS3962DomainRepositoryDatasetCache() throws PartInitException {
		IFile moduleFile = getProject().getFile(MODULE_MODEL);
		IFile domfile = getProject().getFile(DOMAIN_MODEL);
		Assert.assertTrue(moduleFile.exists());
		Assert.assertTrue(domfile.exists());
		IOfsProject ofsProject = OfsCore.getOfsProject(getProject());
		IEditorPart ep = openDefaultEditor(moduleFile);
		PageUiAssert.assertInstanceOfMultiPageEditorPart(ep);		
		closeEditor(ep);
		// clear the models in memory
		ofsProject.clearCache();
		// reload the module
		ep = openDefaultEditor(moduleFile);
		PageUiAssert.assertInstanceOfMultiPageEditorPart(ep);
		URI uri = ModelURIConverter.createModelURI((IResource)domfile);
		// check if the domain model is loaded
		Assert.assertTrue(isResourceLoaded(ofsProject, uri));
		
	}
	
	/**
	 * @param ofsProject
	 * @param uri
	 * @return
	 */
	private boolean isResourceLoaded(IOfsProject ofsProject, URI uri) {
		boolean loaded = false;
		try {
			IOfsModelResource mres = ofsProject.getOfsModelResource(uri);
			loaded = mres.isLoaded();
		} catch (ModelNotFoundException e) {
			loaded = false;
		}
		return loaded;
	}
	
	/**
	 * @param editor
	 */
	private void closeEditor(IEditorPart editor) {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(editor, false);
	}


}
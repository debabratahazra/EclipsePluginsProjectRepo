package com.odcgroup.page.ui.util.tests;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.ui.helper.TemporarilyClosedEditorManager;
import com.odcgroup.workbench.ui.tests.util.AbstractDSUIUnitTest;

/**
 * @author yan
 */
public class TemporarlyClosingEditorManagerTest extends AbstractDSUIUnitTest {

	private static final String DOMAIN_MODEL = "domain/ds3641/DS3641.mml";
	private static final String FRAGMENT_MODEL = "fragment/Default/ds3641/DS3641Fragment.fragment";

    @Before
	public void setUp() throws Exception {
    	createModelsProject();
        copyResourceInModelsProject(DOMAIN_MODEL, FRAGMENT_MODEL);
        closeWelcomeScreen(); // Otherwise the editor won't get displayed
    }

	@After
	public void tearDown() throws Exception {
        deleteModelsProjects();
    }

	/**
	 * Open a fragment and domain, then close them temporarily
	 * @throws PartInitException
	 */
	@Test
	public void testDs3641CloseDomainAndFragment() throws PartInitException {
		openDefaultEditor(getProject().getFile(FRAGMENT_MODEL));
		openDefaultEditor(getProject().getFile(DOMAIN_MODEL));
		
		TemporarilyClosedEditorManager temporarilyClosedEditorManager = 
			new TemporarilyClosedEditorManager("mml", "fragment");
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);

		temporarilyClosedEditorManager.closeEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 0, references.length);
		
		temporarilyClosedEditorManager.restoreEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);
	}
	
	/**
	 * Open a fragment and domain, then close only the domain editor
	 * @throws PartInitException
	 */
	@Test
	public void testDs3641CloseOnlyDomain() throws PartInitException {
		openDefaultEditor(getProject().getFile(FRAGMENT_MODEL));
		openDefaultEditor(getProject().getFile(DOMAIN_MODEL));
		
		TemporarilyClosedEditorManager temporarilyClosedEditorManager = 
			new TemporarilyClosedEditorManager("mml");
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);

		temporarilyClosedEditorManager.closeEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 1, references.length);
		
		temporarilyClosedEditorManager.restoreEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);
	}
	
	/**
	 * Open a fragment and domain, then close only the domain editor
	 * @throws PartInitException
	 */
	@Test
	public void testDs3641CloseByFileName() throws PartInitException {
		openDefaultEditor(getProject().getFile(FRAGMENT_MODEL));
		openDefaultEditor(getProject().getFile(DOMAIN_MODEL));
		
		TemporarilyClosedEditorManager temporarilyClosedEditorManager = 
			new TemporarilyClosedEditorManager() {
				@Override
				protected boolean acceptFile(IFile file) {
					return file.getName().endsWith("DS3641Fragment.fragment");
				}
			
		};
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);

		temporarilyClosedEditorManager.closeEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 1, references.length);
		
		temporarilyClosedEditorManager.restoreEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);
	}
	
	/**
	 * Open a fragment and domain, then close only the domain editor
	 * @throws PartInitException
	 */
	@Test
	public void testDs3641CloseByFileNameAndByType() throws PartInitException {
		openDefaultEditor(getProject().getFile(FRAGMENT_MODEL));
		openDefaultEditor(getProject().getFile(DOMAIN_MODEL));
		
		TemporarilyClosedEditorManager temporarilyClosedEditorManager = 
			new TemporarilyClosedEditorManager("mml") {
				@Override
				protected boolean acceptFile(IFile file) {
					if (super.acceptFile(file)) {
						return true;
					} else {
						return file.getName().endsWith("DS3641Fragment.fragment");
					}
				}
		};
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);

		temporarilyClosedEditorManager.closeEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 0, references.length);
		
		temporarilyClosedEditorManager.restoreEditors();
		references = page.getEditorReferences();
		Assert.assertEquals("Wrong number of editor open", 2, references.length);
	}
	
}

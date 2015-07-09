package com.odcgroup.process.editor.tests.setup;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractProcessEditorTestSetup {
	
	private IProject project = null;

	@Before
	public void setUp() throws Exception {
        /**
         * Close the existing perspectives.
         */
    	IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        Assert.assertNotNull(workbenchWindow);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        Assert.assertNotNull(workbenchPage);

        workbenchPage.closeAllPerspectives(false, false);
        PlatformUI.getWorkbench().showPerspective(
                "com.odcgroup.workbench.ui.perspectives.ofs", workbenchWindow);
		project = createProject();
		
	}

	@After
	public void tearDown() throws Exception {
    	project.delete(true, null);
	}
	
	/**
	 * @throws Exception
	 */
	protected abstract IProject createProject() throws Exception;
}

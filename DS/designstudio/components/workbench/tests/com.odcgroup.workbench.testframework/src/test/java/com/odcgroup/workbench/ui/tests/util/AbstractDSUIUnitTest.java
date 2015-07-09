package com.odcgroup.workbench.ui.tests.util;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.part.FileEditorInput;
import org.junit.Assert;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * Helper class for DS unit tests with UI.
 * @author yan
 */
public class AbstractDSUIUnitTest extends AbstractDSUnitTest {
	
	protected AbstractDSUIUnitTest() {
		super();
	}

	/**
	 * Close the welcome screen (by showing the Odyssey Perspective).
	 * This will make subsequent editor or view to draw their content
	 * during the unit test.
	 */
	public void closeWelcomeScreen() {
		showOdysseyPerspective();
	}

	/**
	 * Display the Odyssey perspective
	 */
	public void showOdysseyPerspective() {
		showPerspective("com.odcgroup.workbench.ui.perspectives.ofs");
	}

	/**
	 * Display the perspectiveId perspective
	 * @param perspectiveId
	 */
	public void showPerspective(String perspectiveId) {
    	IWorkbenchWindow workbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        Assert.assertNotNull(workbenchWindow);
        IWorkbenchPage workbenchPage = workbenchWindow.getActivePage();
        Assert.assertNotNull(workbenchPage);
        workbenchPage.closeAllPerspectives(false, false);
        try {
			PlatformUI.getWorkbench().showPerspective(
					perspectiveId, workbenchWindow);
		} catch (WorkbenchException e) {
			throw new RuntimeException("Unable to show " + perspectiveId + " Perspective", e);
		}
	}

	public IEditorPart openDefaultEditor(IFile inputFile) {
		IEditorDescriptor editor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(inputFile.getName());
		String editorId = editor==null ? "org.eclipse.ui.DefaultTextEditor" : editor.getId();
		return openEditor(inputFile, editorId);
	}

	public IEditorPart openEditor(IFile inputFile, String editorId) {
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			return page.openEditor(new FileEditorInput(inputFile), editorId);
		} catch (PartInitException e) {
			throw new RuntimeException("Unable to open " + inputFile + " file in the " + editorId + "editor.", e);
		}
	}
	
	public void closeSvnInstallConnectorsDialog(){
	    List<Shell> shells = null;
	    if (PlatformUI.getWorkbench().getDisplay() != null) {
		shells = Arrays.asList(PlatformUI.getWorkbench().getDisplay()
			.getShells());
	    }
	    if (shells != null) {
		for (Shell shell : shells) {
		    if (shell.getText().equals("Install Connectors")) {
			shell.close();
		    }
		}
	    }
	}
}

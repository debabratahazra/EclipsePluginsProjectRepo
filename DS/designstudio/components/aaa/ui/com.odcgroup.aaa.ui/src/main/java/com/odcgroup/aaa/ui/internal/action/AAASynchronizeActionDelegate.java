package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.aaa.ui.AAAUIPlugin;

public class AAASynchronizeActionDelegate implements IEditorActionDelegate {

	/*
	 * @see
	 * org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface
	 * .action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IEditorPart editor = page.getActiveEditor();
		String editorId = editor.getEditorSite().getId();
		IEditorInput input = editor.getEditorInput();
		
		// save the editor if dirty
		if (editor.isDirty()) {
			page.saveEditor(editor, true);
		}

		// do the synchronization
		new AAASynchronizeAction().run();

		// reopen the editor
		page.closeEditor(editor, false);
		try {
			page.openEditor(input, editorId);
		} catch (PartInitException ex) {
			String message = "Unable to reopen the editor";
			IStatus status = new Status(IStatus.ERROR, AAAUIPlugin.getDefault().getBundle().getSymbolicName(),
					IStatus.OK, message, ex);
			AAAUIPlugin.getDefault().getLog().log(status);
		}

	}

	/*
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}

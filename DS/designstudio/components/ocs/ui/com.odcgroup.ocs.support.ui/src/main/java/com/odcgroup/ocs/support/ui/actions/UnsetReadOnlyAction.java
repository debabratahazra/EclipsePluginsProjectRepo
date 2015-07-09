package com.odcgroup.ocs.support.ui.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class UnsetReadOnlyAction implements IObjectActionDelegate {

	private IStructuredSelection selection;
	private IWorkbenchPart targetPart;

	/**
	 * Constructor for UnsetReadOnlyAction.
	 */
	public UnsetReadOnlyAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@SuppressWarnings("rawtypes")
	public void run(IAction action) {
		try {
			Iterator it = selection.iterator();
			while (it.hasNext()) {
				final IFile file = (IFile) it.next();
				ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor)
							throws CoreException {
                        ResourceAttributes attr = file.getResourceAttributes();
                        if (attr != null) {
                            attr.setReadOnly(false);
                            file.setResourceAttributes(attr);
                        }
					}
				}, file, IWorkspace.AVOID_UPDATE, null);
			}
		} catch (CoreException e) {
			MessageDialog.openError(
					targetPart.getSite().getShell(),
					"Error",
					e.getStatus().getMessage());
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = (IStructuredSelection) selection;
	}

}

package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.workbench.core.IOfsElement;
import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.helper.TemporarilyClosedEditorManager;

/**
 * DS-2077 Action that synchronizes all datasets (marked synced) with the
 * respective base-class.
 * 
 * @author pkk
 * 
 */
public class SynchronizeDatasetsAction extends Action implements IObjectActionDelegate {

	final static private String ID = "SYNCHRONIZE_DATASETS";
	private IStructuredSelection selection = null;
	
	private TemporarilyClosedEditorManager mmlEditorStateManager =  new TemporarilyClosedEditorManager("mml","domain");

	private final static String CONFIRM_SYNC = "All datasets found under this package and marked " +
			"as \"Synchronized with base class\" will be flushed and re-created. " +
			"Are you sure you want to continue copying?";
	
	private final static String CONFIRM_CLOSE = "Dataset synchronization requires "
		+ "all open domain editors to be closed.\n"
		+ "Are you sure you want to close all open domain editors?";

	/**
	 * default constructor
	 */
	public SynchronizeDatasetsAction() {
		setId(ID);
		setText("Synchronize Datasets");
		setToolTipText("Synchronizes all datasets by copying the respective base-class properties.");
		setImageDescriptor(MdfPlugin.getImageDescriptor("full/obj16/MdfDataset.gif", true));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void run() {
		run(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Shell shell = window.getShell();
		
		// confirm before copying from base-class
		if (!confirmSynchronization(shell)) {
			return;
		}

		mmlEditorStateManager.closeEditors();

		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			public void execute(IProgressMonitor monitor) throws CoreException {
				Object obj = selection.getFirstElement();
				if (obj instanceof IOfsElement) {
					new DatasetFacility().synchronizeDatasets(monitor, (IOfsElement)obj);
				}
			}
		};

		try {
			new ProgressMonitorDialog(shell).run(true, false, operation);
		} catch (Exception e) {
			OfsUICore.getDefault().logError("Error Validating Domain Models", e);
		}
		mmlEditorStateManager.restoreEditors();
	}
	
	protected boolean confirmCloseEditors(Shell shell) {
		return MessageDialog.openConfirm(shell, "Warning", CONFIRM_CLOSE);
	}
	
	protected boolean confirmSynchronization(Shell shell) {
		return MessageDialog.openConfirm(shell, "Warning", CONFIRM_SYNC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection) selection;
		}
	}

	/**
	 * @param selection
	 */
	public void selectionChanged(final ISelection selection) {
		selectionChanged(this, selection);
	}


}

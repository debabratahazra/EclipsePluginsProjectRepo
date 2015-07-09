package com.odcgroup.aaa.ui.internal.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.odcgroup.aaa.core.AAACore;
import com.odcgroup.workbench.core.IOfsModelPackage;

abstract class AAAAction extends Action {
	
	/** The wizard height */
	private static final int SIZING_WIZARD_HEIGHT = 550;

	/** The wizard width */
	private static final int SIZING_WIZARD_WIDTH = 470;
	
	private IStructuredSelection selection;
	
	/**
	 * @return IStructuredSelection the current selection
	 */
	protected final IStructuredSelection getSelection() {
		return selection;
	}

	/**
	 * Creates the wizard to execute.
	 * 
	 * @return IImportWizard
	 */
	abstract protected IImportWizard createImportWizard();
	
	/**
	 * @param window
	 * @return {@code true} if the wizard has not been canceled, otherwise it returns {@code false}
	 */
	protected boolean runWizard(IWorkbenchWindow window) {
		IImportWizard wizard = createImportWizard();
		wizard.init(window.getWorkbench(), (IStructuredSelection) window.getSelectionService().getSelection());
		WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT);
		return Dialog.OK == dialog.open();
	}

	/**
	 * Creates a new AAAAction.
	 */
	protected AAAAction() {
	}
	
	/**
	 * Runs the action.
	 */
	public void run() {
		run(this);
	}

	/**
	 * Runs the action.
	 * 
	 * @param action The action to run
	 */
	public void run(IAction action) {
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		
		ISelection sel = window.getSelectionService().getSelection();
		if(sel instanceof IStructuredSelection) {
			selection = (IStructuredSelection)sel;
			Object element = selection.getFirstElement();
			if(element instanceof IOfsModelPackage){
				String rootFolder = ((IOfsModelPackage)element).getParent().getResource().getName();
				AAACore.setRootAAAFolder(rootFolder);
			}
			runWizard(window);
		}
	}
	
}

package com.odcgroup.t24.common.importer.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public abstract class ModelImportAction extends Action {

	/** The wizard height */
	private static final int SIZING_WIZARD_HEIGHT= 600;

	/** The wizard width */
	private static final int SIZING_WIZARD_WIDTH= 470;
	
	/** create the concrete wizard for importing models */
	protected abstract IImportWizard createImportWizard();
	
	protected int getWizardHeight() {
		return ModelImportAction.SIZING_WIZARD_HEIGHT;
	}

	protected int getWizardWidth() {
		return ModelImportAction.SIZING_WIZARD_WIDTH;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void run() {
		run(this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void run(final IAction action) {
		final IImportWizard wizard= createImportWizard();
		final IWorkbenchWindow window= PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if(!(window.getSelectionService().getSelection() instanceof IStructuredSelection))
			return;
		wizard.init(window.getWorkbench(), (IStructuredSelection) window.getSelectionService().getSelection());
		final WizardDialog dialog = new WizardDialog(window.getShell(), wizard);
		dialog.create();
		dialog.getShell().setSize(Math.max(getWizardWidth(), dialog.getShell().getSize().x), getWizardHeight());
		dialog.open();
	}

	/**
	 * @param text the text for the action
	 */
	public ModelImportAction(String text) {
		setText(text);
	}
	
}

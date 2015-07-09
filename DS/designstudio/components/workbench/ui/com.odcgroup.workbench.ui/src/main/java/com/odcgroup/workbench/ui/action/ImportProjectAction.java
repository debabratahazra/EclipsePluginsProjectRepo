package com.odcgroup.workbench.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizard;

import com.odcgroup.workbench.core.IOfsModelFolder;
import com.odcgroup.workbench.ui.OfsUICore;

public class ImportProjectAction extends Action implements IObjectActionDelegate, ISelectionChangedListener {

	/** The wizard height */
	public static final int SIZING_WIZARD_HEIGHT= 250;

	/** The wizard width */
	public static final int SIZING_WIZARD_WIDTH= 470;

	/** The structured selection, or <code>null</code> */
	private IStructuredSelection fSelection= null;

	final static private String ID = "IMPORT_PROJECT";

	private IWorkbenchWindow window = null;

	public ImportProjectAction() {
		setId(ID);
		setText("Import Projects...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				OfsUICore.PLUGIN_ID, "/icons/import_wiz.gif"));
	}
	
	public ImportProjectAction(IWorkbenchWindow window) {
		this();
		this.window = window;
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
		final IImportWizard wizard = new ExternalProjectImportWizard();
		wizard.init(window.getWorkbench(), fSelection);
		final WizardDialog dialog= new WizardDialog(window.getShell(), wizard);
		dialog.create();
//		dialog.getShell().setSize(Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x), SIZING_WIZARD_HEIGHT);
		dialog.open();
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(final IAction action, final ISelection selection) {
		fSelection= null;
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structured= (IStructuredSelection) selection;
			if (structured.size() == 1) {
				if(structured.getFirstElement() instanceof IOfsModelFolder) {
					fSelection = structured;
				}
			}
		}
		action.setEnabled(fSelection != null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(final SelectionChangedEvent event) {
		selectionChanged(this, event.getSelection());
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setActivePart(final IAction action, final IWorkbenchPart part) {
		window = part.getSite().getWorkbenchWindow();
	}

}

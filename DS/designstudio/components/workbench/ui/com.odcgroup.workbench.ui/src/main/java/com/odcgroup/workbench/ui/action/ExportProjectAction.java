package com.odcgroup.workbench.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.UIPlugin;
import org.eclipse.ui.wizards.datatransfer.ZipFileExportWizard;

import com.odcgroup.workbench.ui.OfsUICore;

public class ExportProjectAction extends Action implements IObjectActionDelegate {

	/** The wizard height */
	public static final int SIZING_WIZARD_HEIGHT= 250;

	/** The wizard width */
	public static final int SIZING_WIZARD_WIDTH= 470;

	/** The structured selection, or <code>null</code> */
	private IStructuredSelection fSelection= null;

	private IWorkbenchPart part;

	final static private String ID = "EXPORT_PROJECT";

	public ExportProjectAction() {
		setId(ID);
		setText("Export to Archive...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				OfsUICore.PLUGIN_ID, "/icons/export_wiz.gif"));
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
		IWorkbench workbench = UIPlugin.getDefault().getWorkbench();
		final IExportWizard wizard = new ZipFileExportWizard();
		if(fSelection==null) fSelection = StructuredSelection.EMPTY;
		wizard.init(workbench, fSelection);
		final WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow().getShell(), wizard);
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
			final IStructuredSelection structured = (IStructuredSelection) selection;
			if (structured.size() > 0) {
				fSelection = structured;
			}
		}
		action.setEnabled(fSelection != null);
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(final ISelection selection) {
		selectionChanged(this, selection);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setActivePart(final IAction action, final IWorkbenchPart part) {
		this.part = part;
	}

}

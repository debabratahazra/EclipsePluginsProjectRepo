package com.odcgroup.pageflow.editor.diagram.custom.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

public class NewPageflowCreationWizard extends AbstractNewModelResourceCreationWizard {

	protected NewPageflowFileCreationPage filePage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.newresource.BasicNewResourceWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = "pageflow";
		super.init(workbench, currentSelection);
		setDefaultPageImageDescriptor(PageflowDiagramEditorPlugin
				.getBundledImageDescriptor("icons/wizban/NewPageflowWizard.gif")); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (filePage == null) {
			if (containerFullPath == null) {
				containerFullPath = getSelectionFullPath();
			}
			filePage = new NewPageflowFileCreationPage(
					"pageflowNewFileCreation", getWorkbench(),
					containerFullPath);
		}
		addPage(filePage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		boolean retVal = filePage.finish();
		filePage.getFile();
		return retVal;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	public boolean canFinish() {
		return filePage.isPageComplete();
	}
}

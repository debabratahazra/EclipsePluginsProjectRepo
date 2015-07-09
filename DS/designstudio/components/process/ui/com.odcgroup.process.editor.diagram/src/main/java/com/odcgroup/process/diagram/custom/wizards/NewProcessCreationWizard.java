package com.odcgroup.process.diagram.custom.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.process.diagram.part.ProcessDiagramEditorPlugin;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

public class NewProcessCreationWizard extends AbstractNewModelResourceCreationWizard {

	protected NewProcessFileCreationPage filePage;

	/*
	 * OCS-24647 (changed extn from process to workflow)
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.newresource.BasicNewResourceWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = "workflow";
		super.init(workbench, currentSelection);
		setDefaultPageImageDescriptor(ProcessDiagramEditorPlugin
				.getBundledImageDescriptor("icons/obj16/NewProcessWizard.png")); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (filePage == null) {
			filePage = new NewProcessFileCreationPage(
					"processNewFileCreation", getWorkbench(),
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

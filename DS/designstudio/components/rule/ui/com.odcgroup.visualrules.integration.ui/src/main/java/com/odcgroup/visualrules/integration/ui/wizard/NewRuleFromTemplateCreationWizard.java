package com.odcgroup.visualrules.integration.ui.wizard;

import java.util.Iterator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.visualrules.integration.template.Template;
import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationWizard;

public class NewRuleFromTemplateCreationWizard extends AbstractNewModelResourceCreationWizard {

	protected NewRuleFromTemplateCreationPage filePage;

	private Template template;

	public NewRuleFromTemplateCreationWizard(Template template) {
		this.template = template;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.newresource.BasicNewResourceWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		model = "rule";
		super.init(workbench, currentSelection);
	}

	public void addPages() {
		super.addPages();
		if (filePage == null) {
			filePage = new NewRuleFromTemplateCreationPage(
					"ruleNewFileCreation", getWorkbench(),
					containerFullPath, getSelectionProject(),
					template);
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

	/** This method returns the rule model file for the current selection.
	 *  If the current selection is not a rule model folder, it returns null.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected IProject getSelectionProject() {
		Iterator it = selection.iterator();
		if (it.hasNext()) {
			Object obj = it.next();
			IResource selectedResource = null;
			if (obj instanceof IResource) {
				selectedResource = (IResource) obj;
			} else if (obj instanceof IAdaptable) {
				selectedResource = (IResource) ((IAdaptable) obj)
						.getAdapter(IResource.class);
			}
			if (selectedResource != null) {
				if(!(selectedResource instanceof IFolder &&
					OfsCore.isOfsModelPackage((IFolder) selectedResource))) {
					selectedResource = selectedResource.getProject().getFolder(model);
				}
			}
			if (selectedResource != null && selectedResource instanceof IFolder) {
				IFolder selectedFolder = (IFolder) selectedResource;
				return selectedFolder.getProject();
			}
		}
		return null;
	}

}

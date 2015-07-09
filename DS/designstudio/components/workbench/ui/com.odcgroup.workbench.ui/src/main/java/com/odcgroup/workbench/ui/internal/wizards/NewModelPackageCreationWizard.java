package com.odcgroup.workbench.ui.internal.wizards;

import java.util.Iterator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.ui.OfsUICore;

public class NewModelPackageCreationWizard extends BasicNewResourceWizard {

	protected NewModelPackageCreationPage folderPage;
	private IPath containerFullPath;
	private IStructuredSelection selection;

	public NewModelPackageCreationWizard() {
		super();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.newresource.BasicNewResourceWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		this.selection = currentSelection;
		setWindowTitle("New model package"); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (folderPage == null) {
			if (containerFullPath == null) {
				containerFullPath = getSelectionFullPath();
			}
			folderPage = new NewModelPackageCreationPage(
					"newModelGroupCreation", getWorkbench(),
					containerFullPath);
		}
		addPage(folderPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		boolean retVal = folderPage.finish();
		return retVal;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	public boolean canFinish() {
		return folderPage.isPageComplete();
	}

	/**
	 * @param selection
	 * @return
	 */
	protected IPath getSelectionFullPath() {
		Iterator it = selection.iterator();
		if (it.hasNext()) {
			Object obj = it.next();
			IResource selectedResource = null;
			if (obj instanceof IFolder) {
				selectedResource = (IFolder) obj;
			} else if (obj instanceof IAdaptable) {
				selectedResource = (IResource) ((IAdaptable) obj)
						.getAdapter(IResource.class);
				if(selectedResource instanceof IFolder && !selectedResource.exists()) {
					try {
						OfsCore.createFolder((IFolder) selectedResource);
					} catch (CoreException e) {
						OfsUICore.getDefault().logError("Cannot create model package folder", e);
					}
				}
			}
			if (selectedResource != null) {
				return selectedResource.getFullPath();
			}
		}
		return null;
	}
}

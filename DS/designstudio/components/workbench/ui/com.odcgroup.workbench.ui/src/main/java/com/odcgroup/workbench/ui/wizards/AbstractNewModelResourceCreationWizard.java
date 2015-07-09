package com.odcgroup.workbench.ui.wizards;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

import com.odcgroup.workbench.core.helper.StringHelper;

abstract public class AbstractNewModelResourceCreationWizard extends BasicNewResourceWizard {

	protected String model;
	protected IPath containerFullPath;
	protected IStructuredSelection selection;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.wizards.newresource.BasicNewResourceWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		super.init(workbench, currentSelection);
		this.selection = currentSelection;
		setWindowTitle("New " + StringHelper.toFirstUpper(model)); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	/**
	 * @generated
	 */
	public void addPages() {
		super.addPages();
		if (containerFullPath == null) {
			containerFullPath = getSelectionFullPath();
		}
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
			if (obj instanceof IResource) {
				selectedResource = (IResource) obj;
			} else if (obj instanceof IAdaptable) {
				selectedResource = (IResource) ((IAdaptable) obj)
						.getAdapter(IResource.class);
			}
			if (selectedResource != null) {
				return selectedResource.getFullPath();
			}
		}
		throw new IllegalArgumentException("Proper folder selection not found.");
	}
	
	public IProject getProject(){
		IProject project = null;
		if(getSelectionFullPath().segmentCount()!=0){
			String projectName = getSelectionFullPath().segment(0);
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		}
	   return project;
	}
}

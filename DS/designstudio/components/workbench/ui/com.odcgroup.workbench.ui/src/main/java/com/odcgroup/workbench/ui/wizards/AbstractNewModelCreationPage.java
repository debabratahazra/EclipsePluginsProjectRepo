package com.odcgroup.workbench.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.wizard.WizardPage;

public abstract class AbstractNewModelCreationPage extends WizardPage {

	protected IProject project;
	protected IPath folderPath;

	/**
	 * @param pageName
	 */
	protected AbstractNewModelCreationPage(String pageName) {
		super(pageName);
	}

	public void setContainer(IContainer selectionContainer) {
		project = selectionContainer.getProject();
		folderPath = selectionContainer.getFullPath();
	}

	public IProject getProject() {
		return project;
	}

	public IPath getFolderPath() {
		return folderPath;
	}

}

package com.odcgroup.t24.packager.ui.launcher;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;

import com.odcgroup.t24.packager.nature.TafjPackagerProjectNature;

public class CreateTafjPackageActionProvider extends CommonActionProvider {

	private CreateTafjPackageAction startPackagerAction;

	public CreateTafjPackageActionProvider() {
	}

	public void init(ICommonActionExtensionSite aSite) {
		startPackagerAction = new CreateTafjPackageAction();
	}

	public void fillContextMenu(IMenuManager menu) {
		// Add the Create Tafj Package menu if the project has the
		// packager nature
		if (getContext() != null &&
				getContext().getSelection() instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)getContext().getSelection();
			if (structuredSelection.size() == 1 && structuredSelection.getFirstElement() instanceof IProject) {
				IProject project = (IProject)structuredSelection.getFirstElement();
				try {
					if (project.hasNature(TafjPackagerProjectNature.NATURE_ID)) {
						menu.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, startPackagerAction);
					}
				} catch (CoreException e) {
					// Ignore
				}
			}
		}
	}
}

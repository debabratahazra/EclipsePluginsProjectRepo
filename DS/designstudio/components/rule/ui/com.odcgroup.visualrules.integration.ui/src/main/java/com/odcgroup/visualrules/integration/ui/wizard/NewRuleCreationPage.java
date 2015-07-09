package com.odcgroup.visualrules.integration.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationPage;

public class NewRuleCreationPage extends AbstractNewModelResourceCreationPage {

	protected IProject project;
	
	/**
	 * @param pageName
	 * @param containerPath
	 */
	protected NewRuleCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, IProject project) {
		super(pageName, workbench, containerFullPath, "rule");
		this.project = project;
	}

	/**
	 * @param monitor the <code>IProgressMonitor</code> to use to indicate progress and check for cancellation
	 * @return boolean indicating whether the creation and opening the Diagram was successful
	 */
	public boolean doFinish(IProgressMonitor monitor) {
		try {
			RulesIntegrationPlugin.createRule(project, fileNameField.getText(), description.getText());
			RulesIntegrationUICore.openRule(project, fileNameField.getText());
		} catch (CoreException e) {
			MessageDialog.openError(getShell(), "Error creating new rule", e.getMessage() +
			"\n\nPlease check for details in the log file!");					
		}
		return true;
	}
}

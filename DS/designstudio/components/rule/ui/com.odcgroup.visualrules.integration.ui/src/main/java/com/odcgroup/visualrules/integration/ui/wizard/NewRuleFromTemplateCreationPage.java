package com.odcgroup.visualrules.integration.ui.wizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IWorkbench;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.template.Template;
import com.odcgroup.visualrules.integration.ui.RulesIntegrationUICore;
import com.odcgroup.workbench.ui.wizards.AbstractNewModelResourceCreationPage;

public class NewRuleFromTemplateCreationPage extends AbstractNewModelResourceCreationPage {

	private IProject project;
	private Template template;
	
	/**
	 * @param pageName
	 * @param containerPath
	 */
	protected NewRuleFromTemplateCreationPage(String pageName, IWorkbench workbench,
			IPath containerFullPath, IProject project, Template template) {
		super(pageName, workbench, containerFullPath, "rule");
		this.project = project;
		this.template = template;
	}

	/**
	 * @param monitor the <code>IProgressMonitor</code> to use to indicate progress and check for cancellation
	 * @return boolean indicating whether the creation and opening the Diagram was successful
	 */
	public boolean doFinish(IProgressMonitor monitor) {
		String qualifiedRuleName = 
			template.getType() + "/" +
			project.getName() + "/" +
			fileNameField.getText();
		try {
			RulesIntegrationPlugin.createRuleFromTemplate(
					project,
					qualifiedRuleName,
					description.getText(),
					template.getQualifiedTemplateName(),
					template.getParams());
			RulesIntegrationUICore.openRule(project, qualifiedRuleName);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
}

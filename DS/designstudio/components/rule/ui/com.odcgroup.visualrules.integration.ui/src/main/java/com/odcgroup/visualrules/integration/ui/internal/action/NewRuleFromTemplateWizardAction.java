package com.odcgroup.visualrules.integration.ui.internal.action;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.visualrules.integration.RulesIntegrationPlugin;
import com.odcgroup.visualrules.integration.template.Template;
import com.odcgroup.visualrules.integration.ui.wizard.NewRuleFromTemplateCreationWizard;
import com.odcgroup.workbench.ui.OfsUICore;

public class NewRuleFromTemplateWizardAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {

	private Template template;
	
	public NewRuleFromTemplateWizardAction(Template template) {
		this.template = template;
		setText(template.getName());
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				RulesIntegrationPlugin.PLUGIN_ID, "icons/rule.gif"));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.actions.AbstractOpenWizardAction#createWizard()
	 */
	protected INewWizard createWizard() throws CoreException {
		if (getSelection().isEmpty())
			return null;
		return (INewWizard)getRegisteredWizardClass();
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	protected Object getRegisteredWizardClass(){		
		return new NewRuleFromTemplateCreationWizard(template);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.actions.AbstractOpenWizardAction#doCreateProjectFirstOnEmptyWorkspace(Shell)
	 */
	protected boolean doCreateProjectFirstOnEmptyWorkspace(Shell shell) {
		return true; // can work on an empty workspace
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		super.run();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
	
}

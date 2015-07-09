package com.odcgroup.service.model.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.service.model.ui.wizard.ServiceWizard;
import com.odcgroup.workbench.ui.OfsUICore;

public class NewServiceAction  extends AbstractOpenWizardAction implements
IWorkbenchWindowActionDelegate {
	public static String PLUGIN_ID = "com.odcgroup.service.model.ui";
	
	public NewServiceAction() {
		setText("New Component...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(PLUGIN_ID, "icons/component.png"));
	}

	@Override
	public void run(IAction action) {
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
	

	@Override
	public void dispose() {
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		
	}

	@Override
	protected INewWizard createWizard() throws CoreException {
		if (getSelection().isEmpty())
			return null;
		return new ServiceWizard();
	}
}

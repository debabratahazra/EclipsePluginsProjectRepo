package com.odcgroup.t24.version.newscreen.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.workbench.ui.OfsUICore;

public class NewScreenAction  extends AbstractOpenWizardAction implements
IWorkbenchWindowActionDelegate {
	public static String PLUGIN_ID = "com.odcgroup.t24.version.editor";
	
	public NewScreenAction() {
		setText("New Screen...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(PLUGIN_ID, "icons/screen.png"));
	}

	@Override
	public void run(IAction action) {
		super.run();
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
		return new NewScreenProjectWizard();
	}
}
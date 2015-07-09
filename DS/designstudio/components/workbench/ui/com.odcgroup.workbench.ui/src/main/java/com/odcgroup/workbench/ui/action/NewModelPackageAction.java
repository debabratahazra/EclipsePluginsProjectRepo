package com.odcgroup.workbench.ui.action;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.workbench.ui.OfsUICore;
import com.odcgroup.workbench.ui.internal.wizards.NewModelPackageCreationWizard;

public class NewModelPackageAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {
	
	public NewModelPackageAction() {
		setText("New Package...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(OfsUICore.PLUGIN_ID, "/icons/newpack_wiz.gif"));
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
		return new NewModelPackageCreationWizard();
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

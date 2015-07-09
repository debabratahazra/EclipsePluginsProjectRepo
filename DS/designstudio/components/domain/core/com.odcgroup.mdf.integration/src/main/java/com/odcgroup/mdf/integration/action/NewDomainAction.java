package com.odcgroup.mdf.integration.action;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.mdf.editor.ui.dialogs.NewDomainCreationWizard;
import com.odcgroup.mdf.integration.Activator;
import com.odcgroup.workbench.ui.OfsUICore;

public class NewDomainAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {

	public NewDomainAction() {
		setText("New Domain...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/domain.gif"));
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
	 * DS-370
	 * changed to a simplified wizard from the previous DomainModelWizard
	 * @return
	 */
	protected Object getRegisteredWizardClass(){		
		return new NewDomainCreationWizard();
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

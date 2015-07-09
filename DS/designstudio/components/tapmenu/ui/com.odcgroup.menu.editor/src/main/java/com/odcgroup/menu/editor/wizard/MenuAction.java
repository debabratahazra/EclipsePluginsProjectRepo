package com.odcgroup.menu.editor.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.menu.editor.MenuEditorPlugin;
import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * @author scn
 *
 */
public class MenuAction extends AbstractOpenWizardAction implements
		IWorkbenchWindowActionDelegate {

	/**
	 * 
	 */
	public MenuAction() {
		setText("New Menu...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				MenuEditorPlugin.PLUGIN_ID, "icons/full/obj16/MenuModelFile.gif"));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.actions.AbstractOpenWizardAction#createWizard()
	 */
	protected INewWizard createWizard() throws CoreException {
		if (getSelection().isEmpty())
			return null;
		return (INewWizard) getRegisteredWizardClass();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		if(!getSelection().isEmpty()) {
			Object obj = getSelection().getFirstElement();
			if (obj instanceof IOfsModelPackage) {
				if (!((IOfsModelPackage) obj).isEmpty()) {
					setEnabled(false);
				} else {
					setEnabled(true);
				}
			}
		}
		return super.isEnabled();
	}

	/**
	 * 
	 * @return
	 */
	protected Object getRegisteredWizardClass() {
		return new MenuWizard();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
	}

}

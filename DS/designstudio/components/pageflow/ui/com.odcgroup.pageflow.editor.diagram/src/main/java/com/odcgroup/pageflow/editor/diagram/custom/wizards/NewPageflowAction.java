package com.odcgroup.pageflow.editor.diagram.custom.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.pageflow.editor.diagram.part.PageflowDiagramEditorPlugin;
import com.odcgroup.workbench.ui.OfsUICore;

public class NewPageflowAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {

	public NewPageflowAction() {
		setText("New Pageflow...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				PageflowDiagramEditorPlugin.ID, "icons/obj16/PageflowDiagramFile.gif"));
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
		return new NewPageflowCreationWizard();
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

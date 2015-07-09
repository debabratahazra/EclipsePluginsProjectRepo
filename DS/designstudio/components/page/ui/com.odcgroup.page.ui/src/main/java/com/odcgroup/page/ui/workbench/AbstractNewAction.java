package com.odcgroup.page.ui.workbench;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.page.ui.PageUIPlugin;
import com.odcgroup.workbench.ui.OfsUICore;

/**
 * Base class for actions.
 * 
 * @author Gary Hayes
 */
abstract public class AbstractNewAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {

	/**
	 * Creates a new AbstractNewAction.
	 * 
	 * @param text The text to display
	 * @param imageName The file name of the image
	 */
	public AbstractNewAction(String text, String imageName) {
		setText(text);
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin(
				PageUIPlugin.PLUGIN_ID, imageName));
	}
	

	/**
	 * @return INewWizard
	 * @throws CoreException
	 */
	protected INewWizard createWizard() throws CoreException {
		if (getSelection().isEmpty())
			return null;
		return (INewWizard) getRegisteredWizardClass();
		
	}
	
	
	/**
	 * 
	 * @return Object
	 */
	abstract protected Object getRegisteredWizardClass();

	/**
	 * @param shell
	 * @return boolean
	 */
	protected boolean doCreateProjectFirstOnEmptyWorkspace(Shell shell) {
		return true; // can work on an empty workspace
	}

	/**
	 * Dispose.
	 */
	public void dispose() {
	}

	/**
	 * Init.
	 * 
	 * @param window
	 */
	public void init(IWorkbenchWindow window) {
	}
	
	/**
	 * Run.
	 * 
	 * @param action
	 */
	public void run(IAction action) {
		super.run();
	}

	/**
	 * @param action
	 * @param selection
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}	
}
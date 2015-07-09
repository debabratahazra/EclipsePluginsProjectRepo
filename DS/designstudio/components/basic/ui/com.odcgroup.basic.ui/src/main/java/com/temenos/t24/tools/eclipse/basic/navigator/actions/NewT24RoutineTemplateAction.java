package com.temenos.t24.tools.eclipse.basic.navigator.actions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.wizards.SelectTemplateWizard;

/**
 * Action class to create new T24 Routine Template Wizard
 *
 * @author vramya
 *
 */
public class NewT24RoutineTemplateAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {
	public NewT24RoutineTemplateAction() {
		setText("T24 routine from Template");
		setImageDescriptor(T24BasicPlugin.imageDescriptorFromPlugin(PluginConstants.BASIC_UI_PLUGIN_ID, "icons/fileImage.gif"));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.actions.AbstractOpenWizardAction#createWizard()
	 */
	protected INewWizard createWizard() throws CoreException {
		return new SelectTemplateWizard();
	}

	public void run(IAction action) {
		super.run();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}
}

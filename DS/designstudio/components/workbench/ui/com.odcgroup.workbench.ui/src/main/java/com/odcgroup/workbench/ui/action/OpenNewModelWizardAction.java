package com.odcgroup.workbench.ui.action;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.workbench.core.OfsCore;

public class OpenNewModelWizardAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {

    private String model;
    private static final String MODEL_EXTENSION_ID = "com.odcgroup.workbench.model";
    
	/**
	 * @param model
	 */
	public OpenNewModelWizardAction(String model) {
		this.model = model;
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
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry.getExtensionPoint(MODEL_EXTENSION_ID);
		if (point == null) return null;
		// iterate over all defined models
		try {
			for(IConfigurationElement configElement : point.getConfigurationElements()) {
				if(configElement.getAttribute("name").equals(model)){
					return configElement.createExecutableExtension("wizardClass");	
				}
			}
		} catch (CoreException e) {
			OfsCore.getDefault().logError("Could not instantiate wizard class", e);
		}
		
		return null;
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

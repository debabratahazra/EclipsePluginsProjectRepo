package com.tel.autosysframework.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class AutosysFrameworkCreationWizard extends Wizard implements INewWizard {
	
	private AutosysFrameworkWizardPage logicPage = null;
	private IStructuredSelection selection;
	public static IWorkbench workbench;
	
	public AutosysFrameworkCreationWizard() {
	}

	public void addPages(){
		setWindowTitle("Autosys File Wizard");		
		logicPage = new AutosysFrameworkWizardPage(workbench,selection);
		addPage(logicPage);		
	}


	public boolean performFinish() {
		return logicPage.finish();
	}


	public void init(IWorkbench aWorkbench, IStructuredSelection currentSelection) {
		workbench = aWorkbench;
		selection = currentSelection;
	}

}

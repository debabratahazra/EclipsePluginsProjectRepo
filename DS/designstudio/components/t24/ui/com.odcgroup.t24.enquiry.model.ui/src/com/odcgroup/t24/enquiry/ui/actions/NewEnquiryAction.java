package com.odcgroup.t24.enquiry.ui.actions;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.odcgroup.workbench.ui.OfsUICore;


public class NewEnquiryAction extends AbstractOpenWizardAction implements IWorkbenchWindowActionDelegate {
	
	public NewEnquiryAction(){
		setText("New Enquiry...");
		setImageDescriptor(OfsUICore.imageDescriptorFromPlugin("com.odcgroup.t24.enquiry.importer.ui", "icons/file_obj.gif"));
	}
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	@Override
	protected INewWizard createWizard() throws CoreException {
		return  new NewEnquiryWizard();
	}

}

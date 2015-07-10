package com.tel.autosysframework.actions.testvector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tel.autosysframework.views.GeneralConfigure;
//import com.tel.autosysframework.views.TestVectorShell;

public class CBSTestVectorAction extends TestVectorLibraryClass implements IWorkbenchWindowActionDelegate {

	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {

	}

	public void run(IAction action) {
		//new TestVectorShell().showForm("Code Block Segmentation");
		loadTestVector();
	}

	private void loadTestVector(){
		if(GeneralConfigure.isGenerateTestVector())
		{
			if((GeneralConfigure.getInputpath1().equalsIgnoreCase("")
					|| GeneralConfigure.getOutputpath().equalsIgnoreCase("")
					|| (GeneralConfigure.isTwoChannel() 
							&& GeneralConfigure.getInputpath2().equalsIgnoreCase("")))) {
				msg = new MessageBox(new Shell(), SWT.ERROR);
				msg.setMessage("General Configuration Parameters Not Set ");
				msg.open();
				return;
			} else {
				if(!GeneralConfigure.isTwoChannel())
					jniTestVectorCRCOneChannel(GeneralConfigure.getInputpath1()
							, GeneralConfigure.getBlocklength1());
				else
					jniTestVectorCRCOneChannel(GeneralConfigure.getInputpath1()
							, GeneralConfigure.getBlocklength1());

				msg = new MessageBox(new Shell(), SWT.ICON_INFORMATION);
				msg.setMessage("Test Vector Loaded Succesfully");
				msg.open();
			}
		} else {
			msg = new MessageBox(new Shell(), SWT.ERROR);
			msg.setMessage("Test Vector Generation Not Selected");
			msg.open();
		}
	}
	public void selectionChanged(IAction action, ISelection selection) {

		/*IStructuredSelection sSelection = (IStructuredSelection) selection;
		Object[] selectedObjects = sSelection.toArray();
		for(int i =0; i<selectedObjects.length; i++) {
			if(selectedObjects[i] instanceof IAdaptable) {
				Object adaptableObject = ((IAdaptable)selectedObjects[i]).getAdapter(IResource.class);
				if(adaptableObject instanceof IProject) {				
					action.setEnabled(true);
					break;
				}
				else { 
					action.setEnabled(false);
				}
			}
		}*/
	}

}

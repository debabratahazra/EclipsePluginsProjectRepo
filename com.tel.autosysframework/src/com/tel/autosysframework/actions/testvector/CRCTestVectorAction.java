package com.tel.autosysframework.actions.testvector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tel.autosysframework.editor.AutosysFrameworkEditor;
import com.tel.autosysframework.views.GeneralConfigure;

public class CRCTestVectorAction extends TestVectorLibraryClass implements IWorkbenchWindowActionDelegate {

	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {

	}

	public void run(IAction action) {
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
				if(!GeneralConfigure.isTwoChannel()) {
//					SubBlockConfig(1);
					/*while (!generate) {
					}*/
					jniTestVectorCRCOneChannel(GeneralConfigure.getInputpath1()
							, GeneralConfigure.getBlocklength1());
				}
				else {
					jniTestVectorCRCTwoChannel(GeneralConfigure.getInputpath1()
							, GeneralConfigure.getBlocklength1()
							, GeneralConfigure.getBlocklength2());
//					SubBlockConfig(2);
				}

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

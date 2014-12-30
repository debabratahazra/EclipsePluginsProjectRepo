package com.tel.autosysframework.actions.testvector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class PrecodingTestVectorAction extends TestVectorLibraryClass implements
		IWorkbenchWindowActionDelegate {

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub

	}

	public void run(IAction action) {
//		loadTestVector(true);

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

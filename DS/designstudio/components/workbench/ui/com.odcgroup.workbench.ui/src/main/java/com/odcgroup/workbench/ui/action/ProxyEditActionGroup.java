package com.odcgroup.workbench.ui.action;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.navigator.resources.actions.EditActionGroup;

import com.odcgroup.workbench.core.IOfsModelPackage;
import com.odcgroup.workbench.core.IOfsModelResource;


public class ProxyEditActionGroup extends EditActionGroup {

	public ProxyEditActionGroup(Shell shell) {
		super(shell);
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {

		IStructuredSelection oldSelection = (IStructuredSelection) getContext().getSelection();
		List<Object> selectedResources = new LinkedList<Object>();
		for(Object element : oldSelection.toArray()) {
			if(element instanceof IOfsModelPackage || element instanceof IOfsModelResource) {
				// get the IResource for the OfsElement and add it to the selection
				Object adapter = ((IAdaptable)element).getAdapter(IResource.class);
				if(adapter!=null) {
					selectedResources.add(adapter);
				}
			} else {
				// keep all other objects in the selection
				selectedResources.add(element);
			}
		}
		IStructuredSelection newSelection = new StructuredSelection(selectedResources);
		getContext().setSelection(newSelection);
		super.fillContextMenu(menu);
	}

}

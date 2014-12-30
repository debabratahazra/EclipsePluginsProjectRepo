package com.cdt.keil.debug.ui.viewAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class Register implements IWorkbenchWindowActionDelegate {

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {}

	@Override
	public void run(IAction action) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.RegisterView");
		} catch (PartInitException e) {}catch(Exception e){}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}

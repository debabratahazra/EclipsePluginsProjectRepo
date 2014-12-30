package com.cdt.keil.debug.ui.viewAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class FlashCodeMemory implements IWorkbenchWindowActionDelegate {
	
	IWorkbenchWindow window;

	@Override
	public void dispose() {}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window=window;
	}

	@Override
	public void run(IAction action) {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().
			showView("com.cdt.keil.debug.ui.FlashCodeMemoryView");
			
			/*WorkbenchPage page=(WorkbenchPage)window.getActivePage();			
			page.detachView(PlatformUI.getWorkbench().getActiveWorkbenchWindow().
					getActivePage().findViewReference("com.cdt.keil.debug.ui.FlashCodeMemoryView"));*/
		} catch (PartInitException e) {}
		catch(Exception e){}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {}

}

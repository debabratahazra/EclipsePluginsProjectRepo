package com.temenos.t24.tools.eclipse.basic.rtc.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.views.checkDependency.CheckDependencyView;

/**
* Action delegate for {@link CheckDependencyView}.
* 
* @author yasar
* 
*/

public class CheckDependencyActionDelegate implements IWorkbenchWindowActionDelegate {

    public void dispose() {
    }

    public void init(IWorkbenchWindow arg0) {
    }

    public void run(IAction arg0) {
        showView();
    }

    private static IViewPart showView() {
        IWorkbenchPage activePage = getActivePage();
        if (activePage == null) {
            return null;
        }
        try {
            return activePage.showView(CheckDependencyView.VIEW_ID);
        } catch (PartInitException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static IWorkbenchPage getActivePage() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        return window.getActivePage();
    }

    public void selectionChanged(IAction arg0, ISelection arg1) {
    }
}

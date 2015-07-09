package com.odcgroup.workbench.ui.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

import com.odcgroup.workbench.ui.internal.perspective.OfsPerspective;

abstract public class AbstractScopeToggleActionProvider extends CommonActionProvider {

    private ICommonViewerWorkbenchSite fWorkbenchSite;
    private Action action;
    private IToolBarManager toolBarManager;

    public void init(ICommonActionExtensionSite aSite) {
    	action = getAction();
    	fWorkbenchSite = (ICommonViewerWorkbenchSite)aSite.getViewSite();
    	fWorkbenchSite.getWorkbenchWindow().addPerspectiveListener(
    		    new IPerspectiveListener() {
    		        public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
    		            refreshActionBars();
    		        }

    		        public void perspectiveChanged(IWorkbenchPage iworkbenchpage, IPerspectiveDescriptor iperspectivedescriptor, String s) {}

    		    });
    }

    protected abstract Action getAction();

    private void refreshActionBars() {
        if(fWorkbenchSite != null && fWorkbenchSite.getActionBars() != null && fWorkbenchSite.getActionBars() != null) {
            fillActionBars(fWorkbenchSite.getActionBars());
            getDisplay().asyncExec(new Runnable() {
                public void run() {
                    fWorkbenchSite.getActionBars().updateActionBars();
                }
            });
        }
    }
    
    public void fillActionBars(IActionBars actionBars)
    {
        toolBarManager = actionBars.getToolBarManager();
        IPerspectiveDescriptor currentPerspective = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getPerspective();
        if(currentPerspective.getId().equals(OfsPerspective.PERSPECTIVE_ID)) {
            if(toolBarManager.find(action.getId()) == null) {
                String id;
                if(toolBarManager.getItems() != null && toolBarManager.getItems().length > 0)
                    id = toolBarManager.getItems()[0].getId();
                else
                    id = null;
                if(id == null)
                    toolBarManager.add(action);
                else
                    toolBarManager.insertBefore(id, action);
            }
        } else {
            toolBarManager.remove(action.getId());
        }
    }

    protected Display getDisplay()
    {
        if(fWorkbenchSite != null && fWorkbenchSite.getWorkbenchWindow() != null) {
            Shell shell = fWorkbenchSite.getWorkbenchWindow().getShell();
            if(shell != null && !shell.isDisposed())
                return shell.getDisplay();
        }
        if(Display.getCurrent() != null) {
            return Display.getCurrent();
        } else {
            return Display.getDefault();
        }
    }


}

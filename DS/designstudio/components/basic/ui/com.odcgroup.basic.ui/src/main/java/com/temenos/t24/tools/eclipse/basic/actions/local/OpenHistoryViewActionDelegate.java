package com.temenos.t24.tools.eclipse.basic.actions.local;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.history.IHistoryView;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * 
 * Opens a view with the current Active Editor's history.
 * 
 * @author lfernandez
 *
 */
public class OpenHistoryViewActionDelegate implements IWorkbenchWindowActionDelegate {

    private IWorkbenchWindow window;
    
    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
        this.window = window;
    }

    public void run(IAction action) {
        // Get the active page.
        if (window == null)
           return;
        IWorkbenchPage page = window.getActivePage();
        if (page == null)
           return;
     
        /** Get current active editor. */
        T24BasicMultiPageEditor ed = EditorDocumentUtil.getActiveMultiPageEditor();
        /** Retrieve the local file associated with it, for which we want to see the history. */
        IFile iFile = ((T24BasicEditor)ed.getSourceEditor()).getIFile();
        /** Finally open the view. */
        IHistoryView hv = TeamUI.getHistoryView();
        hv.showHistoryFor(iFile);
       
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
}

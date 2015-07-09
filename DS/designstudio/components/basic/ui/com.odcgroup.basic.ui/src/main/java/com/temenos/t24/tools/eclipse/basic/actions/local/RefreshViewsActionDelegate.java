package com.temenos.t24.tools.eclipse.basic.actions.local;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.IDocViewProvider;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.views.IT24View;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants;

public class RefreshViewsActionDelegate implements IWorkbenchWindowActionDelegate {

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
        refreshActiveViews(page, ViewConstants.T24_VIEW_IDS);
        /**
         * Editors have two pages; full code and code only (i.e. comments
         * removed), this refreshes the latter
         */
        refreshCodeOnlyViews();
    }

    /**
     * Refreshes all the CodeOnly pages of all T24BasicMultiPageEditor opened.
     */
    public void refreshCodeOnlyViews() {
        IEditorReference[] refs = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();
        for (int i = 0; i < refs.length; i++) {
            if (refs[i].getEditor(false) instanceof T24BasicMultiPageEditor) {
                T24BasicMultiPageEditor multiPageEditor = (T24BasicMultiPageEditor) refs[i].getEditor(true);
                multiPageEditor.refreshCodeOnlyDoc();
            }
        }
    }

    /**
     * Looks for the active views whose IDs are in the array passed and refresh
     * them.
     * 
     * @param page - current workbench active page.
     * @param viewIds - IDs of the views to be refreshed if they are active.
     */
    public void refreshActiveViews(IWorkbenchPage page, String[] viewIds) {
        for (int i = 0; i < viewIds.length; i++) {
            IT24View view = (IT24View) page.findView(viewIds[i]);
            if (view != null) {
                view.refresh();
            }
        }
        IDocViewProvider provider = T24BasicPlugin.getDefault().getProvider();
        if(provider != null) {
            provider.refreshView();
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }
}

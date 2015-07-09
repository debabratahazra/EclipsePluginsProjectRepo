package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.actions.ActionDelegate;

import com.odcgroup.mdf.editor.MdfPlugin;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupport;
import com.odcgroup.mdf.editor.ui.dialogs.EditionSupportFactory;

/**
 * @version 1.0
 * @author Glaude
 */
public class MdfActionDelegate
    extends ActionDelegate
    implements IWorkbenchWindowActionDelegate, IViewActionDelegate, IEditorActionDelegate {
	private IWorkbenchWindow window = null;
    private EditionSupport editionSupport;

    /**
     * Constructor for MdfActionDelegate
     *
     *
     */
    public MdfActionDelegate() {
        super();
    }

    /**
     * @see org.eclipse.ui.IViewActionDelegate#init(org.eclipse.ui.IViewPart)
     */
    public void init(IViewPart view) {
        window = view.getViewSite().getWorkbenchWindow();
        editionSupport = (EditionSupport) view.getAdapter(EditionSupport.class);
    }

    /**
     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
     */
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        window = targetEditor.getEditorSite().getWorkbenchWindow();
        editionSupport = (EditionSupport) targetEditor.getAdapter(EditionSupport.class);
    }

    /**
     * DS-1349
     * @return
     */
    protected EditionSupport getEditionSupport() {
        if (editionSupport != null) {
            return editionSupport;
        } else {
            return EditionSupportFactory.INSTANCE(getWindow());
        }
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init(IWorkbenchWindow window) {
		this.window = window;
    }

    public IWorkbenchWindow getWindow() {
        return (window != null ? window :
        	MdfPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow());
    }

}

package com.odcgroup.mdf.editor.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import com.odcgroup.mdf.editor.ui.editors.DomainModelEditor;

public class BackSelectionAction implements IEditorActionDelegate {

    private IEditorPart activeEditorPart;

    /**
     * 
     */
    public BackSelectionAction() {
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
     */
    public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        activeEditorPart = targetEditor;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        if (activeEditorPart instanceof DomainModelEditor) {
        	DomainModelEditor editor = (DomainModelEditor) activeEditorPart;
        	Object obj = editor.getSelectionStack().getPrevSelection();
        	if (obj != null){
	            TreeViewer viewer = (TreeViewer) editor.getViewer();
	            StructuredSelection selection = new StructuredSelection(obj);
	            viewer.setSelection(selection, true);
	            viewer.setExpandedState(obj, true);
        	}
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
    	// none
    }

}

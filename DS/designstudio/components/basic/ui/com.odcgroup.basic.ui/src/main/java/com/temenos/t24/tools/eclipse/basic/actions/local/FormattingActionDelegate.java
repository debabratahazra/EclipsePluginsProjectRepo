package com.temenos.t24.tools.eclipse.basic.actions.local;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.formatting.T24FormattingStrategy;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

public class FormattingActionDelegate implements IWorkbenchWindowActionDelegate {

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

        /** Get current text selected by cursor. Will be used to restore cursor to that 
         * position after formatting */
        TextSelection selectedText = getTextSelection();
       
        /** Perform the formatting, and replace contents with the formatted result. */
        IDocument doc = EditorDocumentUtil.getActiveDocument();
        T24FormattingStrategy fs = new T24FormattingStrategy();
        String result = fs.format(doc.get());
        doc.set(result);          
       
        /** Restore cursor to old position */
        restoreTextSelection(selectedText);

    }
    
    
    /**
     * Sets cursor to its original position
     * @param selectedText
     */
    private void restoreTextSelection(TextSelection selectedText) {
        if(selectedText != null){
            T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
            editor.getEditorSite().getSelectionProvider().setSelection(selectedText);
        }        
    }

    /**
     * @return Text selected with cursor in current active editor. Null if nothing selected.
     */
    private TextSelection getTextSelection() {
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        TextSelection selectedText = null;
        if (editor != null) {
            selectedText = (TextSelection) editor.getEditorSite()
                .getSelectionProvider().getSelection();
        }
        return selectedText;
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
    }
}


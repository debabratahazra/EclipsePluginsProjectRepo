package com.temenos.t24.tools.eclipse.basic.actions.local; 

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

public class InsertCommentActionDelegate implements IWorkbenchWindowActionDelegate {
    private final static String COMMENT_LINE = "*-----------------------------------------------------------------------------\n";
    
    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }

    public void run(IAction action) {
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        if (editor != null) {
            TextSelection selectedText = (TextSelection) editor.getEditorSite()
                .getSelectionProvider().getSelection();

            // Cursor position
            int cursorPos = selectedText.getOffset();
            IDocument doc = EditorDocumentUtil.getDocument(editor);
            try{
                int curLine = doc.getLineOfOffset(cursorPos);
                int lineOffset = doc.getLineOffset(curLine);
                doc.replace(lineOffset, 0, COMMENT_LINE);
            } catch(BadLocationException  e){
                e.printStackTrace();
            }
        }
    }

    
    public void selectionChanged(IAction action, ISelection selection) {
    }
}

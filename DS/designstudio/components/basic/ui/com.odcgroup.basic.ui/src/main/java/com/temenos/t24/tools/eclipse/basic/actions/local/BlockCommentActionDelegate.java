package com.temenos.t24.tools.eclipse.basic.actions.local; 

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * Adds a comment line above current cursor position. 
 * 
 * @author lfernandez
 *
 */
public class BlockCommentActionDelegate implements IWorkbenchWindowActionDelegate {
    
    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }

    public void run(IAction action) {
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        if (editor != null) {
            TextSelection selectedText = (TextSelection) editor.getEditorSite()
                .getSelectionProvider().getSelection();

            int startLine = selectedText.getStartLine();
            int endLine   = selectedText.getEndLine();
            
            IDocument doc = EditorDocumentUtil.getDocument(editor);
            try{
                for(int i=startLine; i<=endLine; i++){
                    
                    IRegion lineRegion = doc.getLineInformation(i);
                    int lineOffset = lineRegion.getOffset();
                    int lineLength = lineRegion.getLength();
                    String lineContents = doc.get(lineOffset, lineLength);
                    lineContents = "*"+lineContents;
                    doc.replace(lineOffset, lineLength, lineContents);
                }
            } catch(BadLocationException  e){
                e.printStackTrace();
            }
        }
    }
    
    public void selectionChanged(IAction action, ISelection selection) {
    }
}

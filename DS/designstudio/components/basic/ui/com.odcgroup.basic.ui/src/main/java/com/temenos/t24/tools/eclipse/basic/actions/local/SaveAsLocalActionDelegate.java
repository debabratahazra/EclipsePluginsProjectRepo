package com.temenos.t24.tools.eclipse.basic.actions.local;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.editors.remote.T24RemoteFileEditor;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

public class SaveAsLocalActionDelegate implements IWorkbenchWindowActionDelegate {

    public static final String ACTION_ID = "eclipse.basic.actions.saveAsLocal";
    private ISelection selection = null;
    // Current editor
    private T24BasicMultiPageEditor multiPageEditor = null;
    // Current file
    private IFile iFile = null;

    public void dispose() {
        // TODO Auto-generated method stub
    }

    public void init(IWorkbenchWindow window) {
        // TODO Auto-generated method stub
    }

    public void run(IAction action) {
        if (selection != null && (selection instanceof IStructuredSelection)) {
            multiPageEditor = getCurrentEditor();
            if (multiPageEditor != null) {
                performLocalSave(multiPageEditor);
            }
        } else {
            multiPageEditor = EditorDocumentUtil.getActiveMultiPageEditor();
            if (multiPageEditor != null) {
                performLocalSave(multiPageEditor);
            }
        }
    }

    /**
     * Performs the save in local with a dialog to change the location
     * 
     * @param multiPageEditor editor holds the file to be saved
     */
    private void performLocalSave(T24BasicMultiPageEditor multiPageEditor) {
        T24RemoteFileEditor fullEditor = multiPageEditor.getSourceEditor();
        if (!fullEditor.isLocal()) {
            fullEditor.setLocal(true);
            fullEditor.setRemoteSite(null);
            fullEditor.setServerDirectory("");
            RemoteFileEditorUtil.completeDocument(fullEditor);
            multiPageEditor.performSaveAsLocal();
        } else {
            multiPageEditor.doSaveAs();
        }
        // Set the currently saved file to the editor as this is an entirely new
        // file.
        String filePath = EditorDocumentUtil.getActiveEditorLocation(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
        IFile newFile = EditorDocumentUtil.getIFile(filePath);
        fullEditor.setIFile(newFile);
    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
        setIFile();
        // SaveAsLocal enabled only if the chosen file is in the current editor.
        action.setEnabled(!selection.isEmpty() && getCurrentEditor() != null);
    }

    /**
     * Returns the current open multipage editor of the current file
     * 
     * @return multipage editor
     */
    private T24BasicMultiPageEditor getCurrentEditor() {
        if (iFile != null) {
            // get the editor of the given file
            multiPageEditor = EditorDocumentUtil.findOpenMultipageEditor(iFile);
        }
        return multiPageEditor;
    }

    /**
     * Sets the selected file as the current IFile
     */
    private void setIFile() {
        if (selection != null && (selection instanceof IStructuredSelection)) {
            Object obj = (Object) ((IStructuredSelection) selection).getFirstElement();
            if ((obj instanceof IFile)) {
                iFile = (IFile) obj;
                return;
            }
        }
        T24BasicEditor editor = EditorDocumentUtil.getActiveEditor();
        if (editor != null) {
            iFile = editor.getIFile();
        }
    }
}

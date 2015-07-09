package com.temenos.t24.tools.eclipse.basic.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * An EditorInput It is not a model, it is a description of
 * the model source for an <code>IEditorPart</code>.
 */
public class T24BasicEditorInput implements IEditorInput {
    /** Contents of a BASIC file. */
    private String contents;
    /** Filename without prefix (e.g. ACCOUNT.MODULE) */
    private String basicFilenameNoPrefix;
    
    public T24BasicEditorInput(String basicFilenameNoPrefix, String contents){
        this.basicFilenameNoPrefix = basicFilenameNoPrefix;
        this.contents = contents;
    }
    
    public String getContents() {
        return contents;
    }
    
    public boolean exists() {
        return true;
    }

    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    public String getName() {
        return this.basicFilenameNoPrefix;
    }

    public IPersistableElement getPersistable() {
        return null;
    }

    public String getToolTipText() {
        return "T24Basic Editor";
    }

    @SuppressWarnings("rawtypes")
    public Object getAdapter(Class adapter) {
        T24BasicEditor editor = (T24BasicEditor)EditorDocumentUtil.getActiveEditor();
        if (editor != null) {
            return editor.getAdapter(adapter);
        } else {
            return null;
        }
    }
    // ***********************************
    
}

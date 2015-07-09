package com.temenos.t24.tools.eclipse.basic.editors.remote;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

/**
 * A form editor which displays the T24Data files [with extension .d].
 * 
 * @author ssethupathi
 */
public class T24DataFileEditor extends FormEditor {

    private IEditorInput input;
    private IStorage storage;
    private T24DataFileModel model;

    public void init(IEditorSite site, IEditorInput input) {
        try {
            super.init(site, input);
        } catch (PartInitException e) {
            e.printStackTrace();
        }
        this.input = input;
        boolean isLocal = true;
        if (input instanceof FileEditorInput) {
            storage = ((FileEditorInput) input).getStorage();
        } else
            isLocal = false;
        if (storage != null)
            constructModel(isLocal);
    }

    protected void addPages() {
        try {
            addPage(new T24DataPage(this, model));
        } catch (PartInitException e) {
            e.printStackTrace();
        }
        setPartName(input.getName());
    }

    public void doSave(IProgressMonitor monitor) {
    }

    public void doSaveAs() {
    }

    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Returns the associated {@link T24DataFileModel} object.
     * 
     * @return model.
     */
    public T24DataFileModel getModel() {
        return model;
    }

    private void constructModel(boolean isLocal) {
        model = new T24DataFileModel(storage, isLocal);
    }
}

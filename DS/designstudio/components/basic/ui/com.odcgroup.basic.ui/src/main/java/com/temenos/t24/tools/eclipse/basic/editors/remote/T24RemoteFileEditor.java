package com.temenos.t24.tools.eclipse.basic.editors.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.actions.remote.SaveAsRemoteActionDelegate;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicEditor;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;

/**
 * {@link T24RemoteFileEditor} is the default text editor for the
 * {@link IRemoteFile} which is stored into local project. This editor extends
 * the {@link T24BasicEditor} thus providing editor functionalities for a T24
 * Basic file from a {@link RemoteSite}.
 * 
 * @author ssethupathi
 * 
 */
public class T24RemoteFileEditor extends T24BasicEditor {

    private RemoteSite remoteSite;

    /**
     * Associates a {@link RemoteSite} to this editor.
     * 
     * @param remoteSite
     */
    public void setRemoteSite(RemoteSite remoteSite) {
        this.remoteSite = remoteSite;
    }

    /**
     * Returns the associated {@link RemoteSite}.
     * 
     * @return remote site
     */
    public RemoteSite getRemoteSite() {
        return remoteSite;
    }

    /**
     * {@inheritDoc}
     */
    public void doSave(IProgressMonitor monitor) {
        RemoteFileEditorUtil.completeDocument(this);
        super.doSave(monitor);
        if (isLocal()) {
            return;
        }
        save();
    }

    /**
     * {@inheritDoc}
     */
    public void doSaveAs() {
        RemoteFileEditorUtil.completeDocument(this);
        if (isLocal()) {
            super.doSaveAs();
            return;
        }
        SaveAsRemoteActionDelegate actionDelegate = new SaveAsRemoteActionDelegate();
        actionDelegate.saveEditor(this);
        return;
    }

    /**
     * Saves the editor contents into local file before saving into it's
     * {@link RemoteSite}.
     * 
     * @return true is saved, and false otherwise.
     */
    public void doSave() {
        RemoteFileEditorUtil.completeDocument(this);
        super.doSave(null);
    }

    /**
     * Saves the contents of the associated {@link IFile} into the
     * {@link RemoteSite}.
     * 
     * @return true if saved, false otherwise.
     */
    private boolean save() {
        boolean saved = RemoteFileEditorUtil.saveEditorContents(this);
        if (saved) {
            RemoteOperationsLog.info("File " + getBasicFilenameNoPrefix() + " saved successfully");
            return true;
        } else {
            RemoteOperationsLog.info("Unable to save file " + getBasicFilenameNoPrefix());
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            T24MessageDialog dialog = new T24MessageDialog(window.getShell(), "Error", "Unable to save file", MessageDialog.ERROR);
            dialog.open();
        }
        return false;
    }

}

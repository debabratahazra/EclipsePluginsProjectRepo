package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24DataFileTransfer;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * Does check-in T24 data items into RTC source control Currently in PROOF OF
 * CONCEPT stage only
 * 
 * @author ssethupathi
 * 
 */
public class GetFromT24ActionDelegate implements IWorkbenchWindowActionDelegate {

    private IFile file;

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }

    public void run(IAction action) {
        if (file == null) {
            return;
        }
        String localPath = file.getLocation().toOSString();
        String localName = file.getName();
        boolean received = T24DataFileTransfer.getInstance().getFromT24(localPath, localName);
        if (received) {
            EditorDocumentUtil.getIFile(localPath);
            RemoteOperationsLog.info("File " + localName + " received from T24 Server");
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        file = null;
        if (selection instanceof IStructuredSelection) {
            if (((IStructuredSelection) selection).getFirstElement() instanceof IFile) {
                file = (IFile) ((IStructuredSelection) selection).getFirstElement();
            }
        }
        boolean enable = false;
        if (file != null) {
            String fileName = file.getName();
            if (fileName.endsWith(".d") && fileName.contains("!")) {
                enable = true;
            }
        }
        action.setEnabled(enable);
    }
}

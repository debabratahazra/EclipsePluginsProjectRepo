package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24DataFileTransfer;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;

/**
 * Does Check out T24 data items from RTC source control<br>
 * Currently in PROOF OF CONCEPT stage only
 * 
 * @author ssethupathi
 * 
 */
public class SendToT24ActionDelegate implements IWorkbenchWindowActionDelegate {

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
        boolean sent = T24DataFileTransfer.getInstance().sendToT24(localPath, localName);
        if (sent) {
            RemoteOperationsLog.info("File " + localName + " sent to T24 Server");
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
        String fileContent = "";
        
        if (file != null) {
        try {
            fileContent = new StringUtil().getStringFromInputStream(file.getContents());
        } catch (CoreException e) {
            e.printStackTrace();
        }
        String fileName = file.getName();
            if (fileName.endsWith(".d") && fileName.contains("!") && fileContent.length()>0) {
                enable = true;
            }
        }
        action.setEnabled(enable);
    }
}

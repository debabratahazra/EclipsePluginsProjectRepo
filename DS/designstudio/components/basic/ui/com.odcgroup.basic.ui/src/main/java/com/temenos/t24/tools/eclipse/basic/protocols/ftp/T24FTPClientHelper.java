package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.io.File;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.actions.ActionsUtils;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;

/**
 * Helper class for the T24FTP client
 * 
 * @author ssethupathi
 * 
 */
public final class T24FTPClientHelper {

    private T24FTPClientHelper() {
    }

    /**
     * Returns the name in which the remote file to be saved locally. If it is a
     * Basic file, it adds .b extension.
     * 
     * @param remoteName name of the remote file
     * @return local file name
     */
    public static synchronized String getLocalFileName(String remoteName) {
        String localName = remoteName;
        // TODO - Files are not classified currently. For future use.
        // for (String ext : FTPClientImplConstants.NON_BASIC_FILE_EXTENSIONS) {
        // if (remoteName.endsWith(ext)) {
        // return localName;
        // }
        // }
        return localName + ".b";
    }

    /**
     * NOT USED NOW.
     * 
     * @param localPath
     * @return
     */
    public static boolean canSave(String localPath) {
        if (!ActionsUtils.isPromptOverwriting()) {
            return true;
        }
        File file = new File(localPath);
        if (file.isFile() && canOverwrite(localPath)) {
            return true;
        }
        return false;
    }

    /**
     * NOT USED NOW.
     * 
     * @param localPath
     * @return
     */
    private static boolean canOverwrite(String localPath) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window != null) {
            String message = String.format("File %s already exists. Are you sure you want to overwrite?", localPath);
            T24MessageDialog dialog = new T24MessageDialog(window.getShell(), "File exists", message, MessageDialog.ERROR);
            if (dialog.open() == InputDialog.OK) {
                return true;
            }
        }
        return false;
    }
}

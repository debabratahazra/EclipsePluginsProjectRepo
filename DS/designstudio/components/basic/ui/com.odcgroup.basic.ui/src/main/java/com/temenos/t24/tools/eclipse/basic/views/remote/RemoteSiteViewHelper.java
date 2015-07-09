package com.temenos.t24.tools.eclipse.basic.views.remote;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.actions.remote.T24UnitTestActionDelegate;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.FTPClientImplConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IRemoteFile;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteFileTransferUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.t24unit.TestFile;
import com.temenos.t24.tools.eclipse.basic.views.tree.IT24TreeViewNode;

public final class RemoteSiteViewHelper {

    public static boolean openSelected(RemoteSiteViewController viewcontroller, RemoteFileNode selectedNode) {
        IRemoteFile remoteFile = selectedNode.getRemoteFile();
        IT24FTPClient ftpClient = viewcontroller.getFtpClient();
        String localSavedPath = "";
        try {
            localSavedPath = RemoteOperationsManager.getInstance().retrieveFile(ftpClient, remoteFile.getFullPath(),
                    RemoteFileTransferUtil.getDefaultLocalPath(remoteFile.getName()));
        } catch (RemoteOperationsException e) {
            informUser(FTPClientImplConstants.ERROR_DIALOG_CAPTION, e.getMessage());
            return false;
        }
        String errorMsg = "";
        if (localSavedPath != null && localSavedPath.length() > 0) {
            IRemoteFile parentFile = getParent(selectedNode);
            String parentPath = "";
            if (parentFile == null) {
                parentPath = ftpClient.getHomeDir();
            } else {
                parentPath = parentFile.getFullPath();
            }
            errorMsg = RemoteFileEditorUtil.openFileWithEditor(ftpClient.getRemoteSite(), parentPath, localSavedPath);
        } else {
            informUser("Retrieve failed", "Retrieve file operation failed");
            RemoteOperationsLog.error("File " + selectedNode.getLabel() + " failed to retrieve");
            return false;
        }
        if (errorMsg != null && !errorMsg.equals("")) {
            informUser("Open failed", "Unable to open file " + localSavedPath + "\n" + errorMsg);
            RemoteOperationsLog.error("File " + selectedNode.getLabel() + " failed to open");
            return false;
        }
        RemoteOperationsLog.info("File " + selectedNode.getLabel() + " opened successfully");
        return true;
    }

    public static boolean deleteSelected(RemoteSiteViewController viewController, RemoteFileNode selectedNode) {
        IRemoteFile remoteFile = selectedNode.getRemoteFile();
        String title = "Confirm Delete";
        String msg = "Are you sure you want to delete the file " + remoteFile.getFullPath() + " ?";
        boolean deleted = false;
        if (confirmOperation(title, msg)) {
            deleted = viewController.deleteFile(remoteFile);
            if (!deleted) {
                informUser("Delete failed", "Unable to delete file.");
                RemoteOperationsLog.info("File " + selectedNode.getLabel() + " failed to delete");
                return false;
            } else {
                RemoteOperationsLog.info("File " + selectedNode.getLabel() + " deleted successfully");
                return true;
            }
        }
        return false;
    }

    public static boolean runAsTest(RemoteSiteViewController viewController, RemoteFileNode selectedNode) {
        if (selectedNode.isDirectory()) {
            informUser("Currently not supported", "Executing a directory of tests is not supported now!");
            return false;
        }
        ArrayList<TestFile> testFiles = new ArrayList<TestFile>();
        IT24TreeViewNode treeNode = selectedNode.getParent();
        String parentDir = null;
        if (treeNode == null) {
            parentDir = viewController.getFtpClient().getHomeDir();
        }
        if (treeNode instanceof RemoteFileNode) {
            parentDir = ((RemoteFileNode) treeNode).getRemoteFile().getFullPath();
            if (parentDir == null || parentDir.length() <= 0) {
                String currPath = selectedNode.getRemoteFile().getFullPath();
                int endIndex = currPath.lastIndexOf('/');
                parentDir = currPath.substring(0, endIndex);
            }
        }
        TestFile testFile = new TestFile(null, selectedNode.getLabel(), selectedNode.getSite(), parentDir, false);
        testFiles.add(testFile);
        T24UnitTestActionDelegate actionDelegate = new T24UnitTestActionDelegate();
        actionDelegate.run(testFiles);
        return true;
    }

    private static boolean confirmOperation(String dialogTitle, String dialogMessage) {
        T24MessageDialog confirmDialog = new T24MessageDialog(getShell(), dialogTitle, dialogMessage, MessageDialog.WARNING);
        if (confirmDialog.open() != InputDialog.OK) {
            return false;
        }
        return true;
    }

    private static void informUser(String dialogTitle, String dialogMessage) {
        T24MessageDialog informDialog = new T24MessageDialog(getShell(), dialogTitle, dialogMessage, MessageDialog.INFORMATION);
        informDialog.open();
    }

    private static Shell getShell() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            return null;
        }
        return window.getShell();
    }

    private static IRemoteFile getParent(RemoteFileNode node) {
        IRemoteFile parentFile = null;
        if (node.getParent() instanceof RemoteFileNode) {
            RemoteFileNode parentNode = (RemoteFileNode) node.getParent();
            parentFile = parentNode.getRemoteFile();
        }
        return parentFile;
    }
}

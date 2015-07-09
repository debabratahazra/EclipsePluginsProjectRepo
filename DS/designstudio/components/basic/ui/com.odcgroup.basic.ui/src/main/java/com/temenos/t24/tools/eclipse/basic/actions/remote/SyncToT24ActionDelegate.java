package com.temenos.t24.tools.eclipse.basic.actions.remote;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.dialogs.progress.T24ProgressMonitorDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.SyncToRemoteDialog;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.OperationType;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItem;
import com.temenos.t24.tools.eclipse.basic.remote.data.ItemType;
import com.temenos.t24.tools.eclipse.basic.remote.data.RemoteInstallLogger;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24DataTransferHelper;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24RemoteItemInstaller;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24RemoteItemTransfer;

/**
 * Triggers the Sync to server operation.<br>
 * It will either transfer the file structure as it is or install into to the
 * remote site T24 environment.
 * 
 * @author ssethupathi
 * 
 */
public class SyncToT24ActionDelegate implements IWorkbenchWindowActionDelegate {

    private IContainer containerSelected;
    private List<InstallableItem> sourceItems;
    private List<InstallableItem> dataItems;
    private List<InstallableItem> undefinedItems;
    private List<InstallableItem> xmlItems;

    public SyncToT24ActionDelegate() {
        sourceItems = new ArrayList<InstallableItem>();
        dataItems = new ArrayList<InstallableItem>();
        undefinedItems = new ArrayList<InstallableItem>();
        xmlItems = new ArrayList<InstallableItem>();
    }

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }

    /**
     * Gets the option from user either to transfer or install. Creates a
     * runnable with dialog to do it.
     */
    public void run(IAction action) {
        initItemsList();
        SyncToRemoteDialog dialog = new SyncToRemoteDialog(getShell());
        if (dialog.open() == InputDialog.CANCEL) {
            return;
        }
        String siteName = dialog.getSiteName();
        String remoteDir = dialog.getSourceDirectory();
        OperationType opType = dialog.getSyncType();
        IRunnableWithProgress progressDialog = new RunnableWithProgress(siteName, remoteDir, opType);
        T24ProgressMonitorDialog progressMonitorDialog = new T24ProgressMonitorDialog(getShell(), true);
        try {
            progressMonitorDialog.run(true, true, progressDialog);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            RemoteOperationsLog.error(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
            RemoteOperationsLog.error(e.getMessage());
        }
    }

    /**
     * Performs the transfer/install operation.
     * 
     * @param siteName
     * @param remoteDir
     * @param opType
     * @param monitor
     */
    private void performTransfer(String siteName, String remoteDir, OperationType opType, IProgressMonitor monitor) {
        monitor.subTask("Building files list...");
        addInstallableItems(containerSelected);
        String containerRoot = getContainerRoot();
        String remoteRoot = getRemoteRoot(siteName, remoteDir);
        if (remoteRoot == null) {
            return;
        }
        String remoteDataDir = T24DataTransferHelper.getInstance().getDataTempDir(siteName);
        List<String> transferredSourceFiles = null;
        List<String> transferredDataFiles = null;
        if (opType.equals(OperationType.INSTALL)) {
            monitor.subTask("Waiting to transfer files...");
            if (sourceItems.size() > 0) {
                transferredSourceFiles = T24RemoteItemInstaller.getInstance().installSource(sourceItems, remoteRoot, siteName,
                        monitor);
            }
            if (dataItems.size() > 0) {
                transferredDataFiles = T24RemoteItemInstaller.getInstance()
                        .installData(dataItems, remoteDataDir, siteName, monitor);
            }
            if (xmlItems.size() > 0) {
                transferredDataFiles = T24RemoteItemInstaller.getInstance()
                        .installData(xmlItems, remoteDataDir, siteName, monitor);
            }
        } else {
            monitor.subTask("Transfering files to remote site " + siteName + "...");
            int totalItems = 0;
            if (sourceItems.size() > 0) {
                transferredSourceFiles = T24RemoteItemTransfer.getInstance().transfer(sourceItems, containerRoot, remoteRoot,
                        siteName, monitor);
                totalItems += transferredSourceFiles.size();
            }
            if (dataItems.size() > 0) {
                transferredDataFiles = T24RemoteItemTransfer.getInstance().transfer(dataItems, containerRoot, remoteRoot, siteName,
                        monitor);
                totalItems += transferredDataFiles.size();
            }
            if (xmlItems.size() > 0) {
                transferredDataFiles = T24RemoteItemTransfer.getInstance().transfer(xmlItems, containerRoot, remoteRoot, siteName,
                        monitor);
                totalItems += transferredDataFiles.size();
            }
            RemoteOperationsLog.info("Total number of files transfered " + totalItems);
        }
        RemoteInstallLogger.getInstance().generateInfoFile(transferredSourceFiles, transferredDataFiles, siteName);
    }

    /**
     * {@inheritDoc}
     */
    public void selectionChanged(IAction action, ISelection selection) {
        boolean enable = false;
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection selection1 = (IStructuredSelection) selection;
            Object obj = selection1.getFirstElement();
            if (obj instanceof IFolder || obj instanceof IProject) {
                containerSelected = (IContainer) obj;
                enable = true;
            }
        }
        action.setEnabled(enable);
    }

    /**
     * Recursively adds resource to the list.
     */
    private void addInstallableItems(IResource resource) {
        String fileName = null;
        String localPath = null;
        if (resource.getType() == IResource.FILE) {
            fileName = resource.getName();
            localPath = resource.getLocation().toOSString();
            InstallableItem item = new InstallableItem(fileName, localPath);
            if (item.getType().equals(ItemType.DATA)) {
                dataItems.add(item);
            } else if (item.getType().equals(ItemType.SOURCE)) {
                sourceItems.add(item);
            } else if (item.getType().equals(ItemType.XML)) {
                xmlItems.add(item);
            } else {
                undefinedItems.add(item);
            }
        } else if (resource instanceof IContainer) {
            try {
                IResource[] resources = ((IContainer) resource).members();
                for (IResource resource1 : resources) {
                    addInstallableItems(resource1);
                }
            } catch (CoreException e) {
                e.printStackTrace();
                RemoteOperationsLog.error(e.getMessage());
            }
        }
    }

    /**
     * Returns the selected root.
     * 
     * @return
     */
    private String getContainerRoot() {
        String tempPath = containerSelected.getLocation().toOSString();
        int lastIndex = tempPath.lastIndexOf('\\');
        if (lastIndex > 0) {
            tempPath = tempPath.substring(0, lastIndex);
        }
        return tempPath;
    }

    /**
     * Returns the root of the remote site.
     * 
     * @param remoteSiteName
     * @param remoteDir
     * @return
     */
    private String getRemoteRoot(String remoteSiteName, String remoteDir) {
        RemoteSite remoteSite = RemoteSitesManager.getInstance().getRemoteSiteFromName(remoteSiteName);
        String root = RemoteFileEditorUtil.calculateRemotePath(remoteDir, remoteSite);
        return root;
    }

    private Shell getShell() {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        return window.getShell();
    }

    private void initItemsList() {
        sourceItems.clear();
        dataItems.clear();
        undefinedItems.clear();
        xmlItems.clear();
    }

    /**
     * Runs the transfer/install operation using a monitored dialog.
     * 
     */
    private class RunnableWithProgress implements IRunnableWithProgress {

        private String siteName;
        private String remoteDir;
        private OperationType opType;

        public RunnableWithProgress(String siteName, String remoteDir, OperationType opType) {
            this.siteName = siteName;
            this.remoteDir = remoteDir;
            this.opType = opType;
        }

        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
            int totalUnitsOfWork = IProgressMonitor.UNKNOWN;
            monitor.beginTask("Performing sync. Please wait...", totalUnitsOfWork);
            performTransfer(siteName, remoteDir, opType, new SubProgressMonitor(monitor, 1000));
            monitor.done();
        }
    }
}

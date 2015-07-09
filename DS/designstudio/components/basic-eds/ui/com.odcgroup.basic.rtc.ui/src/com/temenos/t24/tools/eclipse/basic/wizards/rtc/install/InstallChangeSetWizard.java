/**
 * 
 */
package com.temenos.t24.tools.eclipse.basic.wizards.rtc.install;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;

import com.temenos.t24.tools.eclipse.basic.dialogs.progress.T24ProgressMonitorDialog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.T24LocalhostClient;
import com.temenos.t24.tools.eclipse.basic.remote.data.InstallableItemCollection;
import com.temenos.t24.tools.eclipse.basic.remote.data.RemoteInstallLogger;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24DataTransferHelper;
import com.temenos.t24.tools.eclipse.basic.remote.data.T24RemoteItemInstaller;

/**
 * @author ssethupathi
 * 
 */
public class InstallChangeSetWizard extends Wizard {

    private InstallChangeSetPage page1;
    private ChangeSetTreePage page2;
    private int workItemRef;
    private String repositoryURI;

    public InstallChangeSetWizard() {
    }

    public int getWorkItemRef() {
        return workItemRef;
    }

    public String getRepositoryURI() {
        return repositoryURI;
    }

    public void setWorkItemRef(int workItemRef) {
        this.workItemRef = workItemRef;
    }

    public void setRepositoryURI(String repositoryURI) {
        this.repositoryURI = repositoryURI;
    }

    public ChangesetInstallerViewController getViewController() {
        return page1.getViewController();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performFinish() {
        ChangeSetInstallManager csInstallManager = page1.getInstallManager();
        InstallableItemCollection itemCollection = csInstallManager.retriveItems(page2.getSelectedItems());
        String siteName = RemoteSitesManager.getInstance().getDefaultSiteName();
        if (siteName == null) {
            RemoteOperationsLog.error("Default remote site not found. Unable to connect to remote site.");
            return true;
        }
        RunnableWithProgress progressDialog = new RunnableWithProgress(itemCollection, siteName);
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
        return true;
    }

    private void performInstall(InstallableItemCollection itemCollection, String siteName, IProgressMonitor monitor) {
        List<String> transferredSourceFiles = null;
        List<String> transferredDataFiles = null;
        String remoteDir = "";
        IT24FTPClient remoteClient = RemoteSitesManager.getInstance().getDefaultFtpClient();
        if (itemCollection.getSourceItems().size() > 0) {
            remoteDir = RemoteOperationsManager.getInstance().getDefaultTempDirectory(remoteClient);
            if (remoteDir.equals("")) {
                return;
            }
            transferredSourceFiles = T24RemoteItemInstaller.getInstance().installSource(itemCollection.getSourceItems(), remoteDir,
                    siteName, monitor);
        }
        if (itemCollection.getDataItems().size() > 0) {
            remoteDir = T24DataTransferHelper.getInstance().getDataTempDir();
            if(remoteClient instanceof T24LocalhostClient){
            	remoteDir = remoteDir.replace("/", "\\");
            }
            transferredDataFiles = T24RemoteItemInstaller.getInstance().installData(itemCollection.getDataItems(), remoteDir,
                    siteName, monitor);
        }
        if (itemCollection.getXmlItems().size() > 0) {
            remoteDir = T24DataTransferHelper.getInstance().getDataTempDir();
            transferredDataFiles = T24RemoteItemInstaller.getInstance().installData(itemCollection.getXmlItems(), remoteDir,
                    siteName, monitor);
        }
        RemoteOperationsLog.info("Change Set installation completed");
        RemoteInstallLogger.getInstance().generateInfoFile(transferredSourceFiles, transferredDataFiles, siteName);
    }

    /**
     * {@inheritDoc}
     */
    public void addPages() {
        setWindowTitle("Install Change Set Wizard");
        page1 = new InstallChangeSetPage(this);
        addPage(page1);
        page2 = new ChangeSetTreePage(this);
        addPage(page2);
    }

    public boolean canFinish() {
        if (this.getContainer().getCurrentPage() == page2) {
            return true;
        }
        return false;
    }

    /**
     * Runs the transfer/install operation using a monitored dialog.
     * 
     */
    private class RunnableWithProgress implements IRunnableWithProgress {

        private InstallableItemCollection itemCollection;
        private String siteName;

        public RunnableWithProgress(InstallableItemCollection itemCollection, String siteName) {
            this.itemCollection = itemCollection;
            this.siteName = siteName;
        }

        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
            int totalUnitsOfWork = IProgressMonitor.UNKNOWN;
            monitor.beginTask("Performing Install. Please wait...", totalUnitsOfWork);
            performInstall(itemCollection, siteName, monitor);
            monitor.done();
        }
    }
}

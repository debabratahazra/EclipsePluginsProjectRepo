/**
 * 
 */
package com.temenos.t24.tools.eclipse.basic.remote.data;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;
import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.jremote.IJremoteClient;
import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsException;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;

/**
 * Helper class for the {@link T24DataFileTransfer}.
 * 
 * @author ssethupathi
 * 
 */
public class T24DataTransferHelper {

    private static T24DataTransferHelper HELPER = new T24DataTransferHelper();
    private static final String SERVER_VIEW_ID = "com.odcgroup.server.ui.views.ServerView";

    private T24DataTransferHelper() {
    }

    /**
     * Returns this instance.
     * 
     * @return instance.
     */
    public static T24DataTransferHelper getInstance() {
        return HELPER;
    }

    private RemoteSitesManager REMOTE_SITES_MANAGER = RemoteSitesManager.getInstance();

    /**
     * Checks if the remote site is available for the data item transfer
     * operation.
     * 
     * @return true if remote site available, false otherwise.
     */
    public boolean isSiteAvailable() {
        String siteName = REMOTE_SITES_MANAGER.getDefaultSiteName();
        if (siteName == null || siteName.length() <= 0) {
            try {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(SERVER_VIEW_ID);
            } catch (PartInitException e) {
                // Try to focus the Server View.
            }
            informFailure("Active Server not found.\nOpen the Server and set it as \"Active\" from Server View to proceed.");
            return false;
        }
        RemoteSite remoteSite = REMOTE_SITES_MANAGER.getRemoteSiteFromName(siteName);
        if (!remoteSite.getConnectionType().equals(RemoteConnectionType.JCA)) {
            informFailure("Connection type of the server: " + siteName
                    + " is not Jbase Agent.\nServers with no Jbase Agent are not supported now.");
            return false;
        }
        return true;
    }

    /**
     * Calls the respective subroutines to perform the get or send operations.
     * 
     * @param subroutine subroutine to be called
     * @param args arguments
     * @return return arguments
     */
    public String[] callSubroutine(String subroutine, String[] args) {
        String siteName = REMOTE_SITES_MANAGER.getDefaultSiteName();
        JSubroutineParameters params = getParameters(args);
        IJremoteClient remoteClient = REMOTE_SITES_MANAGER.getJremoteClient(siteName);
        if (checkRemote(remoteClient)) {
            JSubroutineParameters returnParams = callNewThread(remoteClient, subroutine, params);
            if(returnParams!=null){            
            return getStringParams(returnParams);
            }
        }
        return null;
    }

    /**
     * Gets the data item from the temp folder (DIM.TEMP/DATA) in remote site.
     * 
     * @param localPath local path.
     * @param remotePath remote path.
     * @return local path if retrieved.
     */
    public String retriveFile(String localPath, String remotePath) {
        String siteName = REMOTE_SITES_MANAGER.getDefaultSiteName();
        RemoteSite remoteSite = REMOTE_SITES_MANAGER.getRemoteSiteFromName(siteName);
        try {
            localPath = RemoteOperationsManager.getInstance().retrieveFile(remoteSite, remotePath, localPath);
        } catch (RemoteOperationsException e) {
            informFailure(e.getMessage());
            return null;
        }
        return localPath;
    }

    /**
     * Puts the data item in to the temp folder (DIM.TEMP/DATA) in remote site.
     * 
     * @param localPath local path.
     * @param remotePath remote path.
     * @param fileName data item name.
     * @return true if sent successfully, false otherwise.
     */
    public boolean sendFile(String localPath, String remotePath, String fileName) {
        IFile file = EditorDocumentUtil.getIFile(localPath);
        String siteName = REMOTE_SITES_MANAGER.getDefaultSiteName();
        RemoteSite remoteSite = REMOTE_SITES_MANAGER.getRemoteSiteFromName(siteName);
        boolean sent = false;
        try {
            sent = RemoteOperationsManager.getInstance().saveFile(remoteSite, remotePath, fileName, file);
        } catch (RemoteOperationsException e) {
            informFailure(e.getMessage());
        }
        return sent;
    }

    /**
     * Returns the T24 file name from the data item name passed. For example,
     * F.SPF would be returned if called with F.SPF!SYSTEM
     * 
     * @param t24Name data item name.
     * @return T24 file name.
     */
    public String getT24FileName(String t24Name) {
        if (t24Name == null) {
            return null;
        }
        int index = t24Name.indexOf('!');
        if (index < 0) {
            return null;
        }
        String fileName = t24Name.substring(0, index);
        return fileName;
    }

    /**
     * Returns the T24 record key from the data item name passed. For example,
     * SYSTEM would be returned if called with F.SPF!SYSTEM. if
     * 
     * @param t24Name data item name.
     * @return T24 record key.
     */
    public String getT24RecordKey(String t24Name) {
        if (t24Name == null) {
            return null;
        }
        int index = t24Name.indexOf('!');
        if (index < 0) {
            return null;
        }
        int length = t24Name.length();
        String fileName = t24Name.substring(index + 1, length);
        return fileName;
    }

    /**
     * Returns the temp data directory in the default remote site. This would be, for
     * example, /devareas/user1/TestBase/DIM.TEMP/DATA
     * 
     * @return temp data directory.
     */
    public String getDataTempDir() {
        String siteName = REMOTE_SITES_MANAGER.getDefaultSiteName();
        return getDataTempDir(siteName);
    }
    
    /**
     * Returns the temp data directory of the remote site passed. This would be, for
     * example, /devareas/user1/TestBase/DIM.TEMP/DATA
     * 
     * @return temp data directory.
     */
    public String getDataTempDir(String siteName) {
        RemoteOperationsManager rom = RemoteOperationsManager.getInstance();
        RemoteSite remoteSite = REMOTE_SITES_MANAGER.getRemoteSiteFromName(siteName);
        String homeDir = rom.getHomeDirectory(remoteSite);
        String dataDir = rom.getParentDirectory(remoteSite, homeDir);
        dataDir = dataDir + "/" + PluginConstants.T24DATA_TEMP_DIR;
        return dataDir;
    }
    

    /**
     * Throws up a dialog informing the user with the error, if any.
     * 
     * @param message to be thrown.
     */
    public void informFailure(String message) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (window == null) {
            RemoteOperationsLog.error(message);
            return;
        }
        T24MessageDialog dialog = new T24MessageDialog(window.getShell(), "Error", message, MessageDialog.ERROR);
        dialog.open();
    }

    private static JSubroutineParameters getParameters(String[] args) {
        JSubroutineParameters params = new JSubroutineParameters();
        for (String arg : args) {
            JDynArray jdynArray = new JDynArray();
            jdynArray.insert(arg, 1);
            params.add(jdynArray);
        }
        return params;
    }

    private static String[] getStringParams(JSubroutineParameters params) {
        List<String> paramList = new ArrayList<String>();
        for (JDynArray jdynArray : params) {
            paramList.add(jdynArray.get(1));
        }
        String[] returnParam = new String[paramList.size()];
        paramList.toArray(returnParam);
        return returnParam;
    }

    private static boolean checkRemote(IJremoteClient jremoteClient) {
        if (!jremoteClient.isConnected()) {
            if (jremoteClient.connect()) {
                return true;
            }
            return false;
        }
        return true;
    }

    private JSubroutineParameters callNewThread(final IJremoteClient remoteClient, final String subroutineName,
            final JSubroutineParameters parameters) {
        final SubroutineExecutor executor = new SubroutineExecutor(remoteClient, subroutineName, parameters);
        try {
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            window.run(true, true, new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    Thread thread = new Thread(executor);
                    thread.start();
                    int totalUnitsOfWork = IProgressMonitor.UNKNOWN;
                    String name = "Executing subroutine " + subroutineName + "...";
                    monitor.beginTask(name, totalUnitsOfWork);
                    while (executor.isFinished() != true) {
                        Thread.sleep(1000);
                        monitor.worked(1);
                        if (monitor.isCanceled()) {
                            break;
                        }
                    }
                    monitor.done();
                }
            });
        } catch (InvocationTargetException e) {
            throw new RemoteOperationsException(e);
        } catch (InterruptedException e) {
            throw new RemoteOperationsException(e);
        }
        return executor.getParameters();
    }

    private class SubroutineExecutor implements Runnable {

        private IJremoteClient remoteClient;
        private String subroutineName;
        private JSubroutineParameters parameters;
        private boolean finished;

        public SubroutineExecutor(IJremoteClient remoteClient, String subroutineName, JSubroutineParameters parameters) {
            this.remoteClient = remoteClient;
            this.subroutineName = subroutineName;
            this.parameters = parameters;
        }

        public void run() {
            try {
                JSubroutineParameters paramsIn = parameters;
                parameters = null;
                parameters = remoteClient.run(subroutineName, paramsIn);
                finished = true;
            } catch (JSubroutineNotFoundException e) {
                finished = true;
            } catch (JRemoteException e) {
                finished = true;
            }
        }

        public boolean isFinished() {
            return finished;
        }

        public JSubroutineParameters getParameters() {
            return parameters;
        }
    }
}

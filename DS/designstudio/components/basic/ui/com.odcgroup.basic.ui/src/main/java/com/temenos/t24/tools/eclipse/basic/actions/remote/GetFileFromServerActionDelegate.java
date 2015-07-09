package com.temenos.t24.tools.eclipse.basic.actions.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.actions.ActionsUtils;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteFileOpenDialog;
import com.temenos.t24.tools.eclipse.basic.dialogs.remote.RemoteOperationDialog;
import com.temenos.t24.tools.eclipse.basic.editors.T24BasicMultiPageEditor;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;
import com.temenos.t24.tools.eclipse.basic.editors.remote.T24RemoteFileEditor;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.actions.ActionCommon;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteFileTransferUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsDialogHelper;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsLog;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteOperationsManager;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredRetrieveFileFromServer;
import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;
import com.temenos.t24.tools.eclipse.basic.utils.EditorDocumentUtil;
import com.temenos.t24.tools.eclipse.basic.utils.FileUtil;
import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;
import com.temenos.t24.tools.eclipse.basic.utils.StringUtil;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

public class GetFileFromServerActionDelegate implements IWorkbenchWindowActionDelegate {

    private IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
    // The currently selected object in the active view
    private ISelection selection = null;
    private LogConsole log = LogConsole.getT24BasicConsole();
    private String serverDirectory = "";

    public void dispose() {
    }

    public void init(IWorkbenchWindow window) {
    }

    /**
     * Method invoked by the framework. The main logic of the action goes here.
     */
    public void run(IAction action) {
        String basicFilenameNoPrefix = "";
        T24RemoteFileEditor editor = RemoteFileEditorUtil.getCurrentEditor();
        IFile file = null;
        if (selection != null && (selection instanceof IStructuredSelection)) {
            Object obj = (Object) ((IStructuredSelection) selection).getFirstElement();
            if ((obj instanceof IFile)) {
                file = (IFile) obj;
                basicFilenameNoPrefix = StringUtil.removeBasicExtension(file.getName());
            }
        } else {
            basicFilenameNoPrefix = StringUtil.removeBasicExtension(EditorDocumentUtil.getActiveEditorFilename());
        }
        retrieveFileFromServer(basicFilenameNoPrefix, editor);
    }

    public void retrieveFileFromServer(String basicFilenameNoPrefix, T24RemoteFileEditor editor) {
        RemoteSite remoteSite = null;
        if (editor != null && editor.getRemoteSite() != null) {
            remoteSite = editor.getRemoteSite();
            serverDirectory = remoteSite.getProtocol().name().equals("LOCAL")?  remoteSite.getHomeDirectory() : editor.getServerDirectory() ;
        }
        RemoteOperationDialog dialog = new RemoteFileOpenDialog(EclipseUtil.getActiveWindow().getShell(), basicFilenameNoPrefix,
                remoteSite, serverDirectory);
        /*
         * dialog.open() opens this window, creating it first if it has not yet
         * been created. This method waits (blocks) until the window is closed
         * by the end user, and then it returns the window's return code. A
         * window's return codes are window-specific, although two standard
         * return codes are predefined: OK and CANCEL.
         */
        if (dialog.open() != InputDialog.OK) {
            // The user clicked something else other than OK. The execution
            // is finished.
            return;
        } else {
            // This line is reached if user clicked OK and input
            // validation passed.
            String fileName = dialog.getFileName();
            String serverDir = dialog.getServerDir();
            String siteName = dialog.getSiteName();
            RemoteSite site = RemoteSitesManager.getInstance().getRemoteSiteFromName(siteName);
            RemoteOperationsDialogHelper.updateDefaultServerDirectory(serverDir);
            String remotePath = "";
            if (serverDir.length() > 0) {
                remotePath = RemoteFileEditorUtil.calculateRemotePath(serverDir, site);
            } else {
                remotePath = dialog.getCurrentLocation();
            }
            IT24FTPClient ftpClient = null;
            ftpClient = RemoteSitesManager.getInstance().getFTPClient(siteName);
            if (!RemoteOperationsManager.getInstance().loadFile(ftpClient, site, remotePath, fileName, RemoteFileTransferUtil.getDefaultLocalPath(fileName))) {
                RemoteOperationsLog.error("Unable to open file " + fileName);
            }
        }
    }

    /**
     * With the filename and remote directory, invokes the appropriate method of
     * SessionManager to retrieve the remote file. Note: Remote filenames don't
     * end with the .b extension, as local files do.
     * 
     * @param basicFilenameNoPrefix - e.g. ACCOUNT.FILE (no prefix .b)
     * @param serverDirectory - e.g. GLOBUS.BP
     * @param readOnly - false => before the file is opened, check whether it is
     *            locked, if it is not, then open it and then set a lock. true =>
     *            no check or lock are done.
     */
    public void retrieveFileFromRemoteServer(final String basicFilenameNoPrefix, final String serverDirectory,
            final boolean readOnly) {
        String channelName = (new ProtocolUtil()).getCurrentChannel();
        /** Check that local dir and default project are set, if needed. */
        if (EclipseUtil.isSaveLocallyOn() && !EclipseUtil.isLocalDirAndProjectSet()) {
            /**
             * We want to save locally the files, but the local dir or the
             * project are not set correctly.
             */
            String header = "  Get File";
            ActionsUtils.launchMessageDialogPopup(header, PluginConstants.MESSAGE_NO_LOCALWORKSPACE, MessageDialog.ERROR);
            /** finish execution */
            return;
        }
        MonitoredRetrieveFileFromServer monitoredRetrieveFile = new MonitoredRetrieveFileFromServer();
        Response response = monitoredRetrieveFile.execute(basicFilenameNoPrefix, serverDirectory, readOnly);
        analyseResponse(response, basicFilenameNoPrefix, serverDirectory, channelName);
    }

    /**
     * Analyses the possible outcomes in the response, taking appropriate
     * action.
     * 
     * @param response
     * @param basicFilenameNoPrefix
     * @param serverDirectory
     * @param channelName
     */
    private void analyseResponse(Response response, String basicFilenameNoPrefix, String serverDirectory, String channelName) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (response.getPassed()) {
            /** Retrieval success */
            saveAndOpenFile(response, basicFilenameNoPrefix, serverDirectory, channelName);
        } else if ((new ActionCommon()).isLocked(response)) {
            /**
             * The file is locked by another User in the server. Offer to open
             * in read-only mode.
             */
            String headerTitle = "  Get File Message";
            String message = response.getRespMessage()
                    + "\n\nOpen it anyway? \n(Note: Note you won't be able to save to the server or compile it)";
            T24MessageDialog warningDialog = new T24MessageDialog(window.getShell(), headerTitle, message, MessageDialog.ERROR);
            if (warningDialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK. The execution
                // is finished.
                return;
            }
            boolean readOnly = true;
            retrieveFileFromRemoteServer(basicFilenameNoPrefix, serverDirectory, readOnly);
        } else {
            /** Retrieval failed */
            log.logMessage("File " + basicFilenameNoPrefix + " failed to be retrieved from dir: " + serverDirectory
                    + " - channel: " + channelName + ".\n" + response.getRespMessage());
        }
    }

    /**
     * Saves the file locally, if needed, and opens it with an editor.
     * 
     * @param response
     * @param basicFilenameNoPrefix - e.g. ACCOUNT.MODULE
     * @param serverDirectory - e.g. GLOBUS.BP
     * @param channelName - BROWSER channel name e.g. browser.1, or DEFAULT
     */
    private void saveAndOpenFile(Response response, String basicFilenameNoPrefix, String serverDirectory, String channelName) {
        /** At this point server file retrieval was successful, get contents. */
        String responseXmlContents = (String) response.getObject();
        String basicModuleContents = XmlUtil.getText(responseXmlContents, "//contents");
        if (basicModuleContents != null) {
            // Before the contents are displayed (or saved), it is necessary to
            // sort out the CR (\r) and LF (\n)
            // since the dom4j xml library might have not dealt with them
            // properly.
            basicModuleContents = (new ProtocolUtil()).transformInsolated_CR_LF_IntoWinNewLines(basicModuleContents);
            /** Open file with editor and save locally if needed */
            boolean isSaveLocallyOn = store.getBoolean(PluginConstants.T24_SAVE_LOCALLY_BY_DEFAULT_ON);
            String message = "";
            IFile iFile = null;
            iFile = saveServerFileLocally(basicFilenameNoPrefix, basicModuleContents, isSaveLocallyOn);
            if (iFile != null) {
                // As it is a server file, set false to isLocalFile
                message = EditorDocumentUtil.openFileWithEditor(EclipseUtil.getLocalCurDirectory(isSaveLocallyOn),
                        basicFilenameNoPrefix, serverDirectory, false);
            } else {
                return;
            }
            if ("".equals(message)) {
                // Get the file's relative path to the workspace (e.g.
                // \project_name\folder1\folder2)
                T24BasicMultiPageEditor currentEditor = EditorDocumentUtil.getActiveMultiPageEditor();
                currentEditor.updateTitleImage();
                String localInfo = "";
                if (isSaveLocallyOn) {
                    localInfo = "into: " + EditorDocumentUtil.getLocalRelativeLocation(iFile);
                }
                log.logMessage("(" + response.getActionTimeMillis() + "ms) - File " + basicFilenameNoPrefix + " retrieved OK "
                        + localInfo + " from Server dir: " + serverDirectory + " - channel: " + channelName + ".");
            } else {
                log.logMessage("(" + response.getActionTimeMillis() + "ms) - " + message);
            }
        } else {
            /** File contents were null */
            log.logMessage("(" + response.getActionTimeMillis() + "ms) - File " + basicFilenameNoPrefix
                    + " was retrieved with null contents from dir: " + serverDirectory + " - channel: " + channelName + ".");
        }
    }

    /**
     * Saves the server file in the local workspace.
     * 
     * @param basicFilenameNoPrefix
     * @param basicModuleContents
     * @param isSaveLocallyOn
     * @return null if there was a failure, else the iFile associated.
     */
    private IFile saveServerFileLocally(String basicFilenameNoPrefix, String basicModuleContents, boolean isSaveLocallyOn) {
        IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        FileUtil fu = new FileUtil();
        /** reference to the iFile where the basic was saved. */
        IFile iFile = null;
        // Note: files sent to or coming from the remote server do not have a
        // prefix (typically the .b extension)
        // However when handled by eclipse's editor locally they need to have a
        // local extension,
        // (which in our case is ".b")
        String localCurDir = EclipseUtil.getLocalCurDirectory(isSaveLocallyOn);
        boolean localCurDirValid;
        if (isSaveLocallyOn) {
            /**
             * Check whether the local directory selected by user is within the
             * pre-selectd project also choosed by the user. This is done to
             * prevent server files being saved in unwanted directories, which
             * can happen if a user has many projects opened and browse through
             * them frequently.
             */
            localCurDirValid = isLocalCurDirValid(localCurDir);
        } else {
            /**
             * with this option, files are always saved in the same directory,
             * which is ok.
             */
            localCurDirValid = true;
        }
        if (!localCurDirValid) {
            String message = "Attempting to save file in an invalid local Project. \nYour current default Project is "
                    + store.getString(PluginConstants.T24_LOCAL_DEFAULT_PROJECT_FULLPATH);
            String dialogTitle = "  Get File Message";
            T24MessageDialog warningDialog = new T24MessageDialog(window.getShell(), dialogTitle, message, MessageDialog.ERROR);
            // Opens dialog
            warningDialog.open();
            // terminate execution whatever button the user clicked.
            return null;
        }
        String fullBasicFilenameWithPrefix = localCurDir + System.getProperty("file.separator") + basicFilenameNoPrefix
                + PluginConstants.LOCAL_BASIC_DOT_PREFIX;
        // CHECK whether the file retrieved from the server, is already opened
        // locally and with changes,
        // and whether it already exists in current location.
        iFile = EditorDocumentUtil.getIFile(localCurDir, basicFilenameNoPrefix);
        String relativeFilePath = EditorDocumentUtil.getLocalRelativeLocation(iFile);
        if (iFile != null) {
            // Check whether file is already opened and has been modified
            T24BasicMultiPageEditor multiPageEditor = EditorDocumentUtil.findOpenMultipageEditor(iFile);
            if (multiPageEditor != null && multiPageEditor.isDirty()) {
                // The file is already opened and it has been modified. Alert
                // the User.
                String message = "This program is currently open and changes have been made. \nPlease, save the changes first, then try again.";
                String dialogTitle = "  Get File Message";
                T24MessageDialog warningDialog = new T24MessageDialog(window.getShell(), dialogTitle, message, MessageDialog.ERROR);
                // Opens dialog
                warningDialog.open();
                // terminate execution whatever buttom the user clicked.
                return null;
            }
        }
        /**
         * If saveLocally is true, then check whether a file with same name
         * already exits in the directory.
         */
        if (iFile.exists() && isSaveLocallyOn && ActionsUtils.isPromptOverwriting()) {
            // File exists locally
            String message = "A file with the same name already exists locally in " + relativeFilePath
                    + ". \nDo you want to overwrite it?";
            String dialogTitle = "  Get File Message";
            T24MessageDialog warningDialog = new T24MessageDialog(window.getShell(), dialogTitle, message,
                    MessageDialog.INFORMATION);
            if (warningDialog.open() != InputDialog.OK) {
                // The user clicked something else other than OK. The execution
                // is finished.
                return null;
            }
        }
        // If we reached this point, the user does want to save the file
        // locally, even it
        // that means overwriting a file with same name.
        boolean overwrite = true;
        int result = fu.saveToFile(basicModuleContents, fullBasicFilenameWithPrefix, overwrite);
        if (result >= 0) {
            return iFile;
        } else {
            return null;
        }
    }

    /**
     * Returns true if localDir is within the default local project, false
     * otherwise. This check is done to prevent server files being saved in
     * unwanted directories, which can happen if a user has many projects opened
     * and browse through them frequently.
     * 
     * @param localDir - local dir where a file is meant to be saved.
     * @return boolean
     */
    private boolean isLocalCurDirValid(String localDir) {
        if (localDir == null) {
            return false;
        } else {
            String localDefaultProjectFullPath = store.getString(PluginConstants.T24_LOCAL_DEFAULT_PROJECT_FULLPATH);
            return (localDir.indexOf(localDefaultProjectFullPath) >= 0);
        }
    }

    /**
     * Notifies this action delegate that the selection in the workbench has
     * changed.
     * 
     * @param action the action proxy that handles presentation portion of the
     *            action
     * @param selection the current selection, or <code>null</code> if there
     *            is no selection.
     * @see IActionDelegate#selectionChanged(IAction, ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = selection;
        action.setEnabled(true);
    }

    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
    }
}

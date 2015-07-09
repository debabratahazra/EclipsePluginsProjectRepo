package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.dialogs.T24MessageDialog;
import com.temenos.t24.tools.eclipse.basic.editors.remote.RemoteFileEditorUtil;

public class RemoteOperationsManager {

	private static RemoteSitesManager remoteSitesManager = null;
	private static RemoteOperationsManager remoteOperationsManager = new RemoteOperationsManager();
	private boolean showDialog = false;

	private RemoteOperationsManager() {
		remoteSitesManager = RemoteSitesManager.getInstance();
	}

	public static RemoteOperationsManager getInstance() {
		return remoteOperationsManager;
	}

	
    /**
     * @return the showDialog
     */
    public boolean isShowDialog() {
        return showDialog;
    }

    public boolean connect(IT24FTPClient ftpClient) {
		if (ftpClient == null) {
		    showDialog = false;
			return false;
		}
		if (ftpClient.isConnected() && ftpClient.sendNoOp()) {
		    showDialog = false;
            return true;
        }
		if(!ftpClient.isLoginBefore()){
		    showDialog = false;
            return false;
        }
		try {
		    boolean success = ftpClient.connect();
		    String message = "connection to site " + ftpClient.getRemoteSite().getSiteName() + " successful.";
		    RemoteOperationsLog.info(message);
		   return success;
		} catch (RemoteConnectionException e) {
			errorDialog(e.getMessage());
			RemoteOperationsLog.error(e.getMessage());
			showDialog = true;
			return false;
		}
	}

	public void disconnect(IT24FTPClient ftpClient) {
		if (ftpClient == null) {
			return;
		}
		if (ftpClient.isConnected()) {
			try {
				ftpClient.disconnect();
			} catch (RemoteConnectionException e) {
				// TODO - dialog?
			}
		}
	}

	// success: return home directory
	// failure: return null
	public String getHomeDirectory(RemoteSite remoteSite) {
		if (checkRemote(remoteSite)) {
			return remoteSitesManager.getFTPClient(remoteSite.getSiteName())
					.getHomeDir();
		}
		return null;
	}

	// success: true
	// failure: false
	public String getParentDirectory(RemoteSite remoteSite, String currDir) {
		IT24FTPClient ftpclient = remoteSitesManager.getFTPClient(remoteSite
				.getSiteName());
		if (checkRemote(ftpclient)) {
			try {
				String oldDir = ftpclient.printWorkingDir();
				ftpclient.changeWorkingDir(currDir);
				ftpclient.changeToParentDirectory();
				String parentDir = ftpclient.printWorkingDir();
				ftpclient.changeWorkingDir(oldDir);
				return parentDir;
			} catch (RemoteOperationsException e) {
				String message = FTPClientImplConstants.ERROR_CHANGE_DIR;
				RemoteOperationsLog.error(message + " " + e.getMessage());
				// TODO - dialog?
			}
		}
		return "";
	}

	// success: list of directory names
	// failure : empty array
	public String[] getDirectoryNames(RemoteSite remoteSite, String remotePath) {
		IT24FTPClient ftpclient = remoteSitesManager.getFTPClient(remoteSite
				.getSiteName());
		String[] directoryNames = new String[] { "" };
		if (checkRemote(ftpclient)) {
			try {
				List<IRemoteFile> allFiles = ftpclient.getFiles(remotePath);
				List<String> fileNamesList = new ArrayList<String>();
				fileNamesList.add(remotePath);
				for (IRemoteFile remoteFile : allFiles) {
					if (remoteFile.isDirectory()) {
						fileNamesList.add(remoteFile.getFullPath());
					}
				}
				directoryNames = fileNamesList.toArray(new String[fileNamesList
						.size()]);
			} catch (RemoteOperationsException e) {
				String message = FTPClientImplConstants.ERROR_GET_LIST
						+ remotePath;
				RemoteOperationsLog.error(message + " " + e.getMessage());
				// TODO - dialog?
			}
		}
		return directoryNames;
	}

	// success: true
	// failure false
	public boolean createNewDirectory(String newDirectoryPath,
			RemoteSite remoteSite) {
		if (checkRemote(remoteSite)) {
			try {
				return remoteSitesManager
						.getFTPClient(remoteSite.getSiteName())
						.createDirectory(newDirectoryPath);
			} catch (RemoteOperationsException e) {
				String message = FTPClientImplConstants.ERROR_NEW_DIR
						+ newDirectoryPath;
				RemoteOperationsLog.error(message + " " + e.getMessage());
				// TODO - dialog?
			}
		}
		return false;
	}

	/**
     * Loads the source item from the remote site for the hyper link action.<br>
     * The remote directories specified in the preferences would be searched to
     * load it from.
     * 
     * @param fileName source item name
     * @return true if loaded, false otherwise.
     */
    public boolean loadHyperlink(String fileName) {
        RemoteSite remoteSite = remoteSitesManager.getDefaultSite();
        IT24FTPClient remoteClient = remoteSitesManager.getDefaultFtpClient();
        boolean connected = checkRemote(remoteClient);
        if (!connected) {
            return false;
        }
        String remotePath = null;
        String[] remoteDirectoryNames = RemoteFileTransferUtil.getHyperLinkDirectories();
        String localPath = RemoteFileTransferUtil.getDefaultLocalPath(fileName);
        boolean loaded = false;
        for (String dirName : remoteDirectoryNames) {
            dirName = dirName.trim(); 
            remotePath = RemoteFileEditorUtil.calculateRemotePath(dirName, remoteSite);
            loaded = loadFile(remoteClient, remoteSite, remotePath, fileName, localPath);
            if (loaded) {
                break;
            }
        }
        if (!loaded) {
            RemoteOperationsLog.error("Unable to load file " + fileName);
            return false;
        }
        return true;
    }
	public boolean loadFile(IT24FTPClient ftpClient, RemoteSite site,
			String remotePath, String fileName, String localPath) {
		String serDir = "";
		if (isValid(remotePath)) {
			serDir = remotePath;
			remotePath = remotePath + "/" + fileName;
			try {
				localPath = retrieveFile(ftpClient, remotePath, localPath);
				if (isValid(localPath)) {
					RemoteOperationsLog.info("File " + fileName
							+ " opened successfully");
					RemoteFileEditorUtil.openFileWithEditor(site, serDir,
							localPath);
					return true;
				}
			} catch (RemoteOperationsException e) {
				return false;
			}
		}
		return false;
	}

	public boolean saveFile(RemoteSite remoteSite, String remoteDir,
			String fileName, IFile file) {
		if (remoteSite == null) {
			return false;
		}
		IT24FTPClient ftpClient = RemoteSitesManager.getInstance()
				.getFTPClient(remoteSite.getSiteName());
		if (checkRemote(ftpClient)) {
			try {
				return ftpClient.saveFile(remoteDir, fileName, file);
			} catch (RemoteOperationsException e) {
				String message = FTPClientImplConstants.ERROR_SAVE_FILE
						+ fileName;
				RemoteOperationsLog.error(message + ":" + e.getMessage());
				errorDialog(message);
			}
		}
		return false;
	}

	public String retrieveFile(RemoteSite remoteSite, String remotePath,
			String localPath) throws RemoteOperationsException {
		if (remoteSite == null) {
			return null;
		}
		IT24FTPClient ftpClient = remoteSitesManager.getFTPClient(remoteSite
				.getSiteName());
		try {
			return retrieveFile(ftpClient, remotePath, localPath);
		} catch (RemoteOperationsException e) {
			throw e;
		}
	}

	public String retrieveFile(IT24FTPClient ftpClient, String remotePath,
			String localPath) throws RemoteOperationsException {
		if (checkRemote(ftpClient)) {
			try {
				return ftpClient.retriveFile(remotePath, localPath);
			} catch (RemoteOperationsException e) {
				throw e;
			}
		}
		return null;
	}

	private boolean checkRemote(RemoteSite remoteSite) {
		if (remoteSite == null) {
			String message = FTPClientImplConstants.UNABLE_TO_CONNECT;
			errorDialog(message);
			RemoteOperationsLog.error(message);
			return false;
		}
		return checkRemote(remoteSitesManager.getFTPClient(remoteSite
				.getSiteName()));
	}

	private boolean checkRemote(IT24FTPClient ftpClient) {
		if (ftpClient == null) {
			String message = FTPClientImplConstants.UNABLE_TO_CONNECT ;
			errorDialog(message);
			RemoteOperationsLog.error(message);
			return false;
		}
		if (ftpClient.isConnected() && ftpClient.sendNoOp()) {
			return true;
		}
		if(!ftpClient.isLoginBefore()){
		    return false;
		}
		try {
			ftpClient.disconnect();
			boolean connected = ftpClient.connect();
			return connected;
		} catch (RemoteConnectionException e) {
			String message = FTPClientImplConstants.UNABLE_TO_CONNECT
					+ ftpClient.getRemoteSite().getSiteName();
			errorDialog(message);
			RemoteOperationsLog.error(message + " " + e.getMessage());
			return false;
		}
	}

	private void errorDialog(String message) {
		Shell shell = getShell();
		if (shell == null) {
			return;
		}
		T24MessageDialog dialog = new T24MessageDialog(shell,
				FTPClientImplConstants.ERROR_DIALOG_CAPTION, message,
				MessageDialog.ERROR);
		dialog.open();
	}

	private Shell getShell() {
		IWorkbench wb = PlatformUI.getWorkbench();
		if (wb == null) {
			return null;
		}
		IWorkbenchWindow window = wb.getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		return window.getShell();
	}
	/**
	 * 
	 * @param ftpClient
	 * @return default remote directory for the ftpclient passed . e.g; /glodev/Pareas/user/TestBase/GLOBUS.BP 
	 */
	public String getDefaultRemoteDirectory(IT24FTPClient ftpClient) {
		if (checkRemote(ftpClient)) {
			String remoteDir = "";
			String parentDir = getParentDirectory(ftpClient.getRemoteSite(),
					ftpClient.getHomeDir());
			remoteDir = parentDir + "/"+PluginConstants.SERVER_PRIMARY_SOURCE;
			return remoteDir;
		} else {
			return "";
		}
	}
	
	/**
     * 
     * @param ftpClient
     * @return default temp directory for the ftpclient passed . e.g; /glodev/Pareas/user/TestBase/TestBase.run/RTC.BP 
     */
    public String getDefaultTempDirectory(IT24FTPClient ftpClient) {
        if (checkRemote(ftpClient)) {
            String remoteDir = "";
            String homeDir = getHomeDirectory(ftpClient.getRemoteSite());
            if (homeDir != null) {
                if (ftpClient instanceof T24LocalhostClient) {
                    remoteDir = homeDir + "\\" + PluginConstants.SERVER_TEMP_DIR;
                } else {
                    remoteDir = homeDir + "/" + PluginConstants.SERVER_TEMP_DIR;
                }
            }
            return remoteDir;
        } else {
            return "";
        }
    }
	

	private boolean isValid(String name) {
		if ((name == null) || (name.equals(""))) {
			return false;
		}
		return true;
	}
}

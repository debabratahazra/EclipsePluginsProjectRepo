package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.common.io.Closeables;

/**
 * Class represents an FTP client used by T24Basic plug-in. It inherits the
 * {@link IT24FTPClient} to decouple the FTP protocol implementations with the
 * {@link RemoteOperationsManager}.
 * 
 * @author ssethupathi
 * 
 */
public class T24FTPClient implements IT24FTPClient {

    private FTPClient ftpClient;
    private RemoteSite remoteSite;
    private String homeDir;
    private Boolean isRetrieved;
    private boolean connectedSuccessfully = true;

    /**
     * Creates an T24 FTP Client
     * 
     * @param ftpClient
     * @param remoteSite
     */
    public T24FTPClient(FTPClient ftpClient, RemoteSite remoteSite) {
        this.ftpClient = ftpClient;
        this.remoteSite = remoteSite;
    }

    /**
     * {@inheritDoc}
     */
    public boolean connect() throws RemoteConnectionException {
        try {
            ftpClient.connect(remoteSite.getHostIP(), FTPClientImplConstants.DEFAULT_FTP_PORT);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.disconnect();
                throw new RemoteConnectionException("connect");
            }
            boolean loginOk = ftpClient.login(remoteSite.getUserName(), remoteSite.getPassword());
            if (loginOk) {
                homeDir = printWorkingDir();
                connectedSuccessfully = true;
                return true;
            } else {
                connectedSuccessfully =false ;
                throw new RemoteConnectionException(FTPClientImplConstants.ERROR_LOGIN_FIALURE);
            }
           
        } catch (SocketException e) {
            throw new RemoteConnectionException(FTPClientImplConstants.UNABLE_TO_CONNECT, e);
        }catch (IOException e) {
            throw new RemoteConnectionException(FTPClientImplConstants.UNABLE_TO_CONNECT, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean createDirectory(String remotePath) throws RemoteOperationsException {
        try {
            return ftpClient.makeDirectory(remotePath);
        } catch (IOException e) {
            throw new RemoteOperationsException("mkdir", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteFile(String remotePath) throws RemoteOperationsException {
        try {
            return ftpClient.deleteFile(remotePath);
        } catch (IOException e) {
            throw new RemoteOperationsException("rm", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteDirectory(String remotePath) throws RemoteOperationsException {
        try {
            return ftpClient.removeDirectory(remotePath);
        } catch (IOException e) {
            throw new RemoteOperationsException("rmdir", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void disconnect() {
        if (!ftpClient.isConnected()) {
            return;
        }
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            throw new RemoteOperationsException("disconnect", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String retriveFile(String remotePath, String localPath) throws RemoteOperationsException {
        try {
            boolean retrived = getFile(remotePath, localPath);
            if (retrived) {
                return localPath;
            }
            throw new RemoteOperationsException("File not found");
        } catch (RemoteOperationsException e) {
            throw e;
        } catch (FileNotFoundException e) {
            throw new RemoteOperationsException("Local file to retrieve into not found", e);
        } catch (IOException e) {
            throw new RemoteOperationsException("Unable to close file stream", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean saveFile(String remotePath, String fileName, IFile file) throws RemoteOperationsException {
        if (file == null) {
            return false;
        }
        InputStream is = null;
        try {
            is = file.getContents();
            String remoteFullPath = remotePath + "/" + fileName;
            ftpClient.setFileTransferMode(FTP.ASCII_FILE_TYPE);
            return ftpClient.storeFile(remoteFullPath, is);
        } catch (CoreException e) {
            throw new RemoteOperationsException("put", e);
        } catch (IOException e) {
            throw new RemoteOperationsException("put", e);
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean changeWorkingDir(String pathname) throws RemoteOperationsException {
        boolean changed = false;
        try {
            changed = ftpClient.changeWorkingDirectory(pathname);
        } catch (IOException e) {
            throw new RemoteOperationsException("cd", e);
        }
        return changed;
    }

    /**
     * {@inheritDoc}
     */
    public List<IRemoteFile> getFiles(String parentPath) throws RemoteOperationsException {
        FTPFile[] files = null;
        try {
            if (parentPath == null || parentPath.length() <= 0) {
                parentPath = printWorkingDir();
            }
            files = ftpClient.listFiles(parentPath);
        } catch (IOException e) {
            throw new RemoteOperationsException("ls", e);
        }
        return getRemoteFiles(files, parentPath);
    }

    /**
     * {@inheritDoc}
     */
    public boolean changeToParentDirectory() throws RemoteOperationsException {
        try {
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            throw new RemoteOperationsException("cd ..", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public IRemoteFile getParentFile(String remotePath) {
        int fileNamePos = remotePath.lastIndexOf('/');
        if (fileNamePos > 0) {
            String parentName = remotePath.substring(fileNamePos + 1);
            IRemoteFile parent = new FtpFile(parentName, remotePath + "/" + parentName, true);
            return parent;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String printWorkingDir() throws RemoteOperationsException {
        String pwd = null;
        try {
            pwd = ftpClient.printWorkingDirectory();
            return pwd;
        } catch (IOException e) {
            throw new RemoteOperationsException("pwd", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getHomeDir() {
        if (homeDir == null) {
            // TODO - get the home directory from remote site
        }
        return homeDir;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isConnected() {
        return ftpClient.isConnected();
    }

    /**
     * {@inheritDoc}
     */
    public RemoteSite getRemoteSite() {
        return remoteSite;
    }

    public boolean sendNoOp() {
        try {
            return ftpClient.sendNoOp();
        } catch (IOException e) {
            return false;
        }
    }

    private List<IRemoteFile> getRemoteFiles(FTPFile[] ftpFiles, String parentPath) {
        if (ftpFiles == null) {
            return null;
        }
        List<IRemoteFile> remoteFiles = new ArrayList<IRemoteFile>();
        String fileName = "";
        for (FTPFile file : ftpFiles) {
            fileName = file.getName();
            remoteFiles.add(new FtpFile(fileName, parentPath + "/" + fileName, file.isDirectory()));
        }
        return remoteFiles;
    }

    private boolean getFile(final String remotePath, final String localPath) throws RemoteOperationsException,
            FileNotFoundException, IOException {
        try {
            IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
            window.run(true, true, new IRunnableWithProgress() {

                public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
                    RetrieveThread retriever = new RetrieveThread(remotePath, localPath);
                    Thread thread = new Thread(retriever);
                    thread.start();
                    int totalUnitsOfWork = IProgressMonitor.UNKNOWN;
                    String name = "Retriving file from " + remotePath + "...";
                    monitor.beginTask(name, totalUnitsOfWork);
                    while (retriever.isRetrieved() == null) {
                        isRetrieved = retriever.isRetrieved();
                        if (isRetrieved != null && isRetrieved) {
                            break;
                        }
                        Thread.sleep(1000); // wait one second
                        monitor.worked(1); // add one slot to the progress
                        // monitor
                        if (monitor.isCanceled()) {
                            // This point is reached when the user has clicked
                            // to cancel the monitor.
                            isRetrieved = false;
                            break;
                        }
                    }
                    isRetrieved = retriever.isRetrieved();
                    monitor.done();
                }
            });
        } catch (InvocationTargetException e) {
            throw new RemoteOperationsException(e);
        } catch (InterruptedException e) {
            throw new RemoteOperationsException(e);
        }
        return isRetrieved;
    }

    private class RetrieveThread implements Runnable {

        private Boolean retrieved = null;
        private String remotePath;
        private String localPath;
        private FileOutputStream fos;

        public RetrieveThread(String remotePath, String localPath) {
            this.remotePath = remotePath;
            this.localPath = localPath;
        }

        public void run() {
            try {
                File file = new File(localPath);
                fos = new FileOutputStream(file);
                retrieved = ftpClient.retrieveFile(remotePath, fos);
            } catch (FileNotFoundException e) {
                retrieved = false;
                throw new RemoteOperationsException("File Not Found", e);
            } catch (IOException e) {
                retrieved = false;
                throw new RemoteOperationsException("get", e);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public Boolean isRetrieved() {
            return retrieved;
        }
    }
    
    public boolean isLoginBefore(){
        return connectedSuccessfully;
    }
     

    public boolean startAgent() {
       
        return true;
    }
}

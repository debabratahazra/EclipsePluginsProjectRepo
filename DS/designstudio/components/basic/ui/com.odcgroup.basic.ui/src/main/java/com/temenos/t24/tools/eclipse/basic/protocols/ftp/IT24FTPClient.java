package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.List;
import org.eclipse.core.resources.IFile;

/**
 * Interface which defines the remote operations which are available to T24Basic
 * perspective components. This makes it possible not to change the upper layers
 * when the underlying FTP protocol implementation takes changes.
 * 
 * @author ssethupathi
 * 
 */
public interface IT24FTPClient {

    /**
     * Connects and logs in to the remote site. Uses the associated
     * {@link RemoteSite} for user credentials.
     * 
     * @return true if log in successful. false otherwise
     * @throws RemoteConnectionException
     */
    public boolean connect() throws RemoteConnectionException;

    /**
     * Returns the path of the current working directory.
     * 
     * @return current working directory path
     * @throws RemoteOperationException
     */
    public String printWorkingDir() throws RemoteOperationsException;

    /**
     * Changes the working directory to the remote path passed.
     * 
     * @param remotePath remote path
     * @return true if successful. false otherwise.
     * @throws RemoteOperationException
     */
    public boolean changeWorkingDir(String remotePath) throws RemoteOperationsException;

    /**
     * Retrieves a remote file in the given path into local machine and returns
     * the local path.
     * 
     * @param remotePath remote path
     * @param local path
     * @return local file path
     * @throws RemoteOperationException
     */
    public String retriveFile(String remotePath, String localPath) throws RemoteOperationsException;

    /**
     * Returns the list of {@link IRemoteFile} objects from the given remote
     * path.
     * 
     * @param parentPath parent path
     * @return remote files
     * @throws RemoteOperationException
     */
    public List<IRemoteFile> getFiles(String parentPath) throws RemoteOperationsException;

    /**
     * Saves the given {@link IFile} to the remote site.
     * 
     * @param remotePath remote path
     * @param fileName file name
     * @param file IFile
     * @return true if saved. false otherwise
     * @throws RemoteOperationException
     */
    public boolean saveFile(String remotePath, String fileName, IFile file) throws RemoteOperationsException;

    /**
     * Creates a directory in the remote site.
     * 
     * @param remotePath path of the directory to be created
     * @return true if created successfully. false otherwise.
     * @throws RemoteOperationException
     */
    public boolean createDirectory(String remotePath) throws RemoteOperationsException;

    /**
     * Deletes a remote file.
     * 
     * @param remotePath path of the file.
     * @return true if deleted. false otherwise.
     * @throws RemoteOperationException
     */
    public boolean deleteFile(String remotePath) throws RemoteOperationsException;

    /**
     * Deletes a remote directory which is empty.
     * 
     * @param remotePath path of the directory
     * @return true if deleted. false otherwise.
     * @throws RemoteOperationException
     */
    public boolean deleteDirectory(String remotePath) throws RemoteOperationsException;

    /**
     * Changes the current working directory by moving one level up.
     * 
     * @return true/false
     * @throws RemoteOperationException
     */
    public boolean changeToParentDirectory() throws RemoteOperationsException;

    /**
     * Returns the parent {@link IRemoteFile} of the file in the passed in path.
     * 
     * @param remotePath path in the remote site
     * @return {@link IRemotePath} parent file
     */
    public IRemoteFile getParentFile(String remotePath);

    /**
     * Disconnects from the remote FTP server.
     * 
     * @throws RemoteConnectionException
     */
    public void disconnect() throws RemoteConnectionException;

    /**
     * Returns true if connected to remote server. false otherwise.
     * 
     * @return
     */
    public boolean isConnected();

    /**
     * Returns the associates {@link RemoteSite} object
     * 
     * @return remote site
     */
    public RemoteSite getRemoteSite();

    /**
     * Returns the home directory of the remote server.
     * 
     * @return home directory
     */
    public String getHomeDir();

    public boolean sendNoOp();
    
     /**
     * Returns true if agent gets started.false otherwise
     * 
     * @return home directory
     */
    
    public boolean startAgent();
    /**
     * 
     * @return
     */
    public boolean isLoginBefore();
}

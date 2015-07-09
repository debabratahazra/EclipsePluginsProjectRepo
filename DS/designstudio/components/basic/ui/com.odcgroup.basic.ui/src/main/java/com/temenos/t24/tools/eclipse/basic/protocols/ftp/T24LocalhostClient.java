package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Closeables;


/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class T24LocalhostClient implements IT24FTPClient {
    
    private LocalhostClient client = null; 
    private RemoteSite remoteSite = null;
    private static final Logger logger = LoggerFactory.getLogger(T24LocalhostClient.class);
    
    
    /**
     * @param client 
     * @param remoteSite 
     * 
     */
    public T24LocalhostClient(LocalhostClient client, RemoteSite remoteSite) {
        this.client = client;
        this.remoteSite = remoteSite;
    }

    public boolean connect() throws RemoteConnectionException {
        if(client == null){
            throw new RemoteConnectionException("Localhost client is null");
        }
        if(remoteSite == null){
            throw new RemoteConnectionException("Localhost Site is null");
        }
        try {
            client.connect(remoteSite.getHostIP(), 23);
        } catch (Exception e) {
            logger.error("Connect to localhost file failed: " + e.getMessage());
            return false;
        }
        return true;
    }

    public String printWorkingDir() throws RemoteOperationsException {
        String homeDir = this.remoteSite.getHomeDirectory();
        int index = homeDir.lastIndexOf("\\");
        return homeDir.substring(0, index);
    }

    public boolean changeWorkingDir(String remotePath) throws RemoteOperationsException {
        return true;
    }

    public String retriveFile(String remotePath, String localPath) throws RemoteOperationsException {
        try {
            localPath = localPath.replace("//", "\\");
            remotePath = remotePath.replace("/", "\\");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        File destFile = new File(localPath);
        File srcFile = new File(remotePath);
        FileChannel src = null;
        FileChannel dest = null;
        try {
            src = new FileInputStream(srcFile).getChannel();
            dest = new FileOutputStream(destFile).getChannel();
            dest.transferFrom(src, 0, src.size());
        }  catch (FileNotFoundException e) {
            throw new RemoteOperationsException("File not found. " + srcFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RemoteOperationsException("Error in File retrive. " + srcFile.getAbsolutePath());
        } finally {
            Closeables.closeQuietly(src);
            Closeables.closeQuietly(dest);
        }
        return localPath;
    }

    public List<IRemoteFile> getFiles(String parentPath) throws RemoteOperationsException {
        // Not listed sub folders in Combo box. Return blank List.
        return new ArrayList<IRemoteFile>();
    }

    public boolean saveFile(String remotePath, String fileName, IFile file) throws RemoteOperationsException {
        File srcFile = file.getLocation().toFile();
        File destFile = new File(remotePath + "\\" + fileName);
        FileChannel src = null;
        FileChannel dest = null;
        try {
            src = new FileInputStream(srcFile).getChannel();
            dest = new FileOutputStream(destFile).getChannel();
            dest.transferFrom(src, 0, src.size());
            src.close();
            dest.close();
        }  catch (FileNotFoundException e) {
            throw new RemoteOperationsException(e);
        } catch (IOException e) {
            throw new RemoteOperationsException(e);
        } finally {
            Closeables.closeQuietly(src);
            Closeables.closeQuietly(dest);
        }
        return true;
    }

    public boolean createDirectory(String remotePath) throws RemoteOperationsException {
        remotePath = remotePath.replace("/", "\\");
        boolean success = new File(remotePath).mkdir();
        return success;
    }

    public boolean deleteFile(String remotePath) throws RemoteOperationsException {
        throw new IllegalStateException("This isn't a valid command for Localhost");
    }

    public boolean deleteDirectory(String remotePath) throws RemoteOperationsException {
        throw new IllegalStateException("This isn't a valid command for Localhost");
    }

    public boolean changeToParentDirectory() throws RemoteOperationsException {
        return true;
    }

    public IRemoteFile getParentFile(String remotePath) {
        throw new IllegalStateException("This isn't a valid command for Localhost");
    }

    public void disconnect() throws RemoteConnectionException {
        if(client != null && client.isConnected()){
            try {
                client.disconnect();
            } catch (Exception e) {
                logger.error("Error : " + e.getMessage());
            }
        }
    }

    public boolean isConnected() {
        if(client == null){
            return false;
        }
        return client.isConnected();
    }

    public RemoteSite getRemoteSite() {
        return this.remoteSite;
    }

    public String getHomeDir() {
        if(this.remoteSite != null){
            String dir = this.remoteSite.getHomeDirectory();
            try {
                dir = dir.replace("/", "\\");
            } catch (Exception e) {
                logger.error("Error : " + e.getMessage());
            }
            return dir;
        }
        return "";
    }

    public boolean sendNoOp() {
        // Always True.
        return true;
    }

    public boolean startAgent() {
        return false;
    
    }

    public boolean isLoginBefore() {
        // Always True.
        return true;
    }
}

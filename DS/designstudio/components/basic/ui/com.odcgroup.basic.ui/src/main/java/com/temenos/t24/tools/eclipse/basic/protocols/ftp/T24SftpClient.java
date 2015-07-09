package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.google.common.io.Closeables;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

/**
 * Class represents an SFTP client used by T24Basic plug-in. It inherits the
 * {@link IT24FTPClient} to decouple the SFTP protocol implementations with the
 * {@link RemoteOperationsManager}.
 * 
 * @author ssethupathi
 */
public class T24SftpClient implements IT24FTPClient {

    static final int DEFAULT_BUFFER_SIZE = 16000; // 1024;
    static final long TIMEOUT = 20000L;
    static final long POLL_TIMEOUT = 1000L;
    static final String AGENT_STARTED = "NOTICE starting up jAgent, Process Per Connection mode, listening on port ";
    static final String ERROR_AGENT_STARTED = "ERROR Server is already running on port ";
    static final String ERROR_AGENT_NOT_STARTED = "ERROR Unable to open file /tmp/jagent.lock";
    byte[] buff = new byte[15100];
    private ChannelSftp channel;
    private RemoteSite remoteSite;
    private String rootDir;
    private Session session;
    private Channel channel1;
    private ChannelShell shell;

    /**
     * Creates an SFTP client for the given {@link RemoteSite}.
     * 
     * @param remoteSite
     */
    public T24SftpClient(RemoteSite remoteSite) {
        this.remoteSite = remoteSite;
    }

    /**
     * {@inheritDoc}
     */
    public boolean changeToParentDirectory() throws RemoteOperationsException {
        try {
            if (channel == null) {
                throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
            }
            channel.cd("..");
            return true;
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean changeWorkingDir(String remotePath) throws RemoteOperationsException {
        try {
            if (channel == null) {
                throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
            }
            channel.cd(remotePath);
            return true;
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean connect() throws RemoteConnectionException {
        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts(T24SftpClientHelper.getKnownHostsLocation());
            session = jsch.getSession(remoteSite.getUserName(), remoteSite.getHostIP(), 22);
            session.setUserInfo(new UserInfo() {

                public String getPassphrase() {
                    return null;
                }

                public String getPassword() {
                    return remoteSite.getPassword();
                }

                public boolean promptPassphrase(String message) {
                    return true;
                }

                public boolean promptPassword(String message) {
                    return true;
                }

                public boolean promptYesNo(String message) {
                    return T24SftpClientHelper.checkWithUser(message);
                }

                public void showMessage(String message) {
                }
            });
            session.setPassword(remoteSite.getPassword());
            session.connect();
            channel1 = session.openChannel("sftp");
            channel1.connect();
            channel = (ChannelSftp) channel1;
            rootDir = channel.getHome();
            connectedSuccessfully = true;
            return true;
        } catch (JSchException e) {
            if(e.getMessage() == "Auth fail"){
                connectedSuccessfully =false;
                throw new RemoteConnectionException(FTPClientImplConstants.ERROR_LOGIN_FIALURE,e);
            }
            throw new RemoteConnectionException(FTPClientImplConstants.UNABLE_TO_CONNECT,e);
        } catch (SftpException e) {
            throw new RemoteConnectionException(FTPClientImplConstants.UNABLE_TO_CONNECT,e);
        }
    }
    /** returns true if agent gets started. false otherwise */

    public boolean startAgent() {
        boolean isStarted = false;
        String outputMessage = null;
        InputStream is = null;
        try {
            // To create a channel and execute shell commands
            shell = (ChannelShell) session.openChannel("shell");
            OutputStreamWriter out = new OutputStreamWriter(shell.getOutputStream());
            String[] commands = new String[3];
            commands[0] = "N\r";
            commands[1] = "jbase_agent start -p" + remoteSite.getPortNumber() + "\r";
            shell.setAgentForwarding(true);
            shell.setPty(true);
            shell.connect();
            byte[] buffer = null;
            ByteArrayOutputStream bos = null;
             for (int i = 0; i < 3; i++) {
                buffer = new byte[DEFAULT_BUFFER_SIZE];
                bos = new ByteArrayOutputStream();
                is = shell.getInputStream();
                final long endTime = System.currentTimeMillis() + TIMEOUT;
                while (System.currentTimeMillis() < endTime) {
                    while (is.available() > 0) {
                        int count = is.read(buffer, 0, DEFAULT_BUFFER_SIZE);
                        if (count < buffer.length) {
                            bos.write(buffer, 0, count);
                        } else {
                           
                            break;
                        }
                    }
                    if (shell.isClosed()) {
                       
                        break;
                    }
                }
                if (i != 2) {
                    out.write(commands[i]);
                    out.flush();
                }
            }
            int startIndex = bos.toString().indexOf("NOTICE");
            int lastIndex = bos.toString().lastIndexOf("NOTICE");
            int startErrInd = bos.toString().indexOf("ERROR");
            int lastErrInd = bos.toString().indexOf("ERROR");
            if (startIndex != -1 && lastIndex != -1) {
                if (startIndex == lastIndex) {
                    outputMessage = bos.toString().substring(startIndex, bos.toString().indexOf("port") + 10);
                } else {
                    outputMessage = bos.toString().substring(lastIndex, (bos.toString().length()));
                }
                boolean isMatching = outputMessage.matches(".*" + AGENT_STARTED + remoteSite.getPortNumber());
                if (isMatching) {
                    isStarted = true;
                    return isStarted;
                }
            }
            if (startErrInd != -1 && lastErrInd != -1) {
                if (startErrInd == lastErrInd) {
                    outputMessage = bos.toString().substring(startErrInd, bos.toString().indexOf("port") + 10);
                } else {
                    outputMessage = bos.toString().substring(lastErrInd, (bos.toString().length()));
                }
                boolean isStrMatching = outputMessage.matches(".*" + ERROR_AGENT_STARTED + remoteSite.getPortNumber());
                if (isStrMatching) {
                    isStarted = true;
                } else {
                    isStarted = false;
                }
            }
            return isStarted;
        } catch (Exception e) {
            isStarted = false;
            return isStarted;
        } finally {
            if (shell != null) {
                shell.disconnect();
            }
            Closeables.closeQuietly(is);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean createDirectory(String remotePath) throws RemoteOperationsException {
        try {
            if (channel == null) {
                throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
            }
            channel.mkdir(remotePath);
            return true;
        } catch (SftpException e) {
            // TODO: Exception to be handled when thrown because of file already exists.
            if (e.toString().equals("4: Failure")) {
                return true;
            }
            throw new RemoteOperationsException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    public boolean deleteDirectory(String remotePath) throws RemoteOperationsException {
        try {
            if (channel == null) {
                throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
            }
            channel.rmdir(remotePath);
            return true;
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteFile(String remotePath) throws RemoteOperationsException {
        try {
            if (channel == null) {
                throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
            }
            channel.rm(remotePath);
            return true;
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void disconnect() throws RemoteConnectionException {
        if (channel != null) {
            channel.disconnect();
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<IRemoteFile> getFiles(String parentPath) throws RemoteOperationsException {
        if (channel == null) {
            throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
        }
        List<IRemoteFile> files = new ArrayList<IRemoteFile>();
        try {
            IRemoteFile file;
            Vector<Object> filesVector = channel.ls(parentPath);
            int listSize = filesVector.size();
            for (int ii = 2; ii < listSize; ii++) {
                Object obj = filesVector.elementAt(ii);
                if (obj instanceof LsEntry) {
                    file = createSftpFile(((LsEntry) obj), parentPath);
                    if (!(file.getName().equals(".") || file.getName().equals(".."))) {
                        files.add(file);
                    }
                }
            }
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        }
        return files;
    }

    /**
     * {@inheritDoc}
     */
    public String getHomeDir() {
        return rootDir;
    }

    /**
     * {@inheritDoc}
     */
    public RemoteSite getRemoteSite() {
        return remoteSite;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isConnected() {
        if (channel == null) {
            return false;
        }
        return channel.isConnected();
    }

    /**
     * {@inheritDoc}
     */
    public String printWorkingDir() throws RemoteOperationsException {
        try {
            if (channel == null) {
                throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
            }
            return channel.pwd();
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public IRemoteFile getParentFile(String remotePath) {
        int fileNamePos = remotePath.lastIndexOf('/');
        if (fileNamePos > 0) {
            String parentName = remotePath.substring(fileNamePos + 1);
            IRemoteFile parent = new SftpFile(parentName, remotePath + "/" + parentName, true);
            return parent;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String retriveFile(String remotePath, String localPath) throws RemoteOperationsException {
        if (channel == null) {
            throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
        }
        File file = new File(localPath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            channel.get(remotePath, fos);
        } catch (SftpException e) {
            throw new RemoteOperationsException("File not found.");
        } catch (FileNotFoundException e) {
            throw new RemoteOperationsException("File not found. " + file.getAbsolutePath());
        } finally {
        }
        return localPath;
    }

    /**
     * {@inheritDoc}
     */
    public boolean saveFile(String remotePath, String fileName, IFile file) throws RemoteOperationsException {
        if (channel == null) {
            throw new RemoteOperationsException(FTPClientImplConstants.ERROR_NOT_CONNECTED);
        }
        if (file == null) {
            return false;
        }
        InputStream is = null;
        try {
            is = file.getContents();
            CRFilterInputStream filterInputStream = new CRFilterInputStream(is);
            remotePath = remotePath + "/" + fileName;
            channel.put(filterInputStream, remotePath, 0);
            return true;
        } catch (CoreException e) {
            throw new RemoteOperationsException(e);
        } catch (SftpException e) {
            throw new RemoteOperationsException(e);
        } finally {
            Closeables.closeQuietly(is);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean sendNoOp() {
        if (channel == null) {
            return false;
        }
        return channel.isConnected();
    }

    private IRemoteFile createSftpFile(LsEntry entry, String parentPath) {
        String filename = entry.getFilename();
        boolean isDirectory = entry.getLongname().startsWith("d");
        SftpFile file = new SftpFile(filename, parentPath + "/" + filename, isDirectory);
        return file;
    }

    private class CRFilterInputStream extends InputStream {

        private InputStream is;

        public CRFilterInputStream(InputStream is) {
            this.is = is;
        }

        public int read() throws IOException {
            int read = is.read();
            if (read == 13) {
                read = is.read();
            }
            return read;
        }
    }
    private boolean connectedSuccessfully = true;
    public boolean isLoginBefore() {
        return connectedSuccessfully;
    }
}

package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.ftp.IT24FTPClient;

/**
 * This {@link RemoteConnection} object represent the collection of remote
 * client wrappers for FTP and JCA connectivity.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteConnection {

    private IT24FTPClient ftpClient;
    private IJremoteClient jremoteClient;

    public RemoteConnection(IT24FTPClient ftpClient, IJremoteClient remoteConnection) {
        this.ftpClient = ftpClient;
        this.jremoteClient = remoteConnection;
    }

    /**
     * Returns the instance of {@link IT24FTPClient} for a remote connection.
     * 
     * @return ftpclient
     */
    public IT24FTPClient getFtpClient() {
        return ftpClient;
    }

    /**
     * Returns the instance of {@link IJremoteClient} for a remote connection.
     * 
     * @return jremote client
     */
    public IJremoteClient getJremoteClient() {
        return jremoteClient;
    }
}

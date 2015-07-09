package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

import com.temenos.t24.tools.eclipse.basic.jremote.IJremoteClient;
import com.temenos.t24.tools.eclipse.basic.jremote.JremoteClient;
import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnection;

public class RemoteConnectionFactory {

    private static RemoteConnectionFactory REMOTE_CONNECTION_FACTORY = new RemoteConnectionFactory();

    private RemoteConnectionFactory() {
    }

    public static RemoteConnectionFactory getInstance() {
        return REMOTE_CONNECTION_FACTORY;
    }

    public RemoteConnection createRemoteConnection(RemoteSite remoteSite) {
        if (remoteSite == null) {
            return null;
        }
        IT24FTPClient client = null;
        if (remoteSite.getProtocol().equals(Protocol.FTP)) {
            client = createFtpClient(remoteSite);
        } else if (remoteSite.getProtocol().equals(Protocol.SFTP)){
            client = createSFtpClient(remoteSite);
        } else if(remoteSite.getProtocol().equals(Protocol.LOCAL)){
            client = createLocalClient(remoteSite);
        }
        IJremoteClient jremoteClient = createJremoteClient(remoteSite);
        RemoteConnection remoteConnection = new RemoteConnection(client, jremoteClient);
        return remoteConnection;
    }

    private IT24FTPClient createFtpClient(RemoteSite remoteSite) {
        FTPClient client = new FTPClient();
        RemoteSitePlatform platform = remoteSite.getPlatform();
        if (platform == RemoteSitePlatform.UNIX || platform == RemoteSitePlatform.LINUX) {
            client.configure(new FTPClientConfig(FTPClientConfig.SYST_UNIX));
        }
        if (platform == RemoteSitePlatform.NT) {
            client.configure(new FTPClientConfig(FTPClientConfig.SYST_NT));
        }
        IT24FTPClient ftpClient = new T24FTPClient(client, remoteSite);
        return ftpClient;
    }
    
    private IT24FTPClient createLocalClient(RemoteSite remoteSite) {
        LocalhostClient client = new LocalhostClient();

        RemoteSitePlatform platform = remoteSite.getPlatform();
        IT24FTPClient ftpClient = null;
        if (platform == RemoteSitePlatform.NT) {
            ftpClient = new T24LocalhostClient(client, remoteSite);
        }
        return ftpClient;
    }

    private IT24FTPClient createSFtpClient(RemoteSite remoteSite) {
        IT24FTPClient sftpClient = new T24SftpClient(remoteSite);
        return sftpClient;
    }

    public IJremoteClient createJremoteClient(RemoteSite remoteSite) {
        IJremoteClient jremoteClient = new JremoteClient(remoteSite);
        return jremoteClient;
    }
}

package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;

/**
 * This {@link RemoteSite} class represents a remote T24 site.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteSite {

    private String siteName;
    private String hostIP;
    private String userName;
    private String password;
    private boolean isDefault;
    private RemoteSitePlatform platform;
    private String portNumber;
    private RemoteConnectionType connectionType;
    private Protocol protocol;
    private String homeDirectory;

    public RemoteSite() {
    }

    public RemoteSite(String siteName, String hostIP, String userName, String password, RemoteSitePlatform platform,
            boolean isDefault, String portNo, RemoteConnectionType connectionType, Protocol protocol, String homeDirectory) {
        this.siteName = siteName;
        this.hostIP = hostIP;
        this.userName = userName;
        this.password = password;
        this.platform = platform;
        this.isDefault = isDefault;
        this.portNumber = portNo;
        this.connectionType = connectionType;
        this.protocol = protocol;
        this.homeDirectory = homeDirectory;
    }

    /**
     * Returns the name of the remote site
     * 
     * @return site name
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * Returns the IP address of the the remote site
     * 
     * @return IP address
     */
    public String getHostIP() {
        return hostIP;
    }

    /**
     * Returns the user name
     * 
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns true if this is the default remote site, false otherwise
     * 
     * @return true/false
     */
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * Sets/resets this as a default site
     * 
     * @param isDefault
     */
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the platform of the remote site
     * 
     * @return
     */
    public RemoteSitePlatform getPlatform() {
        return platform;
    }

    /**
     * Returns the port number of the JAgent
     * 
     * @return
     */
    public String getPortNumber() {
        return portNumber;
    }

    /**
     * Returns the connection type selected for the site
     * 
     * @return
     */
    public RemoteConnectionType getConnectionType() {
        return connectionType;
    }
    
    /**
     * Returns the protocol type selected for the site
     * @return
     */
     public Protocol getProtocol() {
     return protocol;
     }
     
     /**
      * Returns the home directory path for the site
      * @return
      */
      public String getHomeDirectory() {
      return homeDirectory;
      }


    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {
        if (obj instanceof RemoteSite) {
            if (((RemoteSite) obj).getSiteName().equals(this.siteName)) {
                return true;
            }
        }
        return false;
    }
}

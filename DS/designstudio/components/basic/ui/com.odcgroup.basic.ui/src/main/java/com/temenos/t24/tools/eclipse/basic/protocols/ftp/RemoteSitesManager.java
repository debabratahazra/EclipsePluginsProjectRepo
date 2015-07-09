package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.model.IDSServerStates;
import com.odcgroup.server.ui.ServerUICore;
import com.odcgroup.t24.server.external.model.internal.ExternalT24Server;
import com.odcgroup.t24.server.external.model.internal.T24ServerException;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.jremote.IJremoteClient;
import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnection;
import com.temenos.t24.tools.eclipse.basic.jremote.RemoteConnectionType;
import com.temenos.t24.tools.eclipse.basic.views.remote.RemoteSiteViewManager;

/**
 * This class is responsible for creation, modification, deletion and access of
 * {@link RemoteSite} objects for a particular workspace. {@link RemoteSite}s
 * are stored in Eclipse memento once created. This is the only place where any
 * {@link RemoteSite} object is created, modified or accessed through.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteSitesManager {

    private static final Logger logger = LoggerFactory.getLogger(RemoteSitesManager.class);
    private static RemoteSitesManager SITE_MANAGER = new RemoteSitesManager();
    private List<RemoteSite> remoteSites = new ArrayList<RemoteSite>();
    private Map<String, RemoteConnection> remoteConnections = new HashMap<String, RemoteConnection>();
    private RemoteSite defaultSite = null;
    private boolean sitesLoaded = false;

    private RemoteSitesManager() {
    }

    /**
     * Returns the singleton instance of this class.
     * 
     * @return
     */
    public static RemoteSitesManager getInstance() {
        return SITE_MANAGER;
    }

    /**
     * Creates a new {@link RemoteSite} and stores it in memento.
     * 
     * @param siteName user defined site name
     * @param hostName host IP address
     * @param userName user name
     * @param password password
     * @param platform remote platform
     * @param isDefault default site or not
     * @return {@link RemoteSite} remote site
     */
    public RemoteSite createSite(String siteName, String hostName, String userName, String password, RemoteSitePlatform platform,
            boolean isDefault, String portNo, RemoteConnectionType connectionType,Protocol protocolType, String homeDirectory ) throws RemoteConnectionException {
        RemoteSite newSite = new RemoteSite(siteName, hostName, userName, password, platform, isDefault, portNo, connectionType,protocolType, homeDirectory);
        
        RemoteConnection remoteConnection = RemoteConnectionFactory.getInstance().createRemoteConnection(newSite);
        remoteSites.add(newSite);
        remoteConnections.put(siteName, remoteConnection);
       
        RemoteSitesManagerHelper.updateRemoteSite(newSite);
        return newSite;
    }

      
    /**
     * Modifies an existing {@link RemoteSite} and updates the memento.
     * 
     * @param siteName
     * @param hostName
     * @param userName
     * @param password
     * @param platform
     * @param isDefault
     * @return
     */
    public RemoteSite editSite(String siteName, String hostName, String userName, String password, RemoteSitePlatform platform,
            boolean isDefault, String portNo, RemoteConnectionType connectionType, Protocol protocol, String homeDirectory) throws RemoteConnectionException {
        RemoteSite remoteSite = getRemoteSiteFromName(siteName);
        if(remoteSite != null) {
            deleteSite(remoteSite);
        }
        return createSite(siteName, hostName, userName, password, platform, isDefault, portNo, connectionType,protocol, homeDirectory);
       
    }

    /**
     * Returns a list of {@link RemoteSite} objects which are available in this
     * workspace.
     * 
     * @return remote sites
     */
    public List<RemoteSite> getRemoteSites() {
        List<IDSServer> servers = ServerUICore.getDefault().getContributions().getServers();
        try {
            for (IDSServer idsServer : servers) {
                if(!sitesLoaded) {
                    T24BasicPlugin.getDefault().finish((ExternalT24Server)idsServer);
                } else {
                    break;
                }
            }
            sitesLoaded = true;
            loadRemoteSites(servers);
        } catch (T24ServerException e) {
            logger.error("Loading Remote Sites failed: " + e.getMessage());
        }
        return remoteSites;
    }

    /**
     * Returns the names of remote sites.
     * 
     * @return remote sute names.
     */
    public String[] getRemoteSiteNames() {
        List<String> siteNames = new ArrayList<String>();
        List<RemoteSite> copyRemoteSite = new ArrayList<RemoteSite>(remoteSites);
        try {
            for (RemoteSite remoteSite : copyRemoteSite) {
                if (SITE_MANAGER.getDefaultSiteName() != null && SITE_MANAGER.getDefaultSiteName().equals(remoteSite.getSiteName())) {
                    siteNames.add(0, SITE_MANAGER.getDefaultSiteName());
                    continue;
                }
                siteNames.add(remoteSite.getSiteName());
            }
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        return siteNames.toArray(new String[0]);
    }
    
    /**
     * Returns the total size of remote sites.
     * 
     * @return total Server.
     */
    public int getRemoteSiteSize() {
        return remoteSites.size();
    }

    /**
     * Returns the {@link RemoteSite} for the passed-in remote site name.
     * 
     * @param siteName
     * @return remote site.
     */
    public RemoteSite getRemoteSiteFromName(String siteName) {
        for (RemoteSite remoteSite : remoteSites) {
            if (remoteSite.getSiteName().equals(siteName)) {
                return remoteSite;
            }
        }
        return null;
    }

    /**
     * Loads the {@link RemoteSite}s available in this workspace.
     * 
     * @throws T24ServerException 
     */
    public void loadRemoteSites(List<IDSServer> servers) throws T24ServerException {
        // Check to set default site same as active server, else error message is displayed to set default server
        defaultSite = null;
        for (IDSServer idsServer : servers) {
            if (idsServer.getState() == IDSServerStates.STATE_ACTIVE
                    || idsServer.getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG) {
                defaultSite = getRemoteSiteFromName(idsServer.getServerProject().getName());
                break;
            }
        }
    }
    
    public void loadAllRemoteSitesForce() throws T24ServerException {
        remoteSites.clear();
        List<IDSServer> servers = ServerUICore.getDefault().getContributions().getServers();
        defaultSite = null;
        for (IDSServer idsServer : servers) {
            T24BasicPlugin.getDefault().finish((ExternalT24Server) idsServer);
            if (idsServer.getState() == IDSServerStates.STATE_ACTIVE
                    || idsServer.getState() == IDSServerStates.STATE_ACTIVE_IN_DEBUG) {
                defaultSite = getRemoteSiteFromName(idsServer.getServerProject().getName());
            }
        }
    }

    /**
     * Sets the passed-in {@link RemoteSite} as the default site.
     * 
     * @param currentSite
     */
    public void setAsDefaultSite(RemoteSite currentSite) {
        RemoteSitesManagerHelper.changeDefaultSite(defaultSite, currentSite);
        defaultSite = currentSite;
    }

    /**
     * Removes a {@link RemoteSite} from the workspace.
     * 
     * @param site
     * @return true if removed, false otherwise.
     */
    public boolean deleteSite(RemoteSite site) {
        RemoteSiteViewManager.getInstance().closeView(site);
        String siteName = site.getSiteName();
        remoteSites.remove(site);
        IT24FTPClient ftpClient = remoteConnections.get(siteName).getFtpClient();
        if(ftpClient!=null){
        ftpClient.disconnect();
        }
        remoteConnections.remove(siteName);
        if (site.isDefault()) {
            defaultSite = null;
        }
        RemoteSitesManagerHelper.deleteRemoteSite(siteName);
        return true;
    }

    /**
     * Returns the associated {@link IT24FTPClient} for the passed-in site name.
     * 
     * @param siteName
     * @return
     */
    public IT24FTPClient getFTPClient(String siteName) {
        if (remoteConnections.containsKey(siteName)) {
            return remoteConnections.get(siteName).getFtpClient();
        }
        return null;
    }

    /**
     * Returns the default {@link RemoteSite} of this workspace.
     * 
     * @return
     */
    public RemoteSite getDefaultSite() {
        return defaultSite;
    }

    public String getDefaultSiteName() {
        getRemoteSites();
        if (defaultSite != null) {
            return defaultSite.getSiteName();
        }
        return null;
    }

    /**
     * Returns the default {@link IT24FTPClient} of this workspace.
     * 
     * @return
     */
    public IT24FTPClient getDefaultFtpClient() {
        if (defaultSite != null) {
            return remoteConnections.get(defaultSite.getSiteName()).getFtpClient();
        }
        return null;
    }

    /**
     * Checks if any site with the siteName exists already.
     * 
     * @param site name
     * @return true if exists, false otherwise.
     */
    public boolean isSiteExist(String siteName) {
        return remoteConnections.containsKey(siteName);
    }

    /**
     * Establishes a connection to the {@link RemoteSite}.
     * 
     * @param siteName
     * @return true if connected, false otherwise.
     */
    public boolean connect(String siteName) {
        IT24FTPClient client = remoteConnections.get(siteName).getFtpClient();
        return RemoteOperationsManager.getInstance().connect(client);
    }

    /**
     * Disconnects the {@link RemoteSite}.
     * 
     * @param siteName
     */
    public void disconnect(String siteName) {
        IT24FTPClient client = remoteConnections.get(siteName).getFtpClient();
        RemoteOperationsManager.getInstance().disconnect(client);
    }

    /**
     * Checks if whether the given site is connected or not.
     * 
     * @param siteName
     * @return true if connected, false otherwise.
     */
    public boolean isConnected(String siteName) {
        IT24FTPClient client = remoteConnections.get(siteName).getFtpClient();
        if (client != null && client.isConnected()) {
            return true;
        }
        return false;
    }

    public IJremoteClient getJremoteClient(String siteName) {
        if (remoteConnections.containsKey(siteName)) {
            return remoteConnections.get(siteName).getJremoteClient();
        }
        return null;
    }
}

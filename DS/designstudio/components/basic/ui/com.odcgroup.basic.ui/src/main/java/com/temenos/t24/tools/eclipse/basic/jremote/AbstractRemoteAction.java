package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSitesManager;

/**
 * Implementation of {@link IRemoteAction} to handle the {@link JRemoteClient}
 * creation.
 * 
 * @author ssethupathi
 * 
 */
public abstract class AbstractRemoteAction implements IRemoteAction {

    protected abstract String getSiteName();

    /**
     * Sets the response as failed if this fails to get a {@link IJremoteClient}.
     * 
     * @param response
     */
    protected abstract void setFailedResponse(Response response);

    /**
     * Returns the {@link IJremoteClient}
     * 
     * @return jremote client
     */
    protected IJremoteClient getJremoteClient() {
        String siteName = getSiteName();
        IJremoteClient jremoteClient = RemoteSitesManager.getInstance().getJremoteClient(siteName);
        if (jremoteClient.isConnected()) {
            return jremoteClient;
        }
        boolean connected = jremoteClient.connect();
        if (!connected) {
            Response response = new Response();
            response.setPassed(false);
            response.setMessage("Unable to connect to jagent");
            setFailedResponse(response);
        }
        return jremoteClient;
    }
}

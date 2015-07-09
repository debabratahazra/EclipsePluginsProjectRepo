package com.temenos.t24.tools.eclipse.basic.jremote;

import com.jbase.jremote.JRemoteException;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;

/**
 * An {@link IRemoteAction} implementation for the T24Basic code compile action.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteCompileAction extends AbstractRemoteAction {

    private RemoteSite remoteSite;
    private String remotePath;
    private String fileName;
    private Response response;

    public RemoteCompileAction(RemoteSite remoteSite, String remotePath, String fileName) {
        this.remoteSite = remoteSite;
        this.remotePath = remotePath;
        this.fileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    public Response execute() throws JRemoteException, NumberFormatException {
        IJremoteClient jremoteClient = getJremoteClient();
        if (jremoteClient == null || !jremoteClient.isConnected()) {
            return response;
        }
        String compileResult = "";
        try {
            compileResult = jremoteClient.compile(remotePath, fileName);
        } catch (JRemoteException e) {
            throw e;
        }
        return buildResponse(compileResult);
    }

    private Response buildResponse(String compileResult) {
        Response response = new Response();
        response.setPassed(true);
        response.setObject(compileResult);
        return response;
    }

    @Override
    protected String getSiteName() {
        return remoteSite.getSiteName();
    }

    @Override
    protected void setFailedResponse(Response response) {
        this.response = response;
    }
}

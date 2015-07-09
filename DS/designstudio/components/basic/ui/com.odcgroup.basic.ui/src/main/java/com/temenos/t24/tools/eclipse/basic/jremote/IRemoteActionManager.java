package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * Interface defines the operations required from a remote action manager.
 * 
 * @author ssethupathi
 * 
 */
public interface IRemoteActionManager {

    /**
     * Performs the passed-in {@link IRemoteAction} in the {@link RemoteSite}
     * 
     * @param action
     * @return Response response
     */
    public Response performAction(IRemoteAction action);

    /**
     * Informs the manager that the {@link IRemoteAction} is finished.
     * 
     * @param response response
     */
    public void actionFinished(Response response);

    /**
     * Informs the manager that due to some reasons the {@link IRemoteAction}
     * has to be stopped.
     * 
     * @param reason
     * @return Response response
     */
    public Response stopAction(String reason);
}

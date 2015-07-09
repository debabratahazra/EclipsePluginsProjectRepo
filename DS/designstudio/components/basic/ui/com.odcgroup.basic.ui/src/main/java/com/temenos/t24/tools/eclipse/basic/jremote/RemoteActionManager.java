package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * A {@link IRemoteActionManager} which controls the {@link IRemoteAction}
 * performed on a {@link RemoteSite}
 * 
 * @author ssethupathi
 * 
 */
public class RemoteActionManager implements IRemoteActionManager {

    private Response response;
    private boolean actionStopped = false;
    private boolean actionFinished = false;

    /**
     * {@inheritDoc}
     */
    public void actionFinished(Response response) {
        actionFinished = true;
        this.response = response;
    }

    /**
     * {@inheritDoc}
     */
    public Response performAction(IRemoteAction action) {
        response = null;
        RemoteActionThread actionThread = new RemoteActionThread(this, action);
        Thread newThread = new Thread(actionThread);
        newThread.start();
        long startTime = System.currentTimeMillis();
        while (!actionFinished && !actionStopped) {
            long sleepTime = 50;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
        if (actionFinished) {
            long duration = System.currentTimeMillis() - startTime;
            response.setActionTimeMillis(duration);
            return response;
        }
        response = new Response();
        response.setPassed(false);
        if (actionStopped) {
            return response;
        } else {
            response.setPassed(false);
            response.setRespMessage("Remote operation terminated abnormally");
            return response;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Response stopAction(String reason) {
        actionStopped = true;
        response = new Response();
        response.setPassed(false);
        response.setMessage(reason);
        return response;
    }
}

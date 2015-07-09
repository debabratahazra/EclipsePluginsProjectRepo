package com.temenos.t24.tools.eclipse.basic.jremote;

import com.jbase.jremote.JRemoteException;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * A {@link Runnable} implementation to perform the {@link IRemoteAction} in a
 * new thread.
 * 
 * @author ssethupathi
 * 
 */
public class RemoteActionThread implements Runnable {

    private RemoteActionManager manager;
    private IRemoteAction action;

    public RemoteActionThread(RemoteActionManager manager, IRemoteAction action) {
        this.manager = manager;
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        Response response = null;
        try {
            response = action.execute();
        } catch (JRemoteException e) {
            response = manager.stopAction(e.getMessage());
        }
        manager.actionFinished(response);
    }
}

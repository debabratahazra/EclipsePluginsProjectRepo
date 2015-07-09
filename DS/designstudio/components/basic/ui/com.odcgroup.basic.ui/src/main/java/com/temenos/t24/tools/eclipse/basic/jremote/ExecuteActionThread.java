package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredAction;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredRunnable;

/**
 * Performs the execution operation in a new thread.
 * 
 * @author ssethupathi
 * 
 */
public class ExecuteActionThread implements MonitoredRunnable {

    private IRemoteActionManager manager;
    private IRemoteAction executeAction;
    private MonitoredAction monitoredAction;

    public ExecuteActionThread(MonitoredAction monitoredAction, IRemoteAction executeAction) {
        this.monitoredAction = monitoredAction;
        this.executeAction = executeAction;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        manager = new RemoteActionManager();
        Response response = manager.performAction(executeAction);
        monitoredAction.setProcessFinished(true);
        monitoredAction.setResponse(response);
    }

    /**
     * {@inheritDoc}
     */
    public Response stopProcess() {
        return manager.stopAction("Execute operation cancelled");
    }
}

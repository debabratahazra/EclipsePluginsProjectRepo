package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredAction;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredRunnable;

/**
 * Performs the compilation operation in a seperate thread.
 * 
 * @author ssethupathi
 * 
 */
public class CompileActionThread implements MonitoredRunnable {

    private IRemoteActionManager manager;
    private IRemoteAction compileAction;
    private MonitoredAction monitoredAction;

    public CompileActionThread(MonitoredAction monitoredAction, IRemoteAction action) {
        this.monitoredAction = monitoredAction;
        this.compileAction = action;
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        manager = new RemoteActionManager();
        Response response = manager.performAction(compileAction);
        monitoredAction.setProcessFinished(true);
        monitoredAction.setResponse(response);
    }

    /**
     * {@inheritDoc}
     */
    public Response stopProcess() {
        return manager.stopAction("Compile operation cancelled");
    }
}

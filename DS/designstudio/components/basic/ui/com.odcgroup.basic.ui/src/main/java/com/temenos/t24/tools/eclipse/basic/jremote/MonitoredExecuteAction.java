package com.temenos.t24.tools.eclipse.basic.jremote;

import java.util.Map;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.AbstractMonitoredAction;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredRunnable;

/**
 * Implements the {@link AbstractMonitoredAction} for executing a T24Basic
 * subroutine in {@link RemoteSite}.
 * 
 * @author ssethupathi
 * 
 */
public class MonitoredExecuteAction extends AbstractMonitoredAction {

    private ExecuteActionThread executeThread;

    /**
     * Executes the action in a new thread which is wrapped within a process
     * monitor.
     * 
     * @param remoteSite
     * @param subroutine
     * @param argIn
     * @return response
     */
    public Response execute(RemoteSite remoteSite, String subroutine, Map<Integer, String> argIn) {
        IRemoteAction action = new RemoteExecuteAction(remoteSite, subroutine, argIn);
        executeThread = new ExecuteActionThread(this, action);
        return super.execute(null);
    }

    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return executeThread;
    }
}

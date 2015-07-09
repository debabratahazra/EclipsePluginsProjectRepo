package com.temenos.t24.tools.eclipse.basic.jremote;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.AbstractMonitoredAction;
import com.temenos.t24.tools.eclipse.basic.protocols.monitored.MonitoredRunnable;

/**
 * Implements the {@link AbstractMonitoredAction} for compiling a T24Basic
 * subroutine in {@link RemoteSite}.
 * 
 * @author ssethupathi
 * 
 */
public class MonitoredCompileAction extends AbstractMonitoredAction {

    private CompileActionThread compileThread;

    /**
     * Executes the action in a new thread which is wrapped within a process
     * monitor.
     * 
     * @param remoteSite
     * @param remoteDir
     * @param file
     * @return response
     */
    public Response execute(RemoteSite remoteSite, String remoteDir, String fileName) {
        IRemoteAction action = new RemoteCompileAction(remoteSite, remoteDir, fileName);
        compileThread = new CompileActionThread(this, action);
        return super.execute(null);
    }

    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return compileThread;
    }
}

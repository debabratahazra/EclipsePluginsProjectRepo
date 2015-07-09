package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class ThreadCompileFile implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private String basicModuleName = "";
    private String fileContents    = "";
    private String serverDirectory = "";
    private MonitoredAction action = null; 
    
    public ThreadCompileFile(MonitoredAction action, String basicModuleName, String serverDirectory, String fileContents){
        this.action   = action;
        this.sesMgr   = RemoteSessionManager.getInstance();
        this.basicModuleName = basicModuleName;
        this.fileContents    = fileContents;
        this.serverDirectory = serverDirectory;
    }

    // Execute the compilation command.
    public void run() {
        Response compileResponse;
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        compileResponse = sesMgr.compileFile(basicModuleName, fileContents, serverDirectory);
        action.setResponse(compileResponse);
        action.setProcessFinished(true);
    }

    public Response stopProcess() {
        return (sesMgr.StopCompilation());
    }

}

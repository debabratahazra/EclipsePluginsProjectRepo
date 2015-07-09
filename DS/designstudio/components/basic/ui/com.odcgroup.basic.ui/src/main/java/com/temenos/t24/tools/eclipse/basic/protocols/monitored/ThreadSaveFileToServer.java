package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class ThreadSaveFileToServer implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private String basicFilenameNoPrefix  = "";
    private String serverDir = "";
    private String basicFileContents = "";
    private MonitoredAction action = null; 
    
    public ThreadSaveFileToServer(MonitoredAction action, String basicFilenameNoPrefix, String serverDirectory, String basicFileContents){
        this.action    = action;
        this.sesMgr    = RemoteSessionManager.getInstance();
        this.basicFilenameNoPrefix  = basicFilenameNoPrefix;
        this.serverDir = serverDirectory;
        this.basicFileContents = basicFileContents;        
    }

    public void run() {
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        Response response = sesMgr.saveFile(basicFilenameNoPrefix, basicFileContents, serverDir);

        action.setResponse(response);
        action.setProcessFinished(true);
    }
    
    public Response stopProcess(){
        return (sesMgr.stopSaveFile());            
    }    
}

package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class ThreadRetrieveFile implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private String basicFilenameNoPrefix  = "";
    private String serverDir = "";
    private MonitoredAction action = null; 
    private boolean readOnly = false;
    
    public ThreadRetrieveFile(MonitoredAction action, String filename, String directory, boolean readOnly){
        this.action    = action;
        this.sesMgr    = RemoteSessionManager.getInstance();
        this.basicFilenameNoPrefix  = filename;
        this.serverDir = directory;
        this.readOnly  = readOnly;        
    }

    public void run() {
        Response res;
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        if(readOnly){
            // opens a file without checking for locks or locking it.
            res = sesMgr.retrieveReadOnlyFile(basicFilenameNoPrefix, serverDir);
            
        } else {
            // checks whether file is already locked, if not it'll be opened and then locked.
            res = sesMgr.retrieveFile(basicFilenameNoPrefix, serverDir);
            
        }
        action.setResponse(res);
        action.setProcessFinished(true);
    }
    
    // Invocation to stop process.  
    public Response stopProcess(){
        return (sesMgr.stopGetFile());            
    }    
}

package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class ThreadSignOn implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private String username  = "";
    private String password  = "";
    private String channel   = "";
    private MonitoredAction action = null; 
    
    public ThreadSignOn(MonitoredAction action, String username, String password, String channel){
        this.action   = action;
        this.sesMgr   = RemoteSessionManager.getInstance();
        this.username = username;
        this.password = password;
        this.channel  = channel;
    }

    // Execute the command.
    public void run() {
        Response signOnResponse;
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        signOnResponse = sesMgr.signOn(username, password, channel);
        action.setResponse(signOnResponse);
        action.setProcessFinished(true);
    }
    
    // Invocation to stop process.  
    public Response stopProcess(){
        return (sesMgr.stopSignOn());            
    }    
}

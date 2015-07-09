package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class ThreadRepeatPassword implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private String processType = "";
    private String username  = "";
    private String firstPswd = "";
    private String secondPswd = "";
    private String channel = "";
    private MonitoredAction action = null; 
    
    /**
     * @param processType - e.g. ProtocolConstants.RESPONSE_TYPE_REPEAT_PASSWORD 
     */
    public ThreadRepeatPassword(MonitoredAction action, String processType, String username, 
                                       String firstPswd, String secondPswd, String channel){
        this.action     = action;
        this.sesMgr     = RemoteSessionManager.getInstance();
        this.processType = processType;
        this.username   = username;
        this.firstPswd  = firstPswd;
        this.secondPswd = secondPswd;
        this.channel    = channel;
    }

    // Execute the command.
    public void run() {
        Response response;
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        
        if (ProtocolConstants.RESPONSE_TYPE_EXPIRED_PASSWORD.equals(processType)) {
            response = sesMgr.repeatPasswordExpired(username, firstPswd, secondPswd, channel);
        } else {
            response = sesMgr.repeatPasswordNewUser(username, firstPswd, secondPswd, channel);
        }
            
        action.setResponse(response);
        action.setProcessFinished(true);
    }
    
    // Invocation to stop process.  
    public Response stopProcess(){
        
        if (ProtocolConstants.RESPONSE_TYPE_EXPIRED_PASSWORD.equals(processType)) {
            return (sesMgr.stopRepeatPasswordExpired());
        } else {
            return (sesMgr.stopRepeatPasswordNewUser());
        }           
    }    
}

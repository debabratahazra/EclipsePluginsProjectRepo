package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import java.util.ArrayList;

import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants.FILE_EXISTS_ON_SERVER;
import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class ThreadFileExistsOnServer implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private String basicFilenameNoPrefix  = "";
    private String serverDir = "";
    private MonitoredAction action = null; 
    
    public ThreadFileExistsOnServer(MonitoredAction action, String basicFilenameNoPrefix, String serverDirectory){
        this.action    = action;
        this.sesMgr    = RemoteSessionManager.getInstance();
        this.basicFilenameNoPrefix  = basicFilenameNoPrefix;
        this.serverDir = serverDirectory;
    }

    @SuppressWarnings("unchecked")
    public void run() {
        RemoteSessionManager sesMgr = RemoteSessionManager.getInstance();
        Response response = sesMgr.getServerFiles(serverDir, "EQ", basicFilenameNoPrefix);

        if (response.getPassed()) {
            ArrayList<String> files = (ArrayList<String>)response.getObject();
            if (files.size() != 0) {
                /** file already exists in server */
                response.setObject(FILE_EXISTS_ON_SERVER.TRUE);
            } else {
                response.setObject(FILE_EXISTS_ON_SERVER.FALSE);
            }
        }         
        action.setResponse(response);
        action.setProcessFinished(true);
    }
    
    public Response stopProcess(){
        return (sesMgr.stopSaveFile());            
    }    
}

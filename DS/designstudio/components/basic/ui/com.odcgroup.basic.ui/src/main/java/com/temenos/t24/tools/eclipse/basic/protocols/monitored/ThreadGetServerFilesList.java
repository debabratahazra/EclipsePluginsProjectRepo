package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.views.IT24ViewItem;
import com.temenos.t24.tools.eclipse.basic.views.ViewManager;
import com.temenos.t24.tools.eclipse.basic.views.ViewConstants.T24_VIEW_ITEM_CATEGORY;

public class ThreadGetServerFilesList implements MonitoredRunnable {

    private RemoteSessionManager sesMgr = null;
    private MonitoredAction action = null; 
    private T24_VIEW_ITEM_CATEGORY viewItem;
    
    public ThreadGetServerFilesList(MonitoredAction action, T24_VIEW_ITEM_CATEGORY viewItem) {
        this.action    = action;
        this.sesMgr    = RemoteSessionManager.getInstance();
        this.viewItem  = viewItem;
    }

    // Execute the command.
    public void run() {
        ViewManager viewManager = new ViewManager();
        IT24ViewItem[] items = viewManager.getViewItems(null, viewItem);
        
        Response response = new Response();
        response.setObject((IT24ViewItem[])items);
        response.setPassed(true);
        
        action.setProcessFinished(true);
        action.setResponse(response);
    }
    
    public Response stopProcess(){
        return sesMgr.stopGetServerFiles();            
    }    
}

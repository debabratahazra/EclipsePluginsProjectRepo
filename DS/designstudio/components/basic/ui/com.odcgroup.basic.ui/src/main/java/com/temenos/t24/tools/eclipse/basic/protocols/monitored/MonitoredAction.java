package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public interface MonitoredAction {
    /**
     * Call back method to allow the monitored thread setting the Response
     * back to the monitor. The response is set as soon as the monitored
     * thread is finished by either natural termination or the user stopping it.
     * 
     * @param response
     */
    public void setResponse(Response response);
    
    /**
     * Call back method to allow the monitored thread indicate to the Monitor
     * that the thread is finished.
     * 
     * @param processFinished
     */
    public void setProcessFinished(boolean processFinished);
}


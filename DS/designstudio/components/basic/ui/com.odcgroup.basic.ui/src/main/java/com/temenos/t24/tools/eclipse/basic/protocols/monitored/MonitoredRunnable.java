package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;


public interface MonitoredRunnable extends Runnable {
    public Response stopProcess();
}

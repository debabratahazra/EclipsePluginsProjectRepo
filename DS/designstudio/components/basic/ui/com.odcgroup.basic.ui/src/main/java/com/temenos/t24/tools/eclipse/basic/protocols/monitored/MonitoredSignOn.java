package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class MonitoredSignOn extends AbstractMonitoredAction {

    public Response execute(String username, String password, String channel) {
        String[] params = new String[]{username, password, channel};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadSignOn(MonitoredSignOn.this, params[0], params[1], params[2]);
    }
}

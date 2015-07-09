package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class MonitoredRepeatPassword extends AbstractMonitoredAction {

    public Response execute(String processType, String username, String firstPswd, String secondPswd, String channel) {
        String[] params = new String[]{processType, username, firstPswd, secondPswd, channel};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadRepeatPassword(MonitoredRepeatPassword.this, params[0], params[1], params[2], params[3], params[4]);
    }
}

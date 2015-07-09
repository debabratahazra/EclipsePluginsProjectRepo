package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class MonitoredRetrieveFileFromServer extends AbstractMonitoredAction {

    public Response execute(String basicFilenameNoPrefix, String serverDirectory, boolean readOnly) {
        String[] params = new String[]{basicFilenameNoPrefix, serverDirectory, Boolean.toString(readOnly)};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadRetrieveFile(MonitoredRetrieveFileFromServer.this, params[0], params[1], Boolean.parseBoolean(params[2]));
    }
}

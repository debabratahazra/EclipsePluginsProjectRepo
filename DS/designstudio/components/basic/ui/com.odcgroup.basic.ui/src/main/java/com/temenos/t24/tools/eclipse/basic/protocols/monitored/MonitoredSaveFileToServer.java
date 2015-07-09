package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class MonitoredSaveFileToServer extends AbstractMonitoredAction {

    public Response execute(String basicFilenameNoPrefix, String serverDirectory, String basicFileContents) {
        String[] params = new String[]{basicFilenameNoPrefix, serverDirectory, basicFileContents};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadSaveFileToServer(MonitoredSaveFileToServer.this, params[0], params[1], params[2]);
    }
}

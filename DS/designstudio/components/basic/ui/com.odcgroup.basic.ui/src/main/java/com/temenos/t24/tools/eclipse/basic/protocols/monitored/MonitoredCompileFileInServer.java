package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

public class MonitoredCompileFileInServer extends AbstractMonitoredAction {

    public Response execute(String basicFilenameNoPrefix, String serverDirectory, String fileContents) {
        String[] params = new String[]{basicFilenameNoPrefix, serverDirectory, fileContents};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadCompileFile(MonitoredCompileFileInServer.this, params[0], params[1], params[2]);
    }
}

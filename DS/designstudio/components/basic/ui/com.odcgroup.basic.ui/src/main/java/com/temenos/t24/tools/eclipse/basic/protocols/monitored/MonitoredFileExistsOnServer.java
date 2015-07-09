package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * To check whether a file already exists on the server.
 * 
 * @author lfernandez
 *
 */
public class MonitoredFileExistsOnServer extends AbstractMonitoredAction {

    public Response execute(String basicFilenameNoPrefix, String serverDirectory) {
        String[] params = new String[]{basicFilenameNoPrefix, serverDirectory};
        return super.execute(params);
    }
    
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadFileExistsOnServer(MonitoredFileExistsOnServer.this, params[0], params[1]);
    }
}

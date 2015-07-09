package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * This class executes test by supplying a worker thread with which this action
 * can be monitored
 * 
 * @author ssethupathi
 * 
 */
public class MonitoredExecuteTestAction extends AbstractMonitoredAction {

    /**
     * Executes the test action by invoking a thread of
     * {@link ThreadExecuteTest} and passing the request to the lower layer
     * 
     * @param fileName
     * @param fileDirectory
     * @param fileContent
     * @return Response
     */
    public Response execute(String fileName, String fileDirectory, String fileContent) {
        String[] params = new String[] { fileName, fileDirectory, fileContent };
        return super.execute(params);
    }

    /**
     * Returns the monitored runnable of execute test action
     */
    @Override
    protected MonitoredRunnable getMonitoredThread(String[] params) {
        return new ThreadExecuteTest(MonitoredExecuteTestAction.this, params[0], params[1], params[2]);
    }
}

package com.temenos.t24.tools.eclipse.basic.protocols.monitored;

import com.temenos.t24.tools.eclipse.basic.protocols.RemoteSessionManager;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * The worker thread which actually executes the test action by passing the
 * request to the lower layer
 * 
 * @author ssethupathi
 * 
 */
public class ThreadExecuteTest implements MonitoredRunnable {

    private RemoteSessionManager sessionManager = RemoteSessionManager.getInstance();
    private MonitoredAction action;
    private String fileName;
    private String fileDirectory;
    private String fileContent;

    /**
     * Construction to this thread
     */
    public ThreadExecuteTest(MonitoredAction action, String fileName, String fileDirectory, String fileContent) {
        this.action = action;
        this.fileName = fileName;
        this.fileDirectory = fileDirectory;
        this.fileContent = fileContent;
    }

    /**
     * Stops processes when user interrupted
     */
    public Response stopProcess() {
        return sessionManager.stopTestExecution();
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        Response executeTestResponse = null;
        executeTestResponse = sessionManager.executeTest(fileName, fileDirectory, fileContent);
        action.setResponse(executeTestResponse);
        action.setProcessFinished(true);
    }
}

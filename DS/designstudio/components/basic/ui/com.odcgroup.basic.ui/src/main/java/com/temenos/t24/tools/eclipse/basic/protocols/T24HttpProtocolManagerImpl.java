package com.temenos.t24.tools.eclipse.basic.protocols;


/**
 * This class is at the bottom of the protocol stack: 
 * 1.- GUI                    => interfaces with user
 * 2.- RemoteSessionManager   => holds sesssion keys. It's the main interface
 * 3.- Actions                => all the action bussiness logic are in here
 * 4.- HttpProtocolManager    => responsible for handling http comms.
 * It creates two threads for handling the comms: 
 * One thread is a timer, the other is the one which performs the comms.
 */
public class T24HttpProtocolManagerImpl implements T24HttpProtocolManager {
    // Threads finish condition flags.    
    private boolean httpFinished    = false;
    private boolean httpStopRequest = false;
    
    private Response response       = null; // Response for this object client.
    
    
    public T24HttpProtocolManagerImpl(){
    }
    
    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.protocols.T24HttpProtocolManager#sendCommand(com.temenos.t24.tools.eclipse.basic.protocols.Command)
     */
    public Response sendCommand(Command cmd) {

            // Connect to external HTTP Server
            // Reset threads finish condition flags.
            httpFinished = false;
            httpStopRequest = false;

            // Start http thread
            T24HttpProtocolManagerThread httpMgrThread = new T24HttpProtocolManagerThread(this, cmd);
            Thread httpThread = new Thread(httpMgrThread);
            httpThread.start();
            
            // Wait in an idel loop for one of the threads (timer and http) to finish, or a stop
            // request
            while (!httpFinished && !httpStopRequest) {
                // sleep ms
                long sleepTime = 50; 
                try{ Thread.sleep(sleepTime); } catch(InterruptedException e){}
            }
            
            // HANDLE THE DIFFERENT TERMINATIONS
            if (httpFinished) {
                // Process has finished normally without timing out or being
                // stopped prematurely.
                // The response has been returned and filled in by
                // T24HttpProtocolManagerThread,
                // so just return it to the client.
                return response;
                
            } else if (httpStopRequest) {
                // The client requested to stop the http comms process.
                // Stop the http thread.
                httpThread.stop();
                httpMgrThread.closeResources();
                // Build the appropriate response
                response = new Response();
                response.setPassed(false);
                response.setRespMessage("Http communication has been stopped when connecting to " + ProtocolUtil.buildUrlString() + ".");
                return response;
                
            } else {
                // THIS SHOULDN'T BE REACHED. ADD IT FORM COMPLETENESS
                httpThread.stop();
                httpMgrThread.closeResources();
                // Build the appropriate response
                response = new Response();
                response.setPassed(false);
                response.setRespMessage("Http communication interrupted with an abnormal termination while connecting to " + ProtocolUtil.buildUrlString() + ".");
                return response;
            }
    }


    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.protocols.T24HttpProtocolManager#httpFinished(com.temenos.t24.tools.eclipse.basic.protocols.Response)
     */
    public void httpFinished(Response res){
        this.httpFinished = true;
        this.response = res;
    }
    
    /* (non-Javadoc)
     * @see com.temenos.t24.tools.eclipse.basic.protocols.T24HttpProtocolManager#stopHttp()
     */
    public void stopHttp(){
        this.httpStopRequest = true;
    }
   
}

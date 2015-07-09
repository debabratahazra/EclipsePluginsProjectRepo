package com.temenos.t24.tools.eclipse.basic.protocols;

public interface T24HttpProtocolManager {

    /**
     * Main class for sending/receiving commands down to the T24 server.
     * @param cmd - XML Command that will be sent over HTTP to the remote server 
     * @return Response
     */
    public Response sendCommand(Command cmd);

    /**
     * Sets instance variable indicating http comms process finished to true, and
     * populates the Response. This method is intended to be invoked by an external 
     * thread which performs the actual HTTP communication. 
     * @param res - Response.
     */
    public void httpFinished(Response res);

    /**
     * Sets instance variable indicating a request to stop the http comms to true. 
     * This method is intended to be invoked by an external client. 
     */
    public void stopHttp();
}

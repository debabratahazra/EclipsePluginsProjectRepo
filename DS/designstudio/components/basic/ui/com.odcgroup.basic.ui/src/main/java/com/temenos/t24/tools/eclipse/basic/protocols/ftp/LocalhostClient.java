package com.temenos.t24.tools.eclipse.basic.protocols.ftp;


/**
 * TODO: Document me!
 *
 * @author hdebabrata
 *
 */
public class LocalhostClient {
    
    private boolean connect = false;

    /**
     * @param hostIP
     * @param i
     */
    public void connect(String hostIP, int i) {
        connect = true;
    }

    /**
     * @return
     */
    public boolean isConnected() {
        return connect;
    }

    /**
     * 
     */
    public void disconnect() {
        connect = false;
    }
}

package com.temenos.t24.tools.eclipse.basic.jremote;

import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineNotFoundException;
import com.jbase.jremote.JSubroutineParameters;

/**
 * Interface defines the operations which are required to be performed in a
 * {@link RemoteSite} through JCA. Implementation of this interface would
 * decorate the {@link JConnection} instance and binds it to the
 * {@link RemoteSite}
 * 
 * @author ssethupathi
 * 
 */
public interface IJremoteClient {

    /**
     * Establishes a connection to the {@link RemoteSite} through the JCA.
     * 
     * @return true if connected and, false otherwise.
     */
    public boolean connect();

    /**
     * Disconnects from the {@link RemoteSite}.
     */
    public void disconnect();

    /**
     * Checks if this client is connected to the {@link RemoteSite}.
     * 
     * @return true if connected and, false otherwise.
     */
    public boolean isConnected();

    /**
     * Performs the compile operation in the {@link RemoteSite}.
     * 
     * @param directory
     * @param file
     * @return response from the JBC compiler
     * @throws JRemoteException
     */
    public String compile(String directory, String file) throws JRemoteException;

    /**
     * Runs the passed-in subroutine with the passed-in parameters.
     * 
     * @param subroutine
     * @param params
     * @return parameters return from the execution
     * @throws JSubroutineNotFoundException
     * @throws JRemoteException
     */
    public JSubroutineParameters run(String subroutine, JSubroutineParameters params) throws JSubroutineNotFoundException,
            JRemoteException; // TODO Parameter wrapper?
}

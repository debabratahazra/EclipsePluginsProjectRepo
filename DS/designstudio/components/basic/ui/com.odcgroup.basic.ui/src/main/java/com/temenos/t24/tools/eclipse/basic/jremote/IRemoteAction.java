package com.temenos.t24.tools.eclipse.basic.jremote;

import com.jbase.jremote.JRemoteException;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;


/**
 * Interface defines any remote action to be executed through JCA
 * 
 * @author ssethupathi
 * 
 */
public interface IRemoteAction {

    /**
     * Executes the this remote action
     * 
     * @return Response response
     * @throws JRemoteException
     */
    public Response execute() throws JRemoteException;
}

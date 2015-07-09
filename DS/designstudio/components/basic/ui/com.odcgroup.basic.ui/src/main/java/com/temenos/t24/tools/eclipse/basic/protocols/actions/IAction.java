package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import com.temenos.t24.tools.eclipse.basic.protocols.Response;

/**
 * Common action interface.
 */
public interface IAction {
    public Response processAction(String[] args);
    public Response stopProcess();
}

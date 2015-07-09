package com.temenos.t24.tools.eclipse.basic.jremote;

import java.util.Map;

import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JRemoteException;
import com.jbase.jremote.JSubroutineParameters;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.ftp.RemoteSite;

public class RemoteExecuteAction extends AbstractRemoteAction {

    private RemoteSite remoteSite;
    private String subroutine;
    private Map<Integer, String> argIn;
    private Response response;

    public RemoteExecuteAction(RemoteSite remoteSite, String subroutine, Map<Integer, String> argIn) {
        this.remoteSite = remoteSite;
        this.subroutine = subroutine;
        this.argIn = argIn;
    }

    public Response execute() throws JRemoteException, NumberFormatException {
        JSubroutineParameters params = constructSubroutineParams();
        IJremoteClient jremoteClient = getJremoteClient();
        if (jremoteClient == null) {
            return response;
        }
        JSubroutineParameters returnParams = jremoteClient.run(subroutine, params);
        return buildResponse(returnParams);
    }

    @Override
    protected String getSiteName() {
        return remoteSite.getSiteName();
    }

    @Override
    protected void setFailedResponse(Response response) {
        this.response = response;
    }

    private JSubroutineParameters constructSubroutineParams() {
        JSubroutineParameters params = new JSubroutineParameters();
        JDynArray array1 = new JDynArray();
        array1.insert("", 1);
        array1.insert(argIn.get(1), 2);
        params.add(array1);
        JDynArray array2 = new JDynArray();
        array2.insert(" ", 1);
        params.add(array2);
        return params;
    }

    private Response buildResponse(JSubroutineParameters returnParams) {
        String result = returnParams.get(1).get(1);
        String xmlOut = "<?xml version=\"1.0\" ?><results>";
        xmlOut = xmlOut + result + "</results>";
        response = new Response();
        response.setPassed(true);
        response.setObject(xmlOut);
        return response;
    }
}

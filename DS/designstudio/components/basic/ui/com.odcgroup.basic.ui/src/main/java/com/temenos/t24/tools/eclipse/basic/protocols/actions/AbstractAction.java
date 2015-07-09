package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.protocols.T24HttpProtocolManager;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;


public abstract class AbstractAction implements IAction {
    /** reference to the http protocol manager */
    protected T24HttpProtocolManager httpMgr = null; 
    
    /** Document that holds the response returned by this class to the layer above it */
    protected Document actionResponseDoc = null;    

    /** 
     * Analyses the response from the server; looking for 
     * sessionToken, error messages and contents. 
     * */
    public abstract Response processResponse(Command cmd, Response res, String[] args);

    /**
     * Creates a brand new Dom style response and assign it
     * to actionResponseDoc.
     * This method is to be called by each action at the beginning
     * of processing the action.
     */
    protected void initialiseResponse(){
        actionResponseDoc = DocumentHelper.createDocument();
        // Add the root element
        actionResponseDoc.addElement("response");
    }
    
    /**
     * Check for common reasons for a bad response.
     * Reasons for a bad response:
     * - no token returned (except for signoff and browser commands (e.g. getChannels)
     * - bad raw response (e.g. http failure and null was returned)
     * - security violation message
     * @param responseXml
     * @return Response -  
     */
    protected Response checkBadResponse(String responseXml) {
       Response response = new Response();
       response.setPassed(true);
       
       // Extract token and embedded msg (if any).
       String sessionTokenFromServer = XmlUtil.getSafeText(responseXml, "//token");
       String msg = XmlUtil.getSafeText(responseXml, "//messages/msg");
       /** Note, some responses have an errorMessage */
       String errorMsg = XmlUtil.getSafeText(responseXml, "//errorMessage");
       String error = XmlUtil.getSafeText(responseXml, "//error");
           
       if(!ActionCommon.isRawResponseOK(responseXml)){
           // There is a comms error.
           response.setPassed(false);
           response.setRespMessage(ProtocolConstants.MESSAGE_FAIL_HTTP_CONNECTION);
           
       } else if(!"".equals(ActionCommon.getMsgError(msg))){
           // There is an error message
           response.setPassed(false);
           response.setRespMessage(msg + " " + ProtocolConstants.MESSAGE_SECURITY_VIOLATION);
           
       } else if(!"".equals(errorMsg) || !"".equals(error)) {
           // There is an error message
           response.setPassed(false);
           response.setRespMessage(errorMsg+error);
           
       } else if ("".equals(sessionTokenFromServer)) {
           /** No Token returned by the server => this is an error happened, unless it is a repeat password process. */
           /** Check if it was due to an repeat password process (e.g. a New User was set up, or password expired) */
           String responseType = XmlUtil.getSafeText(responseXml, "//responseType");
           if (ProtocolConstants.RESPONSE_TYPE_EXPIRED_PASSWORD.equals(responseType) ||
               ProtocolConstants.RESPONSE_TYPE_REPEAT_PASSWORD.equals(responseType)) { 
               
               /** This is the expected response after a new user has been set up, or password has expired. */
               response.setPassed(true);
               
           } else {
               response.setPassed(false);    
               response.setRespMessage(ProtocolConstants.MESSAGE_SECURITY_VIOLATION);
               response.setObject((String)responseXml);
           }
       }
       
       return response;
    }

    /**
     * Method used to inject (e.g. Spring framework) the http manager.
     * @param httpMgr
     */
    public void setHttpMgr(T24HttpProtocolManager httpMgr) {
        this.httpMgr = httpMgr;
    }
    
    /**
     * Stops http comms process.
     */
    public Response stopProcess(){
        if(httpMgr!=null){
            httpMgr.stopHttp();
        }
        Response res = new Response();
        res.setPassed(false);
        res.setRespMessage(ProtocolConstants.MESSAGE_PROCESS_STOPPED);
        return res;
    }  
    
    /**
     * Utility method used for debugging
     */
    protected void debugPrintResult(Command cmd, Response res){
    }
}

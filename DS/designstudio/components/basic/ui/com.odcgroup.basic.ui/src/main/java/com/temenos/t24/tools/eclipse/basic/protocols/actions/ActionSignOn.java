package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Signs on user.
 */
public class ActionSignOn extends AbstractAction {
    
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args){
        String actionId  = args[0];
        String username  = args[1];
        String firstPswd = args[2];
        String secondPswd = args[3]; // Note: secondPswd may not always be needed, thus not always populated.
        String channel   = args[4]; //  One of the channels defined in the Browser configuration file channels.xml 
        
        // Store the channel. It'll be used in subsequent commands
        ProtocolUtil pu = new ProtocolUtil();
        pu.setCurrentChannel(channel);
        
        Command cmd = new Command(ProtocolConstants.COMMAND_SIGN_ON);
        cmd.setBody(buildCommandBody(actionId, username, firstPswd, secondPswd));
        
        // Send Command over HTTP
        Response res = httpMgr.sendCommand(cmd);
        debugPrintResult(cmd, res);
        
        if (!res.getPassed()) {
            // The HTTP communication failed. No need to process the command
            return res;
        }
        
        // Process response
        res = processResponse(cmd, res, args);
        
        return res;        
    }      
    
    /**
     * Analyses the response contents, taking different actions based on them,
     * and transforming the response itself accordingly, so it can be passed
     * to the layer above.
     */
    public Response processResponse(Command cmd, Response inResponse, String[] args) {
        Response outResponse = new Response(inResponse);
        
        // Get the original response from remote server (i.e. Browser)
        String responseXml = (String) inResponse.getObject();        
        String responseType = XmlUtil.getText(responseXml, "//responseType");
        String resBody = "";
        
        /** Check for standard bad responses */
        outResponse = checkBadResponse(responseXml);
        if (!outResponse.getPassed()) {
            // don't proceed processing.
            return outResponse;
        }
        
        /** Extract the token number, which is set by the server */
        String sessionTokenFromServer = XmlUtil.getSafeText(responseXml, "//token");
        outResponse.setSessionToken(sessionTokenFromServer);
        
        if (ProtocolConstants.RESPONSE_TYPE_ERROR_LOGIN.equals(responseType)) {
            
            outResponse.setPassed(false);
            outResponse.setRespMessage(responseType);
            resBody = buildXMLResponse(responseXml, false);
            
        } else if(ProtocolConstants.RESPONSE_TYPE_REPEAT_PASSWORD.equals(responseType) ||
                  ProtocolConstants.RESPONSE_TYPE_EXPIRED_PASSWORD.equals(responseType)) {
            
            outResponse.setPassed(true);
            outResponse.setRespMessage(responseType);
            resBody = buildXMLResponse(responseXml, true);
            
        } else {
            // Succeeded in signing in, responseType = XML.FRAMES
            outResponse.setPassed(true);
            resBody = buildXMLResponse(responseXml, true);
            
        }  
            
        outResponse.setObject((String) resBody);
        return outResponse;
    }

    
    /**
     * Builds the XML Response with the most relevant response elements
     * 
     * @param responseText - xml message sent back by the Browser app
     * @param commandPassed - true/false
     * @return String containing the XML.
     */
    public String buildXMLResponse(String responseText, boolean commandPassed){
        
        // Build a DOM doc with the command data
        Document dom = DocumentHelper.createDocument();
        Element root = dom.addElement("response");
        if(commandPassed){
            root.addElement("token").addText(XmlUtil.getSafeText(responseText, "//token"));
            root.addElement("responseType").addText(XmlUtil.getSafeText(responseText, "//responseType"));
            root.addElement("user").addText(XmlUtil.getSafeText(responseText, "//user"));
            root.addElement("routine").addText(XmlUtil.getSafeText(responseText, "//routine"));
        } else {
            root.addElement("error").addText(XmlUtil.getText(responseText, "//error"));
        }
        return dom.asXML();
    }    
    
    
    /**
     * Builds the XML command that will be sent down to Browser for signing on 
     * the session. 
     * @param actionId - e.g. ProtocolConstants.ID_SIGN_ON
     * @param username 
     * @param firstPswd - password used for normal signing in
     * @param secondPswd - password repetition used for new user, password expire processes.
     * @param channel - one of the channels defined in the Browser configuration file channels.xml
     * @return String containing the XML.
     */
    public String buildCommandBody(String actionId, String username, String firstPswd, String secondPswd) {
        String header = ActionCommon.getCommandHeader();
        
        // Build a DOM doc with the command data
        Document dom = DocumentHelper.createDocument();
        Element root = dom.addElement("ofsSessionRequest")
            .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        
        root.addElement("requestType").addText(getRequestType(actionId));
        
        Element reqArgs = root.addElement("requestArguments");
        
        if (ProtocolConstants.ID_SIGN_ON.equals(actionId)) {
            reqArgs.addElement("signOnName").addText(username);
            reqArgs.addElement("password").addText(firstPswd);
            
        } else {
            reqArgs.addElement("routineName").addText("OS.PASSWORD");
            reqArgs.addElement("routineArgs").addText(getProcess(actionId) + ":" + username + ":" + firstPswd + ":" + secondPswd);
        }

        return header+dom.asXML();
    }
    
    
    private String getProcess(String actionId) {
        if (ProtocolConstants.ID_PASSWORD_NEWUSER.equals(actionId)) {
            return "PROCESS.REPEAT";
        } else {
            return "PROCESS.EXPIRED";
        }
    }
    
    private String getRequestType(String actionId) {
        if (ProtocolConstants.ID_SIGN_ON.equals(actionId)) {
            return ProtocolConstants.REQUEST_TYPE_CREATE_SESSION;
            
        } else {
            return ProtocolConstants.REQUEST_TYPE_UTILITY_ROUTINE;
            
        }        
    }
}

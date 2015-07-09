package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Signs off user.
 */
public class ActionSignOff extends AbstractAction {
    
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args){
        String sessionToken = args[0];
        String username = args[1];        
        Response res = new Response();
        
        Command cmd = new Command(ProtocolConstants.COMMAND_SIGN_OFF);
        cmd.setBody(buildCommandBody(sessionToken, username));
        
        // Send Command over HTTP
        res = httpMgr.sendCommand(cmd);
        debugPrintResult(cmd, res);
        if(!res.getPassed()){
            // The HTTP communication failed. No need to process the command
            return res;
        }
        
        // Process response
        res = processResponse(cmd,res, args);
        return res;        
    }      

    /**
     * Analyses the response contents, taking different actions based on them,
     * and transforming the response itself accordingly, so it can be passed
     * to the layer above.
     */
    public Response processResponse(Command cmd, Response inResponse, String[] args) {
        String responseXml = (String)inResponse.getObject();
        
        if(!ActionCommon.isRawResponseOK(responseXml)){
            // There was an error in the comms. 
            inResponse.setPassed(false);
            inResponse.setRespMessage(ProtocolConstants.MESSAGE_FAIL_HTTP_CONNECTION);
            
        } else {        
            Document dom = DocumentHelper.createDocument();
            Element root = dom.addElement("response");
            root.addElement("token").addText(XmlUtil.getText(responseXml, "//token"));
            root.addElement("responseType").addText(XmlUtil.getText(responseXml, "//responseType"));
            root.addElement("user").addText(XmlUtil.getText(responseXml, "//user"));
            Element logoff = root.addElement("logoff");
            if(logoff!=null)
            logoff.addElement("title").addText(XmlUtil.getSafeText(responseXml,"//logoff/title"));
            logoff.addElement("mainmsg").addText(XmlUtil.getSafeText(responseXml,"//logoff/mainmsg"));
            logoff.addElement("msg").addText(XmlUtil.getSafeText(responseXml,"//logoff/msg"));
    
            inResponse.setPassed(true); // Signing off always succeeds
            inResponse.setObject((String)dom.asXML());
        }
        return inResponse;
    }
    
    
    /**
     * Builds the XML command that will be sent down to Browser for signing on 
     * the session. 
     * @param username
     * @param sessionToken
     * @return String containing the XML.
     */
    public String buildCommandBody(String sessionToken, String username){
        String header = ActionCommon.getCommandHeader();
        
        // Build a DOM doc with the command data
        Document dom = DocumentHelper.createDocument();
        Element root = dom.addElement("ofsSessionRequest")
            .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.addElement("requestType").addText("DESTROY.SESSION");
        root.addElement("token").addText(sessionToken);
        Element reqArgs = root.addElement("requestArguments");
        reqArgs.addElement("routineArgs").addText(username);
        reqArgs.addElement("companyId");
        reqArgs.addElement("windowName").addText("_parent");
        reqArgs.addElement("compScreen").addText("MAIN");

        return header+dom.asXML();
    }
  
}

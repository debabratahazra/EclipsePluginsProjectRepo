package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Unlock file in server.
 */
public class ActionUnlock extends AbstractAction {
        
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args){
        String sessionToken  = args[0];
        String filename      = args[1];        
        
        initialiseResponse();
        
        Response resProcess = null;
        
        Command cmd = buildCommand(sessionToken, filename);

        // Send Command to Browser via HTTP
        resProcess = httpMgr.sendCommand(cmd);
        debugPrintResult(cmd, resProcess);
            
        if(!resProcess.getPassed()){
            // HTTP connection failed somewhere. Don't process any further.
            return resProcess;
        }
            
         // Process response
         resProcess = processResponse(cmd, resProcess, args);
            
         if(!resProcess.getPassed()){
             // T24Server failed processing the command. Don't process any further.
             return resProcess;
         }
            
         // get the sessionToken for the next command (if any)
         sessionToken = resProcess.getSessionToken();
            
        resProcess.setObject((String)actionResponseDoc.asXML());
        return resProcess;         
    }    
    
    /**
     * Analyses the response contents, taking different actions based on them,
     * and transforming the response itself accordingly, so it can be passed
     * to the layer above.
     */
    public Response processResponse(Command cmd, Response inResponse, String args[]) {
        // Create a new response object, copy of the passed one.
        Response outResponse = new Response(inResponse);
        
        Element root = actionResponseDoc.getRootElement();

        // Get the original response from remote server (i.e. Browser)
        String responseXml = (String) inResponse.getObject();        
        
        outResponse = checkBadResponse(responseXml);
        if (!outResponse.getPassed()) {
            root.addElement("error").addText(outResponse.getRespMessage());
            // don't proceed processing.
            return outResponse;
        }
        
        // Extract the token number, which is set by the server
        String sessionTokenFromServer = XmlUtil.getSafeText(responseXml, "//token");
        outResponse.setSessionToken(sessionTokenFromServer);
        
        if (inResponse.getPassed()){

            // CHECK FOR ERRORS IN THE RESONSE MESSAGE
            String error = XmlUtil.getText(responseXml, "//tdevstudio/error");
            if(error != null){
                // T24 Server couldn't process the command
                String errorMsg = XmlUtil.getText(responseXml, "//tdevstudio/error");
                outResponse.setPassed(false);
                outResponse.setRespMessage(errorMsg);
                root.addElement("error").addText(errorMsg);
                
            } else {  
                // No error found => the command could be processed
                String saved = XmlUtil.getSafeText(responseXml, "//tdevstudio/locked");
                root.addElement("locked").addText(saved);
                outResponse.setPassed(true);
            }         
        } 
        return outResponse;
    }   
    
    /**
    * @param cmdSequence - several commands are sent in sequence to the server
    * @param sessionToken - security session provided by sessionManager
    * @param progname - e.g. ACCT.STMT.DATE
    */
    public Command buildCommand(String sessionToken, String basicModuleName){
        Command cmd = new Command(ProtocolConstants.COMMAND_UNLOCK_FILE);
        cmd.setBody(buildCommandBody(sessionToken, basicModuleName));
        return cmd;
    }
     
    /**
     * @param tokenString
     * @param prognameString
     * @return
     */
    public String buildCommandBody(String tokenString, String prognameString){
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev  = buildCommonCommandBody(dom, tokenString);
        dev.addElement("action").addText("REMOVE.LOCK");
        dev.addElement("progname").addText(prognameString);
        
        return header+dom.asXML();
    }

    
    /**
     */
    public Element buildCommonCommandBody(Document dom, String tokenString){
        
        // Build a DOM doc 
        Element root = dom.addElement("ofsSessionRequest")
            .addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.addElement("requestType").addText("UTILITY.ROUTINE");
        root.addElement("token").addText(tokenString);
        root.addElement("detailtag");
        Element reqArgs     = root.addElement("requestArguments");
        reqArgs.addElement("routineName").addText("EB.DEV.STUDIO");
        Element routineArgs = reqArgs.addElement("routineArgs");
        Element dev         = routineArgs.addElement("dev");
        reqArgs.addElement("companyId");
        reqArgs.addElement("windowName");
        reqArgs.addElement("compScreen");
        reqArgs.addElement("unlock");
        reqArgs.addElement("closing");
        
        return dev;
    }    
  
}

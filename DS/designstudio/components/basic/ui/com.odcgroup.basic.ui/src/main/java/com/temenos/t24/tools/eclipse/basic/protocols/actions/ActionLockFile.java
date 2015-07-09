package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Locks file in the server, so other users can't change its contents.
 * The user will unlock the file at some point later.
 */
public class ActionLockFile extends AbstractAction {
        
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args){
        String sessionToken  = args[0];
        String filename      = args[1];        
        String username      = args[2]; // local username, used for locking the file
        String email         = args[3];
        String telephone     = args[4];

        initialiseResponse();        
        
        Response resProcess = null;
        
        // validate inputs
        if("".equals(username) || username == null){
            // Username is mandatory
            resProcess = new Response();
            resProcess.setPassed(false);
            resProcess.setMessage(ProtocolConstants.MESSAGE_LOCK_USERNAME_MANDATORY);
            return resProcess;
        }        
        
        // SEND COMMAND AND PROCESS 
        Command cmd = buildCommand(sessionToken, filename, username, email, telephone);

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
            
        resProcess.setObject((String)actionResponseDoc.asXML());
        return resProcess;         
    }    
    
    /**
     * Analyses the response from Browser, and populates response that will
     * be sent to the client based on that.  
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
    public Command buildCommand(String sessionToken, String basicModuleName, String username, String email, String telephone){
        Command cmd = new Command(ProtocolConstants.COMMAND_LOCK_FILE);
        cmd.setBody(buildCommandBody(sessionToken, basicModuleName, username, email, telephone));
        return cmd;
    }
     
    /**
     * @param tokenString
     * @param prognameString
     * @return
     */
    public String buildCommandBody(String tokenString, String prognameString, String username, String email, String telephone){
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev  = buildCommonCommandBody(dom, tokenString);

        dev.addElement("action").addText("CREATE.LOCK");
        dev.addElement("progname").addText(prognameString);
        dev.addElement("lusername").addText(username);
        dev.addElement("email").addText(email);
        dev.addElement("contactno").addText(telephone);
        
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

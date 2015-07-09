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
 * Saves contents of basicFile in server. It'll overwrite contents if
 * basicFile already exits.
 */
public class ActionSaveFile extends AbstractAction {
        
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args){
        String sessionToken    = args[0];
        String basicFilenameNoPrefix = args[1];        
        String fileContents    = args[2]; // contents not yet encoded in base64 or url
        String directory       = args[3]; // remote server directory where file will be saved 
        String localUsername   = args[4]; // local username, used for locking the file

        initialiseResponse();
        
        Response resProcess = null;
        
        // CHECK IF IT IS LOCKED
        ActionCommon ac = new ActionCommon();
        resProcess = ac.checkLocked(sessionToken, basicFilenameNoPrefix);
        if(!resProcess.getPassed()){
            // Check_lock failed, i.e. either it was locked, or the comms failed while performing the check, 
            // either way return the response
            return resProcess;
        }                  
        
        // SEND COMMANDS AND PROCESS THEM
        Command cmd = buildCommand(sessionToken, basicFilenameNoPrefix, fileContents, directory, localUsername);
    
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
        
        if (ProtocolConstants.COMMAND_SAVE_FILE.equals(cmd.getCommandName()) && inResponse.getPassed()){

            // CHECK FOR ERRORS IN THE RESONSE MESSAGE
            String error = XmlUtil.getText(responseXml, "//tdevstudio/error");
            if(error != null){
                // T24 Server couldn't process the command
                String errorMsg = XmlUtil.getText(responseXml, "//tdevstudio/error");
                outResponse.setPassed(false);
                outResponse.setRespMessage(errorMsg);
                root.addElement("error").addText(errorMsg);
                
            } else {  
                // No error found => the command could be processed OK.
                String saved = XmlUtil.getSafeText(responseXml, "//tdevstudio/saved");
                String noSrcCompile = XmlUtil.getSafeText(responseXml, "//tdevstudio/noSrcCompile");
                root.addElement("saved").addText(saved);
                root.addElement("noSrcCompile").addText(noSrcCompile);
                outResponse.setPassed(true);
            }         } 
        return outResponse;
    }   
    
    /**
    * @param sessionToken - security session provided by sessionManager
    * @param progname - e.g. ACCT.STMT.DATE
    * @param fileContents - String holding the basic module contents not yet encoded
    * @param directory - remote server directory where file will be saved. e.g. GLOBUS.BP
    * @param localUsername - user using the local machine
    */
    public Command buildCommand(String sessionToken, String basicModuleName, String fileContents, 
                                String directory, String localUsername){
        Command cmd = null;
        ProtocolUtil pu = new ProtocolUtil();
        // Encode the BASIC file contents, ready to be sent over HTTP
        String fileEncoded = pu.encodeComplete(fileContents);
        cmd = new Command(ProtocolConstants.COMMAND_SAVE_FILE);
        cmd.setBody(buildCommandBody(sessionToken, basicModuleName, fileEncoded, directory, localUsername));
        return cmd;
    }

    /**
     * @param tokenString
     * @param prognameString
     * @param fileEcoded - file contents to be saved, already coded in base64 and url
     * @param directory - remote server directory where file will be saved. e.g. GLOBUS.BP
     * @param localUsername - username used to locking the file
     * @return
     */
    public String buildCommandBody(String tokenString, String prognameString, String fileEncoded, 
                                     String directory, String localUsername){
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev  = buildCommonCommandBody(dom, tokenString);
        dev.addElement("useBase64").addText("true");
        dev.addElement("action").addText("save");
        dev.addElement("prog").addText(prognameString);
        dev.addElement("bp").addText(directory);
        dev.addElement("code").addText(fileEncoded);
        dev.addElement("lusername").addText(localUsername);
        
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

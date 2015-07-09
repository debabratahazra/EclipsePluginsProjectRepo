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
 * Returns the contents of a program stored in the server. It doesn't checks for locks
 * or locks any program. 
 */
public class ActionRetrieveReadOnlyFile extends AbstractAction {
    
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args) {
        String sessionToken = args[0];
        String basicModuleName = args[1];
        String serverDirectory = args[2]; // remote server directory where
                                            // file will be saved
        String localUsername = args[3]; // local username
        initialiseResponse();
        Response resProcess = null;
        
        // RETRIEVE THE PROGRAM
        Command cmd = buildCommand(sessionToken, basicModuleName, serverDirectory, localUsername);
        
        // Send Command to Browser via HTTP
        resProcess = httpMgr.sendCommand(cmd);
        debugPrintResult(cmd, resProcess);
        if (!resProcess.getPassed()) {
            // HTTP connection failed somewhere. Don't process any further.
            return resProcess;
        }
        
        // Process response
        resProcess = processResponse(cmd, resProcess, args);
        if (!resProcess.getPassed()) {
            // T24Server failed processing the command. Don't process any
            // further.
            return resProcess;
        }
        
        resProcess.setObject((String) actionResponseDoc.asXML());
        return resProcess;
    }        
    
    
    /**
     * Analyses the response from Browser, and populates response that will be
     * sent to the client based on that.
     */
    public Response processResponse(Command cmd, Response inResponse, String[] args) {
        String basicModuleName = args[0];
        
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
        
        if(ProtocolConstants.COMMAND_RETRIEVE_READ_ONLY_FILE.equals(cmd.getCommandName()) && inResponse.getPassed()){

            // Let's check whether there were errors
            String error = XmlUtil.getText(responseXml, "//tdevstudio/error");
            if(error == null){
                // NO ERROR FOUND => the command was successfully processed
                ProtocolUtil pu = new ProtocolUtil();
                String fileBase64 = XmlUtil.getText(responseXml, "//code");

                // DECODE FILE. now we have the original source code of the BASIC module. 
                String contentsDecoded= pu.decodeComplete(fileBase64);
                
                root.addElement("contents").addText(contentsDecoded);
                root.addElement("filename").addText(basicModuleName);
                
                outResponse.setPassed(true);
                
            } else {
                // T24 Server couldn't process the command
                String errorMsg = XmlUtil.getText(responseXml, "//tdevstudio/error");
                outResponse.setPassed(false);
                outResponse.setRespMessage(errorMsg);
                root.addElement("error").addText(errorMsg);
            }
        }
        
        return outResponse;
    }
    
    /**
     * @param sessionToken - security session provided by sessionManager
     * @param progname - e.g. ACCT.STMT.DATE
     * @param fileContents - String holding the basic module contents not yet encoded
     * @param directory - remote server directory where file will be saved. e.g. GLOBUS.BP
     * @param localUsername - user using the local machine
     */
     public Command buildCommand(String sessionToken, String basicModuleName, 
                                 String directory, String localUsername){
         Command cmd = null;
         cmd = new Command(ProtocolConstants.COMMAND_RETRIEVE_READ_ONLY_FILE);
         cmd.setBody(buildCommandBody(sessionToken, basicModuleName, directory));                 
         return cmd;
     }    
    
    /**
     */
    public String buildCommandBody(String tokenString, String prognameString, String serverDirectory){
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev  = buildCommonCommandBody(dom, tokenString);
        dev.addElement("useBase64").addText("true");
        dev.addElement("action").addText("open");
        dev.addElement("prog").addText(prognameString);
        dev.addElement("bp").addText(serverDirectory);
        
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

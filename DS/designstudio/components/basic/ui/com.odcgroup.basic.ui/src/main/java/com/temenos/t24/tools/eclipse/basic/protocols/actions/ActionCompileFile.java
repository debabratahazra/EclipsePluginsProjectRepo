package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eclipse.jface.preference.IPreferenceStore;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Compiles a file in the server.
 */
public class ActionCompileFile extends AbstractAction {
    
    /** The store is injected */
    private IPreferenceStore store;
    
    public static final String TEXT_UNABLE_TO_COMPILE_PATTERN = "Unable to compile";
    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args) {
        String sessionToken = args[0];
        String filename = args[1];
        String fileContents = args[2]; // contents not yet encoded in base64 or
                                        // url
        String directory = args[3]; // remote server directory where file will
                                    // be saved
        String localUsername = args[4]; // local username, used for locking the
                                        // file
        String noSrcCompile = args[5]; // true = don't embed src code; false
                                        // (or "") embed src code
        initialiseResponse();
        Response resProcess = null;
        
        // SEND COMMAND AND PROCESS IT
        Command cmd = buildCommand(sessionToken, filename, fileContents, directory, localUsername, noSrcCompile);
        
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
        
        // get the sessionToken for the next command (if any)
        sessionToken = resProcess.getSessionToken();
        resProcess.setObject((String) actionResponseDoc.asXML());
        return resProcess;
    }        

    /**
     * Analyses the response from Browser, and populates response that will be
     * sent to the client based on that.
     */
    public Response processResponse(Command cmd, Response inResponse, String[] args) {
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

        if(ProtocolConstants.COMMAND_COMPILE_FILE.equals(cmd.getCommandName()) && inResponse.getPassed()){

            // Let's check whether there were errors
            String error = XmlUtil.getText(responseXml, "//tdevstudio/error");
            String compileout = XmlUtil.getText(responseXml, "//tdevstudio/compileout");
            
            if(error != null){
                // T24 Server couldn't process the command
                String errorMsg = XmlUtil.getText(responseXml, "//tdevstudio/error");
                outResponse.setPassed(false);
                outResponse.setRespMessage(errorMsg);
                root.addElement("error").addText(errorMsg);                
                
            } else if("TRUE".equals(XmlUtil.getSafeText(responseXml, "//tdevstudio/locked"))){
                // THE MESSAGE HAD BEEN LOCKED PREVIOUSLY
                // Get details of user locking the file
                String username  = XmlUtil.getSafeText(responseXml, "//tdevstudio/lusername");
                String email     = XmlUtil.getSafeText(responseXml, "//tdevstudio/email");
                String contactno = XmlUtil.getSafeText(responseXml, "//tdevstudio/contactno");
                String lockdate  = XmlUtil.getSafeText(responseXml, "//tdevstudio/lockdate");
                
                outResponse.setPassed(false);
                outResponse.setRespMessage(ProtocolConstants.MESSAGE_FILE_LOCKED+"\nLock details: \n"+
                                        "Username: "+username+" - Email: "+email+" - Contact No: "+contactno+" - "+
                                        "Locked since: "+lockdate);
                outResponse.setObject("");
                
            } else if (compileout!=null && compileout.indexOf(TEXT_UNABLE_TO_COMPILE_PATTERN)>=0) {
                // T24 Server couldn't compile the source file.
                ProtocolUtil pu = new ProtocolUtil();
                String errorMsg = pu.transformFMintoNewLines(compileout);
                outResponse.setPassed(false);
                outResponse.setRespMessage(errorMsg);
                root.addElement("error").addText(errorMsg);
                
            } else { 
                
                ProtocolUtil pu = new ProtocolUtil();                
                // No error found => the command was processed OK
                root.addElement("saved").addText(XmlUtil.getSafeText(responseXml, "//tdevstudio/saved"));
                
                String compileResultAdapted = pu.transformFMintoNewLines(compileout);
                
                root.addElement("compileout").addText(compileResultAdapted);
                root.addElement("cmd").addText(XmlUtil.getText(responseXml, "//tdevstudio/cmd"));
                outResponse.setPassed(true);

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
     * @param noSrcCompile - true = don't embed src code; false (or "") embed src code 
     */
     public Command buildCommand(String sessionToken, String basicModuleName, String fileContents, 
                                 String directory, String localUsername, String noSrcCompile){
         
         Command cmd = new Command(ProtocolConstants.COMMAND_COMPILE_FILE);

         cmd.setBody(buildCommandBody(sessionToken, basicModuleName, fileContents, directory, localUsername, noSrcCompile));                
         return cmd;
     }    

    /**
     * @param tokenString
     * @param prognameString
     * @param fileContents - String holding the basic module contents not yet encoded
     * @param directory - remote server directory where file will be saved. e.g. GLOBUS.BP
     * @param localUsername - username used to locking the file
     * @param noSrcCompile - 
     * @return
     */
    public String buildCommandBody(String tokenString, String prognameString, String fileContents, 
                                     String directory, String localUsername, String noSrcCompile){
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev  = buildCommonCommandBody(dom, tokenString);
        dev.addElement("useBase64").addText("true");
        dev.addElement("action").addText("compile");
        dev.addElement("prog").addText(prognameString);
        dev.addElement("bp").addText(directory);
        
        if (!"true".equals(noSrcCompile)) {
            // Encode the BASIC file contents, ready to be sent over HTTP
            ProtocolUtil pu = new ProtocolUtil();
            String fileEncoded = pu.encodeComplete(fileContents);
            dev.addElement("code").addText(fileEncoded);
        }
        // Check if options are required
        String compileWithDebugProperty = store.getString(PluginConstants.T24_COMPILE_WITH_DEBUG);        
        boolean compileWithDebug  = Boolean.parseBoolean(compileWithDebugProperty);
        if(compileWithDebug){
            dev.addElement("options").addText("D");
        }
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
    
    public void setStore(IPreferenceStore store) {
        this.store = store;
    }
    
}

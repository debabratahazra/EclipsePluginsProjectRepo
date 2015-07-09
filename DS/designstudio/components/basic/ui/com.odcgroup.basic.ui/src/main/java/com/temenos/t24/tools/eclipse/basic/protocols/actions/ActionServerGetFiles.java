package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.temenos.t24.tools.eclipse.basic.protocols.Command;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Get list of basicFilenames (i.e. only names, not contents), from the
 * server. The search of files follows an operation (e.g. LK - like) and a pattern (T...)
 */
public class ActionServerGetFiles extends AbstractAction {

    private static int MAX_DISPLAY_ITEMS = 500;

    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args) {
        String sessionToken = args[0];
        String directory = args[1]; // e.g. GLOBUS.BP
        String operation = args[2]; // e.g. LK (like), GT (greater than), LT, etc 
        String pattern = args[3]; // e.g. ACC... (note "..." represents any char string)
        initialiseResponse();
        Response res = new Response();
        /** Validate inputs */
        if ("".equals(pattern) || pattern == null) {
            /** If not pattern set by user, then internally set an unfeasable pattern */
            operation = "LK";
            pattern = "ZZZYYYZZ###";
        }
        /** There is a BUG in the server code; UL operation needs to be converted into UNLIKE */
        if ("UL".equals(operation)) {
            operation = "UNLIKE";
        }
        Command cmd = new Command(ProtocolConstants.COMMAND_GET_SERVER_FILES);
        cmd.setBody(buildCommandBody(sessionToken, directory, operation, pattern));
        // Send Command over HTTP
        res = httpMgr.sendCommand(cmd);
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
        // Create a new response object, copy of the passed one.
        Response outResponse = new Response(inResponse);
        Element root = actionResponseDoc.getRootElement();
        // Get the original response from remote server (i.e. Browser)
        String responseXml = (String) inResponse.getObject();
        responseXml = responseXml.replaceAll("&", "&amp;");
        outResponse = checkBadResponse(responseXml);
        if (!outResponse.getPassed()) {
            root.addElement("error").addText(outResponse.getRespMessage());
            // don't proceed processing.
            return outResponse;
        }
        // Extract the token number, which is set by the server
        String sessionTokenFromServer = XmlUtil.getSafeText(responseXml, "//token");
        outResponse.setSessionToken(sessionTokenFromServer);
        if (!"".equals(XmlUtil.getSafeText(responseXml, "//error")) || (responseXml == null)) {
            // INTERNAL ERROR WHILE RETRIEVING THE FILES AND DIRS
            outResponse.setPassed(false);
            outResponse.setRespMessage(ProtocolConstants.MESSAGE_FAIL_GET_CHANNELS + "\n"
                    + XmlUtil.getSafeText(responseXml, "//error"));
            outResponse.setObject(null);
        } else {
            // SUCCESS RETRIEVING THE FILES AND DIRS  
            outResponse.setPassed(true);
            // Store the files and directories in a TreeMap (which is a sorted Map)
            // The key will be the file/dir name (which is unique)
            // The value will be either "DIRECTORY" or "FILE"
            ArrayList<String> files = new ArrayList<String>();
            try {
                // Build a dom doc and iterate through the nodes
                Document doc = DocumentHelper.parseText(responseXml);
                List<Element> itemList = doc.selectNodes("//item");
                int count = 0;
                boolean limitReached = false;
                for (Element e : itemList) {
                    if (++count > MAX_DISPLAY_ITEMS) {
                        limitReached = true;
                        break;
                    }
                    String basicFilename = e.getText();
                    /** do not add the binaries, i.e. those starting with $ */
                    if (basicFilename.charAt(0) != '$') {
                        files.add(e.getText());
                    }
                }
                if (limitReached) {
                    outResponse.setRespMessage("Found " + itemList.size()
                            + " items, but due to memory restrictions displayed only " + files.size());
                } else {
                    outResponse.setRespMessage("");
                }
            } catch (DocumentException e) {
                // ERROR while creating the dom doc
                outResponse.setPassed(false);
                outResponse.setRespMessage(ProtocolConstants.MESSAGE_FAIL_GET_FILES
                        + ". Problems parsing the xml response from Browser.");
                outResponse.setObject(null);
            }
            Collections.sort(files);
            outResponse.setObject(files);
        }
        return outResponse;
    }

    /**
     * @param tokenString
     * @param directory - remote server directory where file will be saved. e.g. GLOBUS.BP
     * @return String with the xml command.
     */
    public String buildCommandBody(String tokenString, String directory, String operation, String pattern) {
        String header = ActionCommon.getCommandHeader();
        Document dom = DocumentHelper.createDocument();
        Element dev = buildCommonCommandBody(dom, tokenString);
        dev.addElement("action").addText("LIST.BP");
        dev.addElement("bp").addText(directory);
        if (!"".equals(operation) && !"".equals(pattern)) {
            Element crit = dev.addElement("crit");
            crit.addElement("op").addText(operation);
            crit.addElement("val").addText(pattern);
        }
        return header + dom.asXML();
    }

    /**
     */
    public Element buildCommonCommandBody(Document dom, String tokenString) {
        // Build a DOM doc 
        Element root = dom.addElement("ofsSessionRequest").addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.addElement("requestType").addText("UTILITY.ROUTINE");
        root.addElement("token").addText(tokenString);
        root.addElement("detailtag");
        Element reqArgs = root.addElement("requestArguments");
        reqArgs.addElement("routineName").addText("EB.DEV.STUDIO");
        Element routineArgs = reqArgs.addElement("routineArgs");
        Element dev = routineArgs.addElement("dev");
        reqArgs.addElement("companyId");
        reqArgs.addElement("windowName");
        reqArgs.addElement("compScreen");
        reqArgs.addElement("unlock");
        reqArgs.addElement("closing");
        return dev;
    }
}

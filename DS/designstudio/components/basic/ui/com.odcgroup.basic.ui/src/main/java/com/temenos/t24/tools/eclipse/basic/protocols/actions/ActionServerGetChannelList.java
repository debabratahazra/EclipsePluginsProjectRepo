package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import java.util.ArrayList;
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
 * Gets list of channels available from browser.
 * A channel in this context is a connexion to a T24 server. These
 * connections are defined in channels.xml
 */
public class ActionServerGetChannelList extends AbstractAction {

    /**
     * In combination with a HTTP manager object sends XML commands and retrieve responses
     * to/from Browser over HTTP.
     * @return Response instance with all the relevant info regarding; data returned
     * passed/fail, additional explanatory messages, ... 
     */
    public Response processAction(String[] args){
        Response res = new Response();
        
        Command cmd = new Command(ProtocolConstants.COMMAND_GET_CHANNELS);
        cmd.setBody(buildCommandBody());
        
        // Send Command over HTTP
        res = httpMgr.sendCommand(cmd);
        debugPrintResult(cmd, res);
        
        if(!res.getPassed()){
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
        // Note: this command is executed by the Browser. It does not go down
        // to the T24Server.
        
        // Get response body from Browser
        String responseXml = (String)inResponse.getObject();
        
        // Check if there are any error messages
        String msg = XmlUtil.getSafeText(responseXml, "//messages/msg");
        if("SECURITY.VIOLATION".equals(msg)){
            inResponse.setPassed(false);
            inResponse.setRespMessage(msg + " " + ProtocolConstants.MESSAGE_BROWSER_CANT_EXECUTE_COMMAND+" "+
                               ProtocolConstants.COMMAND_GET_CHANNELS);
           
        } else if (!"".equals(XmlUtil.getSafeText(responseXml, "//error")) || (responseXml==null) || "".equals(responseXml)) {
            // INTERNAL ERROR WHILE RETRIEVING THE LIST OF CHANNELS
            inResponse.setPassed(false);
            inResponse.setRespMessage(ProtocolConstants.MESSAGE_FAIL_GET_CHANNELS+"\n"+XmlUtil.getSafeText(responseXml, "//error"));
            inResponse.setObject(null);

        } else {            
            // SUCCESS RETRIEVING THE LIST OF CHANNELS.  
            inResponse.setPassed(true);

            // create an array with all the channels. 
            List<String>chArrayList = new ArrayList<String>();
            // it contains an item named DEFAULT.
            chArrayList.add(ProtocolConstants.DEFAULT_CHANNEL_STRING);
            try{
                // Browsers Response will be like: <channels><channel>CHANN1</channel><channel> ...
                // Retrieve the channels by iterating through them in the Browser's response.
                // First build a DOM document with the response.                
                Document doc = DocumentHelper.parseText(responseXml);
                List<Element> chList = doc.selectNodes("//channel");
                for (Element e : chList) {
                    chArrayList.add(e.getText());
                }
                
                // Transform the ArrayList into a String[], this is the format 
                // expected by the upper layers
                int arraySize = chArrayList.size();
                String[] channels = new String[arraySize];
                chArrayList.toArray(channels);
                inResponse.setObject(channels);
                
            } catch(DocumentException e){
                // ERROR WHILE BUILDING THE DOCUMENT - CHANNEL LIST RETRIEVAL PROTOCOL FAILED 
                inResponse.setPassed(false);
                inResponse.setRespMessage(ProtocolConstants.MESSAGE_FAIL_GET_CHANNELS+"\n"+XmlUtil.getSafeText(responseXml, "//error"));
                inResponse.setObject(null);
            }
        } 
        
        return inResponse;
    }
    
    
    /**
     * Builds the XML command that will be sent down to Browser. 
     * @return String containing the XML.
     */
    public String buildCommandBody(){
        String command = "command=smartclient&action=" + ProtocolConstants.COMMAND_GET_CHANNELS;
        return command;        
    }

}

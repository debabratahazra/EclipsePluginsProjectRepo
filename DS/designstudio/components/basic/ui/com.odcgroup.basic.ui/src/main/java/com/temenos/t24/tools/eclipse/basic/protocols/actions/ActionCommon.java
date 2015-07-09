package com.temenos.t24.tools.eclipse.basic.protocols.actions;

import java.util.Arrays;

import org.eclipse.jface.preference.IPreferenceStore;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolConstants;
import com.temenos.t24.tools.eclipse.basic.protocols.ProtocolUtil;
import com.temenos.t24.tools.eclipse.basic.protocols.Response;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * Common utility methods in the actions package.
 *
 */
public class ActionCommon {
    private static IPreferenceStore store = (IPreferenceStore)T24BasicPlugin.getBean("preferenceStore");
    private String localUsername  = "";
    private String localEmail     = "";
    private String localTelephone = "";
    
    /**
     * 
     * @param sessionToken
     * @return
     */
    public Response sendEmptyAction(String sessionToken){
        String[] params = {sessionToken};
        IAction action = (IAction)T24BasicPlugin.getBean("actionEmptyAction");
        Response resProcess = action.processAction(params);
        return resProcess;
    }
    
    /**
     * Checks whether a program is locked in the Server, by sending the
     * CHECK_LOCK command down to the Server.
     * If it is locked, then the Response will contain all the relevant information
     * (lusername, email, contactno, lockdate)   
     * @param sessionToken
     * @param basicFilenameNoPrefix
     * @param localUsername
     * @return
     */
    public Response checkLocked(String sessionToken, String basicFilenameNoPrefix){
        String[] params = {sessionToken, basicFilenameNoPrefix};
        IAction action = (IAction)T24BasicPlugin.getBean("actionCheckLock");
        Response resProcess = action.processAction(params);

        if(resProcess.getPassed()){
            // The check lock command was sent ok
            String responseXmlContents = (String) resProcess.getObject();
            String locked = XmlUtil.getText(responseXmlContents, "//locked");
            if("TRUE".equals(locked)){
                // The file is locked. Check if it is locked by local user. If it is, then it is OK.
                /** Get local data */
                reloadLocalUserDetails();
                
                // Get locking details
                String lockUsername  = XmlUtil.getSafeText(responseXmlContents, "//lusername");
                String lockEmail     = XmlUtil.getSafeText(responseXmlContents, "//email");
                String lockContactno = XmlUtil.getSafeText(responseXmlContents, "//contactno");
                String lockdate  = XmlUtil.getSafeText(responseXmlContents, "//lockdate");

                if(lockUsername.equals(localUsername)){
                    // it is locked by the local user, so it is OK. Don't do anything.
                    
                } else {
                    // It has been locked by a different user.
                    resProcess.setPassed(false);
                
                    resProcess.setRespMessage(ProtocolConstants.MESSAGE_FILE_LOCKED+"\nLock details: \n"+
                        "Username: "+lockUsername+" - Email: "+lockEmail+" - Contact No: "+lockContactno+" - "+
                        "Locked since: "+lockdate);
                    
                }
            } 
        }
        return resProcess;

    }
    
    /**
     * Utility method that expresses into a boolean whether a file is locked or not.
     * @param Response - response instance obtained after invoking processAction
     * of ActionCheckLock
     * @return true/false
     */
    public boolean isLocked(Response response){
        String responseXmlContents = (String) response.getObject();
        String locked = XmlUtil.getSafeText(responseXmlContents, "//locked");
        if("TRUE".equals(locked)){
            return true;
        } else {
            return false;
        }
    }    
    
    /**
     * Sends lock file command to server.
     * @param sessionToken
     * @param basicModuleName
     * @return
     */
    public Response lockFile(String sessionToken, String basicModuleName){
        /** Username, email, telephone */
        reloadLocalUserDetails();

        String[] params = {sessionToken, basicModuleName, localUsername, localEmail, localTelephone};
        IAction action = (IAction)T24BasicPlugin.getBean("actionLockFile");
        Response resProcess = action.processAction(params);
        return resProcess;

    }
    
    /**
     * Returns the command header as a string. Its contents vary depending
     * on whether a channel has been previuosly selected or not.
     * @return
     */
    public static String getCommandHeader(){
        String header = "";
        String curChannel = ""; 
        ProtocolUtil pu = new ProtocolUtil();
        curChannel = pu.getCurrentChannel();
        
        if("".equals(curChannel) || curChannel==null || 
           ProtocolConstants.DEFAULT_CHANNEL_STRING.equals(curChannel.toUpperCase())){
            // The channel is either the default one, or it is empty (not yet selected)
            header = "command=smartclient&content=";
        } else {
            // The current channel is neither the default channel nor null (or empty)
            header = "channel="+curChannel+"&command=smartclient&content=";

        } 
        return header;
    }
    
    
    /**
     * Checks whether the passed msg equals one of the known T24 error
     * messages (e.g. SECURITY.VIOLATION, OF.RTN.SECURITY.VIOLATION)
     * @param msg
     * @return the error message if found, or "" if not found.
     */
    public static String getMsgError(String msg){
        if(msg==null){
            return "";
        }
        String[] errorMsg = {"SECURITY.VIOLATION","OF.RTN.SECURITY.VIOLATION"};
        Arrays.sort(errorMsg);
        int idxFound = Arrays.binarySearch(errorMsg, msg);
        if(idxFound>=0)
            return errorMsg[idxFound];
        else
            return "";
    }
    
    
    /**
     * This method is intended for checking the value of the raw xml response from
     * Browser, i.e. as it is!
     * @param responseXml
     * @return true/false
     */
    public static boolean isRawResponseOK(String responseXml){
        if(responseXml==null || 
           "null".equals(responseXml.trim()) || 
           "OFSERROR_TIMEOUT".equals(responseXml) || 
           "OFSERROR_PROCESS".equals(responseXml) ){
            
            return false;
            
        } else {
            return true;
        }
    }
    
    /** Loads values from plug-in store */
    private void reloadLocalUserDetails() {
        localUsername  = store.getString(PluginConstants.T24_LOCAL_USERNAME);
        localEmail     = store.getString(PluginConstants.T24_LOCAL_EMAIL);
        localTelephone = store.getString(PluginConstants.T24_LOCAL_TELEPHONE);
    }
    
}

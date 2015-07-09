package com.temenos.t24.tools.eclipse.basic.protocols;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.springframework.context.ApplicationContext;

import com.temenos.t24.tools.eclipse.basic.PluginConstants;
import com.temenos.t24.tools.eclipse.basic.T24BasicPlugin;
import com.temenos.t24.tools.eclipse.basic.protocols.actions.ActionCommon;
import com.temenos.t24.tools.eclipse.basic.protocols.actions.IAction;
import com.temenos.t24.tools.eclipse.basic.utils.XmlUtil;

/**
 * The protocol package is layered in a stack as follows: 1.- GUI => interfaces
 * with user. The GUI comprises a set of Dialogs and Menus. 2.-
 * RemoteSessionManager => holds sesssion keys. It's the main interface 3.-
 * Actions => all the action bussiness logic are in here 4.- HttpProtocolManager =>
 * responsible for handling http comms.
 */
public class RemoteSessionManager {

    private final static RemoteSessionManager _SESSION_MANAGER = new RemoteSessionManager();
    private String sessionToken = "";
    private String username = "";
    private static boolean userIsSignedOn = false;
    /** Map that holds which processes are running */
    private static Map<String, IAction> processes = new HashMap<String, IAction>();
    private static ApplicationContext springContext = null;

    private RemoteSessionManager() {
        springContext = T24BasicPlugin.getSpringApplicationContext();
    }

    public static RemoteSessionManager getInstance() {
        springContext = T24BasicPlugin.getSpringApplicationContext();
        return _SESSION_MANAGER;
    }

    /**
     * Signing on a user into a server, is the first pre-requisite before any
     * command (e.g. save, get, compile, etc) can be executed. Before signing on
     * a channel must have been selected. This IDE connects to Browser
     * application (deployed on a Web Werver, which can be on the same machine
     * or a remote machine). Browser can have multiple channels configured (see
     * its channels.xml configuration file) A channel, in this context,
     * represents a T24 server. The user is allowed to select the channel he/she
     * wants to work with.
     * 
     * @param channel - one of the channels defined in the Browser configuration
     *            file channels.xml
     */
    public Response signOn(String username, String password, String channel) {
        Response response;
        IAction action = (IAction) springContext.getBean("actionSignOn");
        addToProcessList(ProtocolConstants.ID_SIGN_ON, action);
        // Expected parameter, however it is not used in this type of signing-on
        String secondPswd = "";
        String[] params = new String[] { ProtocolConstants.ID_SIGN_ON, username, password, secondPswd, channel };
        response = getTimedResponse(action, params);
        if (response.getPassed()) {
            // The user has successfully signed on, so accept this user as
            // the valid
            // one for current session
            RemoteSessionManager.userIsSignedOn = true;
            this.username = username;
            // Retrieve the session token from the response, and store it.
            // It'll be used in further communications with the server.
            String resXml = (String) response.getObject();
            String sessionToken = XmlUtil.getText(resXml, "//token");
            setSessionToken(sessionToken);
        } else {
            RemoteSessionManager.userIsSignedOn = false;
        }
        removeProcess(ProtocolConstants.ID_SIGN_ON);
        return response;
    }

    /**
     * New User Created in the remote server: The user will attempt to sign on
     * as normal; the server will respond with a repeat password procedure.
     * 
     * @param channel - one of the channels defined in the Browser configuration
     *            file channels.xml
     */
    public Response repeatPasswordNewUser(String username, String firstPswd, String secondPswd, String channel) {
        return repeatPassword(ProtocolConstants.ID_PASSWORD_NEWUSER, username, firstPswd, secondPswd, channel);
    }

    /**
     * Password has expired. The user will attempt to sign on as normal; the
     * server will respond with a password expired procedure. The user will then
     * require to enter a new password repeated.
     * 
     * @param channel - one of the channels defined in the Browser configuration
     *            file channels.xml
     */
    public Response repeatPasswordExpired(String username, String firstPswd, String secondPswd, String channel) {
        return repeatPassword(ProtocolConstants.ID_PASSWORD_EXPIRED, username, firstPswd, secondPswd, channel);
    }

    /**
     * Some scenarios, or use cases, require the IDE user to repeat a password.
     * E.g. New User Created in the remote server: The user will attempt to sign
     * on as normal; the server will respond with a repeat password procedure.
     * E.g. A password has expired. The user will attempt to sign on as normal;
     * the server will respond with a password expired procedure. The user will
     * then require to enter a new password repeated.
     * 
     * @param channel - one of the channels defined in the Browser configuration
     *            file channels.xml
     */
    private Response repeatPassword(String actionId, String username, String firstPswd, String secondPswd, String channel) {
        Response response;
        IAction action = (IAction) springContext.getBean("actionSignOn");
        addToProcessList(actionId, action);
        String[] params = new String[] { actionId, username, firstPswd, secondPswd, channel };
        response = getTimedResponse(action, params);
        if (response.getPassed()) {
            // The user has successfully signed on, so accept this user as
            // the valid
            // one for current session
            RemoteSessionManager.userIsSignedOn = true;
            this.username = username;
            // Retrieve the session token from the response, and store it.
            // It'll be used in further communications with the server.
            String resXml = (String) response.getObject();
            String sessionToken = XmlUtil.getText(resXml, "//token");
            setSessionToken(sessionToken);
        } else {
            RemoteSessionManager.userIsSignedOn = false;
        }
        removeProcess(actionId);
        return response;
    }

    /**
     * Signs off user from current sesssion.
     * 
     * @return
     */
    public Response signOff() {
        Response response;
        IAction action = (IAction) springContext.getBean("actionSignOff");
        String[] params = new String[] { sessionToken, username };
        response = getTimedResponse(action, params);
        RemoteSessionManager.userIsSignedOn = false;
        this.username = "";
        return response;
    }

    /**
     * @param basicFilenameNoPrefix - e.g. ACCOUNT.MODULE
     * @param filecontents
     * @param directory - remote server directory where file will be saved. e.g.
     *            GLOBUS.BP
     * @return Response The Object element of Response is an xml string, holding
     *         the following information: <response> <saved>TRUE</saved>
     *         </response> or <response> <error>ERROR MESSAGE</error>
     *         </response>
     */
    public Response saveFile(String basicFilenameNoPrefix, String filecontents, String directory) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionSaveFile");
            addToProcessList(ProtocolConstants.ID_SAVE_FILE, action);
            String[] params = { sessionToken, basicFilenameNoPrefix, filecontents, directory, getLocalUsername() };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken())) {
                sessionToken = response.getSessionToken();
            }
            removeProcess(ProtocolConstants.ID_SAVE_FILE);
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * Returns the contents of a program stored in the server. It checks first
     * whether the program has been locked by another user. If it is not locked,
     * it'll be retrieved and a lock will be set.
     * 
     * @param basicFilenameNoPrefix (e.g. "ACCT.ENTRY.STMT")
     * @param directory - remote server directory where file will be saved. e.g.
     *            GLOBUS.BP
     * @return Response: The Object element of Response is an xml string,
     *         holding the following information: <response> <contents>CONTENTS
     *         OF THE BASIC PROGRAM</contents> </response>
     */
    public Response retrieveFile(String basicFilenameNoPrefix, String directory) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionRetrieveFile");
            addToProcessList(ProtocolConstants.ID_GET_FILE, action);
            String[] params = { sessionToken, basicFilenameNoPrefix, directory, getLocalUsername() };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken())) {
                sessionToken = response.getSessionToken();
            }
            removeProcess(ProtocolConstants.ID_GET_FILE);
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * Returns the contents of a program stored in the server. It neither checks
     * for locks, nor it locks any program.
     * 
     * @param basicFilenameNoPrefix (e.g. "ACCT.ENTRY.STMT")
     * @param directory - remote server directory where file will be saved. e.g.
     *            GLOBUS.BP
     * @return Response: The Object element of Response is an xml string,
     *         holding the following information: <response> <contents>CONTENTS
     *         OF THE BASIC PROGRAM</contents> </response>
     */
    public Response retrieveReadOnlyFile(String basicFilenameNoPrefix, String directory) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionRetrieveReadOnlyFile");
            addToProcessList(ProtocolConstants.ID_GET_READONLY_FILE, action);
            String[] params = { sessionToken, basicFilenameNoPrefix, directory, getLocalUsername() };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken())) {
                sessionToken = response.getSessionToken();
            }
            removeProcess(ProtocolConstants.ID_GET_READONLY_FILE);
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * @param basicFilenameNoPrefix - e.g. ACCT.STMT.DATE
     * @param filecontents - undecoded ASCII file contents of the BASIC module
     *            to be compiled
     * @param directory - remote server directory where file will be saved. e.g.
     *            GLOBUS.BP
     * @return Response
     */
    public Response compileFile(String basicFilenameNoPrefix, String filecontents, String directory) {
        Response response = null;
        // Send an empty command, this just replicates what ToolBox does.
        ActionCommon ac = new ActionCommon();
        response = ac.sendEmptyAction(sessionToken);
        if (!response.getPassed()) {
            return response;
        }
        String noSrcCompile = XmlUtil.getSafeText((String) response.getObject(), "//noSrcCompile");
        // Finally send the compile command.
        response = this.compileFileCommand(basicFilenameNoPrefix, filecontents, directory, noSrcCompile);
        return response;
    }

    /**
     * @param basicFilenameNoPrefix - e.g. ACCT.STMT.DATE
     * @param filecontents - undecoded ASCII file contents of the BASIC module
     *            to be compiled
     * @param directory - remote server directory where file will be saved. e.g.
     *            GLOBUS.BP
     * @return Response
     */
    private Response compileFileCommand(String basicFilenameNoPrefix, String filecontents, String directory, String noSrcCompile) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionCompileFile");
            addToProcessList(ProtocolConstants.ID_COMPILE_FILE, action);
            String[] params = { sessionToken, basicFilenameNoPrefix, filecontents, directory, getLocalUsername(), noSrcCompile };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken())) {
                sessionToken = response.getSessionToken();
            }
            removeProcess(ProtocolConstants.ID_COMPILE_FILE);
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * This method is intended to be used to cancel a current signon process.
     */
    public Response stopSignOn() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_SIGN_ON);
        return response;
    }

    public Response stopRepeatPasswordNewUser() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_PASSWORD_NEWUSER);
        return response;
    }

    public Response stopRepeatPasswordExpired() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_PASSWORD_EXPIRED);
        return response;
    }

    /**
     * This method is intended to be used to cancel a current get file process.
     */
    public Response stopGetFile() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_GET_FILE);
        return response;
    }

    public Response stopSaveFile() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_SAVE_FILE);
        return response;
    }

    /**
     * This method is intended to be used to cancel a current get server files
     * process.
     */
    public Response stopGetServerFiles() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_GET_SERVER_FILES);
        return response;
    }

    /**
     * This method is intended to be used to cancel a current compilation
     * process. Typically, the cancellation is triggered by the user, if it is
     * taking longer than expected, or nothing is returned after a period of
     * time.
     */
    public Response StopCompilation() {
        Response response;
        response = stopAndRemoveProcess(ProtocolConstants.ID_COMPILE_FILE);
        return response;
    }

    public Response stopTestExecution() {
        return stopAndRemoveProcess(ProtocolConstants.ID_EXECUTE_TEST);
    }

    /**
     * Adds the passed process (IAction) to a list of processes, used to keep
     * track of which processes are running at any time. Only one instance of
     * each processId can be in the list. If there is already one instance,
     * it'll be stopped and removed from the list before the new one is added.
     * 
     * @param processID
     * @param newProcess
     */
    private void addToProcessList(String processID, IAction newProcess) {
        // Maybe the process is already there, so it's still running.
        // Stop it first, remove it, and then add the new one.
        IAction process = (IAction) processes.get(processID);
        if (process != null) {
            process.stopProcess();
            processes.remove(processID);
        }
        // Add the new process
        processes.put(processID, (IAction) newProcess);
    }

    private Response stopAndRemoveProcess(String processID) {
        Response res;
        IAction process = (IAction) processes.remove(processID);
        if (process != null) {
            res = process.stopProcess();
        } else {
            res = new Response();
            res.setPassed(false);
            res.setRespMessage("Could not stop the process. Process not found");
        }
        return res;
    }

    private void removeProcess(String processID) {
        processes.remove(processID);
    }

    /**
     * Returns a String[] with the available channels in Browser. These channels
     * define which T24 servers are available from Browser.
     * 
     * @return Response - holds list of channels available. Channels are
     *         included in the Object variable (res.getObject()) as a String[].
     *         This String[] contains at least a value of DEFAULT, referring to
     *         the default channel in Browser.
     */
    public Response getChannelList() {
        IAction action = (IAction) springContext.getBean("actionServerGetChannelList");
        Response res = action.processAction(null);
        return res;
    }

    /**
     * @param directory - remote server directory from where to get the Files,
     *            e.g. GLOBUS.BP
     * @param operation - used to browse files (e.g. LK (like), LT (less than),
     *            ...)
     * @param pattern - the operation will apply to the search pattern (e.g.
     *            ACCT...)
     * @return - returns an ArrayList with the Files in the passed directory of
     *         the remote T24 server. The ArrayList is contained in the Object
     *         element of the response.
     */
    public Response getServerFiles(String directory, String operation, String pattern) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionServerGetFiles");
            addToProcessList(ProtocolConstants.ID_GET_SERVER_FILES, action);
            String[] params = { sessionToken, directory, operation, pattern };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken())) {
                sessionToken = response.getSessionToken();
            }
            removeProcess(ProtocolConstants.ID_GET_SERVER_FILES);
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * @param basicFilenameNoPrefix
     * @return Response
     */
    public Response checkLock(String basicFilenameNoPrefix) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionCheckLock");
            String[] params = { sessionToken, basicFilenameNoPrefix, getLocalUsername() };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken()))
                sessionToken = response.getSessionToken();
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * @param basicFilenameNoPrefix
     * @return Response
     */
    public Response unlockFile(String basicFilenameNoPrefix) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionUnlockFile");
            String[] params = { sessionToken, basicFilenameNoPrefix, getLocalUsername() };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken()))
                sessionToken = response.getSessionToken();
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * @param username - name of user for whom locks are requested.
     * @return Response
     */
    public Response getLocks(String username) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionGetLocks");
            String[] params = { sessionToken, username };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken()))
                sessionToken = response.getSessionToken();
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * @param basicFilenameNoPrefix
     * @return Response
     */
    public Response lockFile(String basicFilenameNoPrefix, String username, String email, String telephone) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionLockFile");
            String[] params = { sessionToken, basicFilenameNoPrefix, username, email, telephone };
            response = getTimedResponse(action, params);
            // Update the sessionToken if needed
            if (!"".equals(response.getSessionToken()))
                sessionToken = response.getSessionToken();
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * Executes tests by passing the requests to T24 Server through Browser.
     * 
     * @param fileName test file name
     * @param fileDirectory test file directory
     * @param fileContent content of the test file
     * @return Response returned from T24 Server
     */
    public Response executeTest(String fileName, String fileDirectory, String fileContent) {
        Response response;
        if (userIsSignedOn) {
            IAction action = (IAction) springContext.getBean("actionExecuteTest");
            String usrName = getLocalUsername();
            String[] params = { sessionToken, fileName, fileDirectory, fileContent, usrName };
            response = getTimedResponse(action, params);
            if (!"".equals(response.getSessionToken()))
                sessionToken = response.getSessionToken();
        } else {
            response = new Response();
            response.setPassed(false);
            response.setRespMessage(ProtocolConstants.MESSAGE_NOT_SIGN_ON);
            response.setObject("");
        }
        return response;
    }

    /**
     * Executes the action with the passed parameters, calculates the time it
     * took to execute and add that time in milliseconds to the response.
     * 
     * @param action
     * @param params
     * @return
     */
    public Response getTimedResponse(IAction action, String[] params) {
        long startMillis = System.currentTimeMillis();
        Response res = action.processAction(params);
        long durationMillis = System.currentTimeMillis() - startMillis;
        res.setActionTimeMillis(durationMillis);
        return res;
    }

    public void setSessionToken(String token) {
        sessionToken = token;
    }

    public void setUsername(String login) {
        username = login;
    }

    public boolean isUserSignedOn() {
        return RemoteSessionManager.userIsSignedOn;
    }

    public String getLocalUsername() {
        IPreferenceStore store = (IPreferenceStore) T24BasicPlugin.getBean("preferenceStore");
        String localUsername = store.getString(PluginConstants.T24_LOCAL_USERNAME);
        return localUsername;
    }
}

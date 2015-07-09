package com.temenos.t24.tools.eclipse.basic.protocols;


public class ProtocolConstants {
    
    /** Internal Ids*/
    public final static String ID_SIGN_ON           = "ID.SIGN.ON"; 
    public final static String ID_PASSWORD_NEWUSER  = "ID.PASSWORD.NEWUSER";
    public final static String ID_PASSWORD_EXPIRED  = "ID.PASSWORD.EXPIRED";
    public final static String ID_SIGN_OFF          = "ID.SIGN.OFF";
    public final static String ID_GET_FILE          = "ID.GET.FILE";
    public final static String ID_GET_READONLY_FILE = "ID.GET.READONLY.FILE";
    public final static String ID_SAVE_FILE         = "ID.SAVE.FILE";
    public final static String ID_UNLOCK_FILE       = "ID.UNLOCK.FILE";
    public final static String ID_COMPILE_FILE      = "ID.COMPILE.FILE";
    public final static String ID_GET_SERVER_FILES  = "ID.GET.SERVER.FILES";
    public final static String ID_EXECUTE_TEST      = "ID.EXECUTE.TEST";
    
    /** T24 protocol command names - don't change! */
    public final static String COMMAND_EMPTY_ACTION  = "COMMAND.EMPTY.ACTION";
    public final static String COMMAND_SIGN_ON       = "COMMAND.SIGN.ON";
    public final static String COMMAND_SIGN_OFF      = "COMMAND.SIGN.OFF";
    public final static String COMMAND_CHECK_LOCK    = "COMMAND.GET.LOCK";
    public final static String COMMAND_COMPILE_FILE  = "COMMAND.COMPILE.FILE";
    public final static String COMMAND_GET_CHANNELS  = "GET.CHANNELS";
    public final static String COMMAND_GET_LOCKS     = "COMMAND.GET.LOCKS";
    public final static String COMMAND_LOCK_FILE     = "COMMAND.LOCK.FILE";
    public final static String COMMAND_UNLOCK_FILE      = "COMMAND.UNLOCK.FILE";    
    public final static String COMMAND_GET_SERVER_FILES = "COMMAND.GET.SERVER.FILES";
    public final static String COMMAND_SAVE_FILE     = "COMMAND.SAVE.FILE";
    public final static String COMMAND_RETRIEVE_FILE = "COMMAND.RETRIEVE.FILE";    
    public final static String COMMAND_RETRIEVE_READ_ONLY_FILE = "COMMAND.RETRIEVE.READ.ONLY.FILE";
    public final static String COMMAND_EXECUTE_TEST = "COMMAND.EXECUTE.TEST";
    
    public final static String REQUEST_TYPE_CREATE_SESSION  = "CREATE.SESSION";
    public final static String REQUEST_TYPE_UTILITY_ROUTINE = "UTILITY.ROUTINE";

    public final static String RESPONSE_TYPE_ERROR_LOGIN = "ERROR.LOGIN";
    public final static String RESPONSE_TYPE_REPEAT_PASSWORD  = "XML.REPEAT.PASSWORD";
    public final static String RESPONSE_TYPE_EXPIRED_PASSWORD = "XML.EXPIRED.PASSWORD";
    public final static String RESPONSE_TYPE_XML_FRAMES = "XML.FRAMES";
    
    /** End T24 protocol names */
    
    public static enum FILE_EXISTS_ON_SERVER {TRUE, FALSE}
    
    public final static String DEFAULT_LOCAL_USERNAME  = "default.local.username";
    public final static String DEFAULT_LOCAL_EMAIL     = "default.local.email";
    public final static String DEFAULT_LOCAL_TELEPHONE = "default.local.telephone";
    
    public final static int HTTP_DEFAULT_TIMEOUT_MILLIS = 10000;
    
    public final static String DEFAULT_CHANNEL_STRING = "DEFAULT";
    
    public final static String MESSAGE_FAIL_HTTP_CONNECTION = "Failure in the communication protocol. Please ensure that Browser and T24 Server are running properly."; 
    public final static String MESSAGE_NOT_SIGN_ON = "SECURITY VIOLATION. User has not Signed On";
    public final static String MESSAGE_NO_LOCALWORKSPACE = "Local workspace not set correctly! Please make sure a default local project and a local directory have been selected. Look in Preferences Page.";
    public final static String MESSAGE_SECURITY_VIOLATION = "Please try the operation again.\nVerify that your username/password are correct.";
    public final static String MESSAGE_FILE_LOCKED = "File access is locked.";
    public final static String MESSAGE_CHANNEL_NOT_SELECTED = "A channel has not yet been selected. Please select a default channel before Signing On.\n(Note: a channel represents a remote T24 server.)";
    public final static String MESSAGE_FAIL_GET_CHANNELS = "Failed to retrieve channels.";
    public final static String MESSAGE_FAIL_GET_FILES = "Failed to retrieve files.";
    public final static String MESSAGE_FAIL_SET_CHANNEL  = "Failed to set channel.";
    public final static String MESSAGE_BROWSER_CANT_EXECUTE_COMMAND = "Browser can't execute the command. Please ensure the correct version of Browser is being access.";
    public final static String MESSAGE_COMPILATION_STOPPED = "Compilation was stopped by the user.";
    public final static String MESSAGE_PROCESS_STOPPED = "Process was stopped by the user.";
    public final static String MESSAGE_READ_TIMEOUT_HTTP_CONNECTION = "HTTP connection timed out waiting for a response.";
    public final static String MESSAGE_FAILED_LOCK_FILE = "File failed to be locked.";
    public final static String MESSAGE_LOCK_USERNAME_MANDATORY= "Locking program in Server Failed. Username is emtpy and it is a mandatory field. Check Preferences Page.";
    
}

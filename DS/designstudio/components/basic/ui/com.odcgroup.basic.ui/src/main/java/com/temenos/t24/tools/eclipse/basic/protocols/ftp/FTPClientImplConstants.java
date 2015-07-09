package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import org.eclipse.swt.graphics.Image;

import com.temenos.t24.tools.eclipse.basic.utils.EclipseUtil;

public final class FTPClientImplConstants {

    public static final int DEFAULT_FTP_PORT = 21;
    public static final String REMOTE_VIEW_ID = "com.temenos.t24.tools.eclipse.view.RemoteSystem";
    public static final String REMOTE_FILE_IMAGE_PATH = "/icons/file.gif";
    public static final String REMOTE_DIR_IMAGE_PATH = "/icons/folder.gif";
    public static final Image REMOTE_FILE_IMAGE = EclipseUtil.getImage(REMOTE_FILE_IMAGE_PATH);
    public static final Image REMOTE_DIR_IMAGE = EclipseUtil.getImage(REMOTE_DIR_IMAGE_PATH);
    public static final Image CHANGESET_FILE_IMAGE = EclipseUtil.getImage("/icons/test.png");
    public static final String[] NON_BASIC_FILE_EXTENSIONS = { ".xml", ".sh", ".o", ".so", ".l", ".java", ".class", ".jar" };
    // Error messages
    public static final String ERROR_DIALOG_CAPTION = "Remote Error";
    public static final String UNABLE_TO_CONNECT = "Unable to connect to remote site ";
    public static final String ERROR_RETRIEVE_FILE = "Unable to retrieve file ";
    public static final String ERROR_SAVE_FILE = "Unable to save file ";
    public static final String ERROR_CHANGE_DIR = "Unable to change directory ";
    public static final String ERROR_GET_LIST = "Unable to get files list ";
    public static final String ERROR_DELETE_FILE = "Unable to delete file ";
    public static final String ERROR_CURR_DIR = "Unable to retrieve current dir ";
    public static final String ERROR_NEW_DIR = "Unable to create new dir ";
    public static final String ERROR_GENERIC_OPR = "Unable to perform operation ";
    public static final String ERROR_NOT_CONNECTED = "Remote site not connected.";
    public static final String ERROR_LOGIN_FIALURE = "Login failed.Invalid Username or Password locks the environment,Edit the login details.";
  }

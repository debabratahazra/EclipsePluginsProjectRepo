package com.temenos.t24.tools.eclipse.basic;

import org.eclipse.swt.graphics.RGB;

import com.odcgroup.workbench.core.helper.FeatureSwitches;

/**
 * Central repository of constants used across the plug-in.
 * 
 * @author lfernandez
 * 
 */
public final class PluginConstants {

    /** general info */
    public static final String PLUGIN_DESCRIPTION = "T24 Basic IDE";
    public static final String PLUGIN_VERSION = "XX.YY.ZZ";
    /** local editor colors */
    public static final String T24_EDITOR_COLOR_BACKGROUND = "t24.editor.color.background";
    public static final String T24_EDITOR_COLOR_COMMENT = "t24.editor.color.comment";
    public static final String T24_EDITOR_COLOR_COMMON_VARIABLE = "t24.editor.color.common.variable";
    public static final String T24_EDITOR_COLOR_DEFAULT = "t24.editor.color.default";
    public static final String T24_EDITOR_COLOR_KEYWORD = "t24.editor.color.keyword";
    public static final String T24_EDITOR_COLOR_LITERAL = "t24.editor.color.literal";
    public static final String T24_EDITOR_COLOR_HYPERLINK = "t24.editor.color.hyperlink";
    /** Preferences */
    public static final String T24_REMOTE_HOSTNAME = "t24.remote.server.url.hostname";
    public static final String T24_REMOTE_PORT = "t24.remote.server.url.port";
    /**
     * server directory where User saves/compiles/gets files. By default is
     * GLOBUS.BP
     */
    public static final String T24_REMOTE_SERVER_DIR = "t24.remote.server.directory";
    /**
     * List of server directories separated by comma used for file retrieval
     * during hyperlink operation. By default GLOBUS.BP directory will be set.
     */
    public static final String T24_HYPERLINK_DIR = "t24.hyperlink.server.directory";
    /**
     * Current directory path, where files will be uploaded from the remote
     * server
     */
    public static final String T24_LOCAL_DIRECTORY = "t24.local.cur.directory.path";
    /** Default project, where remote files will be saved. */
    public static final String T24_LOCAL_DEFAULT_PROJECT_FULLPATH = "t24.local.default.project.fullpath";
    /** Location, where t24 doc files are saved. */
    public static final String T24_DOCUMENT_FULLPATH = "t24.doc.fullpath";
    // if true => a file retrieved by the server will always be locally saved.
    // if false => files retrieved from server are only opened in text editors
    // but not saved locally.
    public static final String T24_SAVE_LOCALLY_BY_DEFAULT_ON = "t24.save.locally.by.default.on";
    // if true => overwriting of a file will be prompted with a dialog.
    public static final String T24_PROMPT_BEFORE_OVERWRITING = "t24.always.prompt.before.overwriting";
    // true => collapses all the region in the document. false=> expands all the
    // region in the document.
    public static final String T24_ALWAYS_COLLAPSE_REGION = "t24.always.collapse.region";
    /**
     * Number of spaces in the standard indentation used while performing a
     * document formatting.
     */
    public static final String T24_INDENTATION_SPACES = "t24.formatting.indentation.spaces";
    /**
     * if true => tab transform ("\t" to spaces) will take place when sending a
     * file to Server, otherwise the "\t" will be kept as they are.
     */
    // if true => -D option will be added to EB.COMPILE command. This will allow
    // programs with DEBUG statements to be compiled.
    // if false => -D will not be added.
    public static final String T24_COMPILE_WITH_DEBUG = "t24.remote.compile.allow.debug";
    // Timeout in milliseconds for commands sent down to the server. If no
    // response
    // is received before the timeout period, the command will generate a
    // timeout error.
    public static final String T24_REMOTE_TIMEOUT_MILLIS = "t24.remote.server.http.timeoutmillis";
    /**
     * local user details. These are used, for example, to lock a file in the
     * Server when it is opened
     */
    public static final String T24_LOCAL_USERNAME = "t24.local.user.name";
    public static final String T24_LOCAL_EMAIL = "t24.local.user.email";
    public static final String T24_LOCAL_TELEPHONE = "t24.local.user.telephone";
    public static final String T24UNIT_TEST_COMPILE = "t24.unit.test.compile";
    public static final String T24_SERVER_DIRECTORY = "t24.default.server.dir";
    /** DEFAULT values, used during restore default * */
    public static final String DEFAULT_REMOTE_HOSTNAME = "localhost";
    public static final String DEFAULT_REMOTE_PORT = "8080";
    public static final String DEFAULT_REMOTE_DIR = "BP";
    public static final String DEFAULT_DOCUMENT_FULLPATH = "";
    public static final String DEFAULT_TIMEOUT_MILLIS = "15000";
    public static final String DEFAULT_LOCAL_DIRECTORY = "";
    public static final String DEFAULT_LOCAL_PROJECT = "";
    public static final String DEFAULT_SAVEL_LOCALLY_ON = "false";
    public static final String DEFAULT_INDENTATION_SPACES = "4";
    public static final String DEFAULT_TAB_TRANSFORM_ON = "true";
    public static final String DEFAULT_COMPILE_WITH_DEBUG = "false";
    public static final String DEFAULT_FILL_IN = "Please fill in";
    public static final String DEFAULT_COMPILE_TEST = "false";
    public static final RGB DEFAULT_EDITOR_COLOR_BACKGROUND = new RGB(255, 255, 255); // white
    public static final RGB DEFAULT_EDITOR_COLOR_COMMENT = new RGB(0, 150, 0); // green;
    public static final RGB DEFAULT_EDITOR_COLOR_COMMON_VARIABLE = new RGB(200, 0, 0); // red;
    public static final RGB DEFAULT_EDITOR_COLOR_DEFAULT = new RGB(0, 0, 0); // black
    public static final RGB DEFAULT_EDITOR_COLOR_KEYWORD = new RGB(0, 0, 255); // blue
    public static final RGB DEFAULT_EDITOR_COLOR_LITERAL = new RGB(125, 125, 125); // grey
    public static final RGB DEFAULT_EDITOR_COLOR_HYPERLINK = new RGB(0, 0, 255); // blue
    public static final RGB DEFAULT_COLOR_HOVERED_WORD_ANNOTATION = new RGB(0, 0, 255); // blue
    /** Other constants */
    public static final String config_dir = "/data/";
    public static final String keywordsFilename = "basickeywords.dat";
    public static final String commonVariablesFilename = "commonVariables.dat";
    public static final String basicKeywordsHelpFilename = "basickeywords_help.properties";
    public static final String loggerProperties = "log4j.properties";
    public static final String setupFilename = "t24jbase_setup.properties";
    public static final String globalPropertiesFilename = "t24_global_plugin.properties";
    public static final String t24StateFile = "t24state.xml";
    public static final String t24GlobalPropertiesFile = "t24global.xml";
    // Subroutines,Programs and Inserts
    public static final String t24SourceItemsFile = "t24SourceItems.dat";
    // Objects and methods
    public static final String t24ObjectsFile = "t24Objects.properties";
    /**
     * Local file extension. Typically used when loading or saving a file
     * locally
     */
    public static final String LOCAL_BASIC_DOT_PREFIX = ".b";
    public static final String LOCAL_BASIC_PREFIX = "b";
    /** Backup copy extension default */
    public static final String BACKUP_COPY_DOT_PREFIX = ".BAK";
    public static final String BACKUP_COPY_PREFIX = "BAK";
    /** File images */
    public static final String LOCAL_FILE_IMAGE_PATH = "/icons/default.gif";
    public static final String REMOTE_FILE_IMAGE_PATH = "/icons/t24BlueImage.gif";
    /** Temporary project, for copying temporarily some files */
    public static final String T24_TEMPORARY_IDE_PROJECT = "TempT24BasicIDEProj";
    /** Messages */
    public final static String MESSAGE_NO_LOCALWORKSPACE = "Local workspace not set correctly! Please make sure a default local project and a local directory have been selected. Look in Preferences Page.";
    public final static String MESSAGE_FAIL_SIGN_ON = "SignOn action failed. Check username/password";
    public final static String MESSAGE_PASSWORD_REPEAT = "SignOn requires to repeat the password.";
    public final static String MESSAGE_PASSWORD_EXPIRED = "SignOn action failed. Password expired.";
    public final static String MESSAGE_NO_WORD_DOCUMENT_FOUND="Component document not available";
    public final static String[] t24BranchingItems = { "CALL", "$INSERT", "INSERT", "$INCLUDE", "INCLUDE", "EXECUTE", "DEFFUN" };
    /** Files to load source from */
    public final static String SERVER_PRIMARY_SOURCE = "GLOBUS.BP";
    public final static String SERVER_PRIMARY_SOURCE_ALTERNATE = "T24_BP";
    public final static String SERVER_TEMP_DIR = "RTC.BP";
    /** Template code used for component code creation */
    public final static String T24_OBJECT_TEMPLATE = "t24.object.template";
    public final static String T24_METHOD_TEMPLATE = "t24.method.template";
    /** T24 Method Size */
    public static final int T24METHOD_MIN_LENGTH = 3;
    public static final int T24METHOD_MAX_LENGTH = 35;
    /** T24 Object Size */
    public static final int T24OBJECT_MIN_LENGTH = 3;
    public static final int T24OBJECT_MAX_LENGTH = 35;
    /** Template code used for TUnit code creation */
    public final static String T24_UNITTEST_TEMPLATE = "t24.unittest.template";
    /** Max File Size Limit in bytes - 1 MB */
    public final static long MAX_FILE_SIZE = 1048576;
    /** T24 Subroutine names */
    public final static String T24SUBROUTINE_COMPILE_SOURCE = FeatureSwitches.dsSubroutineEnabled.get() ? "DS.COMPILE" : "EB.COMPILE";
    public final static String T24SUBROUTINE_EXECUTE_UNITTEST = "T24UNIT";
    public final static String T24SUBROUTINE_DATA_GET = "EB.GET.FROM.T24";
    public final static String T24SUBROUTINE_DATA_SEND = "EB.SEND.TO.T24";
    public final static String T24_INSTALL_SOURCE = "EB.INSTALL.SOURCE";
    public final static String T24_INSTALL_DATA = "EB.INSTALL.DATA";
    public final static String T24DATA_TEMP_DIR = "DIM.TEMP/DATA";
    public final static String SERVER_PRIMARY_DATA = "DIM.TEMP.DATA";
    public final static String CHECK_DEPENDENCY = "CHECK.DEPENDENCY";
    public final static String EB_GET_RELATED_UPDATES = "EB.GET.RELATED.UPDATES";
    public final static String SOURCE_TYPE = "SOURCE";
    public final static String DATA_TYPE = "DATA";
    /** Log file folder name. Used during change set installation */
    public final static String LOG_FILE_DIRECTORY = "Logger";
    /** T24 update dependency area details */
    public final static String R09_SITE_NAME = "R09";
    public final static String R10_SITE_NAME = "R10";
    public final static String R09_HOST_NAME = "10.92.5.15";
    public final static String R10_HOST_NAME = "10.92.5.37";
    public final static String R09_USER_NAME = "updtest1";
    public final static String R10_USER_NAME = "r10upd2";
    public final static String R09_PORT_NUMBER = "21567";
    public final static String R10_PORT_NUMBER = "51000";
    
    /** T24 Basic UI's plugin id */
    public final static String BASIC_UI_PLUGIN_ID = "com.odcgroup.basic.ui";
}

package com.temenos.t24.tools.eclipse.basic.protocols.ftp;

import com.temenos.t24.tools.eclipse.basic.utils.LogConsole;

/**
 * This class acts as an intermediary between the {@link LogConsole} and Remote
 * operations by providing different methods based on the sensitivity.
 * 
 * @author ssethupathi
 * 
 */
public final class RemoteOperationsLog {

    private static LogConsole console = LogConsole.getT24BasicConsole();
    private final static String ERROR = "Error: ";
    private final static String WARN = "Warn: ";
    private final static String INFO = "Info: ";

    /**
     * logs information. Eg., File retrieved successfully.
     * 
     * @param message
     */
    public static void info(String message) {
        console.logMessage(INFO + message);
    }

    /**
     * logs errors. Eg., Unable to connect to Remote Site.
     * 
     * @param message
     */
    public static void error(String message) {
        console.logMessage(ERROR + message);
    }

    /**
     * logs warnings. Currently not used.
     * 
     * @param message
     */
    public static void warning(String message) {
        console.logMessage(WARN + message);
    }
}

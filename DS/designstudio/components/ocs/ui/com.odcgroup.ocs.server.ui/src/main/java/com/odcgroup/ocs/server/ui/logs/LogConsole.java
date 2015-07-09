package com.odcgroup.ocs.server.ui.logs;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.odcgroup.ocs.server.ServerCore;
import com.odcgroup.server.ui.internal.CommonConsole;

/**
 * Access to the log console (which is in fact a simple MessageConsole) 
 * @author yan
 * @since 1.40
 */
public class LogConsole extends CommonConsole implements ILogChangedListener {

	/** used to distinguish LogConsole from simple MessageConsole */
	public static final String LOG_CONSOLE_ATTRIB_KEY = LogConsole.class.toString();
	
//	private static final int MAX_CONSOLE_OUTPUT = 5000000; // 5 Mb of log
	
	// Patterns used to colors lines differently according the log level
	private static final String FATAL_TAG = "FATAL";   //$NON-NLS-1$
	private static final String ERROR_TAG = "ERROR";   //$NON-NLS-1$
	private static final String WARN_TAG = "WARN";   //$NON-NLS-1$
	private static final String INFO_TAG = "INFO";  //$NON-NLS-1$
 
	// Name of the watched log file
	protected String watchedLogFileName;
	
	// Defines if the logging is finished
	private boolean loggingFinished;
	
	// Server name of the server logging the file
	private String serverName;

    /**
     * Create a Log Console
     * @param name log file name
     */
	public LogConsole(String watchedLogFileName, String serverName) {
		this.watchedLogFileName = watchedLogFileName;
		this.serverName = serverName;
	}
	
    protected String getScopeQualifier() {
    	return ServerCore.PLUGIN_ID;	
    }
	
	protected String consoleName() {
		return (loggingFinished?"<terminated> ":"") + serverName + " - " + watchedLogFileName;
	}

	protected void configureConsole(MessageConsole console) {
		console.setAttribute(LOG_CONSOLE_ATTRIB_KEY, watchedLogFileName);
//		console.setWaterMarks(MAX_CONSOLE_OUTPUT, MAX_CONSOLE_OUTPUT*10/9);
	}

	// Select the message stream according to the level display in the line 
	private MessageConsoleStream getMessageStream(String newText) {
		if (newText.contains(ERROR_TAG) || newText.contains(FATAL_TAG)){
			return getErrorConsoleStream();
		} else if (newText.contains(WARN_TAG)) {
			return getWarningConsoleStream();
		} else if (newText.contains(INFO_TAG)) {
			return getInfoConsoleStream();
		} else {
			return getDebugConsoleStream();
		}
	}

	/* (non-Javadoc)
	 * @see com.odcgroup.ocs.server.logs.ILogChangedListener#logChanged(java.lang.String)
	 */
	public void logChanged(String newText) {
		MessageConsoleStream stream = getMessageStream(newText);
		if (!stream.isClosed() && messageConsole.getDocument() != null) {
			stream.println(newText);
		}
	}

	public void setLoggingFinished(boolean loggingFinished) {
		this.loggingFinished = loggingFinished;
		refreshConsoleName();
	}

	/**
	 * @param serverName the serverName to set
	 */
	void setServerName(String serverName) {
		this.serverName = serverName;
	}

	@Override
	protected IPreferenceStore getPreferenceStore() {
		return new ScopedPreferenceStore(InstanceScope.INSTANCE, getScopeQualifier());
	}
	
}

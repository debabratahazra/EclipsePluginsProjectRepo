package com.odcgroup.ocs.support.ui.external.tool;

import java.util.Deque;
import java.util.LinkedList;

/**
 * This class is responsible to keep a certain amount of logging lines.
 * To be used with OutputCallbackWithLastEntries
 */
public class LogKeeper {

	protected final static int DEFAULT_LAST_LOG_ENTRIES_SAVED = 100;
	protected int nbLogsEntriesKept;

	private Deque<String> lastLogEntries = new LinkedList<String>();

	/**
	 * Create a container to keep the last log entries.
	 */
	public LogKeeper() {
		this(DEFAULT_LAST_LOG_ENTRIES_SAVED);
	}
	
	/**
	 * Create a container to keep the last log entries.
	 * @param maxLogEntriesKept size of the log entry. 
	 * Use -1 to keep them all.
	 */
	public LogKeeper(int maxLogEntriesKept) {
		this.nbLogsEntriesKept = maxLogEntriesKept;
	}
	
	public void keepLog(String line) {
		// Keep only the most recent lines
		lastLogEntries.addLast(line);
		if (nbLogsEntriesKept > 0 && 
				lastLogEntries.size() > nbLogsEntriesKept) {
			lastLogEntries.removeFirst();
		}
	}
	
	public String getLastLogEntries() {
		StringBuffer result = new StringBuffer();
		for (String line: lastLogEntries) {
			result.append(line);
			result.append("\n");
		}
		return result.toString();
	}
	
}

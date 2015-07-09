package com.odcgroup.ocs.server.ui.logs;

/**
 * Called each time a new line is found in log files
 * @author yan
 * @since 1.40
 */
public interface ILogChangedListener {

	/**
	 * Called each time a new line is found in log files
	 * @param newLine
	 */
	public void logChanged(String newLine);
	
}

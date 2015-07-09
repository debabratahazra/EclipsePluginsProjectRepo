package com.odcgroup.ocs.server.ui.logs.internal.watcher;

import java.io.File;

import com.odcgroup.ocs.server.ui.logs.ILogChangedListener;
import com.odcgroup.ocs.server.ui.logs.ILogWatcherStoppedListener;

/**
 * Interface for log watcher runnable
 * @author yan
 */
public interface ILogWatcherRunnable extends Runnable {

	/**
	 * @return the name of the watched file
	 */
	public String getName();

	/**
	 * Update the log file watched
	 * @param logLocation new file location
	 */
	public void updateLogFileName(File logLocation);

	/**
	 * Request the watcher to stop
	 * @param closeConsole whether or not the console should be closed as well
	 */
	public void requestStop(boolean closeConsole);

	/**
	 * Add a stop listener which will be called when the log watcher is 
	 * stopped
	 * @param listener
	 */
	public void addStopListener(ILogWatcherStoppedListener listener);

	/**
	 * Add a log watcher which will be called when the log watcher 
	 * detects a change
	 * @param listener
	 */
	public void addLogChangedListener(ILogChangedListener listener);
	
}

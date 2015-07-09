package com.odcgroup.ocs.server.ui.logs;

import com.odcgroup.ocs.server.ui.logs.internal.watcher.ILogWatcherRunnable;

/**
 * Listener called when a log watcher dies
 * @author yan
 * @since 1.40
 */
public interface ILogWatcherStoppedListener {

	/**
	 * Called when a log watcher dies
	 * @param watcher dead watcher
	 * @param closeConsole tells the listener if it is requested to close
	 * also the console
	 */
	public void logWatcherStopped(ILogWatcherRunnable watcher, boolean closeConsole);
	
}

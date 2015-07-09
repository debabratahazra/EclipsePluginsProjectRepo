package com.odcgroup.workbench.core.logging;

import org.slf4j.Logger;

/**
 * Logs memory statistics.
 *
 * @author Michael Vorburger
 */
public class MemoryLogUtil {

	public static void logMemory(Logger logger) {
		final int mb = 1024*1024;
		Runtime runtime = Runtime.getRuntime();
		
		long used = (runtime.totalMemory() - runtime.freeMemory()) / mb;
		long free = runtime.freeMemory() / mb;
		long total = runtime.totalMemory() / mb;
		long max = runtime.maxMemory() / mb;
		
		logger.info("Heap stats; used: {} MB, free: {} MB, total: {} MB, max: {} MB", new Object[] { used, free, total, max });
	}	

}

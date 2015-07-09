package com.odcgroup.ocs.support.ui.external.tool;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class LogKeeperTest {

	private static final String SAMPLE_LINE = "Sample log line...";
	
	@Test
	public void testDS4308LogKeeper() {
		LogKeeper logKeeper = new LogKeeper();

		final int NB_LOGS = 2 * LogKeeper.DEFAULT_LAST_LOG_ENTRIES_SAVED;
		final int NB_LOGS_KEPT = LogKeeper.DEFAULT_LAST_LOG_ENTRIES_SAVED;
		
		logEvents(logKeeper, NB_LOGS);
		runasserts(logKeeper, NB_LOGS, NB_LOGS_KEPT);
	}

	@Test
	public void testDS4308LogKeeper_withSpecifiedNbEntriesSaved() {
		final int SPECIFIED_NB_ENTRIES_SAVED = 50;
		LogKeeper logKeeper = new LogKeeper(SPECIFIED_NB_ENTRIES_SAVED);

		final int NB_LOGS = 200;
		
		logEvents(logKeeper, NB_LOGS);
		runasserts(logKeeper, NB_LOGS, SPECIFIED_NB_ENTRIES_SAVED);
	}

	@Test
	public void testDS4308LogKeeper_withUnlimitedEntriesSaved() {
		LogKeeper logKeeper = new LogKeeper(0);

		final int NB_LOGS = 200;
		
		logEvents(logKeeper, NB_LOGS);
		runasserts(logKeeper, NB_LOGS, NB_LOGS);
	}

	/**
	 * Log nbLogItems log events
	 */
	private void logEvents(LogKeeper logKeeper, int nbLogItems) {
		for (int i=0; i<nbLogItems; i++) {
			logKeeper.keepLog(SAMPLE_LINE+(i+1));
		}
	}
	
	private void runasserts(LogKeeper logKeeper, int nbLogs, int nbLogsKept) {
		final String LAST_LOG = SAMPLE_LINE + nbLogs;
		final String FIRST_REMAINING_LOG = SAMPLE_LINE + (nbLogs-nbLogsKept+1);
		
		Assert.assertEquals(nbLogsKept, 
				StringUtils.countMatches(logKeeper.getLastLogEntries(), SAMPLE_LINE));
		Assert.assertTrue("Should end with " + LAST_LOG, 
				StringUtils.removeEnd(logKeeper.getLastLogEntries(), "\n").endsWith(LAST_LOG));
		Assert.assertTrue("The first remaining log should be " + FIRST_REMAINING_LOG, 
				logKeeper.getLastLogEntries().startsWith(FIRST_REMAINING_LOG));
	}

}

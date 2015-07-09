package com.odcgroup.ocs.server.external.ui.logs;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.ocs.server.external.model.internal.ExternalServer;
import com.odcgroup.ocs.server.ui.logs.LogWatcherManager;
import com.odcgroup.ocs.server.ui.logs.internal.watcher.ILogWatcherRunnable;
import com.odcgroup.server.model.IDSServer;

public class LogWatcherManagerTest {

	/**
	 * @see http://rd.oams.com/browse/DS-4394
	 */
	@Test
	public void testGetLocationReturnsTheLogLocationForTheServerSelected() throws Exception {
		String dummyExternalLogFileName = "testExternalLogFileName";
		IDSServer mockExternalServer = Mockito.mock(ExternalServer.class);
		Mockito.when(mockExternalServer.getLogDirectory()).thenReturn("mockExternalLogDirectory");

		LogWatcherManager logWatcherManager = new LogWatcherManager();
		logWatcherManager.startLogWatcher(mockExternalServer, dummyExternalLogFileName);

		Map<String, ILogWatcherRunnable> logWatcherRunnables = logWatcherManager.getLogWatcherRunnables();
		Assert.assertTrue(logWatcherRunnables.containsKey(dummyExternalLogFileName));
	}
}

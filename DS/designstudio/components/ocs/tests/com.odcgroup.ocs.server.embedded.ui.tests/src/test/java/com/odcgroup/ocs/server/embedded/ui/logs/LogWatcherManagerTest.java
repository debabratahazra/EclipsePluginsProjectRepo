package com.odcgroup.ocs.server.embedded.ui.logs;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.ocs.server.embedded.model.internal.EmbeddedServer;
import com.odcgroup.ocs.server.ui.logs.LogWatcherManager;
import com.odcgroup.ocs.server.ui.logs.internal.watcher.ILogWatcherRunnable;
import com.odcgroup.server.model.IDSServer;

public class LogWatcherManagerTest {

	/**
	 * @see http://rd.oams.com/browse/DS-4394
	 */
	@Test
	public void testGetLocationReturnsTheLogLocationForTheServerSelected() throws Exception {
		String dummyEmbeddedLogFileName = "testEmbeddedLogFileName";
		IDSServer mockEmbeddedServer = Mockito.mock(EmbeddedServer.class);
		Mockito.when(mockEmbeddedServer.getLogDirectory()).thenReturn("mockEmbeddedLogDirectory");

		LogWatcherManager logWatcherManager = new LogWatcherManager();
		logWatcherManager.startLogWatcher(mockEmbeddedServer, dummyEmbeddedLogFileName);

		Map<String, ILogWatcherRunnable> logWatcherRunnables = logWatcherManager.getLogWatcherRunnables();
		Assert.assertTrue(logWatcherRunnables.containsKey(dummyEmbeddedLogFileName));
	}
}

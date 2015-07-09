package com.odcgroup.ocs.server.ui.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.server.model.IDSServer;
import com.odcgroup.server.ui.ServerUICore;

/**
 *
 * @author pkk
 *
 */
public class ServerManagerTest {

	@Test
	public void testGetOcsServersReturnsZeroServersWhenNoServersAvailable() throws Exception {
		List<IDSServer> ocsServers = ServerUICore.getDefault().getContributions().getServers();
		Assert.assertTrue(ocsServers.size() == 0);
	}
}

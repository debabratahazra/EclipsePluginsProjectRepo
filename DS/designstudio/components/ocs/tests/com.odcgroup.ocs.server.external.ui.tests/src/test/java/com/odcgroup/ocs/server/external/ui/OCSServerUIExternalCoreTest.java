package com.odcgroup.ocs.server.external.ui;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.ocs.server.external.model.IExternalServer;

public class OCSServerUIExternalCoreTest  {

	
	@Test
	public void testGetDisplayableExternalServers_ReturnsZeroLengthList_WhenThereIsOneExternalServerButItIsNotConfigured() throws Exception {
		OCSServerUIExternalCore ocsServerUIExternalCore = new OCSServerUIExternalCore();
		List<IExternalServer> displayableExternalServers = ocsServerUIExternalCore.getDisplayableExternalServers();
		Assert.assertEquals(0, displayableExternalServers.size());
	}
	
}

package com.temenos.ds.t24.data.eson.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.temenos.ds.t24.data.eson.ESONResourceProviderMapper;
import com.temenos.ds.t24.data.eson.ESONDataModel;
import com.temenos.ds.t24.data.eson.ESONWriter;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.impl.MockResourceProviderService;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * Tests for ESONWriter.
 *
 * @author Michae Vorburger
 */
public class ESONWriterTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ESONWriterTest.class); 

	@Test
	public void writeESON() throws Exception {
		MockResourceProviderService m = new MockResourceProviderService();
		// m.mockGetResourceResponse("/getResource-CURRENCY-CHF_response.xml");
		m.mockGetResourceResponse("/getResource-CUSTOMER-100432_response.xml");
		GetResourceResponse response = m.getResource(new T24UserDetails(), new T24InteractionContext(), null, null, null);

		ESONWriter w = new ESONWriter();
		String eson = w.toESON(response);
		LOGGER.debug("first version:\n" + eson);

		ESONDataModel eson2 = new ESONResourceProviderMapper().toESON(response);
		LOGGER.debug("second version:\n" + eson2);

		String esonText = Resources.toString(Resources.getResource("CUSTOMER-100432.eson"), Charsets.UTF_8);
		assertEquals(esonText, eson);
	}

}

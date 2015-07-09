package com.temenos.ds.t24.data.rps;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.impl.MockResourceProviderService;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * Unit test for the ResourceProviderMock.
 *
 * @author Michael Vorburger
 */
public class MockResourceProviderServiceTest {

	@Test
	public void testMockResourceProviderService() throws Exception {
		MockResourceProviderService m = new MockResourceProviderService();
		m.mockGetResourceResponse("/getResource-CURRENCY-CHF_response.xml");
		GetResourceResponse response = m.getResource(new T24UserDetails(), new T24InteractionContext(), null, null, null);
		assertEquals("Swiss Franc", response.fields.get(2).value);
	}

}

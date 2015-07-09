package com.interaction.example.odata.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.edm.EdmDataServices;
import org.odata4j.edm.EdmEntitySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.interaction.testfx.t24.AbstractT24InteractionTest;
import com.temenos.interaction.testfx.t24.ConfigurationT24;

/**
 * Integration test for company and switch company context. 
 */
public class TestCompanyITCase extends AbstractT24InteractionTest {
	private final static Logger logger = LoggerFactory.getLogger(TestCompanyITCase.class);

	private String baseUri = null;
	private HttpClient client;
	private final String resourceName = "verCustomer_Inputs";

	public TestCompanyITCase() {
		super(new ConfigurationT24(Configuration.TEST_ENDPOINT_URI, Configuration.TEST_COMPANY, Configuration.TEST_USERNAME, Configuration.TEST_PASSWORD, Configuration.TEST_T24_DATE));
	}
	
	@Before
	public void setup() {
		baseUri = config.getEndpointUri();
		client = getHttpClient();
	}
	@Test
	public void testGetServiceDocumentUri() throws Exception {
		ODataConsumer consumer = getODataConsumer();
		// get the service document for the company specific service document
		String serviceRootUri = consumer.getServiceRootUri();
		assertNotNull(serviceRootUri);
		GetMethod method = new GetMethod(serviceRootUri);
		String response = null;
		try {
	    	method.setDoAuthentication(true);		//Require authentication
			client.executeMethod(method);
			assertEquals(200, method.getStatusCode());

			if (method.getStatusCode() == HttpStatus.SC_OK) {
				// read as string
				response = method.getResponseBodyAsString();
				logger.debug("Response = " + response);
			}
		} catch (IOException e) {
			fail(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		// assert the Users entity set exists in service document
		assertTrue(response.contains("<collection href=\"" + resourceName + "\">"));
	}

	@Test
	public void testGetMetadata() throws Exception {
		ODataConsumer consumer = getODataConsumer();
		// get the metadata for the company specific service document
		EdmDataServices metadata = consumer.getMetadata();
		// get the Users entity, must be there to load the user profile
		EdmEntitySet userEntitySet = metadata.getEdmEntitySet(resourceName);
		assertEquals(resourceName, userEntitySet.getName());
	}

	@Test
	@Ignore
	public void testGetRoot() throws Exception {
		GetMethod method = new GetMethod(baseUri + "root");
		Entry rootEntry = makeGetRequestForEntry(client, method);
		List<Link> links = rootEntry.getLinks("http://temenostech.temenos.com/rels/profile");
		assertEquals(1, links.size());
	}

}

package com.temenos.ds.t24.data.eson.tests;

import javax.jms.IllegalStateException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.eson.ESONDataModel;
import com.temenos.ds.t24.data.eson.ESONDataModel.StringAttribute;
import com.temenos.ds.t24.data.eson.ESONParser;
import com.temenos.ds.t24.data.eson.ESONResourceProviderMapper;
import com.temenos.ds.t24.data.eson.T24ResourceProviderFacade;
import com.temenos.ds.t24.data.rps.IntegrationTestData;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.rps.connection.properties.T24ConnectionProperties;
import com.temenos.ds.t24.data.rps.impl.ResourceProviderServiceWrapper;
import com.temenos.ds.t24.data.util.Execution;
import com.temenos.ds.t24.data.util.SafeIntegrationTestExecutor;
import com.temenos.services.resourceprovider.data.T24InteractionContext;
import com.temenos.soa.services.data.T24UserDetails;

/**
 * @author yandenmatten
 */
@Ignore // DS-8885 fix instability issue before re-enabling.
public class ESONIntegrationTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ESONIntegrationTest.class);
	
	@Test
	public void testRoundtripTAFC() throws Exception {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws Exception {
			T24ResourceProviderFacade t24ComponentFwkFacade = new T24ResourceProviderFacade();
			try {
				t24ComponentFwkFacade.connectTAFC("/tafc-integrationtest-server.properties");
				
				String table = IntegrationTestData.CUSTOMER_TABLE;
				String id = IntegrationTestData.CUSTOMER_ID;
				
				String esonFromT24 = readESONFromT24(t24ComponentFwkFacade, table, id);
				Assert.assertTrue("ESON returned from T24 is null or empty", StringUtils.isNotEmpty(esonFromT24));
				LOGGER.debug("Initial eson:\n" + esonFromT24 + "\n\n");
				
				String alteredLocallyEson = alterData(esonFromT24);
				Assert.assertNotEquals(esonFromT24, alteredLocallyEson);
				LOGGER.debug("Altered eson:\n" + alteredLocallyEson + "\n\n");
				
				T24ResourceProviderFacade t24ComponentFwkFacadeAuthorizer = new T24ResourceProviderFacade();
				try {
					t24ComponentFwkFacadeAuthorizer.connectTAFC("/tafc-integrationtest-server-authoriser.properties");
					
					try {
						updateT24(t24ComponentFwkFacade, alteredLocallyEson, id);
					} catch (Exception e) {
						// Authorize the previously NAU record
						t24ComponentFwkFacadeAuthorizer.authorizeRecordSafely(table, id);
						// ... and then retry
						updateT24(t24ComponentFwkFacade, alteredLocallyEson, id);
					}
					// Authorize the record
					t24ComponentFwkFacadeAuthorizer.authorizeRecordSafely(table, id);
				} finally {
					t24ComponentFwkFacadeAuthorizer.close();
				}
				
				String remotelyAlteredEson = readESONFromT24(t24ComponentFwkFacade, table, id);
				AssertESON.assertEqualsIgnoreT24Audit("The read record should be the same as the one sent to T24", alteredLocallyEson, remotelyAlteredEson);
				
				LOGGER.debug("Eson read from T24:\n" + remotelyAlteredEson + "\n\n");
			} finally {
				t24ComponentFwkFacade.close();
			}
		}});
	}
	
	@Test
	@Ignore 
	// TODO DS-8830 RemoteServiceException: Failed to load user sms : null
	public void testRoundtripTAFJ() throws Exception {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws Exception {
			T24ResourceProviderFacade t24ComponentFwkFacade = new T24ResourceProviderFacade();
			try {
				t24ComponentFwkFacade.connectTAFJ("/tafj-integrationtest-server.properties");
				
				String table = IntegrationTestData.CUSTOMER_TABLE;
				String id = IntegrationTestData.CUSTOMER_ID;
				
				String esonFromT24 = readESONFromT24(t24ComponentFwkFacade, table, id);
				Assert.assertTrue("ESON returned from T24 is null or empty", StringUtils.isNotEmpty(esonFromT24));
				LOGGER.debug("Initial eson:\n" + esonFromT24 + "\n\n");
				
				String alteredLocallyEson = alterData(esonFromT24);
				Assert.assertNotEquals(esonFromT24, alteredLocallyEson);
				LOGGER.debug("Altered eson:\n" + alteredLocallyEson + "\n\n");
				
				try {
					updateT24(t24ComponentFwkFacade, alteredLocallyEson, id);
				} catch (Exception e) {
					// Authorize the previously NAU record
					authorizeT24RecordTAFJ(table, id);
					// ... and then retry
					updateT24(t24ComponentFwkFacade, alteredLocallyEson, id);
				}
				// Authorize the record
				authorizeT24RecordTAFJ(table, id);
				
				String remotelyAlteredEson = readESONFromT24(t24ComponentFwkFacade, table, id);
				AssertESON.assertEqualsIgnoreT24Audit("The read record should be the same as the one sent to T24", alteredLocallyEson, remotelyAlteredEson);
				
				LOGGER.debug("Eson read from T24:\n" + remotelyAlteredEson + "\n\n");
			} finally {
				t24ComponentFwkFacade.close();
			}
		}});
	}
	
	private void authorizeT24RecordTAFJ(String table, String id) throws Exception {
		T24ResourceProviderFacade t24ComponentFwkFacadeAuthorizer = new T24ResourceProviderFacade();
		try {
			t24ComponentFwkFacadeAuthorizer.connectTAFJ("/tafj-integrationtest-server-authoriser.properties");
			t24ComponentFwkFacadeAuthorizer.readRecord(table, id);
			t24ComponentFwkFacadeAuthorizer.authorizeRecord(table, id);
		} finally {
			t24ComponentFwkFacadeAuthorizer.close();
		}
	}

	private String readESONFromT24(T24ResourceProviderFacade t24ComponentFwkFacade, String table, String id) throws Exception {
		GetResourceResponse resource = t24ComponentFwkFacade.readRecord(table, id);
		return new ESONResourceProviderMapper().toESON(resource).toString();
	}

	private String alterData(String eson) throws IllegalStateException {
		ESONDataModel esonDataModel = new ESONParser(eson).parse();
		StringAttribute stringAttribute = esonDataModel.root.getFeatureBy("STREET")
				.getValueAsMultiValue()
				.getValueAsNewObject(0)
				.getFeatureBy("STREET")
				.getValueAsStringAttribute();
		stringAttribute.value = alterString(stringAttribute.value);
		return esonDataModel.toString();
	}
	
	private String alterString(String value) {
		if (value.startsWith("DS ")) {
			return StringUtils.removeStart(value, "DS ");
		} else {
			return "DS " + value;
		}
	}

	private void updateT24(T24ResourceProviderFacade t24ComponentFwkFacade, String alteredLocallyEson, String id) throws Exception {
		ESONParser esonParser = new ESONParser(alteredLocallyEson);
		ESONDataModel esonDataModel = esonParser.parse();
		GetResourceResponse resourceResponse = new ESONResourceProviderMapper().toGetResourceResponse(esonDataModel);
		LOGGER.debug("Prepared response:");
		LOGGER.debug(resourceResponse.toString());
		t24ComponentFwkFacade.writeRecord(resourceResponse, id);
	}

	@Test
	public void testT24ConnectionProperties_fromClasspath_ExceptionType() throws SecurityException, NoSuchMethodException {
		Class<?>[] exceptionTypes = T24ConnectionProperties.class.getMethod("fromClasspath", String.class).getExceptionTypes();
		for (Class<?> clazz: exceptionTypes) {
			Assert.assertNotEquals(Exception.class, clazz);
		}
	}

	@Test
	public void testResourceProviderServiceWrapper_getResource_ExceptionType() throws SecurityException, NoSuchMethodException {
		Class<?>[] exceptionTypes = ResourceProviderServiceWrapper.class.getMethod("getResource" , T24UserDetails.class, T24InteractionContext.class, String.class, String.class, String.class).getExceptionTypes();
		for (Class<?> clazz: exceptionTypes) {
			Assert.assertNotEquals(Exception.class, clazz);
		}
	}
	
}

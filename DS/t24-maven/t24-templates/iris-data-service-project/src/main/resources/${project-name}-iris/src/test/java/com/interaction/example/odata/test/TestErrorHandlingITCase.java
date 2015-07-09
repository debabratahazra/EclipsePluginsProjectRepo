package com.interaction.example.odata.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.behaviors.OClientBehaviors;
import org.odata4j.core.OEntity;
import org.odata4j.jersey.consumer.ODataJerseyConsumer;

import com.temenos.interaction.testfx.ExtendedOClientBehaviour;
import com.temenos.interaction.testfx.t24.AbstractT24InteractionTest;
import com.temenos.interaction.testfx.t24.ConfigurationT24;


/**
 * Error handling test cases.   
 */
public class TestErrorHandlingITCase extends AbstractT24InteractionTest {
	
	private ODataConsumer consumer = null;

	private static String BASE_ENTITY = "enqCustomerInfos";
	private static String BASE_ENTRY = "100110";

	public TestErrorHandlingITCase() {
		super(new ConfigurationT24(Configuration.TEST_ENDPOINT_URI, Configuration.TEST_COMPANY, Configuration.TEST_USERNAME, Configuration.TEST_PASSWORD, Configuration.TEST_T24_DATE));
	}
	
	@Before
	public void setup() {
		try {
			
			ExtendedOClientBehaviour behaviour = new ExtendedOClientBehaviour();
			consumer = createODataConsumer(behaviour);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSecureDataServiceAccessSuccess() {
		    List<OEntity> entities = consumer.getEntities(BASE_ENTITY).execute().toList();
			assertNotNull(entities);
			OEntity entity = entities.get(0);
			System.out.println("Customer Key : " + entity.getEntityKey().toString());
	}

	@Test
	public void testAuthenticationFailure() {
        try {
    		ODataConsumer oc = ODataJerseyConsumer.newBuilder(config.getEndpointUri()).setClientBehaviors(
    						OClientBehaviors.basicAuth(
    								getTestUsername(Configuration.TEST_USERNAME), "BADBADPASSWORD")).build();
		    oc.getEntity(BASE_ENTITY, BASE_ENTRY).execute();
        	fail("Should not have got here");
		} catch(RuntimeException re) {
			assertTrue(re.getMessage().contains("401"));
        }
	}
}

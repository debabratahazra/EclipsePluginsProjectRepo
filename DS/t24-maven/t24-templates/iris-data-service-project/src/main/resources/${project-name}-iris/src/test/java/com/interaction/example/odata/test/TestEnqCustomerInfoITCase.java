package com.interaction.example.odata.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.odata4j.core.OEntity;

import com.temenos.interaction.testfx.AbstractInteractionTest;
import com.temenos.interaction.testfx.t24.ConfigurationT24;

public class TestEnqCustomerInfoITCase extends AbstractInteractionTest {
	private final static String testEnquiry = "enqCustomerInfos";

	public TestEnqCustomerInfoITCase() {
		super(new ConfigurationT24(Configuration.TEST_ENDPOINT_URI, Configuration.TEST_COMPANY, Configuration.TEST_USERNAME, Configuration.TEST_PASSWORD, Configuration.TEST_T24_DATE));
	}

	@Test
	public void testForFilterWhichMatchesAllEntities() {
		List<OEntity> entities = getEntitiesAfterSuccess(testEnquiry, "?$filter=CusNo%20eq%20100106");
		assertEquals(1, entities.size());
		
		assertEquals("Industry", entities.get(0).getProperties().get(2).getName());
		assertEquals("1000", entities.get(0).getProperties().get(2).getValue());
		
		assertNotNull(entities.get(0).getProperty("enqCustomerInfo_ShortNameMvGroup"));
	}
	
	@Test
	public void testEnqForZeroRecords() {
		// empty select for enquiry containing a zero record field
		List<OEntity> entities = getEntitiesAfterSuccess(testEnquiry, "?$filter=CusNo%20eq%20foo");
		assertEquals(0, entities.size());
		assertEquals(200,getStatusCodeAfterFailure(testEnquiry, "?$filter=CusNo%20eq%20foo"));
	}
	
}

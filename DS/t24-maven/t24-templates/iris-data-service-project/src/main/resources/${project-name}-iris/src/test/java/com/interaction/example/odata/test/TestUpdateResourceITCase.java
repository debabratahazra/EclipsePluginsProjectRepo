package com.interaction.example.odata.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.apache.abdera.model.Entry;
import org.junit.Before;
import org.junit.Test;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.core.OEntity;

import com.temenos.interaction.testfx.ExtendedOClientBehaviour;
import com.temenos.interaction.testfx.t24.ConfigurationT24;

/**
 * Test cases to verify T24 updates on cached and non-cached resources. 
 */
public class TestUpdateResourceITCase extends AbstractT24InteractionTest {
	
	// Initialisation
	private String customerCode1;
	private String customerCode2;

	private final static String CUSTOMER_INPUT_ENTITY_SET = "verCustomer_Inputs";
	private final static String CUSTOMER_INPUT_ENTITY = "verCustomer_Input";
	private final static String CUSTOMER_INPUT_ID_FIELD = "CustomerCode";
	
	public TestUpdateResourceITCase() {
		super(new ConfigurationT24(Configuration.TEST_ENDPOINT_URI, Configuration.TEST_COMPANY, Configuration.TEST_USERNAME, Configuration.TEST_PASSWORD, Configuration.TEST_T24_DATE));
	}
	
	@Before
	public void setup() {
		try {
			customerCode1 = createNewAuthorisedT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, CUSTOMER_INPUT_ID_FIELD); 
			customerCode2 = createNewAuthorisedT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, CUSTOMER_INPUT_ID_FIELD); 
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Callback function to populate a new T24 entity
	 */
	@Override
	protected void populateNewT24Entity(Entry entry, String id) {
		//Populate new deal
		Map<String, String> testValues = new HashMap<String, String>();
		testValues.put("Mnemonic", "C" + id);
		testValues.put("verCustomer_Input_Name1MvGroup.element.Name1", "Mr  Robin Peterson" + id);
		testValues.put("verCustomer_Input_ShortNameMvGroup.element.ShortName", "Rob" + id);
		testValues.put("Sector", "1001");
		testValues.put("Gender", "MALE");
		testValues.put("Title", "MR");
		testValues.put("FamilyName", "Peterson" + id);
		populateEntryProperties(entry, testValues);
	}
	
	private OEntity modifyCustomer(OEntity entity, String gender, String title) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("Gender", gender);
		values.put("Title", title);

		return modifyEntity(entity, values);
	}
	
	
	@Test
	public void getCustomerWithEtag() {
		ExtendedOClientBehaviour behaviour = new ExtendedOClientBehaviour();
		ODataConsumer consumer = createODataConsumer(behaviour);
		
		OEntity customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		String etag1 = behaviour.getEtag();		//The odata4j jersey consumer does not appear to support etags
		assertTrue(etag1.matches("\"\\S+\"")); 
		assertEquals(customerCode1, customer1.getProperty("CustomerCode").getValue());

		OEntity customer2 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode2).execute();
		String etag2 = behaviour.getEtag();
		assertTrue(etag2.matches("\"\\S+\"")); 
		assertEquals(customerCode2, customer2.getProperty("CustomerCode").getValue());
		
		assertFalse(etag1.equals(etag2));
	}

	@Test
	public void getCustomerWithEtagNotModified() {
		ExtendedOClientBehaviour behaviour = new ExtendedOClientBehaviour();
		ODataConsumer consumer = createODataConsumer(behaviour);
		
		OEntity customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		String etagCustomer1 = behaviour.getEtag();
		assertTrue(etagCustomer1.matches("\"\\S+\"")); 
		assertEquals(customerCode1, customer1.getProperty("CustomerCode").getValue());

		behaviour.setIfNoneMatch(etagCustomer1);
		try {
			consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
			fail("Should have thrown a 304 Not modified exception.");
		}
		catch(RuntimeException re) {
			assertTrue(re.getMessage().contains(Status.NOT_MODIFIED.getReasonPhrase()));
		}
	}

	@Test
	public void updateCustomer() throws Exception {
		ExtendedOClientBehaviour behaviour = new ExtendedOClientBehaviour();
		ODataConsumer consumer = createODataConsumer(behaviour);
		
		//Get customer
		OEntity customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		String originalGender = (String) customer1.getProperty("Gender").getValue();
		String originalTitle = (String) customer1.getProperty("Title").getValue();
		String etagCustomer1 = behaviour.getEtag();
		assertTrue(etagCustomer1.matches("\"\\S+\"")); 
		assertEquals("MALE", customer1.getProperty("Gender").getValue());

		//Modify customer gender to female
    	OEntity entity = modifyCustomer(customer1, "FEMALE", "MS");
    	behaviour.setIfMatch(etagCustomer1);
		consumer.updateEntity(entity).execute();
		authoriseT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, customerCode1);			//authorise change
		
		//Check customer has been modified
		customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		assertTrue(behaviour.getEtag().matches("\"\\S+\"")); 
		assertFalse(behaviour.getEtag().equals(etagCustomer1)); 
		assertEquals("FEMALE", customer1.getProperty("Gender").getValue());

		//Undo changes
    	entity = modifyCustomer(customer1, originalGender, originalTitle);
    	behaviour.setIfMatch(behaviour.getEtag());
		consumer.updateEntity(entity).execute();
		authoriseT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, customerCode1);

		//Check all changes have been undone
		customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		assertTrue(behaviour.getEtag().matches("\"\\S+\"")); 
		assertFalse(behaviour.getEtag().equals(etagCustomer1)); 
		assertEquals("MALE", customer1.getProperty("Gender").getValue());
	}
	
	@Test
	public void updateCachedCustomerSuccess() throws Exception {
		ExtendedOClientBehaviour behaviour = new ExtendedOClientBehaviour();
		ODataConsumer consumer = createODataConsumer(behaviour);
		
		//Get customer
		OEntity customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		String originalGender = (String) customer1.getProperty("Gender").getValue();
		String originalTitle = (String) customer1.getProperty("Title").getValue();
		String etagCustomer1 = behaviour.getEtag();
		assertTrue(etagCustomer1.matches("\"\\S+\"")); 
		assertEquals("MALE", customer1.getProperty("Gender").getValue());

		//Modify customer gender to female
    	OEntity entity = modifyCustomer(customer1, "FEMALE", "MS");
		behaviour.setIfMatch(etagCustomer1);				//Set If-Match header
		consumer.updateEntity(entity).execute();
		authoriseT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, customerCode1);

		//Check customer has been modified
		customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		assertTrue(behaviour.getEtag().matches("\"\\S+\"")); 
		assertEquals("FEMALE", customer1.getProperty("Gender").getValue());

		//Undo changes
    	entity = modifyCustomer(customer1, originalGender, originalTitle);
		behaviour.setIfMatch(behaviour.getEtag());
		consumer.updateEntity(entity).execute();
		authoriseT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, customerCode1);
	}
	
	@Test
	public void updateCachedCustomerConflict() throws Exception {
		ExtendedOClientBehaviour behaviour = new ExtendedOClientBehaviour();
		ODataConsumer consumer = createODataConsumer(behaviour);
		
		//Get customer
		OEntity customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
		String originalGender = (String) customer1.getProperty("Gender").getValue();
		String originalTitle = (String) customer1.getProperty("Title").getValue();
		String etagCustomer1 = behaviour.getEtag();
		assertTrue(etagCustomer1.matches("\"\\S+\"")); 
		assertEquals("MALE", customer1.getProperty("Gender").getValue());

		//Simulate someone else modifying this customer
    	OEntity entity = modifyCustomer(customer1, "FEMALE", "MS");
    	behaviour.setIfMatch(etagCustomer1);
		consumer.updateEntity(entity).execute();
		authoriseT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, customerCode1);

		//Modify customer gender to female
    	entity = modifyCustomer(customer1, "FEMALE", "MRS");
		try {
	    	behaviour.setIfMatch(etagCustomer1);				//Set If-Match header
			consumer.updateEntity(entity).execute();
			fail("Should have thrown a 412 Conflict exception.");
		}
		catch(RuntimeException re) {
			assertTrue(re.getMessage().contains(Status.PRECONDITION_FAILED.getReasonPhrase()));
		}
		finally {
			//Undo changes
			customer1 = consumer.getEntity(CUSTOMER_INPUT_ENTITY_SET, customerCode1).execute();
			etagCustomer1 = behaviour.getEtag();
	    	entity = modifyCustomer(customer1, originalGender, originalTitle);
			behaviour.setIfMatch(etagCustomer1);
			consumer.updateEntity(entity).execute();
			authoriseT24Entity(CUSTOMER_INPUT_ENTITY_SET, CUSTOMER_INPUT_ENTITY, customerCode1);
		}
	}
}

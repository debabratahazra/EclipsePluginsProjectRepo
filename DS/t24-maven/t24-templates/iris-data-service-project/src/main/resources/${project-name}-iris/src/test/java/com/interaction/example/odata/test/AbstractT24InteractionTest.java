package com.interaction.example.odata.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;

import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;
import org.apache.abdera.util.EntityTag;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.odata4j.core.OCollection;
import org.odata4j.core.OComplexObject;
import org.odata4j.core.OEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.interaction.testfx.AbstractInteractionTest;
import com.temenos.interaction.testfx.Configuration;

/**
 * This abstract class provides helper functions to perform T24 related operations. 
 */
public abstract class AbstractT24InteractionTest extends AbstractInteractionTest {
	private final static Logger logger = LoggerFactory.getLogger(AbstractT24InteractionTest.class);

	private final static String TEST_T24_TODAYS_DATE_KEY = "TEST_T24_DATE";
	private final static String TEST_AUTHORISER_USERNAME = "SSOUSER2";
	private final static String TEST_AUTHORISER_PASSWORD = "123456";
	
	public AbstractT24InteractionTest(Configuration config) {
		super(config);
	}
	
	public static String getTestAuthoriserUsername(String defaultUsername) {
		String username = defaultUsername;
		if (System.getProperty(TEST_AUTHORISER_USERNAME) != null)
			username = System.getProperty(TEST_AUTHORISER_USERNAME);
		assert(username != null);
		return username;
	}

	public static String getTestAuthoriserPassword(String defaultPassword) {
		String password = defaultPassword;
		if (System.getProperty(TEST_AUTHORISER_PASSWORD) != null)
			password = System.getProperty(TEST_AUTHORISER_PASSWORD);
		assert(password != null);
		return password;
	}

	/**
	 * Populate a new T24 entity
	 * @param entry entry
	 * @param id id value
	 */
	protected void populateNewT24Entity(Entry entry, String id) {
	}
	
	/**
	 * Return the T24 date
	 * @param defaultDate
	 * @return t24 date
	 */
	public static String getT24TodaysDate(String defaultDate) {
		String date = defaultDate;
		if (System.getProperty(TEST_T24_TODAYS_DATE_KEY) != null)
			date = System.getProperty(TEST_T24_TODAYS_DATE_KEY);
		assert(date != null);
		return date;
	}
	
	/**
	 * Create a new authorised T24 entity
	 * @param entitySetName entity set name
	 * @param idFieldName id field name
	 * @return id of new entity
	 * @throws Exception
	 */
	protected String createNewAuthorisedT24Entity(String entitySetName, String entityName, String idFieldName) throws Exception {
		logger.info("Creating a new entry");
		
		//Create a new entity
		Entry entry = newT24Entity(entitySetName, entityName, idFieldName);
		String id = getEntryElement(entry, idFieldName).getText();
		
		//Authorise the T24 entity
		Link authoriseLink = entry.getLink("http://temenostech.temenos.com/rels/authorise");
		assertNotNull(authoriseLink);
		authoriseT24Entity(authoriseLink.getHref().toString(), entry.getDocument().getEntityTag());

		//Make sure IAuth entry does not exist anymore
		String baseUri = getTestEndpointUri(config.getEndpointUri());
		RequestOptions opts = new RequestOptions();
		opts.setAccept("application/atom+xml");
		ClientResponse res = getAbderaClient().get(baseUri + getUnauthorisedEntityHref(entitySetName, id), opts);
		int status = res.getStatus();
		res.release();
		if(status != 404) {
			Link deleteLink = entry.getLink("http://temenostech.temenos.com/rels/delete");
			deleteEntity(deleteLink.getHref().toString(), entry.getDocument().getEntityTag());
			fail("Unauthorised record still exists: [" + getUnauthorisedEntityHref(entitySetName, id) + "] returned [" + status + "]." );
		}
		return id;
	}
	
	/**
	 * Create a new T24 entity
	 * @param entitySetName entity set name
	 * @param idFieldName id field name
	 * @return new entry
	 * @throws Exception
	 */
	protected Entry newT24Entity(String entitySetName, String entityName, String idFieldName) throws Exception {
		return this.newT24Entity(entitySetName, entityName, idFieldName, null);
	}
	
	/**
	 * Create a new T24 entity
	 * @param entitySetName entity set name
	 * @param idFieldName id field name
	 * @param params query parameters
	 * @return new entry
	 * @throws Exception
	 */
	protected Entry newT24Entity(String entitySetName, String entityName, String idFieldName, NameValuePair[] params) throws Exception {
		//Obtain a new entry
		Entry entry = getNewBlankT24Entity(entitySetName, params);
		String id = getEntryElement(entry, idFieldName).getText();

		// determine the url for the next action i.e. an 'INPUT' action
		List<Link> links = entry.getLinks("http://temenostech.temenos.com/rels/input");
		assertEquals("Just one input link", 1, links.size());
		String inputLink = links.get(0).getHref().toString();
		assertNotNull("Link to use to input new deal", inputLink);
        
		//Populate new deal
		populateNewT24Entity(entry, id);

		// Post the 'new' request
		String uri = getTestEndpointUri(config.getEndpointUri()) + inputLink;
		RequestOptions opts = new RequestOptions();
		opts.setContentType("application/atom+xml;type=entry");
		Entry resultEntry = makePostRequestForEntry(getAbderaClient(), uri, entry, opts);
		return resultEntry;
	}

	/**
	 * Generate a new blank T24 entity with default values.
	 * This method will not commit the new entity.
	 * @param entitySetName entity set name
	 * @return new entity
	 * @throws Exception
	 */
	protected Entry getNewBlankT24Entity(String entitySetName) throws Exception {
		return this.getNewBlankT24Entity(entitySetName, null);
	}
	
	/**
	 * Generate a new blank T24 entity with default values.
	 * This method will not commit the new entity.
	 * @param entitySetName entity set name
	 * @param params query parameters
	 * @return new entity
	 * @throws Exception
	 */
	protected Entry getNewBlankT24Entity(String entitySetName, NameValuePair[] params) throws Exception {
		String baseUri = getTestEndpointUri(config.getEndpointUri());

		// Obtain a new entry
		PostMethod method = new PostMethod(baseUri + entitySetName + "()/new");
		method.setRequestEntity(new StringRequestEntity("", "application/atom+xml", "UTF-8"));
		if(params != null) {
			method.setQueryString(params);
		}
		return makePostRequestForEntry(getHttpClient(), method);
	}
	
	/**
	 * Authorise a T24 entity
	 * @param id id value of T24 entity
	 * @throws Exception
	 */
	protected void authoriseT24Entity(String entitySetName, String entityName, String id) throws Exception {
		//Get the unauthorised entry
		Document<Entry> unauthorisedEntry = getEntity(getUnauthorisedEntityHref(entitySetName, id));
		assertNotNull(unauthorisedEntry);
		
		//Authorise the entity
		Link authoriseLink = unauthorisedEntry.getRoot().getLink("http://temenostech.temenos.com/rels/authorise");
		assertNotNull(authoriseLink);
		authoriseT24Entity(authoriseLink.getHref().toString(), unauthorisedEntry.getEntityTag());
	}
	
	/**
	 * Authorise a T24 entity
	 * @param href link to authorise the resource
	 * @param etag etag
	 * @throws Exception
	 */
	protected void authoriseT24Entity(String href, EntityTag etag) throws Exception {
		//Release the current abdera client to ensure it re-connects after switching user
		releaseAbderaClient();
		
		String baseUri = getTestEndpointUri(config.getEndpointUri());
		AbderaClient abClient = createAbderaClient(getTestAuthoriserUsername(TEST_AUTHORISER_USERNAME), getTestAuthoriserPassword(TEST_AUTHORISER_PASSWORD));
		RequestOptions opts = new RequestOptions();
		opts.setAccept("application/atom+xml");
		opts.setContentType("application/atom+xml;type=entry");
		if(etag != null) {
			opts.setIfMatch(etag);
		}
		ClientResponse res = abClient.put(baseUri + href, (RequestEntity) null, opts);
		assertEquals(200, res.getStatus());
		res.release();
	}

	/**
	 * Check if an entity contains the specified T24 errors
	 * @param entity oentity
	 * @param errorsPropertyName property name of the complex type holding the T24 errors (e.g. Errors_ErrorsMvGroup)
	 * @param expectedErrorCount expected number of errors
	 * @param expectedErrors array of expected errors. Each error should be an array of 4 elements with [Code, Type, Text, Info]
	 * @return true if all expected errors are contained in this entity, false otherwise
	 */
	@SuppressWarnings("unchecked")
	protected boolean checkT24Error(OEntity entity, String errorsPropertyName, int expectedErrorCount, String[]... expectedErrors) {
		OCollection<OComplexObject> errors;
		try {
			errors = (OCollection<OComplexObject>) entity.getProperty(errorsPropertyName).getValue();
			if(errors.size() != expectedErrorCount) {
				logger.error("Received unexpected error count [" + errors.size() + "].");
				return false;
			}
		}
		catch(Exception e) {
			return expectedErrorCount == 0 && expectedErrors.length == 0;
		}
		for(String[] expectedError : expectedErrors) {
			Iterator<OComplexObject> it = errors.iterator();
			boolean found = false;
			while(it.hasNext() && !found) {
				OComplexObject errorItem = it.next();
				found = 
						expectedError[0].equals(errorItem.getProperty("Code").getValue()) &&
						expectedError[1].equals(errorItem.getProperty("Type").getValue()) &&
						errorItem.getProperty("Text").getValue().toString().contains(expectedError[2]) &&
						expectedError[3].equals(errorItem.getProperty("Info").getValue());
			}
			if(found == false) {
				logger.error("Expected error [Code: " + expectedError[0] + ", Type: " + expectedError[1] + ", Text: " + expectedError[2] + ", Info: " + expectedError[3] + "] not found in entity [" + errors.toString() + "].");
				return false;
			}
		}
		return true;		
	}
	
	/**
	 * Return the href to the unauthorised entity
	 * @param entitySetName entity set name
	 * @param id id value
	 * @return href to unauthorised entity resource
	 */
	protected String getUnauthorisedEntityHref(String entitySetName, String id) {
		return entitySetName + "IAuth('" + id + "')";
	}
}

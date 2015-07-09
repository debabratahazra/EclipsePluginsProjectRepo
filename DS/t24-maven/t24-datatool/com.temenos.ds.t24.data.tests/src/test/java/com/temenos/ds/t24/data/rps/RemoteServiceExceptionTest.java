package com.temenos.ds.t24.data.rps;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.temenos.ds.t24.data.eson.T24ResourceProviderFacade;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;
import com.temenos.ds.t24.data.util.Execution;
import com.temenos.ds.t24.data.util.SafeIntegrationTestExecutor;

/**
 * @author yandenmatten
 */
public class RemoteServiceExceptionTest {

	private static Logger LOGGER = LoggerFactory.getLogger(RemoteServiceExceptionTest.class);

	@Before
	public void restoreData() throws IOException, RemoteServiceException {
		restoreData("Some Initial Value");
	}

	@Test
	public void testAllHints() {
		for (String[] errorMessageAndHint: RemoteServiceException.ERROR_MESSAGES_AND_HINTS) {
			RemoteServiceException remoteServiceException = new RemoteServiceException("some message containing " + errorMessageAndHint[0]);
			Assert.assertTrue(remoteServiceException.getMessage().contains(errorMessageAndHint[1]));
		}
	}
	
	@Test
	public void testProvokeErrorWithHint_NoDataChanged_0() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			try {
				facade.connectTAFC("/tafc-integrationtest-server.properties");
				GetResourceResponse record = facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				facade.writeRecord(record, IntegrationTestData.CUSTOMER_ID2);
				Assert.fail("The test should produce an exception, hence never reach here.");
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[0][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[0][1]));
				return;
			} finally {
				facade.close();
			}
		}});
	}

	@Test
	public void testProvokeErrorWithHint_Etag_1() throws IOException, RemoteServiceException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws RemoteServiceException, IOException {
			T24ResourceProviderFacade facade1 = new T24ResourceProviderFacade();
			GetResourceResponse record;
			try {
				facade1.connectTAFC("/tafc-integrationtest-server.properties");
				record = facade1.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
			} finally {
				facade1.close();
			}
			
			T24ResourceProviderFacade facade2 = new T24ResourceProviderFacade();
			try {
				facade2.connectTAFC("/tafc-integrationtest-server.properties");
				facade2.writeRecord(record, IntegrationTestData.CUSTOMER_ID2);
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[1][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[1][1]));
				return;
			} finally {
				facade2.close();
			}
		}});
	}

	@Test
	public void testProvokeErrorWithHint_LeadingMinus_2() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			try {
				facade.connectTAFC("/tafc-integrationtest-server.properties");
				GetResourceResponse record = facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				RemoteServiceTestUtil.alterField(record, "-data with leading minus");
				facade.writeRecord(record, IntegrationTestData.CUSTOMER_ID2);
				Assert.fail("The test should produce an exception, hence never reach here.");
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[2][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[2][1]));
				return;
			} finally {
				facade.close();
			}
		}});
	}

	@Test
	public void testProvokeErrorWithHint_Override_3() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			final String TABLE = IntegrationTestData.CUSTOMER_TABLE;
			final String INITIAL_RECORD_ID = "101001"; // Id of a record with override
			final String FINAL_RECORD_ID = "101006"; // must be non existant
			final String NEW_MNEMONIC = "DSMNEM3"; // must be new
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			facade.setAutoOverride(false);
			GetResourceResponse record;
			try {
				try {
					facade.connectTAFC("/tafc-integrationtest-server.properties");
					record = facade.readRecord(TABLE, INITIAL_RECORD_ID);
					RemoteServiceTestUtil.toggleFieldValue(record, "COUNTRY", "SWITZERLAND", "GREAT BRITAIN");
					RemoteServiceTestUtil.alterField(record, "MNEMONIC", NEW_MNEMONIC);
					facade.resetContext(); // Reset the context to fix the etag issue
				} catch (Exception e) {
					throw new IllegalStateException("Shouldn't fail during these steps", e);
				}
				facade.writeRecord(record, FINAL_RECORD_ID);
				throw new IllegalStateException("The tool should produce an exception, hence never reach here. " +
						"This means the initial record had no override or the final record was already in the DB and authorised.");
			} catch (RemoteServiceException rse) {
				if (rse.getMessage().contains("Response Type = OVERRIDE")) {
					Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
							rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[3][0]));
					Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
							rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[3][1]));
					return;
				} else {
					throw new IllegalStateException("The write failed for a different issue than the OVERRIDE problem.", rse);
				}
			} finally {
				facade.close();
			}
		}});
	}
	
	// Creates a record with OVERRIDE
	public static void main(String[] args) throws IOException {
		final String TABLE = IntegrationTestData.CUSTOMER_TABLE;
		final String INITIAL_RECORD_ID = "101001"; // Id of a record with override
		final String FINAL_RECORD_ID = "101005"; // must be non existant
		final String NEW_MNEMONIC = "DSMNEM3"; // must be new
		T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
		facade.setAutoOverride(false);
		GetResourceResponse record;
		try {
			try {
				facade.connectTAFC("/tafc-integrationtest-server.properties");
				record = facade.readRecord(TABLE, INITIAL_RECORD_ID);
				RemoteServiceTestUtil.toggleFieldValue(record, "COUNTRY", "SWITZERLAND", "GREAT BRITAIN");
				RemoteServiceTestUtil.alterField(record, "MNEMONIC", NEW_MNEMONIC);
				facade.resetContext(); // Reset the context to fix the etag issue
			} catch (Exception e) {
				throw new IllegalStateException("Shouldn't fail during these steps");
			}
			facade.writeRecord(record, FINAL_RECORD_ID);
			throw new IllegalStateException("The tool should produce an exception, hence never reach here. " +
					"This means the initial record had no override or the final record was already in the DB and authorised.");
		} catch (RemoteServiceException rse) {
			if (rse.getMessage().contains("Response Type = OVERRIDE")) {
				System.out.println("The record " + FINAL_RECORD_ID + " of " + TABLE + " has been created successfully.");
				System.out.println(rse.getMessage());
			} else {
				rse.printStackTrace();
			}
			return;
		} finally {
			facade.close();
		}
	}

	@Test
	public void testProvokeErrorWithHint_NAURECexist_4() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			try {
				facade.connectTAFC("/tafc-integrationtest-server.properties");
				GetResourceResponse record = facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				RemoteServiceTestUtil.alterField(record, "Some new value");
				facade.writeRecord(record, IntegrationTestData.CUSTOMER_ID2);
			} catch (RemoteServiceException e) {
				// Ignore
			} finally {
				facade.close();
			}
			try {
				facade.connectTAFC("/tafc-integrationtest-server.properties");
				GetResourceResponse record = facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				RemoteServiceTestUtil.alterField(record, "Some other new value");
				facade.writeRecord(record, IntegrationTestData.CUSTOMER_ID2);
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[4][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[4][1]));
				return;
			} finally {
				facade.close();
			}
		}});
	}

	
	@Test
	public void testProvokeErrorWithHint_InvalidUser_5() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			try {
				facade.connectTAFC("/tafc-integrationtest-server-invalid-user.properties");
				facade.authorizeRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				Assert.fail("The test should produce an exception, hence never reach here.");
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[5][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[5][1]));
				return;
			} finally {
				facade.close();
			}
		}});
	}
	
	@Test
	public void testProvokeErrorWithHint_AlreadyAuthorizedRecord_6() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			try {
				facade.connectTAFC("/tafc-integrationtest-server.properties");
				facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				facade.authorizeRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				Assert.fail("The test should produce an exception, hence never reach here.");
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[6][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[6][1]));
				return;
			} finally {
				facade.close();
			}
		}});
	}

	@Test
	public void testProvokeErrorWithHint_InvalidPassword_7() throws IOException, InvocationTargetException, AssertionError {
		new SafeIntegrationTestExecutor().execute(new Execution() { public void execute() throws IOException {
			T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
			try {
				facade.connectTAFC("/tafc-integrationtest-server-invalid-password.properties");
				GetResourceResponse readRecord = facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
				Assert.fail("The test should produce an exception, hence never reach here.");
			} catch (RemoteServiceException rse) {
				Assert.assertTrue("The exception should contains the original message (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[7][0]));
				Assert.assertTrue("The exception should contains the appropriate hint (" + rse.getMessage() + ")", 
						rse.getMessage().contains(RemoteServiceException.ERROR_MESSAGES_AND_HINTS[7][1]));
				return;
			} finally {
				facade.close();
			}
		}});
	}
	
	private void restoreData(String value) throws IOException, RemoteServiceException {
		// Restore value (if necessary)
		T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
		try {
			facade.connectTAFC("/tafc-integrationtest-server.properties");
			GetResourceResponse record = facade.readRecord(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
			String oldValue = RemoteServiceTestUtil.alterField(record, value);
			if (!oldValue.equals(value)) {
				facade.writeRecord(record, IntegrationTestData.CUSTOMER_ID2);
			}
		} catch (RemoteServiceException e) {
			System.out.println("Ooups, unable to change value...");
		} finally {
			facade.close();
		}
		
		// Restore auth status (if necessary)
		T24ResourceProviderFacade facade2 = new T24ResourceProviderFacade();
		try {
			facade2.connectTAFC("/tafc-integrationtest-server-authoriser.properties");
			facade2.authorizeRecordSafely(IntegrationTestData.CUSTOMER_TABLE, IntegrationTestData.CUSTOMER_ID2);
		} catch (RemoteServiceException e) {
			// Ignore if no change
			System.out.println("Ooups, unable to authorize...");
		} finally {
			facade2.close();
		}
	}
	
}

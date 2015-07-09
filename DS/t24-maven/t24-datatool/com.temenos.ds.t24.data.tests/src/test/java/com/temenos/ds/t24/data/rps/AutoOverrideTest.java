package com.temenos.ds.t24.data.rps;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import com.temenos.ds.t24.data.eson.T24ResourceProviderFacade;
import com.temenos.ds.t24.data.rps.ResourceProviderService.GetResourceResponse;

/**
 * @author yandenmatten
 */
public class AutoOverrideTest {

	@Test @Ignore // DS-5939
	public void testAutoOverride() throws IOException, RemoteServiceException {
		final String TABLE = IntegrationTestData.CUSTOMER_TABLE;
		final String INITIAL_RECORD_ID = "101001"; // Id of a record with override
		final String FINAL_RECORD_ID = "101007"; // must be non existant
		final String NEW_MNEMONIC = "DSMNEM4"; // must be new
		T24ResourceProviderFacade facade = new T24ResourceProviderFacade();
		facade.setAutoOverride(true);
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
		} finally {
			facade.close();
		}
	}
	

}

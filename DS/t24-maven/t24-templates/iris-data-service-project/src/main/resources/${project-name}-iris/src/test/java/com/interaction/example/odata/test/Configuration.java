package com.interaction.example.odata.test;

public interface Configuration {

	// Secure OData Service URL 
	public static final String TEST_ENDPOINT_URI = "http://127.0.0.1:9089/Test-iris/Test.svc/";
	public static final String TEST_INTERACTION_ROOT_PATH = "root";
	// default test details
	public static final String TEST_COMPANY = "EU0010001";
	public static final String TEST_USERNAME = "SSOUSER1";
	public static final String TEST_PASSWORD = "123456";
	public static final String TEST_T24_DATE = "20140422"; // Todays T24 date (see DATE table in T24)
}

package com.odcgroup.aaa.ui.internal.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionInfoTest {

	private static final String SERVER = " server ";
	private static final String DATABASE = " database ";
	private static final String PORT = " 1234 ";
	private static final String CHARSET = " iso_1 ";
	private static final String USERNAME = " username ";
	private static final String PASSWORD = " password ";

	private ConnectionInfo info;
	
	@Before
	public void setUp() throws Exception {
		info = new ConnectionInfo(SERVER, 
				 DATABASE, 
				 PORT, 
				 CHARSET,
				 USERNAME ,
				 PASSWORD);
	}
	
	@Test
	public void testGetServerReturnsTrimmedString() {
		final String expected = "server";
		Assert.assertEquals(expected, info.getServer());
	}

	@Test
	public void testGetDatabaseReturnsTrimmedString() {
		final String expected = "database";
		Assert.assertEquals(expected, info.getDatabase());
	}

	@Test
	public void testGetCharsetReturnsTrimmedString() {
		final String expected = "iso_1";
		Assert.assertEquals(expected, info.getCharset());
	}

	@Test
	public void testGetPortReturnsTrimmedString() {
		final String expected = "1234";
		Assert.assertEquals(expected, info.getPort());
	}

	@Test
	public void testGetUsernameReturnsUmodifiedString() {
		Assert.assertEquals(USERNAME, info.getUsername());
	}

	@Test
	public void testGetPasswordReturnsUmodifiedString() {
		Assert.assertEquals(PASSWORD, info.getPassword());
	}

}

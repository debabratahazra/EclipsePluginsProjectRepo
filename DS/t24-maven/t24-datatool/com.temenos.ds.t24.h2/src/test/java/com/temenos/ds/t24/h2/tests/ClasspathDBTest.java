package com.temenos.ds.t24.h2.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import com.temenos.ds.t24.h2.ClasspathDB;
import com.temenos.ds.t24.h2.DB;

/**
 * Test for ClasspathDB.
 *
 * @author Michael Vorburger
 */
public class ClasspathDBTest {

	@Test
	public void testClasspathDB() throws SQLException, IOException {
		DB db = new ClasspathDB("test/db");
		db.setName("TestDB").setUserName(null).setPassword(null);
		db.start();
		assertTrue(db.isRunning());
		db.stop();
	}

	/**
	 * The use case here is that on an integration test server (e.g. DS pull
	 * requests) there could be several concurrently running builds, who should
	 * have no conflict over a single fixed temporary file name.
	 */
	@Test
	public void testStartTwoDBConcurrently() throws Exception {
		DB firstDB = new ClasspathDB("test/db");
		firstDB.setName("TestDB").setUserName(null).setPassword(null);
		firstDB.start();
		assertTrue(firstDB.isRunning());

		DB secondDB = new ClasspathDB("test/db");
		secondDB.setName("TestDB").setUserName(null).setPassword(null);
		secondDB.start();
		assertTrue(secondDB.isRunning());

		firstDB.stop();
		secondDB.stop();
	}

}

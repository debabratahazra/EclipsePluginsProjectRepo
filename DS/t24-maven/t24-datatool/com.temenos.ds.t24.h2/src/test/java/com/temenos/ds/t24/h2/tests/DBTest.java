package com.temenos.ds.t24.h2.tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import com.temenos.ds.t24.h2.DB;

public class DBTest {

	@Test
	public void testStartDB() throws Exception {
		// TODO Don't hard-code this.. DB must be available as a Maven dependency
		DB db = new DB("/home/mvorburger/dev/DS/BrowserReplacement/base_mb_t24brpdev_34_tafj/h2/", 0);
		assertEquals(0, db.getPort());
		db.start();
		assertTrue(db.getPort() > 0);
		assertTrue(db.isRunning());
		long t = System.currentTimeMillis();
		Connection jdbcConnection = DriverManager.getConnection(db.getJdbcUrl(), "tafj", "tafj");
		// We expect it to be very fast to obtain a connection (because it should be warmed up already)
		assertTrue(System.currentTimeMillis() - t < 1000);
		jdbcConnection.close();
		db.stop();
	}

	/**
	 * The use case here is that in single user local development mode,
	 * a developer may choose to speed things up by leaving a DB running..
	 */
	@Test
	public void testStartDBTwice() throws Exception {
		// TODO change this to use the Maven Artifact, when that's available
		DB firstDB = new DB("/home/mvorburger/dev/DS/BrowserReplacement/base_mb_t24brpdev_34_tafj/h2/", 9092).start();
		assertTrue(firstDB.isRunning());
		DB secondDB = new DB("/home/mvorburger/dev/DS/BrowserReplacement/base_mb_t24brpdev_34_tafj/h2/", 9092).start();
		assertFalse(secondDB.isRunning());
		firstDB.stop();
	}

}

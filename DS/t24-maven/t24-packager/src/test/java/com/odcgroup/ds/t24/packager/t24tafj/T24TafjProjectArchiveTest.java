package com.odcgroup.ds.t24.packager.t24tafj;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class T24TafjProjectArchiveTest {
	
	private static final File FAKE_FILE = new File("fake");

	@Test
	public void testStripVersion() {
		Assert.assertEquals("name.jar", new TafjProjectArchive(FAKE_FILE).stripVersion("name-1.0-SNAPSHOT.jar"));
		Assert.assertEquals("name.jar", new TafjProjectArchive(FAKE_FILE).stripVersion("name-1.0.jar"));
		Assert.assertEquals("splitted-name.jar", new TafjProjectArchive(FAKE_FILE).stripVersion("splitted-name-1.0-SNAPSHOT.jar"));
		Assert.assertEquals("name.jar", new TafjProjectArchive(FAKE_FILE).stripVersion("name.jar"));
		Assert.assertEquals("abc.jar", new TafjProjectArchive(FAKE_FILE).stripVersion("abc-SNAPSHOT.jar"));
		Assert.assertEquals("abc", new TafjProjectArchive(FAKE_FILE).stripVersion("abc-SNAPSHOT"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStripVersionIllegalArg1() {
		new TafjProjectArchive(FAKE_FILE).stripVersion("-1.0.jar");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testStripVersionIllegalArg2() {
		new TafjProjectArchive(FAKE_FILE).stripVersion("-1.0-SNAPSHOT.jar");
	}
	
}

package com.odcgroup.ds.t24.packager.data;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

public class DataHelperTest {
	
	@Test
	public void testGetFilename() throws InvalidDataFilenameException {
		Assert.assertEquals("abc", DataHelper.getFilename("comp/abc!def.d"));
		Assert.assertEquals("abc", DataHelper.getFilename("abc!def.d"));
	}

	@Test
	public void testGetFilenameKO() {
		String[] wrongValues = {
				"comp/abcdef.d",
				"abcdef.d",
				"abc!def",
				"abc",
				"",
				null
		};
		for (String wrongValue: wrongValues) {
			try {
				DataHelper.getFilename(wrongValue);
				throw new RuntimeException("" + wrongValue + " is considered falsly as a correct value.");
			} catch (InvalidDataFilenameException e) {
				// fine, it is expected
			}
		}
	}

	@Test
	public void testGetName() throws InvalidDataFilenameException {
		Assert.assertEquals("def", DataHelper.getName("comp/abc!def.d"));
		Assert.assertEquals("def", DataHelper.getName("abc!def.d"));
	}

	@Test
	public void testGetNameKO() {
		String[] wrongValues = {
				"comp/abcdef.d",
				"abcdef.d",
				"abc!def",
				"abc",
				"",
				null
		};
		for (String wrongValue: wrongValues) {
			try {
				DataHelper.getName(wrongValue);
				throw new RuntimeException("" + wrongValue + " is considered falsly as a correct value.");
			} catch (InvalidDataFilenameException e) {
				// fine, it is expected
			}
		}
	}
	
	@Test
	public void testIsDataFile() {
		Assert.assertTrue("abc", DataHelper.isDataFile(new File("comp/abc!def.d")));
		Assert.assertTrue("abc", DataHelper.isDataFile(new File("abc!def.d")));

		String[] wrongValues = {
				"comp/abcdef.d",
				"abcdef.d",
				"abc!def",
				"abc",
				""
		};
		for (String wrongValue: wrongValues) {
			Assert.assertFalse("" + wrongValue + " is considered falsly as a correct value.", DataHelper.isDataFile(new File(wrongValue)));
		}
	}
	

}

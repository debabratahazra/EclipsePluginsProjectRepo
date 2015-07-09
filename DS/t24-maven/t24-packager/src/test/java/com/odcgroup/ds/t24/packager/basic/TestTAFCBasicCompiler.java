package com.odcgroup.ds.t24.packager.basic;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class TestTAFCBasicCompiler {

	@Test
	public void testExcludeIncludeFiles() {
		File someFile = new File("abc");
		Assert.assertFalse("Should not accept this file", TAFCBasicCompiler.FILENAME_FILTER.accept(someFile, "I_ABC.B"));
		Assert.assertTrue("Should accept this file", TAFCBasicCompiler.FILENAME_FILTER.accept(someFile, "ABC.B"));
		Assert.assertFalse("Should not accept this file", TAFCBasicCompiler.FILENAME_FILTER.accept(someFile, "I_ABC"));
		Assert.assertTrue("Should accept this file", TAFCBasicCompiler.FILENAME_FILTER.accept(someFile, "ABC"));
	}
	
}

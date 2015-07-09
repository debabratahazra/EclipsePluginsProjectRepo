package com.odcgroup.ocs.packager.launcher;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PackagerStreamListenerTest  {
	
	private class SpyMonitor extends NullProgressMonitor {

		int totalWork;
		String lastSubTask;
		
		@Override
		public void subTask(String name) {
			lastSubTask = name;
		}

		@Override
		public void worked(int work) {
			totalWork += work;
		}
	}
	
	private PackagerStreamListener stream;
	private SpyMonitor spyMonitor;
	
	@Before
	public void setUp() {
		spyMonitor = new SpyMonitor();
		stream = new PackagerStreamListener(spyMonitor, 1000);
	}
	
	@Test
	public void testStreamAppended() {
		// Given the stream

		// When
		stream.streamAppended("line1\nline2\nline3\nline4\nline5\n", null);
		
		// Then
		Assert.assertEquals("line5", spyMonitor.lastSubTask);
	}
	
	@Test
	public void testStreamAppendedRemoveInfoPrefix() {
		// Given the stream

		// When
		stream.streamAppended("[INFO] some log", null);
		
		// Then
		Assert.assertEquals("some log", spyMonitor.lastSubTask);
	}
	
	@Test
	public void testStreamAppendedPercentage() {
		// Given the stream

		// When
		for (int i=0; i<10; i++) {
			stream.streamAppended("line1\nline2\nline3\nline4\nline5\n", null);
		}
		
		// Then
		Assert.assertEquals("Should reach 5% (10 times 5 lines with 1000 lines in total", 5, stream.getPercentageDone());
		Assert.assertEquals("Should reach 5% in the monitor too", 5, spyMonitor.totalWork);
	}
	
	@Test
	public void testStreamAppendedPercentageWithoutLastEOL() {
		// Given the stream

		// When
		for (int i=0; i<10; i++) {
			stream.streamAppended("line1\nline2\nline3\nline4\nline5", null);
		}
		
		// Then
		Assert.assertEquals("Should reach 5% (10 times 5 lines with 1000 lines in total", 5, stream.getPercentageDone());
	}
	
	@Test
	public void testStreamAppendedMoreThan100Percents() {
		// Given the stream

		// When
		for (int i=0; i<110; i++) {
			stream.streamAppended("line1\nline2\nline3\nline4\nline5\n" +
					"line6\nline7\nline8\nline9\nline10", null);
		}
		
		// Then
		Assert.assertEquals("Should reach 110%", 110, stream.getPercentageDone());
	}
	
	@Test
	public void testIsBuildSuccessful() {
		// Given the stream

		// When
		stream.streamAppended("some log", null);
		stream.streamAppended(PackagerStreamListener.BUILD_SUCCESS, null);
		stream.streamAppended("some text", null);
		
		// Then
		Assert.assertTrue("The build should be seen as successful", stream.isBuildSuccessful());
	}
	
	@Test
	public void testIsBuildSuccessful_failure() {
		// Given the stream

		// When
		stream.streamAppended("some log", null);
		stream.streamAppended("[INFO] BUILD FAILURE", null);
		stream.streamAppended("some text", null);
		
		// Then
		Assert.assertFalse("The build should be seen as failed", stream.isBuildSuccessful());
	}
	
	@Test
	public void testGetZipPackageGenerated() {
		// Given the stream

		// When
		stream.streamAppended("some log", null);
		stream.streamAppended(PackagerStreamListener.CUSTO_PACKAGE_GENERATED_PREFIX + "somePath/somePackage.zip", null);
		stream.streamAppended("some text", null);
		
		// Then
		Assert.assertEquals("The package name should be extracted from the logs", "somePath/somePackage.zip", stream.getZipPackageGenerated());
	}

	@Test
	public void testGetZipPackageGenerated_NoPackage() {
		// Given the stream

		// When
		stream.streamAppended("some log", null);
		stream.streamAppended("some text", null);
		
		// Then
		Assert.assertNull("The package name should be found", stream.getZipPackageGenerated());
	}

}

package com.odcgroup.ocs.support.installer;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.ocs.support.ui.installer.OcsBinariesExtractionUIFacade;

/**
 * Test Ocs binaries extraction
 * @author yan
 */
public class OcsBinariesExtractionUIFacadeTest  {

	@After
	public void tearDown() {
		OcsBinariesExtractionFacade.INSTANCE = null;
	}
	
	@Test
	public void testCleanUpOldExtraction() throws IOException {
		final File OCS_BINARIES = createTempDir("ocs.binaries");
		OcsBinariesExtractionFacade.INSTANCE = new OcsBinariesExtractionFacade() {
			@Override
			public File getOcsBinariesFolder() {
				return OCS_BINARIES;
			}
		};

		for (String filename : new String[]{
				"ocs.extract.20101212.080000/repo",
				"ocs.extract.20101212.150000/repo",
				"ocs.extract.20101212.120000/repo"}) {
			File extract = new File(OCS_BINARIES, filename);
			extract.mkdirs();
		}
		
		Assert.assertEquals(3, OCS_BINARIES.listFiles().length);
		
		OcsBinariesExtractionUIFacade.instance().cleanUpOldExtraction();
		
		Assert.assertEquals(1, OCS_BINARIES.listFiles().length);
		Assert.assertEquals("ocs.extract.20101212.150000", OCS_BINARIES.listFiles()[0].getName());
	}
	
	private File createTempDir(String name) throws IOException {
		File emptyRepo = File.createTempFile(name, "tmp");
		emptyRepo.delete();
		emptyRepo.mkdirs();
		emptyRepo.deleteOnExit();
		return emptyRepo;
	}
}

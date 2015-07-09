package com.odcgroup.ocs.support.installer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import ch.qos.logback.core.spi.AppenderAttachable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Test Ocs binaries extraction
 * @author yan
 */
public class OcsBinariesExtractionFacadeTest  {

	@After
	public void tearDown() {
		OcsBinariesExtractionFacade.INSTANCE = null;
	}
	
	@Test
	public void testGetOcsBinariesRepositoryFolder_NotExisting() throws IOException {
		final File OCS_BINARIES = createTempDir("ocs.binaries");
		final File NEW_REPO = createTempDir("empty.repo.new");
		OcsBinariesExtractionFacade.INSTANCE = new OcsBinariesExtractionFacade() {
			@Override
			public File generateNewBinariesDestination() {
				return NEW_REPO;
			}

			@Override
			public File getOcsBinariesFolder() {
				return OCS_BINARIES;
			}
		};
		Assert.assertNull("The repository location should be null as it is not existing", 
				OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder());
	}
	
	@Test
	public void testGetOcsBinariesRepositoryFolder_OneExisting() throws IOException {
		final String GET_NEW_BINARIES_DESTINATION_CALLED = "getNewBinariesDestination called";
		final File OCS_BINARIES = createTempDir("ocs.binaries");
		final File NEW_REPO = createTempDir("empty.repo.new");
		final Set<String> result = new HashSet<String>();
		OcsBinariesExtractionFacade.INSTANCE = new OcsBinariesExtractionFacade() {
			@Override
			public File generateNewBinariesDestination() {
				result.add(GET_NEW_BINARIES_DESTINATION_CALLED);
				return NEW_REPO;
			}

			@Override
			public File getOcsBinariesFolder() {
				return OCS_BINARIES;
			}
		};

		File oneRepo = new File(OCS_BINARIES, OcsBinariesExtractionFacade.OCS_EXTRACT_PREFIX + "20101212.121212/" + OcsBinariesExtractionFacade.OCS_EXTRACT_REPO_FOLDER);
		oneRepo.mkdirs();
		
		Assert.assertEquals("The repo should be found", 
				oneRepo, 
				OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder());
		Assert.assertFalse("A new repository location shouldn't be generated", 
				result.contains(GET_NEW_BINARIES_DESTINATION_CALLED));
	}
	
	@Test
	public void testGetOcsBinariesRepositoryFolder_MultipleExisting() throws IOException {
		final File OCS_BINARIES = createTempDir("ocs.binaries");
		OcsBinariesExtractionFacade.INSTANCE = new OcsBinariesExtractionFacade() {
			@Override
			public File getOcsBinariesFolder() {
				return OCS_BINARIES;
			}
		};

		File repoOne = new File(OCS_BINARIES, OcsBinariesExtractionFacade.OCS_EXTRACT_PREFIX + 
				"20101212.121212/" + OcsBinariesExtractionFacade.OCS_EXTRACT_REPO_FOLDER);
		repoOne.mkdirs();
		
		File repoTwo = new File(OCS_BINARIES, OcsBinariesExtractionFacade.OCS_EXTRACT_PREFIX + 
				"20101212.121213/" + OcsBinariesExtractionFacade.OCS_EXTRACT_REPO_FOLDER);
		repoTwo.mkdirs();
		
		File repoThree = new File(OCS_BINARIES,  OcsBinariesExtractionFacade.OCS_EXTRACT_PREFIX + 
				"20101212.121211" + OcsBinariesExtractionFacade.OCS_EXTRACT_REPO_FOLDER);
		repoThree.mkdirs();
		
		Assert.assertEquals("The repo 2 should be found (as it is the most recent one)", 
				repoTwo, 
				OcsBinariesExtractionFacade.instance().getOcsBinariesRepositoryFolder());
	}
	
	@Test
	public void testGenerateNewBinariesDestination() throws ParseException {
		long before = System.currentTimeMillis();
		String newDestinationName = OcsBinariesExtractionFacade.instance().generateNewBinariesDestination().getName();
		long after = System.currentTimeMillis();
		
		Assert.assertTrue(newDestinationName.startsWith("ocs.extract."));
		Date repoDate = OcsBinariesExtractionFacade.EXTRACT_FORMAT.parse(StringUtils.removeStart(newDestinationName, "ocs.extract."));
		
		Assert.assertTrue(before >= repoDate.getTime());
		Assert.assertTrue(repoDate.getTime() <= after);
	}
	
	private File createTempDir(String name) throws IOException {
		File emptyRepo = File.createTempFile(name, "tmp");
		emptyRepo.delete();
		emptyRepo.mkdirs();
		emptyRepo.deleteOnExit();
		return emptyRepo;
	}
	
    @Test
    public void testSlf4jNoNoOpLogger() {
    	Logger eclipseLogger = LoggerFactory.getLogger(OcsBinariesExtractionFacadeTest.class);
    	Assert.assertFalse("Logger not properly configured. NOPLogger means sfl4j couldn't initialize properly and will discard any log.", 
    			eclipseLogger.getClass().getName().equals("org.apache.log4j.spi.NOPLogger"));
    			// !(eclipseLogger instanceof NOPLogger)) doesn't work as all logger implementation inherits from NOPLogger
    	Assert.assertTrue("The logger manipulation done in M2EclipseLoggingHelper will not work. Basically it will break TAP binaries loading", 
    			eclipseLogger.getClass().getName().equals("ch.qos.logback.core.spi.AppenderAttachable"));
    }

}

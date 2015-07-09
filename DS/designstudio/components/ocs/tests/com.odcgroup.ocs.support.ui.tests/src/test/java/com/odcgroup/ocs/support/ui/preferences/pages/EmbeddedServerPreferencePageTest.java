package com.odcgroup.ocs.support.ui.preferences.pages;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ocs.support.installer.OcsBinariesExtractionFacade;

/**
 * @author yan
 */
public class EmbeddedServerPreferencePageTest {

	private static final String SOURCE_ZIP = "source.zip";
	private static final String NON_EXISTANT_FILEPATH = "non-existant-filepath";

	private File tmpFolder;
	private File updateInstallerJar;

	@Before
	public void setUp() throws IOException {
		tmpFolder = File.createTempFile("ocs.extract", "");
		tmpFolder.delete();

		OcsBinariesExtractionFacade.INSTANCE = new OcsBinariesExtractionFacade() {
			public File generateNewBinariesDestination() {
				return tmpFolder;
			};
		};
	}

	@After
	public void tearDown() throws Exception {
		if (updateInstallerJar != null) {
			if(updateInstallerJar.exists()) {
				updateInstallerJar.delete();
			}
		}
	}

	@Test
	public void testDS4123CleanupIfFailed() {
		EmbeddedServerPreferencePage page = new EmbeddedServerPreferencePage() {
			protected boolean extractOcsBinaries(File extractionDestination) {
				// Simulate failure
				return false;
			}
		};

		boolean extractResult = page.extractOcsBinaries();
		Assert.assertFalse("The extraction should have failed", extractResult);
		Assert.assertFalse("The extraction folder should be removed", tmpFolder.exists());
	}

	@Test
	public void testDS4123NoCleanupIfSuccess() {
		EmbeddedServerPreferencePage page = new EmbeddedServerPreferencePage() {
			protected boolean extractOcsBinaries(File extractionDestination) {
				// Simulate success
				return true;
			}
		};

		boolean extractResult = page.extractOcsBinaries();
		Assert.assertTrue("The extraction should have succeeded", extractResult);
		Assert.assertTrue("The extraction folder shouldn't be removed", tmpFolder.exists());
	}

	/**
	 * {@link http://rd.oams.com/browse/DS-4126}
	 */
	@Test
	public void testIsValidOcsDistributionFilepathWithEmptyFilepathReturnsTrue() throws Exception {

		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		boolean validOcsDistributionFilepath = embeddedServerPreferencePage.isValidOcsDistributionFilepath("");
		Assert.assertTrue(validOcsDistributionFilepath);
	}

	/**
	 * {@link http://rd.oams.com/browse/DS-4126}
	 */
	@Test
	public void testIsValidOcsDistributionFilepathWithInvalidFilepathReturnsFalse() throws Exception {
		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		boolean validOcsDistributionFilepath = embeddedServerPreferencePage.isValidOcsDistributionFilepath(NON_EXISTANT_FILEPATH);
		Assert.assertFalse(validOcsDistributionFilepath);
	}

	/**
	 * {@link http://rd.oams.com/browse/DS-4126}
	 */
	@Test
	public void testIsValidOcsDistributionFilepathWithFilepathContainingSourceInItsNameReturnsFalse() throws Exception {
		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		boolean validOcsDistributionFilepath = embeddedServerPreferencePage.isValidOcsDistributionFilepath(SOURCE_ZIP);
		Assert.assertFalse(validOcsDistributionFilepath);
	}


	/**
	 * {@link http://rd.oams.com/browse/DS-4126}
	 */
	@Test
	public void testIsValidOcsDistributionFilepathReturnsFalse() throws Exception {
		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		boolean validOcsDistributionFilepath = embeddedServerPreferencePage.isValidOcsDistributionFilepath(SOURCE_ZIP);
		Assert.assertFalse(validOcsDistributionFilepath);
	}

	/**
	 * {@link http://rd.oams.com/browse/DS-4126}
	 */
	@Test
	public void testIsValidOcsInstallerReturnsTrueWhenFilepathsAreExistingFiles() throws Exception {
		File tempOcsInstallerFile = File.createTempFile(EmbeddedServerPreferencePage.OCS_INSTALLER, "");
		updateInstallerJar = new File(tempOcsInstallerFile.getParent()+"\\"+EmbeddedServerPreferencePage.OCS_INSTALLER);
		updateInstallerJar.createNewFile();

		File tempOcsDistribution = File.createTempFile("TAP-MARKETING_VERSION", ".zip");

		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		boolean validOcsDistributionFilepath = embeddedServerPreferencePage.isValidOcsDistributionFilepath(tempOcsDistribution.getAbsolutePath());
		Assert.assertTrue(validOcsDistributionFilepath);

		tempOcsDistribution.delete();
		tempOcsInstallerFile.delete();


	}

	@Test
	public void testPerformOKReturnsTrueWhenNoFieldsAreUpdated() throws Exception {
		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		boolean performOk = embeddedServerPreferencePage.performOk();
		Assert.assertTrue(performOk);
	}

	@Test
	public void testPerformOKReturnsFalseWhenExtractBinariesReturnsFalse() throws Exception {
		EmbeddedServerPreferencePage embeddedServerPreferencePage = mock(EmbeddedServerPreferencePage.class);
		when(embeddedServerPreferencePage.ocsBinariesExtractionRequired()).thenReturn(true);
		when(embeddedServerPreferencePage.extractOcsBinaries()).thenReturn(false);

		boolean performOk = embeddedServerPreferencePage.performOk();
		Assert.assertFalse(performOk);
	}


	@Test
	public void testPerformOKCallsUpdateM2EclipseConfiguration() throws Exception {

		EmbeddedServerPreferencePage embeddedServerPreferencePage = new EmbeddedServerPreferencePage();
		EmbeddedServerPreferencePage spy = spy(embeddedServerPreferencePage);

		doReturn(true).when(spy).extractOcsBinaries();
		doReturn(true).when(spy).ocsBinariesExtractionRequired();
		doReturn(false).when(spy).updateM2EclipseConfiguration();
		boolean performOk = spy.performOk();
		verify(spy, atLeastOnce()).updateM2EclipseConfiguration();
		Assert.assertTrue(performOk);
	}
	
	@Test
	public void testDS4566PreferenceForEmbededServer_AutomaticalyConfigureMavenToUseCannotBeModified() {
		// Given
		class EmbeddedServerPreferencePageWithHook extends EmbeddedServerPreferencePage {
			boolean superPerformOkCalled;
			@Override
			protected boolean callSuperPerformOk() {
				superPerformOkCalled = true;
				return true;
			}
			@Override
			protected boolean ocsBinariesExtractionRequired() {
				return false;
			}
			
		};
		EmbeddedServerPreferencePageWithHook page = new EmbeddedServerPreferencePageWithHook();
		
		// When
		page.performOk();
		
		// Then
		Assert.assertTrue("superPerformOk() should be called even if no binaries extraction is required", page.superPerformOkCalled);
	}

}

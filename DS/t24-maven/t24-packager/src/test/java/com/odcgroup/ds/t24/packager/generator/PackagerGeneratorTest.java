package com.odcgroup.ds.t24.packager.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.ds.t24.packager.basic.BasicException;
import com.odcgroup.ds.t24.packager.basic.TAFCBasicCompiler;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidationException;
import com.odcgroup.ds.t24.packager.basic.validator.BasicValidator;
import com.odcgroup.ds.t24.packager.data.DataGenerator;
import com.odcgroup.ds.t24.packager.data.InvalidDataFilenameException;
import com.odcgroup.ds.t24.packager.data.TooMuchDataFilesException;
import com.odcgroup.ds.t24.packager.helper.FileHelper;
import com.odcgroup.ds.t24.packager.helper.TarHelper;
import com.odcgroup.ds.t24.packager.helper.ZipHelper;
import com.odcgroup.ds.t24.packager.helper.ZipHelper.Relocate;
import com.odcgroup.ds.t24.packager.log.SysoutPackagerLogger;
import com.odcgroup.ds.t24.packager.t24gen.T24GenProjectArchive;
import com.odcgroup.ds.t24.packager.t24project.T24Project;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchive;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectArchiver;
import com.odcgroup.ds.t24.packager.t24project.T24ProjectPackager;

public class PackagerGeneratorTest {
	
	private static final String T24_GEN_PROJECT = "t24GenProject-1.0-SNAPSHOT-xmlt24i.zip";
	private static final File TEST_TANK = new File(PackagerGeneratorTest.class.getResource("/test-tank").getFile());
	private static final File TEST_TANK_GENERATED = new File(TEST_TANK, "generated");
	private static final File TEST_TANK_ARCHIVES = new File(TEST_TANK, "archives");
	private static final File T24_PROJECT_TEST_ARCHIVE = new File(TEST_TANK_ARCHIVES, "T24ProjectTestArchive-1.0-SNAPSHOT.zip");
	private static final File T24_PROJECT_HIERARCHICAL_DATA_TEST_ARCHIVE = new File(TEST_TANK_ARCHIVES, "T24ProjectHierarchicalDataTestArchive-1.0-SNAPSHOT.zip");
	
	private T24Packager packager;
	private T24ProjectPackager t24ProjectPackager;
	private T24ProjectArchive t24ProjectTestArchive;
	
	private static final PackagerFactory PACKAGER_FACTORY = new PackagerFactory();

	@Before
	public void setUp() throws IOException {
		// Wire the packager
		packager = new T24Packager(PackageTypeEnum.TAFC, "R13", "T24ProjectTestArchive", "123", "WIN", 
				"1.0-SNAPSHOT", TEST_TANK_GENERATED);
		t24ProjectPackager = new T24ProjectPackager();
		t24ProjectPackager.setPackager(packager);
		packager.setT24ProjectPackager(t24ProjectPackager);
		
		t24ProjectTestArchive = new T24ProjectArchive(T24_PROJECT_TEST_ARCHIVE);
		
		// Folder creation
		FileUtils.forceMkdir(TEST_TANK_GENERATED);
	}

	@After
	public void tearDown() throws IOException {
		FileUtils.cleanDirectory(TEST_TANK_GENERATED);
	}

	/**
	 * Validate the T24Project packager behavior:
	 * When process is called, the list of archive to process is emptied
	 */
	@Test
	public void testT24ProjectPackagerEmptyWhenProcessCalled() throws BasicValidationException, BasicException, IOException {
		// Given
		T24ProjectArchive archive = createMockT24ProjectArchive();
		t24ProjectPackager.addT24Project(archive);
		Assert.assertEquals("wrong number of t24 project found in the packager", 
				1, t24ProjectPackager.getT24projectArchives().size());
		
		// When
		t24ProjectPackager.processT24Projects();
		
		// Then
		Assert.assertEquals("wrong number of t24 project found in the packager", 
				0, t24ProjectPackager.getT24projectArchives().size());
	}
	
	@Test
	public void testT24ArchiveExtract() throws IOException {
		// Given
		File allBasicFolder = new File(TEST_TANK_GENERATED, "all-basic");
		File publicBasicFolder = new File(TEST_TANK_GENERATED, "public-basic");
		File dataFolder = new File(TEST_TANK_GENERATED, "data");
		
		// When
		t24ProjectTestArchive.extractAllBasicTo(allBasicFolder);
		t24ProjectTestArchive.extractPublicBasicTo(publicBasicFolder, ZipHelper.RELOCATE_INDENTITY);
		t24ProjectTestArchive.extractDataTo(dataFolder, ZipHelper.RELOCATE_INDENTITY);
		
		// Then
		Assert.assertEquals(12 /*private basic*/ + 2 /*public basic*/, 
				FileHelper.listFiles(new File(allBasicFolder, "T24ProjectTestArchive")).length);
		Assert.assertEquals(2 /*public basic*/, 
				FileHelper.listFiles(new File(publicBasicFolder, "T24ProjectTestArchive")).length);
		Assert.assertEquals(10, FileHelper.listFiles(new File(dataFolder, "T24ProjectTestArchive")).length);
	}
	
	@Test
	public void testT24ProjectPackagerExtractAllArchiveElements() throws BasicValidationException, BasicException, IOException {
		// Given
		t24ProjectPackager.addT24Project(t24ProjectTestArchive);
		
		// When
		t24ProjectPackager.processT24Projects();
		
		// Then
		File tarRootFolder = new File(packager.getPackageWorkingFolder(), "/R13_T24ProjectTestArchive_123_WIN_1.0-SNAPSHOT");
		File bpFolder = new File(packager.getPackageWorkingFolder(), "/R13_T24ProjectTestArchive_123_WIN_1.0-SNAPSHOT/BP");
		Assert.assertTrue("The tar folder has not been extracted: " + tarRootFolder.getAbsolutePath(), 
				tarRootFolder.exists());
		Assert.assertTrue("The BP folder has not been extracted: " + bpFolder.getAbsolutePath(), 
				bpFolder.exists());
		Assert.assertEquals("The tar folder has not been property extracted (" + tarRootFolder.getAbsolutePath() + ")", 
				10 /*data files*/ +1 /*BP folder*/, FileHelper.list(tarRootFolder).length);
		Assert.assertEquals("The BP folder has not been extracted (" + bpFolder.getAbsolutePath() + ")", 
				2, FileHelper.list(bpFolder).length);
	}
	
	@Test
	public void testDataAreFlattenInTar() throws BasicValidationException, BasicException, IOException {
		// Given
		T24ProjectArchive hierachicalDataArchive = new T24ProjectArchive(T24_PROJECT_HIERARCHICAL_DATA_TEST_ARCHIVE);
		t24ProjectPackager.addT24Project(hierachicalDataArchive);
		
		// When
		t24ProjectPackager.processT24Projects();
		
		// Then
		File tarRootFolder = new File(packager.getPackageWorkingFolder(), "/R13_T24ProjectTestArchive_123_WIN_1.0-SNAPSHOT");
		File bpFolder = new File(packager.getPackageWorkingFolder(), "/R13_T24ProjectTestArchive_123_WIN_1.0-SNAPSHOT/BP");
		Assert.assertTrue("The tar folder has not been extracted : " + tarRootFolder.getAbsolutePath(), 
				tarRootFolder.exists());
		Assert.assertFalse("The BP folder has not been extracted : " + bpFolder.getAbsolutePath(), 
				bpFolder.exists());
		Assert.assertEquals("The tar folder has not been property extracted (" + tarRootFolder.getAbsolutePath() + ")", 
				10 /*data files*/, FileHelper.list(tarRootFolder).length);
	}
	
	private T24ProjectArchive createMockT24ProjectArchive() {
		return new T24ProjectArchive(new File("Dummy")) {
			@Override
			public void extractDataTo(File outputFolder, Relocate relocate)
					throws IOException {
				// Do nothing for this test
			}
			@Override
			public void extractPublicBasicTo(File outputFolder,
					Relocate relocate) throws IOException {
				// Do nothing for this test
			}
			@Override
			public void extractAllBasicTo(File outputFolder) throws IOException {
				// Do nothing for this test
			}
		};
	}
	
	/**
	 * Test the behavior if too much files are added to the packager
	 */
	@Test(expected=TooMuchDataFilesException.class)
	public void testMoreThan999FilesInDataGenerator() throws InvalidDataFilenameException, TooMuchDataFilesException {
		DataGenerator dataGenertor = new DataGenerator();
		for (int i=0; i<1000; i++) {
			dataGenertor.addDataFile("F.FileName!Name"+i+".d");
		}
	}

	/**
	 * This test simulate the whole process to generate the package of the ModelBank
	 * @throws T24PackagerException 
	 */
	@Test
	public void testModelbankPackaging() throws IOException, T24PackagerException {
		// Given
		T24Packager packager = createTestPackager("FT_ModelBank");
		
		T24Project project = new T24Project(
				"FT_ModelBank", 
				"1.0-SNAPSHOT", 
				new File(TEST_TANK, "FT_ModelBank"),
				"src/Source/Public",
				"src/Source/Private",
				"src/Data/Public",
				"src/Data/Model",
				new File(TEST_TANK, "target"));

		T24GenProjectArchive t24GenProjectArchive = new T24GenProjectArchive(
				new File(TEST_TANK_ARCHIVES, T24_GEN_PROJECT));
		
		// When
		T24ProjectArchive archive = new T24ProjectArchiver().archive(project);
		packager.addProjectArchive(archive);
		packager.addGenProjectArchive(t24GenProjectArchive);
		
		File generatePackage = packager.generatePackage();
		
		// Then
		List<String> tarEntries = TarHelper.listTarEntries(generatePackage);
		Assert.assertEquals(23, tarEntries.size());
		Assert.assertTrue("Compiled file is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/t24obj/ft_modelbank/jLibDefinition"));
		Assert.assertTrue("Compiled file is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/t24obj/ft_modelbank/lib.el"));
		Assert.assertTrue("Compiled file is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/t24obj/ft_modelbank/lib0.so.2"));
		
		Assert.assertTrue("Basic file is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/BP/E.MB.ACCT.NAME.b"));
		Assert.assertTrue("Basic file is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/BP/E.MB.BUILD.CREDIT.INT.CONDS.b"));
		
		Assert.assertTrue("Version generated by DS is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/ds-generated/FUNDS_TRANSFER,MT103_HP.version.xml"));
		Assert.assertTrue("Enquiry generated by DS is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/ds-generated/test.enquiry.xml"));

		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC001"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC002"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC003"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC004"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC005"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC006"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC007"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC008"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC009"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC010"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC011"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC012"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC013"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC014"));
		Assert.assertTrue("Data record is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/REC015"));
		
		Assert.assertTrue("Data header is missing in the packaged tar", tarEntries.contains("R11_FT_ModelBank_1_WIN_5/R11_FT_ModelBank_1"));

	}
	
	@Test
	public void testDSPacksLocation() {
		Assert.assertEquals("Default location not properly set.", "t24bin/eb_updates", packager.getDetectOsProgramLocation());
		packager.setDetectOsProgramLocation("some_other_location");
		Assert.assertEquals("Location not properly set.", "some_other_location", packager.getDetectOsProgramLocation());
	}

	/**
	 * @return a basic compiler with fake compilation and no basic validation
	 */
	private TAFCBasicCompiler createFakeBasicCompiler() {
		return new TAFCBasicCompiler() {
			@Override
			protected void compile(File sourceFolder, File outputFolder) throws BasicValidationException,
					BasicException {
				// fake compilation only
				File newOutputFolder = new File(outputFolder, "t24obj");
				newOutputFolder.mkdirs();
				File jLibDefinition = new File(newOutputFolder, "jLibDefinition");
				File libEl = new File(newOutputFolder, "lib.el");
				File lib0so2 = new File(newOutputFolder, "lib0.so.2");
				File[] files = new File[] {jLibDefinition, libEl, lib0so2};
				FileOutputStream outputStream = null;
				for (File file: files) {
					try {
						outputStream = new FileOutputStream(file);
						StringReader reader = new StringReader("This is fake content to avoid empty files");
						IOUtils.copy(reader, outputStream);
					} catch (IOException e) {
						throw new BasicException("Unable to (fake) compile...");
					} finally {
						IOUtils.closeQuietly(outputStream);
					}
				}
			}
		};
	}
	
	/**
	 * @return a packager with a fake compilation and no basic validation
	 */
	private T24Packager createTestPackager(String component) throws BasicValidationException,
			BasicException, IOException {
		File packagerOutputFolder = new File(TEST_TANK_GENERATED,
				"packagerProject1/target");
		T24Packager packager = PACKAGER_FACTORY.createPackager(PackageTypeEnum.TAFC, "R11", component,
				"1", "WIN", "5", packagerOutputFolder);
		TAFCBasicCompiler basicCompiler = createFakeBasicCompiler();
		basicCompiler.setPackager(packager);

		basicCompiler.setValidator(new BasicValidator() {
			@Override
			public void validateEnvironnment() throws BasicValidationException {
				// Ignore any validation
			}
		});
		
		packager.setTafcCompiler(basicCompiler);
		
		packager.setLogger(SysoutPackagerLogger.INSTANCE);

		return packager;
	}
	

}

package com.odcgroup.ocs.server.external.ui.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DeployBuilderTest {

	private static final String WINDOWS_TEMP_DIR = System.getProperty("java.io.tmpdir");
	private DeployBuilder deployBuilder;
	
	private List<File> filesToDelete;

	@Before
	public void setUp() throws Exception {
		deployBuilder = new DeployBuilder();
		filesToDelete = new ArrayList<File>();
	}
	
	@After
	public void tearDown() {
		for (File f:filesToDelete) {
			try {
				f.delete();
			} catch (Exception e) {
				// Ignore
			}
		}
		filesToDelete.clear();
	}
	
	@Test
	public void testIsTargetFileUpdateOfSource_ReturnsFalse_IfTargetFileDoesntExists() throws Exception {
		
		File sourceFile = createTempFile();
		File targetFile = createTempFile("nonExistingFileTest");;

		boolean updateFile = deployBuilder.isTargetFileUpdateOfSource(sourceFile, targetFile);

		Assert.assertEquals(false, updateFile);
	}
	
	@Test
	public void testIsTargetFileUpdateOfSource_ReturnsFalse_IfSourceAndTargetAreIdentical() throws Exception {
		
		File sourceFile = createTempFile();
		File targetFile = sourceFile;

		boolean updateFile = deployBuilder.isTargetFileUpdateOfSource(sourceFile, targetFile);

		Assert.assertEquals(false, updateFile);
		
	}
	
	@Test
	public void testIsTargetFileUpdateOfSource_ReturnsFalse_IfTargetIsNewerThatSource() throws Exception {
		
		File olderFile = createTempFile();
		File newerFile = createNewerTempFile(olderFile);

		boolean updateFile = deployBuilder.isTargetFileUpdateOfSource(olderFile, newerFile);

		Assert.assertEquals(false, updateFile);
		
	}
	
	@Test
	public void testIsTargetFileUpdateOfSource_ReturnsTrue_IfTargetIsNewerThatSource() throws Exception {
		
		File olderFile = createTempFile();
		File newerFile = createNewerTempFile(olderFile);

		boolean updateFile = deployBuilder.isTargetFileUpdateOfSource(newerFile, olderFile);

		Assert.assertEquals(true, updateFile);
		
	}

	private File createTempFile() throws IOException {
		return createTempFile("existingFileTest");
	}
		
	private File createTempFile(String fileName) throws IOException {
		File f = File.createTempFile (fileName, "TMP", new File(WINDOWS_TEMP_DIR));
		filesToDelete.add(f);
		return f;
	}
	
	// File system precision might be up to a second depending of the OS.
	// Forces the last modified flag to ensure the unit test stability
	private File createNewerTempFile(File oldFile) throws IOException {
		File newFile = createTempFile();
		newFile.setLastModified(oldFile.lastModified() + 1000);
		return newFile;
	}

}


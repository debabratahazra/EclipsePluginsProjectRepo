package com.odcgroup.ocs.server.embedded.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

public class WuiBlockHelperTest  {
	
	private static final String RESOURCES_FOLDER = "src/test/resources/";
	
	@Test
	public void testGetBlockInfo_InvalidInput() throws IOException {
		Assert.assertNull("Should be null for null parameter", new WuiBlockHelper().getBlockInfo(null));
		
		File nonExistingFile = new File("WuiBlockHelperTest");
		Assert.assertNull("Should be null with not existing file", new WuiBlockHelper().getBlockInfo(nonExistingFile));
		
		File tmpFile = File.createTempFile("testGetBlockInfo", ".tmp");
		tmpFile.deleteOnExit();
		Assert.assertNull("Should be null with empty file", new WuiBlockHelper().getBlockInfo(tmpFile));
	}
	
	@Test
	public void testGetBlockInfo_withFolder() {
		// Given
		File wuiBlockFolder = getResourceFile("wuiBlockFolder");
		Assert.assertTrue("should be a folder", wuiBlockFolder.isDirectory() && wuiBlockFolder.exists());

		// When
		BlockInfo blockInfo = new WuiBlockHelper().getBlockInfo(wuiBlockFolder);
		
		// Then
		Assert.assertNotNull("should return a valid block info", blockInfo);
		Assert.assertEquals("wui block's id not read properly", "http://www.odcgroup.com/wui-block-folder/5.0", blockInfo.getId());
		Assert.assertEquals("wui block's weight not read propertly", 7, blockInfo.getWeight());
	}
	
	@Test
	public void testGetBlockInfo_withZip() {
		// Given
		File wuiBlockZip = getResourceFile("wuiBlock.zip");
		Assert.assertTrue("should be a file", wuiBlockZip.isFile() && wuiBlockZip.exists());

		// When
		BlockInfo blockInfo = new WuiBlockHelper().getBlockInfo(wuiBlockZip);
		
		// Then
		Assert.assertNotNull("should return a valid block info", blockInfo);
		Assert.assertEquals("wui block's id not read properly", "http://www.odcgroup.com/wui-block-zip/5.0", blockInfo.getId());
		Assert.assertEquals("wui block's weight not read propertly", 7, blockInfo.getWeight());
	}
	
	@Test
	public void testGetBlockInfo_withWrongXml() {
		// Given
		File wuiBlockZipWithMalformedBlockXml = getResourceFile("wuiBlockFolderWrongXml");
		Assert.assertTrue(wuiBlockZipWithMalformedBlockXml.exists());
		
		// When
		BlockInfo blockInfo = new WuiBlockHelper().getBlockInfo(wuiBlockZipWithMalformedBlockXml);
		
		// Then
		Assert.assertNull("not well formed wui block should be ignored", blockInfo);
	}
	
	@Test
	public void testGetBlockInfo_withWrongBlockWeight() {
		// Given
		File wuiBlockZipWithMalformedBlockXml = getResourceFile("wuiBlockFolderWrongWeight");
		Assert.assertTrue(wuiBlockZipWithMalformedBlockXml.exists());
		
		// When
		BlockInfo blockInfo = new WuiBlockHelper().getBlockInfo(wuiBlockZipWithMalformedBlockXml);
		
		// Then
		Assert.assertNull("not well formed wui block should be ignored", blockInfo);
	}
	
	@Test
	public void testSort() throws DuplicateWuiBlockIdException {
		// Given
		File blockWeight_05 = getResourceFile("sort/blockWeight_05");
		File blockWeight_10 = getResourceFile("sort/blockWeight_10");
		File blockWeight_15 = getResourceFile("sort/blockWeight_15");
		File blockWeight_20 = getResourceFile("sort/blockWeight_20");
		File blockWeight_25 = getResourceFile("sort/blockWeight_25");
		
		List<File> unsorted = new ArrayList<File>();
		unsorted.add(blockWeight_10);
		unsorted.add(blockWeight_05);
		unsorted.add(blockWeight_20);
		unsorted.add(blockWeight_25);
		unsorted.add(blockWeight_15);
		
		// When
		List<File> sorted = new WuiBlockHelper().sort(unsorted);
		
		// Then
		Assert.assertEquals("blocks not sorted properly", blockWeight_25, sorted.get(0));
		Assert.assertEquals("blocks not sorted properly", blockWeight_20, sorted.get(1));
		Assert.assertEquals("blocks not sorted properly", blockWeight_15, sorted.get(2));
		Assert.assertEquals("blocks not sorted properly", blockWeight_10, sorted.get(3));
		Assert.assertEquals("blocks not sorted properly", blockWeight_05, sorted.get(4));
	}

	@Test
	public void testSort_duplicateWeight() throws DuplicateWuiBlockIdException {
		// Given
		File blockWeight_05 = getResourceFile("sort/blockWeight_05");
		File blockWeight_05b= getResourceFile("sort/blockWeight_05b");
		File blockWeight_15 = getResourceFile("sort/blockWeight_15");
		File blockWeight_20 = getResourceFile("sort/blockWeight_20");
		
		List<File> unsorted = new ArrayList<File>();
		unsorted.add(blockWeight_15);
		unsorted.add(blockWeight_05b);
		unsorted.add(blockWeight_20);
		unsorted.add(blockWeight_05);
		
		// When
		List<File> sorted = new WuiBlockHelper().sort(unsorted);
		
		// Then
		Assert.assertEquals("blocks not sorted properly", blockWeight_20, sorted.get(0));
		Assert.assertEquals("blocks not sorted properly", blockWeight_15, sorted.get(1));
		Assert.assertTrue("blocks not sorted properly", sorted.get(2).equals(blockWeight_05) || sorted.get(2).equals(blockWeight_05b));
		Assert.assertTrue("blocks not sorted properly", sorted.get(3).equals(blockWeight_05) || sorted.get(3).equals(blockWeight_05b));
		Assert.assertNotSame("blocks not sorted properly", sorted.get(3), sorted.get(2));
	}

	@Test
	public void testSort_withNonWuiblock() throws DuplicateWuiBlockIdException {
		// Given
		File blockWeight_05 = getResourceFile("sort/blockWeight_05");
		File blockWeight_10 = getResourceFile("sort/blockWeight_10");
		File blockWeight_15 = getResourceFile("sort/blockWeight_15");
		File someJavaProject = getResourceFile("sort/someJavaProject");
		
		List<File> unsorted = new ArrayList<File>();
		unsorted.add(blockWeight_10);
		unsorted.add(blockWeight_05);
		unsorted.add(someJavaProject);
		unsorted.add(blockWeight_15);
		
		// When
		List<File> sorted = new WuiBlockHelper().sort(unsorted);
		
		// Then
		Assert.assertEquals("blocks not sorted properly", blockWeight_15, sorted.get(0));
		Assert.assertEquals("blocks not sorted properly", blockWeight_10, sorted.get(1));
		Assert.assertEquals("blocks not sorted properly", blockWeight_05, sorted.get(2));
		Assert.assertEquals("blocks not sorted properly", someJavaProject, sorted.get(3));
	}
	
	@Test
	public void testSort_withBrokenBlock() throws DuplicateWuiBlockIdException {
		// Given
		File blockWeight_05 = getResourceFile("sort/blockWeight_05");
		File blockWeight_10 = getResourceFile("sort/blockWeight_10");
		File blockWeight_15 = getResourceFile("sort/blockWeight_15");
		File brokenBlock = getResourceFile("sort/brokenBlock");
		
		List<File> unsorted = new ArrayList<File>();
		unsorted.add(blockWeight_10);
		unsorted.add(blockWeight_05);
		unsorted.add(brokenBlock);
		unsorted.add(blockWeight_15);
		
		// When
		List<File> sorted = new WuiBlockHelper().sort(unsorted);
		
		// Then
		Assert.assertEquals("blocks not sorted properly", blockWeight_15, sorted.get(0));
		Assert.assertEquals("blocks not sorted properly", blockWeight_10, sorted.get(1));
		Assert.assertEquals("blocks not sorted properly", blockWeight_05, sorted.get(2));
		Assert.assertEquals("blocks not sorted properly", brokenBlock, sorted.get(3));
	}
	
	@Test
	public void testSort_duplicateWuiBlockId() {
		// Given
		File blockWeight_05 = getResourceFile("sort/blockWeight_05");
		File blockWeight_05duplicate = getResourceFile("sort/blockWeight_05duplicate");
		List<File> duplicateIdList = new ArrayList<File>();
		duplicateIdList.add(blockWeight_05);
		duplicateIdList.add(blockWeight_05duplicate);
		
		// When
		boolean duplicatedWuiBlockIdDetected = false;
		try {
			new WuiBlockHelper().sort(duplicateIdList);
		} catch (DuplicateWuiBlockIdException e) {
			duplicatedWuiBlockIdDetected = true;
		}
		
		// Then
		Assert.assertTrue("Duplicate id should have been detected", duplicatedWuiBlockIdDetected);
	}
	
	@Test
	public void testSort_noBlock() throws DuplicateWuiBlockIdException {
		// Given
		File brokenBlock = getResourceFile("sort/brokenBlock");
		File someJavaProject = getResourceFile("sort/someJavaProject");
		
		List<File> unsorted = new ArrayList<File>();
		unsorted.add(brokenBlock);
		unsorted.add(someJavaProject);

		// When
		List<File> sorted = new WuiBlockHelper().sort(unsorted);
		
		// Then
		Assert.assertEquals(brokenBlock, sorted.get(0));
		Assert.assertEquals(someJavaProject, sorted.get(1));
	}
	
	private File getResourceFile(String path) {
		URL url = FileLocator.find(OCSServerUIEmbeddedCore.getDefault().getBundle(), new Path(RESOURCES_FOLDER + path), null);
		 File resource;
			try {
				resource = new File(FileLocator.toFileURL(url).toURI());
				if (!resource.exists()) {
					throw new IllegalStateException("resource " + path + " doesn't exist in " + RESOURCES_FOLDER);
				}
				return resource;
			} catch (Exception e) {
				throw new RuntimeException("Unable to find the source folder: " + url, e);
			}
	}

}

package com.odcgroup.ds.t24.packager.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.ds.t24.packager.data.validator.DataHeaderValidator;
import com.odcgroup.ds.t24.packager.generator.PackageTypeEnum;
import com.odcgroup.ds.t24.packager.generator.T24Packager;
import com.odcgroup.ds.t24.packager.generator.T24PackagerException;

public class DataHeaderTest {

	private static final String TEST_PACKAGE = "TestPackage";

	@Test
	public void testDS6254GenerateDataHeaderWithNoRecord() throws IOException, T24PackagerException {
		// Given
		File component = File.createTempFile("DS6254-", "");
		component.delete();
		component.mkdir();
		DataGenerator dataGenerator = new DataGenerator() {
			public String getDataHeaderFilename() {
				return TEST_PACKAGE;
			}
		};
		dataGenerator.setValidator(new DataHeaderValidator());
		T24Packager t24packager = new T24Packager(PackageTypeEnum.TAFC, null, null, null, null, null, null);
		dataGenerator.setPackager(t24packager);
		
		// When
		dataGenerator.processDataFiles(component);
		
		// Then
		File dataHeader = new File(component, TEST_PACKAGE);
		Assert.assertTrue("No data header generated", dataHeader.exists());
		Assert.assertTrue("Empty file generated", FileUtils.readFileToString(dataHeader).length() > 0);
	}
	
	@Test
	public void testDS6341lastCharOfFieldNameAreLostInDataHeaderFile() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InvalidDataFilenameException {
		Assert.assertEquals("RECORD", DataHelper.getName("somefolder/F.TABLE!RECORD.d"));
	}
	
}

package com.odcgroup.ds.t24.packager.helper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class TestTarHelper {
	
	@Test
	public void testCreateTar() throws URISyntaxException, IOException {
		// Given
		URL url = TestTarHelper.class.getResource("/test-tank/test-tar");
		File packageWorkingFolder = new File(url.toURI());
		File packageFile = File.createTempFile("test-tar-", ".tar");
		packageFile.delete();
		packageFile.deleteOnExit();
		
		// When
		TarHelper.createTar(packageFile, packageWorkingFolder);
		
		// Then
		// It doesn't fail
		List<String> tarEntries = TarHelper.listTarEntries(packageFile);
		Assert.assertEquals("Should be readable", 1, tarEntries.size());
	}

}

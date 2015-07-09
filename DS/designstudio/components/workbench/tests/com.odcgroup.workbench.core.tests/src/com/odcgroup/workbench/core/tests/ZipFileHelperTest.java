package com.odcgroup.workbench.core.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.workbench.core.OfsCore;
import com.odcgroup.workbench.core.helper.ZipFileHelper;

/**
 * @author kkr
 */
public class ZipFileHelperTest {

	/**
	 * Test method for {@link com.odcgroup.workbench.core.helper.ZipFileHelper#getSubDirectories(java.util.Enumeration, org.eclipse.core.runtime.Path)}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ZipException 
	 */
	@Test
	public void testGetSubDirectories() throws ZipException, IOException, URISyntaxException {
        final URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
                new Path("resources/test.zip"), null);
        final ZipFile zipFile = new ZipFile(new File(FileLocator.toFileURL(url).toURI()));
        Set<String> subDirs = ZipFileHelper.getSubDirectories(zipFile.entries(), new Path("page"));
        Assert.assertEquals(2, subDirs.size());
        Assert.assertTrue(subDirs.contains("Default"));
        Assert.assertTrue(subDirs.contains("Custo"));
        subDirs = ZipFileHelper.getSubDirectories(zipFile.entries(), new Path("pageflow/com/odcgroup/udp"));
        Assert.assertEquals(2, subDirs.size());
        Assert.assertTrue(subDirs.contains("test"));
        Assert.assertTrue(subDirs.contains("more"));
	}

	/**
	 * Test method for {@link com.odcgroup.workbench.core.helper.ZipFileHelper#getFiles(java.util.Enumeration, org.eclipse.core.runtime.Path)}.
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ZipException 
	 */
	@Test
	public void testGetFiles() throws ZipException, IOException, URISyntaxException {
        final URL url = FileLocator.find(OfsCore.getDefault().getBundle(),
                new Path("resources/test.zip"), null);
        final ZipFile zipFile = new ZipFile(new File(FileLocator.toFileURL(url).toURI()));
        Set<IPath> files = ZipFileHelper.getFiles(zipFile.entries(), new Path("page"));        
        Assert.assertEquals(0, files.size());
        files = ZipFileHelper.getFiles(zipFile.entries(), new Path("pageflow/com/odcgroup/udp/more"));
        Assert.assertEquals(2, files.size());
        Assert.assertTrue(files.contains(new Path("pageflow/com/odcgroup/udp/more/JustOne.pageflow")));
        Assert.assertTrue(files.contains(new Path("pageflow/com/odcgroup/udp/more/something.else")));
        files = ZipFileHelper.getFiles(zipFile.entries(), new Path("pageflow/com/odcgroup/udp/test"));        
        Assert.assertEquals(7, files.size());
	}

}

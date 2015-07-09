package com.temenos.ds.t24.h2.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.temenos.ds.t24.h2.ClasspathExtractor;

public class ClasspathExtractorTest {

//	@Test(expected=IllegalArgumentException.class)
//	public void testFindTagResourceNone() throws IOException {
//		new ClasspathExtractor().getSingleURL("nada");
//	}
//
//	@Test
//	public void testFindTagResourceMoreThanOne() throws IOException {
//		try {
//			new ClasspathExtractor().getSingleURL("META-INF/MANIFEST.MF");
//		} catch (IllegalStateException e) {
//			assertTrue(e.getMessage().contains("junit"));
//			assertTrue(e.getMessage().contains("hamcrest"));
//		}
//	}

	@Test(expected=IllegalArgumentException.class)
	public void testUsingDotInsteadOfSlash() throws IOException {
		new ClasspathExtractor().extractFromClasspathToDirectory("org.junit", new File("nodir"));
	}

	@Test(expected=IOException.class)
	public void testFindTagResourceNone() throws IOException {
		new ClasspathExtractor().extractFromClasspathToDirectory("com/does/not/exist", new File("nodir"));
	}

	@Test(expected=IllegalStateException.class)
	@Ignore // Not yet implemented... not really important for now
	public void testClasspathUnpackerFromClasspathPackageAvailableInTwoJARs() throws IOException {
		new ClasspathExtractor().extractFromClasspathToDirectory("META-INF", new File("nodir"));
	}

	@Test
	public void testExtract() throws IOException {
		final File outDir = new File("./target/ClasspathExtractorTest");
		FileUtils.deleteDirectory(outDir); // TODO Guava alternative?
//		new ClasspathExtractor().extract("org.junit.Test.class", outDir);
		int n = new ClasspathExtractor().extractFromClasspathToDirectory("org/junit", outDir);
		assertTrue("Less than 10 files extracted, only: " + n, n > 10);
		assertTrue(outDir.exists());
		assertTrue(outDir.isDirectory());
		File testClassFile = new File(outDir, "Test.class");
		assertTrue("Test.class lenght() < 601: " + testClassFile.length(), testClassFile.length() > 600);
	}

}

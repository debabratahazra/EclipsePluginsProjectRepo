package com.temenos.ds.t24.h2;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * DB with content available on classpath.
 *
 * @author Michael Vorburger
 */
public class ClasspathDB extends DB {

	private static final String DEFAULT_T24_H2_DB_CLASSPATH_PACKAGE = "com/temenos/mvorburger/t24/core/db/h2";
	private static final ClasspathExtractor extractor = new ClasspathExtractor();

	public ClasspathDB() throws IOException {
		this(DEFAULT_T24_H2_DB_CLASSPATH_PACKAGE);
	}

	public ClasspathDB(String dbClasspathPackagePath) throws IOException {
		this(dbClasspathPackagePath, 0);
	}

	public ClasspathDB(String dbClasspathPackagePath, int port) throws IOException {
		super(getNewTemporaryDatabaseDirectory(dbClasspathPackagePath), port);
	}

	private static File getNewTemporaryDatabaseDirectory(String packagePath) throws IOException {
		// OK, this is a bit of a hack.. ;) @see TODO test start two
		final String name = ClasspathDB.class.getName();
		File tempFile = File.createTempFile(name, null);
		File tempDir = new File(tempFile.getParentFile(), name + ".dir");

		FileUtils.forceMkdir(tempDir);
		extractor.extractFromClasspathToDirectory(packagePath, tempDir);
		return tempDir;
	}

	// TODO support reset() it to the original state.. VERY useful for integration tests!

}

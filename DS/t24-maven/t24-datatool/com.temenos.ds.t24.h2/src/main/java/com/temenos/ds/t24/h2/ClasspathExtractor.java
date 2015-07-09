package com.temenos.ds.t24.h2;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Utility to unpack the contents of a JAR found on the classpath to the file system.
 *
 * The current implementation of this class is "heavily inspired" (essentially copy/pasted)
 * from the ch.vorburger.mariadb4j.Util in https://github.com/vorburger/MariaDB4j.
 *
 * @author Michael Vorburger
 */
public class ClasspathExtractor {

	/**
	 * Extract files from a package on the classpath into a directory.
	 * @param packagePath e.g. "com/stuff" (always forward slash not backslash, never dot)
	 * @param toDir directory to extract to
	 * @return int the number of files copied
	 * @throws java.io.IOException if something goes wrong, including if nothing was found on classpath
	 */
	public int extractFromClasspathToDirectory(String packagePath, File toDir) throws IOException {
		if (packagePath.contains("."))
			throw new IllegalArgumentException("Use / instead of . in packagePath: " + packagePath);
		checkDirectory(toDir);

		String locationPattern = "classpath*:" + packagePath + "/**";
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resourcePatternResolver.getResources(locationPattern);
		if (resources.length == 0) {
			throw new IOException("Nothing found at " + locationPattern);
		}
		int counter = 0;
		for (Resource resource : resources) {
			if (resource.isReadable()) { // Skip hidden or system files
				URL url = resource.getURL();
				String path = url.toString();
				if (!path.endsWith("/")) { // Skip directories
					int p = path.lastIndexOf(packagePath) + packagePath.length();
					path = path.substring(p);
					File targetFile = new File(toDir, path);
					long len = resource.contentLength();
					if (!targetFile.exists() || targetFile.length() != len) { // Only copy new files
						FileUtils.copyURLToFile(url, targetFile);
						counter++;
					}
				}
			}
		}
//        if (counter > 0) {
//            logger.info("Unpacked {} files from {} to {}", new Object[] { counter, locationPattern, toDir });
//        }
		return counter;
	}

	protected void checkDirectory(File dir) throws IOException {
		String absPath = dir.getAbsolutePath();
		if (absPath.trim().length() == 0) {
			throw new IOException(dir.toString() + " is empty");
		}
		if (!dir.isDirectory()) {
			throw new IOException(dir.toString() + " is not a directory");
		}
		if (!dir.canRead()) {
			throw new IOException(dir.toString() + " is not a readable directory");
		}
		if (!dir.canWrite()) {
			throw new IOException(dir.toString() + " is not a writeable directory");
		}
	}

	/**
	 * Retrieve the directory located at the given path.
	 * Checks that path indeed is a reabable directory.
	 * If this does not exist, create it (and log having done so).
	 * @param path directory(ies, can include parent directories) names, as forward slash ('/') separated String
	 * @throws IllegalArgumentException If it is not a directory, or it is not readable
	 * @return safe File object representing that path name
	 * @throws IOException if mkdir failed
	 */
	public File getDirectory(String path) throws IOException {
//	    boolean log = false;
		File dir = new File(path);
		if (!dir.exists()) {
//		    log = true;
//			try {
				FileUtils.forceMkdir(dir);
//			}
//			catch (IOException e) {
//				throw new IllegalArgumentException("Unable to create new directory at path: " + path, e);
//			}
		}
		checkDirectory(dir);
//        if (log) {
//		    logger.info("Created directory: " + absPath);
//        }
		return dir;
	}


// This was my original implementation idea - before I remembered that I had already coded something very similar in MariaDB4j.. ;-)
//
//	public void extract(String classpathResourceTag, File outputDirectory) throws IOException {
//	   URL url = getSingleURL(classpathResourceTag);
//	   // ...
//  }
//
//	protected URL getSingleURL(String classpathResourceTag) throws IOException {
//		Enumeration<URL> foundTags = getClass().getClassLoader().getResources(classpathResourceTag);
//		if (!foundTags.hasMoreElements()) {
//			throw new IllegalArgumentException("Resource not found on classpath (of this ClassLoader): " + classpathResourceTag);
//		}
//		URL url = foundTags.nextElement();
//		if (foundTags.hasMoreElements()) {
//			StringBuilder msg = new StringBuilder("Resource found more than once on classpath (of this ClassLoader): "
//					+ classpathResourceTag + "; 'first' in: " + url.toString() + ", but also: ");
//			while (foundTags.hasMoreElements()) {
//				msg.append(foundTags.nextElement().toString());
//				msg.append(", ");
//			}
//			throw new IllegalStateException(msg.toString());
//		}
//		return url;
//	}

}

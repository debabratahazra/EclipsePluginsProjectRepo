package com.temenos.ds.embedded.server.iris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a workaround to avoid the following problem when the
 * classpath is too long: <code> 
 * java.io.IOException: Cannot run program "DS-T24\jre\bin\javaw.exe"
 * : CreateProcess error=206, (DS-7164)
 * </code>
 * <p>
 * The main method of this class launches an other java main function but loads
 * the classpath from a file specified as an argument in the CLI (instead of
 * using the -cp argument).
 * </p>
 * <p>
 * Syntax:
 * </p>
 * 
 * <pre>
 * classname classpath-file [optional arguments]
 * </pre>
 * <p>
 * The classname is the fully qualified java class that contains the main method
 * to launch
 * </p>
 * <p>
 * The classpath-file specifies the classpath to use to launch the main method.
 * The file should specify a list of jars, files and/or folders (one per line).
 * 
 * @author yan
 * @Modified deb
 */
public class LongClasspathAppStarter {

	/**
	 * Start another main method from a specified class (1st argument) within
	 * the context of a new classloader which contains all the class/resources
	 * defined in a file (2nd argument). The remaining arguments are passed to
	 * the other main method.
	 * 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
		System.out.println("*******************************");
		System.out.println("LongClasspathAppStarter started");
		System.out.println("*******************************");
		System.out.print("args: ");
		for (String arg : args) {
 			System.out.print(arg + " ");
		}
		System.out.println();
		
		// Get the 1st argument as the main class to start, save the remaining
		// args as the args to be transmitted
		if (args.length < 2) {
			throw new IllegalArgumentException("The first argument should be "
					+ "an existing file containing the classpath entries to load, "
					+ "the second one should the SimpleServer starter class.");
		}

		// Get the 1st argument as the classpath file
		String classpathFile = args[0];
		System.out.println("Classpath file: " + classpathFile);

		// Get the 2nd argument as the main class to start
		String starterClassName = args[1];
		if (!new File(classpathFile).exists()) {
			throw new IllegalArgumentException("The first argument should be " +
					"an existing file containing the classpath to load.");
		}
		System.out.println("Starter class name: " + starterClassName);

		// Save the remaining args as the args to be transmitted to the main
		List<String> remainingArgs = new ArrayList<String>();
		for (int i = 2; i < args.length; i++) {
			remainingArgs.add(args[i]);
		}

		// Read all jars/folders that need to be loaded
		System.out.println("classpath:");
		List<URL> urls = new ArrayList<URL>();
		File tempServerFolder = createTempServerFolder();
		if (tempServerFolder != null) {
			try {
				urls.add(tempServerFolder.toURI().toURL());
			} catch (MalformedURLException e) {
				System.err.println("Unable to transform temp folder to URL");
			}
		}
		urls.addAll(readUrlsOfClassPath(classpathFile));
		for (URL url: urls) {
			System.out.println(url);
		}
		
		// Load the jars/folders into a newly created classloader
		ClassLoader previousClassLoader = Thread.currentThread().getContextClassLoader();
		URLClassLoader classloader = URLClassLoader.newInstance(urls.toArray(new URL[urls.size()]), previousClassLoader);
		try {
			// Switch the classloaders
			Thread.currentThread().setContextClassLoader(classloader);

			System.out.println("Loading " + starterClassName);
			Class<?> mainClass = classloader.loadClass(starterClassName);

			System.out.println();
			System.out.println("*************************************");
			System.out.println("Launching IRIS Embedded Server");
			System.out.println("*************************************");
			System.out.print("Starting with the following args: ");
			for (String arg: remainingArgs) {
				System.out.print(arg + " ");
			}
			System.out.println();
			Method main;
			try {
				main = mainClass.getMethod("main", String[].class);
				if (main.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || main.getReturnType() != Void.TYPE) {
					throw new IllegalArgumentException("main is either not static, not public or not returning void");
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(
						"public static void main(String[] args) method not found in the SimpleServer stater class: "
								+ starterClassName, e);
			}

			// Invoke the main method
			try {
				main.invoke(null, (Object) remainingArgs.toArray(new String[remainingArgs.size()]));
			} catch (Exception e) {
				throw new IllegalArgumentException("An error occured while invoking the main method of "
						+ starterClassName, e);
			}

		} finally {
			// Restore the classloader
			Thread.currentThread().setContextClassLoader(previousClassLoader);
		}
	}

	/**
	 * @return
	 * @throws IOException 
	 */
	private static File createTempServerFolder() {
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("iris-server-", "-tempdir");
			if (tmpFile.delete() && tmpFile.mkdir()) {
				return tmpFile;
			}
		} catch (IOException e) {
			// Error handled hereafter
		}
		System.err.println("Unable to create server temp folder" 
				+ (tmpFile!=null?"(" + tmpFile.getAbsolutePath() + ")":""));
		return null;
	}

	/**
	 * @return the list of urls found in classpathFile.
	 * @throws FileNotFoundException
	 *             if the classpathFile file is not found.
	 */
	private static List<URL> readUrlsOfClassPath(String classpathFile) throws FileNotFoundException {
		List<URL> urls = new ArrayList<URL>();
		
		BufferedReader br = null;
		String line = null;
		try {
			br = new BufferedReader(new FileReader(new File(classpathFile)));
			while ((line = br.readLine()) != null) {
				urls.add(new File(line).toURI().toURL());
			}
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Incorrect file format for line: " + line, e);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to read the list of files", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// Ignore.
				}
			}
		}
		return urls;
	}
}

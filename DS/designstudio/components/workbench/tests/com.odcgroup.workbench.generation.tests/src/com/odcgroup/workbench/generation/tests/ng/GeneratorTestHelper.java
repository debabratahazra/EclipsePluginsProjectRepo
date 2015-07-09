package com.odcgroup.workbench.generation.tests.ng;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.odcgroup.workbench.dsl.xml.EIO;

/**
 * Helper to facilitate writing integration tests for CodeGenerator2.
 * 
 * See ExampleGeneratorTest for how to use this.
 *
 * @author Michael Vorburger
 */
public class GeneratorTestHelper {

	public @Inject ResourceSet resourceSet;
	
	public @Inject InMemoryFileSystemAccess fsa;
	public @Inject ValidatingModelLoaderImpl loader;

	public String getGeneratedFile(String path) {
		Preconditions.checkNotNull(fsa, "Why is the IFileSystemAccess null?!");
		path = IFileSystemAccess.DEFAULT_OUTPUT + path;
		CharSequence cs = fsa.getTextFiles().get(path);
		if (cs == null) {
			String availableFiles = fsa.getTextFiles().keySet().toString();
			throw new IllegalStateException("The test did not generate this output file: " + path + "; available are only: " + availableFiles);
		}
		return cs.toString(); // Tip: toString() is important here.. @see https://github.com/junit-team/junit/pull/949
	}

	public URI getURI(String classpathResourceName, Class<?> classLoaderContextToLoadResourceFrom) {
		// This EIO is ONLY to delegate getURI() - it should NOT be used to load
		// resources (tests go through the ModelLoader, only), so null RS is OK:
		EIO eio = new EIO(null);
		return eio.getURI(classpathResourceName, classLoaderContextToLoadResourceFrom);
	}

}

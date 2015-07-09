package com.odcgroup.workbench.generation.tests.ng.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

/**
 * Example Xtext IGenerator implementation, for test(s).
 * 
 * In bigger re-world generators, just for better code readability, you should
 * typically have different IGenerator implementations per type (extension) of
 * model resources.
 * 
 * @author Michael Vorburger
 */
public class XtextIGeneratorSampleImpl implements IGenerator {

	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		EObject rootEObject = input.getContents().get(0);
		Grammar grammar = (Grammar) rootEObject;
		fsa.generateFile("sample/" + input.getURI().lastSegment(), grammar.getName());
	}

}

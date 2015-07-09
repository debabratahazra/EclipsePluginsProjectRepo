package com.odcgroup.workbench.generation.tests.ng.tests;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;

import com.odcgroup.workbench.generation.cartridge.ng.CodeGenerator2;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;

/**
 * Example Xtext IGenerator implementation, for test(s).
 * 
 * Even though it looks like these checks for the model type are duplicated
 * between here and the XtextIGeneratorSampleImpl, this is actually intentional,
 * and better for performance, because it avoids an un-necessary getResource().
 * 
 * In real-world (non-test), if you don't already have an IGenerator to wrap,
 * then from scratch you would write both an IGenerator and a CodeGenerator2,
 * but only a CodeGenerator2... this is just an integration test & example.
 * 
 * Also, an existing IGenerator may handle only one type of model, in which
 * case you would do the URI fileExtension() check only in the CodeGenerator2
 * wrapper, and do nothing (i.e. assume it will be the right type) in the  
 * (existing) IGenerator.
 * 
 * @author Michael Vorburger
 */
public class DSNGCodeGenerator2SampleImpl implements CodeGenerator2 {

	@Inject XtextIGeneratorSampleImpl iGenerator; 
	
	@Override
	public void doGenerate(URI input, ModelLoader loader, IFileSystemAccess fsa) throws Exception {
		if ("xtext".equals(input.fileExtension())) {
			Resource resource = loader.getResource(input);
			iGenerator.doGenerate(resource, fsa);
		}
	}
}

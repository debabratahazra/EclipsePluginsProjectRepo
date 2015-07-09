package com.odcgroup.workbench.generation.tests.ng.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;

/**
 * Example how to write integration tests for code generator "cartridges"
 * (CodeGenerator2) in DS. The "model" loaded by this test is a Mini.xtext, just
 * as an example, because workbench cannot depend on real domain etc. features
 * (layering).
 * 
 * Other more real-world tests can be found e.g. in T24MdfGeneratorTest, TODO
 * IRIS, Edge...
 * 
 * Such tests test only the Generator, NOT DS "infrastructure", and therefore
 * INTENTIONALLY do not (and should never) depend on IOfsProject or
 * BridgeCodeGenerator2 and all that.
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
// Tip: Your own test would have another InjectorProvider here
@InjectWith(XtextInjectorProvider.class)
public class ExampleGeneratorTest {

	@Inject DSNGCodeGenerator2SampleImpl generatorToTest;
	@Inject GeneratorTestHelper h;
	
	// Tip: You should NOT have @BeforeClass with *StandaloneSetup.doSetup() or *PackageImpl.init() in your tests - all that should be in a (custom subclass of) an InjectorProvider (@see https://bugs.eclipse.org/bugs/show_bug.cgi?id=439117)   
	
	@Test
	public void aBasicT24MdfGenerator() throws Exception {
		// Tip: if the "main" resource you are loading depends on other models,
		// you have to load them into the ResourceSet first here:
		// h.loader.getResource(h.getURI("someOtherModelTheMainModelYouGenerateDependsOn.model", getClass()));
		
		URI input = h.getURI("Mini.xtext", getClass());		
		generatorToTest.doGenerate(input, h.loader, h.fsa);
		
		assertEquals("com.odcgroup.ds.Test", h.getGeneratedFile("sample/Mini.xtext")); 
	}

	@Test
	// TODO @FixMethodOrder(MethodSorters.NAME_ASCENDING) when we'll have JUnit 4.11 - else this doesn't really reliably prove much, as test order keeps changes (but it never fails, so safe)
	public void bEnsureEachTestIsFreshAndCleanAndDoesNotRememberPreviouslyLoadedModels() throws Exception {
		assertTrue(h.resourceSet.getResources().isEmpty());
	}
	
	@Test(expected=IllegalStateException.class)
	// TODO @FixMethodOrder(MethodSorters.NAME_ASCENDING) when we'll have JUnit 4.11 - else this doesn't really reliably prove much, as test order keeps changes (but it never fails, so safe) 
	public void cEnsureEachTestIsFreshAndCleanAndDoesNotRememberPreviouslyGeneratedFiles() throws Exception {
		h.getGeneratedFile("sample/Mini.xtext");
	}

}

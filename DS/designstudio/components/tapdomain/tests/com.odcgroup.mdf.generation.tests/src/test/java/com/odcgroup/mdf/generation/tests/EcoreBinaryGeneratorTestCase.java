package com.odcgroup.mdf.generation.tests;

import org.eclipse.emf.ecore.EcorePackage;
import org.junit.After;
import org.junit.Before;

import com.odcgroup.mdf.generation.EcoreBinaryGenerator;
import com.odcgroup.mdf.generation.EcoreGenerator;
import com.odcgroup.mdf.generation.ecore.BinaryResourceFactoryImpl;

public class EcoreBinaryGeneratorTestCase extends EcoreGeneratorTestCase {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecorebin", new BinaryResourceFactoryImpl());
		rs.getPackageRegistry().put("http://www.eclipse.org/emf/2002/Ecore", EcorePackage.eINSTANCE);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Override
	protected EcoreGenerator getGeneratorInstance() {
		return new EcoreBinaryGenerator();
	}
}

package com.odcgroup.domain.workbenchgen.tests;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.odcgroup.domain.DomainInjectorProvider;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.workbench.dsl.xml.EIO;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;

/**
 * Integration Test for ModelLoaderImpl.
 * This class is here in domain-dsl-tests, instead of in workbench-generation-tests,
 * because it needs access to some real test meta model (like Domain, but it could be any). 
 * 
 * @author Michael Vorburger
 */
@RunWith(XtextRunner.class)
@InjectWith(DomainInjectorProvider.class)
public class ModelLoaderImplTest {

	@Inject EIO eio;
	@Inject ResourceSet rs;
	
	@BeforeClass
	public static void setupClass() {
		MdfPackage.eINSTANCE.toString();
	}
	
	@Test
	public void testModelLoader() throws Exception {
		URI customerDomainURI = eio.getURI("/ST_Customer.domain", ModelLoaderImplTest.class);
		EObject deo = eio.load(customerDomainURI);
		assertEquals(MdfPackage.Literals.MDF_DOMAIN, deo.eClass());

		URI anotherDomainURI = eio.getURI("/Another.domain", ModelLoaderImplTest.class);
		EObject context = eio.load(anotherDomainURI);
		
		ModelLoader modelLoader = new ModelLoaderImpl(rs);
		MdfDomain domain = modelLoader.getNamedEObject(context, QualifiedName.create("ST_Customer"), MdfPackage.Literals.MDF_DOMAIN);
		assertEquals("ST_Customer", domain.getName());
		
		MdfClass klass = modelLoader.getNamedEObject(context, QualifiedName.create("ST_Customer", "CUSTOMER"), MdfPackage.Literals.MDF_CLASS);
		assertEquals("CUSTOMER", klass.getName());
	}

	/**
	 * This test passed, but doesn't actually test what it should test (whether
	 * the proxy resolution of a non-loaded resource works; as it's actually not
	 * Proxy when retrieved from the Index). It also passes when resolveProxy()
	 * in ModelLoaderImpl is commented out - so it's not adding much value..
	 * Cannot figure out how to test that, giving up on it; oh well.
	 */
	@Test
	public void testModelLoaderProxyResolution() throws Exception {
		URI customerDomainURI = eio.getURI("/ST_Customer.domain", ModelLoaderImplTest.class);
		Resource resource = eio.loadResource(customerDomainURI);
		resource.unload();
		
		URI anotherDomainURI = eio.getURI("/Another.domain", ModelLoaderImplTest.class);
		EObject context = eio.load(anotherDomainURI);

		ModelLoader modelLoader = new ModelLoaderImpl(rs);
		MdfClass klass = modelLoader.getNamedEObject(context, QualifiedName.create("ST_Customer", "CUSTOMER"), MdfPackage.Literals.MDF_CLASS);
		assertEquals("CUSTOMER", klass.getName());
	}
}

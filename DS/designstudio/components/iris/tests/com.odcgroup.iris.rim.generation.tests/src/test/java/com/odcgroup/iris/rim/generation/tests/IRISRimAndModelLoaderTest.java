package com.odcgroup.iris.rim.generation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.naming.QualifiedName;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.iris.rim.generation.T24ResourceModelsGenerator;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoaderImpl;
import com.odcgroup.workbench.generation.tests.ng.GeneratorTestHelper;
import com.temenos.interaction.rimdsl.rim.DomainModel;
import com.temenos.interaction.rimdsl.rim.Event;
import com.temenos.interaction.rimdsl.rim.RimPackage;

@RunWith(XtextRunner.class)
@InjectWith(IRISInjectorProvider.class) 
public class IRISRimAndModelLoaderTest {

	@Inject T24ResourceModelsGenerator generator;
	@Inject GeneratorTestHelper helper;
	
	@Test
	public void testRimEventAndModelLoader() throws Exception {
		URI uri = helper.getURI("HTTPEvents.rim", getClass());
		Resource r = helper.loader.getResource(uri);
		assertNotNull(r);
	
		ResourceSet rs = r.getResourceSet();
		assertNotNull(rs);
		ModelLoader loader = new ModelLoaderImpl(rs);
		
		DomainModel domainModel = loader.getRootEObject(uri, DomainModel.class);
		assertNotNull(domainModel);

		Event event = loader.getNamedEObject(domainModel, QualifiedName.create("common", "HTTPEvents", "GET"), RimPackage.Literals.EVENT);
		assertNotNull("Event common.HTTPEvents.GET not found", event);
		assertEquals("Event name", event.getName(), "GET");

		event = loader.getNamedEObject(domainModel, QualifiedName.create("common", "HTTPEvents", "DELETE"), RimPackage.Literals.EVENT);
		assertNotNull("Event common.HTTPEvents.DELETE not found", event);
		assertEquals("Event name", event.getName(), "DELETE");

		event = loader.getNamedEObject(domainModel, QualifiedName.create("common", "HTTPEvents", "POST"), RimPackage.Literals.EVENT);
		assertNotNull("Event common.HTTPEvents.POST not found", event);
		assertEquals("Event name", event.getName(), "POST");

		event = loader.getNamedEObject(domainModel, QualifiedName.create("common", "HTTPEvents", "PUT"), RimPackage.Literals.EVENT);
		assertNotNull("Event common.HTTPEvents.PUT not found", event);
		assertEquals("Event name", event.getName(), "PUT");

	}

}

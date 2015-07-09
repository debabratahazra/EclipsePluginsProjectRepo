package com.odcgroup.component.dsl.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.odcgroup.service.model.component.AccessSpecifier;
import com.odcgroup.service.model.component.Component;
import com.odcgroup.workbench.dsl.xml.EIO;

@RunWith(XtextRunner.class)
@InjectWith(ComponentWithDependencyInjectorProvider.class)
public class ComponentParserTest {

	@Inject
	EIO eio;

	@Test
	public void publicMethod() throws Exception {
		URI uri = eio.getURI("sampleComponent.component", ComponentParserTest.class);
		Component c = eio.load(uri, Component.class);
		AccessSpecifier accessSpecifier = c.getContent().get(0).getMethod().get(0).getAccessSpecifier();
		assertEquals(AccessSpecifier.PUBLIC, accessSpecifier);
	}

	@Test
	public void checkPropertyIsArray() throws Exception {
		URI uri = eio.getURI("sampleComponent.component", ComponentParserTest.class);
		Component c = eio.load(uri, Component.class);
		boolean isArray = c.getContent().get(1).getProperty().get(0).isArray();
		assertEquals("Case 1 (Ex: jBC: I_TEST ->MY.VAR) not met.", false, isArray);

		isArray = c.getContent().get(2).getProperty().get(0).isArray();
		assertEquals("Case 2 (Ex: jBC: I_TEST ->MY.VAR()) not met.", true, isArray);

		isArray = c.getContent().get(3).getProperty().get(0).isArray();
		assertEquals("Case 3 (Ex: jBC: I_TEST ->MY.VAR(1)) not met.", true, isArray);
	}

	@Test
	public void externalMethod() throws Exception {
		URI uri = eio.getURI("sampleComponent.component", ComponentParserTest.class);
		Component c = eio.load(uri, Component.class);
		AccessSpecifier accessSpecifier = c.getContent().get(4).getMethod().get(0).getAccessSpecifier();
		assertEquals(AccessSpecifier.EXTERNAL, accessSpecifier);
	}

	@Test
	public void parseComponentName() throws Exception {
		// component name with dot. 
		// Note: The splitting of module & component name test coverage done in ServiceDecriptorTest.java
		URI uri = eio.getURI("sampleComponent.component", ComponentParserTest.class);
		Component c = eio.load(uri, Component.class);
		assertEquals("AA.PaymentSchedule", c.getName()); 
		
		// component name without dot
		uri = eio.getURI("IntegrationComponent.component", ComponentParserTest.class);
		c = eio.load(uri, Component.class);
		assertEquals("IntegrationComponent", c.getName()); 
	}
}

package com.odcgroup.page.model.dsl.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class InjectorUniquenessTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS3929-1.module",
									"module/Default/module/DS3929-2.module");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testSameParser() {
		XtextResource resource1 = (XtextResource) getOfsProject().getModelResourceSet().getResource(URI.createURI("resource:///Default/module/DS3929-1.module"), false);
		XtextResource resource2 = (XtextResource) getOfsProject().getModelResourceSet().getResource(URI.createURI("resource:///Default/module/DS3929-2.module"), false);
		
		// if we have a single injector, all resources will use the same parser instance
		Assert.assertTrue(resource1.getParser()==resource2.getParser());
	}

	@Test
	public void testNoParseResult() {
		XtextResource resource1 = (XtextResource) getOfsProject().getModelResourceSet().getResource(URI.createURI("resource:///Default/module/DS3929-1.module"), false);
		
		Assert.assertTrue(resource1.getParseResult()==null);
	}

}

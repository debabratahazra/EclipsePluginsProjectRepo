package com.odcgroup.workbench.core.repository;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class ModelURIConverterTest extends AbstractDSUnitTest {

	private static final String FUNKY_ENQUIRY = "ds7132/%DEBIT.INT.ADDON.enquiry";

	@Before public void setUp() throws CoreException {
		createModelsProject();
	}

	@After public void tearDown() throws CoreException {
		deleteModelsProjects();
	}

	@Test public void testSpecialCharacterEscaping() {
		URI dsURI =  URI.createURI(ModelURIConverter.SCHEME + ":///" + FUNKY_ENQUIRY, false);
		assertEquals("resource:///ds7132/%25DEBIT.INT.ADDON.enquiry", dsURI.toString());
		
		copyResourceInModelsProject("enquiry/" + FUNKY_ENQUIRY);
		
		ModelURIConverter modelURIConverter = new ModelURIConverter(getOfsProject());
		URI plaformURI = modelURIConverter.toPlatformURI(dsURI);
		assertEquals("platform:/resource/default-models/enquiry/ds7132/%25DEBIT.INT.ADDON.enquiry", plaformURI.toString());
	}
	
}

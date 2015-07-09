package com.odcgroup.workbench.migration;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.workbench.core.IOfsModelResource;
import com.odcgroup.workbench.core.repository.ModelNotFoundException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

public class MetaModelVersionHandlerTest extends AbstractDSUnitTest {

	@Before
	public void setUp() throws Exception {
		createModelsProject();
		copyResourceInModelsProject("module/Default/module/DS3645_dsl.module");
	}
	
	@After
	public void tearDown() throws Exception {
		deleteModelsProjects();
	}

	@Test
	public void testGetMetaModelVersion() throws ModelNotFoundException, CoreException {
		MetaModelVersionHandler handler = new MetaModelVersionHandler() {
			public void setCurrentMetaModelVersion(Resource resource)
					throws MigrationException {
			}
		};
		handler.setModelType("module");

		// test the DSL resource
		IOfsModelResource modelResource = getOfsProject().getOfsModelResource(URI.createURI("resource:///Default/module/DS3645_dsl.module"));
		String version = handler.getMetaModelVersion(modelResource); 
		Assert.assertEquals(handler.getCurrentMetaModelVersion(), version);
	}
	
}

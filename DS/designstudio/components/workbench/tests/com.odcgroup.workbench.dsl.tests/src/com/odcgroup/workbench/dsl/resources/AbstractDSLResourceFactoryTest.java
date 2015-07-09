package com.odcgroup.workbench.dsl.resources;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceFactory;
import org.junit.Test;

/**
 * @author amc
 */
public class AbstractDSLResourceFactoryTest {
	
	@Test
	public void testFactoryCanBeExtended() throws Exception {
		// given 
		final IResourceFactory mockFactory = mock(IResourceFactory.class);
		AbstractDSLResourceFactory factoryUnderTest = new AbstractDSLResourceFactory() {
			@Override
			protected IResourceFactory createResourceFactory() {
				return mockFactory;
			}
		};
		
		// when
		URI uri = null; // not a great test, but we can't mock URIs as they're final
		factoryUnderTest.createResource(uri);

		// then
		verify(mockFactory, times(1)).createResource(uri);
	}
}

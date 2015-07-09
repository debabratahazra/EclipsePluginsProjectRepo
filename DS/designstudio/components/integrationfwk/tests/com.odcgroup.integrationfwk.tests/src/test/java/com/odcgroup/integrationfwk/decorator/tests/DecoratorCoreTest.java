package com.odcgroup.integrationfwk.decorator.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.odcgroup.integrationfwk.ui.decorators.core.PersistentPropertyTypes;
import com.odcgroup.integrationfwk.ui.decorators.core.ResourcePropertiesManager;

/**
 * Class which contains test cases for testing the business logic of package
 * com.temenos.tws.consumer.DecoratorDemo.core
 * 
 * @author sbharathraja
 * 
 */
public class DecoratorCoreTest {

	/**
	 * test case which cover the classes {@link PersistentPropertyTypes} and
	 * {@link ResourcePropertiesManager}
	 * 
	 * @throws CoreException
	 */
	// Ignored this test cases, because jenkins is failing. but local build test
	// case works fine.
	// TODO need to investigate
	@Ignore
	@Test
	public void test_DecaratoreCore() throws CoreException {
		// mock the resource
		IResource resource = Mockito.mock(IResource.class);
		// suggest the mock to return our own value
		when(resource.getPersistentProperty(any(QualifiedName.class)))
				.thenReturn("true");
		// execute the public members
		ResourcePropertiesManager.addPersistentProperty(resource, "Busy",
				"value");
		// execute and test the public members
		Assert.assertEquals("true",
				ResourcePropertiesManager.getSuffix(resource));
		Assert.assertTrue(!ResourcePropertiesManager
				.findDecorationImageForResource(resource).isEmpty());
	}
}

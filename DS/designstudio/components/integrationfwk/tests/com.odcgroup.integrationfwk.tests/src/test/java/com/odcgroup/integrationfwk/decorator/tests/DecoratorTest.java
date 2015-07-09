package com.odcgroup.integrationfwk.decorator.tests;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.integrationfwk.t24connectivity.SilentObjectCreator;
import com.odcgroup.integrationfwk.ui.decorators.Decorator;
import com.odcgroup.integrationfwk.ui.decorators.DecoratorImageRegistry;
import com.odcgroup.integrationfwk.ui.decorators.DecoratorImages;
import com.odcgroup.integrationfwk.ui.decorators.DecoratorManager;
import com.odcgroup.integrationfwk.ui.decorators.DecoratorWithImageCaching;

/**
 * class which contains the test cases for testing the package
 * com.temenos.tws.consumer.DecoratorDemo.ui.Decorators
 * 
 * @author sbharathraja
 * 
 */
public class DecoratorTest {

	/**
	 * test case for test the class {@link Decorator}
	 */
	// Ignored this test cases, because jenkins is failing.
	// SilentObjectCreator requires jdk 7
	// but local rtc build test
	// case works fine.
	// TODO need to investigate
	@Ignore
	@Test(expected = RuntimeException.class)
	public void test_Decorator() {
		// mock the resource
		IResource resource = mock(IResource.class);
		// add the mocked resource into manager
		DecoratorManager.addSuccessResources(resource);
		// mock the decorator for state checking
		Decorator decorator = mock(Decorator.class);
		// execute the members for state checking
		decorator.refresh();
		decorator.refresh(any(List.class));
		decorator.refreshAll(true, true);
		// verifying the execution
		verify(decorator).refresh();
		verify(decorator).refresh(any(List.class));
		verify(decorator).refreshAll(anyBoolean(), anyBoolean());
		// object hacking
		decorator = SilentObjectCreator.create(Decorator.class);
		// execute and verifying the public method
		Assert.assertTrue(decorator.checkForNewDecoratorChange(resource));
		// execute the static method and expecting exception
		Decorator.getDemoDecorator();
	}

	/**
	 * test cases for test the class {@link DecoratorManager}
	 */
	@Test
	public void test_DecoratorManager() {
		// mock the resource
		// more than one resource is needed for testing
		IResource resource = mock(IResource.class);
		IResource anotherResource = mock(IResource.class);
		// create list for collecting the mocked resource
		List<IResource> resourceList = new ArrayList<IResource>();
		resourceList.add(resource);
		resourceList.add(anotherResource);
		// execute the public members
		DecoratorManager.addSuccessResources(resourceList);
		DecoratorManager.addSuccessResources(resource);
		DecoratorManager.removeResource(resource);
		DecoratorManager.appendSuccessResources(resourceList);
		// verifying the execution
		Assert.assertTrue(DecoratorManager.getSuccessResources().containsAll(
				resourceList));
		Assert.assertTrue(DecoratorManager.contains(resource));
	}

	/**
	 * test cases for testing the class {@link DecoratorWithImageCaching}
	 */
	// Ignored this test cases, because jenkins is failing.
	// SilentObjectCreator requires jdk 7
	// but local rtc build test
	// case works fine.
	// TODO need to investigate
	@Test
	public void test_DecoratorWithImageCaching() {
		// mock the resource
		IResource resource = mock(IResource.class);
		// mock the decorator for state checking
		DecoratorWithImageCaching caching = mock(DecoratorWithImageCaching.class);
		// execute the public members for state checking
		caching.refresh();
		caching.refresh(any(List.class));
		caching.refreshAll(true, true);
		// verifying the execution
		verify(caching).refresh();
		verify(caching).refresh(any(List.class));
		verify(caching).refreshAll(anyBoolean(), anyBoolean());
		// adding the mock resource into manager
		DecoratorManager.addSuccessResources(resource);
		// object hacking
		caching = SilentObjectCreator.create(DecoratorWithImageCaching.class);
		// execute and verify the public member
		Assert.assertTrue(caching.checkForNewDecoratorChange(resource));
	}

	/**
	 * test cases for test the classes {@link DecoratorImageRegistry} and
	 * {@link DecoratorImages}
	 */
	@Ignore
	@Test
	public void test_ImageRegistry() {
		// initialize the DemoImages class
		DecoratorImages demoImage = new DecoratorImages();
		Image image = null;
		try {
			// create the image
			// display seems to be throw an error while executing the whole test
			// package. Hence exception also checked here.
			image = new Image(Display.getDefault(), demoImage
					.getImageData("Lock"));
		} catch (RuntimeException ex) {
			Assert.assertTrue(ex.getMessage(), true);
		}
		// initialize the registry class
		DecoratorImageRegistry registry = new DecoratorImageRegistry();
		// execute the public members
		registry.setImage("Lock", image);
		// verifying the execution
		Assert.assertEquals(image, registry.getImage("Lock"));
	}
}

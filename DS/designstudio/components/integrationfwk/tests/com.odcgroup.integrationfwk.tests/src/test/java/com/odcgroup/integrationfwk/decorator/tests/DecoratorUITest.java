package com.odcgroup.integrationfwk.decorator.tests;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.odcgroup.integrationfwk.t24connectivity.SilentObjectCreator;
import com.odcgroup.integrationfwk.ui.decorators.FilePropertyPage;
import com.odcgroup.integrationfwk.ui.decorators.GuiFactory;

/**
 * class which contains the test cases for testing the package
 * com.temenos.tws.consumer.DecoratorDemo.ui
 * 
 * @author sbharathraja
 * 
 */
public class DecoratorUITest {

	/**
	 * test case for test the class {@link FilePropertyPage}
	 */
	// Ignored this test cases, because jenkins is failing.
	// SilentObjectCreator requires jdk 7
	// but local rtc build test
	// case works fine.
	// TODO need to investigate
	@Ignore
	@Test(expected = RuntimeException.class)
	public void test_FilePropertyPage() {
		// mock the class for state checking
		FilePropertyPage page = mock(FilePropertyPage.class);
		// suggest the mocker to return our own value
		when(page.performPublish(anyBoolean(), anyString())).thenReturn(true);
		// state checking
		Assert.assertTrue(page.performPublish(true, "eventName"));
		// object hacking for perform test on public member
		page = SilentObjectCreator.create(FilePropertyPage.class);
		// execute the public member and expect exception
		page.performPublish(true, "eventName");
	}

	/**
	 * test case for test the class {@link GuiFactory}
	 */
	// Ignored this test cases, because jenkins is failing. but local build test
	// case works fine.
	// TODO need to investigate
	@Ignore
	@Test
	public void test_GuiFactory() {

		// create parent composite
		Composite parentComposite = new Composite(new Shell(Display
				.getDefault()), SWT.NONE);
		// create composite using public method execution
		Composite childComposite = GuiFactory.getInstance().createComposite(
				parentComposite, 1);
		// verifying the execution
		Assert.assertNotNull(childComposite.getLayout());
		// create text box using public method execution
		Text text = GuiFactory.getInstance().createTextField(parentComposite);
		// verifying the execution
		Assert.assertNotNull(text.getLayoutData());
	}
}

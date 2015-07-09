package com.odcgroup.process.model.translation;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.process.model.ProcessFactory;
import com.odcgroup.process.model.ServiceTask;
import com.odcgroup.process.model.UserTask;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class ProcessTranslationProviderTest extends AbstractDSUnitTest {
	
	private ServiceTask serviceTask;
	private UserTask userTask;
	
	@Before
	public void setUp() throws Exception {
		
		// create a dummy project with the OfsNature.
		createModelsProject();

		serviceTask = ProcessFactory.eINSTANCE.createServiceTask();
		userTask = ProcessFactory.eINSTANCE.createUserTask();
	}
	
	@After
	public void tearDown() throws Exception {
		serviceTask = null;
		userTask = null;
		deleteModelsProjects();
	}

	@Test
	public void testProcessTranslationProvider () throws TranslationException {
		ITranslation translationForServiceTask = 
			new ProcessTranslationProvider()
				.getTranslation(getProject(), serviceTask);
	
		ITranslation translationForUserTask = 
			new ProcessTranslationProvider()
				.getTranslation(getProject(), userTask);
	
		Assert.assertTrue("Wrong translation object returned", 
				translationForServiceTask instanceof ProcessTranslation);

		Assert.assertTrue("Wrong translation object returned", 
				translationForUserTask instanceof ProcessTranslation);

		translationForServiceTask.setText(ITranslationKind.NAME, Locale.FRENCH, "test1");
		Assert.assertEquals("The translation object is not connected to the class", 
				"test1",
				translationForServiceTask.getText(ITranslationKind.NAME, Locale.FRENCH));
		
		translationForUserTask.setText(ITranslationKind.NAME, Locale.FRENCH, "test2");
		Assert.assertEquals("The translation object is not connected to the class", 
				"test2",
				translationForUserTask.getText(ITranslationKind.NAME, Locale.FRENCH));
	}

}

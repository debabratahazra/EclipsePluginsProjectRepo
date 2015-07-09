package com.odcgroup.page.model.tests.translation;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.translation.EventTranslation;
import com.odcgroup.page.model.translation.EventTranslationProvider;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.workbench.core.tests.util.AbstractDSUnitTest;

/**
 * @author yan
 */
public class EventTranslationProviderTest extends AbstractDSUnitTest {
	
	private Event event;
	
	@Before
	public void setUp() throws Exception {
		createModelsProject();
		event = ModelFactory.eINSTANCE.createEvent();
	}
	
	@After
	public void tearDown() throws Exception {
		event = null;
		deleteModelsProjects();
	}

	@Test
	public void testProcessTranslationProvider () throws TranslationException {
		ITranslation translationForEvent = 
			new EventTranslationProvider()
				.getTranslation(getProject(), event);
	
		Assert.assertTrue("Wrong translation object returned", 
				translationForEvent instanceof EventTranslation);

		translationForEvent.setText(ITranslationKind.NAME, Locale.FRENCH, "test1");
		Assert.assertEquals("The translation object is not connected to the class", 
				"test1",
				translationForEvent.getText(ITranslationKind.NAME, Locale.FRENCH));
	}

}

package com.odcgroup.page.translation.generation.tests;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.translation.EventTranslation;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.TranslationGenerationCore;

/**
 * Check the extension point
 * 
 * @author atr
 */
public class EventTranslationKeyProviderTest extends TranslationKeyTest {
	
	@Test
	public void testEventTranslationKeyProvider() {
		Event event = ModelFactory.eINSTANCE.createEvent("OnClick");
		ITranslation translation = TranslationCore.getTranslationManager(getProject()).getTranslation(event);
		ITranslationKey key = TranslationGenerationCore.getTranslationKey(getProject(), translation);
		Assert.assertTrue("Unable to retrieve a translation key through the extension point", key != null);
		Assert.assertTrue("Unable to retrieve the wrapped translation", key.getTranslation() != null);
		Assert.assertTrue("Wrong wrapped translation", key.getTranslation() instanceof EventTranslation );
		Assert.assertTrue("Wrong wrapped event.", event == key.getTranslation().getOwner());
	}

}

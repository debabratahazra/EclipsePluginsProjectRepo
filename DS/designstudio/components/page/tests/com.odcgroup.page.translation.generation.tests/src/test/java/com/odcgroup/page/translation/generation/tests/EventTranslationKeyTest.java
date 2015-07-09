package com.odcgroup.page.translation.generation.tests;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.page.model.Event;
import com.odcgroup.page.model.ModelFactory;
import com.odcgroup.page.model.Translation;
import com.odcgroup.page.model.translation.EventTranslation;
import com.odcgroup.page.model.translation.EventTranslationProvider;
import com.odcgroup.page.translation.generation.EventTranslationKeyProvider;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.TranslationException;
import com.odcgroup.translation.generation.ITranslationKey;

/**
 * @author atr
 */
public class EventTranslationKeyTest extends TranslationKeyTest {
	
	
	/**
	 * 
	 */
	@Test
	public void testEventTranslationKey() {
		
		// prepare data
		Event event = ModelFactory.eINSTANCE.createEvent("OnClick");
		EventTranslationProvider transProvider = new EventTranslationProvider();
		Translation enTrans = ModelFactory.eINSTANCE.createTranslation();
		enTrans.setLanguage("en");
		enTrans.setMessage("en-test");
		event.getMessages().add(enTrans);
		Translation frTrans = ModelFactory.eINSTANCE.createTranslation();
		frTrans.setLanguage("fr");
		frTrans.setMessage("fr-test");
		event.getMessages().add(frTrans);
		event.setTranslationId(123);
		EventTranslation eventTranslation = new EventTranslation(transProvider, getProject(), event); 
		EventTranslationKeyProvider provider = new EventTranslationKeyProvider();
		
		// retrieve translation key		
		ITranslationKey translationKey = provider.getTranslationKey(project, eventTranslation);
		Assert.assertTrue("Cannot retrieve the translation key.", translationKey != null);

		// check translation kinds
		ITranslationKind kinds[] = translationKey.getTranslationKinds();
		Assert.assertTrue("Wrong number of translation kinds", kinds.length == 1);
		Assert.assertTrue("Wrong translation kind", kinds[0] == ITranslationKind.NAME);
		
		// check wrapped translation
		ITranslation translation = translationKey.getTranslation();
		Assert.assertTrue("Missing wrapped event translation", translation != null);
		Assert.assertTrue("Missing wrapped event ", translation.getOwner() != null);
		Assert.assertTrue("Wrong wrapped event instance", event == translation.getOwner());
		
		ITranslationKind kind = ITranslationKind.NAME;

		String key = translationKey.getKey(kind);

		// check the English message
		try {
			String message = translationKey.getMessage(kind, Locale.ENGLISH);
			Assert.assertTrue("Wrong English message for the key", "en-test".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}

		// check the French message
		try {
			String message = translationKey.getMessage(kind, Locale.FRENCH);
			Assert.assertTrue("Wrong French message for the key", "fr-test".equals(message));
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
		
		// check the German message
		Assert.assertTrue("Wrong translation key for german language", "123.text".equals(key));
		try {
			String message = translationKey.getMessage(kind, Locale.GERMAN);
			Assert.assertTrue("Wrong message for the german key", null == message);
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testEventTranslationKeyWhenNoTranslation() {
		// prepare data
		Event event = ModelFactory.eINSTANCE.createEvent("OnClick");
		EventTranslationProvider transProvider = new EventTranslationProvider();
		EventTranslation eventTranslation = new EventTranslation(transProvider, getProject(), event); 
		EventTranslationKeyProvider provider = new EventTranslationKeyProvider();
		
		// retrieve translation key		
		ITranslationKey translationKey = provider.getTranslationKey(project, eventTranslation);
		Assert.assertTrue("Cannot retrieve the translation key.", translationKey != null);
		
		// check translation kinds
		ITranslationKind kinds[] = translationKey.getTranslationKinds();
		Assert.assertTrue("Wrong number of translation kinds", kinds.length == 1);
		Assert.assertTrue("Wrong translation kind", kinds[0] == ITranslationKind.NAME);
		
		// check wrapped translation
		ITranslation translation = translationKey.getTranslation();
		Assert.assertTrue("Missing wrapped event translation", translation != null);
		Assert.assertTrue("Missing wrapped event ", translation.getOwner() != null);
		Assert.assertTrue("Wrong wrapped event instance", event == translation.getOwner());
		
		ITranslationKind kind = ITranslationKind.NAME;

		String key = translationKey.getKey(kind);
		Assert.assertTrue("The message key must be null if no translation has been defined", null == key);

		try {
			String message = translationKey.getMessage(kind, Locale.ENGLISH);
			Assert.assertTrue("The message must be null if no translation has been defined", null == message);
			
		} catch (TranslationException ex) {
			ex.printStackTrace();
			Assert.fail("Unexpected exception");
		}
		
	}

}

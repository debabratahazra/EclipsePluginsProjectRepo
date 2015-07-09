package com.odcgroup.translation.generation.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.generation.ITranslationKey;
import com.odcgroup.translation.generation.internal.generator.nls.MessageStore;

/**
 * TODO: Document me!
 *
 * @author amc
 *
 */
public class MessageStoreTest {
	
	private static final String UNEXPECTED_MESSAGES_ASSERT_ERROR = "Unexpected extra key/value(s) found in messages";
	
	private static final String NAME_KEY1 = "NameKey1";
	private static final String NAME_KEY2 = "NameKey2";
	private static final String TEXT_KEY1 = "TextKey1";
	private static final String TEXT_KEY2 = "TextKey2";
	
	private static final String ENGLISH_NAME_MESSAGE1 = "EnglishNameMessage1";
	private static final String ENGLISH_NAME_MESSAGE2 = "EnglishNameMessage2";
	private static final String ENGLISH_TEXT_MESSAGE1 = "EnglishTextMessage1";
	private static final String ENGLISH_TEXT_MESSAGE2 = "EnglishTextMessage2";
	
	private static final String FRENCH_NAME_MESSAGE1 = "FrenchNameMessage1";
	private static final String FRENCH_NAME_MESSAGE2 = "FrenchNameMessage2";
	private static final String FRENCH_TEXT_MESSAGE1 = "FrenchTextMessage1";
	private static final String FRENCH_TEXT_MESSAGE2 = "FrenchTextMessage2";
	
	private static final ITranslationKind[] TRANSLATION_KINDS = new ITranslationKind[] {ITranslationKind.NAME, ITranslationKind.TEXT};
	
	private MessageStore messageStore;
	
	@Before
	public void setUp() throws Exception {
		
		messageStore = new MessageStore();
	}
	
	@Test
	public void testWhenNoTranslationsEmptyMapIsRetrieved() throws Exception {
		Map<String, String> messages = messageStore.get(Locale.ENGLISH);
		Assert.assertNotNull(messages);
		Assert.assertEquals(0, messages.size());
	}
	
	@Test
	public void testWhenSingleLocalePresentThatOneAddedTranslationCanBeRetrieved() throws Exception {
		List<Locale> locales = createENOnlyLocalesList();
		
		ITranslationKey key = mock(ITranslationKey.class);
		when(key.getTranslationKinds()).thenReturn(TRANSLATION_KINDS);
		when(key.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key.getMessage(ITranslationKind.NAME, Locale.ENGLISH)).thenReturn(ENGLISH_NAME_MESSAGE1);
		when(key.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key.getMessage(ITranslationKind.TEXT, Locale.ENGLISH)).thenReturn(ENGLISH_TEXT_MESSAGE1);
		
		messageStore.addTranslation(key, locales);
		
		Map<String, String> messages = messageStore.get(Locale.ENGLISH);
		Assert.assertNotNull(messages);
		Assert.assertEquals(ENGLISH_NAME_MESSAGE1, messages.get(NAME_KEY1));
		Assert.assertEquals(ENGLISH_TEXT_MESSAGE1, messages.get(TEXT_KEY1));
		Assert.assertEquals(UNEXPECTED_MESSAGES_ASSERT_ERROR, 2, messages.size());
	}
	
	@Test
	public void testWhenMultipleLocalePresentThatOneAddedTranslationCanBeRetrieved() throws Exception {
		List<Locale> locales = createENAndFRLocalesList();
		
		ITranslationKey key = mock(ITranslationKey.class);
		when(key.getTranslationKinds()).thenReturn(TRANSLATION_KINDS);
		when(key.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key.getMessage(ITranslationKind.NAME, Locale.ENGLISH)).thenReturn(ENGLISH_NAME_MESSAGE1);
		when(key.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key.getMessage(ITranslationKind.TEXT, Locale.ENGLISH)).thenReturn(ENGLISH_TEXT_MESSAGE1);
		
		when(key.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key.getMessage(ITranslationKind.NAME, Locale.FRENCH)).thenReturn(FRENCH_NAME_MESSAGE1);
		when(key.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key.getMessage(ITranslationKind.TEXT, Locale.FRENCH)).thenReturn(FRENCH_TEXT_MESSAGE1);
		
		messageStore.addTranslation(key, locales);

		Map<String, String> englishMessages = messageStore.get(Locale.ENGLISH);
		Assert.assertNotNull(englishMessages);
		Assert.assertEquals(ENGLISH_NAME_MESSAGE1, englishMessages.get(NAME_KEY1));
		Assert.assertEquals(ENGLISH_TEXT_MESSAGE1, englishMessages.get(TEXT_KEY1));
		Assert.assertEquals(2, englishMessages.size());
		
		Map<String, String> frenchMessages = messageStore.get(Locale.FRENCH);
		Assert.assertNotNull(frenchMessages);
		Assert.assertEquals(FRENCH_NAME_MESSAGE1, frenchMessages.get(NAME_KEY1));
		Assert.assertEquals(FRENCH_TEXT_MESSAGE1, frenchMessages.get(TEXT_KEY1));
		Assert.assertEquals(UNEXPECTED_MESSAGES_ASSERT_ERROR, 2, frenchMessages.size());
	}
	
	@Test
	public void testWhenMultipleLocalePresentThatMultipleAddedTranslationCanBeRetrieved() throws Exception {
		List<Locale> locales = createENAndFRLocalesList();
		
		ITranslationKey key = mock(ITranslationKey.class);
		when(key.getTranslationKinds()).thenReturn(TRANSLATION_KINDS);
		when(key.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key.getMessage(ITranslationKind.NAME, Locale.ENGLISH)).thenReturn(ENGLISH_NAME_MESSAGE1);
		when(key.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key.getMessage(ITranslationKind.TEXT, Locale.ENGLISH)).thenReturn(ENGLISH_TEXT_MESSAGE1);
		
		when(key.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key.getMessage(ITranslationKind.NAME, Locale.FRENCH)).thenReturn(FRENCH_NAME_MESSAGE1);
		when(key.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key.getMessage(ITranslationKind.TEXT, Locale.FRENCH)).thenReturn(FRENCH_TEXT_MESSAGE1);
		
		ITranslationKey key2 = mock(ITranslationKey.class);
		when(key2.getTranslationKinds()).thenReturn(TRANSLATION_KINDS);
		when(key2.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY2);
		when(key2.getMessage(ITranslationKind.NAME, Locale.ENGLISH)).thenReturn(ENGLISH_NAME_MESSAGE2);
		when(key2.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY2);
		when(key2.getMessage(ITranslationKind.TEXT, Locale.ENGLISH)).thenReturn(ENGLISH_TEXT_MESSAGE2);
		
		when(key2.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY2);
		when(key2.getMessage(ITranslationKind.NAME, Locale.FRENCH)).thenReturn(FRENCH_NAME_MESSAGE2);
		when(key2.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY2);
		when(key2.getMessage(ITranslationKind.TEXT, Locale.FRENCH)).thenReturn(FRENCH_TEXT_MESSAGE2);
		
		messageStore.addTranslation(key, locales);
		messageStore.addTranslation(key2, locales);

		Map<String, String> englishMessages = messageStore.get(Locale.ENGLISH);
		Assert.assertNotNull(englishMessages);
		Assert.assertEquals(ENGLISH_NAME_MESSAGE1, englishMessages.get(NAME_KEY1));
		Assert.assertEquals(ENGLISH_TEXT_MESSAGE1, englishMessages.get(TEXT_KEY1));
		Assert.assertEquals(ENGLISH_NAME_MESSAGE2, englishMessages.get(NAME_KEY2));
		Assert.assertEquals(ENGLISH_TEXT_MESSAGE2, englishMessages.get(TEXT_KEY2));
		Assert.assertEquals(UNEXPECTED_MESSAGES_ASSERT_ERROR, 4, englishMessages.size());
		
		Map<String, String> frenchMessages = messageStore.get(Locale.FRENCH);
		Assert.assertNotNull(frenchMessages);
		Assert.assertEquals(FRENCH_NAME_MESSAGE1, frenchMessages.get(NAME_KEY1));
		Assert.assertEquals(FRENCH_TEXT_MESSAGE1, frenchMessages.get(TEXT_KEY1));
		Assert.assertEquals(FRENCH_NAME_MESSAGE2, frenchMessages.get(NAME_KEY2));
		Assert.assertEquals(FRENCH_TEXT_MESSAGE2, frenchMessages.get(TEXT_KEY2));
		Assert.assertEquals(UNEXPECTED_MESSAGES_ASSERT_ERROR, 4, frenchMessages.size());
	}
	
	@Test
	public void testThatNewMessagesWithTheSameKeyOverwritePreviouslyAddedMessages() throws Exception {
		List<Locale> locales = createENOnlyLocalesList();
		
		ITranslationKey key = mock(ITranslationKey.class);
		when(key.getTranslationKinds()).thenReturn(TRANSLATION_KINDS);
		when(key.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key.getMessage(ITranslationKind.NAME, Locale.ENGLISH)).thenReturn(ENGLISH_NAME_MESSAGE1);
		when(key.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key.getMessage(ITranslationKind.TEXT, Locale.ENGLISH)).thenReturn(ENGLISH_TEXT_MESSAGE1);
		
		ITranslationKey key2 = mock(ITranslationKey.class);
		when(key2.getTranslationKinds()).thenReturn(TRANSLATION_KINDS);
		when(key2.getKey(ITranslationKind.NAME)).thenReturn(NAME_KEY1);
		when(key2.getMessage(ITranslationKind.NAME, Locale.ENGLISH)).thenReturn(ENGLISH_NAME_MESSAGE2);
		when(key2.getKey(ITranslationKind.TEXT)).thenReturn(TEXT_KEY1);
		when(key2.getMessage(ITranslationKind.TEXT, Locale.ENGLISH)).thenReturn(ENGLISH_TEXT_MESSAGE2);
		
		messageStore.addTranslation(key, locales);
		messageStore.addTranslation(key2, locales);

		Map<String, String> messages = messageStore.get(Locale.ENGLISH);
		Assert.assertNotNull(messages);
		Assert.assertEquals(ENGLISH_NAME_MESSAGE2, messages.get(NAME_KEY1));
		Assert.assertEquals(ENGLISH_TEXT_MESSAGE2, messages.get(TEXT_KEY1));
		Assert.assertEquals(UNEXPECTED_MESSAGES_ASSERT_ERROR, 2, messages.size());
	}

	private List<Locale> createENOnlyLocalesList() {
		List<Locale> locales = new ArrayList<Locale>();
		locales.add(Locale.ENGLISH);
		return locales;
	}
	
	private List<Locale> createENAndFRLocalesList() {
		List<Locale> locales = createENOnlyLocalesList();
		locales.add(Locale.FRENCH);
		return locales;
	}
}

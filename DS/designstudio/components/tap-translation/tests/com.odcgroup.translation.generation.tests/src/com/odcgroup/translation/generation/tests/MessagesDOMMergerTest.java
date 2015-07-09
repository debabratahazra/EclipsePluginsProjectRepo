package com.odcgroup.translation.generation.tests;

import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.CATALOGUE_ELEMENT_NAME;
import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.MESSAGE_ELEMENT_NAME;
import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.MESSAGE_KEY_ATTR_NAME;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.junit.Assert;
import org.junit.Test;

import com.odcgroup.translation.generation.internal.generator.nls.MessagesDOMMerger;

public class MessagesDOMMergerTest {

	private static final String UPDATED_TRANSLATION = "UPATED_TRANSLATION";
	private static final String NEW_TRANSLATION_KEY0 = "NEW_TRANSLATION_KEY0";
	private static final String NEW_TRANSLATION_KEY1 = "NEW_TRANSLATION_KEY1";
	private static final String NEW_TRANSLATION_KEY2 = "NEW_TRANSLATION_KEY2";
	
	private static final String NEW_TRANSLATION0 = "NEW_TRANSLATION0";
	private static final String NEW_TRANSLATION1 = "NEW_TRANSLATION1";
	private static final String NEW_TRANSLATION2 = "NEW_TRANSLATION2";
	
	private static String KEY_PREFIX = "KEY";
	private static String VALUE_PREFIX = "VALUE";
	
	@Test
	public void testDomIsUnchangedWhenNoNewTranslationsAreProvided() throws Exception {
		Document document = createDocumentWithMessages();
		
		Map<String, String> newTranslations = new HashMap<String, String>();
		
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
		
		assertNumMessagesEquals(document, 3);
		assertMessageUnchanged(document, 0);
		assertMessageUnchanged(document, 1);
		assertMessageUnchanged(document, 2);
	}
	
	@Test
	public void testMessageIsUpdatedWhenUpdateToExistingTranslationIsProvided() throws Exception {
		Document document = createDocumentWithMessages();
		Map<String, String> newTranslations = new HashMap<String, String>();
		newTranslations.put(getMessageKey(1), UPDATED_TRANSLATION);
		
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
		
		assertNumMessagesEquals(document, 3);
		assertMessageUnchanged(document, 0);
		Element updatedMessage = getMessage(document, 1);
		assertMessageKeyEquals(getMessageKey(1), updatedMessage);
		assertMessageValueEquals(UPDATED_TRANSLATION, updatedMessage);
		assertMessageUnchanged(document, 2);
	}
	
	@Test
	public void testTranslationsWithEmptyValuesAreNotAppended() throws Exception {
		Document document = createDocumentWithMessages();
		Map<String, String> newTranslations = new HashMap<String, String>();
		newTranslations.put(NEW_TRANSLATION_KEY0, "");
		
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
		
		assertNumMessagesEquals(document, 3);
		assertMessageUnchanged(document, 0);
		assertMessageUnchanged(document, 1);
		assertMessageUnchanged(document, 2);
	}
	
	@Test
	public void testThatAfterMergingIntoEmptyCatalogueKeysAreInAscendingAlphabeticalOrder() throws Exception {
		Document document = createDocumentWithNoMessages();
		Map<String, String> newTranslations = new HashMap<String, String>();
		newTranslations.put(NEW_TRANSLATION_KEY0, NEW_TRANSLATION0);
		newTranslations.put(NEW_TRANSLATION_KEY1, NEW_TRANSLATION1);
		newTranslations.put(NEW_TRANSLATION_KEY2, NEW_TRANSLATION2);
		
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
		
		assertNumMessagesEquals(document, 3);
		assertMessageKeyAndValueEquals(document, 0, NEW_TRANSLATION_KEY0, NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 1, NEW_TRANSLATION_KEY1, NEW_TRANSLATION1);
		assertMessageKeyAndValueEquals(document, 2, NEW_TRANSLATION_KEY2, NEW_TRANSLATION2);
	}
	
	@Test
	public void testThatAfterMergingIntoExistingCatalogueKeysAreInAscendingAlphabeticalOrder() throws Exception {
		Document document = createEmptyDocument();
		Element catalogue = createEmptyCatalogue();
		addMessage(catalogue, "B", NEW_TRANSLATION0);
		addMessage(catalogue, "D", NEW_TRANSLATION0);
		addMessage(catalogue, "F", NEW_TRANSLATION0);
		document.setContent(catalogue);
		
		Map<String, String> newTranslations = new HashMap<String, String>();
		newTranslations.put("A", NEW_TRANSLATION0);
		newTranslations.put("C", NEW_TRANSLATION0);
		newTranslations.put("E", NEW_TRANSLATION0);
		newTranslations.put("G", NEW_TRANSLATION0);
		
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
		
		assertNumMessagesEquals(document, 7);
		assertMessageKeyAndValueEquals(document, 0, "A", NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 1, "B", NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 2, "C", NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 3, "D", NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 4, "E", NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 5, "F", NEW_TRANSLATION0);
		assertMessageKeyAndValueEquals(document, 6, "G", NEW_TRANSLATION0);
	}
	
	@Test
	public void testWhenExistingTranslationIsReplacedByEmptyStringThatMessageElementIsRemoved() throws Exception {
		Document document = createDocumentWithMessages();
		Map<String, String> newTranslations = new HashMap<String, String>();
		newTranslations.put(getMessageKey(1), "");
		
		MessagesDOMMerger merger = new MessagesDOMMerger(document, newTranslations);
		merger.merge();
		
		assertNumMessagesEquals(document, 2);
		assertMessageKeyAndValueEquals(document, 0, getMessageKey(0), getMessageValue(0));
		assertMessageKeyAndValueEquals(document, 1, getMessageKey(2), getMessageValue(2)); // message 2 will now be at position 1
	}

	private Document createDocumentWithMessages() {
		Document document = createEmptyDocument();
		addCatalogueWithMessages(document);
		return document;
	}
	
	private Document createDocumentWithNoMessages() {
		Document document = createEmptyDocument();
		document.addContent(createEmptyCatalogue());
		return document;
	}
	
	private Document createEmptyDocument() {
		Document document = new Document();
		document.addContent(new Comment("This is a comment node"));
		return document;
	}

	private void addCatalogueWithMessages(Document document) {
		document.addContent(createCatalogueWithMessages());
	}
	
	private Element createCatalogueWithMessages() {
		Element catalogueElement = createEmptyCatalogue();
		addMessage(catalogueElement, 0);
		addMessage(catalogueElement, 1);
		addMessage(catalogueElement, 2);
		return catalogueElement;
	}

	private Element createEmptyCatalogue() {
		Element catalogueElement = new Element(CATALOGUE_ELEMENT_NAME);
		return catalogueElement;
	}

	private void addMessage(Element catalogueElement, int messageNum) {
		addMessage(catalogueElement, getMessageKey(messageNum), getMessageValue(messageNum));
	}
	
	private void addMessage(Element catalogueElement, String key, String value) {
		Element messageElement = new Element(MESSAGE_ELEMENT_NAME);
		messageElement.setAttribute(MESSAGE_KEY_ATTR_NAME, key);
		messageElement.setText(value);
		catalogueElement.addContent(messageElement);
	}
	
	private void assertNumMessagesEquals(Document document, int expectedNumMessages) {
		Element catalogue = getCatalogueElement(document);
		Assert.assertNotNull(catalogue);
		List<Element> messages = getMessages(catalogue);
		Assert.assertEquals(expectedNumMessages, messages.size());
	}

	private void assertMessageKeyAndValueEquals(Document document, int messageNum, String key, String value) {
		Element updatedMessage = getMessage(document, messageNum);
		assertMessageKeyEquals(key, updatedMessage);
		assertMessageValueEquals(value, updatedMessage);
	}

	private void assertMessageKeyEquals(String expected, Element message) {
		Assert.assertEquals(expected, message.getAttributeValue(MESSAGE_KEY_ATTR_NAME));
	}

	private void assertMessageValueEquals(String expected, Element message) {
		Assert.assertEquals(expected, message.getValue());
	}
	
	private void assertMessageUnchanged(Document document, int messageNum) {
		Element message = getMessage(document, messageNum);
		assertMessageKeyEquals(getMessageKey(messageNum), message);
		assertMessageValueEquals(getMessageValue(messageNum), message);
	}

	private Element getMessage(Document document, int messageNum) {
		Element catalogueElement = getCatalogueElement(document);
		Element message = getMessage(catalogueElement, messageNum);
		Assert.assertNotNull(message);
		return message;
	}

	private Element getMessage(Element catalogueElement, int messageNum) {
		List<Element> messages = getMessages(catalogueElement);
		Assert.assertTrue("No message found at index "+messageNum, messages.size() > messageNum);
		Element message = (Element)messages.get(messageNum);
		return message;
	}

	@SuppressWarnings("unchecked")
	private List<Element> getMessages(Element catalogueElement) {
		List<Element> messages = (List<Element>)catalogueElement.getChildren();
		return messages;
	}
	
	private Element getCatalogueElement(Document document) {
		Element catalogueElement = document.getRootElement();
		Assert.assertEquals(catalogueElement.getName(), CATALOGUE_ELEMENT_NAME);
		return catalogueElement;
	}
	
	private String getMessageValue(int messageNum) {
		return VALUE_PREFIX+messageNum;
	}

	private String getMessageKey(int messageNum) {
		return KEY_PREFIX+messageNum;
	}
	
}

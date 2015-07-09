package com.odcgroup.translation.generation.internal.generator.nls;

import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.I18N_NAMESPACE;
import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.MESSAGE_ELEMENT_NAME;
import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.MESSAGE_KEY_ATTR_NAME;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;

/**
 * Takes a set of updated and new translations and merges them into an existing document
 * updating any existing messages and adding new ones to the end 
 *
 * @author amc
 *
 */
public class MessagesDOMMerger {
	
	private Document document;
	private Map<String, String> newAndUpdatedTranslations;
	private Set<String> newKeys; // initially contains all keys, but will contain only new keys at end of parsing
	
	/**
	 * @param document document to be updated with {@link #newAndUpdatedTranslations}
	 * @param newAndUpdatedTranslations any updated or new translations from the models undergoing generation
	 */
	public MessagesDOMMerger(Document document, Map<String, String> newAndUpdatedTranslations) {
		this.document = document;
		this.newAndUpdatedTranslations = newAndUpdatedTranslations;
	}

	/**
	 * Updates any existing messages and inserts new messages in key order to the document's catalogue element
	 */
	public void merge() {
		initNewKeys(newAndUpdatedTranslations);
		Element catalogue = document.getRootElement();
		List<Element> messages = getMessages(catalogue);
		updateMessages(messages); 
		insertNewMessages(messages);
		sortMessages(catalogue);
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void sortMessages(Element parent) {
        List children = parent.removeContent();
        Collections.sort(children, MessageKeyComparator.getInstance());
        parent.addContent(children);
    }
    
	private void initNewKeys(Map<String, String> newAndUpdatedTranslations) {
		newKeys = new HashSet<String>(newAndUpdatedTranslations.keySet());
	}
	
	private void updateMessages(List<Element> messages) {
		Iterator<Element> iterator = messages.iterator();
		while(iterator.hasNext()) {
			Element message = iterator.next();
			updateMessage(message);
			if(shouldRemoveMessage(message)) {
				iterator.remove();
			}
		}
	}

	private boolean shouldRemoveMessage(Element message) {
		return "".equals(message.getValue());
	}

	private void updateMessage(Element message) {
		String key = message.getAttribute(MESSAGE_KEY_ATTR_NAME).getValue();
		String newTranslation = newAndUpdatedTranslations.get(key);
		if(newTranslation != null) {
			replaceTranslation(message, key, newTranslation);
		}
	}
	
	private void insertNewMessages(List<Element> messages) {
		for(String key : newKeys) {
			String value = newAndUpdatedTranslations.get(key);
			if(StringUtils.isNotEmpty(value)) {
				Element newMessage = createMessageElement(key, value);
				messages.add(newMessage);
			}
		}
	}

	private Element createMessageElement(String key, String value) {
		Element message = new Element(MESSAGE_ELEMENT_NAME, I18N_NAMESPACE);
		message.setAttribute(MESSAGE_KEY_ATTR_NAME, key);
		message.setText(value);
		return message;
	}
	

	private void replaceTranslation(Element message, String key, String newTranslation) {
		message.setText(newTranslation);
		newKeys.remove(key);
	}
	
	@SuppressWarnings("unchecked")
	private List<Element> getMessages(Element catalogue) {
		List contents = catalogue.getContent();
		for(int i = contents.size() - 1; i >= 0; i--) {
			Object content = contents.get(i);
			if(!(content instanceof Element)) {
				contents.remove(i);
			}
		}
		return (List<Element>)contents;
	}
}

package com.odcgroup.translation.generation.internal.generator.nls;

import static com.odcgroup.translation.generation.internal.generator.nls.MessagesXMLConstants.MESSAGE_KEY_ATTR_NAME;

import java.util.Comparator;

import org.jdom.Element;

/**
 *
 * @author amc
 *
 */
public class MessageKeyComparator implements Comparator<Element> {

	private static final MessageKeyComparator instance = new MessageKeyComparator();
	
	private MessageKeyComparator() {
		// singleton
	}
	
	public static MessageKeyComparator getInstance() {
		return instance;
	}	
	
	@Override
	public int compare(Element elem1, Element elem2) {
		String key1 = elem1.getAttributeValue(MESSAGE_KEY_ATTR_NAME);
		String key2 = elem2.getAttributeValue(MESSAGE_KEY_ATTR_NAME);
		return key1.compareTo(key2);
	}

}

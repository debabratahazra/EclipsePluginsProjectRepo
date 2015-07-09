package com.odcgroup.page.translation.migration.internal;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author atr
 */
class PageModelSaxHandler extends DefaultHandler implements IPageModelHandler {
	
	/** All the elements that can contains a translation key */
	private Set<String> i18nTags = null;
	
	private Stack<String> stack = new Stack<String>();
	
	private Map<String /* xmi-id */, Set<String> /* keys */> xmiIdMap;
	
	boolean findKey = false;

	@Override
	public void startDocument() throws SAXException {
		findKey = false;
		i18nTags = new HashSet<String>();
		i18nTags.add("text");
		i18nTags.add("tooltip");
		i18nTags.add("caption");
		i18nTags.add("name");
		i18nTags.add("confirm");
	}

	@Override
	public void endDocument() throws SAXException {
		if (!stack.isEmpty()) {
			throw new IllegalStateException();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("contents") || qName.equals("events") || qName.equals("widget")) {
			findKey = true;
			//String typeName = attributes.getValue("typeName");
			String xmiId = attributes.getValue("xmi:id");
			stack.push(xmiId);
		} else if (qName.equals("properties") && findKey) {
			String type = attributes.getValue("typeName");
			if (i18nTags.contains(type)) {
				String key = attributes.getValue("value");
				if (StringUtils.isNotEmpty(key)) {
					Set<String> keySet = xmiIdMap.get(stack.peek());
					if (keySet == null) {
						keySet = new HashSet<String>();
						xmiIdMap.put(stack.peek(), keySet);
					}
					keySet.add(key);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("contents") || qName.equals("events") || qName.equals("widget")) {
			// done with this element
			findKey = false;
			stack.pop();
		}
	}
	
	@Override
	public final DefaultHandler getHandler() {
		return this;
	}

	@Override
	public final void setDataMap(Map<String, Set<String>> map) {
		if (null == map) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.xmiIdMap = map;
	}

	/**
	 * Constructor
	 */
	public PageModelSaxHandler() {
	}

}

package com.odcgroup.page.translation.migration.internal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Look for all numerical messsage keys.
 * @author atr
 */
class MessageRepositorySaxHandler extends DefaultHandler implements IMessageRepositoryHandler {
	
	private class Message {
		String lang;
		String text;
		String gid;
		public Message(String language, String text, String gid) {
			this.lang = language;
			this.text = text;
			this.gid = gid;
		}
	}
	
	private Map<String/*key*/, Map<String/*lang*/, String/*text*/>> keyMap;

	private Map<String/*msg*/, Message> msgMap; 

	private String language;

	@Override
	public void startDocument() throws SAXException {
		msgMap = new HashMap<String, Message>();
	}

	@Override
	public void endDocument() throws SAXException {
		msgMap.clear();
		msgMap = null;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equals("catalogues")) {
			language = attributes.getValue("locale");
		} else if (qName.equals("messages") && language != null) {
			String gid = attributes.getValue("group");
			if (StringUtils.isNotBlank(gid)) {
				String text = attributes.getValue("value");
				if (StringUtils.isNotBlank(text)) {
					String mid  = attributes.getValue("xmi:id");
					msgMap.put(mid, new Message(language, text, gid));
				}
			}
		} else if (qName.equals("groups")) {
			String gid = attributes.getValue("xmi:id");
			if (StringUtils.isNotBlank(gid)) {
				String key = attributes.getValue("key");
				if (StringUtils.isNotBlank(key)) {
					key = key.trim();
					if (Character.isDigit(key.charAt(0))) {
						// numerical key
						String mids = attributes.getValue("messages");
						if (StringUtils.isNotBlank(mids)) {
							mids = mids.trim();
							String[]midArray = mids.split(" ");
							for (String mid : midArray) {
								Message message = msgMap.get(mid);
								if (message != null) {
									// add message to the global keyMap
									Map<String, String> map = keyMap.get(key);
									if (map == null) {
										map = new HashMap<String, String>();
										keyMap.put(key, map);
									}
									map.put(message.lang, message.text);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equals("catalogues")) {
			language = null;
		}
	}

	@Override
	public final DefaultHandler getHandler() {
		return this;
	}

	@Override
	public void setDataMap(Map<String, Map<String, String>> keyMap) {
		this.keyMap = keyMap;
	}

}

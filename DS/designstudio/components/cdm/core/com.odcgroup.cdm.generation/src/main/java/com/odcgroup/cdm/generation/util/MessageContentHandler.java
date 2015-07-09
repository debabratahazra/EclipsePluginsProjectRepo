package com.odcgroup.cdm.generation.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Message content handler used to import messages.xml files into a MessageRepository.
 * 
 * @author Gary Hayes
 */
public class MessageContentHandler extends DefaultHandler {
	/** The String used when we are inside an element. */
	private StringBuilder value;

	/**
	 * Creates a new CodeContentHandler.
	 * 
	 */
	public MessageContentHandler() {
	}

	/**
	 * Method called when we start an element.
	 * 
	 * @param uri
	 *            The uri of the element
	 * @param localName
	 *            The local name of the element
	 * @param qName
	 *            The qualified name of the element
	 * @param attributes
	 *            The list of Attributes
	 * @throws SAXException
	 *             Thrown if an error occurs
	 */
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	}

	/**
	 * Method called when encounter text.
	 * 
	 * @param ch
	 *            The array of characters
	 * @param start
	 *            The start of the character sequence
	 * @param length
	 *            The length of characters which apply
	 */
	public void characters(char[] ch, int start, int length) {
		value.append(ch, start, length);
	}

	/**
	 * Method called when we end an element.
	 * 
	 * @param uri
	 *            The uri of the element
	 * @param localName
	 *            The local name of the element
	 * @param qName
	 *            The qualified name of the element
	 * @throws SAXException
	 *             Thrown if an error occurs
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
	}
}

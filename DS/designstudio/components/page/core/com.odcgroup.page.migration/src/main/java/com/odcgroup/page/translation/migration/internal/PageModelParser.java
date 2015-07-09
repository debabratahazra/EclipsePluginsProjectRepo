package com.odcgroup.page.translation.migration.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * @author atr
 */
class PageModelParser {
	
	private SAXParser parser; 
	
	private IPageModelHandler handler;
	
	/**
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	protected SAXParser createParser() throws ParserConfigurationException, SAXException {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = spf.newSAXParser();
		return parser;
	}
	
	/**
	 * @return
	 */
	protected IPageModelHandler createHandler() {
		return new PageModelSaxHandler();
	}
	
	/**
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	protected final SAXParser getParser() throws ParserConfigurationException, SAXException {
		if (null == parser) {
			parser = createParser();
		}
		return parser;
	}
	
	/**
	 * @return
	 */
	protected final IPageModelHandler getHandler() {
		if (null == handler) {
			handler = createHandler();
		}
		return handler;
	}
	
	/**
	 * @param stream
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public void parse(InputStream is, Map<String, Set<String>> map) throws SAXException, IOException, ParserConfigurationException {
		if (null == is) {
			throw new IllegalArgumentException("Argument cannot be null");
		}
		InputSource source = new InputSource();
		source.setByteStream(is);
		
		IPageModelHandler modelHandler = getHandler();
		modelHandler.setDataMap(map);
		getParser().parse(source, modelHandler.getHandler());
	}
	
	public PageModelParser() {
		super();
	}

}

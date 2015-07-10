package com.zealcore.se.core.importer.lttng;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXXmlParser extends DefaultHandler {

	private String folder = null;
	private String tempVal = null;
	
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
			//reset
			tempVal = "";
			
	 }


	 public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	 }

	 public void endElement(String uri, String localName,
		String qName) throws SAXException {

		if(qName.equalsIgnoreCase("Folder")) {
			//add it to the list
			folder = tempVal;

		 }

	 }
	 
	 public void parseDocument(String file) {

			//get a factory
			SAXParserFactory spf = SAXParserFactory.newInstance();
			try {

				//get a new instance of parser
				SAXParser sp = spf.newSAXParser();
				//parse the file and also register this class for call backs
				sp.parse(file, this);

			}catch(SAXException se) {
				se.printStackTrace();
			}catch(ParserConfigurationException pce) {
				pce.printStackTrace();
			}catch (IOException ie) {
				ie.printStackTrace();
			}
	 	 }
		
	 public void parseDocument(InputStream file) {

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			//parse the file and also register this class for call backs
			sp.parse(file, this);

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
 	 }
	 
	public String getFolder() {
		return folder;
	}


	public void setFolder(String folder) {
		this.folder = folder;
	}

}

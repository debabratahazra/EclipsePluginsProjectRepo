package com.odcgroup.cdm.generation.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.command.CompoundCommand;
import org.xml.sax.SAXException;

/**
 * Reads OCS messages into a map.
 * 
 * @author Gary Hayes
 */
public class OcsMessagesReader {
    
    /** The locale of the messages. */
    private String locale;
    
    /** The messages. */
    private Map<String, String> messages;
    
    /**
     * Creates a new OcsMessagesReader.
     * 
     * @param fileName The fully-qualified name of the file to import
     */
    public OcsMessagesReader(String fileName) {
        messages = new HashMap<String, String>();
        importMessages(fileName);
    }
    
    /**
     * Imports the messages.
     * 
     * @param fileName The name of the file to read
     */
    private void importMessages(String fileName) {

    	// First import the messaged into a temporary MessageRepository
        // we do this to make use of the existing MessageContentHandler
        
        try {
            FileInputStream messageFile = new FileInputStream(fileName);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            CompoundCommand compoundCommand = new CompoundCommand();
            MessageContentHandler handler = new MessageContentHandler();
            saxParser.getXMLReader().setFeature("http://xml.org/sax/features/namespaces", Boolean.TRUE);
            saxParser.parse(messageFile, handler);
            compoundCommand.execute();

        } catch (SAXException ex) {
            throw new RuntimeException("SAX Exception with file " + fileName, ex);
        } catch (ParserConfigurationException pce) {
            throw new RuntimeException("SAX Exception with file " + fileName, pce);
        } catch (IOException ex) {
            throw new RuntimeException("SAX Exception with file " + fileName, ex);
        }
        
//        // Now create the messages map
//        for (MessageGroup mg : tmr.getGroups()) {
//            String key = mg.getKey();
//            // The message content handler creates a single Message in each group
//            String value = mg.getMessages().get(0).getValue();
//            messages.put(key, value);
//        }
        
        // Find the Locale from the filename.
        // First remove the file extension
        String s = fileName.substring(0, fileName.length() - 4);
        int i = s.lastIndexOf("messages");
        if (i == s.length() - 8) {
            locale = "en";
        } else {
            locale = s.substring(i + 8 + 1);
        }
    }

    /**
     * Gets the locale.
     * 
     * @return String The locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Gets the messages.
     * 
     * @return Map of message keys and values
     */
    public Map<String, String> getMessages() {
        return messages;
    }
}
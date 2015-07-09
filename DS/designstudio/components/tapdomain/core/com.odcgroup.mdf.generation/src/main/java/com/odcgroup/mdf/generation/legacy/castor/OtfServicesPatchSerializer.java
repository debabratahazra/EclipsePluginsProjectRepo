package com.odcgroup.mdf.generation.legacy.castor;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * A serializer for a otf-services patch file.
 * 
 * @author <a href="mailto:dnemeshazy@odyssey-group.com">David Nemeshazy </a>
 * @version 1.0
 */
public class OtfServicesPatchSerializer extends CastorSerializer {
    /**
     * DOCUMENT ME!
     * 
     * @param handler
     * @param messageHandler
     */
    public OtfServicesPatchSerializer(ContentHandler handler,
            MessageHandler messageHandler) {
        super(handler, messageHandler);
    }

    /**
     * @see com.odcgroup.mdf.generation..castor.CastorSerializer#handle(com.odcgroup.mdf.metamodel.MdfDomain)
     */
    public void handle(MdfDomain domain) throws SAXException {
    	
    	AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "xpath", "/otf-services"); 
        
        startElement("patch", attrs );
        
    	attrs = new AttributesImpl();
        addAttribute(attrs, "name", "jdbc/"+domain.getName()); 
        addAttribute(attrs, "href", "jdbc/advisor"); 
        
        startElement("services:locator-ref", attrs );
        endElement("services:locator-ref");

        endElement("patch");
    }

   
    
}
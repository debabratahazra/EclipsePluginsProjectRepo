package com.odcgroup.mdf.generation.legacy.castor;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.metamodel.MdfDomain;

/**
 * A serializer for JDO database file.
 * 
 * @author <a href="mailto:dnemeshazy@odyssey-group.com">David Nemeshazy </a>
 * @version 1.0
 */
public class CastorJDODatabaseSerializer extends CastorSerializer {
    /**
     * DOCUMENT ME!
     * 
     * @param handler
     * @param messageHandler
     */
    public CastorJDODatabaseSerializer(ContentHandler handler,
            MessageHandler messageHandler) {
        super(handler, messageHandler);
    }

    /**
     * @see com.odcgroup.mdf.generation.legacy.castor.CastorSerializer#handle(com.odcgroup.mdf.metamodel.MdfDomain)
     */
    public void handle(MdfDomain domain) throws SAXException {
    	AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", domain.getName()); 
        addAttribute(attrs, "engine", "generic"); 
        
        startElement("database", attrs);
        
        attrs = new AttributesImpl();
        addAttribute(attrs, "class-name", "com.odcgroup.otf.castor.OtfDataSource"); 
        startElement("data-source", attrs);
        
        attrs = new AttributesImpl();
        addAttribute(attrs, "pool-name", domain.getName()); 
        
        startElement("params", attrs);
        endElement("params");
        endElement("data-source");
        

        attrs = new AttributesImpl();
        addAttribute(attrs, "href", CastorGenerationCore.getDomainJDOMappingName(domain.getName())); 

        startElement("mapping", attrs);
        endElement("mapping");
        
        endElement("database");
    }

   
    
}
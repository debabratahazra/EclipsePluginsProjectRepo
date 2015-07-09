package com.odcgroup.mdf.generation.legacy.xsd;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.common.mdf.generation.legacy.ModelWriter;
import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.utils.Slf4jMessageHandler;


/**
 * This class represents a MML to XML Schema transformer.
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class XSDModelWriter extends SchemaCore implements ModelWriter {
    private static Logger LOGGER = LoggerFactory.getLogger(XSDModelWriter.class);
    private static final String COPYRIGHT =
        "Schema automatically writen by Odyssey Schema generator. Do not modify.";
    private MessageHandler messageHandler = new Slf4jMessageHandler(LOGGER);

    public XSDModelWriter() {
        super();
    }
    
    /**
     * @see com.odcgroup.mdf.ModelWriter#setMessageHandler(com.odcgroup.mdf.MessageHandler)
     */
    public void setMessageHandler(MessageHandler handler) {
        if (handler == null) throw new NullPointerException("handler");
        this.messageHandler = handler;
    }

    /**
     * @see com.odcgroup.mdf.transform.ModelWriter#getName()
     */
    public String getName() {
        return "XML Schema generator";
    }

	/**
	 * @see com.odcgroup.mdf.ModelWriter#write(com.odcgroup.mdf.metamodel.MdfDomain, java.io.File)
	 */
    public void write(MdfDomain domain, Collection<MdfDomain> importedDomains,
			OutputStreamFactory factory) throws IOException {
        String path = getDomainSchemaPath(domain.getName());
        messageHandler.info("Writing XML Schema for domain ["
            + domain.getName() + "] to path [" + path + "]");
            
        OutputStream schema = factory.openStream(path);

        try {
            OutputStream out =
                new BufferedOutputStream(schema);
            XMLSerializer xml =
                new XMLSerializer(out,
                    new OutputFormat(Method.XML, "UTF-8", true));
            SchemaSerializer serializer = new SchemaSerializer(xml, messageHandler);
    
            try {
                xml.startDocument();
    			xml.comment(COPYRIGHT);
    
                xml.startPrefixMapping(XSD_PREFIX, XSD_NAMESPACE);
                xml.startPrefixMapping("", domain.getNamespace());
    
                Iterator it = importedDomains.iterator();
    
                while (it.hasNext()) {
                    MdfDomain imp = (MdfDomain) it.next();
                    xml.startPrefixMapping(imp.getName(), imp.getNamespace());
                }
    
                AttributesImpl attrs = new AttributesImpl();
    			serializer.addAttribute(attrs, "targetNamespace", domain.getNamespace());
    			serializer.addAttribute(attrs, "elementFormDefault", "qualified");
    			serializer.addAttribute(attrs, "attributeFormDefault", "unqualified");
    
    			serializer.startElement("schema", attrs);
                serializer.handle(domain, importedDomains);
    			serializer.endElement("schema");
    			
    			it = importedDomains.iterator();
    
    			while (it.hasNext()) {
    				MdfDomain imp = (MdfDomain) it.next();
    				xml.endPrefixMapping(imp.getName());
    			}
                xml.endPrefixMapping("");
    			xml.endPrefixMapping(XSD_PREFIX);
    
                xml.endDocument();
    
                messageHandler.info("Done writing XML schema for domain ["
                    + domain.getName() + "]");
            } catch (SAXException e) {
                messageHandler.error(e.getLocalizedMessage(), e);
                throw new IOException(e.getMessage());
            }
        } finally {
            if (schema != null) schema.close();
        }
    }

}

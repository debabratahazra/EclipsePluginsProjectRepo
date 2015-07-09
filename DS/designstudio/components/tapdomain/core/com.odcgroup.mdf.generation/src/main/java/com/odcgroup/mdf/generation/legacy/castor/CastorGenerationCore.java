package com.odcgroup.mdf.generation.legacy.castor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.OutputStreamFactory;
import com.odcgroup.mdf.core.ObjectId;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.transform.castor.CastorCore;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.utils.castor.fieldhandler.ObjectIdFieldHandler;
import com.odcgroup.otf.castor.fieldHandler.URIFieldHandler;
import com.odcgroup.otf.xml.XmlConstants;

/**
 * @author yan
 */
public class CastorGenerationCore extends CastorCore {

    /** TODO: DOCUMENT ME! */
    public static final String COLLECTION = "arraylist";

    /** TODO: DOCUMENT ME! */
    public static final boolean SCHEMA_USE;
    private static final String COPYRIGHT = "Mapping automatically writen by Odyssey Castor Mapping generator. Do not modify.";
    private static final Map MML2CASTOR;
    private static final Map MML2CASTORJDO;
    private static final Map FIELD_HANDLERS;

    private static final String MML2CASTORJDO_REF_DB_TYPE = "varchar";
    private static final String REF_FIELD_HANDLER = ObjectIdFieldHandler.class.getName();
    private static final String REF_CASTOR_TYPE = ObjectId.class.getName();
    
    static {
        Map mml2castor = new HashMap();

        mml2castor.put(PrimitivesDomain.BOOLEAN, "boolean");
        mml2castor.put(PrimitivesDomain.BYTE, "byte");
        mml2castor.put(PrimitivesDomain.CHAR, "char");
        mml2castor.put(PrimitivesDomain.DATE, "date");
        mml2castor.put(PrimitivesDomain.DATE_TIME, "date");
        mml2castor.put(PrimitivesDomain.DOUBLE, "double");
        mml2castor.put(PrimitivesDomain.FLOAT, "float");
        mml2castor.put(PrimitivesDomain.INTEGER, "integer");
        mml2castor.put(PrimitivesDomain.LONG, "long");
        mml2castor.put(PrimitivesDomain.SHORT, "short");
        mml2castor.put(PrimitivesDomain.STRING, "string");
        mml2castor.put(PrimitivesDomain.URI, "string");
        mml2castor.put(PrimitivesDomain.DECIMAL, "double");

        mml2castor.put(PrimitivesDomain.BOOLEAN_OBJ, "boolean");
        mml2castor.put(PrimitivesDomain.BYTE_OBJ, "byte");
        mml2castor.put(PrimitivesDomain.CHAR_OBJ, "char");
        mml2castor.put(PrimitivesDomain.DOUBLE_OBJ, "double");
        mml2castor.put(PrimitivesDomain.FLOAT_OBJ, "float");
        mml2castor.put(PrimitivesDomain.INTEGER_OBJ, "integer");
        mml2castor.put(PrimitivesDomain.LONG_OBJ, "long");
        mml2castor.put(PrimitivesDomain.SHORT_OBJ, "short");

        MML2CASTOR = Collections.unmodifiableMap(mml2castor);

        Map mml2castorjdo = new HashMap();

        mml2castorjdo.put(PrimitivesDomain.BOOLEAN, "bit");
        mml2castorjdo.put(PrimitivesDomain.BYTE, "tinyint");
        mml2castorjdo.put(PrimitivesDomain.CHAR, "char");
        mml2castorjdo.put(PrimitivesDomain.DATE, "timestamp");
        mml2castorjdo.put(PrimitivesDomain.DATE_TIME, "timestamp");
        mml2castorjdo.put(PrimitivesDomain.DOUBLE, "double");
        mml2castorjdo.put(PrimitivesDomain.FLOAT, "numeric");
        mml2castorjdo.put(PrimitivesDomain.INTEGER, "integer");
        mml2castorjdo.put(PrimitivesDomain.LONG, "numeric");
        mml2castorjdo.put(PrimitivesDomain.SHORT, "smallint");
        mml2castorjdo.put(PrimitivesDomain.STRING, "varchar");
        mml2castorjdo.put(PrimitivesDomain.URI, "varchar");
        mml2castorjdo.put(PrimitivesDomain.DECIMAL, "double");

        mml2castorjdo.put(PrimitivesDomain.BOOLEAN_OBJ, "bit");
        mml2castorjdo.put(PrimitivesDomain.BYTE_OBJ, "tinyint");
        mml2castorjdo.put(PrimitivesDomain.CHAR_OBJ, "char");
        mml2castorjdo.put(PrimitivesDomain.DOUBLE_OBJ, "double");
        mml2castorjdo.put(PrimitivesDomain.FLOAT_OBJ, "numeric");
        mml2castorjdo.put(PrimitivesDomain.INTEGER_OBJ, "integer");
        mml2castorjdo.put(PrimitivesDomain.LONG_OBJ, "numeric");
        mml2castorjdo.put(PrimitivesDomain.SHORT_OBJ, "smallint");

        MML2CASTORJDO = Collections.unmodifiableMap(mml2castorjdo);

        Map handlers = new HashMap();
        handlers.put(PrimitivesDomain.URI, URIFieldHandler.class.getName());
        FIELD_HANDLERS = Collections.unmodifiableMap(handlers);

        String sup = System.getProperty(SCHEMA_USE_PROPERTY, "true");
        SCHEMA_USE = Boolean.valueOf(sup).booleanValue();
    }

    /**
     * Static method ro resolve type for mapping file.
     *
     * @param property The property to resolve
     *
     * @return TODO: DOCUMENT ME!
     */
    public static String getCastorType(MdfProperty property) {
        String type = (String) MML2CASTOR.get(externalType(property));

        if (type == null) {
            MdfEntity entity = property.getType();

            if (property instanceof MdfAssociation) {
                MdfAssociation assoc = (MdfAssociation) property;

                if (assoc.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) {
                    return JavaCore.getQualifiedRefClassName((MdfClass) entity);
                }
            } else if (entity instanceof MdfEnumeration) {
                entity = ((MdfEnumeration) entity).getType();
            } else if (entity instanceof MdfBusinessType) {
                entity = ((MdfBusinessType) entity).getType();
            }

            return getCastorType(entity);
        }

        return type;
    }

    /**
     * Static method to resolve type for mapping file using a MdfEntity
     *
     * @param entity TODO: DOCUMENT ME!
     *
     * @return TODO: DOCUMENT ME!
     */
    public static String getCastorType(MdfEntity entity) {
        String type = (String) MML2CASTOR.get(entity);

        if (type == null) {
            type = JavaCore.getQualifiedBeanClassName(entity);
        }

        return type;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @param attribute TODO: DOCUMENT ME!
     *
     * @return TODO: DOCUMENT ME!
     */
    public static String getDBType(MdfAttribute attribute) {

    	MdfEntity entity = attribute.getType();
    	
    	// support attributes of type enumeration...
    	if (entity instanceof MdfEnumeration) {
    		return (String) MML2CASTORJDO.get(((MdfEnumeration) entity).getType());
    	}
    	// support attributes of type business type...
    	if (entity instanceof MdfBusinessType) {
    		return (String) MML2CASTORJDO.get(((MdfBusinessType) entity).getType());
    	}
    	
        return (String) MML2CASTORJDO.get(attribute.getType());
    }
    
    public static String getRefDBType() {
    	return MML2CASTORJDO_REF_DB_TYPE;
    }
    
    public static String getRefCastorType() {
    	return REF_CASTOR_TYPE;
    }

    /**
     * Returns the path XML mapping of a domain.
     *
     * @param domain the domain name
     *
     * @return String the path of the XML mapping file
     */
    public static String getDomainJDOMappingPath(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF").append(File.separatorChar);
        buffer.append("mappings").append(File.separatorChar);
        buffer.append(getDomainJDOMappingName(domain));

        return buffer.toString();
    }
    
    public static String getDomainJDOMappingName(String domain) {
    	return CastorCore.NAME_FORMAT.format(domain)+".dbmap";
    }


    /**
     * Returns the path XML mapping of a domain.
     *
     * @param domain the domain name
     *
     * @return String the path of the XML mapping file
     */
    public static String getDomainJDODatabasePath(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF").append(File.separatorChar);
        buffer.append("mappings").append(File.separatorChar);
        buffer.append(getDomainJDODatabaseName(domain));

        return buffer.toString();
    }

    
    public static String getDomainJDODatabaseName(String domain) {
    	return CastorCore.NAME_FORMAT.format(domain)+".dbconf";
    }


    /**
     * Returns the path XML mapping of a domain.
     *
     * @param domain the domain name
     *
     * @return String the path of the XML mapping file
     */
    public static String getOtfServicesPatchPath(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF").append(File.separatorChar);
        buffer.append("dcfg-patch").append(File.separatorChar);
        buffer.append(getOtfServicesPatchName(domain));

        return buffer.toString();
    }

    
    public static String getOtfServicesPatchName(String domain) {
    	return CastorCore.NAME_FORMAT.format(domain)+"-castor-ds-__otf-services__.patch";
    }
    /**
     * Returns the path XML mapping of a domain.
     *
     * @param domain the domain name
     *
     * @return String the path of the XML mapping file
     */
    public static String getDomainMappingPath(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF").append(File.separatorChar);
        buffer.append("mappings").append(File.separatorChar);
        buffer.append(CastorCore.NAME_FORMAT.format(domain));
        buffer.append(".xmap");

        return buffer.toString();
    }

    /**
     * Returns the resource path JDO mapping of a domain.
     *
     * @param domain the domain name
     *
     * @return String the path of the JDO mapping resource
     */
    public static String getDomainJDOMappingResource(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF/mappings/");
        buffer.append(getDomainJDOMappingName(domain));

        return buffer.toString();
    }
    


    /**
     * Returns the resource path JDO database file of a domain.
     *
     * @param domain the domain name
     *
     * @return String the path of the JDO database file resource
     */
    public static String getDomainJDODatabaseResource(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF/mappings/");
        buffer.append(getDomainJDODatabaseName(domain));

        return buffer.toString();
    }
    

    /**
     * Static method ro resolve field handler for mapping file.
     *
     * @param property The property to resolve
     *
     * @return String A string representation of the field handler
     */
    public static String getFieldHandler(MdfProperty property) {
        return getFieldHandler(externalType(property));
    }

    /**
     * Static method to resolve type for mapping file using a MdfEntity
     *
     * @param entity TODO: DOCUMENT ME!
     *
     * @return TODO: DOCUMENT ME!
     */
    public static String getFieldHandler(MdfEntity entity) {
        return (String) FIELD_HANDLERS.get(entity);
    }
    
    public static String getRefFieldHandler() {
    	return REF_FIELD_HANDLER;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @param domain TODO: DOCUMENT ME!
     * @param factory TODO: DOCUMENT ME!
     * @param messageHandler TODO: DOCUMENT ME!
     * @param forXML TODO: DOCUMENT ME!
     */
    public void writeDomain(MdfDomain domain, OutputStreamFactory factory,
        MessageHandler messageHandler, boolean forXML)
        throws IOException {
        String path = null;

        if (forXML) {
            path = getDomainMappingPath(domain.getName());
        } else {
            path = getDomainJDOMappingPath(domain.getName());
        }

        messageHandler.info("Writing castor mapping for domain [" +
            domain.getName() + "] to path [" + path + "]");

        OutputStream mapping = factory.openStream(path);

        try {
            OutputStream out = new BufferedOutputStream(mapping);
            XMLSerializer xml = new XMLSerializer(out,
                    new OutputFormat(Method.XML, "UTF-8", true));
            CastorSerializer serializer = null;

            if (forXML) {
                serializer = new CastorSerializer(xml, messageHandler);
            } else {
                serializer = new CastorJDOSerializer(xml, messageHandler);
            }

            try {
                xml.startDocument();
                xml.comment(COPYRIGHT);
                xml.startDTD("mapping",
                    "-//EXOLAB/Castor Mapping DTD Version 1.0//EN",
                    "http://tools.odcgroup.com/advisor/2.00.0/schemas/otf-castor-mapping.dtd");
                serializer.startElement("mapping", XmlConstants.EMPTY_ATTRIBUTES);
                serializer.startElement("description",
                    XmlConstants.EMPTY_ATTRIBUTES);
                serializer.characters("JDO Mapping file for domain " +
                    domain.getName());
                serializer.endElement("description");
                serializer.handle(domain);
                serializer.endElement("mapping");
                xml.endDocument();

                messageHandler.info("Done writing castor mapping for domain [" +
                    domain.getName() + "]");
            } catch (SAXException e) {
                messageHandler.error(e.getLocalizedMessage(), e);
                throw new IOException(e.getLocalizedMessage());
            }
        } finally {
            if (mapping != null) {
                mapping.close();
            }
        }
    }
    
    public void writeDatabaseFileForDomain(MdfDomain domain, OutputStreamFactory factory,
            MessageHandler messageHandler) throws IOException {
    	String path = getDomainJDODatabasePath(domain.getName());

        messageHandler.info("Writing castor database file for domain [" +
            domain.getName() + "] to path [" + path + "]");

        OutputStream mapping = factory.openStream(path);

        try {
            OutputStream out = new BufferedOutputStream(mapping);
            XMLSerializer xml = new XMLSerializer(out,
                    new OutputFormat(Method.XML, "UTF-8", true));
            CastorSerializer serializer = new CastorJDODatabaseSerializer(xml, messageHandler);

            try {
                xml.startDocument();
                xml.comment(COPYRIGHT);
                xml.startDTD("database",
                    "-//EXOLAB/Castor JDO Configuration DTD Version 1.0//EN",
                    "http://tools.odcgroup.com/advisor/schemas/2002-07/otf-castor-jdo-conf.dtd");
                
                serializer.handle(domain);
                
                xml.endDocument();

                messageHandler.info("Done writing castor database file for domain [" +
                    domain.getName() + "]");
            } catch (SAXException e) {
                messageHandler.error(e.getLocalizedMessage(), e);
                throw new IOException(e.getLocalizedMessage());
            }
        } finally {
            if (mapping != null) {
                mapping.close();
            }
        }
    	
    }
    
    
	public void writeOtfServicesPatch(MdfDomain domain, OutputStreamFactory factory,
            MessageHandler messageHandler) throws IOException {
    	String path = getOtfServicesPatchPath(domain.getName());

        messageHandler.info("Writing otf-services patch for datasource for domain [" +
            domain.getName() + "] to path [" + path + "]");

        OutputStream mapping = factory.openStream(path);

        try {
            OutputStream out = new BufferedOutputStream(mapping);
            
            XMLSerializer xml = new XMLSerializer(out,
                    new OutputFormat(Method.XML, "UTF-8", true));
            CastorSerializer serializer = new OtfServicesPatchSerializer(xml, messageHandler);

            try {
                xml.startDocument();
                xml.comment(COPYRIGHT);
                
                serializer.handle(domain);
                
                xml.endDocument();

                messageHandler.info("Done writing castor database file for domain [" +
                    domain.getName() + "]");
            } catch (SAXException e) {
                messageHandler.error(e.getLocalizedMessage(), e);
                throw new IOException(e.getLocalizedMessage());
            }
        } finally {
            if (mapping != null) {
                mapping.close();
            }
        }
    	
    }
    

    private static MdfEntity externalType(MdfProperty property) {
        MdfEntity entity = property.getType();

        if (entity instanceof MdfEnumeration) {
            entity = ((MdfEnumeration) entity).getType();
        }

        if (entity instanceof MdfBusinessType) {
            entity = ((MdfBusinessType) entity).getType();
        }

        return entity;
    }
}

package com.odcgroup.mdf.generation.legacy.castor;

import java.util.Iterator;
import java.util.List;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.ext.castor.CastorAspect;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.transform.java.JavaCore;

/**
 * A different serializer for JDO mapping. A JDO mapping is different, for now,
 * nothing for domain, refs, associations, etc... Only simple classes with
 * attributes
 * 
 * @author <a href="mailto:dnemeshazy@odyssey-group.com">David Nemeshazy </a>
 * @version 1.0
 */
public class CastorJDOSerializer extends CastorSerializer {
    /**
     * DOCUMENT ME!
     * 
     * @param handler
     * @param messageHandler
     */
    public CastorJDOSerializer(ContentHandler handler,
            MessageHandler messageHandler) {
        super(handler, messageHandler);
    }

    /**
     * @see com.odcgroup.mdf.generation.legacy.castor.CastorSerializer#handle(com.odcgroup.mdf.metamodel.MdfDomain)
     */
    public void handle(MdfDomain domain) throws SAXException {
        // we handle the domain differently from XML
        //		parse classes
        List classes = sortClasses(domain.getEntities());

        for (int i = 0; i < classes.size(); ++i) {
            handleClass((MdfClass) classes.get(i));
        }
    }

    private void handleAttribute(MdfAttribute attribute) throws SAXException {
        String columnName = SQLAspect.getSqlName(attribute);

        if (columnName == null) {
            columnName = attribute.getName();
        }

        String name = attribute.getName();
        info("Creating mapping for attribute " + name);

        String typeName = CastorGenerationCore.getCastorType(attribute);

        // field handler if needed, depending on the type of the attribute
        // if not needed, this is null
        String fieldHandler = CastorGenerationCore.getFieldHandler(attribute);

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", name); // Java field name!!
        addAttribute(attrs, "type", typeName); // java type, not XML!

        if (fieldHandler != null) {
            addAttribute(attrs, "handler", fieldHandler);
        }

        startElement("field", attrs);

        attrs = new AttributesImpl();
        addAttribute(attrs, "name", columnName);
        String dbType = CastorGenerationCore.getDBType(attribute);
        addAttribute(attrs, "type", dbType);

	if ("timestamp".equals(dbType)) {
		// dirty="ignore" is required for timestamp in order to avoid transaction exception
        	addAttribute(attrs, "dirty", "ignore");
	}

        startElement("sql", attrs);
        endElement("sql");

        endElement("field");
    }
    
    private void handleAssociation(MdfAssociation association) throws SAXException {
    	if ((association.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) && (association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE)) {
	    	String columnName = SQLAspect.getSqlName(association);
	
	        if (columnName == null) {
	            columnName = association.getName();
	        }
	
	        String name = association.getName()+"Ref";
	        info("Creating mapping for attribute " + name);
	
	        String typeName = CastorGenerationCore.getRefCastorType();
	
	        // field handler if needed, depending on the type of the attribute
	        // if not needed, this is null
	        String fieldHandler = CastorGenerationCore.getRefFieldHandler();
	
	        AttributesImpl attrs = new AttributesImpl();
	        addAttribute(attrs, "name", name); // Java field name!!
	        addAttribute(attrs, "type", typeName); // java type, not XML!
	
	        if (fieldHandler != null) {
	            addAttribute(attrs, "handler", fieldHandler);
	        }
	
	        startElement("field", attrs);
	
	        attrs = new AttributesImpl();
	        addAttribute(attrs, "name", columnName);
	        addAttribute(attrs, "type", CastorGenerationCore.getRefDBType());
	
	        startElement("sql", attrs);
	        endElement("sql");
	
	        endElement("field");
    	}
    }

    private void handleClass(MdfClass klass) throws SAXException {
        String tableName = SQLAspect.getSqlName(klass);

        if (tableName != null) {
            info("Creating bean mapping for class "
                    + klass.getName());

            AttributesImpl attrs = new AttributesImpl();
            addAttribute(attrs, "name", JavaCore
                    .getQualifiedBeanClassName(klass));

            handleDepends(klass, attrs);
            
            // PK = identity
            List pks = klass.getPrimaryKeys(true); 
            if ((pks != null) && (!pks.isEmpty())) {
                MdfProperty pk = (MdfProperty) pks.get(0);
                addAttribute(attrs, "identity", JavaCore.getFieldName(pk));
            } else {
                throw new RuntimeException("Cannot generate JDO mapping file for a class ["+klass+"] " +
                		"that does not contain a primary key!");
            }

            // key-generator
            addAttribute(attrs, "key-generator", "OTF");

            startElement("class", attrs);

            // write the cache type if annotation is present
            cacheType(klass);
            
            
            //class map-to name
            mapTo(tableName);

            for (Iterator it = klass.getProperties(true).iterator(); it.hasNext();) {
                MdfProperty property = (MdfProperty) it.next();

                if (property instanceof MdfAttribute) {
                    handleAttribute((MdfAttribute) property);
                } else if (property instanceof MdfAssociation) {
                	handleAssociation((MdfAssociation)property);
                }
            }

            endElement("class");
        }
    }

    private void mapTo(String name) throws SAXException {
        AttributesImpl attrs = new AttributesImpl();

        addAttribute(attrs, "table", name);

        startElement("map-to", attrs);
        endElement("map-to");
    }
    
    
    private void cacheType(MdfClass klass) throws SAXException {
    	
    	String cacheType = CastorAspect.getCacheType(klass);
    	if (cacheType != null) {
    		AttributesImpl attrs = new AttributesImpl();

            addAttribute(attrs, "type", cacheType);
            String capacity = CastorAspect.getCacheCapacity(klass);
            if (capacity != null) {
            	addAttribute(attrs, "capacity", capacity);
            }

            startElement("cache-type", attrs);
            endElement("cache-type");
    		
    		
    	}
    	
    	
    }
    

    private void handleDepends(MdfClass klass, AttributesImpl attrs) throws SAXException {
    	
    	String depends = CastorAspect.getDepends(klass);
    	if (depends != null) {
            addAttribute(attrs, "depends", depends);
    	}
    	
    	
    }
    
    
}
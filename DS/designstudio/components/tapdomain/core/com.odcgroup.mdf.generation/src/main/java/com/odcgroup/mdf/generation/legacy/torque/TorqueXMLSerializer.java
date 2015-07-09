package com.odcgroup.mdf.generation.legacy.torque;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.ext.sql.SQLAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfCoreDomain;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.otf.xml.AbstractSAXHandler;

/**
 * DOCUMENT ME!
 * 
 * @author <a href="mailto:dnemeshazyodyssey-group.com">David Nemeshazy </a>
 * @version 1.0
 */
public class TorqueXMLSerializer extends AbstractSAXHandler {
    private static final Map MMLTypes2Torque;

    private static final TorqueInfoType REF_TORQUE_TYPE = new TorqueInfoType("VARCHAR", "255");
    
    static {
        Map m = new HashMap();

        m.put(MdfCoreDomain.BOOLEAN, new TorqueInfoType("SMALLINT", null));
        m.put(MdfCoreDomain.BYTE, new TorqueInfoType("TINYINT", null));
        m.put(MdfCoreDomain.DATE, new TorqueInfoType("DATE", null));
        m.put(MdfCoreDomain.DATE_TIME, new TorqueInfoType("TIMESTAMP", null));
        m.put(MdfCoreDomain.DOUBLE, new TorqueInfoType("DECIMAL", "15,5"));
        m.put(MdfCoreDomain.FLOAT, new TorqueInfoType("DECIMAL", "15,5"));
        m.put(MdfCoreDomain.INTEGER, new TorqueInfoType("INTEGER", null));
        m.put(MdfCoreDomain.LONG, new TorqueInfoType("DECIMAL", "20"));
        m.put(MdfCoreDomain.SHORT, new TorqueInfoType("SMALLINT", null));
        m.put(MdfCoreDomain.CHAR, new TorqueInfoType("CHAR", null));
        m.put(MdfCoreDomain.STRING, new TorqueInfoType("VARCHAR", "255"));
        m.put(MdfCoreDomain.URI, new TorqueInfoType("VARCHAR", "128"));
        m.put(MdfCoreDomain.DECIMAL, new TorqueInfoType("DECIMAL", "15,5"));

        m.put(MdfCoreDomain.BOOLEAN_OBJ, new TorqueInfoType("SMALLINT", null));
        m.put(MdfCoreDomain.BYTE_OBJ, new TorqueInfoType("TINYINT", null));
        m.put(MdfCoreDomain.CHAR_OBJ, new TorqueInfoType("CHAR", null));
        m.put(MdfCoreDomain.DOUBLE_OBJ, new TorqueInfoType("DECIMAL", "15,5"));
        m.put(MdfCoreDomain.FLOAT_OBJ, new TorqueInfoType("DECIMAL", "15,5"));
        m.put(MdfCoreDomain.INTEGER_OBJ, new TorqueInfoType("INTEGER", null));
        m.put(MdfCoreDomain.LONG_OBJ, new TorqueInfoType("DECIMAL", "20"));
        m.put(MdfCoreDomain.SHORT_OBJ, new TorqueInfoType("SMALLINT", null));

        MMLTypes2Torque = Collections.unmodifiableMap(m);
    }

    private final MessageHandler messageHandler;

    /**
     * Constructor for SchemaSerializer
     * 
     * @param handler
     *            TODO: DOCUMENT ME!
     * @param messageHandler
     *            TODO: DOCUMENT ME!
     */
    public TorqueXMLSerializer(ContentHandler handler,
            MessageHandler messageHandler) {
        super(handler);
        this.messageHandler = messageHandler;
    }

    /**
     * Writes the content of the torque file for this domain
     * 
     * @param domain
     *            domain to write
     * 
     * @throws SAXException
     *             if something goes wrong
     */
    public void handle(MdfDomain domain) throws SAXException {
        messageHandler.info("Processing domain" + domain.getName());
        // a database for a domain
        startDatabase(domain);

        for (Iterator it = domain.getClasses().iterator(); it.hasNext();) {
        	MdfClass entity = (MdfClass) it.next();
            handle((MdfClass) entity);
        }

        endDatabase();
    }

    private void addDescription(AttributesImpl attrs, String documentation)
            throws SAXException {
        addAttribute(attrs, TorqueConstants.DESCRIPTION, (documentation==null)?"":documentation);
    }

    private void addType(AttributesImpl attrs, MdfAttribute attribute)
            throws SAXException {
    	// support attributes of type enumeration...
    	MdfEntity type = attribute.getType();
    	if (type instanceof MdfEnumeration) {
    		type = ((MdfEnumeration)type).getType();
    	}
        TorqueInfoType info = (TorqueInfoType) MMLTypes2Torque.get(type);

        if (info != null) {
            if (info.getSize() != null) {
                addAttribute(attrs, TorqueConstants.SIZE, info.getSize());
            }

            addAttribute(attrs, TorqueConstants.TYPE, info.getType());
        } else {
            // throw exception, only primitive types supported!!
        }
    }
    


    private void addRefType(AttributesImpl attrs, MdfAssociation association)
            throws SAXException {
    	
        TorqueInfoType info = REF_TORQUE_TYPE;

        if (info.getSize() != null) {
            addAttribute(attrs, TorqueConstants.SIZE, info.getSize());
        }

        addAttribute(attrs, TorqueConstants.TYPE, info.getType());
    }

    private void endDatabase() throws SAXException {
        endElement(TorqueConstants.DATABASE);
    }

    private void handle(MdfClass mdfClass) throws SAXException {
        messageHandler.info("Processing class" + mdfClass.getName());

        // only if table extension used
        String tableName = SQLAspect.getSqlName(mdfClass);

        if (tableName != null) {
            AttributesImpl attrs = new AttributesImpl();

            // add table name
            addAttribute(attrs, TorqueConstants.NAME, tableName);

            // add description
            addDescription(attrs, mdfClass.getDocumentation());

            startElement(TorqueConstants.TABLE, attrs);

            Map indexes = new HashMap();
            
            // each attribute with column extension will be added
            for (Iterator it = mdfClass.getProperties(true).iterator(); it
                    .hasNext();) {
                MdfProperty property = (MdfProperty) it.next();
                List idxNames = SQLAspect.getIndexNames(property);
                if ((idxNames != null) && (idxNames.size()>0)) {
                	for (Iterator idxNamesIt = idxNames.iterator();idxNamesIt.hasNext();) {
                		String idx = (String)idxNamesIt.next();
	                	List l = (List)indexes.get(idx);
	                	if (l == null) {
	                		l = new ArrayList();
	                		indexes.put(idx, l);
	                	}
	                	l.add(property);
                	}
                }
                
                if (property instanceof MdfAttribute) {
                    handle((MdfAttribute) property);
                } else if (property instanceof MdfAssociation) {
                	handle((MdfAssociation)property);
                }
            }

            handleIndexes(indexes);
            
            endElement(TorqueConstants.TABLE);
        }
    }
    private void handleIndexes(Map indexes) throws SAXException {
    	
        for (Iterator it = indexes.entrySet().iterator(); it
                .hasNext();) {
        	Entry entry = (Entry)it.next();
        	String idxName = (String)entry.getKey();
        	List properties = (List)entry.getValue();
        	
        	if ((properties != null) && (properties.size()>0)) {
        		handleIndex(idxName, properties);
        	}
        }
    }

    private void handleIndex(String idx, List properties) throws SAXException {
    	AttributesImpl attrs = new AttributesImpl();

        // add index name
        addAttribute(attrs, TorqueConstants.NAME, idx);
    	
    	startElement(TorqueConstants.INDEX, attrs);
    	for (Iterator it = properties.iterator();it.hasNext();) {
	    	MdfProperty p = (MdfProperty)it.next();
	    	String columnName = getSqlName(p);
    		
    		attrs = new AttributesImpl();
	    	addAttribute(attrs, TorqueConstants.NAME, columnName);
	    	startElement(TorqueConstants.INDEX_COLUMN, attrs);
	    	endElement(TorqueConstants.INDEX_COLUMN);
    	}
    	
        endElement(TorqueConstants.INDEX);
    }
    
    
    private void handle(MdfAttribute attribute) throws SAXException {
        messageHandler.info("Processing attribute" + attribute.getName());

        // only if column extension is there
        String columnName = getSqlName(attribute);

        AttributesImpl attrs = new AttributesImpl();

        // add column name
        addAttribute(attrs, TorqueConstants.NAME, columnName);

        //	add PK ?
        if (attribute.isPrimaryKey()) {
            // do not use addAttribute, because it formats the attribute name
            attrs.addAttribute("", TorqueConstants.PK, TorqueConstants.PK,
                    CDATA, "true");
        }

        //	add required (PK is always required)
        if ((attribute.isRequired()) || (attribute.isPrimaryKey())) {
            addAttribute(attrs, TorqueConstants.REQUIRED, "true");
        } else {
            addAttribute(attrs, TorqueConstants.REQUIRED, "false");
        }

        // add sizing and type depending on the attribute type of course
        addType(attrs, attribute);

        //	add description
        addDescription(attrs, attribute.getDocumentation());

        startElement(TorqueConstants.COLUMN, attrs);

        endElement(TorqueConstants.COLUMN);
    }

	private String getSqlName(MdfProperty p) {
		String columnName = SQLAspect.getSqlName(p);

        if (columnName == null) {
            columnName = p.getName();
        }
		return columnName;
	}
    
    private void handle(MdfAssociation association) throws SAXException {
        messageHandler.info("Processing association" + association.getName());
    	
        if ((association.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) && (association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE)) {
	    	// only if column extension is there

            String columnName = getSqlName(association);
	
	        AttributesImpl attrs = new AttributesImpl();
	
	        // add column name
	        addAttribute(attrs, TorqueConstants.NAME, columnName);
	
	        //	add required
	        if (association.isRequired()) {
	            addAttribute(attrs, TorqueConstants.REQUIRED, "true");
	        } else {
	            addAttribute(attrs, TorqueConstants.REQUIRED, "false");
	        }
	
	        // add sizing and type depending on the attribute type of course
	        addRefType(attrs, association);
	
	        //	add description
	        addDescription(attrs, association.getDocumentation());
	
	        startElement(TorqueConstants.COLUMN, attrs);
	
	        endElement(TorqueConstants.COLUMN);
        }
    }

    private void startDatabase(MdfDomain domain) throws SAXException {
        AttributesImpl attrs = new AttributesImpl();

        // add DB name
        addAttribute(attrs, TorqueConstants.NAME, TorqueWriter.NAME_FORMAT
                .format(domain.getName()));

        //add description
        //addDescription(attrs, domain.getComments());

        startElement(TorqueConstants.DATABASE, attrs);
    }

}
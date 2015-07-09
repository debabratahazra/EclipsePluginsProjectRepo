package com.odcgroup.mdf.generation.legacy.castor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.core.DomainObject;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.transform.castor.CastorCore;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.otf.xml.AbstractSAXHandler;


/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini</a>
 * @version 1.0
 */
public class CastorSerializer extends AbstractSAXHandler {
    /** TODO: DOCUMENT ME! */
    private final MessageHandler messageHandler;
    private String nsprefix = null;
    private String nsuri = null;

    /**
     * Constructor for CastorSerializer
     *
     * @param handler TODO: DOCUMENT ME!
     * @param messageHandler TODO: DOCUMENT ME!
     */
    public CastorSerializer(ContentHandler handler,
        MessageHandler messageHandler) {
        super(handler);
        setElementNamesFormat(null);
        setAttributeNamesFormat(null);
        this.messageHandler = messageHandler;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @param domain TODO: DOCUMENT ME!
     *
     * @throws SAXException TODO: DOCUMENT ME!
     */
    public void handle(MdfDomain domain) throws SAXException {
        nsprefix = CastorCore.NAME_FORMAT.format(domain.getName());
        nsuri = domain.getNamespace();

        // parse classes
        List classes = sortClasses(domain.getClasses());

        for (int i = 0; i < classes.size(); ++i) {
            handle((MdfClass) classes.get(i));
        }

        info("Creating mapping for domain bean");

        //domain class name
        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", JavaCore.getQualifiedBeanClassName(domain));
        startElement("class", attrs);

        //XML mapping name
        mapTo(nsprefix, null, false);

        // mode attribute
        attrs = new AttributesImpl();
        addAttribute(attrs, "name", "mode");
        addAttribute(attrs, "type", "string");
        startElement("field", attrs);

        attrs = new AttributesImpl();
        addAttribute(attrs, "name", "mode");
        addAttribute(attrs, "node", "attribute");
        startElement("bind-xml", attrs);
        endElement("bind-xml");

        endElement("field");

        // content field
        attrs = new AttributesImpl();
        addAttribute(attrs, "name", "content");
        addAttribute(attrs, "type", DomainObject.class.getName());
        addAttribute(attrs, "collection", CastorGenerationCore.COLLECTION);
        startElement("field", attrs);

        attrs = new AttributesImpl();
        addAttribute(attrs, "auto-naming", "deriveByClass");
        addAttribute(attrs, "node", "element");
        startElement("bind-xml", attrs);
        endElement("bind-xml");

        endElement("field");
        endElement("class");
    }

    /**
     * Sorts the classes. Castor expects superclasses to be defined before
     * subclasses in its mapping file. This method ensures that this is the
     * case.
     *
     * @param original List of classes to sort.
     *
     * @return ArrayList List of sorted classes.
     */
    protected List sortClasses(Collection original) {
        List tosort = new ArrayList(original);
        List sorted = new ArrayList();
        boolean written = false;
        MdfClass klass = null;
        MdfName baseClassName = null;

        // Loop until original list is empty.
        while (!tosort.isEmpty()) {
            written = false;

            int i = 0;

            //loop until class found to write to new list
            while ((!written) && (i < tosort.size())) {
                if (tosort.get(i) instanceof MdfClass) {
                    klass = (MdfClass) tosort.get(i);

                    if (klass.getBaseClass() == null) {
                        // if class has no other MdfClass dependancy, then write to new list
                        sorted.add(klass);
                        tosort.remove(i);
                        written = true;
                    } else {
                        //else loop through checking for dependency
                        boolean write = true;
                        int j = 0;

                        while ((j < tosort.size()) && (write == true)) {
                            if (tosort.get(j) instanceof MdfClass) {
                                MdfClass parent = ((MdfClass) tosort.get(j));

                                if (parent != null) {
                                    baseClassName = parent.getQualifiedName();

                                    //does the parent class still exist in the vector? If so dont write.
                                    if (klass.getBaseClass().getQualifiedName()
                                                 .equals(baseClassName)) {
                                        write = false;
                                    }
                                } else {
                                    write = false;
                                }

                                j++;
                            } else {
                                tosort.remove(j);
                            }
                        }

                        if (write) {
                            sorted.add(klass);
                            tosort.remove(i);
                            written = true;
                        } else {
                            i++;
                        }
                    }
                } else {
                    tosort.remove(i);
                }
            }
        }

        return sorted;
    }

    private void handle(MdfClass klass) throws SAXException {
        handleBean(klass);
        handleRef(klass);
    }

    private void handle(MdfProperty property) throws SAXException {
        if (property instanceof MdfReverseAssociation) {
            return;
        } else if (property instanceof MdfAssociation) {
            handle((MdfAssociation) property);
        } else if (property instanceof MdfAttribute) {
            handle((MdfAttribute) property);
        } else {
            throw new RuntimeException("Unknown property type: " +
                property.getClass());
        }
    }

    private void handle(MdfAttribute attribute) throws SAXException {
        String name = attribute.getName();
        info("Creating mapping for attribute " + name);

        String typeName = CastorGenerationCore.getCastorType(attribute);

        // field handler if needed, depending on the type of the attribute
        // if not needed, this is null
        String fieldHandler = CastorGenerationCore.getFieldHandler(attribute);

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", name);
        addAttribute(attrs, "type", typeName);

        if (fieldHandler != null) {
            addAttribute(attrs, "handler", fieldHandler);
        }

        startElement("field", attrs);

        attrs = new AttributesImpl();
        addAttribute(attrs, "name", CastorCore.NAME_FORMAT.format(name));
        addAttribute(attrs, "node",
            attribute.isPrimaryKey() ? "attribute" : "element");

        startElement("bind-xml", attrs);
        endElement("bind-xml");

        endElement("field");
    }

    private void handle(MdfAssociation association) throws SAXException {
        String name = association.getName();
        info("Creating mapping for association " + name);

        String typeName = CastorGenerationCore.getCastorType(association);

        // field handler if needed, depending on the type of the association
        // if not needed, this is null
        String fieldHandler = CastorGenerationCore.getFieldHandler(association);

        AttributesImpl attrs = new AttributesImpl();

        if (association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) {
            name += "Ref";
        }

        addAttribute(attrs, "name", name);
        addAttribute(attrs, "type", typeName);

        if (fieldHandler != null) {
            addAttribute(attrs, "handler", fieldHandler);
        }

        if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
            addAttribute(attrs, "collection", CastorGenerationCore.COLLECTION);
        }

        startElement("field", attrs);

        attrs = new AttributesImpl();

        addAttribute(attrs, "name",
            CastorCore.NAME_FORMAT.format(association.getName()));
        addAttribute(attrs, "node", "element");

        startElement("bind-xml", attrs);
        endElement("bind-xml");

        endElement("field");
    }

    private void handleBean(MdfClass klass) throws SAXException {
        info("Creating bean mapping for class " +
            klass.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", JavaCore.getQualifiedBeanClassName(klass));

        if (klass.getBaseClass() != null) {
            addAttribute(attrs, "extends",
                JavaCore.getQualifiedBeanClassName(klass.getBaseClass()));
        }

        if (klass.isAbstract()) {
            addAttribute(attrs, "verify-constructable", "false");
        }

        startElement("class", attrs);

        //class map-to name
        mapTo(CastorCore.NAME_FORMAT.format(klass.getName()), klass.getName(),
            klass.isAbstract());

        for (Iterator it = klass.getProperties().iterator(); it.hasNext();) {
            handle((MdfProperty) it.next());
        }

        endElement("class");
    }

    private void handleRef(MdfClass klass) throws SAXException {
        if (klass.hasPrimaryKey(true)) {
            info("Creating ref mapping for class " +
                klass.getName());

            AttributesImpl attrs = new AttributesImpl();
            addAttribute(attrs, "name", JavaCore.getQualifiedRefClassName(klass));
            addAttribute(attrs, "verify-constructable", "false");

            MdfClass base = klass.getBaseClass();

            if ((base != null) && base.hasPrimaryKey(true)) {
                addAttribute(attrs, "extends",
                    JavaCore.getQualifiedRefClassName(base));
            }

            // class tag
            startElement("class", attrs);

            //class map-to name
            String className = klass.getName() + "Ref";
            mapTo(CastorCore.NAME_FORMAT.format(className), className,
                klass.isAbstract());

            // now we create the primary key field
            List allpks = klass.getPrimaryKeys(true);
            List pks = klass.getPrimaryKeys();
            int offset = pks.size() - allpks.size() + 1;

            for (int i = 0; i < pks.size(); i++) {
                MdfProperty mdfProperty = (MdfProperty) pks.get(i);

                attrs = new AttributesImpl();
                addAttribute(attrs, "name", "primaryKeyValue");
                addAttribute(attrs, "type",
                    CastorGenerationCore.getCastorType(mdfProperty));

                addAttribute(attrs, "get-method", "getPrimaryKeyValue");
                addAttribute(attrs, "set-method", "%" + (i + offset));

                String fieldHandler = CastorGenerationCore.getFieldHandler(mdfProperty);

                if (fieldHandler != null) {
                    addAttribute(attrs, "handler", fieldHandler);
                }

                startElement("field", attrs);

                StringBuffer temp = new StringBuffer();
                temp.append("ref-");
                temp.append(mdfProperty.getName());

                attrs = new AttributesImpl();
                addAttribute(attrs, "name",
                    CastorCore.NAME_FORMAT.format(temp.toString()));
                addAttribute(attrs, "node", "attribute");

                startElement("bind-xml", attrs);
                endElement("bind-xml");

                endElement("field");
            }

            endElement("class");
        }
    }

    /**
     * Writes the map-to element of the class. If the SCHEMA_USE property is
     * set, put the ns-prefix and ns-uri and eventually the xstype attributes.
     *
     * @param name - the name of the xml element
     * @param xsType - the xs type to use for the class
     * @param isAbstract TODO: DOCUMENT ME!
     *
     * @throws SAXException TODO: DOCUMENT ME!
     */
    private void mapTo(String name, String xsType, boolean isAbstract)
        throws SAXException {
        if (isAbstract && !CastorGenerationCore.SCHEMA_USE) {
            return;
        }

        AttributesImpl attrs = new AttributesImpl();

        if (!isAbstract) {
            addAttribute(attrs, "xml", name);
        }

        if (CastorGenerationCore.SCHEMA_USE) {
            addAttribute(attrs, "ns-prefix", nsprefix);
            addAttribute(attrs, "ns-uri", nsuri);

            if (xsType != null) {
                addAttribute(attrs, "xstype", xsType);
            }
        }

        startElement("map-to", attrs);
        endElement("map-to");
    }

    protected void debug(Object message) {
        if (messageHandler != null) messageHandler.debug(message);
    }

    protected void error(Object message, Exception e) {
        if (messageHandler != null) messageHandler.error(message, e);
    }

    protected void info(Object message) {
        if (messageHandler != null) messageHandler.info(message);
    }

    protected void warn(Object message) {
        if (messageHandler != null) messageHandler.warn(message);
    }
    
    
}

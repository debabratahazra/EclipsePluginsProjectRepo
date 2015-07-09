package com.odcgroup.mdf.generation.legacy.xsd;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.core.DomainObjectExtent;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfCoreDomain;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.otf.xml.AbstractSAXHandler;


/**
 * TODO: DOCUMENT ME!
 *
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class SchemaSerializer extends AbstractSAXHandler {
    private final MessageHandler messageHandler;

    /**
     * Constructor for SchemaSerializer
     *
     * @param handler TODO: DOCUMENT ME!
     * @param messageHandler TODO: DOCUMENT ME!
     */
    public SchemaSerializer(ContentHandler handler,
        MessageHandler messageHandler) {
        super(handler, SchemaCore.XSD_NAMESPACE, SchemaCore.XSD_PREFIX);
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
    public void handle(MdfDomain domain, Collection<MdfDomain> importedDomains) throws SAXException {
        Iterator it = importedDomains.iterator();

        while (it.hasNext()) {
            handleImport((MdfDomain) it.next());
        }

        writeTypeRefDeclaration(domain);
        writeTypeDefinition(domain);
        writeRootElement(domain);
    }

    private boolean isPrimitiveObject(MdfEntity entity) {
        return ((entity.equals(MdfCoreDomain.BOOLEAN_OBJ)) ||
        (entity.equals(MdfCoreDomain.BYTE_OBJ)) ||
        (entity.equals(MdfCoreDomain.DOUBLE_OBJ)) ||
        (entity.equals(MdfCoreDomain.CHAR_OBJ)) ||
        (entity.equals(MdfCoreDomain.FLOAT_OBJ)) ||
        (entity.equals(MdfCoreDomain.INTEGER_OBJ)) ||
        (entity.equals(MdfCoreDomain.LONG_OBJ)) ||
        (entity.equals(MdfCoreDomain.SHORT_OBJ)));
    }

    private String getRelativeName(MdfDomain domain, MdfEntity entity) {
        if (domain.getName().equals(entity.getParentDomain().getName())) {
            return entity.getName();
        } else {
            return entity.getQualifiedName().toString();
        }
    }

    private void handleImport(MdfDomain imp) throws SAXException {
        messageHandler.info("Procesing domain imports");

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "namespace", imp.getNamespace());

        StringBuffer buffer = new StringBuffer();
        buffer.append(SchemaCore.NAME_FORMAT.format(imp.getName()));
        buffer.append(".xsd");
        addAttribute(attrs, "schemaLocation", buffer.toString());

        startElement("import", attrs);
        endElement("import");
    }

    private boolean hasComments(MdfModelElement element) {
    	return StringUtils.isNotEmpty(element.getDocumentation());
    }

    private void writeAnnotation(MdfModelElement element)
        throws SAXException {
    	if (!hasComments(element))
    		return;

        startElement("annotation", EMPTY_ATTRIBUTES);
        
        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "xml:lang", "en"); // for backward compatibility
        startElement("documentation", attrs);
        characters(element.getDocumentation());
        endElement("documentation");
        
        endElement("annotation");
    }

    private void writeAnnotation(String documentation)
        throws SAXException {
        startElement("annotation", EMPTY_ATTRIBUTES);
        startElement("documentation", EMPTY_ATTRIBUTES);
        characters(documentation);
        endElement("documentation");
        endElement("annotation");
    }

    private void writeClassTypeDefinition(MdfClass klass)
        throws SAXException {
        messageHandler.info("Processing complexType definition for class " +
            klass.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", klass.getName());

        if (klass.isAbstract()) {
            addAttribute(attrs, "abstract", "true");
        }

        startElement("complexType", attrs);

        writeAnnotation(klass);

        MdfClass baseClass = klass.getBaseClass();

        if (baseClass != null) {
            startElement("complexContent", EMPTY_ATTRIBUTES);

            attrs = new AttributesImpl();
            addAttribute(attrs, "base",
                getRelativeName(klass.getParentDomain(), baseClass));
            startElement("extension", attrs);
            writePropertyTypeDefinition(klass);
            endElement("extension");

            endElement("complexContent");
        } else {
            writePropertyTypeDefinition(klass);
        }

        endElement("complexType");
    }

    private void writeElementDeclaration(MdfDomain domain)
        throws SAXException {
        messageHandler.info("Processing elements declaration for classes");

        Iterator it = domain.getClasses().iterator();

        while (it.hasNext()) {
        	MdfClass klass = (MdfClass) it.next();

        	// we write here only the classes that have a primary key
            // directly
            // they can be referenced in the domainobject
            if (klass.hasPrimaryKey(true)) {
                AttributesImpl attrs = new AttributesImpl();
                addAttribute(attrs, "name",
                    SchemaCore.NAME_FORMAT.format(klass.getName()));
                addAttribute(attrs, "type", klass.getName());
                addAttribute(attrs, "minOccurs", "0");
                addAttribute(attrs, "maxOccurs", "unbounded");
                startElement("element", attrs);
                endElement("element");
            }
        }
    }

    private void writeElementRefDeclaration(MdfDomain domain)
        throws SAXException {
        messageHandler.info("Processing elements declaration for references");

        Iterator it = domain.getClasses().iterator();

        while (it.hasNext()) {
        	MdfClass klass = (MdfClass) it.next();

            if (klass.hasPrimaryKey(true)) {
                String name = klass.getName() + "Ref";

                AttributesImpl attrs = new AttributesImpl();
                addAttribute(attrs, "name",
                    SchemaCore.NAME_FORMAT.format(name));
                addAttribute(attrs, "type", name);
                addAttribute(attrs, "minOccurs", "0");
                addAttribute(attrs, "maxOccurs", "unbounded");
                startElement("element", attrs);
                endElement("element");
            }
        }
    }

    private void writeEnumTypeDefinition(MdfEnumeration e)
        throws SAXException {
        messageHandler.info("Processing simpleType definition for enumeration " +
            e.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", e.getName());

        startElement("simpleType", attrs);
        writeAnnotation(e);

        attrs = new AttributesImpl();
        addAttribute(attrs, "base", SchemaCore.getMdfValueTypeRef(e));
        startElement("restriction", attrs);
        writeNameValuePairs(e);
        endElement("restriction");

        endElement("simpleType");
    }

    private void writeNameValuePairs(MdfEnumeration e)
        throws SAXException {
        String xsdType = SchemaCore.toXSDType(e.getType());

        if (xsdType.equals("xs:boolean")) {
            // Special treatment for boolean enums that does not support the
            // 'enumeration' facet
            Iterator it = e.getValues().iterator();

            StringBuffer pattern = new StringBuffer();

            while (it.hasNext()) {
                MdfEnumValue nv = (MdfEnumValue) it.next();
                pattern.append(nv.getValue());

                if (it.hasNext()) {
                    pattern.append('|');
                }
            }

            AttributesImpl attrs = new AttributesImpl();
            addAttribute(attrs, "value", pattern.toString());
            startElement("pattern", attrs);
            endElement("pattern");
        } else {
            Iterator it = e.getValues().iterator();

            while (it.hasNext()) {
                MdfEnumValue nv = (MdfEnumValue) it.next();
                AttributesImpl attrs = new AttributesImpl();
                addAttribute(attrs, "value", nv.getValue());

                startElement("enumeration", attrs);

                if (hasComments(nv)) {
                    writeAnnotation(nv);
                }

                endElement("enumeration");
            }
        }
    }

    private void writePrimaryKeyAttribute(MdfClass klass, boolean isRef)
        throws SAXException {
        Iterator it = klass.getPrimaryKeys().iterator();

        while (it.hasNext()) {
            MdfProperty primaryKey = (MdfProperty) it.next();

            StringBuffer xsname = new StringBuffer();

            if (isRef) {
                xsname.append("ref-");
            }

            xsname.append(primaryKey.getName());

            AttributesImpl attrs = new AttributesImpl();
            addAttribute(attrs, "name",
                SchemaCore.NAME_FORMAT.format(xsname.toString()));

            if (primaryKey instanceof MdfAttribute) {
                MdfAttribute attribute = (MdfAttribute) primaryKey;
                addAttribute(attrs, "type",
                    SchemaCore.getPropertyType(attribute));
                addAttribute(attrs, "use", "required");
            }
            // should not appear, remove?
            else if (primaryKey instanceof MdfAssociation) {
                MdfAssociation association = (MdfAssociation) primaryKey;
                addAttribute(attrs, "type",
                    SchemaCore.getPropertyType(association));

                if (!association.isPrimaryKey()) {
                    if (!association.isRequired()) {
                        addAttribute(attrs, "minOccurs", "0");
                    }

                    if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
                        addAttribute(attrs, "maxOccurs", "unbounded");
                    }
                }
            }

            startElement("attribute", attrs);

            if (hasComments(primaryKey)) {
                writeAnnotation(primaryKey);
            }

            endElement("attribute");
        }
    }

    private void writeProperty(MdfProperty property) throws SAXException {
        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name",
            SchemaCore.NAME_FORMAT.format(property.getName()));
        addAttribute(attrs, "type", SchemaCore.getPropertyType(property));

        if (!property.isPrimaryKey()) {
            if (!property.isRequired()) {
                addAttribute(attrs, "minOccurs", "0");
            }
        }

        if (property instanceof MdfAttribute) {
            MdfAttribute attribute = (MdfAttribute) property;
            String defaultValue = attribute.getDefault();
            MdfEntity type = attribute.getType();

            if(type instanceof MdfBusinessType) {
            	type = ((MdfBusinessType) type).getType();
            }
            
            if (defaultValue != null) {
                if (attribute.getType() instanceof MdfEnumeration) {
                    MdfEnumeration e = (MdfEnumeration) attribute.getType();
                    type = e.getType();
                    defaultValue = e.getValue(defaultValue).getValue();
                }

                boolean specifyDefault = true;

                if (isPrimitiveObject(type)) {
                    // we don't need to add default for null or NaN object primitives. The Java Object takes
                    // care of that and to represent null or NaN is to not put the element in the XML file!
                    if ("null".equalsIgnoreCase(defaultValue)) {
                        specifyDefault = false;
                    } else if ("NaN".equalsIgnoreCase(defaultValue)) {
                        specifyDefault = false;
                    }
                } else if (type.equals(MdfCoreDomain.DATE)) {
                    // When date we must filter-out "today()" which is not supported by XML Schema
                    if ("today()".equals(defaultValue)) {
                        specifyDefault = false;
                    }
                } else if (type.equals(MdfCoreDomain.DATE_TIME)) {
                    // When dateTime we must filter-out "now()" which is not supported by XML Schema
                    if ("now()".equals(defaultValue)) {
                        specifyDefault = false;
                    }
                }

                if (specifyDefault) {
                    addAttribute(attrs, "default", defaultValue);
                }
            }
        } else if (property instanceof MdfAssociation) {
            MdfAssociation association = (MdfAssociation) property;

            if (!association.isPrimaryKey()) {
                if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
                    addAttribute(attrs, "maxOccurs", "unbounded");
                }
            }
        }

        startElement("element", attrs);

        if (hasComments(property)) {
            writeAnnotation(property);
        }

        endElement("element");
    }

    private void writePropertyTypeDefinition(MdfClass klass)
        throws SAXException {
        if (!klass.getProperties().isEmpty()) {
            boolean seq = false;
            Iterator it = klass.getProperties().iterator();

            while (it.hasNext()) {
                MdfProperty property = (MdfProperty) it.next();

                if (property instanceof MdfReverseAssociation) {
                    continue;
                }

                if (!property.isPrimaryKey()) {
                    if (!seq) {
                        startElement("sequence", EMPTY_ATTRIBUTES);
                        seq = true;
                    }

                    writeProperty(property);
                }
            }

            if (seq) {
                endElement("sequence");
            }
        }

        writePrimaryKeyAttribute(klass, false);
    }

    private void writeRootElement(MdfDomain domain) throws SAXException {
        messageHandler.info("Processing root element declaration");

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name",
            SchemaCore.NAME_FORMAT.format(domain.getName()));

        startElement("element", attrs);
        writeAnnotation(domain);

        startElement("complexType", EMPTY_ATTRIBUTES);
        startElement("choice", EMPTY_ATTRIBUTES);

        attrs = new AttributesImpl();
        addAttribute(attrs, "minOccurs", "0");
        addAttribute(attrs, "maxOccurs", "unbounded");
        startElement("sequence", attrs);
        writeElementDeclaration(domain);
        endElement("sequence");

        startElement("sequence", attrs);
        writeElementRefDeclaration(domain);
        endElement("sequence");
        endElement("choice");

        // add the mode attribute : string with enumeration : publish and
        // remove
        attrs = new AttributesImpl();
        addAttribute(attrs, "name", "mode");
        addAttribute(attrs, "use", "optional");
        addAttribute(attrs, "default", DomainObjectExtent.PUBLISH);

        startElement("attribute", attrs);
        startElement("simpleType", EMPTY_ATTRIBUTES);

        attrs = new AttributesImpl();
        addAttribute(attrs, "base", SchemaCore.XSD_PREFIX + ":string");
        startElement("restriction", attrs);

        attrs = new AttributesImpl();
        addAttribute(attrs, "value", DomainObjectExtent.PUBLISH);
        startElement("enumeration", attrs);
        endElement("enumeration");

        attrs = new AttributesImpl();
        addAttribute(attrs, "value", DomainObjectExtent.REMOVE);
        startElement("enumeration", attrs);
        endElement("enumeration");

        endElement("restriction");
        endElement("simpleType");
        endElement("attribute");

        endElement("complexType");
        endElement("element");
    }

    private void writeTypeDefinition(MdfDomain domain)
        throws SAXException {
        Iterator it = domain.getEntities().iterator();

        while (it.hasNext()) {
            MdfEntity entity = (MdfEntity) it.next();

            if (entity instanceof MdfClass) {
                writeClassTypeDefinition((MdfClass) entity);
            }  else if (entity instanceof MdfEnumeration) {
                writeEnumTypeDefinition((MdfEnumeration) entity);             	
            }
        }
        
    }

    private void writeTypeRefDeclaration(MdfDomain domain)
        throws SAXException {
        Iterator it = domain.getClasses().iterator();

        while (it.hasNext()) {
        	MdfClass klass = (MdfClass) it.next();

            if (klass.hasPrimaryKey(true)) {
                messageHandler.info(
                    "Processing complexType definition for reference to class " +
                    klass.getName());

                AttributesImpl attrs = new AttributesImpl();

                addAttribute(attrs, "name", klass.getName() + "Ref");
                startElement("complexType", attrs);
                writeAnnotation("A reference to " + klass.getName());

                MdfClass baseClass = klass.getBaseClass();

                if ((baseClass != null) && baseClass.hasPrimaryKey(true)) {
                    startElement("complexContent", EMPTY_ATTRIBUTES);

                    attrs = new AttributesImpl();
                    addAttribute(attrs, "base",
                        getRelativeName(klass.getParentDomain(), baseClass) +
                        "Ref");
                    startElement("extension", attrs);
                    writePrimaryKeyAttribute(klass, true);
                    endElement("extension");

                    endElement("complexContent");
                } else {
                    writePrimaryKeyAttribute(klass, true);
                }

                endElement("complexType");
            }
        }
    }
}

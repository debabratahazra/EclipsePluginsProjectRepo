package com.odcgroup.mdf.generation.legacy.mml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

import com.odcgroup.mdf.MessageHandler;
import com.odcgroup.mdf.ext.MDFAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfCoreDomain;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.mml.model.MmlConstants;
import com.odcgroup.otf.xml.AbstractSAXHandler;


/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class MmlSerializer extends AbstractSAXHandler implements LexicalHandler {

    private final MessageHandler messageHandler;
    private LexicalHandler lexicalHandler = null;
    private final MmlModelWriter writer;
    private final static MdfModelElementComparator MDF_MODEL_ELEMENT_COMPARATOR = new MdfModelElementComparator();
    private final static MdfAnnotationPropertyComparator MDF_ANNOTATION_PROPERTY_COMPARATOR = new MdfAnnotationPropertyComparator();
    private final static MdfAnnotationComparator MDF_ANNOTATION_COMPARATOR = new MdfAnnotationComparator();

    /**
     * Constructor for MmlSerializer
     * 
     * @param handler
     */
    public MmlSerializer(MmlModelWriter writer, ContentHandler handler, MessageHandler messageHandler) {
        super(handler, MdfCoreDomain.NAMESPACE_URI,
                MdfCoreDomain.DEFAULT_NAMESPACE_PREFIX);
        this.writer = writer;
        setElementNamesFormat(null);
        setAttributeNamesFormat(null);
        this.messageHandler = messageHandler;
        if (handler instanceof LexicalHandler) {
            lexicalHandler = (LexicalHandler) handler;
        }
    }

    /**
     * Generates SAX events for a given domain.
     * 
     * @param domain
     * @throws SAXException
     */
    public void handle(MdfDomain domain, Collection<MdfDomain> importedDomains) throws SAXException {
        messageHandler.info("Writing MML model file for domain "
                + domain.getName());

        String version = MDFAspect.getVersion(domain);
        if (!MDFAspect.CURRENT_VERSION.equals(version)) {
            messageHandler.info("Original version of the meta-model for this domain was different ("
                    + version
                    + ") than the current one ("
                    + MDFAspect.CURRENT_VERSION
                    + "). Writing this model may make it backward incompatible "
                    + "with previous versions of the MDF!");
        }

        messageHandler.info("Writing MML header");
        startPrefixMapping(getNamespacePrefix(), getNamespaceURI());
        startPrefixMapping(XML_SCHEMA_INSTANCE_DEFAULT_NAMESPACE_PREFIX,
                XML_SCHEMA_INSTANCE_NAMESPACE_URI);

        Iterator it;

//        // -- Add writers for unknown extensions which have a schemaLocation
//        it = domain.getExtensions().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            String namespace = (String) entry.getKey();
//            if (extensionWriters.get(namespace) == null) {
//                UnknownExtension ext = (UnknownExtension) entry.getValue();
//                String location = namespace + " " + ext.getSchemaURI();
//                String prefix = ext.getPrefix();
//                extensionWriters.put(namespace, new UnknownExtensionWriter(
//                        namespace, location, prefix));
//            }
//        }
//
        StringBuffer schemaLocation = new StringBuffer();
        schemaLocation.append(getNamespaceURI()).append(" mml.xsd");
//
//        it = extensionWriters.values().iterator();
//        while (it.hasNext()) {
//            MmlExtensionWriter writer = (MmlExtensionWriter) it.next();
//            String prefix = writer.getDefaultPrefix();
//            if (prefix != null) {
//                startPrefixMapping(prefix, writer.getNamespaceURI());
//            }
//
//            String schemaLoc = writer.getSchemaLocation();
//            if (schemaLoc != null) {
//                schemaLocation.append(' ').append(schemaLoc);
//            }
//        }

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", domain.getName());
        addAttribute(attrs, "namespace", domain.getNamespace());
        addAttribute(attrs, "metamodelVersion", domain.getMetamodelVersion());
        attrs.addAttribute(XML_SCHEMA_INSTANCE_NAMESPACE_URI, "schemaLocation",
                XML_SCHEMA_INSTANCE_DEFAULT_NAMESPACE_PREFIX
                        + ":schemaLocation", CDATA, schemaLocation.toString());

        startElement("domain", attrs);
        handleDocumentation(domain);
        handleAnnotations(domain);
//        handleDeprecationInfo(domain);

        it = importedDomains.iterator();
        while (it.hasNext()) {
            MdfDomain imported = (MdfDomain) it.next();
            attrs = new AttributesImpl();
            addAttribute(attrs, "location", writer.getModelPath(imported));
            addAttribute(attrs, "namespace", imported.getNamespace());
            startElement("import", attrs);
            endElement("import");
        }

        // handle classes
        it = sortModelElementList(domain.getClasses()).iterator();
        while (it.hasNext()) {
            handle((MdfClass) it.next());
        }
        
        // handle datasets
        it = sortModelElementList(domain.getDatasets()).iterator();
        while (it.hasNext()) {
            handle((MdfDataset) it.next());
        }
        
        // handle enumerations
        it = sortModelElementList(domain.getEnumerations()).iterator();
        while (it.hasNext()) {
        	handle((MdfEnumeration)it.next());
        }
        	
        // handle businessTypes
        it = sortModelElementList(domain.getBusinessTypes()).iterator();
        while (it.hasNext()) {
        	handle((MdfBusinessType) it.next());
        }  

        endElement("domain");

        endPrefixMapping(XML_SCHEMA_INSTANCE_DEFAULT_NAMESPACE_PREFIX);
        endPrefixMapping(getNamespacePrefix());

        messageHandler.info("Done writing MML model file");
    }

    private String getRelativeName(MdfDomain domain, MdfEntity entity) {
    	if (entity.getQualifiedName() == null) {
    		return entity.getName();
    	}
        if (domain.getName().equals(entity.getQualifiedName().getDomain())) {
            return entity.getName();
        } else {
            return entity.getQualifiedName().toString();
        }
    }    

    private void handle(MdfEnumeration e) throws SAXException {
        messageHandler.info("Writing enumeration " + e.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", e.getName());
        addAttribute(attrs, "acceptNullValue", Boolean.valueOf(e.isAcceptNullValue()));        

        MdfEntity type = e.getType();
        if (type != null) {
            addAttribute(attrs, "type", getRelativeName(e.getParentDomain(),
                    type));
        }

        startElement("enumeration", attrs);
        handleDocumentation(e);
        handleAnnotations(e);
//        handleDeprecationInfo(e);

        Iterator it = e.getValues().iterator();

        while (it.hasNext()) {
            handle((MdfEnumValue) it.next());
        }

        endElement("enumeration");
    }

    private void handle(MdfBusinessType bType) throws SAXException {
        messageHandler.info("Writing business type " + bType.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", bType.getName());
        MdfEntity type = bType.getType();
        if (type != null) {
	        addAttribute(attrs, "type", getRelativeName(bType.getParentDomain(),
	        		type));
        }

        startElement("business-type", attrs);
        handleDocumentation(bType);
        handleAnnotations(bType);
//        handleDeprecationInfo(bType);

        endElement("business-type");
    }

    private void handle(MdfEnumValue value) throws SAXException {
        messageHandler.info("Writing enumerated value " + value.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", value.getName());
        addAttribute(attrs, "value", value.getValue());

        startElement("name-value", attrs);
        handleDocumentation(value);
        handleAnnotations(value);
//        handleDeprecationInfo(value);
        endElement("name-value");
    }

    private void handle(MdfClass klass) throws SAXException {
        messageHandler.info("Writing class " + klass.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", klass.getName());

        MdfClass baseClass = klass.getBaseClass();
        if (baseClass != null) {
            addAttribute(attrs, "extends", getRelativeName(
                    klass.getParentDomain(), baseClass));
        }

        addAttribute(attrs, "abstract", Boolean.valueOf(klass.isAbstract()));

        startElement("class", attrs);
        handleDocumentation(klass);
        handleAnnotations(klass);
//        handleDeprecationInfo(klass);

        Iterator it = sortModelElementList(klass.getProperties()).iterator();

        while (it.hasNext()) {
            handle((MdfProperty) it.next());
        }

        endElement("class");
    }

    private void handle(MdfDataset dataset) throws SAXException {
        messageHandler.info("Writing dataset " + dataset.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", dataset.getName());

        MdfClass baseClass = dataset.getBaseClass();
        if (baseClass != null) {
            addAttribute(attrs, "base-class", getRelativeName(
                    dataset.getParentDomain(), baseClass));
        }
        addAttribute(attrs, "linked", Boolean.valueOf(dataset.isLinked()));
        addAttribute(attrs, "sync", Boolean.valueOf(dataset.isSync()));

        startElement("dataset", attrs);
        handleDocumentation(dataset);
        handleAnnotations(dataset);
//        handleDeprecationInfo(dataset);

        Iterator it = sortModelElementList(dataset.getProperties()).iterator();

        while (it.hasNext()) {
            handle((MdfDatasetProperty) it.next());
        }

        endElement("dataset");
    }

    private void handle(MdfDatasetProperty property) throws SAXException {
        if (property instanceof MdfDatasetDerivedProperty) {
            handle((MdfDatasetDerivedProperty) property);
        } else if (property instanceof MdfDatasetMappedProperty) {
            handle((MdfDatasetMappedProperty) property);
        }
    }
    
    private void handle(MdfProperty property) throws SAXException {
        if (property instanceof MdfAttribute) {
            handle((MdfAttribute) property);
        } else if (property instanceof MdfAssociation) {
            handle((MdfAssociation) property);
        }
        // MdfReverseAssociations as properties in a MdfClass are ignored.
        // they are taken into account at the association level
    }

    private void handle(MdfAttribute attribute) throws SAXException {
        messageHandler.info("Writing attribute " + attribute.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", attribute.getName());

        MdfEntity type = attribute.getType();
        if (type != null) {
            addAttribute(attrs, "type", getRelativeName(
                    attribute.getParentClass().getParentDomain(), type));
        }

        addAttribute(attrs, "business-key", Boolean.valueOf(
                attribute.isBusinessKey()));
        addAttribute(attrs, "primary-key",
        		Boolean.valueOf(attribute.isPrimaryKey()));
        addAttribute(attrs, "required", Boolean.valueOf(attribute.isRequired()));

        if (attribute.getDefault() != null) {
            addAttribute(attrs, "default", attribute.getDefault());
        }

        int multiplicity = attribute.getMultiplicity();
        if (MdfConstants.MULTIPLICITY_MANY == multiplicity) {
	        addAttribute(attrs, "multiplicity", MmlConstants.getMultiplicity(multiplicity));
        }

        startElement("attribute", attrs);
        handleDocumentation(attribute);
        handleAnnotations(attribute);
//        handleDeprecationInfo(attribute);
        endElement("attribute");
    }

    private void handle(MdfAssociation association) throws SAXException {
        messageHandler.info("Writing association " + association.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", association.getName());

        MdfEntity type = association.getType();
        if (type != null) {
            addAttribute(attrs, "type", getRelativeName(
                    association.getParentClass().getParentDomain(), type));
        }

        addAttribute(attrs, "business-key", Boolean.valueOf(
                association.isBusinessKey()));
        addAttribute(attrs, "primary-key", Boolean.valueOf(
                        association.isPrimaryKey()));
        addAttribute(attrs, "required", Boolean.valueOf(association.isRequired()));
        addAttribute(attrs, "containment",
                MmlConstants.getContainment(association.getContainment()));
        addAttribute(attrs, "multiplicity",
                MmlConstants.getMultiplicity(association.getMultiplicity()));

        startElement("association", attrs);
        handleDocumentation(association);
        handleAnnotations(association);
//        handleDeprecationInfo(association);
        if (association.getReverse() != null) {
            handle(association.getReverse());
        }
        endElement("association");
    }

    private void handle(MdfDatasetMappedProperty property) throws SAXException {
        messageHandler.info("Writing dataset property " + property.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", property.getName());

        MdfEntity type = property.getType();
        if (type instanceof MdfDataset) {
            addAttribute(attrs, "dataset", getRelativeName(
                    property.getParentDataset().getParentDomain(), type));
        }
        addAttribute(attrs, "unique", Boolean.valueOf(property.isUnique()));
        addAttribute(attrs, "single-valued", Boolean.valueOf(property.isSingleValued()));
        addAttribute(attrs, "path", property.getPath());

        startElement("mapped-property", attrs);
        handleDocumentation(property);
        handleAnnotations(property);
//        handleDeprecationInfo(property);
        endElement("mapped-property");
    }

    private void handle(MdfDatasetDerivedProperty property) throws SAXException {
        messageHandler.info("Writing dataset property " + property.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", property.getName());

        MdfEntity type = property.getType();
        if (type != null) {
	        addAttribute(attrs, "type", getRelativeName(
	                    property.getParentDataset().getParentDomain(), type));
        }
    
    	addAttribute(attrs, "multiplicity",
            MmlConstants.getMultiplicity(property.getMultiplicity()));
    
        if (property.getDefault() != null) {
            addAttribute(attrs, "default", property.getDefault());
        }

        startElement("derived-property", attrs);
        handleDocumentation(property);
        handleAnnotations(property);
//        handleDeprecationInfo(property);
        endElement("derived-property");
    
    }
    
    private void handle(MdfReverseAssociation association) throws SAXException {
        messageHandler.info("Writing reverse association "
                + association.getName());

        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "name", association.getName());

        addAttribute(attrs, "multiplicity",
                MmlConstants.getMultiplicity(association.getMultiplicity()));

        startElement("reverse-association", attrs);
        handleDocumentation(association);
        handleAnnotations(association);
//        handleDeprecationInfo(association);
        endElement("reverse-association");
    }

//    private void handleDeprecationInfo(MdfModelElement element)
//            throws SAXException {
//        if (null != element.getDeprecationInfo()) {
//            AttributesImpl attrs = new AttributesImpl();
//            addAttribute(attrs, "version",
//                    element.getDeprecationInfo().getVersion());
//            startElement("deprecated", attrs);
//            characters(element.getDeprecationInfo().getComment());
//            endElement("deprecated");
//        }
//    }

    private void handleDocumentation(MdfModelElement element)
            throws SAXException {
        String documentation = element.getDocumentation();

        if (documentation != null) {
            startElement("documentation", EMPTY_ATTRIBUTES);
            characters(documentation);
            endElement("documentation");
        }
    }

    private void handleAnnotations(MdfModelElement element) throws SAXException {
//        // Code for backward compatibility
//        Iterator it = element.getExtensions().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            handleExtension((String) entry.getKey(), entry.getValue());
//        }

    	Iterator it = sortAnnotationList(element.getAnnotations()).iterator();
        while (it.hasNext()) {
            handleAnnotation((MdfAnnotation) it.next());
        }
    }

    private void handleAnnotation(MdfAnnotation annotation) throws SAXException {
        AttributesImpl attrs = new AttributesImpl();
        addAttribute(attrs, "namespace", annotation.getNamespace());
        addAttribute(attrs, "name", annotation.getName());

        startElement("annotation", attrs);
        Iterator it = sortAnnotationPropertyList(annotation.getProperties()).iterator();
        while (it.hasNext()) {
            MdfAnnotationProperty p = (MdfAnnotationProperty) it.next();
            attrs = new AttributesImpl();
            addAttribute(attrs, "name", p.getName());
            startElement("property", attrs);
            if (p.isCDATA()) {
            	startCDATA();
            }
            if (p.getValue() != null) {
            	characters(p.getValue());
            } else {
            	characters("");
            }
            if (p.isCDATA()) {
                endCDATA();
            }
            endElement("property");

        }
        endElement("annotation");
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#startDTD(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void startDTD(String name, String publicId, String systemId)
            throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.startDTD(name, publicId, systemId);
        }
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#endDTD()
     */
    public void endDTD() throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.endDTD();
        }
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#startEntity(java.lang.String)
     */
    public void startEntity(String name) throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.startEntity(name);
        }
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#endEntity(java.lang.String)
     */
    public void endEntity(String name) throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.endEntity(name);
        }
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#startCDATA()
     */
    public void startCDATA() throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.startCDATA();
        }
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#endCDATA()
     */
    public void endCDATA() throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.endCDATA();
        }
    }

    /**
     * @see org.xml.sax.ext.LexicalHandler#comment(char[], int, int)
     */
    public void comment(char[] ch, int start, int length) throws SAXException {
        if (lexicalHandler != null) {
            lexicalHandler.comment(ch, start, length);
        }
    }
    
    private List sortModelElementList(List mdfModelElements) {
    	List dest = new ArrayList(mdfModelElements);
    	Collections.copy(dest, mdfModelElements);
    	Collections.sort(dest, MDF_MODEL_ELEMENT_COMPARATOR);
    	return dest;
    }
    
    private static class MdfModelElementComparator implements Comparator, Serializable {
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			MdfModelElement m1 = (MdfModelElement)o1;
			MdfModelElement m2 = (MdfModelElement)o2;
			String name1 = "";
			String name2 = "";
			if (m1!=null && m1.getName()!=null) {
				name1 = m1.getName();
			}
			if (m2!=null && m2.getName()!=null) {
				name2 = m2.getName();
			}
			return name1.compareTo(name2);
		}
    }
    
    private List sortAnnotationPropertyList(List mdfAnnotationProperties) {
    	List dest = new ArrayList(mdfAnnotationProperties);
    	Collections.copy(dest, mdfAnnotationProperties);
    	Collections.sort(dest, MDF_ANNOTATION_PROPERTY_COMPARATOR);
    	return dest;
    }
    
    private static class MdfAnnotationPropertyComparator implements Comparator, Serializable {
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			MdfAnnotationProperty m1 = (MdfAnnotationProperty)o1;
			MdfAnnotationProperty m2 = (MdfAnnotationProperty)o2;
			String name1 = "";
			String name2 = "";
			if (m1!=null && m1.getName()!=null) {
				name1 = m1.getName();
			}
			if (m2!=null && m2.getName()!=null) {
				name2 = m2.getName();
			}
			return name1.compareTo(name2);
		}
    }
    
    private List sortAnnotationList(List mdfAnnotations) {
    	List dest = new ArrayList(mdfAnnotations);
    	Collections.copy(dest, mdfAnnotations);
    	Collections.sort(dest, MDF_ANNOTATION_COMPARATOR);
    	return dest;
    }
    
    private static class MdfAnnotationComparator implements Comparator,Serializable {
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Object o1, Object o2) {
			MdfAnnotation m1 = (MdfAnnotation)o1;
			MdfAnnotation m2 = (MdfAnnotation)o2;
			String name1 = "";
			String name2 = "";
			if (m1!=null && m1.getName()!=null) {
				name1 = m1.getName();
			}
			if (m2!=null && m2.getName()!=null) {
				name2 = m2.getName();
			}
			return name1.compareTo(name2);
		}
    }
    
    
        
}

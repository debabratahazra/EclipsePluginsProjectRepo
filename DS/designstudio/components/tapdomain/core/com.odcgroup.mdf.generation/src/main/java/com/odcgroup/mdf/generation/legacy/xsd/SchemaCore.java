package com.odcgroup.mdf.generation.legacy.xsd;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfCoreDomain;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.otf.xml.XMLNameFormat;


/**
 * TODO: DOCUMENT ME!
 * 
 * @author <a href="mailto:cvedovini@odyssey-group.com">Claude Vedovini </a>
 * @version 1.0
 */
public class SchemaCore {

    /** The XML Schema namespace */
    public static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    /** The default prefix for XML Schema */
    public static final String XSD_PREFIX = "xs";
    private static final Map MML2XSD;

    public static final XMLNameFormat NAME_FORMAT = XMLNameFormat
            .getDefault();

    static {
        Map mml2xsd = new HashMap();

        mml2xsd.put(MdfCoreDomain.BOOLEAN, "xs:boolean");
        mml2xsd.put(MdfCoreDomain.BYTE, "xs:byte");
        mml2xsd.put(MdfCoreDomain.DATE, "xs:dateTime");
        mml2xsd.put(MdfCoreDomain.DATE_TIME, "xs:dateTime");
        mml2xsd.put(MdfCoreDomain.DOUBLE, "xs:double");
        mml2xsd.put(MdfCoreDomain.FLOAT, "xs:float");
        mml2xsd.put(MdfCoreDomain.INTEGER, "xs:integer");
        mml2xsd.put(MdfCoreDomain.LONG, "xs:long");
        mml2xsd.put(MdfCoreDomain.SHORT, "xs:short");
        mml2xsd.put(MdfCoreDomain.CHAR, "xs:string");
        mml2xsd.put(MdfCoreDomain.STRING, "xs:string");
        mml2xsd.put(MdfCoreDomain.URI, "xs:anyURI");
        mml2xsd.put(MdfCoreDomain.DECIMAL, "xs:decimal");

		mml2xsd.put(MdfCoreDomain.BOOLEAN_OBJ, "xs:boolean");
		mml2xsd.put(MdfCoreDomain.BYTE_OBJ, "xs:byte");
		mml2xsd.put(MdfCoreDomain.DOUBLE_OBJ, "xs:double");
		mml2xsd.put(MdfCoreDomain.FLOAT_OBJ, "xs:float");
		mml2xsd.put(MdfCoreDomain.INTEGER_OBJ, "xs:integer");
		mml2xsd.put(MdfCoreDomain.LONG_OBJ, "xs:long");
		mml2xsd.put(MdfCoreDomain.SHORT_OBJ, "xs:short");
		mml2xsd.put(MdfCoreDomain.CHAR_OBJ, "xs:string");

        MML2XSD = Collections.unmodifiableMap(mml2xsd);
    }

    /**
     * TODO: DOCUMENT ME!
     * 
     * @param domain TODO: DOCUMENT ME!
     * @return TODO: DOCUMENT ME!
     */
    public static String getDomainSchemaPath(String domain) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("META-INF").append(File.separatorChar);
        buffer.append("schemas").append(File.separatorChar);
        buffer.append(NAME_FORMAT.format(domain)).append(".xsd");
        return buffer.toString();
    }

    /**
     * Returns the XML Schema type name for the specified MDF enumeration.
     * 
     * @param enum a MDF enumeration instance.
     * @return an XML Schema type name.
     */
    public static String getMdfValueTypeRef(MdfEnumeration e) {
        return (String) MML2XSD.get(e.getType());
    }

    /**
     * Returns the XML Schema type name for the specified MDF property.
     * 
     * @param property a MDF property.
     * @return an XML Schema type name.
     */
    public static String getPropertyType(MdfProperty property) {
    	MdfEntity typeEntity = property.getType();
		if(typeEntity instanceof MdfBusinessType) {
			MdfBusinessType businessType = (MdfBusinessType) typeEntity;
			typeEntity = businessType.getType();
		}

        String ptype = (String) MML2XSD.get(typeEntity);

        if (ptype != null) { return ptype; }

        StringBuffer completeType = new StringBuffer();
        MdfDomain domain = property.getParentClass().getParentDomain();
        String refDomainName = typeEntity.getQualifiedName()
                .getDomain();

        if (!refDomainName.equals(domain.getName())) {
            completeType.append(refDomainName);
            completeType.append(":");
        }

        StringBuffer type = new StringBuffer();
        type.append(typeEntity.getQualifiedName().getLocalName());

        if ((property instanceof MdfAssociation)
                && (!typeEntity.isPrimitiveType())
                && (((MdfAssociation) property).getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE)) {
            type.append("Ref");
        }

        completeType.append(type.toString());
        return completeType.toString();
    }

    /**
     * Converts a qualified MML type name to an XML Schema type name.
     * 
     * @param entity the qualified MML type name.
     * @return an XML Schema type name.
     */
    public static String toXSDType(MdfEntity entity) {
        String ptype = (String) MML2XSD.get(entity);

        if (ptype != null) {
            return ptype;
        } else {
            MdfName qname = entity.getQualifiedName();
            MdfDomain domain = entity.getParentDomain();

            if (domain.getName().equals(qname.getDomain())) {
                return qname.getLocalName();
            } else {
                return qname.toString();
            }
        }
    }
}
package com.odcgroup.mdf.ecore;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;

/**
 * MdfCoreDomain done right for EMF.
 * 
 * The "legacy" one in MDF Core has "bad" non-EMF Primitives which don't work for us.
 * 
 * @author Michael Vorburger
 */
public class PrimitivesDomain {

    public static final String NAME = "mml";

	/** The singleton core domain instance */
    public static final MdfDomainImpl DOMAIN;
    
	public static final MdfPrimitiveImpl BOOLEAN;
	public static final MdfPrimitiveImpl BYTE;
	public static final MdfPrimitiveImpl CHAR;
	public static final MdfPrimitiveImpl DOUBLE;
	public static final MdfPrimitiveImpl FLOAT;
	public static final MdfPrimitiveImpl INTEGER;
	public static final MdfPrimitiveImpl LONG;
	public static final MdfPrimitiveImpl SHORT;
	public static final MdfPrimitiveImpl DECIMAL;
	
	public static final MdfPrimitiveImpl DATE;
	public static final MdfPrimitiveImpl DATE_TIME;
	public static final MdfPrimitiveImpl STRING;
	public static final MdfPrimitiveImpl URI;
	public static final MdfPrimitiveImpl BOOLEAN_OBJ;
	public static final MdfPrimitiveImpl BYTE_OBJ;
	public static final MdfPrimitiveImpl CHAR_OBJ;
	public static final MdfPrimitiveImpl DOUBLE_OBJ;
	public static final MdfPrimitiveImpl FLOAT_OBJ;
	public static final MdfPrimitiveImpl INTEGER_OBJ;
	public static final MdfPrimitiveImpl LONG_OBJ;
	public static final MdfPrimitiveImpl SHORT_OBJ;

	private static Map<MdfPrimitiveImpl, Class<?>> javaTypes = new HashedMap(25);
	
	/**
	 * This was introduced for backwards compatibility with the old
	 * com.odcgroup.mdf.transform.java.JavaCore (mdf-core.jar), because
	 * that doesn't work anymore, since it compares to the now "bad" non-EMF Primitives.
	 *  
	 * @param primitive MDF Primitive
	 * @return Java type (e.g. String.class etc. etc.)
	 */
	public static Class<?> getJavaType(MdfPrimitive primitive) {
		Class<?> clazz = javaTypes.get(primitive);
		if (clazz == null)
			// BEWARE: If primitive.getClass() isn't our EMF *Impl but the old MDF, then.. you should be using this, but JavaCore (but WHY/where in DS would you still have old MDF Impl?!)
			throw new IllegalArgumentException("No registered Java class for MDF Primitive with QN: " + primitive.getQualifiedName().toString() + " of class: " + primitive.getClass());
		return clazz;
	}
	
	public static boolean hasUnderlyingPrimitiveType(MdfEntity entity) {
		return ((entity instanceof MdfEnumeration)
				|| (entity instanceof MdfBusinessType)
				|| (entity instanceof MdfPrimitive));
	}
	
	public static Class<?> getJavaType(MdfEntity entity) {
		if (entity instanceof MdfEnumeration) {
			return getJavaType(((MdfEnumeration) entity).getType());
		} else if (entity instanceof MdfBusinessType) {
			return getJavaType(((MdfBusinessType) entity).getType());
		} else if (entity instanceof MdfPrimitive) {
			return getJavaType(((MdfPrimitive) entity));
		} else {
			throw new IllegalArgumentException("Unknow Java type for MdfEntity: " + entity.getQualifiedName().toString() + " of class: " + entity.getClass());
		}
	}
	// copy/paste from JavaCore getTypeName(MdfProperty property)
	public static Class<?> getJavaType(MdfAttribute attribute) {
		try {
			return getJavaType(attribute.getType());
		} catch (Exception e) {
			// NOTE: Unlike the old JavaCore, this does NOT (yet?) handle non-enum/BT MdfAttributes..
			throw new IllegalArgumentException("Unknow Java type for MdfAttribute: " + attribute.getQualifiedName(), e);
		}
	}			
	public static Class<?> getJavaType(MdfProperty property) {
		if (property instanceof MdfAttribute) {
			return getJavaType((MdfAttribute) property);
//		} else if (property instanceof MdfAssociation) {
//			return getJavaType((MdfAssociation) property);
//		} else if (property instanceof MdfReverseAssociation) {
//			return getJavaType((MdfReverseAssociation) property);
		} else {
			throw new IllegalArgumentException("Unknow (unhandled) property type: " + property.getClass());
		}
	}
	public static Class<?> getJavaType(MdfDatasetProperty property) {
		if (property instanceof MdfDatasetMappedProperty) {
			return getJavaType((MdfDatasetMappedProperty) property);
		} else {
			return getJavaType((MdfDatasetDerivedProperty) property);
		}
	}
	public static Class<?> getJavaType(MdfDatasetDerivedProperty property) {
		if (property.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
			return List.class;
		} else {
			return getJavaType(property.getType());
		}
	}
	public static Class<?> getJavaType(MdfDatasetMappedProperty property) {
		if (property.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY && !(property).isSingleValued()) {
			return List.class;
		} else {
			return getJavaType(property.getType());
		}
	}
	
	static {
		DOMAIN = (MdfDomainImpl) MdfFactory.eINSTANCE.createMdfDomain();
		DOMAIN.setName(NAME);
		DOMAIN.setNamespace("http://www.odcgroup.com/mml");
		DOMAIN.setDocumentation("Core Domain for Primitives, created programmatically by mdf-ecore's (NOT TAP OCS MDF JAR) " + PrimitivesDomain.class.getName());
		DOMAIN.setMetamodelVersion("8.1.0"); // Just because it was introduced (in this incarnation) in DS v8.1.0.. ;-)

		org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createURI("resource:///core/primitives.domain"); // DS-7404
		Resource corePrimitivesDomainResource = new ResourceImpl(uri);
		corePrimitivesDomainResource.getContents().add(DOMAIN);
		
	    BOOLEAN = newPrimitive("boolean", boolean.class);
	    BYTE = newPrimitive("byte", byte.class);
	    CHAR = newPrimitive("char", char.class);
	    DOUBLE = newPrimitive("double", double.class);
	    FLOAT = newPrimitive("float", float.class);
	    INTEGER = newPrimitive("integer", int.class); 
	    LONG = newPrimitive("long", long.class);
	    SHORT = newPrimitive("short", short.class);
	    DECIMAL = newPrimitive("decimal", double.class);
	    
	    DATE = newPrimitive("date", Date.class);
	    DATE_TIME = newPrimitive("dateTime", Date.class);
		STRING  = newPrimitive("string", String.class);
	    URI = newPrimitive("URI", com.odcgroup.otf.utils.inet.URI.class);
	    BOOLEAN_OBJ = newPrimitive("Boolean", Boolean.class);
	    BYTE_OBJ = newPrimitive("Byte", Byte.class);
	    CHAR_OBJ = newPrimitive("Character", Character.class);
	    DOUBLE_OBJ = newPrimitive("Double", Double.class);
	    FLOAT_OBJ = newPrimitive("Float", Float.class);
	    INTEGER_OBJ = newPrimitive("Integer", Integer.class);
	    LONG_OBJ = newPrimitive("Long", Long.class);
	    SHORT_OBJ = newPrimitive("Short", Short.class);
	    
	    javaTypes = Collections.unmodifiableMap(javaTypes);
	}

	private static MdfPrimitiveImpl newPrimitive(String name, Class<?> clazz) {
		MdfPrimitiveImpl p = (MdfPrimitiveImpl) MdfFactory.eINSTANCE.createMdfPrimitive();
		p.setName(name);
		DOMAIN.getPrimitives().add(p);		
		javaTypes.put(p, clazz);
		return p;
	}

}

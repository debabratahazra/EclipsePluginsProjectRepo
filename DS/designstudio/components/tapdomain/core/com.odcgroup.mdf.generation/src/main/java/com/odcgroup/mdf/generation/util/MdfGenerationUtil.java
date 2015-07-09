package com.odcgroup.mdf.generation.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.transform.java.JavaCore;
import com.odcgroup.mdf.utils.MdfEntityProxy;
import com.odcgroup.otf.utils.inet.URI;
import com.odcgroup.otf.utils.lang.ISO8601;

/**
 * 
 * moved some of the methods from JavaCore to be able to use PrimitivesDomain
 */
public class MdfGenerationUtil {
	
	protected static final String COLLECTION = List.class.getName();
	protected static final String GENERIC_COLLECTION_PATTERN = COLLECTION + "/*<{0}>*/";
	protected static final String CONCRETE_GENERIC_COLLECTION_PATTERN = ArrayList.class.getName() + "/*<{0}>*/";
	public static final String DEFAULT_PACKAGE_NAME = "no_package";

	public static String getEnumValueInitCode(MdfEnumValue value) {
		return getTypeInitCode(value.getParentEnumeration().getType(), value
				.getValue(), false);
	}	

	public static String getPropertyInitCode(MdfProperty p) {
		if (p instanceof MdfAttribute) {
			return getAttributeInitCode((MdfAttribute) p);
		} else if (p instanceof MdfAssociation) {
			return JavaCore.getAssociationInitCode((MdfAssociation) p);
		}

		return null;
	}

	public static String getAttributeInitCode(MdfAttribute attribute) {
		try {
			String defaultValue = attribute.getDefault();
		
			if ("null".equalsIgnoreCase(defaultValue)) {
				defaultValue = null;
			}
		
			return getTypeInitCode((MdfPrimitive) attribute.getType(), defaultValue, false);
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the init code of the " + getContext(attribute) + " attribute", e);
		}
	}
	
	protected static String getTypeInitCode(MdfPrimitive type, String value, boolean forceObject) {
		Class javaType;
		
		if (type instanceof MdfEnumeration) {
			MdfEnumeration e = (MdfEnumeration) type;

			if (value != null) {
				try {
					return JavaCore.getQualifiedModelClassName(e) + "."
						+ e.getValue(value).getName();
				} catch (NullPointerException ex) {
					throw new RuntimeException("Default value is an invalid enum value");
				}
			}			

			if (forceObject) {
				javaType = convertToObjectType(PrimitivesDomain.getJavaType(e.getType()));
			} else {
				javaType = PrimitivesDomain.getJavaType(e.getType());
			}
			return getTypeInitCode(javaType, null);
		}


		if (forceObject) {
			javaType = convertToObjectType(PrimitivesDomain.getJavaType(type));
		} else {
			javaType = PrimitivesDomain.getJavaType(type);
		}
		return getTypeInitCode(javaType, value);
	}
	
	private static String getTypeInitCode(Class type, String value) {
		if (type == null) {
			throw new RuntimeException("Invalid Type!");
		}

		if (type == boolean.class) {
			if (value == null) {
				return Boolean.FALSE.toString();
			} else {
				return Boolean.valueOf(value).toString();
			}
		}

		if (type == byte.class) {
			if (value == null) {
				return "(byte) 0";
			} else {
				return "(byte) " + Byte.parseByte(value);
			}
		}

		if (type == char.class) {
			if ((value == null) || (value.length() == 0)) {
				return "'\\0'";
			} else {
				return "'" + value.charAt(0) + "'";
			}
		}

		if (type == double.class) {
			if (value == null) {
				return "0D";
			} else if ("NaN".equalsIgnoreCase(value)) {
				return "Double.NaN";
			} else { 
				return Double.parseDouble(value) + "D";
			}
		}

		if (type == float.class) {
			if (value == null) {
				return "0F";
			} else if ("NaN".equalsIgnoreCase(value)) {
				return "Float.NaN";
			} else {
				return Float.parseFloat(value) + "F";
			}
		}

		if (type == int.class) {
			if (value == null) {
				return "0";
			} else {
				return "" + Integer.parseInt(value);
			}
		}

		if (type == long.class) {
			if (value == null) {
				return "0L";
			} else {
				return Long.parseLong(value) + "L";
			}
		}

		if (type == short.class) {
			if (value == null) {
				return "(short) 0";
			} else {
				return "(short) " + value;
			}
		}

		if (type.isPrimitive()) {
			throw new RuntimeException("Invalid Type!");
		}

		if (value == null) {
			return null;
		}

		if (type == Date.class) {
			if ("today()".equals(value))
				return "new java.util.Date()";

			if ("now()".equals(value))
				return "new java.util.Date()";

			return ISO8601.class.getName() + ".parse(\"" + value + "\")";
		}

		if (type == String.class) {
			return "\"" + value + "\"";
		}

		if (type == URI.class) {
			return "new " + URI.class.getName() + "(\"" + value + "\")";
		}

		if (type == Boolean.class) {
			return "Boolean." + Boolean.valueOf(value).toString().toUpperCase();
		}

		if (((type == Double.class) || (type == Float.class))
				&& ("NaN".equalsIgnoreCase(value))) {
			return "null";
		}

		// OCS-29745/DS-2462 Optimize initialization for wrapper of primitive
		if (type == Byte.class ||
				type == Character.class ||
				type == Integer.class ||
				type == Long.class ||
				type == Short.class ||
				type == Double.class ||
				type == Float.class) {
			return getTypeInitCode(JavaCore.getPrimitiveType(type), value);
		} else {
			return "new " + JavaCore.getShortName(type.getName()) + "("
					+ getTypeInitCode(JavaCore.getPrimitiveType(type), value) + ")";
		}
	}

	/**
	 * @param association
	 * @return
	 */
	private static String getContext(MdfModelElement model) {
		if (model == null) {
			return "null";
		}
		try {
			return model.getQualifiedName().getQualifiedName();
		} catch (RuntimeException e) {
			return model.toString();
		}
		
	}

	/**
	 * Return the list type PermValues for the property
	 * @param property
	 * @return the list type PermValues for the property
	 */
	public static String getPermValuesListType(MdfDatasetProperty property) {
		MdfEntity type = property.getType();
		if (type instanceof MdfEnumeration) {
			MdfEnumeration e = (MdfEnumeration)type;
			// Get the enumerations underlying type
			return getGenericCollection(e.getType());
		} else if (property instanceof MdfDatasetMappedProperty && 
				((MdfDatasetMappedProperty)property).isTypeDataset()) {
			return getGenericCollection(property.getType());
		}
		throw new IllegalArgumentException("Cannot define PermValues for " + property.getName());
	}

	/**
	 * Return the generic collection adapted to the type  
	 * @param mdfEntity
	 * @return the generic collection adapted to the type
	 */
	public static String getGenericCollection(MdfEntity entity) {
		String arg;
		if (entity instanceof MdfPrimitive) {
			arg = JavaCore.getShortName(convertToObjectType(PrimitivesDomain.getJavaType((MdfPrimitive)entity)).getName());
		} else {
			arg = JavaCore.getQualifiedModelClassName(entity);
		}
		return MessageFormat.format(GENERIC_COLLECTION_PATTERN, new String[]{arg});
	}

	/**
	 * Return the list type PermValues for the property
	 * @param property
	 * @return the list type PermValues for the property
	 */
	public static String getPermValuesConcreteListType(MdfDatasetProperty property) {
		MdfEntity type = property.getType();
		if (type instanceof MdfEnumeration) {
			MdfEnumeration e = (MdfEnumeration)type;
			// Get the enumerations underlying type
			return getConcreteGenericCollection(e.getType());
		} else if (property instanceof MdfDatasetMappedProperty && 
				((MdfDatasetMappedProperty)property).isTypeDataset()) {
			return getConcreteGenericCollection(property.getType());
		}
		throw new IllegalArgumentException("Cannot define PermValues for " + property.getName());
	}

	/**
	 * Return the concrete generic collection adapted to the type  
	 * @param mdfEntity
	 * @return the generic collection adapted to the type
	 */
	public static String getConcreteGenericCollection(MdfEntity entity) {
		String arg;
		if (entity instanceof MdfPrimitive) {
			arg = JavaCore.getShortName(convertToObjectType(PrimitivesDomain.getJavaType(entity)).getName());
		} else {
			arg = JavaCore.getQualifiedModelClassName(entity);
		}
		return MessageFormat.format(CONCRETE_GENERIC_COLLECTION_PATTERN, new String[]{arg});
	}

	public static String getDatasetPropertyInitCode(MdfDatasetProperty p) {
		if (p instanceof MdfDatasetDerivedProperty) {
			return getDatasetDerivedPropertyInitCode((MdfDatasetDerivedProperty) p);
		}
		return null;
	}
	
	public static String getDatasetDerivedPropertyInitCode(MdfDatasetDerivedProperty p) {
		try {
			if (p.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				return "new " + ArrayList.class.getName() + "()";
			}
			
			String defaultValue = p.getDefault();
	
			if ("null".equalsIgnoreCase(defaultValue)) {
				defaultValue = null;
			}
	
			MdfEntity type = p.getType();
			
			if (type instanceof MdfPrimitive) {
				return getTypeInitCode((MdfPrimitive) type, defaultValue, true);
			} else {
				return "null";
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the dataset derived property init code of the " + getContext(p) + " property", e);
		}
	}
	
	/**
	 * TODO: DOCUMENT ME!
	 * 
	 * @param property
	 *            TODO: DOCUMENT ME!
	 * @return TODO: DOCUMENT ME!
	 */
	public static String getTypeName(MdfProperty property) {
		if (property instanceof MdfAttribute) {
			return getTypeName((MdfAttribute) property);
		} else if (property instanceof MdfAssociation) {
			return getTypeName((MdfAssociation) property);
		} else if (property instanceof MdfReverseAssociation) {
			return getTypeName((MdfReverseAssociation) property);
		} else {
			throw new RuntimeException("Unknow property type: "
					+ property.getClass());
		}
	}

	/**
	 * Returns the fully qualified Java class name referenced by the specified
	 * MDF association.
	 * 
	 * @param association
	 *            a MDF association
	 * @return a String.
	 */
	public static String getTypeName(MdfAssociation association) {
		try {
			if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				return COLLECTION;
			} else {
				MdfEntity entity = association.getType();
	
				if (association.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) {
					return getQualifiedRefClassName((MdfClass) entity);
				} else {
					return getQualifiedModelClassName(entity);
				}
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the type of the " + getContext(association) + " association", e);
		}
	}

	public static String getTypeName(MdfReverseAssociation association) {
		try {
			if (association.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				return COLLECTION;
			} else {
				MdfEntity entity = association.getType();
				return getQualifiedRefClassName((MdfClass) entity);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the type of the " + getContext(association) + " reverse association", e);
		}
	}

	/**
	 * Returns the fully qualified Java class name mapped with the type of
	 * specified MDF attribute.
	 * 
	 * @param attribute
	 *            a MDF attribute.
	 * @return a String.
	 */
	public static String getTypeName(MdfAttribute attribute) {
		try {
			MdfEntity entity = attribute.getType();
	
			if (entity instanceof MdfEnumeration) {
				entity = ((MdfEnumeration) entity).getType();
			}
			if (entity instanceof MdfBusinessType) {
				entity = ((MdfBusinessType) entity).getType();
			}
	
			return getQualifiedModelClassName(entity);
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the type of the " + getContext(attribute) + " attribute", e);
		}
	}

	public static String getTypeName(MdfDatasetMappedProperty property) {
		try {
			if (property.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY
					&& !(property).isSingleValued()) {
				return COLLECTION;
			}
	
			MdfEntity entity = property.getType();
	
			if (entity instanceof MdfEnumeration) {
				entity = ((MdfEnumeration) entity).getType();
			}
			if (entity instanceof MdfBusinessType) {
				entity = ((MdfBusinessType) entity).getType();
			}
	
			if (PrimitivesDomain.hasUnderlyingPrimitiveType(entity)) {
				Class type = (Class) convertToObjectType(PrimitivesDomain.getJavaType(entity));
				return type.getName();
			} else {
				StringBuffer buffer = new StringBuffer();
				buffer.append(getModelPackage(entity));
	
				if (buffer.length() > 0) {
					buffer.append('.');
				}
	
				buffer.append(getModelClassName(entity));
				return buffer.toString();
			}
	
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the type of the " + getContext(property) + " dataset mapped property.  (Might be a models dependencies issue.)", e);
		}
	}

	public static String getTypeName(MdfDatasetProperty property) {
		try {
			if (property instanceof MdfDatasetMappedProperty) {
				return getTypeName((MdfDatasetMappedProperty) property);
			} else {
				return getTypeName((MdfDatasetDerivedProperty) property);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the type of the " + getContext(property) + " dataset property", e);
		}
	}
	
	public static String getTypeName(MdfDatasetDerivedProperty property) {
		try {
			if (property.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				return COLLECTION;
			}
	
			MdfEntity entity = property.getType();
	
			if (entity instanceof MdfPrimitive) {
				if (entity instanceof MdfEnumeration) {
					entity = ((MdfEnumeration) entity).getType();
				}
				
				// SAM: See OCS-25828
				if (PrimitivesDomain.hasUnderlyingPrimitiveType(entity)) {
					Class type = (Class) convertToObjectType(PrimitivesDomain.getJavaType(entity));
					return type.getName();
				} else  {
					StringBuffer buffer = new StringBuffer();
					buffer.append(getModelPackage(entity));
		
					if (buffer.length() > 0) {
						buffer.append('.');
					}
		
					buffer.append(getModelClassName(entity));
					return buffer.toString();
				}
			} else {
				return getQualifiedModelClassName(entity);
			}
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get the type of the " + getContext(property) + " dataset derived property", e);
		}
	}
	
	/**
	 * Returns the Java classname for an entity
	 * 
	 * @param entity the MdfEntity
	 *         
	 * @return the Java classname
	 */
	public static String getModelClassName(MdfEntity entity) {
		return entity.getName();
	}
	
	/**
	 * Returns the qualified class name of the specified MDF entity.
	 * 
	 * @param entity
	 *            a MDF entity.
	 * @return TODO: DOCUMENT ME!
	 */
	public static String getQualifiedModelClassName(MdfEntity entity) {
		Class type = getJavaType(entity);

		if (type == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(getModelPackage(entity));

			if (buffer.length() > 0) {
				buffer.append('.');
			}

			buffer.append(getModelClassName(entity));
			return buffer.toString();
		}

		return type.getName();
	}

	/**
	 * Returns the qualified package for this domain.
	 * 
	 * @param domain
	 *            a MDF domain.
	 * @return a String.
	 */
	public static String getModelPackage(MdfDomain domain) {
		try {
			StringBuffer buffer = new StringBuffer();
			String pckge = JavaAspect.getPackage(domain);
	
			if (pckge != null) {
				pckge = pckge.trim();
				if (pckge.length() == 0)
					pckge = DEFAULT_PACKAGE_NAME;
			} else {
				pckge = DEFAULT_PACKAGE_NAME;
			}
	
			buffer.append(pckge);
			return buffer.toString();
		} catch (RuntimeException e) {
			throw new RuntimeException("Unable to get model package of the " + getContext(domain) + " domain", e);
		}
	}

	/**
	 * Returns the qualified package for this entity.
	 * 
	 * @param entity
	 *            a MDF entity.
	 * @return a String.
	 */
	public static String getModelPackage(MdfEntity entity) {
		return getModelPackage(entity.getParentDomain());
	}

	public static Class<?> getJavaType(MdfEntity entity) {
		if (MdfEntityProxy.isProxy(entity)) {
			entity = MdfEntityProxy.resolve(entity);
		}
		if(entity instanceof MdfBusinessType) {
			MdfBusinessType businessType = (MdfBusinessType) entity;
			entity = businessType.getType();
//		} else if(entity instanceof MdfEnumeration) {
//			entity = ((MdfEnumeration)entity).getType();
		}
		
		// resolve primitive types by the static hashmap
		if (PrimitivesDomain.hasUnderlyingPrimitiveType(entity)) {
			return PrimitivesDomain.getJavaType(entity);
		} else {
			return null;
		}
	}

	/**
	 * Returns the fully qualified ref name for the specified MDF entity.
	 * 
	 * @param klass
	 *            a MDF entity.
	 * @return a String.
	 */
	public static String getQualifiedRefClassName(MdfClass klass) {
		Class type = getJavaType(klass);

		if (type == null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(getRefPackage(klass));

			if (buffer.length() > 0) {
				buffer.append('.');
			}

			buffer.append(getRefClassName(klass));
			return buffer.toString();
		}

		return type.getName();
	}
	
	/**
	 * TODO: DOCUMENT ME!
	 * 
	 * @param entity
	 *            TODO: DOCUMENT ME!
	 * @return TODO: DOCUMENT ME!
	 */
	public static String getRefPackage(MdfEntity entity) {
		return getModelPackage(entity);
	}

	/**
	 * Returns the bean name for the specified MDF entity.
	 * 
	 * @param klass
	 *            a MDF entity.
	 * @return a String.
	 */
	public static String getRefClassName(MdfClass klass) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(klass.getName());
		buffer.append("Ref");
		return buffer.toString();
	}

	public static Class<?> convertToObjectType(Class<?> type) {
		if (type == boolean.class) return Boolean.class;
		if (type == byte.class) return Byte.class;
		if (type == char.class) return Character.class;
		if (type == double.class) return Double.class;
		if (type == float.class) return Float.class;
		if (type == int.class) return Integer.class;
		if (type == long.class) return Long.class;
		if (type == short.class) return Short.class;
		return type;
	}
	

}

package com.odcgroup.mdf.entity.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfConstants;
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
import com.odcgroup.mdf.transform.java.JavaConstNameFormat;
import com.odcgroup.otf.utils.inet.URI;
import com.odcgroup.otf.utils.lang.ISO8601;

/**
 * Helper for entity generators
 * @author yan
 */
public class EntityHelper {

    /** Namespace URI */
    public static final String NAMESPACE_URI = "http://www.odcgroup.com/mdf/java";

    public static final String PACKAGE = "Package";
    
    private static final String JAVA_UTIL_SET = "java.util.Set";

    private static final String DEFAULT_PACKAGE_NAME = "no_package";

	private static final Map<MdfPrimitive, Class<?>> MML2JAVAOBJ;

	static {
		Map<MdfPrimitive, Class<?>> mml2javaObj = new HashMap<MdfPrimitive, Class<?>>();

		mml2javaObj.put(PrimitivesDomain.BOOLEAN, Boolean.class);
		mml2javaObj.put(PrimitivesDomain.BYTE, Byte.class);
		mml2javaObj.put(PrimitivesDomain.DATE, Date.class);
		mml2javaObj.put(PrimitivesDomain.DATE_TIME, Date.class);
		mml2javaObj.put(PrimitivesDomain.DOUBLE, Double.class);
		mml2javaObj.put(PrimitivesDomain.FLOAT, Float.class);
		mml2javaObj.put(PrimitivesDomain.INTEGER, Integer.class);
		mml2javaObj.put(PrimitivesDomain.LONG, Long.class);
		mml2javaObj.put(PrimitivesDomain.SHORT, Short.class);
		mml2javaObj.put(PrimitivesDomain.CHAR, Character.class);
		mml2javaObj.put(PrimitivesDomain.STRING, String.class);
		mml2javaObj.put(PrimitivesDomain.URI, URI.class);
		mml2javaObj.put(PrimitivesDomain.DECIMAL, Double.class);

		mml2javaObj.put(PrimitivesDomain.BOOLEAN_OBJ, Boolean.class);
		mml2javaObj.put(PrimitivesDomain.BYTE_OBJ, Byte.class);
		mml2javaObj.put(PrimitivesDomain.DOUBLE_OBJ, Double.class);
		mml2javaObj.put(PrimitivesDomain.FLOAT_OBJ, Float.class);
		mml2javaObj.put(PrimitivesDomain.INTEGER_OBJ, Integer.class);
		mml2javaObj.put(PrimitivesDomain.LONG_OBJ, Long.class);
		mml2javaObj.put(PrimitivesDomain.SHORT_OBJ, Short.class);
		mml2javaObj.put(PrimitivesDomain.CHAR_OBJ, Character.class);

		MML2JAVAOBJ = Collections.unmodifiableMap(mml2javaObj);
	}
	
	private static final Comparator<MdfModelElement> COMPARATOR = 
		new Comparator<MdfModelElement>() {
			public int compare(MdfModelElement p1, MdfModelElement p2) {
				if (p1.getName() == null) {
					return +1;
				}
				if (p2.getName() == null) {
					return -1;
				}
				return p1.getName().compareTo(p2.getName());
			}
		};
	
    /**
     * Returns the Java package associated to the given MDF domain.
     * @param domain
     * @return the Java package
     */
    public static String getPackage(MdfDomain domain) {
    	return JavaAspect.getPackage(domain);
    }

    /**
     * @param properties
     * @return the properties grouped by type (primary key, business key, ...)
     * and sorted alphabetically in a group
     */
	public static List<MdfProperty> sortClassProperties(List<MdfProperty> properties) {
		// Sort alphabetically the properties
		List<MdfProperty> alphabeticallySortedProperties = new ArrayList<MdfProperty>(properties);
		Collections.sort(alphabeticallySortedProperties, COMPARATOR);
		
		// Group the result by category (primary key, business key, ...)
		Set<MdfProperty> result = new LinkedHashSet<MdfProperty>();
		
		// 1st primary keys
		for (MdfProperty property : alphabeticallySortedProperties) {
			if (property.isPrimaryKey()) {
				result.add(property);
			}
		}
		
		// 2nd business keys
		for (MdfProperty property : alphabeticallySortedProperties) {
			if (property.isBusinessKey()) {
				result.add(property);
			}
		}
		
		// 3rd attributes
		for (MdfProperty property : alphabeticallySortedProperties) {
			if (property instanceof MdfAttribute) {
				result.add(property);
			}
		}
		
		// 4th associations
		for (MdfProperty property : alphabeticallySortedProperties) {
			if ((property instanceof MdfAssociation) ||
					(property instanceof MdfReverseAssociation)) {
				result.add(property);
			}
		}
		
		// 5th the rest (for safety)
		for (MdfProperty property : alphabeticallySortedProperties) {
			result.add(property);
		}
		
		// Convert to a list
		return new ArrayList<MdfProperty>(result);
	}

	/**
	 * @param modelElements
	 * @return the model elements by their name sorted by their name
	 */
	public static List<MdfModelElement> sortDatasetProperties(List<MdfDatasetProperty> modelElements) {
		List<MdfModelElement> sortedElements = new ArrayList<MdfModelElement>(modelElements);
		Collections.sort(sortedElements, COMPARATOR);
		return sortedElements;
	}
	
	/**
	 * @param key
	 * @return the type of the property. If it is a java primitive type,
	 * return the wrapper type instead. 
	 */
	public static String getJavaType(MdfProperty property) {
		try {
			MdfEntity propertyType = property.getType();
			if(propertyType == null) {
				throw new RuntimeException("Failed to retrieve property type for " + property.getQualifiedName().getQualifiedName());
			}
			
			String result = getJavaType(propertyType);
	        if (property instanceof MdfAssociation || 
	        		property instanceof MdfReverseAssociation) {
	            if (property.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
	                result = JAVA_UTIL_SET + "<" + result + ">";
	            }
			}
	        
	        return result;
		} catch (RuntimeException e) {
			throw new EntityGeneratorException(property, e);
		}
	}

	/**
	 * @param propertyType
	 * @return the java type of the entity. If it is a java primitive type,
	 * return the wrapper type instead. 
	 */
	public static String getJavaType(MdfEntity propertyType) {
		try {
			
			return extractJavaType(propertyType);
		} catch (RuntimeException e) {
			throw new EntityGeneratorException(propertyType, e);
		}
	}

    private static String getShortName(String name) {
    	return StringUtils.removeStart(name, "java.lang.");
	}

	/**
	 * Return the java type of a data set property.
	 * @param property
	 *            The MDF data set property.
	 * @return The java type for a MDF data set property.
	 */
	public static String getJavaType(MdfDatasetProperty datasetProperty) {
		try {
			MdfEntity propertyType = datasetProperty.getType();
			
			if(propertyType == null) {
				throw new RuntimeException("Failed to retrieve property type for " + datasetProperty.getQualifiedName().getQualifiedName());
			}
			
			String result = "";
			result = extractJavaType(propertyType);
			
			// DS-4763 
			if (propertyType instanceof MdfEnumeration) {
				if (((MdfEnumeration)propertyType).getType().getName().equals("EnumMask")) {
					return JAVA_UTIL_SET + "<" + result + ">";
				}
			}

			boolean isMultiValued = datasetProperty.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY;
			if (datasetProperty instanceof MdfDatasetMappedProperty) {
				isMultiValued = isMultiValued && !((MdfDatasetMappedProperty)datasetProperty).isSingleValued();
				
			}
			
			if (!isMultiValued) {
				return result;
			} else {
				return JAVA_UTIL_SET + "<" + result + ">";
			}
		} catch (RuntimeException e) {
			throw new EntityGeneratorException(datasetProperty, e);
		}
	}

	/**
	 * Extracts the java type from the MdfEntity, which is passed in.
	 * 
	 * @param propertyType
	 * @return The java type for the MdfEntity.
	 */
	private static String extractJavaType(MdfEntity propertyType) {
		MdfEntity currentPropertyType = propertyType;
		
		// If it's a an enum type, get the underlying type
		if (currentPropertyType instanceof MdfEnumeration) {
			currentPropertyType = ((MdfEnumeration)currentPropertyType).getType();
		}

		// If it's a business type, get the underlying type
		if (currentPropertyType instanceof MdfBusinessType) {
			currentPropertyType = ((MdfBusinessType)currentPropertyType).getType();
		}
		
        checkForNull(currentPropertyType, propertyType);
		
		// Resolve primitive types by the static HashMap
		String result;
		if (MML2JAVAOBJ.containsKey(currentPropertyType)) {
			Class<?> type = MML2JAVAOBJ.get(currentPropertyType);
			String qualifiedModelClassName = type.getName();
			result = getShortName(qualifiedModelClassName);
		} else {
			StringBuffer buffer = new StringBuffer();
			MdfDomain parentDomain =  currentPropertyType.getParentDomain();
			checkForNull(parentDomain, currentPropertyType);
			String pckge = getPackage(parentDomain);
			pckge = StringUtils.trim(pckge);
			if (StringUtils.isEmpty(pckge)) {
				pckge = DEFAULT_PACKAGE_NAME;
			}
			buffer.append(pckge);
			buffer.append('.');
			buffer.append(currentPropertyType.getName());
			result = buffer.toString();
		}
		return result;
	}
	
	/**
	 * Checks for null and will throw an exception is true, using the "parent" property
	 * type as the context.
	 *  
	 * @param currentPropertyType
	 * 				The propertyType which is being checked for null.
	 * @param propertyType 
	 * 				This is the propertyType which will give us the context.
	 */
	private static void checkForNull(MdfModelElement currentPropertyType, MdfModelElement propertyType) {
		MdfModelElementImpl currentPropertyTypeImpl = (MdfModelElementImpl) currentPropertyType;
		 // DS-7381 if (currentPropertyType == null || (currentPropertyTypeImpl.eIsProxy() && currentPropertyTypeImpl.getQualifiedName() == null)) {
		if (currentPropertyType == null || currentPropertyTypeImpl.eIsProxy()) {
			throw new RuntimeException("Failed to retrieve property type for " + propertyType.getQualifiedName().getQualifiedName());
		}
	}
	
	/**
	 * @param enumValue
	 * @return the java value to be used to initialize the enum value
	 */
	public static String getInitCode(MdfEnumValue enumValue) {
		try {
			MdfPrimitive enumType = enumValue.getParentEnumeration().getType();
			if (enumType instanceof MdfBusinessType) {
				enumType = ((MdfBusinessType)enumType).getType();
			}
			Class<?> javaType = MML2JAVAOBJ.get(enumType);
			String value = enumValue.getValue();
			
			if (javaType == null) {
				throw new RuntimeException("Invalid java type: " + javaType);
			}
			if (value == null) {
				throw new RuntimeException("Illegal value: " + value);
			}
	
			if (javaType == Boolean.class) {
				return Boolean.valueOf(value).toString();
			} else if (javaType == Byte.class) {
				return Byte.valueOf(value).toString();
			} else if (javaType == Short.class) {
				return "" + Short.valueOf(value);
			} else if (javaType == Integer.class) {
				return "" + Integer.valueOf(value);
			} else if (javaType == Long.class) {
				return Long.valueOf(value) + "L";
			} else if (javaType == Double.class) {
				if ("NaN".equalsIgnoreCase(value)) {
					return "Double.NaN";
				} else {
					return Double.valueOf(value) + "D";
				}
			} else if (javaType == Float.class) {
				if ("NaN".equalsIgnoreCase(value)) {
					return "Float.NaN";
				} else {
					return Float.valueOf(value) + "F";
				}
			} else if (javaType == Character.class) {
				if (value.length() != 1) {
					throw new RuntimeException("Illegal Character value: " + value);
				}
				return "'" + value.charAt(0) + "'";
			} else if (javaType == Date.class) {
				if ("today()".equals(value) || "now()".equals(value)) {
					return "new java.util.Date()";
				} else {
					return ISO8601.class.getName() + ".parse(\"" + value + "\")";
				}
			} else if (javaType == String.class) {
				return "\"" + value + "\"";
			} else if (javaType == URI.class) {
				return "new " + URI.class.getName() + "(\"" + value + "\")";
			}
			throw new RuntimeException("Invalid Type: " + javaType);
		} catch (RuntimeException e) {
			throw new EntityGeneratorException(enumValue, e);
		}
	}
	
	/**
	 * @param entity
	 * @return the identifier used to generate the model instance 
	 * stored in the model's bootstrap class 
	 */
	public static String getEntityModelInstanceName(MdfEntity entity) {
		return JavaConstNameFormat.INSTANCE.format(entity.getName());
	}

	
	/**
	 * For constants value, enums uses primitives to allow switch/case
	 * construct in the code
	 * @param propertyType
	 * @return
	 */
	public static String getJavaTypeForEnumConstant(MdfEntity propertyType) {
		String type = getJavaType(propertyType);
		 if (type.equals("Byte"))
			 return "byte";
		 if (type.equals("Double"))
			 return "double";
		 if (type.equals("Float"))
			 return "float";
		 if (type.equals("Integer"))
			return "int";
		 if (type.equals("Long"))
			 return "long";
		 if (type.equals("Character"))
			 return "char";
		 
		 return type;
	}
	
}

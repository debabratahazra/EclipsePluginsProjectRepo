package com.odcgroup.mdf.model.util;

import org.apache.commons.lang.StringUtils;

import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.mdf.utils.PathVisitor;

/**
 * Helper class to read/write specific annotations
 * @author atr
 */
public final class ModelAnnotationHelper {
	
	public static final int ANNOTATION_CONSTRAINTS_PROPERTY_MAX_DB_LENGTH = 60;

	public static final String ANNOTATION_CONSTRAINTS = "Constraints";
	public static final String ANNOTATION_CONSTRAINTS_NAMESPACE = "http://www.odcgroup.com/mdf/ext/constraints";
	public static final String ANNOTATION_CONSTRAINTS_PROPERTY_MAXLENGTH = "maxLength";

	public static final String ANNOTATION_TRIPLEA = "TripleA";
	public static final String ANNOTATION_TRIPLEA_NAMESPACE = "http://www.odcgroup.com/mdf/aaa";
	public static final String ANNOTATION_TRIPLEA_PROPERTY_DEFAULT_DISPLAY_LENGTH = "AttrDefaultDisplayLength";
	public static final String ANNOTATION_TRIPLEA_PROPERTY_MAX_DB_LENGTH = "AttrMaxDbLength";

	public static final String ANNOTATION_WIDGET = "Widget";
	public static final String ANNOTATION_WIDGET_NAMESPACE = "http://www.odcgroup.com/mdf/ext/gui";
	public static final String ANNOTATION_WIDGET_NAMESPACE_PROPERTY_MAXCHARS = "chars";

	private static class PathResolver extends PathVisitor {

		private final MdfDataset root;
		private MdfEntity type;
		private MdfProperty prop = null;
		private MdfAssociation assoc = null;

		PathResolver(MdfDataset root) {
			this.root = root;
			this.type = root.getBaseClass();
		}

		public boolean visit(String name) {
			if (type instanceof MdfClass) {
				prop = ((MdfClass) type).getProperty(name);
				if (prop == null) {
					prop = ModelHelper.getReverseAssociation(
							root.getParentDomain(), (MdfClass) type, name);
				}
				if (prop == null) {
					// Property <name> does not exist for class
					// <type.getQualifiedName()>
					return false;
				} else {
					if (prop instanceof MdfAssociation) {
						assoc = (MdfAssociation) prop;
					}
					type = prop.getType();
					return true;
				}
			} else {
				// Property <name> does not exist for type
				// <type.getQualifiedName()>
				return false;
			}
		}

		public MdfProperty resolve(String path) {
			PathVisitor.visitPath(path, this);
			return prop;
		}

	}
	
	/**
	 * Returns the mapped attribute
	 * @param dataset dataset that ownd the property
	 * @param property property of the dataset
	 * @return the mapped attribute
	 */
	public static MdfProperty getMappedAttribute(MdfDataset dataset, MdfDatasetMappedProperty property) {
		String path = ((MdfDatasetMappedProperty) property).getPath();
		PathResolver resolver = new PathResolver(dataset);
		MdfProperty mappedAttribute = resolver.resolve(path);
		return mappedAttribute;
	}	
	
	/**
	 * Returns the mapped attribute
	 * @param property attribute of a dataset
	 * @return the mapped attribute
	 */
	public static MdfProperty getMappedAttribute(MdfDatasetMappedProperty property) {
		return ModelAnnotationHelper.getMappedAttribute(property.getParentDataset(), property);
	}

	/**
	 * Retrieve the displayLength from the annotation
	 * 
	 * @param element
	 *            an element of the domain
	 * @return return a string representation of the display length or null if
	 *         not found.
	 */
	public static String getDisplayLengthFromAnnotation(MdfModelElement element) {

		// the result
		String displayLength = null;

		// retrieve the value from the business type
		if (element instanceof MdfBusinessType) {
			MdfBusinessType businessType = (MdfBusinessType) element;
			MdfAnnotation annotation = businessType.getAnnotation(ANNOTATION_WIDGET_NAMESPACE, ANNOTATION_WIDGET);
			if (annotation != null) {
				MdfAnnotationProperty anProperty = annotation.getProperty(ANNOTATION_WIDGET_NAMESPACE_PROPERTY_MAXCHARS);
				if (anProperty != null) {
					displayLength = anProperty.getValue();
				}
			}

			// retrieve the value from domain element
		} else {
			MdfAnnotation annotation = element.getAnnotation(ANNOTATION_TRIPLEA_NAMESPACE, ANNOTATION_TRIPLEA);
			if (annotation != null) {
				MdfAnnotationProperty anProperty = annotation.getProperty(ANNOTATION_TRIPLEA_PROPERTY_DEFAULT_DISPLAY_LENGTH);
				if (anProperty != null) {
					displayLength = anProperty.getValue();
				}
			}
		}

		// The display length is not null if defined.
		// It is then normalized
		if (displayLength != null) {
			displayLength = displayLength.trim();
			if (displayLength.isEmpty()) {
				displayLength = null;
			}
		}

		// result
		return displayLength;

	}

	/**
	 * Retrieve the db storage length from the annotation
	 * 
	 * @param element
	 *            an element of the domain
	 * @return return a string representation of the display length or null if
	 *         not found.
	 */
	public static String getMaxDatabaseLengthFromAnnotation(MdfModelElement element) {

		// the result
		String displayLength = null;

		// retrieve the value from the business type
		if (element instanceof MdfBusinessType) {
			MdfBusinessType businessType = (MdfBusinessType) element;
			MdfAnnotation annotation = businessType.getAnnotation(ANNOTATION_CONSTRAINTS_NAMESPACE, ANNOTATION_CONSTRAINTS);
			if (annotation != null) {
				MdfAnnotationProperty anProperty = annotation.getProperty(ANNOTATION_CONSTRAINTS_PROPERTY_MAXLENGTH);
				if (anProperty != null) {
					displayLength = anProperty.getValue();
				} else {
					// should never go here if business types are well defined
					displayLength = ANNOTATION_CONSTRAINTS_PROPERTY_MAX_DB_LENGTH+"";
				}
			}

			// retrieve the value from domain element
		} else {
			MdfAnnotation annotation = element.getAnnotation(ANNOTATION_TRIPLEA_NAMESPACE, ANNOTATION_TRIPLEA);
			if (annotation != null) {
				MdfAnnotationProperty anProperty = annotation.getProperty(ANNOTATION_TRIPLEA_PROPERTY_MAX_DB_LENGTH);
				if (anProperty != null) {
					displayLength = anProperty.getValue();
				}
			}
		}

		// The display length is not null if defined.
		// It is then normalized
		if (displayLength != null) {
			displayLength = displayLength.trim();
			if (displayLength.isEmpty()) {
				displayLength = null;
			}
		}

		// result
		return displayLength;

	}

	/**
	 * Retrieve the displayLength from the given attribute. If
	 * still not found retrieve it from the business type or null if not supported
	 * 
	 * @param dataset
	 *            the dataset
	 * @param attribute
	 *            the dataset attribute
	 */
	public static String getDisplayLength(MdfProperty attribute) {
		String displayLength = getDisplayLengthFromAnnotation(attribute);
		if (displayLength == null) {
			// retrieve the value from the base type
			MdfEntity type = attribute.getType();
			if (type instanceof MdfBusinessType) {
				String typeName = type.getName();
				if (typeName.equals("Code") || typeName.equals("Name")) {
					displayLength = getDisplayLengthFromAnnotation((MdfBusinessType) type);
				}
			}
		}
		return displayLength;
	}
	
	/**
	 * Retrieve the displayLength from the given dataset attribute. If not
	 * found, try to retrieve it from the mapped attribute in the base class. If
	 * still not found retrieve it from the business type.
	 * 
	 * @param dataset
	 *            the dataset
	 * @param attribute
	 *            the dataset attribute
	 */
	public static String getDisplayLength(MdfDataset dataset, MdfDatasetProperty attribute) {
		String displayLength = null;
		// retrieve the value from the dataset attribute
		displayLength = getDisplayLengthFromAnnotation(attribute);
		if (displayLength == null) {
			// retrieve the value from the mapped attribute in the base class.
			if (attribute instanceof MdfDatasetMappedProperty) {
				// resolve the path to find the attribute.
				MdfProperty mappedAttribute = getMappedAttribute(dataset, (MdfDatasetMappedProperty)attribute);
				if (mappedAttribute != null) {
					// the path is valid, retrieve the value from it
					displayLength = getDisplayLength(mappedAttribute);
				}
			} else { // derived attribute, simply retrieve the value from its type.
				MdfEntity type = attribute.getType();
				if (type instanceof MdfBusinessType) {
					String typeName = type.getName();
					if (typeName.equals("Code") || typeName.equals("Name")) {
						displayLength = getDisplayLengthFromAnnotation((MdfBusinessType) type);
					}
				}
			}
		}
		return displayLength;
	}

	/**
	 * Retrieve the db storage length from the given attribute. If not
	 * found, try to retrieve from the business type. Returns null if not supported
	 * 
	 * @param dataset
	 *            the dataset
	 * @param attribute
	 *            the dataset attribute
	 */
	public static String getMaxDatabaseLength(MdfProperty attribute) {
		String displayLength = getMaxDatabaseLengthFromAnnotation(attribute);
		if (displayLength == null) {
			// retrieve the value from the base type
			MdfEntity type = attribute.getType();
			if (type instanceof MdfBusinessType) {
				String typeName = type.getName();
				if (typeName.equals("Code") || typeName.equals("Name")) {
					displayLength = getMaxDatabaseLengthFromAnnotation((MdfBusinessType) type);
				}
			}
		}
		return displayLength;

	}

	/**
	 * Retrieve the db storage length from the given dataset attribute. If not
	 * found, try to retrieve it from the mapped attribute in the base class. If
	 * still not found retrieve it from the business type.
	 * 
	 * @param dataset
	 *            the dataset
	 * @param attribute
	 *            the dataset attribute
	 */
	public static String getMaxDatabaseLength(MdfDataset dataset, MdfDatasetProperty attribute) {
		String displayLength = null;
		// retrieve the value from the dataset attribute
		displayLength = getMaxDatabaseLengthFromAnnotation(attribute);
		if (displayLength == null) {
			// retrieve the value from the mapped attribute in the base class.
			if (attribute instanceof MdfDatasetMappedProperty) {
				// resolve the path to find the attribute.
				String path = ((MdfDatasetMappedProperty) attribute).getPath();
				if (StringUtils.isEmpty(path)) {
					return null;
				}
				PathResolver resolver = new PathResolver(dataset);
				MdfProperty mappedAttribute = resolver.resolve(path);
				if (mappedAttribute != null) {
					// the path is valid, retrieve the value from it
					displayLength = getMaxDatabaseLength(mappedAttribute);
				}
			} else { // derived attribute, simply retrieve the value from its type.
				MdfEntity type = attribute.getType();
				if (type instanceof MdfBusinessType) {
					String typeName = type.getName();
					if (typeName.equals("Code") || typeName.equals("Name")) {
						displayLength = getMaxDatabaseLengthFromAnnotation((MdfBusinessType) type);
					}
				}
			}
		}
		return displayLength;
	}
	
	/**
	 * Retrieve the displayLength from the given dataset attribute. If not
	 * found, try to retrieve it from the mapped attribute in the base class. If
	 * still not found retrieve it from the business type.
	 * 
	 * @param dataset
	 *            the dataset
	 * @param attributeName
	 *            the name of the attribute in the dataset
	 */
	public static String getDisplayLength(MdfDataset dataset, String attributeName) {
		String displayLength = null;
		MdfDatasetProperty attribute = dataset.getProperty(attributeName);
		if (attribute != null) {
			displayLength = ModelAnnotationHelper.getDisplayLength(dataset, attribute);
		}
		return displayLength;
	}

	/**
	 * Retrieve the displayLength from the given dataset attribute. If not
	 * found, try to retrieve it from the mapped attribute in the base class. If
	 * still not found retrieve it from the business type.
	 * 
	 * @param attribute
	 *            the dataset attribute
	 */
//	public static String getDisplayLength(MdfDatasetProperty attribute) {
//		return ModelAnnotationHelper.getDisplayLength(attribute.getParentDataset(), attribute);
//	}

	/**
	 * Retrieve the storage length from the given dataset attribute. If not
	 * found, try to retrieve it from the mapped attribute in the base class. If
	 * still not found retrieve it from the business type.
	 * 
	 * @param attribute
	 *            the dataset attribute
	 */
//	public static String getMaxDatabasebLength(MdfDatasetProperty attribute) {
//		return ModelAnnotationHelper.getMaxDatabaseLength(attribute.getParentDataset(), attribute);
//	}

}

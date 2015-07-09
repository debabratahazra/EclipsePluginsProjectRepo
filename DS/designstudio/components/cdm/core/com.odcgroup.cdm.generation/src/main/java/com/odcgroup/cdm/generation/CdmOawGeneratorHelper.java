package com.odcgroup.cdm.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.odcgroup.cdm.generation.util.CdmConstants;
import com.odcgroup.mdf.ecore.MdfAssociationImpl;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetDerivedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetMappedProperty;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfName;
import com.odcgroup.mdf.metamodel.MdfNameFactory;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.mdf.utils.ModelHelper;
import com.odcgroup.workbench.core.helper.StringHelper;

/**
 * Helper class.
 * 
 * @author Frederic Le Maitre
 * @author Gary Hayes
 */
public class CdmOawGeneratorHelper {

    private static Map<List<String>, String> cdmModelBeansRelations = new HashMap<List<String>, String>();
    
    // @see DS-1636 Single valued mapped attributes are not automatically generated in Dataset Impl
    private static List<MdfName> cdmClassWithoutAttributeDescriptor = Arrays.asList(
    		MdfNameFactory.createMdfName("CDMAPP", "DirectRelationshipNature"),
    		MdfNameFactory.createMdfName("CDMAPP", "DirectRelationshipType"),
    		MdfNameFactory.createMdfName("CDMAPP", "GroupRelationshipItem"),
    		MdfNameFactory.createMdfName("CDMAPP", "ParticipantRole"));

    private final static String JAVA_TYPE_BOOLEAN = "java.lang.Boolean";
    private final static String JAVA_TYPE_BYTE = "java.lang.Byte";
    private final static String JAVA_TYPE_CHARACTER = "java.lang.Character";
    private final static String JAVA_TYPE_DATE = "java.util.Date";
    private final static String JAVA_TYPE_DOUBLE = "java.lang.Double";
    private final static String JAVA_TYPE_FLOAT = "java.lang.Float";
    private final static String JAVA_TYPE_INTEGER = "java.lang.Integer";
    private final static String JAVA_TYPE_LONG = "java.lang.Long";
    private final static String JAVA_TYPE_SHORT = "java.lang.Short";
    private final static String JAVA_TYPE_STRING = "java.lang.String";
    @SuppressWarnings("unused")
	private final static String JAVA_TYPE_TIMESTAMP = "java.lang.Timestamp";
    private final static String JAVA_TYPE_URI = "com.odcgroup.otf.utils.inet.URI";
    
    private final static String MML_TYPE_BOOLEAN_OBJECT = "mml:Boolean";
    private final static String MML_TYPE_BOOLEAN_PRIMITIVE = "mml:boolean";
    private final static String MML_TYPE_BYTE_OBJECT = "mml:Byte";
    private final static String MML_TYPE_BYTE_PRIMITIVE = "mml:byte";
    private final static String MML_TYPE_CHARACTER_OBJECT = "mml:Character";
    private final static String MML_TYPE_CHAR_OBJECT = "mml:Char";
    private final static String MML_TYPE_CHAR_PRIMITIVE = "mml:char";
    private final static String MML_TYPE_DATE = "mml:date";
    private final static String MML_TYPE_DATETIME = "mml:dateTime";

    private final static String MML_TYPE_DOUBLE_OBJECT = "mml:Double";
    private final static String MML_TYPE_DOUBLE_PRIMITIVE = "mml:double";
    private final static String MML_TYPE_FLOAT_OBJECT = "mml:Float";
    private final static String MML_TYPE_FLOAT_PRIMITIVE = "mml:float";
    private final static String MML_TYPE_INTEGER_OBJECT = "mml:Integer";
    private final static String MML_TYPE_INTEGER_PRIMITIVE = "mml:integer";
    private final static String MML_TYPE_LONG_OBJECT = "mml:Long";
    private final static String MML_TYPE_LONG_PRIMITIVE = "mml:long";
    private final static String MML_TYPE_SHORT_OBJECT = "mml:Short";
    private final static String MML_TYPE_SHORT_PRIMITIVE = "mml:short";
    private final static String MML_TYPE_STRING = "mml:string";
    private final static String MML_TYPE_URI = "mml:URI";

    static {
		List<String> participantCompanyRelation = new ArrayList<String>();
		participantCompanyRelation.add("com.odafs.mirage.businessComponent.partyModel.Company");
		participantCompanyRelation.add("com.odafs.mirage.businessComponent.partyModel.ClientRelationship");
	
		List<String> participantPersonRelation = new ArrayList<String>();
		participantPersonRelation.add("com.odafs.mirage.businessComponent.partyModel.Person");
		participantPersonRelation.add("com.odafs.mirage.businessComponent.partyModel.ClientRelationship");
	
		List<String> clientRelationshipParticipantRelation = new ArrayList<String>();
		clientRelationshipParticipantRelation.add("com.odafs.mirage.businessComponent.accountModel.Participant");
	
		cdmModelBeansRelations.put(participantCompanyRelation,
			"com.odafs.mirage.businessComponent.accountModel.Participant");
		cdmModelBeansRelations.put(participantPersonRelation,
			"com.odafs.mirage.businessComponent.accountModel.Participant");
		cdmModelBeansRelations.put(clientRelationshipParticipantRelation,
			"com.odafs.mirage.businessComponent.partyModel.ClientRelationship");
    }

    /**
     * Returns true if the Attribute Descriptor should be added to the Dataset
     * implementation.
     * 
     * @param datasetProperty the dataset property.
     * @return <code>true</code> if the Attribute Descriptor should be added
     *         to the Dataset implementation else <code>false</code>.
     */
    public static boolean addAttributeDescriptor(MdfDatasetProperty datasetProperty) {
    	if (datasetProperty instanceof MdfDatasetMappedProperty) {
    		MdfDatasetMappedProperty datasetMappedProprety = (MdfDatasetMappedProperty)datasetProperty;

    		// If the referenced class doesn't have an attribute descriptor,
    		// then do not add it into the attribute descriptor initialization.
    		if (!hasAttributeDescriptor(datasetMappedProprety)) {
    			return false;
    		}
    	}
    	return !isMultiValued(datasetProperty);
    }

    /**
     * Returns true if the class referenced by the dataset property has its own
     * attribute descriptor. The check is based on know list of classes which 
     * doesn't have attribute descriptor (CDM specific).
	 * @param datasetProperty to test
	 * @return <code>true</code> if the class referenced by the dataset property 
	 *         has its own attribute descriptor, <code>false</code> otherwise.
	 */
	private static boolean hasAttributeDescriptor(MdfDatasetMappedProperty datasetProperty) {
		MdfClass currentClass = getSourceBean(datasetProperty);
		return !cdmClassWithoutAttributeDescriptor.contains(currentClass.getQualifiedName());
	}

	/**
     * Returns the piece of code that must be executed before the setter
     * instruction of the dataset property.
     * 
     * @param datasetProperty the dataset property.
     * @return a piece of code that must be executed before the setter
     *         instruction of the dataset property in parameter.
     */
    public static String beforeSetterInstruction(MdfDatasetMappedProperty datasetProperty) {
		String beforeSetterInstruction = "";
		String associationPropertyName = datasetProperty.getPath()
			.substring(datasetProperty.getPath().indexOf(".") + 1);
	
		if (MdfConstants.MULTIPLICITY_MANY == datasetProperty.getMultiplicity()) {
		    return beforeSetterInstruction;
		}
	
		if ("Country".equals(datasetProperty.getType().getName())) {
		    beforeSetterInstruction = "CountryCode myCountryCode = new CountryCode();myCountryCode.setISOValue("
			    + associationPropertyName + ");\n\n";
		}
	
		return beforeSetterInstruction;
    }

    /**
     * Returns the name of the attribute pointed by the dataset property.
     * 
     * @param datasetProperty the dataset property.
     * @return the name of the attribute pointed by the dataset property.
     */
    public static String getAttributeName(MdfDatasetMappedProperty datasetProperty) {
		if (isMultiValued(datasetProperty)) {
		    return datasetProperty.getName();
		}
	
		return datasetProperty.getPath().substring(datasetProperty.getPath().lastIndexOf(".") + 1);
    }

    /**
     * Returns the CDM name (or GCL name) of a dataset property. This name is
     * used for the security.
     * 
     * @param datasetProperty the dataset property.
     * @return the CDM name of the dataset property used for the security.
     */
    public static String getCDMName(MdfDatasetMappedProperty datasetProperty) {
		String cdmName = getAnnotationValue(datasetProperty, CdmConstants.GCL_NAMESPACE,
			CdmConstants.GCL_ANNOTATION_NAME, "value");
	
		if (cdmName == null) {
		    return "";
		}
	
		return cdmName;
    }

    /**
     * Returns the piece of code corresponding to the accessor methods of the
     * attributes of the dataset implementation.
     * 
     * @param dataset the dataset to treat.
     * @return the piece of code corresponding to the accessor methods of the
     *         attributes of the dataset implementation.
     */
    public static String getDatasetImplAttributesAccessorMethodsDeclaration(MdfDataset dataset) {
		StringBuffer result = new StringBuffer("");
		Map<String, String> attributes = getDatasetImplAttributes(dataset);
	
		for (String attributeName : attributes.keySet()) {
		    // Getter.
		    result.append("\npublic ");
		    result.append(attributes.get(attributeName));
		    result.append(" get");
		    result.append(StringHelper.toFirstUpper(attributeName));
		    result.append("() {");
		    result.append("return ");
		    result.append(attributeName);
		    result.append(";}\n");
	
		    // Setter.
		    result.append("\npublic void");
		    result.append(" set");
		    result.append(StringHelper.toFirstUpper(attributeName));
		    result.append("(");
		    result.append(attributes.get(attributeName));
		    result.append(" ");
		    result.append(attributeName);
		    result.append(") {");
		    result.append("this.");
		    result.append(attributeName);
		    result.append(" = ");
		    result.append(attributeName);
		    result.append(";\n");
	
		    result.append("\ninitializeAttributeDescriptor();\n");
		    result.append("}\n");
		}
	
		return result.toString();
    }

    /**
     * Returns the piece of code corresponding to the declaration of the
     * attributes of the dataset implementation.
     * 
     * @param dataset the dataset to treat.
     * @return the piece of code corresponding to the declaration of the
     *         attributes of the dataset implementation.
     */
    public static String getDatasetImplAttributesDeclaration(MdfDataset dataset) {
		StringBuffer result = new StringBuffer("");
		Map<String, String> attributes = getDatasetImplAttributes(dataset);
	
		for (String attributeName : attributes.keySet()) {
		    result.append("\nprivate ");
		    result.append(attributes.get(attributeName));
		    result.append(" ");
		    result.append(attributeName);
		    result.append(";");
		}
	
		return result.toString();
    }

    /**
     * Returns the piece of code corresponding to the constructor of the dataset
     * implementation.
     * 
     * @param dataset the dataset to treat.
     * @return the piece of code corresponding to the constructor of the dataset
     *         implementation.
     */
    public static String getDatasetImplConstructor(MdfDataset dataset) {
		StringBuffer result = new StringBuffer("");
		Map<String, String> attributes = getDatasetImplAttributes(dataset);
	
		List<String> constructorParameters = new ArrayList<String>(attributes.keySet());
		Collections.sort(constructorParameters);
	
		result.append("\npublic ").append(dataset.getName()).append("DatasetImpl");
		result.append("(");
		for (String attributeName : constructorParameters) {
		    result.append(attributes.get(attributeName));
		    result.append(" ");
		    result.append(attributeName);
		    result.append(", ");
		}
	
		if (result.lastIndexOf(", ") != -1) {
		    result.delete(result.lastIndexOf(", "), result.lastIndexOf(", ") + 2);
		}
	
		result.append(") {\n");
		for (String attributeName : constructorParameters) {
		    result.append("this.");
		    result.append(attributeName);
		    result.append(" = ");
		    result.append(attributeName);
		    result.append(";\n");
		}
	
		result.append("\ninitializeAttributeDescriptor();\n");
		result.append("}\n");
	
		return result.toString();
    }

    /**
     * Returns the getter instruction for the dataset property in parameter.
     * 
     * @param datasetProperty the dataset property.
     * @return the getter instruction for the dataset property in parameter.
     */
    public static String getGetterInstruction(MdfDatasetMappedProperty datasetProperty) {
		if (isMultiValued(datasetProperty)) {
		    return "return " + datasetProperty.getName() + ";";
		}
	
		String getterInstruction = getAnnotationValue(datasetProperty, CdmConstants.CDM_BACKEND_NAMESPACE,
			CdmConstants.CDM_BACKEND_GETTER_ANNOTATION_NAME, "value");
	
		String[] propertyPathElements = datasetProperty.getPath().split("\\.");
	
		if (MdfConstants.MULTIPLICITY_ONE == datasetProperty.getMultiplicity()) {
		    MdfDataset dataset = datasetProperty.getParentDataset();
	
		    if (propertyPathElements.length == 1) {
				if (getterInstruction == null) {
				    boolean isReverseAssociation = isReverseAssociation(dataset.getParentDomain(), dataset
					    .getBaseClass(), datasetProperty.getPath());
		
				    if (!isReverseAssociation) {
						getterInstruction = StringHelper.toFirstLower(dataset.getBaseClass().getName())
							+ ".get"
							+ StringHelper.toFirstUpper(dataset.getBaseClass().getProperty(
								datasetProperty.getPath()).getName()) + "()";
				    }
				} else {
				    if (getterInstruction.indexOf("#realClassName#") != -1) {
						getterInstruction = getterInstruction.replaceAll("#realClassName#", StringHelper
							.toFirstLower(dataset.getBaseClass().getName()));
				    }
				}
		    } else if (propertyPathElements.length == 2) {
				String associationEntityName = datasetProperty.getPath().substring(0,
					datasetProperty.getPath().indexOf("."));
				String associationPropertyName = datasetProperty.getPath().substring(
					datasetProperty.getPath().indexOf(".") + 1);
		
				if (getterInstruction == null) {
				    boolean isReverseAssociation = isReverseAssociation(dataset.getParentDomain(), dataset
					    .getBaseClass(), associationEntityName);
		
				    if (!isReverseAssociation) {
						getterInstruction = StringHelper.toFirstLower(((MdfClass) dataset.getBaseClass().getProperty(
							associationEntityName).getType()).getName())
							+ ".get" + StringHelper.toFirstUpper(associationPropertyName) + "()";
				    } else {
						Map<String, String> attributes = getDatasetImplAttributes(dataset);
			
						MdfReverseAssociation reverseAssociation = ModelHelper.getReverseAssociation(dataset
							.getParentDomain(), dataset.getBaseClass(), associationEntityName);
			
						String entityName = StringHelper.toFirstLower(reverseAssociation.getAssociation()
							.getParentClass().getName());
			
						if (attributes.get(entityName) != null) {
						    getterInstruction = entityName + ".get"
							    + StringHelper.toFirstUpper(associationPropertyName) + "()";
						}
				    }
				} else {
				    if (getterInstruction.indexOf("#realClassName#") != -1) {
						getterInstruction = getterInstruction.replaceAll("#realClassName#", StringHelper
							.toFirstLower(((MdfClass) dataset.getBaseClass().getProperty(associationEntityName)
								.getType()).getName()));
				    }
				}
		    } else if (propertyPathElements.length >= 3 && datasetProperty.isSingleValued()) {
		    	// Find the last association
		    	MdfClass lastAssociationPropertyType = getLastAssociationPropertyType(dataset, propertyPathElements);
		    	if (getterInstruction == null) {
			    	// The association must exists as a class attribute
		    		String associationPropertyName = propertyPathElements[propertyPathElements.length-2];
					Map<String, String> attributes = getDatasetImplAttributes(dataset);
					if (attributes.get(associationPropertyName) != null) {
			    		getterInstruction = StringHelper.toFirstLower(lastAssociationPropertyType.getName())
			    			+ ".get" + StringHelper.toFirstUpper(propertyPathElements[propertyPathElements.length-1]) + "()";
					} else {
						System.out.println("Why not ?!!");
					}
				} else {
				    if (getterInstruction.indexOf("#realClassName#") != -1) {
						getterInstruction = getterInstruction.replaceAll("#realClassName#", StringHelper
							.toFirstLower(lastAssociationPropertyType.getName()));
				    }
				}
		    }
	
		    if (getterInstruction == null) {
				getterInstruction = "//TODO This method has to be manually implemented in an inherit class.\nthrow new RuntimeException(\"This method has to be manually implemented in an inherit class.\");";
		    } else {
				getterInstruction = "return " + modifyGetterInstruction(datasetProperty, getterInstruction) + ";";
		    }
		} else {
		    getterInstruction = "//TODO This method has to be manually implemented in an inherit class.\nthrow new RuntimeException(\"This method has to be manually implemented in an inherit class. - YAN4\");";
		}
	
		return getterInstruction;
    }

    /**
     * Return the java type of a property.
     * 
     * @param property the property.
     * @return the java type for a property.
     */
    public static String getJavaType(MdfProperty property) {
		if (MdfConstants.MULTIPLICITY_MANY == property.getMultiplicity()) {
		    return "java.util.List";
		}
	
		if (property.getType() instanceof MdfEnumeration) {
		    MdfEnumeration mdfEnumeration = (MdfEnumeration) property.getType();
	
		    return translateMMLTypeInJavaType(mdfEnumeration.getType().toString());
		} else if (property.getType() instanceof MdfPrimitive) {
		    MdfPrimitive mdfPrimitive = (MdfPrimitive) property.getType();
	
		    return translateMMLTypeInJavaType(mdfPrimitive.getQualifiedName().toString());
		}
	
		return property.getType().getQualifiedName().getLocalName();
    }
    
    /**
     * @param property
     * @return
     */
    public static String getDefaultEnumeratedValue(MdfDatasetDerivedProperty property) {    	
    	if (property.getType() instanceof MdfEnumeration) {
    		MdfEnumeration mdfEnumeration = (MdfEnumeration) property.getType();
    		return mdfEnumeration.getValue(property.getDefault()).getValue();
    	} else {
    		return property.getDefault();
    	}
    }

    /**
     * Return the java type of a dataset property.
     * 
     * @param property the mdf dataset property.
     * @return the java type for a mdf dataset property.
     */
    public static String getJavaType(MdfDatasetProperty property) {
		if (isMultiValued(property)) {
		    return "java.util.List";
		}
	
		if (property.getType() instanceof MdfEnumeration) {
		    MdfEnumeration mdfEnumeration = (MdfEnumeration) property.getType();
	
		    return translateMMLTypeInJavaType(mdfEnumeration.getType().toString());
		} else if (property.getType() instanceof MdfPrimitive) {
		    MdfPrimitive mdfPrimitive = (MdfPrimitive) property.getType();
	
		    return translateMMLTypeInJavaType(mdfPrimitive.getQualifiedName().toString());
		}
	
		return property.getType().getQualifiedName().getLocalName();
    }
    
    /**
     * @param property
     * @return
     */
    public static String getDefaultValue(MdfDatasetDerivedProperty property) {
    	if (isMultiValued(property)) {
    		return null;
    	}
    	if (property.getDefault() != null && property.getDefault().trim().length()>0){
    		return property.getDefault();
    	}
    	return null;
    }

    /**
     * Returns the setter instruction for the dataset property in parameter.
     * 
     * @param datasetProperty the dataset property.
     * @return the setter instruction for the dataset property in parameter.
     */
    public static String getSetterInstruction(MdfDatasetMappedProperty datasetProperty) {
		if (isMultiValued(datasetProperty)) {
		    String n = datasetProperty.getName();
		    return "this." + n + " = " + n + ";";
		}
	
		String setterInstruction = getAnnotationValue(datasetProperty, CdmConstants.CDM_BACKEND_NAMESPACE,
			CdmConstants.CDM_BACKEND_SETTER_ANNOTATION_NAME, "value");
	
		String[] propertyPathElements = datasetProperty.getPath().split("\\.");
	
		if (MdfConstants.MULTIPLICITY_ONE == datasetProperty.getMultiplicity()) {
		    MdfDataset dataset = datasetProperty.getParentDataset();
		    String associationPropertyName = null;
	
		    if (propertyPathElements.length == 1) {
				associationPropertyName = getSourceBeanAttributeName(datasetProperty);
		
				if (setterInstruction == null) {
				    boolean isReverseAssociation = isReverseAssociation(dataset.getParentDomain(), dataset
					    .getBaseClass(), datasetProperty.getPath());
		
				    if (!isReverseAssociation) {
						setterInstruction = StringHelper.toFirstLower(dataset.getBaseClass().getName()) + ".set"
							+ StringHelper.toFirstUpper(associationPropertyName) + "("
							+ getSourceBeanAttributeName(datasetProperty) + ")";
				    }
				} else {
				    if (setterInstruction.indexOf("#realClassName#") != -1) {
						setterInstruction = setterInstruction.replaceAll("#realClassName#", StringHelper
							.toFirstLower(dataset.getBaseClass().getName()));
				    }
				}
		    } else if (propertyPathElements.length == 2) {
				String associationEntityName = datasetProperty.getPath().substring(0,
					datasetProperty.getPath().indexOf("."));
				associationPropertyName = datasetProperty.getPath().substring(
					datasetProperty.getPath().indexOf(".") + 1);
		
				if (setterInstruction == null) {
				    boolean isReverseAssociation = isReverseAssociation(dataset.getParentDomain(), dataset
					    .getBaseClass(), associationEntityName);
		
				    if (!isReverseAssociation) {
						setterInstruction = StringHelper.toFirstLower(((MdfClass) dataset.getBaseClass().getProperty(
							associationEntityName).getType()).getName())
							+ ".set"
							+ StringHelper.toFirstUpper(associationPropertyName)
							+ "("
							+ associationPropertyName + ")";
				    } else {
						Map<String, String> attributes = getDatasetImplAttributes(dataset);
			
						MdfReverseAssociation reverseAssociation = ModelHelper.getReverseAssociation(dataset
							.getParentDomain(), dataset.getBaseClass(), associationEntityName);
			
						String entityName = StringHelper.toFirstLower(reverseAssociation.getAssociation()
							.getParentClass().getName());
			
						if (attributes.get(entityName) != null) {
						    setterInstruction = entityName + ".set"
							    + StringHelper.toFirstUpper(associationPropertyName) + "("
							    + associationPropertyName + ")";
						}
				    }
				} else {
				    if (setterInstruction.indexOf("#realClassName#") != -1) {
						setterInstruction = setterInstruction.replaceAll("#realClassName#", StringHelper
							.toFirstLower(((MdfClass) dataset.getBaseClass().getProperty(associationEntityName)
								.getType()).getName()));
				    }
				}
			} else if (propertyPathElements.length >= 3 && datasetProperty.isSingleValued()) {
				// Find the last association 
				MdfClass lastAssociationPropertyType = getLastAssociationPropertyType(dataset, propertyPathElements);
				String associationEntityName = propertyPathElements[propertyPathElements.length-2];
	    		associationPropertyName = propertyPathElements[propertyPathElements.length-1];
	    		
	    		if (setterInstruction == null) {
			    	// The association must exists as a class attribute
					Map<String, String> attributes = getDatasetImplAttributes(dataset);
					if (attributes.get(associationEntityName) != null) {
					    setterInstruction = StringHelper.toFirstLower(lastAssociationPropertyType.getName())
				    		+ ".set" + StringHelper.toFirstUpper(associationPropertyName) + "("+associationPropertyName+")";
					} else {
						System.out.println("Why not ?!!");
					}
				} else {
				    if (setterInstruction.indexOf("#realClassName#") != -1) {
						setterInstruction = setterInstruction.replaceAll("#realClassName#", StringHelper
							.toFirstLower(lastAssociationPropertyType.getName()));
				    }
				}
			}
		
		    if (setterInstruction == null) {
			setterInstruction = "//TODO This method has to be manually implemented in an inherit class.\nthrow new RuntimeException(\"This method has to be manually implemented in an inherit class.\");";
		    } else {
			setterInstruction = modifySetterInstruction(datasetProperty, setterInstruction, associationPropertyName)
				+ ";";
		    }
		} else {
		    setterInstruction = "//TODO This method has to be manually implemented in an inherit class.\nthrow new RuntimeException(\"This method has to be manually implemented in an inherit class. - YAN2\");";
		}
	
		return setterInstruction;
    }

    /**
     * Returns the name of the attribute pointed by the dataset property.
     * 
     * @param datasetProperty the dataset property.
     * @return the name of the attribute pointed by the dataset property.
     */
    public static String getSourceBeanAttributeName(MdfDatasetMappedProperty datasetProperty) {
    	return datasetProperty.getPath().substring(datasetProperty.getPath().lastIndexOf(".") + 1);
    }

    /**
     * Returns the name of the class containing the attribute pointed by the
     * dataset property.
     * 
     * @param datasetProperty the dataset property.
     * @return the name of the class containing the attribute pointed by the
     *         dataset property.
     */
    public static String getSourceBeanName(MdfDatasetMappedProperty datasetProperty) {
		return StringHelper.toFirstLower(getSourceBean(datasetProperty).getName());
    }

    /**
     * Returns the class containing the attribute pointed by the
     * dataset property.
     * 
     * @param datasetProperty the dataset property.
     * @return the class containing the attribute pointed by the
     *         dataset property.
     */
    public static MdfClass getSourceBean(MdfDatasetMappedProperty datasetProperty) {
		MdfClass myClass = datasetProperty.getParentDataset().getBaseClass();
	
		if (datasetProperty.getPath().indexOf(".") != -1) {
		    String pieceOfPath = datasetProperty.getPath().substring(0, datasetProperty.getPath().lastIndexOf("."));
	
		    while (pieceOfPath.indexOf(".") != -1) {
			myClass = (MdfClass) getPropertyType(datasetProperty.getParentDataset().getParentDomain(), myClass,
				pieceOfPath.substring(0, datasetProperty.getPath().indexOf(".")));
	
			pieceOfPath = pieceOfPath.substring(pieceOfPath.indexOf(".") + 1);
		    }
	
		    myClass = (MdfClass) getPropertyType(datasetProperty.getParentDataset().getParentDomain(), myClass,
			    pieceOfPath);
		}
	
		return myClass;
    }

    /**
     * Tests if the dataset property is linked to a custom field or not.
     * 
     * @param datasetProperty the dataset property.
     * @return <code>true</code> if the dataset property is linked to a custom
     *         field, else <code>false</code>.
     */
    public static boolean isCustomField(MdfDatasetMappedProperty datasetProperty) {
		MdfAnnotation annotation = getAnnotation(datasetProperty, CdmConstants.CUSTOM_FIELD_NAMESPACE,
			CdmConstants.CUSTOM_FIELD_ANNOTATION_NAME);
	
		if (annotation != null) {
		    return true;
		}
	
		return false;
    }

    /**
     * Returns true if the property is multi-valued.
     * 
     * @param datasetProperty the dataset property.
     * @return <code>true</code> if the property is multi-valued, else
     *         <code>false</code>.
     */
    public static boolean isMultiValued(MdfDatasetProperty datasetProperty) {
		if (!(datasetProperty instanceof MdfDatasetMappedProperty)) {
			return datasetProperty.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY ? true : false;
		}

		MdfDatasetMappedProperty dmp = (MdfDatasetMappedProperty) datasetProperty;		
		
		if (dmp.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY && !dmp.isSingleValued()) {
			// Many-valued property
			return true;
		}
		return false;
	}

    /**
	 * Retrieve a specific annotation for a dataset property.
	 * 
	 * @param datasetProperty
	 *            the dataset property.
	 * @param namespace
	 *            the namespace of the annotation to retrieve.
	 * @param name
	 *            the name of the annotation to retrieve.
	 * @return a specific annotation for a dataset property.
	 */
    private static MdfAnnotation getAnnotation(MdfDatasetMappedProperty datasetProperty, String namespace, String name) {
		String[] propertyPathElements = datasetProperty.getPath().split("\\.");
	
		MdfClass myClass = datasetProperty.getParentDataset().getBaseClass();
		String associationPropertyName = datasetProperty.getPath();
	
		int i = 0;
	
		while (i < propertyPathElements.length) {
		    MdfEntity mdfEntity = getPropertyType(datasetProperty.getParentDataset().getParentDomain(), myClass,
			    propertyPathElements[i]);
	
		    if (mdfEntity instanceof MdfClass) {
			myClass = (MdfClass) mdfEntity;
		    } else {
			associationPropertyName = propertyPathElements[i];
		    }
	
		    i = i + 1;
		}
	
		if (myClass.getProperty(associationPropertyName) != null) {
		    return myClass.getProperty(associationPropertyName).getAnnotation(namespace, name);
		}
	
		return null;
    }

    /**
     * Retrieve a specific annotation value for a dataset property.
     * 
     * @param datasetProperty the dataset property.
     * @param namespace the namespace of the annotation to retrieve.
     * @param name the name of the annotation to retrieve.
     * @param property the property to retrieve in the annotation.
     * @return a specific annotation value for a dataset property.
     */
    private static String getAnnotationValue(MdfDatasetMappedProperty datasetProperty, String namespace, String name,
	    String property) {
		MdfAnnotation annotation = getAnnotation(datasetProperty, namespace, name);
	
		if (annotation == null) {
		    return null;
		}
	
		return annotation.getProperty(property).getValue();
    }

    /**
     * Return the attributes of the dataset implementation. In the map, the keys
     * contain the attributes name, the values contain the attributes class
     * name.
     * 
     * @param dataset the dataset
     * @return the map with the attributes name and class name.
     */
    private static Map<String, String> getDatasetImplAttributes(MdfDataset dataset) {
		Map<String, String> attributes = new HashMap<String, String>();
	
		MdfAnnotation backEndEntityAnnotation = dataset.getBaseClass().getAnnotation(
			CdmConstants.CDM_BACKEND_NAMESPACE, CdmConstants.CDM_BACKEND_ENTITY_ANNOTATION_NAME);
	
		/*
		 * If the annotation does not exist on the base class of the dataset, we
		 * look on the super classes.
		 */
		if (backEndEntityAnnotation == null) {
		    MdfClass baseClass = dataset.getBaseClass().getBaseClass();
	
		    while (baseClass != null && backEndEntityAnnotation == null) {
			backEndEntityAnnotation = baseClass.getAnnotation(CdmConstants.CDM_BACKEND_NAMESPACE,
				CdmConstants.CDM_BACKEND_ENTITY_ANNOTATION_NAME);
	
			baseClass = baseClass.getBaseClass();
		    }
		}
	
		if (backEndEntityAnnotation == null) {
		    return attributes;
		}
	
		String backEndEntityName = backEndEntityAnnotation.getProperty("value").getValue();
		attributes.put(StringHelper.toFirstLower(dataset.getBaseClass().getName()), backEndEntityName);
	
		for (Object object : dataset.getProperties()) {
	
		    if (!(object instanceof MdfDatasetMappedProperty)) {
			// Only Mapped Dataset Properties are linked to real CDM classes
			continue;
		    }
		    MdfDatasetMappedProperty datasetProperty = (MdfDatasetMappedProperty) object;
	
		    if (datasetProperty.getPath().indexOf(".") != -1) {
			String entityPropertyName = datasetProperty.getPath().substring(0,
				datasetProperty.getPath().indexOf("."));
	
			if ((dataset.getBaseClass().getProperty(entityPropertyName) instanceof MdfAssociationImpl)
				|| isReverseAssociation(dataset.getParentDomain(), dataset.getBaseClass(), entityPropertyName)) {

				String[] propertyPathElements = datasetProperty.getPath().split("\\.");
				MdfClass propertyType;
				if (datasetProperty.isSingleValued()) {
					propertyType = getLastAssociationPropertyType(dataset, propertyPathElements);
				} else {
					propertyType = (MdfClass) getPropertyType(dataset.getParentDomain(), dataset
						    .getBaseClass(), entityPropertyName);
				}
	
			    String attributeName = StringHelper.toFirstLower(propertyType.getName());
			    String attributeClassName = null;
	
			    MdfAnnotation propertyTypeBackEndEntityAnnotation = propertyType.getAnnotation(
				    CdmConstants.CDM_BACKEND_NAMESPACE, CdmConstants.CDM_BACKEND_ENTITY_ANNOTATION_NAME);
	
			    /*
			     * If the annotation does not exist on the class we look on
			     * the super classes.
			     */
			    if (propertyTypeBackEndEntityAnnotation == null) {
					MdfClass baseClass = propertyType.getBaseClass();
		
					while (baseClass != null && propertyTypeBackEndEntityAnnotation == null) {
					    propertyTypeBackEndEntityAnnotation = baseClass
						    .getAnnotation(CdmConstants.CDM_BACKEND_NAMESPACE,
							    CdmConstants.CDM_BACKEND_ENTITY_ANNOTATION_NAME);
		
					    baseClass = baseClass.getBaseClass();
					}
			    }
	
			    if (propertyTypeBackEndEntityAnnotation != null) {
					attributeClassName = propertyTypeBackEndEntityAnnotation.getProperty("value").getValue();
			    }
	
			    if (attributeName != null && attributeClassName != null && attributes.get(attributeName) == null) {
					attributes.put(attributeName, attributeClassName);
			    }
			}
		    }
		}
	
		// Check missing attributes.
		for (List<String> relation : cdmModelBeansRelations.keySet()) {
		    boolean found = true;
	
		    for (String attribute : relation) {
				found = found && attributes.containsValue(attribute);
				if (!found) {
				    break;
				}
		    }
	
		    if (found) {
				String newAttribute = cdmModelBeansRelations.get(relation);
		
				if (!attributes.containsValue(newAttribute)) {
				    String newKey = StringHelper
					    .toFirstLower(newAttribute.substring(newAttribute.lastIndexOf(".") + 1));
				    attributes.put(newKey, newAttribute);
				}
		    }
	
		}
		
		return attributes;
    }

	/**
	 * @param dataset
	 * @param propertyPathElements
	 * @return
	 */
	private static MdfClass getLastAssociationPropertyType(MdfDataset dataset, String[] propertyPathElements) {
		MdfClass currentClass = dataset.getBaseClass().getBaseClass();
		for (int i=0; i<propertyPathElements.length-1; i++) {
			currentClass = (MdfClass)getPropertyType(currentClass.getParentDomain(), currentClass, propertyPathElements[i]);
		}
		return currentClass;
	}

    /**
     * Returns the type of the class property, even if the property is a reverse
     * association.
     * 
     * @param mdfDomain the domain.
     * @param mdfClass a class in the domain.
     * @param propertyName the name of the property.
     * @return the type of the class property.
     */
    private static MdfEntity getPropertyType(MdfDomain mdfDomain, MdfClass mdfClass, String propertyName) {
		if (mdfClass.getProperty(propertyName) != null) {
		    return mdfClass.getProperty(propertyName).getType();
		} else {
		    // It's a reverse association.
		    MdfReverseAssociation reverseAssociation = ModelHelper.getReverseAssociation(mdfDomain, mdfClass,
			    propertyName);
	
		    return reverseAssociation.getType();
		}
    }

    /**
     * Checks if the property is an association of the class via a reverse
     * association.
     * 
     * @param mdfDomain the domain.
     * @param mdfClass a class in the domain.
     * @param propertyName the name of the property.
     * @return <code>true</code> if the property is a reverse association,
     *         else <code>false</code>.
     */
    private static boolean isReverseAssociation(MdfDomain mdfDomain, MdfClass mdfClass, String propertyName) {
		MdfReverseAssociation reverseAssociation = ModelHelper.getReverseAssociation(mdfDomain, mdfClass, propertyName);
	
		return reverseAssociation != null;
    }

    private static String modifyGetterInstruction(MdfDatasetMappedProperty datasetProperty, String getterInstruction) {
        // TODO: Starting with OCS 1.40 on Java 6, return Integer.valueOf(...) instead of return new Integer(...) 
    	// should be used (but it's not yet possible in 1.30.6, as valueOf is @since Java 1.5 only).
		if (JAVA_TYPE_BOOLEAN.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Boolean(" + getterInstruction + ")";
		} else if (JAVA_TYPE_BYTE.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Byte(" + getterInstruction + ")";
		} else if (JAVA_TYPE_CHARACTER.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Character(" + getterInstruction + ")";
		} else if (JAVA_TYPE_DOUBLE.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Double(" + getterInstruction + ")";
		} else if (JAVA_TYPE_FLOAT.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Float(" + getterInstruction + ")";
		} else if (JAVA_TYPE_INTEGER.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Integer(" + getterInstruction + ")";
		} else if (JAVA_TYPE_LONG.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Long(" + getterInstruction + ")";
		} else if (JAVA_TYPE_SHORT.equals(getJavaType(datasetProperty))) {
		    getterInstruction = "new Short(" + getterInstruction + ")";
		}
	
		return getterInstruction;
    }

    private static String modifySetterInstruction(MdfDatasetMappedProperty datasetProperty, String setterInstruction,
	    String associationPropertyName) {
		int coupure = setterInstruction.lastIndexOf(associationPropertyName) + associationPropertyName.length();
	
		if (coupure == -1) {
		    return setterInstruction;
		}
	
		String setterInstructionBegin = setterInstruction.substring(0, coupure);
		String setterInstructionEnd = setterInstruction.substring(coupure);
	
		if (JAVA_TYPE_BOOLEAN.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".booleanValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_BYTE.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".byteValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_CHARACTER.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".charValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_DOUBLE.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".doubleValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_FLOAT.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".floatValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_INTEGER.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".intValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_LONG.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".longValue()" + setterInstructionEnd;
		} else if (JAVA_TYPE_SHORT.equals(getJavaType(datasetProperty))) {
		    setterInstruction = setterInstructionBegin + ".shortValue()" + setterInstructionEnd;
		}
	
		return setterInstruction;
    }

    /**
     * Translate a MML type in a Java type (primitive type if possible). For
     * example "mml:integer" is translated in "int".
     * 
     * @param mmlType a MML type ("mml:string", "mml:integer", "mml:double",
     *                etc.)(.
     * @return the corresponding Java type "String", "int", "double", etc.).
     */
    //DS-1647
    public static String translateMMLTypeInJavaType(String mmlType) {

		if (mmlType == null || mmlType.length() == 0) {
		    return "";
		} else if (MML_TYPE_BOOLEAN_PRIMITIVE.equals(mmlType) || MML_TYPE_BOOLEAN_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_BOOLEAN;
		} else if (MML_TYPE_BYTE_PRIMITIVE.equals(mmlType) || MML_TYPE_BYTE_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_BYTE;
		} else if (MML_TYPE_CHAR_PRIMITIVE.equals(mmlType)
					|| MML_TYPE_CHAR_OBJECT.equals(mmlType)
					|| MML_TYPE_CHARACTER_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_CHARACTER;
		} else if (MML_TYPE_DOUBLE_PRIMITIVE.equals(mmlType) || MML_TYPE_DOUBLE_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_DOUBLE;
		} else if (MML_TYPE_FLOAT_PRIMITIVE.equals(mmlType) || MML_TYPE_FLOAT_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_FLOAT;
		} else if (MML_TYPE_INTEGER_PRIMITIVE.equals(mmlType) || MML_TYPE_INTEGER_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_INTEGER;
		} else if (MML_TYPE_LONG_PRIMITIVE.equals(mmlType) || MML_TYPE_LONG_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_LONG;
		} else if (MML_TYPE_SHORT_PRIMITIVE.equals(mmlType) || MML_TYPE_SHORT_OBJECT.equals(mmlType)) {
		    return JAVA_TYPE_SHORT;
		} else if (MML_TYPE_DATE.equals(mmlType)) {
		    return JAVA_TYPE_DATE;
		} else if (MML_TYPE_DATETIME.equals(mmlType)) {
		    //return JAVA_TYPE_TIMESTAMP; //DS-1650
			return JAVA_TYPE_DATE;
		} else if (MML_TYPE_STRING.equals(mmlType)) {
		    return JAVA_TYPE_STRING;
		} else if (MML_TYPE_URI.equals(mmlType)) {
		    return JAVA_TYPE_URI;
		}
	
		return mmlType;
    }

}

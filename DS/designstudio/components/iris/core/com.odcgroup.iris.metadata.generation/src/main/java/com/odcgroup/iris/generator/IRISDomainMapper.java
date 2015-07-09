package com.odcgroup.iris.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.iris.generator.utils.GeneratorConstants;
import com.odcgroup.iris.generator.utils.IRISMetadataGeneratorCommon;
import com.odcgroup.mdf.ecore.MdfModelElementImpl;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.T24Aspect;
import com.odcgroup.t24.enquiry.util.EMEntity;
import com.odcgroup.t24.enquiry.util.EMProperty;
import com.odcgroup.t24.enquiry.util.EMTerm;
import com.odcgroup.t24.enquiry.util.EMTermType;
import com.odcgroup.t24.enquiry.util.EMUtils;


/**
 * Helper methods for MdfDomain objects.
 */
public class IRISDomainMapper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(IRISDomainMapper.class);
	private Boolean isT24Nature = null;

	public EMEntity getEntity(MdfClass mdfClass, FieldTypeChecker fieldTypeChecker, List<EMProperty> ignoreEMPropertyList) {
		EMEntity entity = null;
		
		setT24Nature(mdfClass);
		
		if (isT24Nature()) {
			entity = new EMEntity(EMUtils.camelCaseName(mdfClass.getName()));
		}
		else
		{
			entity = new EMEntity(mdfClass.getName());
		}
		String t24Name = IRISDomainMapper.getT24Name(mdfClass);
		entity.setT24Name(t24Name);
		
		//TODO : Remove once AA_Mortgage Sample is Removed - Add Entity vocabulary terms
		String t24AaProductGroup = getMdfClassAnnotation(mdfClass, GeneratorConstants.T24_ANNOTATION_AA_PRODUCT_GROUP);
		if(t24AaProductGroup != null) {
			entity.addVocabularyTerm(new EMTerm(EMTermType.T24_AA_PRODUCT_GROUP_TERM, t24AaProductGroup));
		}
		
		// Add all properties
		entity.addProperties(getDomainProperties(mdfClass, t24Name, fieldTypeChecker, ignoreEMPropertyList));
		return entity;
	}
	
	private void setT24Nature(MdfClass mdfClass) {
		if ( isT24Nature != null ) {
			return;
		}
		
		// Get T24 annotation from class
		MdfAnnotation annot = mdfClass.getAnnotation(GeneratorConstants.T24_ANNOTATION_NAMESPACE, GeneratorConstants.T24_ANNOTATION_NAME);
		if (annot == null) {			
			// Get T24 annotation from parent domain
			MdfDomain mdfDomain = mdfClass.getParentDomain();
			if (mdfDomain != null) 	{
							
				@SuppressWarnings("unchecked")
				Iterator<MdfAnnotation> it = mdfDomain.getAnnotations().iterator();

		        while (it.hasNext()) {
		            annot = it.next();
		            
		            if (annot.getNamespace().equals(GeneratorConstants.T24_ANNOTATION_NAMESPACE)) {
						isT24Nature =  true;								    
						return;
			        }
		        }
			}
			isT24Nature =  false;
		} 
		else {
			isT24Nature = true;
		}
	}
	
	protected boolean isT24Nature()
	{
		return isT24Nature.booleanValue();
	}
	
	public List<EMProperty> getDomainAsProperties(MdfClass mdfClass, FieldTypeChecker fieldTypeChecker, List<EMProperty> ignoreEMPropertyList) {
		if (mdfClass != null) {
			return getDomainProperties(mdfClass, IRISDomainMapper.getT24Name(mdfClass), fieldTypeChecker, ignoreEMPropertyList);
		} else {
			LOGGER.warn("MdfClass provided is null, returning empty properties...");
			return new ArrayList<EMProperty>();
		}
	}
	
	/**
	 * Method to return List<EMProperty> from MdfClass
	 * @param mdfClass
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<EMProperty> getDomainProperties(MdfClass mdfClass, String t24Name, FieldTypeChecker fieldTypeChecker, List<EMProperty> ignoreEMPropertyList) {
		List<EMProperty> emProperties = new ArrayList<EMProperty>();
		List<MdfProperty> properties = mdfClass.getProperties();
		// This is important to set T24 nature
		setT24Nature(mdfClass);	
		
		for (MdfProperty mdfProperty : properties) {
			Boolean isAlreadyExist = false;
			EMProperty property = map(mdfProperty, t24Name, "", fieldTypeChecker);
			addComplexProperty(mdfProperty, property, 1, t24Name, "", fieldTypeChecker);
			if(ignoreEMPropertyList != null) {
				Iterator<EMProperty> iter = ignoreEMPropertyList.iterator();
				while (iter.hasNext()) {
					if(property.equals(iter.next())) {
						isAlreadyExist = true;
						break;
					}
				}
				if(!isAlreadyExist) {
					ignoreEMPropertyList.add(property);
					emProperties.add(property);					
				}
			} else {
				emProperties.add(property);			
			}
		}
		return emProperties;
	}
	
	/**
	 * Extract T24 Name from MdfClass<Domain> Object
	 * @param modelElement
	 * @return
	 */
	public static String getT24Name(MdfModelElement modelElement) {
		String t24Name = T24Aspect.getT24Name(modelElement);
		if(t24Name == null) {	// TODO: this is a hack, the t24Name should never be null.
			t24Name = modelElement.getName();
			t24Name = t24Name.replace("_", ".");
		}
		return t24Name;
	}		
	
	private void addComplexProperty(MdfProperty mdfProperty, EMProperty property, int propertyDepth, String applicationName, String aaPropertyClassName, FieldTypeChecker fieldTypeChecker) {
		if (mdfProperty instanceof MdfAssociation) {
			MdfAssociation assoc = (MdfAssociation) mdfProperty;
			if (assoc.getContainment() == MdfConstants.CONTAINMENT_BYVALUE && assoc.getMultiplicity() == MdfConstants.MULTIPLICITY_MANY) {
				MdfEntity type = Application.getTypeAndWarnIfProxy(assoc);
				if (type instanceof MdfClass) {
					mapRef((MdfClass)type, property, propertyDepth, applicationName, aaPropertyClassName, fieldTypeChecker);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void mapRef(MdfClass mdfClass, EMProperty property, int propertyDepth, String applicationName, String aaPropertyClassName, FieldTypeChecker fieldTypeChecker) {
	
		// This is important to set T24 nature
		setT24Nature(mdfClass);	

		//Add complex type vocabulary terms
		String aaProperty = getMdfClassAnnotation(mdfClass, GeneratorConstants.T24_ANNOTATION_AA_PROPERTY);
		if(aaProperty != null) {
			property.addVocabularyTerm(new EMTerm(EMTermType.TERM_T24_AA_PROPERTY, aaProperty));
			property.addVocabularyTerm(new EMTerm(EMTermType.LIST_TERM, "true"));
			propertyDepth--;	// AA properties are more deeply nested, but could still contain their own Sv groups, so they need to be 'raised'
			// Find out the T24 Name and associate all nested properties with it
			aaPropertyClassName = aaPropertyClassName.equals("") ? getT24Name(mdfClass) : aaPropertyClassName;
			// We need an entry in T24 properties so set the T24 Name for AA Property Class
			property.setT24Name(aaPropertyClassName);
			
			// Additional AA Properties //
			
				// for AA Property Classes we have to add additional property to hold property class information
				// which is only available at runtime
				property.addSubProperty(new EMProperty(EMUtils.camelCaseName(mdfClass.getName()) + "_PropertyClass"));
				
				// Property to hold ForwardDate information
				EMProperty effectiveProp = new EMProperty("Effective");
				effectiveProp.setT24Name("EFFECTIVE");
				effectiveProp.setPropertyGroup("propertyGroup: " + aaPropertyClassName);
				property.addSubProperty(effectiveProp);
		}
		if (property.getName().equals("ErrorMessages")) {
			propertyDepth = 0;		// Special hack for the hand crafted 'ErrorMessages' group inside Hothouse POC.
		}
		
		String group = "";
		if (propertyDepth == 1) {
			group = GeneratorConstants.MVGROUP_SUFFIX;
		} else if (propertyDepth == 2) {
			group = GeneratorConstants.SVGROUP_SUFFIX;
		}
		property.setName(property.getName() + group);
		if (propertyDepth >= 1) {
			property.addVocabularyTerm(new EMTerm(EMTermType.LIST_TERM, "true"));
			IRISMetadataGeneratorCommon.addOriginalMvSvPropertiesInGroup(property);
		}
		propertyDepth++;
		
		List<MdfProperty> properties = mdfClass.getProperties();
		for (MdfProperty mdfProperty : properties) {
			if (T24Aspect.isMultiLanguage(mdfProperty)) {
				property.addVocabularyTerm(new EMTerm(EMTermType.TERM_LANG_TYPE,"true"));
				EMProperty languageCodeProperty = new EMProperty("LanguageCode");
				property.addSubProperty(languageCodeProperty);			
			}			
			if (!property.getName().equals(mdfProperty.getName())) {
				EMProperty prop = map(mdfProperty, applicationName, aaPropertyClassName, fieldTypeChecker);
				if (aaPropertyClassName != null && !aaPropertyClassName.equals("")) {
					prop.setPropertyGroup("propertyGroup: " + aaPropertyClassName);
				}
				addComplexProperty(mdfProperty, prop, propertyDepth, applicationName, aaPropertyClassName, fieldTypeChecker);
				property.addSubProperty(prop);
			} else {
				addVocabularyTerms(property, mdfProperty, applicationName, aaPropertyClassName, fieldTypeChecker);
			}
		}
	}
	
	private EMProperty map(MdfProperty mdfProperty, String applicationName, String aaPropertyClassName, FieldTypeChecker fieldTypeChecker) {
		EMProperty property = createEmProperty(mdfProperty.getName());
		property.setT24Name(T24Aspect.getT24Name((MdfModelElementImpl)mdfProperty));
		String t24JoinedTo = T24Aspect.getT24Name(Application.getTypeAndWarnIfProxy(mdfProperty));
		if (t24JoinedTo != null && !t24JoinedTo.isEmpty()) {
			property.setJoinedto("joinedTo: " + t24JoinedTo);
		}
		addVocabularyTerms(property, mdfProperty, applicationName, aaPropertyClassName, fieldTypeChecker);
		return property;
	}

	/**
	 * create EmProperty and camel case if T24 property
	 * @param mdfProperty
	 * @return
	 */
	private EMProperty createEmProperty(String propertyName) {
		EMProperty property = null;
		
		if (isT24Nature) {
			property = new EMProperty(EMUtils.camelCaseName( propertyName));
		}
		else
		{
			property = new EMProperty(propertyName);
		}
		return property;
	}
	
	/**
	 * @param property
	 * @param mdfProperty
	 */
	private void addVocabularyTerms(EMProperty property, MdfProperty mdfProperty, String applicationName, String aaPropertyClassName, FieldTypeChecker fieldTypeChecker) {
		// We do not want to generate TERM_ID fields for AA Property Classes
		if (mdfProperty.isPrimaryKey() && (aaPropertyClassName == null || aaPropertyClassName.isEmpty())) {
			property.addVocabularyTerm(new EMTerm(EMTermType.ID_TERM, "true"));
		}
		if (mdfProperty.isRequired()) {
			property.addVocabularyTerm(new EMTerm(EMTermType.REQ_TERM, "true"));	
		}	
		String valueType = this.getValueType(mdfProperty);
		if (valueType != null && fieldTypeChecker.isTypeSafe(applicationName, property.getT24Name())) {
			property.addVocabularyTerm(new EMTerm(EMTermType.TYPE_TERM, valueType));
		}
		
		String businessType = getBusinessType(mdfProperty);
		if (businessType != null) {
			property.addVocabularyTerm(new EMTerm(EMTermType.BUSINESS_TYPE_TERM, businessType));
		}
	}
	
	/**
	 * @param mdfProperty
	 * @return
	 */
	public String getValueType(MdfProperty mdfProperty) {
		if (mdfProperty == null) {
			return null;
		}
		String valueType = null;
		MdfEntity mdfEntity = Application.getTypeAndWarnIfProxy(mdfProperty);
		String t24Type = getMdfPropertyAnnotation(mdfProperty, GeneratorConstants.T24_ANNOTATION_T24TYPE);
		if (mdfEntity instanceof MdfPrimitive) {
			MdfPrimitive mdfPrimitive = (MdfPrimitive) mdfEntity;
			if (mdfPrimitive instanceof MdfEnumeration) {
				return null;
			} else if (mdfPrimitive instanceof MdfBusinessType) {
	        	MdfBusinessType bType = (MdfBusinessType) mdfPrimitive;
	        	MdfPrimitive prim = bType.getType();
	        	valueType = getTermValueType(prim, t24Type);
	        } else {
	        	valueType = getTermValueType(mdfPrimitive, t24Type);
	        }
		}
		return valueType;
	}

	public String getBusinessType(MdfProperty mdfProperty) {
		if (mdfProperty == null) return null;

		String businessType = getMdfPropertyAnnotation(mdfProperty, GeneratorConstants.T24_ANNOTATION_BUSINESS_TYPE);
		return businessType;
	}
	
	private String getTermValueType(MdfPrimitive type, String t24Type) {
		if (type.equals(PrimitivesDomain.STRING)) {
			//Check if this is a T24 enumeration (list of strings separated by comma)
			return t24Type != null && t24Type.equals(GeneratorConstants.T24_TYPE_ENUMERATION) ? t24Type : null;
		}
		else if (type.equals(PrimitivesDomain.CHAR)
				|| type.equals(PrimitivesDomain.CHAR_OBJ) 
				|| type.equals(PrimitivesDomain.BYTE)
				|| type.equals(PrimitivesDomain.BYTE_OBJ)
				|| type.equals(PrimitivesDomain.URI)) {
			return null;
		} else if (type.equals(PrimitivesDomain.BOOLEAN)
				|| type.equals(PrimitivesDomain.BOOLEAN_OBJ)) {
			return GeneratorConstants.T24_TYPE_BOOLEAN;
		} else if (type.equals(PrimitivesDomain.DOUBLE)
				|| type.equals(PrimitivesDomain.DOUBLE_OBJ) 
				|| type.equals(PrimitivesDomain.DECIMAL)
				|| type.equals(PrimitivesDomain.FLOAT)
				|| type.equals(PrimitivesDomain.FLOAT_OBJ)) {
			return GeneratorConstants.T24_TYPE_NUMBER;
		} else if (type.equals(PrimitivesDomain.INTEGER)
				|| type.equals(PrimitivesDomain.INTEGER_OBJ)
				|| type.equals(PrimitivesDomain.LONG)
				|| type.equals(PrimitivesDomain.LONG_OBJ) 
				|| type.equals(PrimitivesDomain.SHORT)
				|| type.equals(PrimitivesDomain.SHORT_OBJ)) {
			return GeneratorConstants.T24_TYPE_INTEGER_NUMBER;
		} else if (type.equals(PrimitivesDomain.DATE)) {
			return GeneratorConstants.T24_TYPE_DATE;
		} else if (type.equals(PrimitivesDomain.DATE_TIME)) {
			return GeneratorConstants.T24_TYPE_TIMESTAMP;
		}  	
		return null;
	}

	/**
	 * Return a T24 annotation of an MDF class.
	 * @param mdfClass MDF class
	 * @param annotation Annotation name
	 * @return Annotation value
	 */
	private String getMdfClassAnnotation(MdfClass mdfClass, String annotation) {
		String annotationValue = null;
		MdfAnnotation t24Annotation = mdfClass.getAnnotation(GeneratorConstants.T24_ANNOTATION_NAMESPACE, GeneratorConstants.T24_ANNOTATION_NAME);
		if(t24Annotation != null) {
			MdfAnnotationProperty annotationProp = t24Annotation.getProperty(annotation);
			if(annotationProp != null) {
				annotationValue = annotationProp.getValue();
			}
		}
		// TODO: For AA only, above logic should already have found the annotation as specified in specs
		// Adding below logic to manually find AA.PROPERTY.CLASS
		if (annotationValue == null && GeneratorConstants.T24_ANNOTATION_AA_PROPERTY.equals(annotation) && mdfClass.getName().indexOf("__") == -1) {
			// Only for AA Module --> AA.ARR.* Classes
			String classModule = getMdfDomainAnnotation(mdfClass.getParentDomain(), 
														GeneratorConstants.DOMAIN_MODULE_ANNOTATION);
			if (classModule != null && 
					(classModule.equals(GeneratorConstants.AA_MODULE) || 
					 classModule.equals(GeneratorConstants.AB_MODULE) || 
					 classModule.equals(GeneratorConstants.AI_MODULE) || 
					 classModule.equals(GeneratorConstants.AP_MODULE))) {
				String classT24Name = getT24Name(mdfClass);
				if (classT24Name.startsWith(GeneratorConstants.AA_PROP_CLASS_PREFIX)) {
					// This should be the AA Property Class
					annotationValue = classT24Name.replace(GeneratorConstants.AA_PROP_CLASS_PREFIX, "");
				}
			}
		}
		return annotationValue;
	}
	
	/**
	 * Return a T24 annotation of an MDF property.
	 * @param mdfProperty MDF class
	 * @param annotation Annotation name
	 * @return Annotation value
	 */
	private String getMdfPropertyAnnotation(MdfProperty mdfProperty, String annotation) {
		MdfAnnotation t24Annotation = mdfProperty.getAnnotation(GeneratorConstants.T24_ANNOTATION_NAMESPACE, GeneratorConstants.T24_ANNOTATION_NAME);
		if(t24Annotation != null) {
			MdfAnnotationProperty annotationProp = t24Annotation.getProperty(annotation);
			if(annotationProp != null) {
				return annotationProp.getValue();
			}
		}
		return null;
	}
	
	/**
	 * Method to check if MdfClass is of AA Type
	 * @param mdfClass
	 * @return
	 */
	public static boolean isAAResource(MdfClass mdfClass) {
		// Check if it is not a AA Product Line
		if (GeneratorConstants.AA_PRODUCT_LINES.contains(mdfClass.getName())) {
			return true;
		} else {
			// See if this is AA.ARR from AA Module
			String classModule = IRISDomainMapper.
								getMdfDomainAnnotation(mdfClass.getParentDomain(), 
								GeneratorConstants.DOMAIN_MODULE_ANNOTATION);
			return (classModule != null && 
				   (classModule.equals(GeneratorConstants.AA_MODULE) 
						   || classModule.equals(GeneratorConstants.AI_MODULE)
						   || classModule.equals(GeneratorConstants.AB_MODULE)
						   || classModule.equals(GeneratorConstants.AP_MODULE)) && 
					IRISDomainMapper.getT24Name(mdfClass).startsWith(GeneratorConstants.AA_PROP_CLASS_PREFIX));
		}
		
	}	
	
	/**
	 * Return a T24 annotation of an MDF class.
	 * @param mdfDomain MDF Domain
	 * @param annotation Annotation name
	 * @return Annotation value
	 */
	private static String getMdfDomainAnnotation(MdfDomain mdfDomain, String annotation) {
		String annotationValue = null;
		// There might be no Domain received here but just in case
		if (mdfDomain != null) {
			MdfAnnotation t24Annotation = mdfDomain.getAnnotation(GeneratorConstants.T24_ANNOTATION_NAMESPACE, GeneratorConstants.T24_ANNOTATION_NAME);
			if(t24Annotation != null) {
				MdfAnnotationProperty annotationProp = t24Annotation.getProperty(annotation);
				if(annotationProp != null) {
					annotationValue = annotationProp.getValue();
				}
			}
		}
		return annotationValue;
	}
	
}

package com.odcgroup.t24.applicationimport;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;

import com.odcgroup.domain.annotations.MdfAnnotationsUtil;
import com.odcgroup.mdf.ext.AbstractAspect;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.t24.applicationimport.schema.ApplicationLevel;
import com.odcgroup.t24.applicationimport.schema.ApplicationType;
import com.odcgroup.t24.applicationimport.schema.FieldAlignment;
import com.odcgroup.t24.applicationimport.schema.InputBehaviour;
import com.odcgroup.translation.core.ITranslation;
import com.odcgroup.translation.core.ITranslationKind;
import com.odcgroup.translation.core.ITranslationManager;
import com.odcgroup.translation.core.TranslationCore;
import com.odcgroup.translation.core.TranslationException;

/**
 * Helper to set/get T24 Annotations on Domains.
 * 
 * Similar to the AAAAspect & Co. which extend AbstractAspect from MDF Core, but
 * deliberately here in DS instead of MDF Core, because these are not going to
 * be needed RT (in the currently planning architecture; contrary to TAP where
 * TSL needs them at RT).
 * 
 * @author Michael Vorburger
 */
public class T24Aspect extends AbstractAspect {

	// NOTE The following NS String is also in the xText DSL DomainValueConverterService and must be kept in sync
	public static final String T24_ANNOTATIONS_NAMESPACE_URI = "http://www.temenos.com/t24";
	
	// i stands for... let's say "stuff added by Introspection/Import" ;)
	public static final String ANNOTATION = "i";
	

	public static final String LOCAL_FIELD = "LocalFields";
	
	// ---
	// T24 Annotation actually really needed by DS
	
	public static final String DESCRIPTION = "description";
	public static void setDescription(MdfClass klass, String descriptionValue,IProject project) throws TranslationException {
		 ITranslationManager translationManger = TranslationCore.getTranslationManager(project);
		 ITranslation mdfTranslation = translationManger.getTranslation(klass);
		 mdfTranslation.setText(ITranslationKind.DESCRIPTION, Locale.ENGLISH, descriptionValue);
	}
	
	public static String getDescription(MdfClass klass,IProject project) throws TranslationException {
		 ITranslationManager translationManger = TranslationCore.getTranslationManager(project);
		 ITranslation mdfTranslation = translationManger.getTranslation(klass);
		 String description =mdfTranslation.getText(ITranslationKind.DESCRIPTION, Locale.ENGLISH);
		 return description ;
		
	}
	private static final String MODULE = "module";
	public static void setModule(MdfDomain domain, String moduleName) {
		setT24Annotation(domain, MODULE, moduleName);
	}
	public static String getModule(MdfDomain domain) {
		return getT24Annotation(domain, MODULE);
	}

	private static final String NAME = "t24Name";
	public static void setT24Name(MdfModelElement model, String t24Name) {
		setT24Annotation(model,NAME, t24Name);
	}
	public static String getT24Name(MdfModelElement model) {
		return getT24Annotation(model, NAME);
	}
	private static final String GENOPERATION = "genOperation";
	public static void setGenOperation(MdfModelElement model, String genOperationValue) {
		setT24Annotation(model,GENOPERATION, genOperationValue);
	}
	public static String getGenOperation(MdfModelElement model) {
		return getT24Annotation(model, GENOPERATION);
	}
	// ---
	// Application/MDF Class level annotations (currently) more for "doc"
	
	private static final String NONSTOP = "nonStop";
	public static void setNonStop(MdfClass klass, boolean nonStop) {
		// Intentional: only add annotation if true, for DSL brevity
		if (nonStop)
			setT24Annotation(klass, NONSTOP, Boolean.toString(nonStop));
	}
	public static boolean isNonStop(MdfClass klass) {
		// Intentional: it will be false if there is no such annotation
		return Boolean.parseBoolean(getT24Annotation(klass, NONSTOP));
	}
	
	private static final String LEVEL = "level";
	public static void setLevel(MdfClass klass, ApplicationLevel level) {
		setT24Annotation(klass, LEVEL, level.name());
	}
	public static ApplicationLevel getLevel(MdfClass klass) {
		return ApplicationLevel.valueOf(getT24Annotation(klass, LEVEL));
	}
	
	private static final String TYPE = "type";
	public static void setType(MdfClass klass, ApplicationType type) {
		setT24Annotation(klass, TYPE, type.name());
	}
	public static ApplicationType getType(MdfClass klass) {
		ApplicationType result = null;
		
		String annotation = getT24Annotation(klass, TYPE);
		
		// Let's be defensive
		if(annotation != null) {
			result = ApplicationType.valueOf(annotation);
		}
		
		return result;

	}
        
	//Local REF FIeld Annotation.
	private static final String LOCAL_REF_FIELD_ALLOWED = "localRefAllowed";

	public static boolean isLocalRefAllowed(MdfClass klass){
	    String localRefAllowed = getT24Annotation(klass, LOCAL_REF_FIELD_ALLOWED);
	    return Boolean.parseBoolean(localRefAllowed);
	}
	public static void setLocalRefAllowed(MdfClass klass, boolean localRefAllowed){
	    setT24Annotation(klass, LOCAL_REF_FIELD_ALLOWED, Boolean.toString(localRefAllowed));
	}
	// ---
	// Application Field/MDF Property level annotations (currently) more for "doc"
	
	private static final String ALIGNMENT = "align";
	public static void setAlignment(MdfProperty property, FieldAlignment align) {
		setT24Annotation(property, ALIGNMENT, align.name());
	}
	
	public static FieldAlignment getAlignment(MdfProperty property) {
		String alignement = getT24Annotation(property, ALIGNMENT);
		if (alignement != null) {
			return FieldAlignment.valueOf(alignement);
		}
		return null;
	}

	private static final String INPUT_BEHAVIOUR = "inputBehaviour";
	public static void setInputBehaviour(MdfProperty property, InputBehaviour ib) {
		setT24Annotation(property, INPUT_BEHAVIOUR, ib.name());
	}
	
	public static InputBehaviour getInputBehaviour(MdfProperty property) {
		String value = getT24Annotation(property, INPUT_BEHAVIOUR);
		return (value != null) ? InputBehaviour.valueOf(getT24Annotation(property, INPUT_BEHAVIOUR)) : null;
	}
	
	public static final String BUSINESS_TYPE = "businessType";
	public static void setBusinessType(MdfProperty property, String businessType) {
		setT24Annotation(property, BUSINESS_TYPE, businessType);
	}
	
	public static String getBusinessType(MdfProperty property) {
		return getT24Annotation(property, BUSINESS_TYPE);
	}
	
	private static final String CORE = "core";
	public static void setCore(MdfProperty property, Boolean value) {
		setT24Annotation(property, CORE, value.toString());
	}
	
	public static Boolean isCore(MdfProperty property) {
		String string = getT24Annotation(property, CORE);
		return (StringUtils.isBlank(string) ? false : Boolean.valueOf(string));
	}

	private static final String MANDATORY = "mandatory";
	public static void setMandatory(MdfProperty property, Boolean value) {
		setT24Annotation(property, MANDATORY, value.toString());
	}
	
	public static Boolean isMandatory(MdfProperty property) {
		String string = getT24Annotation(property, MANDATORY);
		return (StringUtils.isBlank(string) ? false : Boolean.valueOf(string));
	}
	
	private static final String MAX_LENGTH = "maxLength";
	public static void setMaxLength(MdfProperty property, Integer len) {
		setT24Annotation(property, MAX_LENGTH, len.toString());
	}
	public static Integer getMaxLength(MdfProperty property) {
		String maxLenght = getMaxLengthAsString(property);
		if(maxLenght !=null){
		   return Integer.valueOf(maxLenght);
		}
		return null;
	}

	public static String getMaxLengthAsString(MdfProperty property) {
		return getT24Annotation(property, MAX_LENGTH);
	}

	private static final String MULTILANG = "multiLanguage";
	public static void setMultiLanguage(MdfProperty property, boolean multiLanguage) {
		// Intentional: only add annotation if true, for DSL brevity
		if (multiLanguage)
			setT24Annotation(property, MULTILANG, Boolean.toString(multiLanguage));
	}
	public static boolean isMultiLanguage(MdfProperty property) {
		// Intentional: it will be false if there is no such annotation
		return Boolean.parseBoolean(getT24Annotation(property, MULTILANG));
	}

	private static final String SYS_NUMBER = "sysNumber";
	public static void setSysNumber(MdfProperty property, Double number) {
		setT24Annotation(property, SYS_NUMBER, number.toString());
	}
	public static Double getSysNumber(MdfProperty property) {
		String string = getSysNumberAsString(property);
		if (string != null)
			return Double.valueOf(string);
		else
			return null;
	}
	
	public static String getSysNumberAsString(MdfProperty property) {
		return getT24Annotation(property, SYS_NUMBER);
	}

	/* Annotations for Local Ref/Fields Definition */
	
	private static final String LOCAL_FIELDS_DEFINITION = "localFieldsDefinition";
	public static void setLocalFields(MdfDomain domain, boolean value) {
		setT24Annotation(domain, LOCAL_FIELDS_DEFINITION, ""+value, LOCAL_FIELD);
	}
	public static boolean getLocalRefDefinition(MdfDomain domain) {
		String string = getT24Annotation(domain, LOCAL_FIELDS_DEFINITION, LOCAL_FIELD);
		if (string != null)
			return Boolean.valueOf(string);
		else
			return false;
	}
	
	private static final String LOCAL_REF_APPLICATION = "localRefApplications";
	public static void setLocalRefApplications(MdfDomain domain, boolean value) {
		setT24Annotation(domain, LOCAL_REF_APPLICATION, ""+value, LOCAL_FIELD);
	}
	public static boolean getLocalRefApplications(MdfDomain domain) {
		String string = getT24Annotation(domain, LOCAL_REF_APPLICATION, LOCAL_FIELD);
		if (string != null)
			return Boolean.valueOf(string);
		else
			return false;
	}
	
	/**
	 * Annotation for ProductLines
	 */
	private static final String PRODUCT_LINES = "Products";
	private static final String PRODUCT_NAME = "AAProducts";
	public static void setProductLines(MdfDomain domain, boolean value) {
		setT24Annotation(domain, PRODUCT_NAME, ""+value, PRODUCT_LINES);
	}
	public static boolean getProductLines(MdfDomain domain) {
		String string = getT24Annotation(domain, PRODUCT_NAME, PRODUCT_LINES);
		if (string != null)
			return Boolean.valueOf(string);
		else
			return false;
	}
	
	private static final String MAX_CHARS = "maxChars";
	public static void setMaxChars(MdfProperty property, int value) {
		setT24Annotation(property, MAX_CHARS, ""+value, LOCAL_FIELD);
	}
	public static int getMaxChars(MdfProperty property) {
		String string = getT24Annotation(property, MAX_CHARS, LOCAL_FIELD);
		if (string != null)
			return Integer.valueOf(string);
		else
			return -1;
	}
	
	private static final String VIRTUAL_TABLE = "virtualTable";
	public static void setVirtualTable(MdfProperty property, String value) {
		setT24Annotation(property, VIRTUAL_TABLE, ""+value, LOCAL_FIELD);
	}
	public static String getVirtualTable(MdfProperty property) {
		String string = getT24Annotation(property, VIRTUAL_TABLE, LOCAL_FIELD);
		if (string != null)
			return string;
		else
			return null;
	}
	
	private static final String MIN_CHARS = "minChars";
	public static void setMinChars(MdfProperty property, int value) {
		setT24Annotation(property, MIN_CHARS, ""+value, LOCAL_FIELD);
	}
	public static int getMinChars(MdfProperty property) {
		String string = getT24Annotation(property, MIN_CHARS, LOCAL_FIELD);
		if (string != null)
			return Integer.valueOf(string);
		else
			return -1;
	}
	
	private static final String OVERRIDE_POSSIBLE = "overridePossible";
	public static void setOverridePossible(MdfProperty property, boolean value) {
		setT24Annotation(property, OVERRIDE_POSSIBLE, ""+value, LOCAL_FIELD);
	}
	public static boolean getOverridePossible(MdfProperty property) {
		String string = getT24Annotation(property, OVERRIDE_POSSIBLE, LOCAL_FIELD);
		if (string != null)
			return Boolean.valueOf(string);
		else
			return false;
	}
	
	private static final String DEFAULT_POSSIBLE = "defaultPossible";
	public static void setDefaultPossible(MdfProperty property, boolean value) {
		setT24Annotation(property, DEFAULT_POSSIBLE, ""+value, LOCAL_FIELD);
	}
	public static boolean getDefaultPossible(MdfProperty property) {
		String string = getT24Annotation(property, DEFAULT_POSSIBLE, LOCAL_FIELD);
		if (string != null)
			return Boolean.valueOf(string);
		else
			return false;
	}
	
	private static final String NO_INPUT_CHANGE = "noInputChange";
	public static void setNoInputChange(MdfProperty property, String value) {
		setT24Annotation(property, NO_INPUT_CHANGE, value, LOCAL_FIELD);
	}
	public static String getNoInputChange(MdfProperty property) {
		return getT24Annotation(property, NO_INPUT_CHANGE, LOCAL_FIELD);
	}
	
	private static final String APPLICATION_ENRICH = "applicationEnrich";
	public static void setApplicationEnrich(MdfProperty property, String value) {
		setT24Annotation(property, APPLICATION_ENRICH, value, LOCAL_FIELD);
	}
	public static String getApplicationEnrich(MdfProperty property) {
		return getT24Annotation(property, APPLICATION_ENRICH, LOCAL_FIELD);
	}
	
	public static void removeAnnotation(MdfDomain domain, String namespace, String annotationName) {
		MdfAnnotationsUtil.removeAnnotation(domain, namespace, annotationName);
	}
	
	// ---
	
	private static void setT24Annotation(MdfModelElement model, String name, String value) {
		if (value != null)
			MdfAnnotationsUtil.setAnnotationProperty(model, T24_ANNOTATIONS_NAMESPACE_URI, ANNOTATION, name, value, false);
	}
	
	private static void setT24Annotation(MdfModelElement model, String name, String value, String annotName) {
		if (value != null)
			MdfAnnotationsUtil.setAnnotationProperty(model, T24_ANNOTATIONS_NAMESPACE_URI, annotName, name, value, false);
	}

	private static String getT24Annotation(MdfModelElement model, String name) {
		assertNotAProxy(model);
		return getAnnotationProperty(model, T24_ANNOTATIONS_NAMESPACE_URI, ANNOTATION, name);
	}
	
	private static String getT24Annotation(MdfModelElement model, String name, String annotName) {
		assertNotAProxy(model);
		return getAnnotationProperty(model, T24_ANNOTATIONS_NAMESPACE_URI, annotName, name);
	}
	
	private static void assertNotAProxy(MdfModelElement model) {
//		MdfModelElementImpl impl = (MdfModelElementImpl) model;
//		if (impl.eIsProxy())
//			throw new IllegalArgumentException("MdfModelElement is still an EMF Proxy, cannot getAnnotationProperty: " + model.toString());
	}

	// Added for DS-6613 - T24 Business Types import : BEGIN
	private static final String BUSINESS_TYPES = "businessTypes";
	public static void setBusinessTypes(MdfDomain domain, boolean value) {
		setT24Annotation(domain, BUSINESS_TYPES, "" + value, ANNOTATION);
	}
	// Added for DS-6613 - T24 Business Types import : END
	
	// Added for DS-6835 - allowed-functions : BEGIN
	private static final String ALLOWED_FUNCTIONS = "allowedFunctions";
	public static void setAllowedFunctions(MdfClass klass, String allowedFunctions) {
		setT24Annotation(klass, ALLOWED_FUNCTIONS, allowedFunctions);
	}
	
	public static String getAllowedFunctions(MdfClass klass) {
		return getT24Annotation(klass, ALLOWED_FUNCTIONS);
	}
	// Added for DS-6835 - allowed-functions : END
	
	// Added for DS-6836 - additional-info : BEGIN
	private static final String ADDITIONAL_INFO = "additionalInfo";
	public static void setAdditionalInfo(MdfClass klass, String additionalInfo) {
		setT24Annotation(klass, ADDITIONAL_INFO, additionalInfo);
	}
	
	public static String getAdditionalInfo(MdfClass klass) {
		return getT24Annotation(klass, ADDITIONAL_INFO);
	}
	// Added for DS-6836 - additional-info : END
	
	private static final String ONCHANGE_BEHAVIOUR = "onchangeBehaviour";
	public static void setOnchangeBehaviour(MdfProperty property, String onchangeBehaviour) {
		setT24Annotation(property, ONCHANGE_BEHAVIOUR, onchangeBehaviour);
	}

	public static String getOnchangeBehaviour(MdfProperty property) {
		return getT24Annotation(property, ONCHANGE_BEHAVIOUR);
	}

	private static final String IS_TEXT_AREA = "isTextarea";
	public static void setTextArea(MdfProperty property, boolean istextArea) {
		setT24Annotation(property, IS_TEXT_AREA, Boolean.toString(istextArea));
	}

	public static boolean getTextArea(MdfProperty property) {
		String isTextArea = getT24Annotation(property, IS_TEXT_AREA);
		if (isTextArea != null) {
			return Boolean.parseBoolean(isTextArea);
		}
		return false;
	}

	private static final String IS_IMAGE = "is-image";
	public static void setImage(MdfProperty property, boolean isImage) {
		setT24Annotation(property, IS_IMAGE, Boolean.toString(isImage));
	}

	public static boolean getImage(MdfProperty property) {
		String isImage = getT24Annotation(property, IS_IMAGE);
		if (isImage != null) {
			return Boolean.parseBoolean(isImage);
		}
		return false;
	}

	private static final String MASK = "mask";
	public static void setMask(MdfProperty property, String mask) {
		setT24Annotation(property, MASK, mask);
	}

	public static String getMask(MdfProperty property) {
		return getT24Annotation(property, MASK);
	}
	
	private static final String TYPE_MODIFIERS = "typeModifiers";
	public static void setTypeModifiers(MdfProperty property, String mask) {
		setT24Annotation(property, TYPE_MODIFIERS, mask);
	}

	public static String getTypeModifiers(MdfProperty property) {
		return getT24Annotation(property, TYPE_MODIFIERS);
	}
	
	private static final String MV_SV_EXAPNSION_ACCESS = "mvSvExpansionAccess";
	public static void setMvSvExpansionAccess(MdfProperty property, String mask) {
		setT24Annotation(property, MV_SV_EXAPNSION_ACCESS, mask);
	}

	public static String getMvSvExpansionAccess(MdfProperty property) {
		return getT24Annotation(property, MV_SV_EXAPNSION_ACCESS);
	}
}

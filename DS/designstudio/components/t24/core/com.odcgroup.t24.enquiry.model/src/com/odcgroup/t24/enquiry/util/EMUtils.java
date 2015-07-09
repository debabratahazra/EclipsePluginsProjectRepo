package com.odcgroup.t24.enquiry.util;

import java.util.List;

import org.eclipse.xtext.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.t24.enquiry.enquiry.Enquiry;
import com.odcgroup.workbench.generation.cartridge.ng.ModelLoader;


public class EMUtils {
	private static Logger LOGGER = LoggerFactory.getLogger(EMUtils.class);

	public static final String T24_ANNOTATION_NAMESPACE = "http://www.temenos.com/t24";
	public static final String T24_ANNOTATION_NAME = "i";
	public static final String T24_ANNOTATION_MODULE = "module";
	public static final String T24_ANNOTATION_T24TYPE = "t24Type";
	public static final String T24_ANNOTATION_AA_PRODUCT_GROUP = "aaProductGroup";
	public static final String T24_ANNOTATION_AA_PROPERTY = "aaProperty";

	/**
	 * Convert a name to camel-case format
	 * e.g. ACCOUNT.NUMBER to AccountNumber
	 * @param name name
	 * @return converted name
	 */
	
	public static String camelCaseName(String name) 
	{
		if(name == null || name.equals("")) {
			return "";
		} else {
			StringBuilder converted = new StringBuilder();
			// Add an 'N' prefix to fields that start with a number (eg DRAWINGS has field called 202.BK.TO.BK)
			// otherwise the generated odata response contains invalid xml like <d:202BkToBk></d:202BkToBk>
			if (Character.isDigit(name.charAt(0))) {
				name = 'N' + name;
			}
			name = name.replace( "@", "" );
			name = name.replace( "%", "" );
			/*
			 * Replacing the $ and & by a . makes such a enquiry :
			 * %DRAWINGS$NAU to generate such a resource : enqDrawingsNau
			 * Which is quite nice. 
			 * Also, $ Sign and & was crashing the rim.
			 */
			name = name.replace( "$", "." );
			name = name.replace( "&", "." );
			String[] fieldWords = name.split("(\\.|-|_)");
			
			if(fieldWords.length == 0) {
				converted.append( camelCaseWord( name ) );
			}
			else {
				for( int i = 0; i < fieldWords.length; i++ ) {
					converted.append( camelCaseWord( fieldWords[i] ) );
				}
			}	
			return converted.toString();
		}
	}
	
	private static String camelCaseWord( String word )
	{
	    if(word == null || word.equals("")) {
            return "";
        }
	    
		//Only camel case if all letters are either upper case or only lower case
		String checkWord = word.replaceAll("\\d*", "").replaceAll(",", "");	//Remove numbers and commas
		if(!checkWord.matches("\\p{javaUpperCase}*") && !checkWord.matches("\\p{javaLowerCase}*")) {
			return word;		
		}
		
		StringBuilder camelCased = new StringBuilder();
		String lowerWord = word.toLowerCase();
		
		// If word has an "," it could be a version name so change it to a "_" and camel case around it
		String[] fieldWords = lowerWord.split("(,)");		
		if ( fieldWords.length == 0 ) {
			camelCased.append( upperInitialCharacter( lowerWord ) );
		}
		else {
			for( int i = 0; i < fieldWords.length; i++ ) {
				if ( i < fieldWords.length - 1 ) {
					camelCased.append( upperInitialCharacter( fieldWords[i] + "_" ) );
				}
				else {
					camelCased.append( upperInitialCharacter( fieldWords[i] ) );
				}
			}
		}		
		return camelCased.toString();
	}

	/**
	 * Turns the first character of a string in to an uppercase character 
	 * @param source The source string
	 * @return String Resultant string
	 */
	public static String upperInitialCharacter(String source) 
	{
    	final StringBuilder result = new StringBuilder(source.length());
    	if (!source.isEmpty()) {
    		result.append(Character.toUpperCase(source.charAt(0))).append(source.substring(1));
    	}
    	return result.toString();
	}
	
	/**
	 * Returns the named item from the list, looking recursively through any sub properties if required.
	 * @param properties
	 * @param name
	 * @return
	 */
	public static EMProperty getPropertyByName(List<EMProperty> properties, String name) {
		for (EMProperty property : properties) {
			if (property.getName().equals(name)) {
				return property;
			} else if (!property.getSubProperties().isEmpty()) {
				EMProperty subProperty = EMUtils.getPropertyByName(property.getSubProperties(), name);
				if (subProperty != null) {
					return subProperty;
				}
			}
		}
		return null;
	}

	
	/**
	 * Returns the named item from the list, looking recursively through any sub properties if required.
	 * @param properties
	 * @param t24name
	 * @return
	 */
	public static EMProperty getPropertyByT24Name(List<EMProperty> properties, String t24name) {
		for (EMProperty property : properties) {
			if (property.getT24Name().equals(t24name)) {
				return property;
			} else if (!property.getSubProperties().isEmpty()) {
				EMProperty subProperty = EMUtils.getPropertyByT24Name(property.getSubProperties(), t24name);
				if (subProperty != null) {
					return subProperty;
				}
			}
		}
		return null;
	}

	public static MdfClass getAppByEnquiry(ModelLoader loader, Enquiry enquiry) {
		String fullName = enquiry.getFileName();
		if (fullName == null) {
			LOGGER.error("Not a valid Enquiry model, change to an assert once DS-7344 is fixed");
			return null;
		}
		String domainName = fullName.split(":")[1].substring(1);
		String appName = fullName;
		if (fullName.contains(":")) {
			appName = appName.split(":")[2];
			appName = appName.split("#")[0];
			appName = appName.replace(".", "_");
		}
		if (appName.contains("$")) {
			appName = appName.split("\\$")[0];
		}
		
		QualifiedName qname = QualifiedName.create(domainName, appName);
		
		MdfClass mdfClass = loader.getNamedEObject(enquiry, qname, MdfPackage.Literals.MDF_CLASS);
		if (mdfClass == null) {
			// Nothing found, return null.
			LOGGER.warn("Underlying application " + appName + " requested, not found in project");
		}
		return mdfClass;
	}

	/**
	 * Return a T24 annotation of an MDF domain.
	 * @param mdfDomain MDF domain
	 * @param annotation Annotation name
	 * @return Annotation value
	 */
	public static String getMdfDomainAnnotation(MdfDomain mdfDomain, String annotation) {
		MdfAnnotation t24Annotation = mdfDomain.getAnnotation(T24_ANNOTATION_NAMESPACE, T24_ANNOTATION_NAME);
		if(t24Annotation != null) {
			MdfAnnotationProperty annotationProp = t24Annotation.getProperty(annotation);
			if(annotationProp != null) {
				return annotationProp.getValue();
			}
		}
		return null;
	}

	/**
	 * Return a T24 annotation of an MDF class.
	 * @param mdfClass MDF class
	 * @param annotation Annotation name
	 * @return Annotation value
	 */
	public static String getMdfClassAnnotation(MdfClass mdfClass, String annotation) {
		MdfAnnotation t24Annotation = mdfClass.getAnnotation(T24_ANNOTATION_NAMESPACE, T24_ANNOTATION_NAME);
		if(t24Annotation != null) {
			MdfAnnotationProperty annotationProp = t24Annotation.getProperty(annotation);
			if(annotationProp != null) {
				return annotationProp.getValue();
			}
		}
		return null;
	}

}

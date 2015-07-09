package com.odcgroup.t24.mdf.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.xtext.validation.ValidationMessageAcceptor;

import com.odcgroup.domain.validation.DomainValidator;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.t24.applicationimport.T24Aspect;

/**
 * This class implements specific constraints for T24 Domain elements
 */
public class T24DomainJavaValidator implements DomainValidator {
	
	private static final String SOURCE_LEVEL = "5.0";
    private static final String COMPLIANCE_LEVEL = "5.0";

	private static final String NAMESPACE_URI = "http://www.odcgroup.com/mdf/translation";
	
	private boolean isDomainRelatedToLocalRef(MdfDomain domain) {
		return T24Aspect.getLocalRefApplications(domain)  || T24Aspect.getLocalRefDefinition(domain);
	}
    
	private void validateFieldName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element, MdfDomain domain) {
		String name = element.getName();
		if (StringUtils.isNotBlank(name)) {
			if(!isDomainRelatedToLocalRef(domain)) {
				if(T24Aspect.getModule(domain) == null){
					if (!JavaConventions.validateFieldName(name, SOURCE_LEVEL, COMPLIANCE_LEVEL).isOK()) {
						messageAcceptor.acceptError("'" + name + "' is not a valid name", 
								(EObject)element, 
								MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME, 
								ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
								null);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
    private void checkTranslationTextDoesNotContainsCRLF(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
		List<MdfAnnotation> annotations = element.getAnnotations(NAMESPACE_URI);
    	for (MdfAnnotation annotation : annotations) {
			List<MdfAnnotationProperty> properties = (List<MdfAnnotationProperty>)annotation.getProperties();
    		for (MdfAnnotationProperty property : properties) {
    			String text = property.getValue();
				if (text!=null && text.contains("\r\n")) {
					text = text.replaceAll("\n", "");
					text = text.replaceAll("\r", "");
					messageAcceptor.acceptError("Translation ( " + text + ") text cannot contain a CARRIAGE RETURN \"\\r\" or LINE FEED \"\\n\"", 
							(EObject)property, 
							MdfPackage.Literals.MDF_ANNOTATION_PROPERTY__VALUE, 
							ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
							null);
				}
    		}
    	}
	}

	@Override
	public void checkFastMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
		checkTranslationTextDoesNotContainsCRLF(messageAcceptor, element);
	}
	
	@Override
	public void checkNormalMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

	@Override
	public void checkExpensiveMdfElement(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
	}

    @Override
    public void validateTypeName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
    	String name = element.getName();
    	if (StringUtils.isNotBlank(name)) {
			if (!JavaConventions.validateJavaTypeName(name, SOURCE_LEVEL, COMPLIANCE_LEVEL).isOK()) {	
				messageAcceptor.acceptError("'" + name + "' is not a valid name",
						(EObject) element,
						MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME,
						ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
						null);
			}
    	}
    }
    
	@Override
	public void validateFieldName(ValidationMessageAcceptor messageAcceptor, MdfModelElement element) {
		// name validation
		if (element instanceof MdfAttribute) {
			MdfAttribute attribute = (MdfAttribute) element;
			validateFieldName(messageAcceptor, attribute, attribute.getParentClass().getParentDomain());
		} else if (element instanceof MdfAssociation) {
			MdfAssociation association = (MdfAssociation) element;
			validateFieldName(messageAcceptor, association, association.getParentClass().getParentDomain());
		} else if (element instanceof MdfEnumValue) {
			MdfEnumValue enumValue = (MdfEnumValue) element;
			validateFieldName(messageAcceptor, enumValue, enumValue.getParentEnumeration().getParentDomain());
		} else if (element instanceof MdfEnumeration) {
			MdfEnumeration enumeration = (MdfEnumeration) element;
			validateFieldName(messageAcceptor, enumeration, enumeration.getParentDomain());
		} else {
	    	String name = element.getName();
			if (StringUtils.isNotBlank(name)) {
		    	if (!JavaConventions.validateFieldName(name, SOURCE_LEVEL, COMPLIANCE_LEVEL).isOK()) {
		    		messageAcceptor.acceptError("'" + name + "' is not a valid name",  
							(EObject) element,
		    				MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME,
		    				ValidationMessageAcceptor.INSIGNIFICANT_INDEX, 
		    				null);
		    	}
	        }

		}
	}
		   
	@Override
	public boolean leadsToCyclicDependency(MdfEntity entity, MdfDomain currentDomain) {
		return false;
	}

}

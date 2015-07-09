package com.odcgroup.domain.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.core.JavaModelStatus;
import org.eclipse.jdt.internal.core.util.Messages;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.odcgroup.mdf.ecore.MdfEntityImpl;
import com.odcgroup.mdf.ecore.MdfPackage;
import com.odcgroup.mdf.ecore.PrimitivesDomain;
import com.odcgroup.mdf.ecore.util.PrimitiveTypeValidator;
import com.odcgroup.mdf.ext.java.JavaAspect;
import com.odcgroup.mdf.metamodel.MdfAnnotation;
import com.odcgroup.mdf.metamodel.MdfAnnotationProperty;
import com.odcgroup.mdf.metamodel.MdfAssociation;
import com.odcgroup.mdf.metamodel.MdfAttribute;
import com.odcgroup.mdf.metamodel.MdfBusinessType;
import com.odcgroup.mdf.metamodel.MdfClass;
import com.odcgroup.mdf.metamodel.MdfConstants;
import com.odcgroup.mdf.metamodel.MdfDataset;
import com.odcgroup.mdf.metamodel.MdfDatasetProperty;
import com.odcgroup.mdf.metamodel.MdfDomain;
import com.odcgroup.mdf.metamodel.MdfEntity;
import com.odcgroup.mdf.metamodel.MdfEnumValue;
import com.odcgroup.mdf.metamodel.MdfEnumeration;
import com.odcgroup.mdf.metamodel.MdfModelElement;
import com.odcgroup.mdf.metamodel.MdfPrimitive;
import com.odcgroup.mdf.metamodel.MdfProperty;
import com.odcgroup.mdf.metamodel.MdfReverseAssociation;
import com.odcgroup.otf.utils.inet.MalformedUriException;

@SuppressWarnings("restriction")
public class DomainJavaValidator extends AbstractDomainJavaValidator {
	private static final Logger logger = LoggerFactory.getLogger(DomainJavaValidator.class);

	/** Extension point */
	private static final String DOMAIN_VALIDATION = "com.odcgroup.mdf.validation.domainvalidation";
	
    private DomainValidator delegate = new DefaultDomainValidator(); 
    
    @Inject
    private ResourceDescriptionsProvider resourceDescriptionsProvider;
    
	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;
    
	@Inject
	private IResourceDescriptions resourceDescriptions;
	
	public DomainJavaValidator() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry == null) {
			// This will happen in the case of the new style @RunWith(XtextRunner.class) tests run standalone
			logger.warn("Running in 'standalone' mode (@RunWith(XtextRunner.class) test, probably?); cannot consult extension points, so ignoring custom DomainValidator");
			return;
		}
		IExtensionPoint point = registry.getExtensionPoint(DOMAIN_VALIDATION);
		if (point == null) {
			// This could happen e.g. if running as Plug-In Test in OSGi mode but with a required plug-in not open in the workspace
			logger.warn("Running in OSGi mode, but found no use of Extension Point " + DOMAIN_VALIDATION + " , so ignoring custom DomainValidator");
			return;
		}
		IExtension[] extensions = point.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] elements = extensions[i].getConfigurationElements();
			for (int kx = 0; kx < elements.length; kx++) {
				IConfigurationElement configElement = elements[kx];
				try {
					DomainValidationProvider provider = 
							(DomainValidationProvider) configElement.createExecutableExtension("class");
					delegate = provider.getDomainValidator();
					return;
				} catch (CoreException ex) {
					ex.printStackTrace();
					logger.error("DomainJavaValidator() constructor failed", ex);
				}
			}
		}
    }
    
    private boolean isValidValue(MdfPrimitive type, String value) {
        if (type instanceof MdfEnumeration) {
            MdfEnumeration e = (MdfEnumeration) type;
            return (e.getValue(value) != null);
        } else if (type instanceof MdfBusinessType) {
        	MdfBusinessType bType = (MdfBusinessType) type;
        	if (bType.getType() != null) {
        		return PrimitiveTypeValidator.isValidValue(bType.getType(), value);
        	} else {
            	return false;        		
        	}
        } else {
            return PrimitiveTypeValidator.isValidValue(type, value);
        }
    }
    
	private boolean isValidDefaultValue(MdfAttribute attr) {
        String dflt = attr.getDefault();
        MdfPrimitive type = (MdfPrimitive) attr.getType();

        if (attr.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
            return isValidValue(type, dflt);
        } else {
            StringTokenizer token = new StringTokenizer(dflt, ",");
            while (token.hasMoreTokens()) {
                if (!isValidValue(type, token.nextToken().trim())) {
                    return false;
                }
            }
            return true;
        }
    }
	
    private boolean isHierarchyValid(MdfClass klass) {
        return isHierarchyValid(klass, new HashSet<MdfClass>());
    }

    private boolean isHierarchyValid(MdfClass klass, Set<MdfClass> parents) {
        if (klass == null) return true;
        if (MdfEntityImpl.isProxy(klass)) return false;

        if (parents.contains(klass)) {
            return false;
        } else {
            parents.add(klass);
            return isHierarchyValid(klass.getBaseClass(), parents);
        }
    }
    
    private void validateType(MdfEntity type, MdfAssociation assoc) {
    	if (type == null || !(type instanceof MdfClass)) {
            error(assoc.getType().getQualifiedName() + " is not a valid association type", MdfPackage.Literals.MDF_ASSOCIATION__TYPE);
            return;
        } 
    	
    	if (assoc.getContainment() == MdfConstants.CONTAINMENT_BYREFERENCE) {        	
           	if (! MdfEntityImpl.isProxy(type) && ((MdfClass) type).getAttributesRef().isEmpty()) {
                error(type.getQualifiedName()
                		+ " is not a valid type for this association. "
                        + "Either the type must be referenceable or the association containment must be 'By value'",
                        MdfPackage.Literals.MDF_ASSOCIATION__TYPE);
                return;
            }

           	if (delegate.leadsToCyclicDependency((MdfClass) type, assoc.getParentClass().getParentDomain())) {
                error(
                        "Association type \'"+type.getQualifiedName()+"\' results in " 
                        + "cyclic reference with \'"
                        + ((MdfClass) type).getParentDomain().getQualifiedName()+"\' domain",
                        MdfPackage.Literals.MDF_ASSOCIATION__TYPE);
           	}
        }
    }
    
    private String getAlternateName(MdfEntity entity) {
		StringBuilder alternateName = new StringBuilder();
		String myPackage = JavaAspect.getPackage(entity.getParentDomain());
		if (StringUtils.isNotBlank(myPackage)) {
			QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName((EObject)entity);
			if (qualifiedName != null) {
				int count = qualifiedName.getSegmentCount();
				alternateName.append(myPackage);
				alternateName.append(".");
				for (int sx = 1; sx < count; sx++) {
					if (sx > 1)
						alternateName.append(".");
					alternateName.append(qualifiedName.getSegment(sx));
				}
			}
		}
		return alternateName.toString();
    }
    
    // TODO make a call to DomainRepository: (see DS-7248)
    private List<String> getAllAlternateNames() {
    	List<String> result = new ArrayList<String>();
		for (IResourceDescription resourceDescription : resourceDescriptions.getAllResourceDescriptions()) {
			for (IEObjectDescription eobjectDescription : resourceDescription.getExportedObjects()) {
				String name = eobjectDescription.getUserData("ALTERNATE_NAME");
				if (name != null) result.add(name);
			}
		}
		return result;
    }
    
    @Check(CheckType.NORMAL) 
    public void checkDomainDuplicate(MdfDomain domain) {
    	
    	EObject eDomain = (EObject)domain;
    	QualifiedName domainQN = qualifiedNameProvider.getFullyQualifiedName(eDomain); 
        IResourceDescriptions resourceDescriptions = resourceDescriptionsProvider.getResourceDescriptions(eDomain.eResource());
        
        Iterable<IEObjectDescription> objects = resourceDescriptions.getExportedObjects(MdfPackage.Literals.MDF_DOMAIN, domainQN, true);
        if (IterableExtensions.size(objects) > 1) {
        	error("A domain with the same name already exists", MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
        }
    }
	
	@Check
	public void checkModelElementHasValidName(MdfModelElement element) {
		String name = element.getName();
		if (name == "")
			System.out.println();
		if (StringUtils.isBlank(name)) {
			// This validation can be avoided if the grammar is modified to set name as mandatory
			error("The name must be specified", MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
		} else if (JavaKeywordChecker.getInstance().isKeyword(name)) {
            error("'" + name + "' is a reserved Java keyword", MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
		}
	}   
	
	@Check
	// This validation can be avoided if the grammar is modifier to set name as mandatory
	public void checkModelElementHasValidName(MdfAnnotationProperty annotationProperty) {
		String name = annotationProperty.getName();
		if (StringUtils.isBlank(name)) {
			error("The name must be specified", MdfPackage.Literals.MDF_ANNOTATION_PROPERTY__NAME);
		}
	}  
	
    @Check
    public void checkAnnotation(MdfAnnotation annotation) {
    	
    	// verify name
    	// This validation can be avoided if the grammar is modified to set name as mandatory
		String name = annotation.getName();
		if (StringUtils.isBlank(name)) {
			error("The name must be specified", MdfPackage.Literals.MDF_ANNOTATION__NAME);
		}

		//verify namespace
		// This validation can be avoided if the grammar is modified to set namespace as mandatory
    	String namespace = annotation.getNamespace();
    	if (StringUtils.isBlank(namespace)) {
            error("Annotation namespace must be specified", MdfPackage.Literals.MDF_ANNOTATION__NAMESPACE);
            return;
        }

    	// This validation could be avoided if the grammar is modified 
        try {
            com.odcgroup.otf.utils.inet.URI.parse(namespace);
        } catch (MalformedUriException e) {
            error("'" + namespace + "' is not a valid namespace", MdfPackage.Literals.MDF_ANNOTATION__NAMESPACE);
        }

    }


	@Check
	public void checkDomain(MdfDomain domain) {
		
		delegate.validateTypeName(getMessageAcceptor(), domain);
		
		String myPackage = JavaAspect.getPackage(domain);
		if (StringUtils.isBlank(myPackage)) {
            	error("The package is required", MdfPackage.Literals.MDF_MODEL_ELEMENT__ANNOTATIONS);
            	return;
		}
		
		IStatus status = validatePackageName(myPackage);
		if (!status.isOK()) {
			error(status.getMessage(), MdfPackage.Literals.MDF_MODEL_ELEMENT__ANNOTATIONS);
		}

        String namespace = domain.getNamespace();
        if (StringUtils.isBlank(namespace)) {
            error("A domain namespace must be specified", MdfPackage.Literals.MDF_DOMAIN__PRIMITIVES);
            return;
        }

        try {
            com.odcgroup.otf.utils.inet.URI.parse(namespace);
        } catch (MalformedUriException e) {
            error("'" + namespace + "' is not a valid namespace", MdfPackage.Literals.MDF_DOMAIN__PRIMITIVES);
        }
	}
    
	/**
	 * This was originally copy/pasted from
	 * org.eclipse.jdt.core.JavaConventions, but as that uses IWorkspace, it
	 * wont work in standalone tests, so rewritten here
	 */
	private IStatus validatePackageName(String name) {
		final char DOT = '.';
		if (name == null) {
			return new Status(IStatus.ERROR, JavaCore.PLUGIN_ID, -1, Messages.convention_package_nullName, null);
		}
		int length;
		if ((length = name.length()) == 0) {
			return new Status(IStatus.ERROR, JavaCore.PLUGIN_ID, -1, Messages.convention_package_emptyName, null);
		}
		if (name.charAt(0) == DOT || name.charAt(length-1) == DOT) {
			return new Status(IStatus.ERROR, JavaCore.PLUGIN_ID, -1, Messages.convention_package_dotName, null);
		}
		if (CharOperation.isWhitespace(name.charAt(0)) || CharOperation.isWhitespace(name.charAt(name.length() - 1))) {
			return new Status(IStatus.ERROR, JavaCore.PLUGIN_ID, -1, Messages.convention_package_nameWithBlanks, null);
		}
		int dot = 0;
		while (dot != -1 && dot < length-1) {
			if ((dot = name.indexOf(DOT, dot+1)) != -1 && dot < length-1 && name.charAt(dot+1) == DOT) {
				return new Status(IStatus.ERROR, JavaCore.PLUGIN_ID, -1, Messages.convention_package_consecutiveDotsName, null);
				}
		}
		return JavaModelStatus.VERIFIED_OK;
	}

	//@Check(CheckType.EXPENSIVE))
	public void checkEntityNameIsUniqueAcrossModels(MdfEntity entity) {
		String alternateName = getAlternateName(entity);
		if (StringUtils.isNotBlank(alternateName)) {
			int count = 0;
			for (String name : getAllAlternateNames()) {
				if (alternateName.equals(name)) {
					if ( ++count > 1 ) {
						error("An entity with the same name is already existing in another domain with same package. Please choose another name",
								MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
						break;
					}
				}
			}
		}
	}
	
	@Check
	public void checkClass(MdfClass klass) {

		delegate.validateTypeName(getMessageAcceptor(), klass);

        MdfClass baseClass = klass.getBaseClass();
        if (baseClass != null) {
            if (!MdfEntityImpl.isProxy(baseClass)) {
              if (!isHierarchyValid(klass)) {
                error("Class hierarchy for class "
                        + klass.getQualifiedName() + " is invalid",
                        MdfPackage.Literals.MDF_CLASS__BASE_CLASS);
              } else if (delegate.leadsToCyclicDependency(baseClass, klass.getParentDomain())) {
            	error("Superclass \'"+baseClass.getQualifiedName() 
            			+ "\' results in cyclic reference with \'" 
            			+ baseClass.getParentDomain().getQualifiedName()+"\' domain", 
            			MdfPackage.Literals.MDF_CLASS__BASE_CLASS);
              }
            }
        }

	}
	
	@Check(CheckType.FAST)
	public void checkDuplicateProperties(MdfClass klass) {
        Map<String, Integer> duplicates = new HashMap<String, Integer>();
        @SuppressWarnings("unchecked")
		List<MdfProperty> properties = klass.getProperties();
        for (MdfProperty property : properties) {
        	String key = property.getName();
        	Integer n = duplicates.get(key);
        	n = (n == null) ? 1 : ++n;
        	duplicates.put(key, n);
        }
        for (MdfProperty property : properties) {
        	String key = property.getName();
        	if (duplicates.get(key) > 1) {
        		error("Duplicate attribute "+key+", please specify another name", 
        				(EObject)property,
                		MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
        	}
        }
	}


	@Check
	public void checkBusinessTypeHasValidBaseType(MdfBusinessType type) {
		
		if (type.getType() == null) {
			error("Base type must be specified", MdfPackage.Literals.MDF_BUSINESS_TYPE__TYPE);
			return;
		}

// useless, the setter already raised an exception BusinessTypeImpl#setType
//		if (!(type instanceof MdfPrimitive) || MdfEntityImpl.isProxy(type)) {
//            error(" is not a valid base type", MdfPackage.Literals.MDF_BUSINESS_TYPE__TYPE);
//            return;
//		}
		
		delegate.validateTypeName(getMessageAcceptor(), type);
	}
	
	@Check
	public void checkEnumeration(MdfEnumeration enumeration) {
		
		delegate.validateFieldName(getMessageAcceptor(), enumeration);
		
        MdfEntity type = enumeration.getType();
        if (type == null) {
            error("An enumeration type must be specified", MdfPackage.Literals.MDF_ENUMERATION__TYPE);
            return;
        }

        if (!(type instanceof MdfPrimitive)) {
            error(type.getQualifiedName() + " is not a valid enumeration type", 
            		MdfPackage.Literals.MDF_ENUMERATION__TYPE);
            return;
        }
        
	}
	
	@Check(CheckType.FAST)
	public void checkDuplicateEnumeratedValues(MdfEnumeration enumeration) {
		
        Map<String, Integer> duplicates = new HashMap<String, Integer>();

        // check for duplicated values in enumerated values (linear time)
        @SuppressWarnings("unchecked")
		List<MdfEnumValue> values = enumeration.getValues();
        for (MdfEnumValue value : values) {
        	String key = value.getValue();
        	Integer n = duplicates.get(key);
        	n = (n == null) ? 1 : ++n;
        	duplicates.put(key, n);
        }
        for (MdfEnumValue value : values) {
        	String key = value.getValue();
        	if (duplicates.get(key) > 1) {
        		error("The value "+key+" already exists in this enumeration, please specify another value", 
        				(EObject)value,
                		MdfPackage.Literals.MDF_ENUM_VALUE__VALUE);
        	}
        }
        
        // check for duplicated names in enumerated values (linear time)
        duplicates.clear();
        for (MdfEnumValue value : values) {
        	String key = value.getName();
        	Integer n = duplicates.get(key);
        	n = (n == null) ? 1 : ++n;
        	duplicates.put(key, n);
        }
        for (MdfEnumValue value : values) {
        	String key = value.getName();
        	if (duplicates.get(key) > 1) {
        		error("Duplicate Enumerated value "+key+", please specify another name", 
        				(EObject)value,
                		MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
        	}
        }
	}

	@Check
	public void checkEnumValue(MdfEnumValue enumValue) {
        
        delegate.validateFieldName(getMessageAcceptor(), enumValue); 

        String value = enumValue.getValue();
        if (StringUtils.isBlank(value)) {
            error("A value must be specified", 
            		MdfPackage.Literals.MDF_ENUM_VALUE__VALUE);
            return;
        }

        MdfPrimitive type = (MdfPrimitive) enumValue.getParentEnumeration().getType();
        if (type != null) {
            if (type.equals(PrimitivesDomain.DATE)
                    || type.equals(PrimitivesDomain.DATE_TIME)) {
                if (value.equals("now()") || value.equals("today()")) {
                    error("The value is not valid for this type of enumeration", 
                    		MdfPackage.Literals.MDF_ENUM_VALUE__VALUE);
                }
            }

            if (!PrimitiveTypeValidator.isValidValue(type, value)) {
                error("The value is not valid for this type of enumeration", 
                		MdfPackage.Literals.MDF_ENUM_VALUE__VALUE);
            }
            
            if ("EnumMask".equals(type.getName())) {
            	// if we are here, the value is really an integer.
            	if (Long.valueOf(value) < 0) {
                    error("Negative value is not valid.",
                    		MdfPackage.Literals.MDF_ENUM_VALUE__VALUE);
            	}
            }
        }
        
	}
	
	@Check
	public void checkAssociation(MdfAssociation association) {
		
		delegate.validateFieldName(getMessageAcceptor(), association);
		
        MdfEntity type = association.getType();
        if (type == null) {
            error("Association type must be specified for " + association.getName(),
                    MdfPackage.Literals.MDF_ASSOCIATION__TYPE);
        }
        
        MdfReverseAssociation reverseAssociation = association.getReverse();
        if (reverseAssociation != null) {
        	validateReverseAssociation(reverseAssociation);
        }
        
        // TODO Should be tested with T24 ???
        validateType(type, association);
	}

	@Check
	public void checkAttribute(MdfAttribute attribute ) {
        
		delegate.validateFieldName(getMessageAcceptor(), attribute);

		MdfEntity type = attribute.getType();
        if (type == null) {
            error("An attribute type must be specified", MdfPackage.Literals.MDF_ATTRIBUTE__TYPE);
            return;
        }

    	if (!(type instanceof MdfPrimitive)) {
    		error(type.getQualifiedName() + " is not a valid attribute type", MdfPackage.Literals.MDF_ATTRIBUTE__TYPE);
    		return;
    	}
    	
    	if (type instanceof MdfBusinessType) { 
        	if (delegate.leadsToCyclicDependency(type, attribute.getParentClass().getParentDomain())) {
        		error("Attribute type \'"+type.getQualifiedName()
                        + "\' results in cyclic reference with \'"
                        + ((MdfBusinessType) type).getParentDomain().getQualifiedName() + "\' domain",
                        MdfPackage.Literals.MDF_ATTRIBUTE__TYPE);
        		return;
        	}
    	} else {
            String dflt = attribute.getDefault();
            if (dflt != null) {
                if (!isValidDefaultValue(attribute)) {
                    error("The default value is not valid for the chosen type", MdfPackage.Literals.MDF_ATTRIBUTE__TYPE);
                    return;
                }
            }
        }	

    	// TODO this rule is for TAP only
    	if (attribute.getMultiplicity() == MdfConstants.MULTIPLICITY_ONE) {
	        // DS4858: Attribute with Multiplicity MANY
	    	if (type instanceof MdfEnumeration) {
	    		if ("EnumMask".equals(((MdfEnumeration) type).getType().getName())) {
	    			error("The multiplicity must be Many when the underlying business type is EnumMask.",
	    				MdfPackage.Literals.MDF_PROPERTY__MULTIPLICITY);
	    		}
	    	}
    	}		

		// Check multiple primary keys
        if (attribute.isPrimaryKey()) {
            if (attribute.getParentClass().getPrimaryKeys(true).size() > 1) {
            	error("The containing class already has a primary-key, classes can have only one primary-key",
                	MdfPackage.Literals.MDF_PROPERTY__PRIMARY_KEY);
            }
        }
	
	}    

	private void validateReverseAssociation(MdfReverseAssociation reverseAssociation) {
		
		delegate.validateFieldName(getMessageAcceptor(), reverseAssociation);
		
        MdfEntity type = reverseAssociation.getType();
        if (type == null) {
            error("An association type must be specified", MdfPackage.Literals.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE);
            return;
        }

        if (!(type instanceof MdfClass)) {
            error(type.getQualifiedName() + " is not a valid association type", MdfPackage.Literals.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE);
            return;
        } else if (((MdfClass) type).getAttributesRef().isEmpty()) {
            error(type.getQualifiedName() + " is not a valid type for this association. The type must be referenceable", 
            		MdfPackage.Literals.MDF_REVERSE_ASSOCIATION__REVERSED_ASSOCIATION_TYPE);
            return;
        }

        MdfAssociation reverse = reverseAssociation.getAssociation();
        if (reverse == null) {
            error("The reverse association must be specified.", MdfPackage.Literals.MDF_ASSOCIATION__REVERSE); // wrong? (DS-7792)
            return;
        }

        if (reverse.getContainment() != MdfConstants.CONTAINMENT_BYREFERENCE) {
            error(reverse.getQualifiedName() + " is not a valid reverse association. The association containment must be 'By reference'",
                    MdfPackage.Literals.MDF_ASSOCIATION__CONTAINMENT);
            return;
        }

        MdfClass klass = reverseAssociation.getParentClass();
        boolean parentClassOk = false;
        do {
            parentClassOk = klass.equals(reverse.getType());
            klass = klass.getBaseClass();
        } while (!parentClassOk && (klass != null));
        if (!parentClassOk) {
            error(reverse.getQualifiedName() + " is not a valid reverse association. The association type must be "
                    + klass.getQualifiedName() + " or a parent class",
                    MdfPackage.Literals.MDF_ASSOCIATION__REVERSE); // wrong? (DS-7792)
        }

	}
	
	@Check(CheckType.FAST) 
	public void checkDuplicateDatasetProperties(MdfDataset dataset) {
	    Map<String, Integer> duplicates = new HashMap<String, Integer>();
	    @SuppressWarnings("unchecked")
		List<MdfDatasetProperty> properties = dataset.getProperties();
	    for (MdfDatasetProperty property : properties) {
	    	String key = property.getName();
	    	Integer n = duplicates.get(key);
	    	n = (n == null) ? 1 : ++n;
	    	duplicates.put(key, n);
	    }
	    for (MdfDatasetProperty property : properties) {
	    	String key = property.getName();
	    	if (duplicates.get(key) > 1) {
	         	error("Duplicate dataset property "+key+", please specify another name",
	         			(EObject)property,
						MdfPackage.Literals.MDF_MODEL_ELEMENT__NAME);
	    	}
	    }
	}
	
	/*
	 * Here are all the custom validation tests implemented in other plugins. 
	 * Ex: specific validation for T24/TAP products
	 */
	
	@Check(CheckType.FAST) 
	public void checkFastMdfElement(MdfModelElement element) {
		delegate.checkFastMdfElement(getMessageAcceptor(), element);
	}

	@Check(CheckType.NORMAL)
	public void checkNormalMdfElement(MdfModelElement element) {
		delegate.checkNormalMdfElement(getMessageAcceptor(), element);
	}

	@Check(CheckType.EXPENSIVE)
	public void checkExpensiveMdfElement(MdfModelElement element) {
		delegate.checkExpensiveMdfElement(getMessageAcceptor(), element);
	}
	
} 


